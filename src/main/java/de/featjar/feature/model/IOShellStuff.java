package de.featjar.feature.model;

import java.util.List;
import java.io.IOException;
import java.net.http.HttpClient.Version;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.ArrayList;

import de.featjar.base.FeatJAR;
import de.featjar.base.data.Problem;
import de.featjar.base.data.Result;
import de.featjar.base.io.IO;
import de.featjar.base.io.format.AFormats;
import de.featjar.base.io.format.IFormat;
import de.featjar.base.io.format.IFormatSupplier;
import de.featjar.base.log.Log.Verbosity;
import de.featjar.feature.model.io.FeatureModelFormats;
import de.featjar.formula.assignment.BooleanAssignment;
import de.featjar.formula.assignment.BooleanAssignmentGroups;
import de.featjar.formula.assignment.BooleanAssignmentList;
import de.featjar.formula.assignment.IAssignment;
import de.featjar.formula.io.BooleanAssignmentGroupsFormats;
import de.featjar.formula.io.FormulaFormats;
import de.featjar.formula.structure.IFormula;

public class IOShellStuff {
	
	
//	public static <T> void test(Result<T> res, Path path) {
//		Result<T> resio = IO.load(path, res);
//		
//		List<AFormats<T>> a = new ArrayList<AFormats<T>>();
//		
//		
//	}
	
//	public static void abc() {
//	loadAFormat(Paths.get("asdads"), FeatureModelFormats.getInstance());
//	
//	BooleanAssignment b = ll(Paths.get("asdads"), BooleanAssignmentGroupsFormats.getInstance()).getFirstGroup().get(0);
//	
//	Result<BooleanAssignmentGroups> res = loadAFormat(Paths.get("asdads"), BooleanAssignmentGroupsFormats.getInstance()).orElseLog(Verbosity.WARNING);
//	BooleanAssignment a = res.get().getFirstGroup().get(0);
//}
	
	public static <T> void save(Result<T> res, Path path, IFormat<T> format) {		
		try {
			IO.save(res.get(), path, format);
		} catch (IOException e) {
			FeatJAR.log().error(e);
		}
	}
	
//	public static <T> Result<T> loadAFormat(Path path, AFormats<T> format){
//		
//		return IO.load(path, format);
//	}
	
	public static <T> T loadAFormat(Path path, AFormats<T> format){
		
		return IO.load(path, format).orElseLog(Verbosity.WARNING);
	}
	
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
		return IO.load(path, BooleanAssignmentGroupsFormats.getInstance())
				.map(BooleanAssignmentGroups::getFirstGroup)
				.map(BooleanAssignmentList::getFirst)
				.orElseLog(Verbosity.WARNING);
	}
	
	public static BooleanAssignmentList loadSample(Path path) {
		Result<BooleanAssignmentGroups> result = IO.load(path, BooleanAssignmentGroupsFormats.getInstance());
		
		if(result.isEmpty()) {
			List<Problem> problems = result.getProblems();
			FeatJAR.log().problems(problems);
			return null; // TODO throw Exception
		} else {
			return result.get().getFirstGroup();
		}
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
