package io.github.oliviercailloux.j_voting.mvc.gui;


import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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
        initViewEvents();
    }

    private void initEditionView() {
        String voterName = this.controller.getModel().getVoter().toString();
        editionView.displayVoters(voterName);

        Set<Alternative> a = this.controller.getModel().getAlternatives();
        editionView.displayAlternatives(a);
    }

    private void initViewEvents() {
        List<Control> compositeChilds= new ArrayList<>(Arrays.asList(this.editionView.getComposite().getChildren()));

        for (Control ctr : compositeChilds) {
            Optional<Object> eventName = Optional.ofNullable(ctr.getData("event"));
            if(eventName.isPresent()) {
                this.dispatchEvents(ctr);
            }

        }
    }

    private void dispatchEvents(Control ctr) {
        switch (ctr.getData("event").toString()) {
            case "deleteAlternativeBtn":
                this.handleDeleteAlternative(ctr);
                break;
            case "addAlternativeBtn":
            	this.handleAddAlternative(ctr);
            	break;
        }

    }

    private void handleDeleteAlternative(Control ctr) {
    	Button deleteBtn = (Button) ctr;
        deleteBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                Button btnData = (Button) e.getSource();
                Alternative alt = (Alternative) btnData.getData("alt");
                controller.getModel().removeAlternative(alt);
                List<Control> controlsToDelete = getControlsById("alt", alt.getId());

                editionView.positionDeleting(ctr.getBounds().y);
                
               for(Control ctr : controlsToDelete) {
                   editionView.removeControl(ctr);
               }
            }
        });
    	
    }

    private void handleAddAlternative(Control ctr) {
        Button addBtn = (Button) ctr;

        addBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Integer ctrId = Integer.parseInt(ctr.getData("addAltID").toString());
                Text textField = (Text) getControlsById("addAltID", ctrId).get(1);
                try {
                    Integer textFieldId = Integer.parseInt(textField.getText());
                    Alternative newAlt = Alternative.withId(textFieldId);
                    cleanAltContent(controller.getModel().getAlternatives());
                    controller.getModel().addAlternative(newAlt);
                    editionView.displayAlternatives(controller.getModel().getAlternatives());
                    initViewEvents();
                }
                catch (NumberFormatException err) {
                    textField.setText("Not a number");
                }
            }
        });
    }

    private List<Control> getControlsById(String ctrName, Integer id) {
        List <Control> altControls = new ArrayList<>(this.getControlsByKey(ctrName));
        List <Control> filteredCtr;

        filteredCtr = altControls.stream()
                .filter(ctr -> ctr.getData(ctrName).toString().equals(id.toString()))
                .collect(Collectors.toList());

        return filteredCtr;
    }

    private List<Control> getControlsByKey(String key) {
        List<Control> compositeChilds = new ArrayList<>(Arrays.asList(this.editionView.getComposite().getChildren()));
        List <Control> ctr = new ArrayList<>();

        for(Control control : compositeChilds) {
            Optional<Object> ctrData = Optional.ofNullable(control.getData(key));
            if (ctrData.isPresent()) {
                ctr.add(control);
            }
        }
        return ctr;
    }

    private void cleanAltContent(Set<Alternative> altList) {
        for (Alternative alt : altList) {
            List<Control> controlsToClean = getControlsById("alt", alt.getId());
            for(Control ctr : controlsToClean) {
                this.editionView.removeControl(ctr);
            }
        }
        List<Control> addAltControlsToClean = getControlsByKey("addAltID");
        for(Control ctr : addAltControlsToClean) {
            this.editionView.removeControl(ctr);
        }
    }

}
