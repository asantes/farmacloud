package com.farmacloud.shared.model.infoView;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class MedicamentoSuggestion implements IsSerializable,Suggestion
{
	String displayName;
	String replacementName;
	
	@Override
	public String getDisplayString() {
		return this.displayName.toLowerCase();
	}

	@Override
	public String getReplacementString() {
		return this.replacementName.toLowerCase();
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setReplacementName(String replacementName) {
		this.replacementName = replacementName;
	}
}
