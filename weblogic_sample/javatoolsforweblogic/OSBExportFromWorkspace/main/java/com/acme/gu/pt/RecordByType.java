package com.ictra.gu.pt;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class RecordByType {
	private String type;
	private List<Long> measurements = Collections.synchronizedList(new ArrayList<Long>());

	public RecordByType(String type) {
		this.type = type;
	}

	public synchronized void addEntry(long elapsed) {
		measurements.add(elapsed);
	}

	public long averageExecutionTime() {
		long result = 0;
		synchronized (measurements) {
			for (Long item : measurements) {
				result += item;
			}		
		}

		return measurements.size() > 0 ? result / measurements.size() : 0;
	}

	public int numberOfRecordsAboveThreshold(long i) {
		int count = 0;
		synchronized (measurements) {
			for (Long item : measurements) {
				if (item > i)
					count++;
			}
		}
		return count;
	}

	public synchronized void clear() {
		measurements.clear();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
