package de.featjar.feature.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.featjar.formula.structure.IFormula;

public class PrintShellCommand implements IShellCommand {

	@Override
	public void execute(ShellSession session) {
		//TODO layer for electing the type and then
		printMap(session.featureModelList);
		printMap(session.formulaList);
		printMap(session.configList);
		printMap(session.sampleList);
		printMap(session.listOfSampleLists);		
	}
	
	
	private void printMap(Map<String, ?> map) {
		if(map.isEmpty()) {
			return;
		}
		map.forEach((key, t) ->{
			if (t instanceof List) {
				System.out.println(key + ": ");
				((List) t).forEach(l -> System.out.println(l));
			} else if (t instanceof IFormula) {
				System.out.println(key + ": " + "\n" + ((IFormula) t).print());
			} else {
				System.out.println(key + ": " + "\n" + t);
			}
		});
	}
	
    public Optional<String> getShortName() {
        return Optional.of("print");
    }

    public Optional<String> getDescription(){
    	return Optional.of("WIP");
    }
}
