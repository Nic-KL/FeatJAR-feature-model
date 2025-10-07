package de.featjar.feature.model;

import java.util.List;
import java.util.Optional;

import de.featjar.feature.model.io.FeatureModelFormats;


public class LoadFeatureModel extends LoadShellCommand {

	@Override
	public void execute(ShellSession session, List<String> cmdParams) {			
		//TODO global abort, catch Exception					
		
//		String name = null;
//		String path = null;
//		
//		if(cmdParams.isEmpty()) {
//	        name = setVarName(getDefaultName().orElse(""), getFormatName().orElse(""), session, cmdParams);        
//	        path = setPath(getFormatName().orElse("Empty Model ?"), cmdParams);
//		} else if(cmdParams.size() == 1) {
//			name = setVarName(getDefaultName().orElse(""), getFormatName().orElse(""), session, cmdParams);     
//			path = cmdParams.get(0);
//		} else {
//			name = cmdParams.get(0);
//			path = cmdParams.get(1);
//		}
//        
//        if(path.isBlank()) {
//        	FeatJAR.log().info("No " + getFormatName().orElse("") + " specified"); // TODO maybe add problems ?
//        	return;
//        }
//        
//        loadFormat(IO.load(
//				Paths.get(path), FeatureModelFormats.getInstance()), name, session);
		
//		IO.load(Paths.get(""), FeatureModelFormats.getInstance());
		
		parseArguments(session, cmdParams, FeatureModelFormats.getInstance());
	}		
    @Override
    public Optional<String> getShortName() {
        return Optional.of("loadFeatureModel");
    }
    @Override
    public Optional<String> getDescription(){
    	return Optional.of("load a feature model - <cmd> <name> <path>");
    }
	@Override
	public Optional<String> getFormatName() {
		return Optional.of("FeatureModel");
	}
	@Override
	public Optional<String> getDefaultName() {
		return Optional.of("fm");
	}
}

