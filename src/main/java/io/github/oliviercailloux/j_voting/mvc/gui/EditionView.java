package io.github.oliviercailloux.j_voting.mvc.gui;

import io.github.oliviercailloux.j_voting.Alternative;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

import java.util.Set;

public class EditionView {
    private TabFolder tabfolder;
    private TabItem editionTab;
    private Composite mainComposite;


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
        voter.setData("event", "voterBox");
    	voter.setText(voterName);
    	voter.setBounds(10,10,200,25);
        editionTab.setControl(mainComposite);
    }

    // todo : revoir le layout avec les class layout de SWT c'est mieux que des set bounds avec X, Y je pense
    public void displayAlternatives(Set<Alternative> altSet) {
    	int counterY = 50;
    	int id = 0;
    	// j'ai trouvé que l'ID comme solution pour l'instant pour lier un btn avec son text field
        // on peut faire un group avec Voter + ses alternatives et on le branche sur le le mainComposite.
        // je vais continuer ca
    	for(Alternative a : altSet) {
            Text alt = new Text(mainComposite, SWT.BORDER);
            alt.setData("event", "alternativeBox");
            alt.setData("id", id);
            alt.setText(a.toString());
            alt.setBounds(10,counterY,100,25);

            Button btn = new Button(mainComposite, SWT.NONE);
            btn.setBounds(120, counterY,100,25);
            btn.setText("Delete");
            btn.setData("event", "deleteAlternativeBtn");
            btn.setData("id", id);
            id++;
            counterY += 30;
        }
        editionTab.setControl(mainComposite);
    }

    // work in progress
    public void getTextFieldById(Group gr, String id) {

    }

    public Composite getComposite() {
        return this.mainComposite;
    }

	

}
