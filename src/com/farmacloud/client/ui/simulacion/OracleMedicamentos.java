package com.farmacloud.client.ui.simulacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import com.farmacloud.client.gui.helpers.RPCSuggestOracle;
import com.farmacloud.client.services.ServicioGestionMedicamento;
import com.farmacloud.client.services.ServicioGestionMedicamentoAsync;
import com.farmacloud.client.ui.modals.ModalRpcFailure;
import com.farmacloud.shared.model.infoView.MedicamentoSuggestion;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle.MultiWordSuggestion;

public class OracleMedicamentos extends RPCSuggestOracle
{
	private final ServicioGestionMedicamentoAsync servicioGestionMedicamento  = GWT.create(ServicioGestionMedicamento.class);
	
	@Override
	public void sendRequest(final Request request, final Callback callback) 
	{
		  System.out.println("Query de tamaño --> "+request.getQuery().length()+" Buscando coincidencias para: "+request.getQuery().toUpperCase());
		  
		  /* Solo se realiza la peticion si se han introducido por teclado mas de un numero de caracteres */
		  if(request.getQuery().length()>=1)
		  {	 
			  /* Establecemos un limite de sugerencias */
			  request.setLimit(20);
			  
			  /* Solicitamos las sugerencias al servidor */
			  servicioGestionMedicamento.getSuggestions(request, new AsyncCallback<List<MultiWordSuggestion>>()
			  {
					@Override
					public void onSuccess(List<MultiWordSuggestion> result) 
					{
						System.out.println("success at getSuggestions");
						if(!result.isEmpty())
						{
							System.out.println("Recibimos "+result.size() +" coincidencias");
							Response response = new Response();
							response.setSuggestions(result);

							callback.onSuggestionsReady(request, response);
						}
						else{
							sinCoincidencias(request, callback);
						}
					}
					
					@Override
					public void onFailure(Throwable caught) 
					{
						System.out.println("failure at getSuggestions");
						new ModalRpcFailure(true);
					}
				});
		 }
		  else{
			  sinCoincidencias(request, callback);
		  }		
	}
	
	public void sinCoincidencias(final Request request, final Callback callback)
	{
		 System.out.println("sin coincidencias");
		 Response response = new Response();
		 MultiWordSuggestion aux = new MultiWordSuggestion("","No se han encontrado coincidencias");
		 List<MultiWordSuggestion> lista = new ArrayList<MultiWordSuggestion>();
		 lista.add(aux);
		 response.setSuggestions(lista);
		 callback.onSuggestionsReady(request, response);
	}
	
	@Override
	public boolean isDisplayStringHTML(){
		return true;
	}
}
