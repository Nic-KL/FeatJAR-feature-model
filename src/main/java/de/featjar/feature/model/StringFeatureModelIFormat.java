package de.featjar.feature.model;

import de.featjar.base.data.Result;
import de.featjar.base.io.format.IFormat;

public class StringFeatureModelIFormat implements IFormat<FeatureModel> {
	
	@Override
	public Result<String> serialize(FeatureModel featureModel) {		
        return Result.of(String.valueOf(featureModel));
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
    public boolean supportsWrite() {
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
