package de.featjar.feature.model;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import de.featjar.formula.io.FormulaFormats;
import de.featjar.formula.structure.IFormula;

public class LoadFormula extends LoadShellCommand {
	
	@Override
	public void execute(ShellSession session, List<String> cmdParams) {
		//TODO global abort, catch Exception						
		
		String name = cmdParams.size() > 0 ? cmdParams.get(0) : 
			setName().orElse("formula" + (session.getSize() + 1));
		
        String path = cmdParams.size() > 1 ? cmdParams.get(1) : 
        	Shell.readCommand("Enter a path to load a Formula:").orElse("");
        
        if(path.isBlank()) {
        	System.out.println("No Formula specified");
        	return;
        }

		session.put(name, 
				loadAFormat(
						Paths.get(path), FormulaFormats.getInstance()), IFormula.class);
		
//		session.put(setName().orElse("formula" + (session.elements.size() + 1)), 
//		loadAFormat(
//				Paths.get(Shell.readCommand("Enter a vaild path to load a Formula:\n")
//				.orElse("")), FormulaFormats.getInstance()), IFormula.class);
	}	
    @Override
    public Optional<String> getShortName() {
        return Optional.of("load-Formula");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("load a formula - <cmd> <name> <path>");
    }
}
