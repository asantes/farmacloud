package com.farmacloud.client;

import com.farmacloud.client.gui.anonymousUser.MenuView;
import com.farmacloud.client.gui.anonymousUser.MenuViewImp;
import com.farmacloud.client.gui.anonymousUser.NoticiasView;
import com.farmacloud.client.gui.anonymousUser.NoticiasViewImp;
import com.farmacloud.client.gui.loggedUser.LoggedHeaderView;
import com.farmacloud.client.gui.loggedUser.LoggedHeaderViewImp;
import com.farmacloud.client.gui.pharmaUsers.MedicamentosAñadirInfoView;
import com.farmacloud.client.gui.pharmaUsers.MedicamentosAñadirInfoViewImp;
import com.farmacloud.client.gui.pharmaUsers.PedidosAñadirView;
import com.farmacloud.client.gui.pharmaUsers.PedidosAñadirViewImp;
import com.farmacloud.client.gui.pharmaUsers.PedidosMainView;
import com.farmacloud.client.gui.pharmaUsers.PedidosMainViewImp;
import com.farmacloud.client.gui.pharmaUsers.RecepcionView;
import com.farmacloud.client.gui.pharmaUsers.RecepcionViewImp;
import com.farmacloud.client.gui.pharmaUsers.SimulacionRunningView;
import com.farmacloud.client.gui.pharmaUsers.SimulacionRunningViewImp;
import com.farmacloud.client.gui.pharmaUsers.VentaView;
import com.farmacloud.client.gui.pharmaUsers.VentaViewImp;
import com.farmacloud.client.places.HomePlace;
import com.farmacloud.client.ui.medicamentos.MedicamentosView;
import com.farmacloud.client.ui.medicamentos.MedicamentosViewImp;
import com.farmacloud.client.ui.menu.MenuPharmaView;
import com.farmacloud.client.ui.menu.MenuPharmaViewImp;
import com.farmacloud.client.ui.noticias.EscribirNoticiaView;
import com.farmacloud.client.ui.noticias.EscribirNoticiaViewImp;
import com.farmacloud.client.ui.proveedores.AñadirProveedorView;
import com.farmacloud.client.ui.proveedores.AñadirProveedorViewImp;
import com.farmacloud.client.ui.proveedores.VerProveedoresView;
import com.farmacloud.client.ui.proveedores.VerProveedoresViewImp;
import com.farmacloud.client.ui.registro.RegistroView;
import com.farmacloud.client.ui.registro.RegistroViewImp;
import com.farmacloud.client.ui.registroPharma.RegistroPharmaView;
import com.farmacloud.client.ui.registroPharma.RegistroPharmaViewImp;
import com.farmacloud.client.ui.simulacion.SimularView;
import com.farmacloud.client.ui.simulacion.SimularViewImp;
import com.farmacloud.client.ui.widgetsold.InfoMedicamentoView;
import com.farmacloud.client.ui.widgetsold.InfoMedicamentoViewImp;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceHistoryMapper;

public class ClientFactoryImp implements ClientFactory{
	
	private final PlaceHistoryMapper historyMapper0 = GWT.create(PlaceHistoryMapperImpl.class);
	private final PlaceHistoryMapper historyMapper = new PlaceHistoryMapperWithoutColon(historyMapper0);
	private final EventBus eventBus = new SimpleEventBus();
	//@SuppressWarnings("deprecation")
	private final PlaceControllerExt placeController = new PlaceControllerExt(eventBus, new HomePlace());
	private final MenuView homeView = new MenuViewImp();
	private final RegistroView registroView = new RegistroViewImp();
	private final RegistroPharmaView registroPharmaView = new RegistroPharmaViewImp();
	private final LoggedHeaderView loggedView = new LoggedHeaderViewImp();
	private final MenuPharmaView menuPharmaView = new MenuPharmaViewImp();
	private final RecepcionView recepcionView = new RecepcionViewImp();
 

	private final AñadirProveedorView proveedoresAñadirView = new AñadirProveedorViewImp();
	private final VerProveedoresView verProveedoresView = new VerProveedoresViewImp();

	private final PedidosAñadirView pedidosAñadirView = new PedidosAñadirViewImp();
	private final MedicamentosView medicamentosView = new MedicamentosViewImp();

	private final PedidosMainView pedidosMainView = new PedidosMainViewImp();
	private final InfoMedicamentoView infoMedicamentoView = new InfoMedicamentoViewImp();
	private final MedicamentosAñadirInfoView medicamentosAñadirInfoView = new MedicamentosAñadirInfoViewImp();
	private final VentaView ventaView = new VentaViewImp();

	private final SimularView simularView = new SimularViewImp();
	private final SimulacionRunningView simulacionRunningView = new SimulacionRunningViewImp();
	private final EscribirNoticiaView escribirNoticiaView = new EscribirNoticiaViewImp();
	private final NoticiasView	noticiaView = new NoticiasViewImp();
	
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public MenuView getHomeView() {
		return homeView;
	}
	
	@Override
	public PlaceControllerExt getPlaceController() {
		return this.placeController;
	}

	@Override
	public LoggedHeaderView getLoggedView() {
		return loggedView;
	}

	@Override
	public MenuPharmaView getMenuPharmaView() {
		return this.menuPharmaView;
	}

	@Override
	public RecepcionView getRecepcionView() {
		return this.recepcionView;
	}

	@Override
	public AñadirProveedorView getProveedoresAñadirView() {
		return proveedoresAñadirView;
	}

	@Override
	public PedidosAñadirView getPedidosAñadirView() {
		return this.pedidosAñadirView;
	}

	@Override
	public MedicamentosView getMedicamentosView() {
		return this.medicamentosView;
	}

	@Override
	public PedidosMainView getPedidosMainView() {
		return this.pedidosMainView;
	}

	@Override
	public InfoMedicamentoView getInfoMedicamentoView() {
		return this.infoMedicamentoView;
	}

	@Override
	public MedicamentosAñadirInfoView getMedicamentosAñadirInfoView() {
		return this.medicamentosAñadirInfoView;
	}

	@Override
	public VentaView getVentasView() {
		return this.ventaView;
	}

	@Override
	public SimularView getSimularView() {
		return this.simularView;
	}

	@Override
	public SimulacionRunningView getSimulacionRunningView() {
		return this.simulacionRunningView;
	}

	@Override
	public EscribirNoticiaView getEscribirNoticiaView() {
		return this.escribirNoticiaView;
	}

	@Override
	public NoticiasView getNoticiaView() {
		return this.noticiaView;
	}

	@Override
	public PlaceHistoryMapper getPlaceHistoryMapper() {
		return this.historyMapper;
	}

	@Override
	public RegistroView getRegistroView() {
		return this.registroView;
	}

	@Override
	public RegistroPharmaView getRegistroPharmaView() {
		return this.registroPharmaView;
	}

	@Override
	public VerProveedoresView getVerProveedoresView() {
		return verProveedoresView;
	}
}
