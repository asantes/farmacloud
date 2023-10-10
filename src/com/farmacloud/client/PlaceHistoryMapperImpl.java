package com.farmacloud.client;

import com.farmacloud.client.places.EscribirNoticiaPlace;
import com.farmacloud.client.places.HomePlace;
import com.farmacloud.client.places.LoggedHomePlace;
import com.farmacloud.client.places.MedicamentosAñadirPlace;
import com.farmacloud.client.places.MedicamentosMenuPlace;
import com.farmacloud.client.places.MedicamentosPlace;
import com.farmacloud.client.places.NoticiasPlace;
import com.farmacloud.client.places.PedidosMainPlace;
import com.farmacloud.client.places.PedidosMenuPlace;
import com.farmacloud.client.places.PedidosNuevoPlace;
import com.farmacloud.client.places.PharmaHomePlace;
import com.farmacloud.client.places.AñadirProveedor;
import com.farmacloud.client.places.ProveedoresPlace;
import com.farmacloud.client.places.RecepcionPlace;
import com.farmacloud.client.places.RegistroPharmaPlace;
import com.farmacloud.client.places.RegistroPlace;
import com.farmacloud.client.places.SimulacionRunningPlace;
import com.farmacloud.client.places.SimularPlace;
import com.farmacloud.client.places.VentaPlace;
import com.farmacloud.client.places.VerProveedoresPlace;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({HomePlace.Tokenizer.class, RegistroPlace.Tokenizer.class, RegistroPharmaPlace.Tokenizer.class, LoggedHomePlace.Tokenizer.class,
				PharmaHomePlace.Tokenizer.class, RecepcionPlace.Tokenizer.class, ProveedoresPlace.Tokenizer.class,
				AñadirProveedor.Tokenizer.class, VerProveedoresPlace.Tokenizer.class, PedidosMenuPlace.Tokenizer.class, PedidosNuevoPlace.Tokenizer.class,
				MedicamentosPlace.Tokenizer.class, MedicamentosMenuPlace.Tokenizer.class, PedidosMainPlace.Tokenizer.class,
				MedicamentosAñadirPlace.Tokenizer.class, VentaPlace.Tokenizer.class, SimularPlace.Tokenizer.class,
				SimulacionRunningPlace.Tokenizer.class, EscribirNoticiaPlace.Tokenizer.class, NoticiasPlace.Tokenizer.class})

public interface PlaceHistoryMapperImpl extends PlaceHistoryMapper{}