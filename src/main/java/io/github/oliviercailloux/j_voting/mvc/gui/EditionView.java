package io.github.oliviercailloux.j_voting.mvc.gui;

import io.github.oliviercailloux.j_voting.Alternative;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class EditionView {
    private TabFolder tabfolder;
    private TabItem editionTab;
    private Composite mainComposite;
    private List<Text> listText;
    private List<Button> listButton;
    
    public static EditionView create(TabFolder mainTabFolder) {
        return new EditionView(mainTabFolder);
    }

    private EditionView(TabFolder mainTabFolder) {
        this.tabfolder = mainTabFolder;
        this.mainComposite = new Composite(tabfolder, SWT.NONE);
        this.listText = new ArrayList<>();
        this.listButton = new ArrayList<>();
        initEditionTab();
    }

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

    public void displayAlternatives(Set<Alternative> altSet) {
    	int counterY = 50;
    	int lasty = 0;

    	for(Alternative a : altSet) {
    		
    		Text alt = new Text(mainComposite, SWT.BORDER);
            alt.setData("event", "alternativeBox");
            alt.setData("alt", a);
            alt.setText(a.toString());
            alt.setBounds(10,counterY,100,25);
            listText.add(alt);
  
            Button btn = new Button(mainComposite, SWT.NONE);
            btn.setBounds(120, counterY,100,25);
            btn.setText("Delete");
            btn.setData("event", "deleteAlternativeBtn");
            btn.setData("alt", a);
            counterY += 30;
            listButton.add(btn);
            
            lasty = btn.getBounds().y;      
        }
    
        editionTab.setControl(mainComposite);
        displayAddAlternatives(lasty, altSet.size()+1);
    }
    
    public void displayAddAlternatives(int positionY, int controlId) {
    	Button btn = new Button(mainComposite, SWT.NONE);
        btn.setBounds(120, positionY+30,100,25);
        btn.setText("Add Alternative");
        btn.setData("event", "addAlternativeBtn");
        btn.setData("addAltID", controlId);

        Text newAlt = new Text(mainComposite, SWT.BORDER);
        newAlt.setBounds(10, positionY + 30,100,25);
        editionTab.setControl(mainComposite);
        newAlt.setData("addAltID", controlId);
    }
    
    public void positionDeleting(int y) {
    	
    	int count = 0;
    	for(int i=0 ; i<listText.size(); i++) {
    		
    		if(listText.get(i).getBounds().y > y) {
    		
    			listText.get(i).setLocation(10, y - 1 + count);
    			listButton.get(i).setLocation(120, y - 1 + count);
    			count = count + 30;	
    		}
    	}
    	
    }

    public Composite getComposite() {
        return this.mainComposite;
    }

    public void removeControl(Control ctr) {
        ctr.dispose();
    }

	

}
