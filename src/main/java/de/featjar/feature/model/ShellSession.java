package de.featjar.feature.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.featjar.formula.assignment.BooleanAssignment;
import de.featjar.formula.assignment.BooleanAssignmentGroups;
import de.featjar.formula.assignment.BooleanAssignmentList;
import de.featjar.formula.structure.IFormula;

public class ShellSession {
	
	public Map<String, FeatureModel> featureModelList = new HashMap<>();
	
	public Map<String, IFormula> formulaList = new HashMap<>();
	
	public Map<String, BooleanAssignment> configList = new HashMap<>();
	
	public Map<String, BooleanAssignmentList> sampleList = new HashMap<>();
	
	public Map<String, BooleanAssignmentGroups> listOfSampleLists = new HashMap<>();
	
	public List<Map<String, ?>> all = List.of(featureModelList, formulaList, configList, sampleList, listOfSampleLists);
	
	public Optional<Map<String, ?>> getModel(String key){
		return all.stream().filter(m -> m.containsKey(key)).findFirst();
	}
}
