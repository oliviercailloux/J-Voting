package io.github.oliviercailloux.j_voting.profileManipulationGUI;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.events.*;
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
    }

    /**
     * Display the default edition view when user enters the gui
     */
    private void initEditionView() {
        String voterName = this.controller.getModel().getVoter().toString();
        editionView.displayVoters(voterName);

        Set<Alternative> a = this.controller.getModel().getAlternatives();
        editionView.displayAlternatives(a);
        editionView.attachAddAlternativeListener(this.buildAddAlternativeBehavior());
    }

    private SelectionAdapter buildAddAlternativeBehavior() {
        return new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Button btnData = (Button) e.getSource();
                handleAddAlternative(btnData);
            }
        };
    }

    private void handleAddAlternative(Button btn) {
        Text textField = this.editionView.getAddAlternativeControls().get(btn);
        String string = textField.getText();

        // Method found on stackoverflow to check whether the provided string is a number
        Matcher matcher = Pattern.compile("[0-9]*+$").matcher(string);
        if (!matcher.matches()) {
            textField.setText("Not a number");
            return;
        }

        Integer textFieldId = Integer.parseInt(textField.getText());
        Alternative newAlt = Alternative.withId(textFieldId);

        if(controller.getModel().getAlternatives().contains(newAlt)){
            textField.setText("Alternative already exists");
            return;
        }

        editionView.cleanAltContent();
        controller.getModel().addAlternative(newAlt);
        editionView.displayAlternatives(controller.getModel().getAlternatives());
        editionView.attachAddAlternativeListener(this.buildAddAlternativeBehavior());
    }

}
