package com.siemieniuk.animals;

/**
 * Represents states of Predator
 * 0: Hunting (a predator needs to hunt)
 * 1: Relaxation (a predator relaxes)
 * @author Szymon Siemieniuk
 *
 */
public enum PredatorMode {
	HUNTING {
		@Override public String toString() {
			return "hunting";
		}
	},
	
	RELAXATION {
		@Override public String toString() {
			return "relaxation";
		}
	}
}
