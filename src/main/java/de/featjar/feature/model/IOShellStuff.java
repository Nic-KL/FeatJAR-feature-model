package de.featjar.feature.model;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.util.ArrayList;

import de.featjar.base.FeatJAR;
import de.featjar.base.data.Problem;
import de.featjar.base.data.Result;
import de.featjar.base.io.IO;
import de.featjar.base.log.Log.Verbosity;
import de.featjar.feature.model.io.FeatureModelFormats;
import de.featjar.formula.assignment.BooleanAssignment;
import de.featjar.formula.assignment.BooleanAssignmentGroups;
import de.featjar.formula.assignment.BooleanAssignmentList;
import de.featjar.formula.io.BooleanAssignmentGroupsFormats;
import de.featjar.formula.io.FormulaFormats;
import de.featjar.formula.structure.IFormula;

public class IOShellStuff {
	
	public static IFeatureModel loadFeatureModel(Path path) {		
		
		Result<FeatureModel> result = IO.load(path, FeatureModelFormats.getInstance());
        
		if(result.isEmpty()) {
			List<Problem> problems = result.getProblems();
			FeatJAR.log().problems(problems);
			return null; // TODO throw Exception
		} else {
			return result.get();
		}
	}
	
	public static IFormula loadFormula(Path path) {
		
		Result<IFormula> result = IO.load(path, FormulaFormats.getInstance());
        
		if(result.isEmpty()) {
			List<Problem> problems = result.getProblems();
			FeatJAR.log().problems(problems);
			return null; // TODO throw Exception
		} else {
			return result.get();
		}
	}
	
	public static BooleanAssignment loadConfig(Path path) {
		return null;
	}
	
	public static BooleanAssignmentList loadSample(Path path) {
		return null;
	}
	
	public static BooleanAssignmentGroups loadSampleGroup(Path path) {
		Result<BooleanAssignmentGroups> result = IO.load(path, BooleanAssignmentGroupsFormats.getInstance());
        
		if(result.isEmpty()) {
			List<Problem> problems = result.getProblems();
			FeatJAR.log().problems(problems);
			return null; // TODO throw Exception
		} else {
			return result.get();
		}
	}

}
