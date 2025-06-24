package de.featjar.feature.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import de.featjar.base.extension.IExtension;
import de.featjar.base.io.IO;
import de.featjar.formula.assignment.BooleanAssignmentGroups;
import de.featjar.formula.io.FormulaFormats;
import de.featjar.formula.structure.IFormula;

public class LoadShellCommand extends AbstractShellCommand {

	public LoadShellCommand() {
		super();
	}
	
	private static class LoadFeatureModel implements IShellCommand {

		@Override
		public void execute(ShellSession session) {
			String inputString;
			inputString = readCommand("Enter a vaild path:\n$$$");
			Path path = Paths.get(inputString); // TODO Exception, Exit -> globales abort !, auslagern ?
			
			IFeatureModel fm = IOShellStuff.loadFeatureModel(path); // TODO catch Exception
			session.fmList.add(fm);
//			System.out.println(session.fmList.get(0));			
		}		
	}
	
	private static class LoadFormula implements IShellCommand {

		@Override
		public void execute(ShellSession session) {
			String inputString;
			inputString = readCommand("Enter a vaild path:\n$$$ ");
			Path path = Paths.get(inputString);
			
			IFormula formula = IOShellStuff.loadFormula(path); // TODO catch Exception
			session.ifList.add(formula);
//			IO.print(formula, IFOrm);
		}	
	}	

	private static class LoadSampleGroup implements IShellCommand {

		@Override
		public void execute(ShellSession session) {
			String inputString;
			inputString = readCommand("Enter a vaild path:\n$$$ ");
			Path path = Paths.get(inputString);
			
			BooleanAssignmentGroups sampleGroup = IOShellStuff.loadSampleGroup(path); // TODO catch Exception
			session.listOfSampleLists.add(sampleGroup);
//			System.out.println(session.ifList.get(0));			
		}	
	}	
	
	
	private final Map<String, IShellCommand> subCommands = Map.of(
			"1", new LoadFeatureModel(),
			"2", new LoadFormula(),
			"3", new LoadSampleGroup(),
			"4", (session) -> {System.out.println("nothing");}
			);
	
	//TODO PrintShellcommands nachgucken Extensions -> laden wie in dieser java
	
	@Override
	public void execute(ShellSession session) {
		String inputString;
		do {
			inputString = readCommand("load: 1) FeatureModel, 2) Formula 3) SampleGroup 4) nothing, e) exit\n$$ ");
			IShellCommand lShellCommand = subCommands.get(inputString);
			if(lShellCommand != null) {
				lShellCommand.execute(session);
			}
			if(inputString.equals("e")) {break;}
		}while(inputString != "e");		
	}
	
    @Override
    public Optional<String> getShortName() {
        return Optional.of("load");
    }


}
