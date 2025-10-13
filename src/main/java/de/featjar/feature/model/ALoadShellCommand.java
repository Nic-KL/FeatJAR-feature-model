package de.featjar.feature.model;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import de.featjar.base.FeatJAR;
import de.featjar.base.data.Problem;
import de.featjar.base.data.Result;
import de.featjar.base.io.IO;
import de.featjar.base.io.format.AFormats;
import de.featjar.base.log.Log.Verbosity;
import de.featjar.base.shell.IShellCommand;
import de.featjar.base.shell.Shell;
import de.featjar.base.shell.ShellSession;
import de.featjar.formula.assignment.BooleanAssignment;
import de.featjar.formula.assignment.BooleanAssignmentGroups;
import de.featjar.formula.assignment.BooleanAssignmentList;
import de.featjar.formula.structure.IFormula;


public abstract class ALoadShellCommand implements IShellCommand {
	    
    public abstract Optional<String> getFormatName();
    
    public abstract Optional<String> getDefaultName();
    
    public String setPath(List<String> cmdParams) {
    	return cmdParams.size() > 1 ? cmdParams.get(1) : 
        	Shell.readCommand("Enter a path to load a " + getFormatName().orElse("") + " or leave blank to abort").orElse("");
    }
    
    public String setVarName(ShellSession session, List<String> cmdParams) {
    	return cmdParams.size() > 0 ? cmdParams.get(0) : 
    		Shell.readCommand("choose a name for your " 
    		+ getFormatName().orElse("") 
    		+ " or enter for default (" 
    		+ getDefaultName().orElse("") + (session.getSize()+1) 
    		+ ")")
			.orElse(getDefaultName().orElse("") + (session.getSize() + 1));
    }
    
	public void removeSingleEntry(ShellSession session, String name) {
		session.remove(name).ifPresentOrElse(e ->  FeatJAR.log().message("Removing of " + e + " successful"),
				() -> FeatJAR.log().error("Could not find a variable named " + name));
	}
	
	public Optional<String> resolveKeyDoubling(String key, ShellSession session) {
    	while(session.containsKey(key)) {    		
        	FeatJAR.log().message("This session already contains a Variable with that name: \n");
        	session.printVariable(key);    
    		String choice = Shell.readCommand("Overwrite " + key + " ? (y)es, (r)ename, (a)bort").orElse("").toLowerCase();
    		if(Objects.equals("y", choice)) {
    			removeSingleEntry(session, choice);
    			break;
    		} else if(Objects.equals("r", choice)) {
    			key = Shell.readCommand("Enter another vaiable name: ").orElse("");
    		} else if(Objects.equals("a", choice)){
    			FeatJAR.log().message("Aborted\n");
    			return Optional.empty();     		
    		}
    	} 
    	return Optional.of(key);
	}
    
    private <T> void loadFormat(Result<T> result, String key, ShellSession session) {
    	
    	key = resolveKeyDoubling(key, session).get();
    	
    	if(key.isEmpty()) {
    		return;
    	}
    	
		if(result.isPresent()) {			
			if (FeatureModel.class.isAssignableFrom(result.get().getClass())) {       
				session.put(key, (FeatureModel) result.get(), FeatureModel.class);
				
			} else if (IFormula.class.isAssignableFrom(result.get().getClass())){ 
				session.put(key, (IFormula) result.get(), IFormula.class);
				
			} else if (BooleanAssignment.class.isAssignableFrom(result.get().getClass())){ 
				session.put(key, ((BooleanAssignmentGroups) result.get()).getFirstGroup().getFirst(), BooleanAssignment.class);
				
			} else if (BooleanAssignmentList.class.isAssignableFrom(result.get().getClass())){
				session.put(key, ((BooleanAssignmentGroups) result.get()).getFirstGroup(), BooleanAssignmentList.class);
				
			} else if (BooleanAssignmentGroups.class.isAssignableFrom(result.get().getClass())){
				session.put(key, (BooleanAssignmentGroups) result.get(), BooleanAssignmentGroups.class);
				
			}
			FeatJAR.log().message(key + " successfully loaded\n");		
		} else {						
			printformatedProblem(result, key);			
		}
    }
    
    private <T> void printformatedProblem(Result<T> result, String key) {
		String wrongFormat = "Possible formats:";
        String input = Problem.printProblems(result.getProblems());
        
        if(input.contains(wrongFormat)) {
            int firstIndex = input.indexOf(wrongFormat) + wrongFormat.length();
            int SecondIndex = input.lastIndexOf("]") + 1;        
            String message = input.substring(0, firstIndex);
            String possibleFormats = input.substring(firstIndex , SecondIndex);
            possibleFormats = possibleFormats.replace(",", ",\n"); 
            
            FeatJAR.log().error(message + "\n");
            FeatJAR.log().error(possibleFormats);
        } else {
			FeatJAR.log().error("Could not load file %s for variable %s", result.getProblems().get(0).getMessage(), key);
			FeatJAR.log().problems(result.getProblems(), Verbosity.DEBUG);
        }        
    }
    
	public <T> void parseArguments(ShellSession session, List<String> cmdParams, AFormats<T> format) {		
		String name = setVarName(session, cmdParams); 
		String path = setPath(cmdParams);
        
        if(path.isBlank()) {
        	// TODO add result / problems 
        	FeatJAR.log().debug("No correct path for %s specified", getFormatName().orElse("")); 
        	return;
        }
        
        loadFormat(IO.load(
				Paths.get(path), format), name, session);
	}
}
