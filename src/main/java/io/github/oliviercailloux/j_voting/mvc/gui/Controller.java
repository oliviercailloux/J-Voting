package io.github.oliviercailloux.j_voting.mvc.gui;

import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;

public class Controller {
	private MutableLinearPreference mlp;
	
	public MutableLinearPreference getModel() {
		return mlp;
	}

	public static Controller inst(MutableLinearPreference m) {
		return new Controller(m);
	}
	
	private Controller(MutableLinearPreference m) {
		this.mlp = m;
	}
}
