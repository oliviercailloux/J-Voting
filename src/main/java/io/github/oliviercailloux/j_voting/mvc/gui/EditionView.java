package io.github.oliviercailloux.j_voting.mvc.gui;

import com.google.common.base.Preconditions;
import io.github.oliviercailloux.j_voting.Alternative;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

import java.util.List;

public class EditionView {
    private TabFolder tabfolder;
    private TabItem editionTab;
   
    public static EditionView create(TabFolder mainTabFolder) {
        return new EditionView(mainTabFolder);
    }

    private EditionView(TabFolder mainTabFolder) {
        this.tabfolder = mainTabFolder;
        initEditionTab();
    }

    // ca va évoluer ça (peut etre passer dans le controller)
    private void initEditionTab() {
        this.editionTab = new TabItem(this.tabfolder, SWT.NONE);
        editionTab.setText("Edition");
    }
    
    public void displayVoters(String voterName) {
    	Composite voters = new Composite(this.tabfolder,SWT.NONE);
    	Text voter = new Text(voters, SWT.BORDER);
    	voter.setText(voterName);
    	voter.setBounds(10,10,200,25);
    	editionTab.setControl(voters);
    }
    
    public void displayAlternatives(List<Alternative> alternativeList) {
        //Preconditions.checkNotNull(alternativeList);
    	//for(Alternative alt : alternativeList) {
        // Work in progress
        //  !! J'ai l'impression que l'on peut pas brancher plusieurs composite sur le tabfolder :/
            Composite altContainer = new Composite(this.tabfolder, SWT.NONE);
            Text alt1 = new Text(altContainer, SWT.BORDER);
            alt1.setText("Alt 1");
            alt1.setBounds(10,80,100,25);

            Text alt2 = new Text(altContainer, SWT.BORDER);
            alt2.setText("Alt 2");
            alt2.setBounds(10,120,100,25);
            editionTab.setControl(altContainer);
       // }

    }

}
