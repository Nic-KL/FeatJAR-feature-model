package de.featjar.feature.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ClearShellCommand implements IShellCommand {

	@Override
	public void execute(ShellSession session, List<String> cmdParams) {
		
//		List<String> cmdArg = Arrays.stream(Shell.readCommand("Enter the variable names you want to delete. \n(If you type: full <varname> all models of that type get deleted):")
//				.toLowerCase()
//				.split("\\s+"))
//				.collect(Collectors.toList());
		
			if(Objects.equals("y", Shell.readCommand("Clearing the entire session. Proceed ? (y)es (n)o").orElse("").toLowerCase().trim())) {
				session.clear();
				System.out.println("Clearing successful");
			} else {
				System.out.println("Clearing aborted");
			}
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
