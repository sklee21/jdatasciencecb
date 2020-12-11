package net.uxl21.jdatasciencecb.util;

import java.util.Date;

public class StopWatch {
	
	private String label = null;

	private Date startDt = null;
	
	private Date finishDt = null;
	
	private long ellapedMs = -1L;
	
	private long ellapedSecs = -1L;
	
	
	
	public StopWatch() {
		this.label = "StopWatch";
	}
	
	
	public StopWatch(String label) {
		this.label = label;
	}
	
	
	
	public void start() {
		this.startDt = new Date();
	}
	
	
	public void stop() {
		this.finishDt = new Date();
		this.ellapedMs = this.finishDt.getTime() - this.startDt.getTime();
		this.ellapedSecs = this.ellapedMs / 1000L;
	}
	
	
	
	public String getLabel() {
		return this.label;
	}
	
	
	public long getEllapedMs() {
		return this.ellapedMs;
	}
	
	
	public long getEllapedSecs() {
		return this.ellapedSecs;
	}


	@Override
	public String toString() {
		if( this.startDt == null ) {
			return this.label + " is not started.";
		}
		
		if( this.finishDt == null ) {
			return this.label + " is still running.";
		}
		
		return this.label + ": Ellaped time is " + this.ellapedMs + " milliseconds";
	}
	
}
