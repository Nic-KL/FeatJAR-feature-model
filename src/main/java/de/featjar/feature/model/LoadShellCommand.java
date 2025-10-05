package de.featjar.feature.model;

import java.nio.file.Path;
import java.util.Optional;

import de.featjar.base.FeatJAR;
import de.featjar.base.data.Result;
import de.featjar.base.io.IO;
import de.featjar.base.io.format.AFormats;
import de.featjar.base.log.Log.Verbosity;
import de.featjar.formula.assignment.BooleanAssignment;
import de.featjar.formula.assignment.BooleanAssignmentGroups;
import de.featjar.formula.assignment.BooleanAssignmentList;
import de.featjar.formula.structure.IFormula;


public abstract class LoadShellCommand implements IShellCommand {
	
	public static <T> Result<T> loadFormat(Path path, AFormats<T> format) {		
		return IO.load(path, format);//.orElseLog(Verbosity.WARNING);
	}
	
    @Override
    public Optional<String> getShortName() {
        return Optional.of("load");
    }
    
    public Optional<String> setName(String model, String defaultName) {    	
    	return Shell.readCommand("choose a name for your " + model + " or enter for default (" + defaultName + ")");
    }
    
    public abstract Optional<String> getModelName();
    
    public abstract Optional<String> getDefaultName();
    
    public void handleDuplicate(String name, ShellSession session) {
    	FeatJAR.log().info("This session already contains a Variable with that name: \n");
    	session.printVariable(name);
    	
    }
    
    public <T> void saveModel(Result<T> result, String name, ShellSession session) {
    	
    	if(session.containsKey(name)) {    		
    		handleDuplicate(name, session);
    		
    		//TODO question to replace already present entries
    		
    		//TODO back to execute, or delete and proceed
    		
    		
    		return;
    	}
    	
    	
		if(result.isPresent()) {			
			if (FeatureModel.class.isAssignableFrom(result.get().getClass())) {       
				session.put(name, (FeatureModel) result.get(), FeatureModel.class);
				
			} else if (IFormula.class.isAssignableFrom(result.get().getClass())){ 
				session.put(name, (IFormula) result.get(), IFormula.class);
				
			} else if (BooleanAssignment.class.isAssignableFrom(result.get().getClass())){ 
				session.put(name, ((BooleanAssignmentGroups) result.get()).getFirstGroup().getFirst(), BooleanAssignment.class);
				
			} else if (BooleanAssignmentList.class.isAssignableFrom(result.get().getClass())){
				session.put(name, ((BooleanAssignmentGroups) result.get()).getFirstGroup(), BooleanAssignmentList.class);
				
			} else if (BooleanAssignmentGroups.class.isAssignableFrom(result.get().getClass())){
				session.put(name, (BooleanAssignmentGroups) result.get(), BooleanAssignmentGroups.class);
				
			}
			FeatJAR.log().info(name + " successfully loaded\n");		
		} else {
			FeatJAR.log().problems(result.getProblems(), Verbosity.ERROR);
		}
    }
}
