package de.featjar.feature.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import de.featjar.base.extension.IExtension;
import de.featjar.formula.structure.IFormula;

public class LoadShellCommand extends AbstractShellCommand {

	public LoadShellCommand() {
		super("load");
	}
	
	private static class LoadFeatureModel implements IShellCommand {

		@Override
		public void execute(ShellSession session, String[] args) {
			String inputString;
			inputString = readCommand("Enter a vaild path:\n$$$");
			Path path = Paths.get(inputString); // TODO Exception, Exit -> globales abort !, auslagern ?
			
			IFeatureModel fm = IOShellStuff.loadFeatureModel(path); // TODO catch Exception
			session.fmList.add(fm);
			System.out.println(session.fmList.getFirst());			
		}		
	}
	
	private static class LoadFormula implements IShellCommand {

		@Override
		public void execute(ShellSession session, String[] args) {
			String inputString;
			inputString = readCommand("Enter a vaild path:\n$$$ ");
			Path path = Paths.get(inputString);
			
			IFormula fm = IOShellStuff.loadFormula(path); // TODO catch Exception
			session.ifList.add(fm);
			System.out.println(session.ifList.getFirst());			
		}		
	}	
	
	private final Map<String, IShellCommand> subCommands = Map.of(
			"1", new LoadFeatureModel(),
			"2", new LoadFormula(),
			"3", (session, arg) -> {System.out.println("nothing");}
			);
	
	//TODO PrintShellcommands nachgucken Extensions -> laden wie in dieser java
	
	@Override
	public void execute(ShellSession session, String[] args) {
		String inputString;
		do {
			inputString = readCommand("load: 1) FeatureModel, 2) Formula 3) nothing, e) exit\n$$ ");
			IShellCommand lShellCommand = subCommands.get(inputString);
			if(lShellCommand != null) {
				lShellCommand.execute(session, args);
			}
			if(inputString.equals("e")) {break;}
		}while(inputString != "e");		
	}
	
    @Override
    public Optional<String> getShortName() {
        return Optional.of("load");
    }


}
