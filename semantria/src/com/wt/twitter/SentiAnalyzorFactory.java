package com.wt.twitter;

public class SentiAnalyzorFactory {

	static SentiAnalyzor _instance = null;
	
	static public SentiAnalyzor getInstance() {
		if (_instance == null)
			_instance = new SentiAnalyzor();
		
		return _instance;
	}
}
