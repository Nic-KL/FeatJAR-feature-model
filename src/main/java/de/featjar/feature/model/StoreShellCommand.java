package de.featjar.feature.model;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.featjar.base.FeatJAR;
import de.featjar.base.io.IO;
import de.featjar.formula.assignment.BooleanAssignment;
import de.featjar.formula.assignment.BooleanAssignmentGroups;
import de.featjar.formula.assignment.BooleanAssignmentList;
import de.featjar.formula.io.textual.BooleanAssignmentGroupsString;
import de.featjar.formula.io.textual.BooleanAssignmentListString;
import de.featjar.formula.io.textual.BooleanAssignmentString;
import de.featjar.formula.io.textual.StringFormulaFormat;
import de.featjar.formula.structure.IFormula;

// TODO nicht alle Vriablen sondern nur X gleiches für load, Format dass alles als String speichert, autocomplete und dann erst weiter \r dann geht er einen zurück in der konsole, globales abort

public class StoreShellCommand implements IShellCommand {	
	
	public StoreShellCommand() {
		super();
	}
	
	public void execute(ShellSession session, List<String> cmdParams) {		
		final Path path = Paths.get("");
		
		if(cmdParams.isEmpty()) {
			cmdParams = Shell.readCommand("Enter the variable names you want to store:")
		    .map(c -> Arrays.stream(c.split("\\s+")).collect(Collectors.toList()))
		    .orElse(Collections.emptyList());
		}
		
        cmdParams.forEach(e -> {
            session.getElement(e).ifPresent(element -> {
//                FeatJAR.log().info(FeatureModel.class + " " + element.getClass().toString() + " " + session.getType(e));
                if (FeatureModel.class.isAssignableFrom(element.getClass())) {                	
                	try {
                        IO.save((FeatureModel) element, path.resolve(e), new StringFeatureModelIFormat());
                        storingMessage(e);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }           	
//                    session.get(e, FeatureModel.class).ifPresent(v -> {
//                        try {
//                            IO.save(v, path.resolve(e), new StringFeatureModelIFormat());
//                            storingMessage(e);
//                        } catch (IOException ioe) {
//                            ioe.printStackTrace();
//                        }
//                    });
                } else if (IFormula.class.isAssignableFrom(element.getClass())){          	
                	try {
                        IO.save((IFormula) element, path.resolve(e), new StringFormulaFormat());
                        storingMessage(e);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }                	
                } else if (BooleanAssignment.class.isAssignableFrom(element.getClass())){
                	try {
                        IO.save((BooleanAssignment) element, path.resolve(e), new BooleanAssignmentString());
                        storingMessage(e);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }           
                } else if (BooleanAssignmentList.class.isAssignableFrom(element.getClass())){
                	try {
                        IO.save((BooleanAssignmentList) element, path.resolve(e), new BooleanAssignmentListString());
                        storingMessage(e);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }   
                } else if (BooleanAssignmentGroups.class.isAssignableFrom(element.getClass())){
                	try {
                        IO.save((BooleanAssignmentGroups) element, path.resolve(e), new BooleanAssignmentGroupsString());
                        storingMessage(e);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }   
                }
            });
        });

	}
	
	private void storingMessage(String element) {
        FeatJAR.log().info("Storing of " + element +" Successful");
	}
	
    @Override
    public Optional<String> getShortName() {
        return Optional.of("store");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("stores seesion objetcs on your hard drive - <cmd> <path> <format>");
    }
}
