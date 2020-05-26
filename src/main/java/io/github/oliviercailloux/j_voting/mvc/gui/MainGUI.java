package io.github.oliviercailloux.j_voting.mvc.gui;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

public class MainGUI {

	public static void main(String[] args) {
		new MainGUI().displayGUI();
	}

	public void displayGUI() {
		Display display = new Display();
		Shell shell = new Shell(display, SWT.CLOSE | SWT.RESIZE);
		shell.setText("J-Voting");
		shell.setSize(400, 100);

		// Todo - centrer le shell et avoir une plus grande taille par défault
		//  j'ai pas trouvé comment faire.

		// Toutes les instances vont ici

		View view = View.create(shell);
		EditionView editionView = view.buildEditionView();
		VisualizationView visualizationView = view.buildVisualizationView();

		// Pierre tu peux instancier ici les 3 classes de controllers avec des factories + une mutable pref nommée model
		// Tu passes le model au big boss controller + tu fais un getter dessus
		// tu passes chaque view dans son controller respectif +
		// tu passes le big boss controller dans les enfants controllers (on fait comme ca pour l'instant mais ca va peut etre changer selon les com de cailloux).
		// Si t'es chaud tu peux essayer d'afficher une case avec le nom du voter du model dans l'onglet edition


		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}



}
