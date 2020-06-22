package io.github.oliviercailloux.j_voting.profileManipulationGUI;

import io.github.oliviercailloux.j_voting.Alternative;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class EditionView {
    private TabFolder tabfolder;
    private TabItem editionTab;
    private Composite mainComposite;
    private GridLayout gridLayout;
    private Map<Button, Text> addAlternativeControls;
    private Map<Alternative, Text> alternativeControls;

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
        this.addAlternativeControls = new LinkedHashMap<>();
        this.alternativeControls = new LinkedHashMap<>();

        this.gridLayout = new GridLayout(1, false);
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

    /**
     * Creation and display the text field with the voter
     * 
     * @param voterName for the voter's name in the Mutable Linear Preference.
     */
    public void displayVoters(String voterName) {
        Text voter = new Text(mainComposite, SWT.BORDER);
        voter.setText(voterName);

        GridData gridData = new GridData(GridData.FILL, GridData.VERTICAL_ALIGN_BEGINNING,
                false, false);
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
            alt.setText(a.toString());

            GridData data = new GridData(120, 15);
            data.horizontalAlignment = GridData.BEGINNING;
            alt.setLayoutData(data);
            this.alternativeControls.put(a, alt);
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

        GridData data = new GridData(120, 15);
        newAlt.setLayoutData(data);

        Button btn = new Button(mainComposite, SWT.NONE);
        btn.setText("Add Alternative");
        btn.setData("event", "addAlternativeBtn");
        btn.setData("addAltID", controlId);
        this.addAlternativeControls.put(btn, newAlt);
    }

    public void attachAddAlternativeListener(SelectionAdapter behavior) {
        for(Button btn : this.addAlternativeControls.keySet()) {
            btn.addSelectionListener(behavior);
        }
    }

    public Map<Button, Text> getAddAlternativeControls() {
        return addAlternativeControls;
    }

    public void cleanAltContent() {
        for(Text ctr : alternativeControls.values()) {
            ctr.dispose();
        }
        for (Button btn : addAlternativeControls.keySet()) {
            btn.dispose();
        }
        for (Text text : addAlternativeControls.values()) {
            text.dispose();
        }
        this.alternativeControls.clear();
        this.addAlternativeControls.clear();
    }
}
