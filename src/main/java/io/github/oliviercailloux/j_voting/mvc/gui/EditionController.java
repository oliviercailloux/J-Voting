package io.github.oliviercailloux.j_voting.mvc.gui;


import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    /**
     * Display the default edition view when user enters the gui
     */
    private void initEditionView() {
        String voterName = this.controller.getModel().getVoter().toString();
        editionView.displayVoters(voterName);

        Set<Alternative> a = this.controller.getModel().getAlternatives();
        editionView.displayAlternatives(a);
    }

    /**
     * Attach all the controls in the view to its corresponding event callback
     */
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
		default:
			break;
        }
    }

    /**
     * Callback for deleting the delete btn + its associated text field in the edition view
     * @param ctr the controlled clicked by the user
     */
    private void handleDeleteAlternative(Control ctr) {
    	Button deleteBtn = (Button) ctr;
        deleteBtn.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
            	Button btnData = (Button) e.getSource();
                Alternative alt = (Alternative) btnData.getData("alt");
                controller.getModel().removeAlternative(alt);
                List<Control> controlsToDelete = getControlsById("alt", alt.getId());

                for(Control c : controlsToDelete) {
                	editionView.removeControl(c);
                }
            }
        });
    	
    }

    /**
     * Callback for adding an alternative btn + its associated text field
     * @param ctr the add alternative btn clicked by the user
     */
    private void handleAddAlternative(Control ctr) {
        Button addBtn = (Button) ctr;

        addBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Integer ctrId = Integer.parseInt(ctr.getData("addAltID").toString());
                Text textField = (Text) getControlsById("addAltID", ctrId).get(0);

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
          	  	
          	  	cleanAltContent(controller.getModel().getAlternatives());
          	  	controller.getModel().addAlternative(newAlt);
          	  	editionView.displayAlternatives(controller.getModel().getAlternatives());
          	  	initViewEvents();
            }
        });
    }

    /**
     * Getter on the edition view in order to find a particular control by its ID
     * @param ctrName name given in the data object of the control to find
     * @param id id of the desired control
     * @return a list of controls matching with the given parameters
     */
    private List<Control> getControlsById(String ctrName, Integer id) {
        List <Control> altControls = new ArrayList<>(this.getControlsByKey(ctrName));
        List <Control> filteredCtr;

        filteredCtr = altControls.stream()
                .filter(ctr -> ctr.getData(ctrName).toString().equals(id.toString()))
                .collect(Collectors.toList());

        return filteredCtr;
    }

    /**
     * Getter on the edition view in order to find a particular control by its key (set in the data object of the control)
     * @param key corresponding to the control to find
     * @return a list of controls matching with the given parameter
     */
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

    /**
     * remove of the alternative related control in the view
     * @param altList set of alternatives corresponding to the alternative related controls to delete in the view
     */
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
