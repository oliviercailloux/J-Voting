package io.github.oliviercailloux.j_voting.mvc.gui;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.*;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.classes.MutableLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;

import javax.naming.ldap.Control;

public class EditionController {
    private EditionView editionView;
    private Controller controller;
    public static int globalInt = 0;

    public static EditionController create(EditionView editionView, Controller mainController) {
        return new EditionController(editionView, mainController);
    }

    private EditionController(EditionView editionView, Controller mainController) {
        this.editionView = editionView;
        this.controller = mainController;
        initEditionView();
    }

    // A voir jsp c'est ok comme logique
    private void initEditionView() {
        String voterName = this.controller.getModel().getVoter().toString();
        editionView.displayVoters(voterName);

        Set<Alternative> a = this.controller.getModel().getAlternatives();
        editionView.displayAlternatives(a);
        initEventTest();
    }


    private void initEventTest() {
    	
    	List<Alternative> alt = new ArrayList<>();
        List composite = Arrays.asList(this.editionView.getComposite().getChildren());
      
        
        Text voter = (Text) composite.get(0);
        voter.addModifyListener(new ModifyListener() {
            @Override
			public void modifyText(ModifyEvent e) {
            	
            	
            	globalInt = Integer.parseInt(((Text)e.widget).getText());
            	
            }
        });
        
        Voter v1 = Voter.createVoter(globalInt);
        
	    Text t1 = (Text) composite.get(1);
	    t1.addModifyListener(new ModifyListener() {
            @Override
			public void modifyText(ModifyEvent e) {
            	
            	
            	int a1 = Integer.parseInt(((Text)e.widget).getText());
            	Alternative alternative1 = Alternative.withId(a1);
            	alt.add(alternative1);
            	MutableLinearPreference model = MutableLinearPreferenceImpl.given(v1,alt);
            	System.out.println(model);
            }
        });
	    
		
        // Je dois continuer ce test
        // On recupère tous les élément du composite, faut trouver un moyen de les distinguer
        // Des qu'on est capable de les distinguer on peut dispatcher  les events correspondants.
    }




}
