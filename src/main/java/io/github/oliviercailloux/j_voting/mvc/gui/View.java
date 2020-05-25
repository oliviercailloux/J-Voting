package io.github.oliviercailloux.j_voting.mvc.gui;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

public class View {
    private Shell mainShell;
    private TabFolder tabfolder;

    public static View create(Shell mainShell) {
        return new View(mainShell);
    }

    private View(Shell shell) {
        this.mainShell = shell;
        initTabFolder();
    }

    private void initTabFolder() {
        this.tabfolder = new TabFolder(this.mainShell, SWT.NONE);
        tabfolder.setSize(200,200);
    }

    public EditionView buildEditionView() {
        return EditionView.create(this.tabfolder);
    }

    public VisualizationView buildVisualizationView() {
        return VisualizationView.create(this.tabfolder);
    }


}
