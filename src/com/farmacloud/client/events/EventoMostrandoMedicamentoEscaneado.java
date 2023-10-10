package com.farmacloud.client.events;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public class EventoMostrandoMedicamentoEscaneado extends GenericEvent 
{
	private final String codigoNacional;
	private final boolean esNuevo;
	
	public EventoMostrandoMedicamentoEscaneado(String _codigoNacional, boolean _esNuevo)
	{
		this.codigoNacional = _codigoNacional;
		this.esNuevo = _esNuevo;
	}
	
	public String getCodigoNacional(){
		return this.codigoNacional;
	}
	
	public boolean getEsNuevo(){
		return this.esNuevo;
	}
}
