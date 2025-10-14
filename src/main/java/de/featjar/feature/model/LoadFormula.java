package de.featjar.feature.model;

import java.util.List;
import java.util.Optional;

import de.featjar.base.shell.ShellSession;
import de.featjar.formula.io.FormulaFormats;

public class LoadFormula extends ALoadShellCommand {
	
	@Override
	public void execute(ShellSession session, List<String> cmdParams) {		
		parseArguments(session, cmdParams, FormulaFormats.getInstance());
	}	
    @Override
    public Optional<String> getShortName() {
        return Optional.of("loadFormula");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("load a formula - <cmd> <name> <path>");
    }
	@Override
	public Optional<String> getFormatName() {
		return Optional.of("Formula");
	}
	@Override
	public Optional<String> getDefaultName() {
		return Optional.of("form");
	}
}
