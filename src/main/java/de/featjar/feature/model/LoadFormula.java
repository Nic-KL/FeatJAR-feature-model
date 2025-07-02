package de.featjar.feature.model;

import java.nio.file.Paths;
import de.featjar.formula.io.FormulaFormats;

public class LoadFormula extends LoadShellCommand {
	
	@Override
	public void execute(ShellSession session) {
		//TODO global abort, catch Exception			
		session.formulaList.add(loadAFormat(Paths.
				get(Shell.readCommand("Enter a vaild path to load a Formula:\n")), FormulaFormats
				.getInstance()));	
	}	
}
