package de.featjar.feature.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.featjar.base.FeatJAR;
import de.featjar.formula.structure.IFormula;

public class PrintShellCommand implements IShellCommand {

	@Override
	public void execute(ShellSession session, List<String> cmdParams) {
		//  TODO layer for electing the type and then	
		
		if(cmdParams.isEmpty()) {
			cmdParams = Shell.readCommand("Enter the variable names you want to print:")
				    .map(c -> Arrays.stream(c.toLowerCase().split("\\s+")).collect(Collectors.toList()))
				    .orElse(Collections.emptyList());
		}
			
		cmdParams.forEach(e -> {
		    session.getElement(e)
		        .ifPresentOrElse(m -> {
		        	System.out.println(e + ": \n\n");
		        	printMap(m);
		        }, () -> FeatJAR.log().error("Could not find a variable named " + e));
		});	
	}
	
	private void printMap(Object v) {
			if (v instanceof IFormula) {
				System.out.println(((IFormula) v).print());
			} else {
				System.out.println(v);
			}
			System.out.println("");
	}
	
    public Optional<String> getShortName() {
        return Optional.of("print");
    }

    public Optional<String> getDescription(){
    	return Optional.of("WIP");
    }
}
