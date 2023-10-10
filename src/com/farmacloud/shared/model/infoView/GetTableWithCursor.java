package com.farmacloud.shared.model.infoView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.farmacloud.shared.model.abstracts.MedicamentoAbstracto;

public class GetTableWithCursor implements Serializable
{
	List<MedicamentoAbstracto> listaMedicamento = new ArrayList<MedicamentoAbstracto>();
	String cursor;
	int total;
	int firstIndex;
	
	public GetTableWithCursor(){
	}

	public List<MedicamentoAbstracto> getListaMedicamento() {
		return listaMedicamento;
	}

	public void setListaMedicamento(List<MedicamentoAbstracto> listaMedicamento) {
		this.listaMedicamento = listaMedicamento;
	}

	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}
}
