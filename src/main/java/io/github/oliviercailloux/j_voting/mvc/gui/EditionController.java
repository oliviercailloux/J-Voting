package io.github.oliviercailloux.j_voting.mvc.gui;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.*;

import io.github.oliviercailloux.j_voting.Alternative;

public class EditionController {
    private EditionView editionView;
    private Controller controller;

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
        Text voter = (Text) ctr;
        voter.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                Text textInput = (Text) e.getSource();
                System.out.println(textInput.getText());
                // todo
            }
        });
    }

    private void handleAlternativeEvent(Control ctr) {
        Text alt = (Text) ctr;
        alt.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                Text textInput = (Text) e.getSource();
                System.out.println(textInput.getText());
                // todo
            }
        });
    }



}
