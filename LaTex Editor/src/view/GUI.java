package view;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

import control.*;

public class GUI extends JFrame 
{                 
	private JButton CreateButton;
	private JButton CommitButton;
	private JButton LoadButton;
	private JButton SaveButton;
	private JButton RollbackButton;
	private JRadioButton DisableVersionTrackingButton;
	private JComboBox<String> TemplateBox;
	private JComboBox<String> VersionBox;
	private JComboBox<String> CommandBox;
	private JScrollPane ScrollPane;
	private JTextArea TextArea;
	private JFileChooser fileChooser;
	private static Controller MainController;
     
	private static final long serialVersionUID = 1L;
	public GUI() {
		initComponents();
	}	

	public JTextArea getTextArea() {
		return TextArea;
	}
	public JComboBox<String> getVersionBox() {
		return VersionBox;
	}
	
	public JComboBox<String> getTemplateBox() {
		return TemplateBox;
	}
	
	public JFileChooser getFileChooser() {
		return fileChooser;
	}
	
	public JButton getRollbackButton(){
		return RollbackButton;
	}
	
	public void ToggleCommands(boolean enabled) {
		VersionBox.setEnabled(enabled);
        CommandBox.setEnabled(enabled);
        CommitButton.setEnabled(enabled);
        RollbackButton.setEnabled(enabled);
        DisableVersionTrackingButton.setEnabled(enabled);
        SaveButton.setEnabled(enabled);
	}
	
	public void InitCommands() {
		MainController.CommitInit();
		MainController.ChangeVersionInit();
		MainController.DisableInit();
		MainController.RollbackInit();
		MainController.SaveInit();
	}
	                         
