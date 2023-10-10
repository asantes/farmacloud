package com.farmacloud.client.presenter.anonymousUser;

import com.farmacloud.client.places.NoticiasPlace;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;

public class MyNavigator
{
	public class MyNavigatorHelper{
		
		private int indiceStart;
		private int indiceEnd;
		private int indiceActual;
		
		public MyNavigatorHelper(int _indiceStart, int _indiceEnd, int _indiceActual){
			
			indiceStart = _indiceStart;
			indiceEnd = _indiceEnd;
			indiceActual = _indiceActual;
		}

		public int getIndiceStart() {
			return indiceStart;
		}

		public void setIndiceStart(int indiceStart) {
			this.indiceStart = indiceStart;
		}

		public int getIndiceEnd() {
			return indiceEnd;
		}

		public void setIndiceEnd(int indiceEnd) {
			this.indiceEnd = indiceEnd;
		}

		public int getIndiceActual() {
			return indiceActual;
		}

		public void setIndiceActual(int indiceActual) {
			this.indiceActual = indiceActual;
		}
	}
	
	private final int NUM_ELEMENTOS_PAGINA;
	private final int NUM_INDICES;

	public MyNavigator(int numIndices, int numElementosPagina)
	{
		NUM_ELEMENTOS_PAGINA = numElementosPagina;
		NUM_INDICES = numIndices;
	}
	
	public MyNavigatorHelper refresh(int indiceActual, int elementosTotales, int elementosRecibidos)
	{
		MyNavigatorHelper mnh = null;
		
		if(indiceActual>=1 && elementosTotales>=0 && elementosRecibidos>=0)
		{
			int indice = indiceActual;
			int indiceStart = 1;
			int indiceEnd = NUM_INDICES;
			int paginasTotales;
			
			/* Calculamos el numero de paginas totales necesarias para mostrar todos los elementos
			 * persistidos en le BBDD
			 */
			int resto = elementosTotales%NUM_ELEMENTOS_PAGINA;
			if(resto==0)
				paginasTotales = (elementosTotales/NUM_ELEMENTOS_PAGINA);
			else paginasTotales = (elementosTotales/NUM_ELEMENTOS_PAGINA)+1;
			
			
			/* Suficientes elementos para el indice actual */
			if(elementosRecibidos==NUM_ELEMENTOS_PAGINA)
			{
				if(elementosTotales==indiceActual*NUM_ELEMENTOS_PAGINA)
				{
					/* Hay elementos disponibles para mostrar la pagina solicitada al completo, pero
					 * no se dispone de elementos para mostar mas paginas. Esto implica que la pagina
					 * actual sera el ultimo de los indices
					 */
					indiceStart = calcularIndiceStart(indiceActual);
					indiceEnd = indiceActual;
				}
				else
				{
					/* Hay elementos para mostrar la pagina actual completa y mas */
					indiceStart = calcularIndiceStart(indiceActual);
					indiceEnd = calcularIndiceEnd(indiceActual);
					
					if(paginasTotales<indiceEnd)
						indiceEnd = paginasTotales;
					
					System.out.println("Paginas totales "+paginasTotales);
				}
			} 
			else if(elementosRecibidos<NUM_ELEMENTOS_PAGINA)
				{
					/* No tenemos elementos para mostrar la pagina actual completa */
					indiceStart = calcularIndiceStart(indiceActual);
					indiceEnd = indiceActual;
				}
			
			mnh = new MyNavigatorHelper(indiceStart, indiceEnd, indiceActual);
		}
		
		return mnh;
	}
		
			
    int calcularIndiceStart(int indiceActual)
    {
    	int indiceStart;
    	
		if(indiceActual<=NUM_INDICES/2)
		{
			indiceStart = 1;
		}
		else
		{
			indiceStart = indiceActual - (NUM_INDICES/2);
		}
		
    	return indiceStart;
    }
    
    int calcularIndiceEnd(int indiceActual)
    {
    	int indiceEnd;
    	
		if(indiceActual<=NUM_INDICES/2)
		{
			indiceEnd = NUM_INDICES;
		}
		else
		{
			indiceEnd = indiceActual + (NUM_INDICES/2);
		}
		
		return indiceEnd;
    }
}