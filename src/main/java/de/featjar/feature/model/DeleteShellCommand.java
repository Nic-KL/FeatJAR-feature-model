package de.featjar.feature.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.featjar.base.FeatJAR;

public class DeleteShellCommand implements IShellCommand {

	@Override
	public void execute(ShellSession session, List<String> cmdParams) {
		
		if(cmdParams.isEmpty()) {
			cmdParams = Shell.readCommand("Enter the variable names you want to delete:")
				    .map(c -> Arrays.stream(c.split("\\s+")).collect(Collectors.toList()))
				    .orElse(Collections.emptyList());
		}
		
//		if(cmdParams.get(0).equals("full")) {
//			session.getElement(cmdParams.get(1))
//			.ifPresentOrElse(Map::clear, () -> FeatJAR.log().error("no correct key specified"));
//			return;
//		}	
		
		cmdParams.forEach(e -> {
			session.remove(e).ifPresentOrElse(a -> System.out.println("Removing of " + e + " Successful"),
					() -> FeatJAR.log().error("Could not find a variable named " + e));
		});		
	}
	
	@Override
    public Optional<String> getShortName() {
        return Optional.of("delete");
    }
	
	@Override
    public Optional<String> getDescription(){
    	return Optional.of("WIP");
    }

}
