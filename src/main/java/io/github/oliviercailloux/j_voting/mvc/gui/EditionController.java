package io.github.oliviercailloux.j_voting.mvc.gui;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Composite;

import io.github.oliviercailloux.j_voting.Alternative;
import io.github.oliviercailloux.j_voting.Voter;
import io.github.oliviercailloux.j_voting.preferences.classes.MutableLinearPreferenceImpl;
import io.github.oliviercailloux.j_voting.preferences.interfaces.MutableLinearPreference;

public class EditionController {
    private EditionView editionView;
    private Controller controller;
    
    private List<Alternative> listalt = new ArrayList<>();
    private Voter v;

	public static EditionController create(EditionView editionView, Controller mainController) {
        return new EditionController(editionView, mainController);
    }

    private EditionController(EditionView editionView, Controller mainController) {
        this.editionView = editionView;
        this.controller = mainController;
        initEditionView();
        initViewEvents();
    }

    private void initEditionView() {
        String voterName = this.controller.getModel().getVoter().toString();
        editionView.displayVoters(voterName);

        Set<Alternative> a = this.controller.getModel().getAlternatives();
        editionView.displayAlternatives(a);
    }


    private void initViewEvents() {
    	
    	List<Alternative> alt = new ArrayList<>();
        List<Control> compositeChilds= new ArrayList<>(Arrays.asList(this.editionView.getComposite().getChildren()));

        for (Control ctr : compositeChilds) {
            this.dispatchEvents(ctr);
        }

        // Je pense qu'on peut pas se charger pour l'instant des logiques de rename de voter / alternative
        // On a aucun moyen de modifier le voter ou l'alternative de la MLP
        // --> Mais on peut se focus sur add et delete alternative

    }

    // todo rajouter une Enums pour les noms d'event des "case :"
    private void dispatchEvents(Control ctr) {
        switch (ctr.getData("event").toString()) {
            case "alternativeBox":
                this.handleAlternativeEvent(ctr);
                break;
            case "voterBox":
                this.handleVoterEvent(ctr);
                break;
            case "deleteAlternativeBtn":
                this.handleDeleteEvent(ctr);
        }

        // this is gonna grow
    }

    private void handleDeleteEvent(Control ctr) {
        // on fait un getparent du btn pour récup son group parent
        // ON fait un getTextFieldById avec ce group + id
        // on recup l'id de l'alterentave du text field -> on la cherche dans MLP
        // On la delete de mlp
        // on delete le text field (call une method de la vue avec text field en param).
    	
    	
    	
    }

    private void handleVoterEvent(Control ctr) {

    	//Evenenemnts focus in et focus out plus approprié
    	Text voter = (Text) ctr;
    	voter.addFocusListener(new FocusListener() {
            @Override
			public void focusGained(FocusEvent e) {
            	
            	//Vérification que la saisie soit que des nombres et soit pas vide
            }
            @Override
			public void focusLost(FocusEvent e) {
            	Text textInput = (Text) e.getSource();
              
                //je crée le voteur avec la saisie de l'utilisateur
                v = Voter.createVoter((Integer.parseInt(textInput.getText())));
                
                MutableLinearPreference m = MutableLinearPreferenceImpl.given(v,listalt);
                
                System.out.println(m);
            }
          });
    }

    private void handleAlternativeEvent(Control ctr) {
    	
        Text alt = (Text) ctr;
    	alt.addFocusListener(new FocusListener() {
            @Override
			public void focusGained(FocusEvent e) {
            	
            	//Vérification que la saisie soit que des nombres et sois pas vide
            }
            @Override
			public void focusLost(FocusEvent e) {
            	Text textInput = (Text) e.getSource();
              
            	Alternative a = Alternative.withId(Integer.parseInt(textInput.getText()));
                
            	if(!(listalt.contains(a))) {
            		listalt.add(a);
            	}
               
                MutableLinearPreference m = MutableLinearPreferenceImpl.given(v,listalt);
                
                System.out.println(m);
            }
          });
    }
    
    
    
    
    
 

    


}
