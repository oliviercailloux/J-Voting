package io.github.oliviercailloux.j_voting.mvc.gui;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
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

		// Todo - centrer le shell et avoir une plus grande taille par défautl j'ai pas trouvé comment faire.

		// Toutes les instances vont ici






		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}



}
