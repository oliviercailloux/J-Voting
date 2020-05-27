package io.github.oliviercailloux.j_voting.mvc.gui;

import com.google.common.base.Preconditions;
import io.github.oliviercailloux.j_voting.Alternative;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EditionView {
    private TabFolder tabfolder;
    private TabItem editionTab;
    private Group group;
    
   
    public static EditionView create(TabFolder mainTabFolder) {
        return new EditionView(mainTabFolder);
    }

    private EditionView(TabFolder mainTabFolder) {
        this.tabfolder = mainTabFolder;
        this.group = new Group(tabfolder, SWT.NONE);
        initEditionTab();
    }

    // ca va évoluer ça (peut etre passer dans le controller)
    private void initEditionTab() {
        this.editionTab = new TabItem(this.tabfolder, SWT.NONE);
        editionTab.setText("Edition");
    }
    
    public void displayVoters(String voterName) {
    	Text voter = new Text(group, SWT.BORDER);
    	voter.setText(voterName);
    	voter.setBounds(10,10,200,25);
    	editionTab.setControl(group);
    }
    
    public void displayAlternatives(Set<Alternative> altSet) {
    
    	// J'ai du créer un group en attribut plutôt qu'un composite et 
    	// dans le constructeur je lui donne le tabfolder
    	int counterY = 50;
    	for(Alternative a : altSet) {
            Text alt = new Text(group, SWT.BORDER);
            alt.setText(a.toString());
            alt.setBounds(10,counterY,100,25);
            editionTab.setControl(group);
            counterY += 30;
        }


    }

	

}
