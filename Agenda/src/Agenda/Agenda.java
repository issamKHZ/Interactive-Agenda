package Agenda;

import java.time.LocalTime;
import java.util.*;

public class Agenda {
	
	private List<Task> tasks;

	public Agenda(List<Task> tasks) {
		super();
		this.tasks = tasks;
	}
	
	public Agenda() {
		super();
		this.tasks = new ArrayList<Task>(); 
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public Task getTaskByTitle(String title) {
		for(Task task: this.tasks) {
			if (task.getTitle().equals(title)) {
				return task;
			} 
		}
		return null;
	}
	
	public void addTask(Task task) {
		int initialSize = this.tasks.size();
		int i = 0;
		while (i < this.tasks.size() && !this.tasks.contains(task)) {
			if (this.tasks.get(i).getStartTime().isAfter(task.getStartTime())) {
				this.tasks.add(i, task);				
			}
			i++;
		}
		if (this.tasks.size() == initialSize) {
			this.tasks.add(task);
		}		
	}
	
	public void deleteTask(Task task) {
		if (this.tasks.contains(task)) {
			this.tasks.remove(task);
		}
	}
	
	public LocalTime getStartTimeByName(String title) {
		Task task = this.getTaskByTitle(title);
		return task.getStartTime();
	}
	
	public LocalTime getEndTimeByName(String title) {
		Task task = this.getTaskByTitle(title);
		return task.getEndTime();
	}
	
	public void setStartTimeByName(String title, LocalTime time) {
		Task task = this.getTaskByTitle(title);
		task.setStartTime(time);
	}
	
	public void setEndTimeByName(String title, LocalTime time) {
		Task task = this.getTaskByTitle(title);
		task.setEndTime(time);
	}
	
	public void setTitleByName(String oldTitle, String newTitle) {
		Task task = this.getTaskByTitle(oldTitle);
		task.setTitle(newTitle);
	}
	
	public void setSTCheckedByName(String title) {
		Task task = this.getTaskByTitle(title);
		task.setStartTimeChecked(false);
	}
	
	public void setETCheckedByName(String title) {
		Task task = this.getTaskByTitle(title);
		task.setEndTimeChecked(false);
	}
	
	public void sortAgenda() {
		this.tasks.sort((t1, t2) -> t1.getStartTime().compareTo(t2.getStartTime())); 
	}
}
