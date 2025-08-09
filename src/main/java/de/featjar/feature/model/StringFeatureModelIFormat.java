package de.featjar.feature.model;

import de.featjar.base.data.Result;
import de.featjar.base.io.format.IFormat;

public class StringFeatureModelIFormat implements IFormat<FeatureModel> {
	
	@Override
	public Result<String> serialize(FeatureModel fm) {		
        return Result.of(fm.toString());
    }
	
	@Override
	public StringFeatureModelIFormat getInstance() {
		return this;
	}

	@Override
	public String getName() {
		return "featuremodel";
	}
	
    @Override
    public boolean supportsSerialize() {
        return true;
    }

    @Override
    public boolean supportsParse() {
        return false;
    }
    
    @Override
    public String getFileExtension() {
        return "text";
    }
}
