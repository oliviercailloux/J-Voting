package io.github.oliviercailloux.j_voting.mvc.gui;

import org.eclipse.swt.widgets.*;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.classes.MutableLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Rectangle;

public class MainGUI {

	public static void main(String[] args) {
		new MainGUI().displayGUI();
	}

	public void displayGUI() {
		Display display = new Display();
		Shell shell = new Shell(display, SWT.CLOSE | SWT.RESIZE);
		shell.setText("J-Voting");
		shell.setSize(600, 400);
		centerOnScreen(display, shell);

		// Views
		View view = View.create(shell);
		EditionView editionView = view.buildEditionView();
		VisualizationView visualizationView = view.buildVisualizationView();

		// Model
		Voter v1 = Voter.createVoter(1);
		Alternative a = Alternative.withId(2);
		Alternative a2 = Alternative.withId(3);
		List<Alternative> alt = new ArrayList<>();
		alt.add(a);
		alt.add(a2);
		MutableLinearPreference model = MutableLinearPreferenceImpl.given(v1,alt);


		// Controllers
		// j'ai du mettre controller en static :/ Ecoute je vois pas de pb !
		Controller controller = Controller.inst(model);
		EditionController editionController = EditionController.create(editionView, controller);





		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}

	private void centerOnScreen(Display display, Shell shell) {
		Rectangle screenSize = display.getPrimaryMonitor().getBounds();
		shell.setLocation((screenSize.width - shell.getBounds().width) / 2,
				(screenSize.height - shell.getBounds().height) / 2);
	}



}
