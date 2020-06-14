package io.github.oliviercailloux.j_voting.mvc.gui;

import io.github.oliviercailloux.j_voting.Alternative;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import java.util.Set;

public class EditionView {
    private TabFolder tabfolder;
    private TabItem editionTab;
    private Composite mainComposite;
    
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
        voter.setData("event", "voterBox");
    	voter.setText(voterName);
    	voter.setBounds(10,10,200,25);
        editionTab.setControl(mainComposite);
    }

    /**
     * Creation and display of text fields with alternatives. 
     * Creating and displaying buttons for deleting an alternative.
     * 
     * @param altSet for the list of alternatives to display in the Mutable Linear Preference..
     */
    public void displayAlternatives(Set<Alternative> altSet) {
    	int counterY = 50;
    	int lasty = 0;

    	for(Alternative a : altSet) {
    		
    		Text alt = new Text(mainComposite, SWT.BORDER);
            alt.setData("event", "alternativeBox");
            alt.setData("alt", a);
            alt.setText(a.toString());
            alt.setBounds(10,counterY,150,25);
  
            Button btn = new Button(mainComposite, SWT.NONE);
            btn.setBounds(170, counterY,100,25);
            btn.setText("Delete");
            btn.setData("event", "deleteAlternativeBtn");
            btn.setData("alt", a);
            counterY += 30;
            
            lasty = btn.getBounds().y;      
        }
    
        editionTab.setControl(mainComposite);
        displayAddAlternatives(lasty, altSet.size()+1);
    }
    
    /**
     * Creation and display of the text field of the alternative to be added. 
     * Creation and display of the add button.
     * 
     * @param positionY for the position of the last alternative of the MutableLinearPreference
     * @param controlId for the alternative id.
     */
    public void displayAddAlternatives(int positionY, int controlId) {
    	Button btn = new Button(mainComposite, SWT.NONE);
        btn.setBounds(170, positionY+30,100,25);
        btn.setText("Add Alternative");
        btn.setData("event", "addAlternativeBtn");
        btn.setData("addAltID", controlId);

        Text newAlt = new Text(mainComposite, SWT.BORDER);
        newAlt.setBounds(10, positionY + 30,150,25);
        editionTab.setControl(mainComposite);
        newAlt.setData("addAltID", controlId);
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
        ctr.dispose();
    }

}
