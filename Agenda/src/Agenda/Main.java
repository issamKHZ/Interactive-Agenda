package Agenda;

import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.concurrent.*;

import Enumerations.States;
import Enumerations.Time;

public class Main {	
	
	public static int speedFactor = 1;
	
	public static void main(String[] args) {        
        Agenda agenda = initializeFullAgenda();               
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        try {        	            
            Future<Void> future = AgendaHandler.openAgenda(agenda);
            future.get();
            scheduler.scheduleAtFixedRate(() -> {
                AgendaHandler.taskReminder(agenda, Time.START);	            
                AgendaHandler.taskReminder(agenda, Time.END);	            
            }, 0, 2, TimeUnit.SECONDS);
            AgendaHandler.timeAsker(agenda);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }                              
    }
	
	
	public static LocalTime formatTime(String time) {
		time = time.replace('h', ':');
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        return LocalTime.parse(time, formatter);
	}
		
	public static Agenda initializePartiel() {
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(new Task("Breakfast time", LocalTime.of(8, 00), LocalTime.of(8, 30), States.UNDONE, false, false));
		tasks.add(new Task("Take medicine", LocalTime.of(10, 00), LocalTime.of(10, 15), States.UNDONE, false, false));
		tasks.add(new Task("Watch TV", LocalTime.of(12, 30), LocalTime.of(1, 00), States.UNDONE, false, false));
		Agenda agenda = new Agenda(tasks);
		return agenda;
	}
	
	public static Agenda initializeFullAgenda() {
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(new Task("Breakfast time", LocalTime.of(8, 00), LocalTime.of(9, 30), States.UNDONE, false, false));
		tasks.add(new Task("Take medicine", LocalTime.of(10, 00), LocalTime.of(10, 15), States.UNDONE, false, false));
		tasks.add(new Task("Watch TV", LocalTime.of(10, 30), LocalTime.of(12, 00), States.UNDONE, false, false));
		tasks.add(new Task("Lunch time", LocalTime.of(12, 30), LocalTime.of(13, 30), States.UNDONE, false, false));
		tasks.add(new Task("take a nap", LocalTime.of(14, 00), LocalTime.of(15, 30), States.UNDONE, false, false));
		tasks.add(new Task("solve puzzles", LocalTime.of(16, 30), LocalTime.of(17, 30), States.UNDONE, false, false));
		tasks.add(new Task("Dinner time", LocalTime.of(22, 05), LocalTime.of(00, 45), States.UNDONE, false, false));
		Agenda agenda = new Agenda(tasks);
		return agenda;
	}
}
