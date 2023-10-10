package com.farmacloud.client.presenter.pharmaUsers;

import java.util.ArrayList;
import java.util.List;

import com.farmacloud.client.ClientFactory;
import com.farmacloud.client.gui.pharmaUsers.SimulacionRunningView;
import com.farmacloud.client.gui.pharmaUsers.SimulacionRunningView.Presenter;
import com.farmacloud.client.places.SimulacionRunningPlace;
import com.farmacloud.client.services.ServicioSimulacion;
import com.farmacloud.client.services.ServicioSimulacionAsync;
import com.farmacloud.shared.model.simulacion.MedicamentoFarmaciaSimulacion;
import com.farmacloud.shared.model.simulacion.Simulacion;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SimulacionRunningPresenter extends AbstractActivity implements Presenter
{
	private SimulacionRunningView view;
	private ClientFactory clientFactory;
	private List<HandlerRegistration> registrations = new ArrayList();
	
	private final ServicioSimulacionAsync servicioSimulacion = GWT.create(ServicioSimulacion.class);
	private final ServicioSimulacionAsync servicioSimulacionCheck = GWT.create(ServicioSimulacion.class);
	
	private SimulacionRunningPlace simulacionPlace;
	
	public SimulacionRunningPresenter(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) 
	{
		this.view = clientFactory.getSimulacionRunningView();
		this.view.setPresenter(this);
		panel.setWidget(view.asWidget());
		
		if(this.clientFactory.getPlaceController().getWhere() instanceof SimulacionRunningPlace)
		{
			System.out.println("Si que lo es");
			this.simulacionPlace = (SimulacionRunningPlace) this.clientFactory.getPlaceController().getWhere();
			if(simulacionPlace.getSimulacion()!=null && !simulacionPlace.getListaAux().isEmpty())
				crearSimulacion(simulacionPlace);
		}
	}
	
	@Override
	public void onStop(){
		for (HandlerRegistration registration : registrations) {
		      registration.removeHandler();
		}
	}
	
    public void goTo(Place place){
    	clientFactory.getPlaceController().goTo(place);
    }

    void crearSimulacion(SimulacionRunningPlace simulacionRunningPlace)
    {
		servicioSimulacion.crearNuevaSimulacion(simulacionRunningPlace.getSimulacion(), simulacionRunningPlace.getListaAux(), new AsyncCallback<Simulacion>() 
		{
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Cliente fallo: creando simulacion");
			}

			@Override
			public void onSuccess(Simulacion result)
			{
				System.out.println("Cliente ok: creando simulacion");
				if(result!=null)
				{
					System.out.println(result.getFrecuenciaSimulacion()+result.getListaMedicamentoFarmaciaSimulacion().get(0).getCodigoNacional());
					//listaMedicamentosASimular.clear();
					simular(result);
				}
			}
		});
    }
    
    public void simular(final Simulacion simulacionEnCurso)
    {
		RealizarVentaTimer realizarVentaTimer = new RealizarVentaTimer(simulacionEnCurso);
	    CheckTimer checkTimer = new CheckTimer(simulacionEnCurso);
	    
	    System.out.println("Se han creado los dos Timer!");
	    
	    realizarVentaTimer.scheduleRepeating(simulacionEnCurso.getFrecuenciaSimulacion()*1000);
	    checkTimer.scheduleRepeating(simulacionEnCurso.getFrecuenciaSimulacion()*1000/5);   
    }
    
    class RealizarVentaTimer extends Timer
    {
    	Simulacion simulacionEnCurso;

    	public RealizarVentaTimer(Simulacion _simulacionEnCurso)
    	{
    		super();
    		this.simulacionEnCurso = _simulacionEnCurso;
		}
    	
		@Override
		public void run() 
		{
	    	for(MedicamentoFarmaciaSimulacion m : simulacionEnCurso.getListaMedicamentoFarmaciaSimulacion())
	    	{
	    		/* Calculamos la Poison */
	    		int poison = poisson(m.getParametro());
	    		
	    		/* Pedimos los medicamentos */
	    		System.out.println("Justo antes de realizar venta del medicamento "+m.getCodigoNacional());
	    		servicioSimulacion.realizarVenta(simulacionEnCurso.getEncodedKey(), m.getCodigoNacional(), poison, new VentaSimulacionRellamada());
	    	}
		}
    }
    
    class CheckTimer extends Timer
    {
    	Simulacion simulacionEnCurso;

    	public CheckTimer(Simulacion _simulacionEnCurso)
    	{
    		super();
    		this.simulacionEnCurso = _simulacionEnCurso;
		}
		@Override
		public void run() 
		{
			System.out.println("Check routine: justo antes de chekear tiempos");
			servicioSimulacionCheck.comprobarTiemposSimulacion(simulacionEnCurso.getEncodedKey(), new CheckSimulacionRellamada());
		}
    }
    
    public int poisson(int landa)
    { 
	  int r = 0;
	  do
	  {
	    double a = Random.nextDouble();
	    double p = Math.exp(-landa);

	    while (a > p) 
	    {
	        r++;
	        a = a - p;
	        p = p * landa / r;
	    }
	  }while(r>2*landa);
	  
	    System.out.println("Me sale un poisson de: "+r);
	    return r;
    }
    
   class VentaSimulacionRellamada implements AsyncCallback<MedicamentoFarmaciaSimulacion>
   {
		@Override
		public void onFailure(Throwable caught) 
		{
			System.out.println("Cliente error: venta simulacion");
		}
	
		@Override
		public void onSuccess(MedicamentoFarmaciaSimulacion result) 
		{
			System.out.println("Cliente ok: venta simulacion");
			/* Actualizamos la tabla */
			if(result!=null)
			{
				System.out.println("Actualizando dataprovider");
				//view.getDataProvider().addMedicament(result);
				//view.getDataProvider().refreshDisplays();
			}
		}
   }
   
   class CheckSimulacionRellamada implements AsyncCallback<Void>
   {
		@Override
		public void onFailure(Throwable caught)
		{
			System.out.println("Check error: al volver");
		}
	
		@Override
		public void onSuccess(Void result) 
		{
			System.out.println("Check ok: al volver");
		}
   }
}
