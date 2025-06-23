package de.featjar.feature.model;

import java.util.ArrayList;
import java.util.List;

import de.featjar.formula.assignment.BooleanAssignment;
import de.featjar.formula.assignment.BooleanAssignmentGroups;
import de.featjar.formula.assignment.BooleanAssignmentList;
import de.featjar.formula.structure.IFormula;

public class ShellSession {
	public List<IFeatureModel> fmList = new ArrayList<>();
	
	public List<IFormula> ifList = new ArrayList<>();

	public List<BooleanAssignment> configList = new ArrayList<>();
	
	public List<BooleanAssignmentList> sampleList = new ArrayList<>();
	
	public List<BooleanAssignmentGroups> listofSampleLists = new ArrayList<>();
}
