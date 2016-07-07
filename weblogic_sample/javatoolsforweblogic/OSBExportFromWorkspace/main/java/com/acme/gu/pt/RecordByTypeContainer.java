package com.ictra.gu.pt;

import java.util.Collection;
import java.util.HashMap;

@SuppressWarnings("serial")
public class RecordByTypeContainer extends HashMap<String, RecordByType> {

	public RecordByType getRecordByType(String type) {
		RecordByType result = get(type);
		if (result == null) {
			result = new RecordByType(type);
			this.put(type, result);
		}
		return result;
	}
	
	public Collection<RecordByType> getAllRecordByType() {
		return values();
	}

}
