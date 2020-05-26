package io.github.oliviercailloux.j_voting.mvc.gui;

import org.eclipse.swt.widgets.*;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.classes.MutableLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;

import java.util.ArrayList;
import java.util.HashSet;
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

		Rectangle screenSize = display.getPrimaryMonitor().getBounds();
		shell.setLocation((screenSize.width - shell.getBounds().width) / 2, 
				(screenSize.height - shell.getBounds().height) / 2);

		// Toutes les instances vont ici
		View view = View.create(shell);
		EditionView editionView = view.buildEditionView();
		VisualizationView visualizationView = view.buildVisualizationView();

		// Pierre tu peux instancier ici les 3 classes de controllers avec des factories + une mutable pref nommée model
		//Instancie une MLP
		Voter v1 = Voter.createVoter(1);
		Alternative a = Alternative.withId(2);
		Alternative a2 = Alternative.withId(3);
		List<Alternative> alt = new ArrayList<>();
		alt.add(a);
		alt.add(a2);
		MutableLinearPreference model = MutableLinearPreferenceImpl.given(v1,alt);
		
		// Tu passes le model au big boss controller + tu fais un getter dessus
		// j'ai du mettre controller en static :/
		Controller c = Controller.inst(model);
		
		// tu passes chaque view dans son controller respectif +
		// tu passes le big boss controller dans les enfants controllers (on fait comme ca pour l'instant mais ca va peut etre changer selon les com de cailloux).
		// Si t'es chaud tu peux essayer d'afficher une case avec le nom du voter du model dans l'onglet edition
		
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}



}
