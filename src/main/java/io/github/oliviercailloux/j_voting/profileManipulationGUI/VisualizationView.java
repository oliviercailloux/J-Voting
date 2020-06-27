package io.github.oliviercailloux.j_voting.profileManipulationGUI;

import org.eclipse.swt.widgets.*;

import com.google.common.base.Preconditions;

import org.eclipse.swt.*;

public class VisualizationView {
	private TabFolder tabfolder;
	private TabItem visualizationTab;

	public static VisualizationView create(TabFolder mainTabFolder) {
		return new VisualizationView(mainTabFolder);
	}

	private VisualizationView(TabFolder mainTabFolder) {
		Preconditions.checkNotNull(mainTabFolder);
		this.tabfolder = mainTabFolder;
		initVisualizationTab();
	}

	private void initVisualizationTab() {
		this.visualizationTab = new TabItem(this.tabfolder, SWT.NONE);
		visualizationTab.setText("Visualization");
	}

}
