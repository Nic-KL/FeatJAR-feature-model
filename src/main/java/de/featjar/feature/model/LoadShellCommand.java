package de.featjar.feature.model;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import de.featjar.base.FeatJAR;
import de.featjar.base.data.Problem;
import de.featjar.base.data.Result;
import de.featjar.base.io.IO;
import de.featjar.base.io.format.AFormats;
import de.featjar.base.io.format.IFormat;
import de.featjar.base.log.Log.Verbosity;
import de.featjar.feature.model.io.FeatureModelFormats;
import de.featjar.formula.assignment.BooleanAssignment;
import de.featjar.formula.assignment.BooleanAssignmentGroups;
import de.featjar.formula.assignment.BooleanAssignmentList;
import de.featjar.formula.structure.IFormula;


public abstract class LoadShellCommand implements IShellCommand {
	
    @Override
    public Optional<String> getShortName() {
        return Optional.of("load");
    }
    
    public abstract Optional<String> getFormatName();
    
    public abstract Optional<String> getDefaultName();
    
    public String setPath(List<String> cmdParams) {
    	return cmdParams.size() > 1 ? cmdParams.get(1) : 
        	Shell.readCommand("Enter a path to load a " + getFormatName().orElse("")).orElse("");
    }
    
    public String setVarName(ShellSession session, List<String> cmdParams) {
    	return cmdParams.size() > 0 ? cmdParams.get(0) : 
    		Shell.readCommand("choose a name for your " + getFormatName().orElse("") + " or enter for default (" + getDefaultName().orElse("") + ")")
			.orElse(getDefaultName().orElse("") + (session.getSize() + 1));
    }
    
    public void handleDuplicate(String name, ShellSession session) {
    	FeatJAR.log().info("This session already contains a Variable with that name: \n");
    	session.printVariable(name);    	
    }
    
    public <T> void loadFormat(Result<T> result, String name, ShellSession session) {
    	
    	while(session.containsKey(name)) {    		
    		handleDuplicate(name, session);
    		String choice = Shell.readCommand("Overwrite " + name + " ? (y)es, (r)ename, (a)bort").orElse("").toLowerCase();
    		if(choice.equals("y")) {
    			new DeleteShellCommand().removeSingleEntry(session, name); 
    			break;
    		} else if(choice.equals("r")) {
    			name = Shell.readCommand("Enter another vaiable name: ").orElse("");
    		} else if(choice.equals("a")){
    			FeatJAR.log().info("Aborted\n");
    			return;     		
    		}
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
			FeatJAR.log().error("Could not load file %s for variable %s", result.getProblems().get(0).getMessage(), name);
			FeatJAR.log().problems(result.getProblems(), Verbosity.DEBUG);
		}
    }
    
	public <T> void parseArguments(ShellSession session, List<String> cmdParams, AFormats<T> format) {
		
		String name = setVarName(session, cmdParams);        
		String path = setPath(cmdParams);
        
        if(path.isBlank()) {
        	FeatJAR.log().info("No " + getFormatName().orElse("") + " specified"); // TODO maybe add problems ?
        	return;
        }
        
        loadFormat(IO.load(
				Paths.get(path), format), name, session);
	}
}
