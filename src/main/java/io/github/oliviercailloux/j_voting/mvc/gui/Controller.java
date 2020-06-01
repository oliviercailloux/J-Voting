package io.github.oliviercailloux.j_voting.mvc.gui;

import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;

public class Controller {
	private MutableLinearPreference mlp;
	
	public MutableLinearPreference getModel() {
		return mlp;
	}

	public static Controller inst(MutableLinearPreference startModel) {
		return new Controller(startModel);
	}
	
	private Controller(MutableLinearPreference startModel) {
		this.mlp = startModel;
	}

	public EditionController buildEditionController(EditionView view) {
		return EditionController.create(view, this);
	}



}
