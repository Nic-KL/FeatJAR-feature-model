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
import de.featjar.base.shell.IShellCommand;
import de.featjar.base.shell.Shell;
import de.featjar.base.shell.ShellSession;
import de.featjar.formula.assignment.BooleanAssignment;
import de.featjar.formula.assignment.BooleanAssignmentGroups;
import de.featjar.formula.assignment.BooleanAssignmentList;
import de.featjar.formula.io.textual.BooleanAssignmentGroupsString;
import de.featjar.formula.io.textual.BooleanAssignmentListString;
import de.featjar.formula.io.textual.BooleanAssignmentString;
import de.featjar.formula.io.textual.StringFormulaFormat;
import de.featjar.formula.structure.IFormula;


public class StoreShellCommand implements IShellCommand {	
	
	public void execute(ShellSession session, List<String> cmdParams) {		
		final Path path = Paths.get("");
		
		if(cmdParams.isEmpty()) {
			cmdParams = Shell.readCommand("Enter the variable names you want to store:")
		    .map(c -> Arrays.stream(c.split("\\s+")).collect(Collectors.toList()))
		    .orElse(Collections.emptyList());
		}
		
        cmdParams.forEach(e -> {
            session.getElement(e).ifPresentOrElse(element -> {     	
            	try {            		
                    if (FeatureModel.class.isAssignableFrom(element.getClass())) {                	
                        IO.save((FeatureModel) element, path.resolve(e), new StringFeatureModelIFormat());
                        storingMessage(e);
                        
                    } else if (IFormula.class.isAssignableFrom(element.getClass())){          	
                        IO.save((IFormula) element, path.resolve(e), new StringFormulaFormat());
                        storingMessage(e);
                        
                    } else if (BooleanAssignment.class.isAssignableFrom(element.getClass())){
                        IO.save((BooleanAssignment) element, path.resolve(e), new BooleanAssignmentString());
                        storingMessage(e);      
                        
                    } else if (BooleanAssignmentList.class.isAssignableFrom(element.getClass())){
                        IO.save((BooleanAssignmentList) element, path.resolve(e), new BooleanAssignmentListString());
                        storingMessage(e);
                        
                    } else if (BooleanAssignmentGroups.class.isAssignableFrom(element.getClass())){
                        IO.save((BooleanAssignmentGroups) element, path.resolve(e), new BooleanAssignmentGroupsString());
                        storingMessage(e); 
                    } 
                    
            	} catch(IOException ioe) {
            		ioe.printStackTrace();
            	}   	
            }, () -> FeatJAR.log().error("could not find %s", e));
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
    	return Optional.of("stores seesion variables on your hard drive - <cmd> <varname> ...");
    }
}
