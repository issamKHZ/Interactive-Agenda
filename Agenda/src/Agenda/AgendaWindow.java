package Agenda;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import javax.swing.border.TitledBorder;
import javax.swing.*;

import Enumerations.States;

public class AgendaWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private static JTextArea interactiveConsoleArea;
    public static JTextField interactiveInputField;
    private static JButton submitButton;
    
    public static JLabel lamp;
    public static JTextField notification;
    
    public static JTextField lastNameValue;
    public static JTextField lastStatusValue;
    public static JTextField lastTimeValue;
    
    public static JTextField nextNameValue;
    public static JTextField nextStatusValue;
    public static JTextField nextTimeValue;
    
    public static JTextField currentNameValue;
    public static JTextField currentStatusValue;
    public static JTextField currentTimeValue;
    
    private static JPanel taskListPanel;       
    

	public AgendaWindow (Agenda agenda) {
		super();
		build(agenda);
	}

	private void build(Agenda agenda) {
		setTitle("Interactive Agenda"); 
		setSize(1000,500); 
		setLocationRelativeTo(null); 		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(buildMainPage(agenda));
	}
	
	public static JPanel buildMainPage(Agenda agenda) {
        JPanel panel = new JPanel(new GridBagLayout());       

        JPanel header;
        JPanel content;
        JPanel agendaCustom;

        // // Add header panel
        GridBagConstraints panelLayout = new GridBagConstraints();
        panelLayout.fill = GridBagConstraints.BOTH;
        panelLayout.weightx = 1;
        panelLayout.weighty = 0.1;         
        panelLayout.gridy = 0;      
        
        header = BuildHeaderPanel();
        panel.add(header, panelLayout);                

        
        
        // // Add content panel
        panelLayout.weighty = 0.8;         
        panelLayout.gridy = 1;

        content = BuildContentPanel();
        panel.add(content, panelLayout);

        
        
        // // Add AgendaCustom panel
        panelLayout.weighty = 0.1;
        panelLayout.gridy = 2;

        agendaCustom = BuildAgendaCustomPanel(agenda);
        panel.add(agendaCustom, panelLayout);

        return panel;
    }
	
	private static JPanel BuildHeaderPanel() {
		JPanel header = new JPanel(new GridBagLayout());
        header.setBackground(Color.WHITE);
        
        JLabel headerLabel = new JLabel("Welcome to your interactive Agenda");
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER); 

    	// customize app title in the header
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0; 
        labelConstraints.gridy = 0; 
        labelConstraints.weightx = 1; 
        labelConstraints.weighty = 1; 
        labelConstraints.anchor = GridBagConstraints.CENTER; 

        header.add(headerLabel, labelConstraints);
        
        return header;
	}
	
	private static JPanel BuildContentPanel() {
		JPanel content = new JPanel(new GridBagLayout());
		JPanel timeAskerPart;
		JPanel taskReminderPart;
		JPanel firstPanel = new JPanel(new GridBagLayout());;		
		JPanel speedDropdownPanel;
		
		content.setBackground(Color.GRAY);
		firstPanel.setBackground(Color.GRAY);	
		
		GridBagConstraints contentConstraints = new GridBagConstraints();
		contentConstraints.fill = GridBagConstraints.BOTH;
		contentConstraints.gridx = 0;
		contentConstraints.weighty = 1;
		contentConstraints.weightx = 0.6;
				
		timeAskerPart = BuildTimeAskerPanel();
		firstPanel.add(timeAskerPart, contentConstraints);					
		
		contentConstraints.gridx = 1;
		contentConstraints.weightx = 0.4;
		
		taskReminderPart = BuildTaskReminderPanel();
		firstPanel.add(taskReminderPart, contentConstraints);
		
		GridBagConstraints firstPanelC = new GridBagConstraints();
		firstPanelC.fill = GridBagConstraints.BOTH;
		firstPanelC.gridy = 0;
		firstPanelC.weightx = 1;
		firstPanelC.weighty = 0.9;
			    	    	   
	    content.add(firstPanel, firstPanelC);
	    
	    firstPanelC.gridy = 1;
	    firstPanelC.weighty = 0.1;
	    speedDropdownPanel = buildTimerPanel();
	    content.add(speedDropdownPanel, firstPanelC);
		
		return content;
	}
	
	private static JPanel buildTimerPanel() {
	    JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    JLabel label = new JLabel("Clock");

	    JTextField horloge = new JTextField();
	    
	    horloge.setPreferredSize(new Dimension(60, horloge.getPreferredSize().height + 10));	    
	    horloge.setFont(new Font("Arial", Font.BOLD, 18));
	    horloge.setHorizontalAlignment(JTextField.CENTER);
	    
	    Timer timer = new Timer(1000, e -> { 
            String currentTime = new SimpleDateFormat("HH:mm").format(new Date());
            horloge.setText(currentTime);
        });
	    
	    timer.start();
	    
	    panel.add(label);
	    panel.add(horloge);
	    
	    return panel;
	}

	private static JPanel BuildTimeAskerPanel() {
	    JPanel panel = new JPanel(new GridBagLayout());
	    
	    // Outputs
	    GridBagConstraints panelConstraints = new GridBagConstraints();
	    panelConstraints.fill = GridBagConstraints.BOTH;
	    panelConstraints.weightx = 1;
	    panelConstraints.weighty = 0.7;
	    panelConstraints.gridy = 0;
	    
	    interactiveConsoleArea = new JTextArea();
	    interactiveConsoleArea.setEditable(false);	    
	    interactiveConsoleArea.setBackground(Color.BLACK);
	    interactiveConsoleArea.setForeground(Color.GREEN);
	    interactiveConsoleArea.setSize(new Dimension(10, 50));
	    JScrollPane scrollPanel = new JScrollPane(interactiveConsoleArea);
	    scrollPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    panel.add(scrollPanel, panelConstraints);
	    
	    // Input 
	    interactiveInputField = new JTextField();
	    interactiveInputField.setPreferredSize(new Dimension(200, 30)); 
	    
	    submitButton = new JButton("Send");
	    submitButton.setPreferredSize(new Dimension(80, 30)); 
	    
	    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    inputPanel.add(interactiveInputField);
	    inputPanel.add(submitButton);
	    
	    panelConstraints.gridy = 1;
	    panelConstraints.weighty = 0.1;
	    panel.add(inputPanel, panelConstraints);
	    
	    return panel;
	}
	
	private static JPanel BuildAgendaCustomPanel(Agenda agenda) {
		JPanel agendaCustom = new JPanel(new GridBagLayout());               
        agendaCustom.setBackground(Color.WHITE);            
        
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0; 
        buttonConstraints.gridy = 0; 
        buttonConstraints.weightx = 1; 
        buttonConstraints.weighty = 1; 
        buttonConstraints.anchor = GridBagConstraints.CENTER;
        
        JButton agendaOverlayButton = new JButton("Open Agenda");
        agendaOverlayButton.addActionListener(e -> openTaskManager(agenda));
        agendaOverlayButton.setHorizontalAlignment(SwingConstants.CENTER);
        
        agendaCustom.add(agendaOverlayButton, buttonConstraints);
        return agendaCustom;
	}
	
	private static JPanel BuildTaskReminderPanel() {
		
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// last/next Panel
		JPanel lastNextPanel = new JPanel();
		lastNextPanel.setLayout(new GridLayout(1, 2, 10, 10));			

		// Init fields 
		lastNameValue = new JTextField();
		lastStatusValue = new JTextField();
		lastTimeValue = new JTextField();
		
		nextNameValue = new JTextField();
		nextStatusValue = new JTextField();
		nextTimeValue = new JTextField();
		
		// Init last and next panels
		JPanel lastTaskPanel = BuildLastTaskPanel("LAST TASK");

		JPanel endLampPanel = BuildNextTaskPanel("NEXT TASK");			

		lastNextPanel.add(lastTaskPanel);
		lastNextPanel.add(endLampPanel);

		// Task Panel
		JPanel taskPanel = BuildCurrentTaskPanel();
				

		content.add(lastNextPanel); 
		content.add(Box.createVerticalStrut(20)); 
		content.add(taskPanel); 
		content.add(Box.createVerticalGlue()); 
		return content;				

	}
	
	private static JPanel BuildLastTaskPanel(String title) {		

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		
		TitledBorder border = BorderFactory.createTitledBorder(title);			
		border.setTitleFont(new Font("Arial", Font.BOLD, 14));
		border.setTitleColor(Color.BLUE);
        
		panel.setBorder(border);
        
        JLabel nameLabel = new JLabel("    Name :");
        lastNameValue = new JTextField("", 10);        
        lastNameValue.setEditable(false);
        lastNameValue.setBackground(Color.white);
        lastNameValue.setFont(new Font("Arial", Font.BOLD, 13));
        lastNameValue.setHorizontalAlignment(JTextField.CENTER);
        
        JLabel statusLabel = new JLabel("    Status :");
        lastStatusValue = new JTextField("", 10);
        lastStatusValue.setEditable(false);
        lastStatusValue.setBackground(Color.white);
        lastStatusValue.setFont(new Font("Arial", Font.BOLD, 13));
        lastStatusValue.setHorizontalAlignment(JTextField.CENTER);

        /*JLabel timeLabel = new JLabel("    End time :");
        lastTimeValue = new JTextField("", 10);
        lastTimeValue.setEditable(false);*/

        panel.add(nameLabel);
        panel.add(lastNameValue);

        panel.add(statusLabel);
        panel.add(lastStatusValue);

        /*panel.add(timeLabel);
        panel.add(lastTimeValue);*/
        
        return panel;
	}
	
	private static JPanel BuildNextTaskPanel(String title) {		

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		
		TitledBorder border = BorderFactory.createTitledBorder(title);			
		border.setTitleFont(new Font("Arial", Font.BOLD, 14));
		border.setTitleColor(Color.BLUE);
        
		panel.setBorder(border);

        JLabel nameLabel = new JLabel("    Name :");
        nextNameValue = new JTextField("", 10);        
        nextNameValue.setEditable(false);
        nextNameValue.setBackground(Color.white);
        nextNameValue.setFont(new Font("Arial", Font.BOLD, 13));
        nextNameValue.setHorizontalAlignment(JTextField.CENTER);

        JLabel statusLabel = new JLabel("    Status :");
        nextStatusValue = new JTextField("", 10);
        nextStatusValue.setEditable(false);
        nextStatusValue.setBackground(Color.white);
        nextStatusValue.setFont(new Font("Arial", Font.BOLD, 13));
        nextStatusValue.setHorizontalAlignment(JTextField.CENTER);

        JLabel timeLabel = new JLabel("    Start time:");
        nextTimeValue = new JTextField("", 10);
        nextTimeValue.setEditable(false);
        nextTimeValue.setBackground(Color.white);
        nextTimeValue.setFont(new Font("Arial", Font.BOLD, 13));
        nextTimeValue.setHorizontalAlignment(JTextField.CENTER);

        panel.add(nameLabel);
        panel.add(nextNameValue);

        /*panel.add(statusLabel);
        panel.add(nextStatusValue);*/

        panel.add(timeLabel);
        panel.add(nextTimeValue);
        
        return panel;
	}
	
	private static JPanel BuildCurrentTaskPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));			
		
		TitledBorder border = BorderFactory.createTitledBorder("CURRENT TASK");			
		border.setTitleFont(new Font("Arial", Font.BOLD, 14));
		border.setTitleColor(Color.BLUE);
        
		mainPanel.setBorder(border);
		
		// Create the panel for current task informations
		JPanel CurrentInfosPanel = new JPanel();
		CurrentInfosPanel.setLayout(new GridLayout(3, 2));
		
		// Create the field for task name
		JLabel nameLabel = new JLabel("    Name :");
        currentNameValue = new JTextField("", 10);        
        currentNameValue.setEditable(false);
        currentNameValue.setBackground(Color.white);
        currentNameValue.setFont(new Font("Arial", Font.BOLD, 13));
        currentNameValue.setHorizontalAlignment(JTextField.CENTER);

        // Create the field for task state
        JLabel statusLabel = new JLabel("    Status :");
        currentStatusValue = new JTextField("", 10);
        currentStatusValue.setEditable(false);
        currentStatusValue.setBackground(Color.white);
        currentStatusValue.setFont(new Font("Arial", Font.BOLD, 13));
        currentStatusValue.setHorizontalAlignment(JTextField.CENTER);

        // 	Create the field for task start time
        JLabel timeLabel = new JLabel("    Start time:");
        currentTimeValue = new JTextField("", 10);
        currentTimeValue.setEditable(false);
        currentTimeValue.setBackground(Color.white);
        currentTimeValue.setFont(new Font("Arial", Font.BOLD, 13));
        currentTimeValue.setHorizontalAlignment(JTextField.CENTER);
        
        CurrentInfosPanel.add(nameLabel);
        CurrentInfosPanel.add(currentNameValue);
        
        CurrentInfosPanel.add(statusLabel);
        CurrentInfosPanel.add(currentStatusValue);
        
        CurrentInfosPanel.add(timeLabel);
        CurrentInfosPanel.add(currentTimeValue);
        
        
        // Create the panel for current task start notification
        JPanel notifPanel = new JPanel();
        notifPanel.setLayout(new BoxLayout(notifPanel, BoxLayout.Y_AXIS));
        notifPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		lamp = new JLabel();
		lamp.setOpaque(true);
		lamp.setBackground(Color.GRAY);
		lamp.setPreferredSize(new Dimension(70, 70));
		lamp.setMaximumSize(new Dimension(30, 30));
		lamp.setAlignmentX(Component.CENTER_ALIGNMENT);		

		notification = new JTextField("", 10);
		notification.setHorizontalAlignment(SwingConstants.CENTER);
		notification.setEditable(false);
		notification.setMaximumSize(new Dimension(200, 20));
		notification.setAlignmentX(Component.CENTER_ALIGNMENT);

		notifPanel.add(lamp);
		notifPanel.add(Box.createVerticalStrut(10)); 		
		notifPanel.add(notification);
                
        mainPanel.add(CurrentInfosPanel);
        mainPanel.add(notifPanel);
        
		return mainPanel;
	}
	
	private static void openTaskManager(Agenda agenda) {
        JFrame taskManagerFrame = new JFrame("Tasks management");
        taskManagerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        taskManagerFrame.setSize(800, 400);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Tasks list
        taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));
        taskListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        fillTaskListPanel(agenda);

        JScrollPane scrollPane = new JScrollPane(taskListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
        

        // Edit/Creation forms
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel taskNameLabel = new JLabel("Task title :");
        JTextField taskNameField = new JTextField(20);

        JLabel taskStartTimeLabel = new JLabel("Task start time :");
        JTextField taskStartTimeField = new JTextField(10);
        
        JLabel taskEndTimeLabel = new JLabel("Task end time :");
        JTextField taskEndTimeField = new JTextField(10);

        JButton saveButton = new JButton("Add new Task");
        
        saveButton.addActionListener(e -> {
        	agenda.addTask(new Task(taskNameField.getText(), 
        							LocalTime.parse(taskStartTimeField.getText()), 
        							LocalTime.parse(taskEndTimeField.getText()),
        							States.UNDONE, false, false));
        	refreshTaskList(agenda);
        });

        formPanel.add(taskNameLabel);
        formPanel.add(taskNameField);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(taskStartTimeLabel);
        formPanel.add(taskStartTimeField);
        formPanel.add(taskEndTimeLabel);
        formPanel.add(taskEndTimeField);
        formPanel.add(Box.createVerticalStrut(20)); 
        formPanel.add(saveButton);

        // Add main panels
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(formPanel, BorderLayout.EAST);

        // Add content panel to the frame
        taskManagerFrame.add(mainPanel);
        taskManagerFrame.setVisible(true);
	}
	
	private static void openEditDialog(Agenda agenda, Task task) {
	    JDialog editDialog = new JDialog();
	    editDialog.setTitle("Edit task");
	    editDialog.setModal(true);
	    editDialog.setSize(400, 300);
	    editDialog.setLayout(new GridLayout(4, 2, 10, 10));
	    editDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    	   
	    JLabel titleLabel = new JLabel("Task title :");
	    JTextField titleField = new JTextField(task.getTitle());
	    
	    JLabel startLabel = new JLabel("Start time :");
	    JTextField startField = new JTextField(task.getStartTime().toString());
	    
	    JLabel endLabel = new JLabel("End time :");
	    JTextField endField = new JTextField(task.getEndTime().toString());
	    
	    JButton saveButton = new JButton("Save");
	    
	    // Action to save modif
	    saveButton.addActionListener(e -> {
	        agenda.setTitleByName(task.getTitle(), titleField.getText());
	        agenda.setStartTimeByName(titleField.getText(), LocalTime.parse(startField.getText())); 
	        agenda.setEndTimeByName(titleField.getText(), LocalTime.parse(endField.getText()));
	        agenda.setSTCheckedByName(titleField.getText());
	        agenda.setETCheckedByName(titleField.getText());
	        editDialog.dispose(); 
	        refreshTaskList(agenda);
	    });
	    
	    
	    editDialog.add(titleLabel);
	    editDialog.add(titleField);
	    editDialog.add(startLabel);
	    editDialog.add(startField);
	    editDialog.add(endLabel);
	    editDialog.add(endField);
	    editDialog.add(new JLabel()); 
	    editDialog.add(saveButton);
	    
	    editDialog.setLocationRelativeTo(null); 
	    editDialog.setVisible(true);
	}

	private static void refreshTaskList(Agenda agenda) {
		agenda.sortAgenda();
	    taskListPanel.removeAll(); 
	    fillTaskListPanel(agenda);
	    taskListPanel.revalidate();
	    taskListPanel.repaint();
	}
	
	
	private static void fillTaskListPanel(Agenda agenda) {
		JLabel addTaskLabel = new JLabel("Tasks list");		
        taskListPanel.add(addTaskLabel);
        taskListPanel.add(Box.createVerticalStrut(10)); 

        for (Task task: agenda.getTasks()) {
            JPanel taskPanel = new JPanel();
            JPanel buttonsPanel = new JPanel(new FlowLayout());
            taskPanel.setLayout(new BorderLayout());
            JLabel taskLabel = new JLabel(task.getTitle() + " (" + task.getStartTime() + " - " + task.getEndTime() + ")");
            JButton editTaskButton = new JButton("Edit");
            JButton deleteTaskButton = new JButton("Delete");
            editTaskButton.addActionListener(e -> openEditDialog(agenda, task));
            deleteTaskButton.addActionListener(e -> {
            	
            });
            deleteTaskButton.addActionListener(e -> {
    	        agenda.deleteTask(task);
    	        refreshTaskList(agenda);
    	    });
            buttonsPanel.add(editTaskButton);
            buttonsPanel.add(deleteTaskButton);
            taskPanel.add(taskLabel, BorderLayout.CENTER);
            taskPanel.add(buttonsPanel, BorderLayout.EAST);
            taskPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            taskListPanel.add(taskPanel);
            taskListPanel.add(Box.createVerticalStrut(5)); 
        }
	}
	public static void appendToConsole(String message, String jump) {
        SwingUtilities.invokeLater(() -> {
        	interactiveConsoleArea.append(message + jump);
        });
    }
	
	public static JButton getSubmitButton() {		
		return submitButton;
	}
}
