package org.parking.main.constants;

import org.junit.internal.JUnitSystem;

public enum WhenToNotifySubscribers {
	HUNDRED_PERCENT (100),
	EIGHTY_PERCENT (80);
	private final  int levelCode;

    private WhenToNotifySubscribers(int levelCode) {
        this.levelCode = levelCode;
    }
    
    public int getValue() {
    	return this.levelCode;
    }
}

