package io.github.oliviercailloux.j_voting.mvc.gui;

import org.eclipse.swt.widgets.*;

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

		// Controllers
		Controller controller = Controller.withDefaultModel();
		EditionController editionController = controller.buildEditionController(editionView);

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
