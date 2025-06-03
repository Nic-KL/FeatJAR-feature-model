package de.featjar.feature.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import de.featjar.formula.structure.IFormula;

public class LoadShellCommand extends AbstractShellCommand {

	public LoadShellCommand() {
		super("load");
	}
	
	private static class LoadFeatureModel implements IShellCommand {

		@Override
		public void execute(ShellContext ctx, String[] args) {
			String inputString;
			inputString = ctx.readCommand("Enter a vaild path:\\n$$$");
			Path path = Paths.get(inputString);
			
			IFeatureModel fm = IOShellStuff.loadFeatureModel(path); // TODO catch Exception
			IOShellStuff.fmList.add(fm);
			System.out.println(IOShellStuff.fmList.getFirst());
			
		}
		
	}
	
	private static class LoadFormula implements IShellCommand {

		@Override
		public void execute(ShellContext ctx, String[] args) {
			String inputString;
			inputString = ctx.readCommand("Enter a vaild path:\n$$$ ");
			Path path = Paths.get(inputString);
			
			IFormula fm = IOShellStuff.loadFormula(path); // TODO catch Exception
			IOShellStuff.ifList.add(fm);
			System.out.println(IOShellStuff.ifList.getFirst());
			
		}
		
	}
	
	private final Map<String, IShellCommand> subCommands = Map.of(
			"1", new LoadFeatureModel(),
			"2", (ctx, arg) -> {System.out.println("nothing");}
			);
	
	@Override
	public void execute(ShellContext ctx, String[] args) {
		String inputString;
		do {
			inputString = ctx.readCommand("1) load FeatureModel, 2) load nothing e) exit\n$$ ");
			IShellCommand lShellCommand = subCommands.get(inputString);
			if(lShellCommand != null) {
				lShellCommand.execute(ctx, args);
			}
			if(inputString.equals("e")) {break;}
		}while(inputString != "e");
		
	}


}
