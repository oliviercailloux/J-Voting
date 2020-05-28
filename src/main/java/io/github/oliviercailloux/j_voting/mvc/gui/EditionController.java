package io.github.oliviercailloux.j_voting.mvc.gui;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.eclipse.swt.widgets.*;

import io.github.oliviercailloux.j_voting.Alternative;

import javax.naming.ldap.Control;

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
        List test = Arrays.asList(this.editionView.getComposite().getChildren());
        // Je dois continuer ce test
        // On recupère tous les élément du composite, faut trouver un moyen de les distinguer
        // Des qu'on est capable de les distinguer on peut dispatcher  les events correspondants.
    }




}
