package Agenda;

import java.awt.Color;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import Enumerations.States;
import Enumerations.Time;

public class AgendaHandler {

	private static JButton submitButton;
	
	private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static Future<Void> openAgenda(Agenda agenda) {
        return executor.submit(() -> {
        	SwingUtilities.invokeLater(new Runnable(){
    			public void run(){
    				AgendaWindow window = new AgendaWindow(agenda);
    				window.setVisible(true);    				
    			}
    		});
            
            Thread.sleep(1000); 
            return null;
        });
    }
	
	public static void timeAsker(Agenda agenda) {
		submitButton = AgendaWindow.getSubmitButton();
		while (true) {			
			LocalTime heure = null;
			List<Task> undoneTasks = new ArrayList<Task>();
						
			heure = Utils.askForTime(submitButton);
					
	        if (heure != null) {
	        	undoneTasks = Utils.checkUndoneTasks(heure, agenda); 	        
	        	if (undoneTasks.size() != 0) {	 
	        		AgendaWindow.appendToConsole("You still have some undone tasks before this time : ", "\n");	        		        	
		        	for (Task task: undoneTasks) {
	        			AgendaWindow.appendToConsole("  - " + task.getTitle(), "\n");		        	
		        	}
		        	AgendaWindow.appendToConsole("\n", "");
		        	
		        	try {
		                Thread.sleep(3000); 
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        	Task finalTask = undoneTasks.get(undoneTasks.size() - 1);
		        	Utils.askForDoingTask(agenda, finalTask, submitButton);     	
	        	} else {
	        		AgendaWindow.appendToConsole("Chill, you already did all tasks", "\n \n");
	        	}
	        }
		}
	}
	
	public static void taskReminder(Agenda agenda, Time timeMotif) {
		checkLastTask(agenda);
		checkNextTask(agenda);
		checkCurrentTask(agenda);
		checkCurrentTaskLastMinutes(agenda);
		LocalTime now = LocalTime.now();
		Task task; 
		if (timeMotif.equals(Time.START)) {
			task = Utils.checkTasksTime(now, agenda, timeMotif);
			if (task != null && !task.isStartTimeChecked()) {
	        	task.setStartTimeChecked(true);
	        	try {
	        		AgendaWindow.notification.setText("It's time to start your task");
	        		for (int i=0; i<10; i++) {
	        			AgendaWindow.lamp.setBackground(Color.GREEN);
		        		Thread.sleep(100);
		        		AgendaWindow.lamp.setBackground(Color.GRAY);
		        		Thread.sleep(100);
	        		}	        			        		
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        	AgendaWindow.lamp.setBackground(Color.GREEN);
	        	AgendaWindow.notification.setText("This task is starting");
	        }
		} else {
			task = Utils.checkTasksTime(now, agenda, timeMotif);
			if (task != null && !task.isEndTimeChecked()) {
				task.setEndTimeChecked(true);
				try {
	        		AgendaWindow.notification.setText("You have 10min left before the end time of this task");
	        		for (int i=0; i<10; i++) {
	        			AgendaWindow.lamp.setBackground(Color.RED);
		        		Thread.sleep(100);
		        		AgendaWindow.lamp.setBackground(Color.GRAY);
		        		Thread.sleep(100);
	        		}	        		
	        		AgendaWindow.lamp.setBackground(Color.RED);
	        		Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}					        
			}
		}		
	}
	
	public static void checkLastTask(Agenda agenda) {
		agenda.sortAgenda();
		int index = 0;
		Task task;
		LocalTime now = LocalTime.now().withSecond(0).withNano(0);
		while (index < agenda.getTasks().size() && agenda.getTasks().get(index).getEndTime().compareTo(now) <= 0 && agenda.getTasks().get(index).getStartTime().compareTo(now) <= 0) {
			index++;
		}
		
		if (index == 0) {						
			task = Utils.compare2daysTask(agenda, 
										  agenda.getTasks().get(agenda.getTasks().size()-1), 
										  now, 
										  index);
		} else {
			task = Utils.compare2daysTask(agenda, 
										  agenda.getTasks().get(index-1), 
										  now, 
										  index);			
		}
		AgendaWindow.lastNameValue.setText(task.getTitle());
		States state = task.getState();
		if (state == States.UNDONE) {
			AgendaWindow.lastStatusValue.setForeground(Color.RED);			
		} else {
			AgendaWindow.lastStatusValue.setForeground(Color.GREEN);
		}
		AgendaWindow.lastStatusValue.setText(state.toString());			
		AgendaWindow.lastTimeValue.setText(task.getEndTime().toString());
	}
	
	public static void checkNextTask(Agenda agenda) {
		agenda.sortAgenda();
		int index = 0;
		LocalTime now = LocalTime.now().withSecond(0).withNano(0);
		while (index < agenda.getTasks().size() && !agenda.getTasks().get(index).getStartTime().isAfter(now)) {
			index++;
		}
		Task task;
		if (index == agenda.getTasks().size()) {
			task = agenda.getTasks().get(0);
		} else {
			task = agenda.getTasks().get(index);
		}
		AgendaWindow.nextNameValue.setText(task.getTitle());
		States state = task.getState();
		if (state == States.UNDONE) {
			AgendaWindow.nextStatusValue.setForeground(Color.RED);			
		} else {
			AgendaWindow.nextStatusValue.setForeground(Color.GREEN);
		}
		AgendaWindow.nextStatusValue.setText(state.toString());
		AgendaWindow.nextTimeValue.setText(task.getStartTime().toString());
	}
	
	public static void checkCurrentTask(Agenda agenda) {		
		agenda.sortAgenda();				
		List<Task> tasks = agenda.getTasks();
		Task task = null;
		LocalTime now = LocalTime.now().withSecond(0).withNano(0);
		
		for (int i=0; i < tasks.size(); i++) {	
			if (tasks.get(i).getStartTime().isAfter(tasks.get(i).getEndTime())) {
				if (Utils.isBetween(now, tasks.get(i).getStartTime(), LocalTime.of(23,59,59))
						|| Utils.isBetween(now, LocalTime.of(0,0,1), tasks.get(i).getEndTime())) {
					
					task = tasks.get(i);
				}
			} else {
				if (tasks.get(i).getStartTime().compareTo(now)<= 0 && 
						   tasks.get(i).getEndTime().isAfter(now)) {
					task = tasks.get(i);
				}
			}
		}		
		if (task != null) {
			AgendaWindow.currentNameValue.setText(task.getTitle());
			States state = task.getState();
			if (state == States.UNDONE) {
				AgendaWindow.currentStatusValue.setForeground(Color.RED);			
			} else {
				AgendaWindow.currentStatusValue.setForeground(Color.GREEN);
			}
			AgendaWindow.currentStatusValue.setText(task.getState().toString());
			AgendaWindow.currentTimeValue.setText(task.getStartTime().toString());
			AgendaWindow.notification.setText("Ongoing task");
			AgendaWindow.lamp.setBackground(Color.GREEN);
		} else {
			AgendaWindow.currentNameValue.setText("");
			AgendaWindow.currentTimeValue.setText("");
			AgendaWindow.currentStatusValue.setText("");
			AgendaWindow.notification.setText("");
			AgendaWindow.lamp.setBackground(Color.GRAY);
		}
	}
	
	public static void checkCurrentTaskLastMinutes(Agenda agenda) {
		agenda.sortAgenda();				
		List<Task> tasks = agenda.getTasks();
		Task thisTask = null;
		Task task;
		LocalTime now = LocalTime.now().withSecond(0).withNano(0);
		LocalTime preEndTime; 
		for (int i = 0; i < tasks.size(); i++) {
			task = tasks.get(i);
			preEndTime = task.getEndTime().minusMinutes(10);
			if (preEndTime.compareTo(now)<= 0 && task.getEndTime().isAfter(now)) {
				thisTask = task;
			}
		}
		if (thisTask != null) {
			AgendaWindow.notification.setText("This task will end at " + thisTask.getEndTime());
			AgendaWindow.lamp.setBackground(Color.RED);
		}
	}
}
