package com.farmacloud.client.presenter.pharmaUsers;

import java.util.ArrayList;
import java.util.List;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.services.ServicioGestionMedicamento;
import com.farmacloud.client.services.ServicioGestionMedicamentoAsync;
import com.farmacloud.client.ui.medicamentos.MedicamentosView;
import com.farmacloud.client.ui.medicamentos.MedicamentosView.Presenter;
import com.farmacloud.client.ui.widgets.UiHelper;
import com.farmacloud.shared.model.DTO.ProgressUploadDTO;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;

public class MedicamentosPresenter extends AbstractActivity implements Presenter
{
	private ClientFactory clientFactory;
	private MedicamentosView view;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();
	
	private final ServicioGestionMedicamentoAsync servicioGestionMedicamento = GWT.create(ServicioGestionMedicamento.class);
	
	private SubmitCompleteEvent lastResponse;
	private MyTimer t;
	private boolean subidaCompletada = false;
	
	public MedicamentosPresenter(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.view = clientFactory.getMedicamentosView();
		this.view.setPresenter(this);
		panel.setWidget(view.asWidget());
		bind();
	}
	
	public void bind(){
		this.registrations.add(this.view.getSubmit().addClickHandler(new SubmitClickHandler()));
		this.registrations.add(this.view.getForm().addSubmitCompleteHandler(new MySubmitCompletedHandler()));
	}
	
	@Override
	public void onStop(){
		for (HandlerRegistration registration : registrations) {
		      registration.removeHandler();
		}
	}

    public String mayStop(){
    	if(t!=null)
    		if(t.isRunning())
    			t.cancel();
    	return null;
    }

    public void goTo(Place place){
    	clientFactory.getPlaceController().goTo(place);
    }
    
    class SubmitClickHandler implements ClickHandler
    {
    	@Override
		public void onClick(ClickEvent event)
    	{
    		event.preventDefault();
		  	/* Comprobamos que se ha selecionado un fichero */
	        String filename = view.getFileName();
	        if (filename.length() == 0) 
	        {
	           Window.alert("No File Specified!");
	        } 
	        else
	        {
	           view.setSubmitButtonEnable(false);
	           subidaCompletada = false;
	           view.submit();
	           t = new MyTimer();
	           t.schedule(1000);
	        }	
    	}
    }
	
    class MySubmitCompletedHandler implements SubmitCompleteHandler
    {
		@Override
		public void onSubmitComplete(SubmitCompleteEvent response) 
		{
			System.out.println("onSubmitComplete");
			
			subidaCompletada = true;
            // When the form submission is successfully completed, this 
            //event is fired. Assuming the service returned a response 		
			if(lastResponse!=response && !response.equals(""))
			{
				lastResponse = response;
	        	 /* Obtenemos la respuesta */ 
	            String respuesta = response.getResults();
	           
	            /* Eliminamos de la respuesta todo tipo de cabeceras HTML < > */
	            if(!respuesta.equals("")){
	            	System.out.println("failure at upload");
	            	String respuestaLimpia = respuesta.replaceAll("<.+?>", "");
	            	System.out.println(respuestaLimpia);
	            	view.getProgressBar().setPercent(100.0);
	            	view.getProgressBar().setText("100%");
	            	//new MyModal(respuestaLimpia);
	            }
	            else{
	            	System.out.println("success at upload");
	            	view.getProgressBar().setPercent(100.0);
	            	view.getProgressBar().setText("100%");
	            	UiHelper.Notify(true, "", "Catalogo actualizado");
	            }
	        }
			else{
				/* Llamada duplicada. Pasa en algunas versiones de algunos navegadores */
				lastResponse = response;
			}
		}
     }

	private class MyTimer extends Timer
	{
		MyTimer yo = this;
		static final int PERIOCIDAD = 750; //1 segundo
		
		int fallos = 0;
		int afterSubmit = 0;
		static final int MAX_FALLOS = 5;
		static final int CALLS_AFTER_SUBMIT = 2;
		
		@Override
		public void run() 
		{
			servicioGestionMedicamento.progressUpload(new AsyncCallback<ProgressUploadDTO>() 
			{
				@Override
				public void onFailure(Throwable caught) {
					fallos++;
					System.out.println("failure at at progressUpload. Intento numero "+fallos +caught.toString());
					if(fallos<MAX_FALLOS && !subidaCompletada)
						yo.schedule(PERIOCIDAD);
				}
				
				@Override
				public void onSuccess(ProgressUploadDTO result)
				{
					System.out.println("success at progressUpload");
					if(result!=null)
					{
						System.out.println("leidos --> "+result.getProgressInfo());
						if(result.isContentLengthKnown()){
							if(result.getPercentDone()==100){
								subidaCompletada = true;
								System.out.println("porcentaje --> "+result.getPercentDone()+"%");
							}
						}
						
						int porcentaje = result.getPercentDone();

						if(!subidaCompletada){
							yo.schedule(PERIOCIDAD);
							view.getProgressBar().setPercent(porcentaje);
							view.getProgressBar().setText(String.valueOf(porcentaje)+"%");
							view.upDateInfoProgress(result.getProgressInfo());
						}
						else /* Permitimos dos llamadas despues del onSubmitComplete para que se tomen los progresos de la subida lo mas actualizados posibles */
						{
							afterSubmit++;
							if(afterSubmit<=CALLS_AFTER_SUBMIT)
							{
								view.upDateInfoProgress(result.getProgressInfo());
								yo.schedule(PERIOCIDAD);
							}
						}
					}
					else
					{
						System.out.println("recibimos progressUploadDTO null...");
					}
				}
			});
		}
	}
}