    private void initComponents() 
    {
        ScrollPane = new JScrollPane();
        TextArea = new JTextArea();
        CreateButton = new JButton();
        TemplateBox = new JComboBox<String>();
        LoadButton = new JButton();
        SaveButton = new JButton();
        VersionBox = new JComboBox<String>();
        CommandBox = new JComboBox<String>();
        RollbackButton = new JButton();
        CommitButton = new JButton();
        DisableVersionTrackingButton = new JRadioButton("Disable/Enable");
        fileChooser = new JFileChooser();
        
        ToggleCommands(false);
        
        Font textfont = new Font("SansSerif", Font.PLAIN, 18);
        Font uifont =  new Font("SansSerif", Font.PLAIN, 14);
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("TexEditor");
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        TextArea.setColumns(20);
        TextArea.setRows(5);
        TextArea.setFont(textfont);
        ScrollPane.setViewportView(TextArea);
        
        CommandBox.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		boolean canuse = MainController.canUse(TemplateBox.getSelectedItem().toString(), CommandBox.getSelectedItem().toString());
        		if(canuse)
        			MainController.enact(CommandBox.getSelectedItem().toString());
        		else
        			warning("This command is not available for this template!");
        	}
        });
        
        CreateButton.setText("CREATE");
        CreateButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Creation(TemplateBox.getSelectedItem().toString());
        	}
        });

        TemplateBox.setModel(new DefaultComboBoxModel<String>(new String[] { "no template","article", "letter","book","report" }));
        TemplateBox.setToolTipText("");
        TemplateBox.setFont(uifont);

        LoadButton.setText("LOAD");
        LoadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) 
            {
            	fileChooser.setDialogTitle("Load");
        		int returnVal = fileChooser.showOpenDialog(getParent());
        		if (returnVal == JFileChooser.APPROVE_OPTION)
        		{
        			InitCommands();
        			MainController.enact("load");
    				ToggleCommands(true);
    				DisableVersionTrackingButton.setSelected(false);
                }
            }
        });
        
        VersionBox.setModel(new DefaultComboBoxModel<String>(new String[] { "volatile", "stable" }));
        VersionBox.setFont(uifont);
        VersionBox.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		if(MainController.canUse(VersionBox.getSelectedItem().toString(), "changeVersion")){
        			MainController.enact("changeVersion");
        		}
        	}
        });
        
        CommandBox.setModel(new DefaultComboBoxModel<String>(new String[] {"chapter", "section", "subsection" ,"subsubsection", "item list" , "enum list" , "table" ,"figure"}));
        CommandBox.setFont(uifont);
        
        RollbackButton.setText("ROLLBACK");
        RollbackButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		if(!MainController.canUse("", "rollback")){
        			warning("Version Tracking is not enabled");
        			return;
        		}
        		MainController.enact("rollback");
        	}
        });
        
        CommitButton.setText("COMMIT");
        CommitButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e) {
        		MainController.enact("commit");
        	}
        });

        SaveButton.setText("SAVE");
        SaveButton.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		fileChooser.setDialogTitle("Save");   
        		//MainController.enact("save");
        		//fileChooser.setCurrentDirectory(new File("C:\\Users\\me\\Desktop"));
        		int returnVal = fileChooser.showOpenDialog(getParent());
        		if (returnVal == JFileChooser.APPROVE_OPTION){
        			MainController.enact("save");
                }
        	}
        });
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ScrollPane)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(LoadButton, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                    .addComponent(CreateButton, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                    .addComponent(SaveButton, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 208, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(CommandBox, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
                    .addComponent(TemplateBox, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
                    .addComponent(VersionBox, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
                .addGap(188, 188, 188)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(RollbackButton, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
                    .addComponent(CommitButton, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(CreateButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(RollbackButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                    .addComponent(TemplateBox))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(SaveButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addComponent(VersionBox, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addComponent(CommitButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(LoadButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CommandBox, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(ScrollPane, GroupLayout.PREFERRED_SIZE, 654, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        
        add(DisableVersionTrackingButton);
        DisableVersionTrackingButton.setBounds(760,115,125,50);
        DisableVersionTrackingButton.setVisible(true);
        DisableVersionTrackingButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        DisableVersionTrackingButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		if(DisableVersionTrackingButton.isSelected())
        		{
        			MainController.enact("disable");
        			VersionBox.setEnabled(false);
        			CommitButton.setEnabled(false);
        		}
        		else
        		{
        			MainController.enact("enable");
        			VersionBox.setEnabled(true);
        			CommitButton.setEnabled(true);
        		}
        	}
        });
        pack();
    }

    public static void main(String args[]) 
    {
    	//Change Nimbus for alternative style
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() 
            {
            	GUI MainWindow = new GUI();
            	MainWindow.setVisible(true);
                MainController = new Controller(MainWindow);
            }
        });
    }
    
    public void warning(String message)
    {
    	JFrame WarningFrame = new JFrame("Warning!");
    	WarningFrame.setBounds(100, 150, 450, 300);
    	WarningFrame.setResizable(false);
    	WarningFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	WarningFrame.getContentPane().setLayout(null);
		
		JLabel lblThisCommandIs = new JLabel(message);
		lblThisCommandIs.setBounds(0, -25, 450, 252);
		lblThisCommandIs.setFont(new Font("SansSerif", Font.BOLD, 14));
		lblThisCommandIs.setHorizontalAlignment(SwingConstants.CENTER);
		WarningFrame.getContentPane().add(lblThisCommandIs);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		WarningFrame.dispose();
        	}
        });
		btnOk.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnOk.setBounds(165, 180, 110, 30);
		WarningFrame.getContentPane().add(btnOk);
		WarningFrame.setVisible(true);
    }
                     
    private JFrame CreationFrame;
    private JButton SaveChangesButton;
    private JButton CancelButton;
    private JTextField AuthorField;
    private JTextField CopyrightField;
    private JTextField VersionIDField;
    private JTextField DateField;
    
    private void Creation(String template) 
    {
    	CreationFrame = new JFrame("Creation Info");
    	CreationFrame.setResizable(false);
    	CreationFrame.setBounds(100, 100, 500, 500);
    	CreationFrame.setVisible(true);
    	CreationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	CreationFrame.getContentPane().setLayout(null);
		
    	Font textfield = new Font("SansSerif", Font.PLAIN, 16);
    	
    	AuthorField = new JTextField();
    	AuthorField.setFont(textfield);
    	AuthorField.setText("Author");
    	AuthorField.setBounds(150, 20, 180, 45);
    	CreationFrame.getContentPane().add(AuthorField);
		AuthorField.setColumns(10);
		
		DateField = new JTextField("Date");
		DateField.setFont(textfield);
		DateField.setBounds(150, 100, 180, 45);
		CreationFrame.getContentPane().add(DateField);
		
		CopyrightField = new JTextField();
		CopyrightField.setFont(textfield);
		CopyrightField.setText("Copyrights");
		CopyrightField.setBounds(150, 180, 180, 45);
		CreationFrame.getContentPane().add(CopyrightField);
		CopyrightField.setColumns(10);
		
		VersionIDField = new JTextField();
		VersionIDField.setFont(textfield);
		VersionIDField.setText("VersionID");
		VersionIDField.setBounds(150, 260, 180, 45);
		CreationFrame.getContentPane().add(VersionIDField);
		VersionIDField.setColumns(10);
		
		SaveChangesButton = new JButton("Save Changes");
		SaveChangesButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				MainController.CreateInit(TemplateBox.getSelectedItem().toString());
				MainController.enact("create");
        		InitCommands();
				ToggleCommands(true);
				DisableVersionTrackingButton.setSelected(false);
				CreationFrame.dispose();
			}
		});
		SaveChangesButton.setFont(textfield);
		SaveChangesButton.setBounds(40, 340, 180, 45);
		CreationFrame.getContentPane().add(SaveChangesButton);
		
		CancelButton = new JButton("Cancel");
		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreationFrame.dispose();
			}
		});
		CancelButton.setFont(textfield);
		CancelButton.setBounds(260, 340, 180, 45);
		CreationFrame.getContentPane().add(CancelButton);
    }
}
