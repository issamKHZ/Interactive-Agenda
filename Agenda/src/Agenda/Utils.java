package Agenda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import javax.swing.JButton;

import Enumerations.States;
import Enumerations.Time;

public class Utils {	
	
	public static Task checkTasksTime(LocalTime now, Agenda agenda, Time timeMotif) {
		LocalTime time;
		LocalTime checkTime ;
		LocalTime normalizedNow = now.withSecond(0).withNano(0);		
		for (Task task: agenda.getTasks()) {
			if (timeMotif.equals(Time.START)) {
				time = task.getStartTime().withSecond(0).withNano(0);
				checkTime = time;
			} else {
				time = task.getEndTime().withSecond(0).withNano(0);
				checkTime = time.minusMinutes(10);
			}
			
			if (normalizedNow.compareTo(checkTime) == 0) {
				return task;
			}
		}
		return null;
	}	
	
	public static LocalTime askForTime(JButton button) {
	    final Object lock = new Object();
	    final String[] inputHolder = {""};
	    boolean valideTime = false;
	    LocalTime heure = null;

	    
	    button.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            synchronized (lock) {
	                inputHolder[0] = AgendaWindow.interactiveInputField.getText();
	                lock.notify();
	            }
	        }
	    });

	    while (!valideTime) {
	        AgendaWindow.appendToConsole("Please enter a time in the format HH:mm (ex: 5:30) : ", "");

	        synchronized (lock) {
	            try {
	                while (inputHolder[0].isEmpty()) {
	                    lock.wait(); 
	                }
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	                AgendaWindow.appendToConsole("Thread interrupted. Exiting...", "\n \n");
	                return null;
	            }
	        }

	        String input1 = inputHolder[0];
	        inputHolder[0] = "";

	        if (input1.equals("now")) {
	        	AgendaWindow.appendToConsole("now", "\n \n");
	            heure = LocalTime.now().withSecond(0).withNano(0);
	            AgendaWindow.interactiveInputField.setText("");
	            valideTime = true;
	        } else {
	            input1 = input1.replace('h', ':');
	            AgendaWindow.appendToConsole(input1, "\n \n");
	            try {
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
	                heure = LocalTime.parse(input1, formatter);
	                AgendaWindow.interactiveInputField.setText("");
	                valideTime = true;
	            } catch (DateTimeParseException e) {
	                AgendaWindow.appendToConsole("Format incorrect. Veuillez réessayer.", "\n \n");
	            }
	        }
	    }

	    return heure;
	}
	
	public static void askForDoingTask(Agenda agenda, Task finalTask, JButton button) {		
		boolean valideAnswer = false;
		final Object lock = new Object();
	    final String[] inputHolder = {""};
	    String input2 = null;
		
		button.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            synchronized (lock) {
	                inputHolder[0] = AgendaWindow.interactiveInputField.getText();
	                lock.notify();
	            }
	        }
	    });
		
		while (!valideAnswer) {
			AgendaWindow.appendToConsole("are you doing this task (" + finalTask.getTitle() + ") now ? (yes/no) : ", "");
			
			synchronized (lock) {
	            try {
	                while (inputHolder[0].isEmpty()) {
	                    lock.wait(); 
	                }
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	                AgendaWindow.appendToConsole("Thread interrupted. Exiting...", "\n \n");	                
	            }
	        }
			
			input2 = inputHolder[0];	
			inputHolder[0] = "";
			
			if (input2.equals("yes") || input2.equals("no")) {
				valideAnswer = true;
				AgendaWindow.appendToConsole(input2, "\n \n");
				AgendaWindow.interactiveInputField.setText("");
			} else {
				AgendaWindow.appendToConsole(input2, "\n \n");
				AgendaWindow.appendToConsole("The answer must be yes or no !", "\n \n");
			}
	    }
		
		if (input2.equals("yes")) {
			agenda.getTaskByTitle(finalTask.getTitle()).setState(States.DONE);	        		
		}
	}
	
	// Check the undone tasks before this time 
	public static List<Task> checkUndoneTasks(LocalTime time, Agenda agenda) {
		List<Task> tasks = agenda.getTasks();
		List<Task> undoneTasks = new ArrayList<Task>();
		for (Task task: tasks) {
			if (task.getStartTime().compareTo(time) <= 0 && task.getState() == States.UNDONE) {
				undoneTasks.add(task);
			}
		}
		return undoneTasks;
	}	
	
	public static boolean isBetween(LocalTime time, LocalTime start, LocalTime end) {
		return time.compareTo(start) >= 0 && time.compareTo(end) <= 0; 
	}
	
	public static Task compare2daysTask(Agenda agenda, Task task, LocalTime time, int index) {
		if (task.getStartTime().isAfter(task.getEndTime())) {
			if (Utils.isBetween(time, task.getStartTime(), LocalTime.of(23,59,59))
				|| Utils.isBetween(time, LocalTime.of(0,0,1), task.getEndTime())) {
				return agenda.getTasks().get(index-2);
			}
		} 
		return task;
	}

}