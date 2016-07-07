package com.ictra.gu.pt;

import java.util.Collection;

public class PerformanceMeasurement {
	
	RecordByTypeContainer recordByTypeContainer = new RecordByTypeContainer();

	public void record(String type, long elapsed) {
		RecordByType recordByType = recordByTypeContainer.getRecordByType(type);
		recordByType.addEntry(elapsed);
	}

	public long averageExecutionTime() {
		long sum = 0;
		for (RecordByType recordByType : recordByTypeContainer.getAllRecordByType()) {
			sum += recordByType.averageExecutionTime();
		}
		return recordByTypeContainer.size() > 0 ? sum / recordByTypeContainer.size() : 0;
	}

	public Collection<RecordByType> getRecordByTypes() {
		return recordByTypeContainer.getAllRecordByType();
	}

	public void reset() {
		for (RecordByType recordByType : recordByTypeContainer.getAllRecordByType()) {
			recordByType.clear();
		}		
	}


}
