package com.farmacloud.client;

import com.farmacloud.client.gui.anonymousUser.MenuView;
import com.farmacloud.client.gui.anonymousUser.NoticiasView;
import com.farmacloud.client.gui.loggedUser.LoggedHeaderView;
import com.farmacloud.client.gui.pharmaUsers.MedicamentosAñadirInfoView;
import com.farmacloud.client.gui.pharmaUsers.PedidosAñadirView;
import com.farmacloud.client.gui.pharmaUsers.PedidosMainView;
import com.farmacloud.client.gui.pharmaUsers.RecepcionView;
import com.farmacloud.client.gui.pharmaUsers.SimulacionRunningView;
import com.farmacloud.client.gui.pharmaUsers.VentaView;
import com.farmacloud.client.ui.medicamentos.MedicamentosView;
import com.farmacloud.client.ui.menu.MenuPharmaView;
import com.farmacloud.client.ui.noticias.EscribirNoticiaView;
import com.farmacloud.client.ui.proveedores.AñadirProveedorView;
import com.farmacloud.client.ui.proveedores.VerProveedoresView;
import com.farmacloud.client.ui.registro.RegistroView;
import com.farmacloud.client.ui.registroPharma.RegistroPharmaView;
import com.farmacloud.client.ui.simulacion.SimularView;
import com.farmacloud.client.ui.widgetsold.InfoMedicamentoView;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceHistoryMapper;

public interface ClientFactory 
{
	EventBus getEventBus();
	PlaceControllerExt getPlaceController();
	PlaceHistoryMapper getPlaceHistoryMapper();
	//AppPlaceHistoryMapper getAppPlaceHistoryMapper();

	LoggedHeaderView getLoggedView();
	RecepcionView getRecepcionView();
	
	/* Medicamentos */
	InfoMedicamentoView getInfoMedicamentoView();
	MedicamentosView getMedicamentosView();
	MedicamentosAñadirInfoView getMedicamentosAñadirInfoView();
	
	/* Menu */
	MenuView getHomeView();
	MenuPharmaView getMenuPharmaView();
	
	/* Noticias */
	EscribirNoticiaView getEscribirNoticiaView();
	NoticiasView getNoticiaView();
	
	/* Pedidos */
	PedidosAñadirView getPedidosAñadirView();
	PedidosMainView getPedidosMainView();

	/* Proveedores*/
	AñadirProveedorView getProveedoresAñadirView();
	VerProveedoresView getVerProveedoresView();
	
	/* Registro */
	RegistroView getRegistroView();
	RegistroPharmaView getRegistroPharmaView();
	
	/* Simulacion */
	SimularView getSimularView();
	SimulacionRunningView getSimulacionRunningView();
	
	/* Ventas */
	VentaView getVentasView();
}
