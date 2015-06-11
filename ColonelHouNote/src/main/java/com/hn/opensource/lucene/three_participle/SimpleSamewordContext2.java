package com.hn.lu.three_participle;

import java.util.HashMap;
import java.util.Map;

public class SimpleSamewordContext2 implements SamewordContext {
	
	Map<String,String[]> maps = new HashMap<String,String[]>();
	public SimpleSamewordContext2() {
		maps.put("�й�",new String[]{"�쳯","��½"});
	}

	@Override
	public String[] getSamewords(String name) {
		return maps.get(name);
	}

}
