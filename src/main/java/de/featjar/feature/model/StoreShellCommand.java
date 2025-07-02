package de.featjar.feature.model;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import de.featjar.base.FeatJAR;
import de.featjar.base.data.Result;
import de.featjar.base.io.IO;
import de.featjar.base.io.format.IFormat;
import de.featjar.base.io.text.StringTextFormat;
import de.featjar.feature.model.io.FeatureModelFormats;
import de.featjar.feature.model.io.xml.XMLFeatureModelFormat;
import de.featjar.formula.io.csv.BooleanAssignmentGroupsCSVFormat;
import de.featjar.formula.io.dimacs.FormulaDimacsFormat;

// TODO nicht alle Vriablen sondern nur X gleiches für load, Format dass alles als String speichert, autocomplete und dann erst weiter \r dann geht er einen zurück in der konsole, globales abort

public class StoreShellCommand implements IShellCommand {	
	
	public StoreShellCommand() {
		super();
	}

	public static <T> void save(T res, Path path, IFormat<T> format) {		
		try {
			IO.save(res, path, format);
		} catch (IOException e) {
			FeatJAR.log().error(e);
			e.printStackTrace();
		}
	}

	@Override
	public void execute(ShellSession session) {		
//		StringTextFormat st = new StringTextFormat();

		System.out.println(session.listOfSampleLists.get(0));
		session.listOfSampleLists.forEach(t -> save(t, Paths.get("/home/hans/Dokumente/Uni/Arbeit/Sebastian/Neu/FeatJAR/feature-model/abc.xml"), new BooleanAssignmentGroupsCSVFormat()));
	}
	
    @Override
    public Optional<String> getShortName() {
        return Optional.of("store");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("WIP store seesion objetcs on your hard drive - <cmd> <path> <format>");
    }
}
