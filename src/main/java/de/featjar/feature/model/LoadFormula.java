package de.featjar.feature.model;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import de.featjar.base.FeatJAR;
import de.featjar.formula.io.FormulaFormats;

public class LoadFormula extends LoadShellCommand {
	
	@Override
	public void execute(ShellSession session, List<String> cmdParams) {		
		//TODO global abort, catch Exception					
		
		String name = cmdParams.size() > 0 ? cmdParams.get(0) : 
			setName(getModelName().orElse(""), getDefaultName().orElse(""))
			.orElse(getDefaultName().orElse("") + (session.getSize() + 1));
		
        String path = cmdParams.size() > 1 ? cmdParams.get(1) : 
        	Shell.readCommand("Enter a path to load a " + getModelName().orElse("")).orElse("");
        
        if(path.isBlank()) {
        	FeatJAR.log().info("No " + getModelName().orElse("") + " specified"); // TODO maybe add problems ?
        	return;
        }
        
		saveModel(loadFormat(
				Paths.get(path), FormulaFormats.getInstance()), name, session);

//		session.put(name, 
//				loadAFormat(
//						Paths.get(path), FormulaFormats.getInstance()), IFormula.class);
		
		
//		Result<IFormula> formula = loadFormat(
//						Paths.get(path), FormulaFormats.getInstance());;
//						
//		if(formula.isPresent()) {
//			FeatJAR.log().info(name + "successfully loaded");
//			session.put(name, formula.get(), IFormula.class);
//		} else {
//			FeatJAR.log().problems(formula.getProblems(), Verbosity.ERROR);
//		}
        
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
	@Override
	public Optional<String> getModelName() {
		return Optional.of("Formula");
	}
	@Override
	public Optional<String> getDefaultName() {
		return Optional.of("form");
	}
}
