package com.farmacloud.server.services;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.commons.lang3.time.DateUtils;

import com.farmacloud.client.services.ServicioSimulacion;
import com.farmacloud.server.utiles.PMF;
import com.farmacloud.shared.model.simulacion.MedicamentoFarmaciaSimulacion;
import com.farmacloud.shared.model.simulacion.MedicamentoPedidoSimulacion;
import com.farmacloud.shared.model.simulacion.PedidoSimulacion;
import com.farmacloud.shared.model.simulacion.Simulacion;
import com.farmacloud.shared.model.simulacion.UnidadMedicamentoSimulacion;
import com.farmacloud.shared.model.simulacion.VentaSimulacion;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ImpServicioSimulacion extends RemoteServiceServlet implements ServicioSimulacion 
{
	public static final Logger log = Logger.getLogger(ImpServicioSimulacion.class.getName());
	
	@Override
	public Simulacion crearNuevaSimulacion(Simulacion simulacion,
			List<MedicamentoFarmaciaSimulacion> listaMeds) 
	{
		Simulacion simulacionBBDD = null;
		Simulacion simulacionRetorno = null;
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		/* D E L E T I N G		D E L E T I N G		*/
		Query q10 = pm.newQuery(PedidoSimulacion.class);
		List<PedidoSimulacion> listPed = (List<PedidoSimulacion>) q10.execute();
		for(PedidoSimulacion p : listPed)
		{
			for(MedicamentoPedidoSimulacion m : p.getListaMedicametoPedidoSimulacion())
			{
				p.getListaMedicametoPedidoSimulacion();
				if(p.getListaMedicametoPedidoSimulacion()!=null)
					pm.deletePersistentAll(m.getListaUnidadMedicamentoSimulacion());
			}
			
			pm.deletePersistentAll(p.getListaMedicametoPedidoSimulacion());
			pm.deletePersistent(p);
		}
		Query q11 = pm.newQuery(Simulacion.class);
		List<Simulacion> listaSim = (List<Simulacion>) q11.execute();
		for(Simulacion s : listaSim)
		{
			pm.deletePersistentAll(s.getListaMedicamentoFarmaciaSimulacion());
			pm.deletePersistent(s);
		}
		Query q12 = pm.newQuery(VentaSimulacion.class);
		List<VentaSimulacion> listaV = (List<VentaSimulacion>) q12.execute();
		for(VentaSimulacion v: listaV)
			pm.deletePersistent(v);
		/* D E L E T I N G		D E L E T I N G		*/
		
		/* Obtenemos el num de la Simulacion mas alta */
		int contador;
		Query q = pm.newQuery(Simulacion.class);
		q.setOrdering("num desc");
		List<Simulacion> listaS = (List<Simulacion>) q.execute();
		if(listaS.isEmpty())
			contador = 1;
		else  contador = listaS.get(0).getNum();
		
		/* Buscamos si existe alguna Simulacion en curso */
		Query q2 = pm.newQuery(Simulacion.class);
		q2.setFilter("estado == estadoParam");
		q2.declareParameters("String estadoParam");
		
		boolean exito = false;
		int retries = 5;
		while(retries>0 && !exito)
		{
			try
			{
				/* Si la lista esta vacia entonces no hay ninguna Simulacion en curso */
				List<Simulacion> listaSimulacion = (List<Simulacion>) q2.execute("RUNNING");
				if(listaSimulacion.isEmpty())
				{
					/* Establecemos la informacion de la Simulacion y la persistimos */
					simulacion.setListaMedicamentoFarmaciaSimulacion(listaMeds);
					for(MedicamentoFarmaciaSimulacion mfs: simulacion.getListaMedicamentoFarmaciaSimulacion())
						mfs.setNumDisponibles(mfs.getStockMaximo());
					simulacion.setFechaInicio(new Date());
					simulacion.setNum(contador);
					simulacion.setEstado("RUNNING");
					Date siguienteLlegada = DateUtils.addSeconds(simulacion.getFechaInicio(), simulacion.getFrecuenciaLlegadaPedidos());
					Date siguienteSiguienteLlegada = DateUtils.addSeconds(siguienteLlegada, simulacion.getFrecuenciaLlegadaPedidos());
					simulacion.setSiguienteLlegada(siguienteLlegada);
					simulacion.setSiguienteSiguienteLlegada(siguienteSiguienteLlegada);
					
					tx.begin();
					simulacionBBDD = pm.makePersistent(simulacion);
					
					/* Al tratarse de la creacion de la Simulacion, abastecemos el stock completo de todos los Medicamentos incluidos en la simulacion */
					PedidoSimulacion pedidoSimulacion = new PedidoSimulacion();
					String simulacionKey = simulacion.getEncodedKey();
					pedidoSimulacion.setSimulacionKey(simulacionKey);
					pedidoSimulacion.setEstado("RECEPCIONADO");
					pedidoSimulacion.setFecha(new Date());
					pedidoSimulacion.setFehaLlegada(pedidoSimulacion.getFecha());
					
					for(MedicamentoFarmaciaSimulacion mfs : simulacion.getListaMedicamentoFarmaciaSimulacion())
					{
						MedicamentoPedidoSimulacion medicamentoPedidoSimulacion = new MedicamentoPedidoSimulacion();
						medicamentoPedidoSimulacion.setCodigoNacional(mfs.getCodigoNacional());
						medicamentoPedidoSimulacion.setUnidadesPedidas(mfs.getStockMaximo());
						medicamentoPedidoSimulacion.setUnidadesRecibidas(mfs.getStockMaximo());
						
						for(int i=0; i< mfs.getStockMaximo(); i++)
						{
							UnidadMedicamentoSimulacion unidadMedicamentoSimulacion = new UnidadMedicamentoSimulacion();
							unidadMedicamentoSimulacion.setSimulacionKey(simulacionKey);
							unidadMedicamentoSimulacion.setEstado("DISPONIBLE");
							unidadMedicamentoSimulacion.setCodigoNacional(mfs.getCodigoNacional());
							unidadMedicamentoSimulacion.setParentEncodedKey(pedidoSimulacion.getIdInterno());
							medicamentoPedidoSimulacion.getListaUnidadMedicamentoSimulacion().add(unidadMedicamentoSimulacion);
						}
						pedidoSimulacion.getListaMedicametoPedidoSimulacion().add(medicamentoPedidoSimulacion);
					}
					
					/* Persistimos el Pedido en estado "RECEPCIONADO". Por lo tanto ya existen las UnidadesMedicamentoSimulacion vendibles */
					PedidoSimulacion pedidoSimulacionDDBB = pm.makePersistent(pedidoSimulacion);
					//simulacionBBDD.getPedidosRecepcionadosKeys().add(pedidoSimulacionDDBB.getIdInterno());
					
					//System.out.println("la clave del primer pedido -> "+pedidoSimulacionDDBB.getEncodedKey());
					
					tx.commit();
					
					if(simulacionBBDD!=null)
					{
						simulacionRetorno = pm.detachCopy(simulacionBBDD);
						simulacionRetorno.getListaMedicamentoFarmaciaSimulacion();
					}
					exito = true;
				}
				else
				{
					System.out.println("Server info: ya hay una simulacion en curso");
					throw new SimulacionEnCurso("pues eso...");
				}
			}
			catch (ConcurrentModificationException | SimulacionEnCurso e)
			{
		        if (retries == 0) {
		        	System.out.println("Server error: creando nueva Simulacion. Todas las retries realizadas");
		            try {
						throw e;
					} catch (Throwable e1) {
						e1.printStackTrace();
					}
		        }
		        --retries;
		        System.out.println("Server error: creando nueva Simulacion. Intento numero "+retries);
			}
			finally
			{
				if(tx.isActive()){
					tx.rollback();
				}
				if(retries==0){
				q.closeAll();
				q2.closeAll();
				pm.close();
				}
			}
		}
	
		if(exito){
			System.out.println("Server ok: nueva simulacion");
			q.closeAll();
			q2.closeAll();;
			pm.close();
		}
		return simulacionRetorno;
	}
	
	@Override
	public MedicamentoFarmaciaSimulacion realizarVenta(String simulacionKey, String codigoNacional, int numeroUnidades) 
	{
		/*PersistenceManager pm2 = null;
		Transaction tx2 = null;
		pm2 = PMF.get().getPersistenceManager();
		tx2 = pm2.currentTransaction();
		FetchPlan fp = pm2.getFetchPlan();
		FetchGroup fg = pm2.getFetchGroup(Parent.class, "test");
		fg.addMember("son");
		fp.addGroup("test");*/
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		List<UnidadMedicamentoSimulacion> listaUMSBuenos = new ArrayList<UnidadMedicamentoSimulacion>();	
	
		/* Obtenemos la Simulacion consistentemente */
		Simulacion simulacion = pm.getObjectById(Simulacion.class, KeyFactory.stringToKey(simulacionKey).getId());
		
		/* Obtenemos el indice del MFS del que actualizar parametros(media, contadorVentas, numeroDisponibles...) */
		int indice = -1;
		for(int i=0; i<simulacion.getListaMedicamentoFarmaciaSimulacion().size(); i++)
			if(simulacion.getListaMedicamentoFarmaciaSimulacion().get(i).getCodigoNacional().equals(codigoNacional))
				indice = i;
		
		/* Creamos la Venta por adelantado*/
		VentaSimulacion ventaSimulacion = new VentaSimulacion();
		ventaSimulacion.setFechaVenta(new Date());
		ventaSimulacion.setSimulacionKey(simulacionKey);
		
		boolean exito = false;
		int retries = 5;
		while(retries>0 && !exito)
		{
			try
			{	
				if(pm.isClosed()){
					pm = PMF.get().getPersistenceManager();
					tx = pm.currentTransaction();
				}
				pm.setIgnoreCache(true);
				tx.setRestoreValues(true);
				tx.setRetainValues(true);
			
				tx.begin();
				
				/* Obtenemos la Simulacion consistentemente de nuevo. Por si se ha actualizado */
				simulacion = pm.getObjectById(Simulacion.class, KeyFactory.stringToKey(simulacionKey).getId());
				
				ventaSimulacion.getListaClavesUnidadMedicamentoSimulacion().clear();
				/* Obtenemos los UMS DISPONIBLE del medicamento a vender */
				listaUMSBuenos = getUMSDisponibles(pm, tx, simulacion, codigoNacional);
				
				/* Si la lista esta vacia entonces tenemos rotura de inventario */
				if(!listaUMSBuenos.isEmpty())
				{
					/* Si no tenemos unidades suficientes tambien hay rotura */
					if(listaUMSBuenos.size()>=numeroUnidades)
					{
						/* Guardamos la clave de todos los UMS que vamos a vender en Venta */
						for(int i=0; i<numeroUnidades; i++)
						{
							/* Marcamos los UMS como VENDIDOS */
							UnidadMedicamentoSimulacion unidadAuxMedicamentoSimulacion = listaUMSBuenos.get(i);
							unidadAuxMedicamentoSimulacion.setEstado("VENDIDO");
							ventaSimulacion.getListaClavesUnidadMedicamentoSimulacion().add(unidadAuxMedicamentoSimulacion.getEncodedKey());
						}
						System.out.println("Hemos vendido "+ventaSimulacion.getListaClavesUnidadMedicamentoSimulacion().size());
						
						/* Persistimos la Venta */
						pm.makePersistent(ventaSimulacion);
						
						/* Actualizamos los atributos pertinentes del MedicamentoFarmaciaSimulacion correspondiente con el medicamento vendido */
						int contadorVentasTiempo = simulacion.getListaMedicamentoFarmaciaSimulacion().get(indice).getContadorVentasTiempo() + 1;
						simulacion.getListaMedicamentoFarmaciaSimulacion().get(indice).setContadorVentasTiempo(contadorVentasTiempo);
						
						int contadorVentas = simulacion.getListaMedicamentoFarmaciaSimulacion().get(indice).getContadorVentas() + numeroUnidades;
						simulacion.getListaMedicamentoFarmaciaSimulacion().get(indice).setContadorVentas(contadorVentas);
						
						double media = (double)contadorVentas/contadorVentasTiempo;
						simulacion.getListaMedicamentoFarmaciaSimulacion().get(indice).setMedia(media);
						
						simulacion.getListaMedicamentoFarmaciaSimulacion().get(indice).getListaVentasTiempo().add(new Integer(numeroUnidades));
						
						/* Calculamos la varianza */
						double sumatorioCuadrado = 0.0;
						if(!simulacion.getListaMedicamentoFarmaciaSimulacion().get(indice).getListaVentasTiempo().isEmpty())
						{
							for(Integer i : simulacion.getListaMedicamentoFarmaciaSimulacion().get(indice).getListaVentasTiempo())
								sumatorioCuadrado = (sumatorioCuadrado + (i.intValue()*i.intValue()));
								
							double varianza = (double)((sumatorioCuadrado/contadorVentasTiempo)) - (media*media);
							simulacion.getListaMedicamentoFarmaciaSimulacion().get(indice).setVarianza(varianza);
							System.out.println("varianxa "+varianza);
							System.out.println("m3dia "+media);
						}
						/* ---------------------- */
						
						int disponibles = listaUMSBuenos.size()-numeroUnidades;
						if(disponibles>=0)
							simulacion.getListaMedicamentoFarmaciaSimulacion().get(indice).setNumDisponibles(disponibles);
						else throw new DisponiblesNegativos("Server Exception: disponibles negativos");
						
					}
					else 
					{
						System.out.println("***************************************************************************");
						System.out.println("Server fallo: nos hemos quedado sin Unidades suficientes de "+codigoNacional);
						System.out.println("***************************************************************************");
					}
				}
				else
				{	System.out.println("***************************************************************************");
					System.out.println("Server fallo: nos hemos quedado sin Unidades de "+codigoNacional +" !!");
					System.out.println("***************************************************************************");
				}
			
				tx.commit();
				exito = true;
			}
			catch (java.util.ConcurrentModificationException | DisponiblesNegativos |javax.jdo.JDOException e)
			{
		        if (retries == 0)
		        {
		        	System.out.println("Server error: simulacion venta. Todas las retries realizadas");
		            try {
						throw e;
					} catch (Throwable e1) {
						e1.printStackTrace();
					}
		        }
		        else{
			        --retries;
			        System.out.println("Server error: simulacion venta. Intento numero "+retries);
			        System.out.println(e);
		        }
			}
			finally
			{
				System.out.println("SOY FINALLY");
				if (tx.isActive()){
			        tx.rollback();
			        pm.close();
			    }
			}
		}
		
		/* Si la venta ha tenido exito, comprobamos el punto de pedido */
		//if(exito)
		//{
			System.out.println("Server ok: venta. Con "+simulacion.getListaMedicamentoFarmaciaSimulacion().get(indice).getNumDisponibles());
			MedicamentoFarmaciaSimulacion mfs = pm.detachCopy(simulacion.getListaMedicamentoFarmaciaSimulacion().get(indice));
			pm.close();
			comprobarPuntoPedido(simulacionKey, indice);
			return mfs;
		//}
		
		//return null;
	}
	
	void comprobarPuntoPedido(String simulacionKey, int indice)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Simulacion simulacionEnCurso;

		boolean exito = false;
		int retries = 5;
		while(retries>0 && !exito)
		{
			try
			{
				tx.begin();
				
				/* Obtenemos la entidad simulacion de forma consistente */
				simulacionEnCurso = pm.getObjectById(Simulacion.class, KeyFactory.stringToKey(simulacionKey).getId());
				if(true)
				{
					/* Obtenemos los atributos necesarios para trabajar con mayor facilidad */
					String codigoNacional = simulacionEnCurso.getListaMedicamentoFarmaciaSimulacion().get(indice).getCodigoNacional();
					int tiempoGuia = simulacionEnCurso.getFrecuenciaLlegadaPedidos();
					double media = simulacionEnCurso.getListaMedicamentoFarmaciaSimulacion().get(indice).getMedia();
					double varianza = simulacionEnCurso.getListaMedicamentoFarmaciaSimulacion().get(indice).getVarianza();
					double desvTipica = Math.sqrt(varianza);
					int disponibles = getUMSDisponibles(pm, tx, simulacionEnCurso, codigoNacional).size();
					int unidadDeTiempo = simulacionEnCurso.getFrecuenciaSimulacion();
					int frecuencia = simulacionEnCurso.getFrecuenciaLlegadaPedidos();
					Date siguienteLlegada = simulacionEnCurso.getSiguienteLlegada();

					/* Calculamos la diferencia entre tiempos */
					double diferencia = getDateDiff(new Date(), siguienteLlegada,TimeUnit.MILLISECONDS)/1000;
					
					/* Calculamos el punto de reabastecimiento. Necesitamos R y S */
					double r = media * (tiempoGuia/simulacionEnCurso.getFrecuenciaSimulacion());
					double s;
					if(varianza>0)
						 s = 0.8289 * desvTipica * Math.sqrt(diferencia);
					else s = 0;
					double puntoDeReabastecimiento = r + s;
					
					double necesarios = (diferencia/unidadDeTiempo)*media;
					System.out.println("DIFERENCIA = "+diferencia);
					System.out.println("MEDIA DEL MED. = "+media);
					System.out.println("LOS DISPONIBLS = "+disponibles);
					System.out.println("R = "+r +" S = "+s);
					System.out.println("PUNTO DE PEDIDO = "+puntoDeReabastecimiento);
					
					/* Si se cumple la condicion es que necesitamos pedir el medicamento */
					if(puntoDeReabastecimiento>=disponibles && !simulacionEnCurso.getListaMedicamentoFarmaciaSimulacion().get(indice).isEsperandoUnidades())
					{
						System.out.println("Server info: Hay que pedir unidades");
						MedicamentoFarmaciaSimulacion mfs = simulacionEnCurso.getListaMedicamentoFarmaciaSimulacion().get(indice);
						MedicamentoPedidoSimulacion mps = new MedicamentoPedidoSimulacion();
						mps.setCodigoNacional(mfs.getCodigoNacional());
						double sobrantes = disponibles - ((double)(diferencia/frecuencia) * media);
						double unidadesNecesarias = (media*(tiempoGuia/simulacionEnCurso.getFrecuenciaSimulacion())) - sobrantes ;
						System.out.println("NECESARIAS "+unidadesNecesarias);
						mps.setUnidadesPedidas((int) (unidadesNecesarias+6.0));
						
						/* Si no existe un Pedido con estado "FORMANDO" lo creamos */
						if(simulacionEnCurso.getPedidFormandoseKey()==null)
						{
							//Creamos el pedido
							System.out.println("Creando nuevo Pedido");
							PedidoSimulacion pedidoSimulacion = new PedidoSimulacion();
							pedidoSimulacion.getListaMedicametoPedidoSimulacion().add(mps);
							pedidoSimulacion.setEstado("FORMANDO");
							pm.makePersistent(pedidoSimulacion);
						//	simulacionEnCurso.setPedidFormandoseKey(pedidoSimulacion.getEncodedKey());
						}
						/* Si ya existe un Pedido "FORMANDO", añadimos a este el MedicamentoFarmaciaSimulacion a pedir con su numero de unidads UMS */
						else
						{
							//Añadimos a pedido existente
							System.out.println("Añadiendo a pedido existente");
							/* Obtenemos el pedido */
							PedidoSimulacion pedidoSimulacion = pm.getObjectById(PedidoSimulacion.class, KeyFactory.stringToKey(simulacionEnCurso.getPedidFormandoseKey()).getId());
							/* Añadimos el medicamento y su cantidades a pedir al pedido */
							pedidoSimulacion.getListaMedicametoPedidoSimulacion().add(mps);
						}
						
						simulacionEnCurso.getListaMedicamentoFarmaciaSimulacion().get(indice).setEsperandoUnidades(true);
					}
					else
					{
						System.out.println("Server info: No es necesario pedir aun");
					}
				}
				tx.commit();
				exito = true;
			}
			catch (ConcurrentModificationException | javax.jdo.JDOException e)
			{
		        if (retries == 0) {
		        	System.out.println("Server error: Comprobando PDP. Todass las retries realizadas");
		        	System.out.println(e);
						throw e;
		        }
		        --retries;
		        System.out.println("Server error: Comprobando PDP. Intento numero "+retries);
		        System.out.println(e);
			}
			finally
			{
				if (tx.isActive()){
			        tx.rollback();
			    }
				if(retries==0){
		        	pm.close();
				}
			}
		}
		
		if(exito){
			pm.close();
		}
	}
		
	@Override
	public void comprobarTiemposSimulacion(String simulacionKey) 
	{
		int unaConstante = -1;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		/* Tenemos que comprobar:
		 * 				
		 * 		1º) Si hay que actualizar los atributos de la siguienteLlegada de pedido(entidad Simulacion)
		 * 			-en caso de tener que actualizar dichos tiempos, se realizara el Pedido "FORMANDO" que tiene Simulacion
		 */
		
		long idKey = KeyFactory.stringToKey(simulacionKey).getId();
		Date fechaActual = new Date();
		double diferenciaTiempo;
		
		boolean exito = false;
		int retries = 5;
		while(retries>0 && !exito)
		{
			try
			{
				if(pm.isClosed()){
					pm = PMF.get().getPersistenceManager();
					tx = pm.currentTransaction();
				}
				tx.begin();
				
				Simulacion simulacion = pm.getObjectById(Simulacion.class,idKey);
				diferenciaTiempo = getDateDiff(simulacion.getSiguienteLlegada(), fechaActual, TimeUnit.MILLISECONDS)/1000;
				
				/* Si la diferencia entre el tiempo actual y el de llegada del siguiente reparto es pequeña hacemos cambios.
				 * Dos posibilidadades:
				 * 			1) que la diferencia sea mayor que cero.
				 * 			2) que la diferencia sea menor que cero.
				 */
				if(diferenciaTiempo>=0 || (diferenciaTiempo<0 && diferenciaTiempo>=unaConstante))
				{	
					/* El tiempo siguiente-siguiente viejo pasa a ser el siguiente nuevo */
					Date tiempoLlegadaViejo = simulacion.getSiguienteLlegada();
					Date tiempoLlegadaLlegadaViejo = simulacion.getSiguienteSiguienteLlegada();
					simulacion.setSiguienteSiguienteLlegada(new Date());
					simulacion.setSiguienteLlegada(tiempoLlegadaLlegadaViejo);
					simulacion.setSiguienteSiguienteLlegada(DateUtils.addSeconds(simulacion.getSiguienteLlegada(), simulacion.getFrecuenciaLlegadaPedidos()));
					
					/* Obtenemos el pedido "FORMANDO" de forma consistente */	
					if(simulacion.getPedidFormandoseKey()!=null)
					{
						long pedidoFormandoKey = KeyFactory.stringToKey(simulacion.getPedidFormandoseKey()).getId();
						PedidoSimulacion pedidoSimulacionFormando = pm.getObjectById(PedidoSimulacion.class, pedidoFormandoKey);
						
						/* El tiempo actual aun no ha sido superado por el de llegada. La diferencia es lo suficientemente pequeña como para cambiar tiempos, este
						 * margen viene dado por BLABLA. De esta forma se simula la hora limite de pedido.
						 */
						if(diferenciaTiempo<0)
						{
							System.out.println("El pedido pasa de FORMANDO a ESPERANDO");
							/* El pedido pasa de estado "FORMANDO" a "ESPERANDO. Esperando a ser recepcionado (se recepcionara muy brevemente) */
							pedidoSimulacionFormando.setEstado("ESPERANDO"); 
							pedidoSimulacionFormando.setFecha(new Date());
							pedidoSimulacionFormando.setFehaLlegada(tiempoLlegadaViejo); /* Se recepciona cuando llega el siguiente camion */
							/* En la entidad Simulacion, reflejamos que pasa de un estado a otro */
							simulacion.getPedidosEsperandoRecepcionKeys().add(0, simulacion.getPedidFormandoseKey());
							simulacion.setPedidFormandoseKey(null);
						}
						
						/* Si la diferencia es positiva es que el tiempo actual a superado al de la llegada del pedido. Esto no es deseable... */
						if(diferenciaTiempo>=0)
						{
							System.out.println("El pedido pasa de FORMANDO a ESPERANDO");
							/* El pedido pasa de estado "FORMANDO" a "ESPERANDO. Esperando a ser recepcionado (se recepcionara muy brevemente) */
							pedidoSimulacionFormando.setEstado("ESPERANDO"); 
							pedidoSimulacionFormando.setFecha(new Date());
							pedidoSimulacionFormando.setFehaLlegada(tiempoLlegadaViejo); /* Se recepciona cuando llega el siguiente camion */
							/* En la entidad Simulacion, reflejamos que pasa de un estado a otro */
							simulacion.getPedidosEsperandoRecepcionKeys().add(0, simulacion.getPedidFormandoseKey());
							simulacion.setPedidFormandoseKey(null);
						}
					}
				}
				
				tx.commit();
				exito = true;
			}
			catch (ConcurrentModificationException | javax.jdo.JDOException e)
			{
		        if (retries == 0) {
		        	System.out.println("Server comprobando tiempos: error");
						throw e;
		        }
		        --retries;
		        System.out.println("Server comprobando tiempos: retry "+retries);
			}
			finally
			{
				if (tx.isActive()){
			        tx.rollback();
			    }
			
				pm.close();
			}
		}
		
		recepcionarPedido(simulacionKey);
	}

	void recepcionarPedido(String key)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		
		String codigoNacional = null;
		boolean exito = false;
		int retries = 5;
		while(retries>0 && !exito)
		{
			System.out.println("RECEPCION VUELVO A ITERAR");
			try
			{
				/* Si se ha cerrado el PersistentManaeger lo abrimos */
				if(pm.isClosed()){
					System.out.println("Lo abrimos Recepcion Persistence Manaher");
					pm = PMF.get().getPersistenceManager();
					tx = pm.currentTransaction();
				}
				
				/* Empieza la transaccion */
				tx.begin();
				
				/* Obtenemos la entidad simulacion*/
				Simulacion simulacion = null;
				try{
				simulacion = pm.getObjectById(Simulacion.class,key);
				}catch(Exception e){
					System.out.println(e);
					log.log(Level.INFO, "EEEEEEEEEEEEEEEEEE");
				}
		
				System.out.println("SEGUN LA RECIBO TENEMOS UN NUMERO DE ESPERANDO RECEPCIONAR DE -> "+simulacion.getPedidosEsperandoRecepcionKeys().size());
				System.out.println("Hemos recepcionado");
				for(String s: simulacion.getPedidosRecepcionadosKeys())
					System.out.println(s);
				
				/* Para todos los pedidos en estado "ESPERANDO" comprobamos si necesitan ser ya recepcionados */
				if(!simulacion.getPedidosEsperandoRecepcionKeys().isEmpty())
				{
					for(String keyPedido: simulacion.getPedidosEsperandoRecepcionKeys())
					{
						System.out.println("Server recepcionando: hay un pedido ESPERANDO");
						
						/* Obtenemos cada pedido "ESPERANDO" de forma consistente */
						long idPedido = KeyFactory.stringToKey(keyPedido).getId(); 
						PedidoSimulacion pedidoEsperando = pm.getObjectById(PedidoSimulacion.class, idPedido);
						
						/* Calculamos la diferencia de tiempo entre la hora de llegada del pedido y la hora actual */
						double diferenciaTiempo = getDateDiff(new Date(), pedidoEsperando.getFehaLlegada(), TimeUnit.MILLISECONDS)/1000;
						
						System.out.println("Server recepcionando: la diferencia de tiempo es "+diferenciaTiempo);
						
						/* Si la diferencia es igual o mayor que cero toca recepcionar el pedido */
						if(diferenciaTiempo<=0.0)
						{
							System.out.println("Server recepcionando: toca recepcionar pedido");
							
							/* Para cada medicamento ordenado en el pedido creamos las correspondientes unidades */
							for(MedicamentoPedidoSimulacion mps : pedidoEsperando.getListaMedicametoPedidoSimulacion())
							{
								List<UnidadMedicamentoSimulacion> listaUMS = new ArrayList<UnidadMedicamentoSimulacion>();
								for(int i=0; i<mps.getUnidadesPedidas(); i++)
								{
									/* Creamos tantas UnidadesMedicamentoSimulacion(las que se venden) como unidades demandadas y las marcamos como DISPONIBLES */
									UnidadMedicamentoSimulacion ums = new UnidadMedicamentoSimulacion();
									if(!mps.getListaUnidadMedicamentoSimulacion().isEmpty())
										System.out.println("ESTO ES LA HOSIA YA: tenia que estar vacio!!!!"+mps.getListaUnidadMedicamentoSimulacion().size());
									ums.setCodigoNacional(mps.getCodigoNacional());
									ums.setEstado("DISPONIBLE");
									ums.setSimulacionKey(key);
									ums.setFechaCreacion(new Date());
									listaUMS.add(ums);
								}
								System.out.println("Server recepcion: se han creado "+listaUMS.size());
								mps.getListaUnidadMedicamentoSimulacion().addAll(listaUMS);
								mps.setUnidadesRecibidas(listaUMS.size());
							
								/* Establecemos el nuevo numero de unidades disponibles de dicho medicamento */
								for(MedicamentoFarmaciaSimulacion mfs : simulacion.getListaMedicamentoFarmaciaSimulacion())
								{
									if(mfs.getCodigoNacional().equals(mps.getCodigoNacional()))
									{
										codigoNacional = mfs.getCodigoNacional();
										mfs.setEsperandoUnidades(false);
										
										/* Obtenemos todas las unidades DISPONIBLE actualmente de dicho medicamento */
										List<UnidadMedicamentoSimulacion> listaUMSDefinitiva = getUMSDisponibles(pm, tx, simulacion, codigoNacional);
										int numDisponibles = listaUMSDefinitiva.size();
										
										System.out.println("Server informa recepcion pedido:");
										System.out.println("	disponibles = "+numDisponibles +"	pedidas = "+mps.getUnidadesPedidas());
										
										mfs.setNumDisponibles(numDisponibles+mps.getUnidadesPedidas());
										if(mfs.getNumDisponibles()>mfs.getStockMaximo())
										{
											System.out.println("***************************************************************************");
											System.out.println("HEMOS SUPERADO EL SM "+mfs.getNumDisponibles());
											System.out.println("***************************************************************************");
										}
									}
								}
							}
							 /* Marcamos el estado del Pedido como "RECEPCIONADO" */
							pedidoEsperando.setEstado("RECEPCIONADO");
							
							/* Eliminamos el pedido de la lista ESPERANDO de la entidad Simulacion */
							int indice = -1;
							for(int i=0; i<simulacion.getPedidosEsperandoRecepcionKeys().size(); i++)
								if(simulacion.getPedidosEsperandoRecepcionKeys().get(i).equals(keyPedido))
									indice = i;
							simulacion.getPedidosEsperandoRecepcionKeys().remove(indice);
							
							/* Lo añadimos a la lista de RECEPCIONADOS */
							//simulacion.getPedidosRecepcionadosKeys().add(pedidoEsperando.getEncodedKey());
							
							//System.out.println("Server recepcion: recepcionado con key "+pedidoEsperando.getEncodedKey());
							System.out.println("\t tamaño "+simulacion.getPedidosRecepcionadosKeys().size());
		
							System.out.println("Se tenia que recepcionar a las : "+pedidoEsperando.getFehaLlegada() +" y se ha hecho a las : "+new Date());
						}
					}
				}
				
				/* Guardamos los cambios */
				tx.commit();
				exito = true;
			}
			catch (ConcurrentModificationException | javax.jdo.JDOException e)
			{
		        if (retries == 0) {
		        	System.out.println("Server recepcion: recepcion mal");
						throw e;
		        }
		        --retries;
		        System.out.println("Server recepcion: reintento recepcion "+retries);
		        System.out.println(e);
			}
			finally
			{
				if (tx.isActive()){
					System.out.println("Roleado recepccion");
			        tx.rollback();
			    }
				
				System.out.println("CErrando el pm recepcion");
		        pm.close();
			}
		}
		
		if(exito)
			System.out.println("Server recepcion: ok");
	}

	List<UnidadMedicamentoSimulacion> getUMSDisponibles(PersistenceManager pm, Transaction tx, Simulacion simulacion, String codigoNacional)
	{
		List<UnidadMedicamentoSimulacion> listaUmsDefinitiva = new ArrayList<UnidadMedicamentoSimulacion>();
		
		/* Ancestor Query para obtener los UnidadMedicamentoSimulacion a partir de su abuelo PedidoSimulacion */
		Query qUms = pm.newQuery(UnidadMedicamentoSimulacion.class);
		qUms.setFilter("parentEncodedKey == parentEncodedKeyParam && codigoNacional == codigoNacionalParam && estado == estadoParam");
		qUms.declareParameters("String parentEncodedKeyParam, String codigoNacionalParam, String estadoParam");
		
		
		/* Iteramos para todos los PedidoSimulacion en estado RECEPCIONADO */
		for(String s : simulacion.getPedidosRecepcionadosKeys())
		{	
			/* Obtenemos todos los UnidadMedicamentoSimulacion del PedidoSimulacion que esten DISPONIBLE */
			if(s!=null)
			{
				List<UnidadMedicamentoSimulacion>listaUmsAux = (List<UnidadMedicamentoSimulacion>) qUms.execute(s, codigoNacional, "DISPONIBLE");
				if(!listaUmsAux.isEmpty())
					for(UnidadMedicamentoSimulacion ums : listaUmsAux)
						listaUmsDefinitiva.add(ums);		
			}
		}
		
		System.out.println("NUEVO METODO: tenemos "+listaUmsDefinitiva.size() +" disponibles");
		
		qUms.closeAll();
		return listaUmsDefinitiva;
	}
	
	class DisponiblesNegativos extends Throwable {
		
		public DisponiblesNegativos(String mensaje){
			super(mensaje);
		}
	}
	
	class SimulacionEnCurso extends Throwable {
			
		public SimulacionEnCurso(String mensaje){
			super(mensaje);
		}
	}
	
	/**
	 * Get a diff between two dates
	 * @param date1 the oldest date
	 * @param date2 the newest date
	 * @param timeUnit the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
}










