package Agenda;

import java.time.LocalTime;

import Enumerations.States;

public class Task {

	private String title;
	private LocalTime startTime;
	private LocalTime endTime;
	private States state;
	private boolean startTimeChecked;
	private boolean endTimeChecked;
	
	public Task(String title, LocalTime startTime, LocalTime endTime, States state, boolean startTimeChecked, boolean endTimeChecked) {
		super();
		this.title = title;
		this.startTime = startTime;
		this.endTime = endTime;
		this.state = state;
		this.startTimeChecked = startTimeChecked;
		this.endTimeChecked = endTimeChecked;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public States getState() {
		return state;
	}

	public void setState(States state) {
		this.state = state;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public boolean isStartTimeChecked() {
		return startTimeChecked;
	}

	public void setStartTimeChecked(boolean startTimeChecked) {
		this.startTimeChecked = startTimeChecked;
	}

	public boolean isEndTimeChecked() {
		return endTimeChecked;
	}

	public void setEndTimeChecked(boolean endTimeChecked) {
		this.endTimeChecked = endTimeChecked;
	}
	
}
