package de.featjar.feature.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import de.featjar.base.FeatJAR;

public class ClearShellCommand implements IShellCommand {

	@Override
	public void execute(ShellSession session) {
		
		List<String> cmdArg = Arrays.stream(Shell.readCommand("Enter the variable names you want to delete. \n(If you type: full <varname> all models of that type get deleted):")
				.toLowerCase()
				.split("\\s+"))
				.collect(Collectors.toList());
		
		if(cmdArg.get(0).equals("full")) {
			session.getModel(cmdArg.get(1)).ifPresentOrElse(Map::clear, () -> FeatJAR.log().error("no correct key specified"));
			return;
		}
			
		cmdArg.forEach(e -> session.getModel(e)
				.map(m -> m.remove(e))
				.ifPresentOrElse(v -> System.out.println("Removing of " + e +" Successful")
						, () -> FeatJAR.log().error("Could not find a variable named " + e)));
	}
	
	@Override
    public Optional<String> getShortName() {
        return Optional.of("clear");
    }
	
	@Override
    public Optional<String> getDescription(){
    	return Optional.of("WIP");
    }
	
}
