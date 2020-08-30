package io.github.oliviercailloux.j_voting.profile_gui;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.MutableLinearPreference;
import io.github.oliviercailloux.j_voting.preferences.classes.MutableLinearPreferenceImpl;

import java.util.ArrayList;
import java.util.List;

public class Controller {
	private MutableLinearPreference mlp;

	public MutableLinearPreference getModel() {
		return mlp;
	}

	public static Controller inst(MutableLinearPreference startModel) {
		return new Controller(startModel);
	}

	/**
	 * Creation of the LinearMutablePreference by default when launching the
	 * application
	 */
	public static Controller withDefaultModel() {
		Voter v1 = Voter.createVoter(1);
		Alternative a = Alternative.withId(2);
		Alternative a2 = Alternative.withId(3);
		Alternative a3 = Alternative.withId(4);
		Alternative a4 = Alternative.withId(5);
		List<Alternative> alt = new ArrayList<>();
		alt.add(a);
		alt.add(a2);
		alt.add(a3);
		alt.add(a4);
		MutableLinearPreference model = MutableLinearPreferenceImpl.given(v1, alt);

		return new Controller(model);
	}

	private Controller(MutableLinearPreference startModel) {
		this.mlp = startModel;
	}

	public EditionController buildEditionController(EditionView view) {
		return EditionController.create(view, this);
	}
}
