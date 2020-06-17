package io.github.oliviercailloux.j_voting.mvc.gui;

import io.github.oliviercailloux.j_voting.Alternative;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import java.util.Set;

public class EditionView {
    private TabFolder tabfolder;
    private TabItem editionTab;
    private Composite mainComposite;
    private GridLayout gridLayout;

    /**
     * Factory method to create the edition window 
     * @param mainTabFolder the tab folder that holds the view
     * @return a new window
     */
    public static EditionView create(TabFolder mainTabFolder) {
        return new EditionView(mainTabFolder);
    }

    private EditionView(TabFolder mainTabFolder) {
        this.tabfolder = mainTabFolder;
        this.mainComposite = new Composite(tabfolder, SWT.NONE);

        this.gridLayout = new GridLayout(2, false);
        this.mainComposite.setLayout(gridLayout);

        initEditionTab();
    }

    /**
     * Initialization of the editing tab window
     */
    private void initEditionTab() {
        this.editionTab = new TabItem(this.tabfolder, SWT.NONE);
        editionTab.setText("Edition");
    }

    // Changer constructeur de gridData

    /**
     * Creation and display the text field with the voter
     * 
     * @param voterName for the voter's name in the Mutable Linear Preference.
     */
    public void displayVoters(String voterName) {
        Text voter = new Text(mainComposite, SWT.BORDER);
        voter.setData("event", "voterBox");
        voter.setText(voterName);

        GridData gridData = new GridData();
        // This text field must take two spaces
        gridData.horizontalSpan = 2;
        gridData.horizontalAlignment = GridData.FILL;
        voter.setLayoutData(gridData);
    }

    /**
     * Creation and display of text fields with alternatives. 
     * Creating and displaying buttons for deleting an alternative.
     * 
     * @param altSet for the list of alternatives to display in the Mutable Linear Preference..
     */
    public void displayAlternatives(Set<Alternative> altSet) {
        for(Alternative a : altSet) {
            Text alt = new Text(mainComposite, SWT.BORDER);
            alt.setData("event", "alternativeBox");
            alt.setData("alt", a);
            alt.setText(a.toString());
            GridData data = new GridData();
            data.widthHint = 120;
            data.horizontalAlignment = GridData.BEGINNING;
            alt.setLayoutData(data);

            Button btn = new Button(mainComposite, SWT.NONE);
            btn.setText("Delete");
            btn.setData("event", "deleteAlternativeBtn");
            btn.setData("alt", a);
        }

        displayAddAlternatives( altSet.size()+1);
        mainComposite.layout(true);
    }
    
    /**
     * Creation and display of the text field of the alternative to be added. 
     * Creation and display of the add button.
     *
     * @param controlId for the alternative id.
     */
    public void displayAddAlternatives(int controlId) {
        Text newAlt = new Text(mainComposite, SWT.BORDER);
        editionTab.setControl(mainComposite);
        newAlt.setData("addAltID", controlId);

        GridData data = new GridData();
        data.widthHint = 120;
        newAlt.setLayoutData(data);

        Button btn = new Button(mainComposite, SWT.NONE);
        btn.setText("Add Alternative");
        btn.setData("event", "addAlternativeBtn");
        btn.setData("addAltID", controlId);
    }
    
    /**
     * Return the composite of the tab.
     * @return mainComposite
     */
    public Composite getComposite() {
        return this.mainComposite;
    }
    
    /**
     * Delete window control
     * @param ctr the control to be removed
     */
    public void removeControl(Control ctr) {
        ctr.getParent().layout();
        ctr.dispose();
    }
}
