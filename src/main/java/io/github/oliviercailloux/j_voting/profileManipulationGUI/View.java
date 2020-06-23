package io.github.oliviercailloux.j_voting.profileManipulationGUI;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

public class View {
	private Shell mainShell;
	private TabFolder tabfolder;

	/**
	 * Factory method to create the main Shell
	 * 
	 * @param mainShell
	 * @return a new shell
	 */
	public static View create(Shell mainShell) {
		return new View(mainShell);
	}

	private View(Shell shell) {
		this.mainShell = shell;
		initTabFolder();
	}

	/**
	 * Initialization of the window
	 */
	private void initTabFolder() {
		this.tabfolder = new TabFolder(this.mainShell, SWT.NONE);
		tabfolder.setSize(600, 400);
	}

	/**
	 * Creating the editing tab
	 */
	public EditionView buildEditionView() {
		return EditionView.create(this.tabfolder);
	}

	/**
	 * Creating the viewing tab
	 */
	public VisualizationView buildVisualizationView() {
		return VisualizationView.create(this.tabfolder);
	}

}
