package io.github.oliviercailloux.j_voting.mvc.gui;

import io.github.oliviercailloux.j_voting.Alternative;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

import java.util.Set;

public class EditionView {
    private TabFolder tabfolder;
    private TabItem editionTab;
    private Composite mainComposite;

    // Pierre je suis repassé à un composite principal mais ca revient au même que group
    // .
    public static EditionView create(TabFolder mainTabFolder) {
        return new EditionView(mainTabFolder);
    }

    private EditionView(TabFolder mainTabFolder) {
        this.tabfolder = mainTabFolder;
        this.mainComposite = new Composite(tabfolder, SWT.NONE);
        initEditionTab();
    }

    // Demander si ca passe dans le controller
    private void initEditionTab() {
        this.editionTab = new TabItem(this.tabfolder, SWT.NONE);
        editionTab.setText("Edition");
    }
    
    public void displayVoters(String voterName) {
    	Text voter = new Text(mainComposite, SWT.BORDER);
    	voter.setText(voterName);
    	voter.setBounds(10,10,200,25);
    	editionTab.setControl(mainComposite);
    }
    
    public void displayAlternatives(Set<Alternative> altSet) {
    	int counterY = 50;
    	for(Alternative a : altSet) {
            Text alt = new Text(mainComposite, SWT.BORDER);
            alt.setText(a.toString());
            alt.setBounds(10,counterY,100,25);
            editionTab.setControl(mainComposite);
            counterY += 30;
        }

    }

    public Composite getComposite() {
        return this.mainComposite;
    }

	

}
