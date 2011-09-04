package com.tnicoll.apps.bookworm;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import javax.swing.*;



/**
 * Main Class for creating
 *
 */
public class BookWormMain extends javax.swing.JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2381944488789900157L;
	private JMenuItem helpMenuItem;
	private JPanel menuPanel;
	private JMenuBar jMenuBar1;
	private JMenu jMenu5;
	private JPanel mainPanel;
	private JMenuItem exitMenuItem;
	private JSeparator jSeparator2;
	private JMenuItem closeFileMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem openFileMenuItem;
	private JMenuItem newFileMenuItem;
	private JMenu jMenu3;
	private JTextArea console;
	private JFileChooser fc = new JFileChooser();
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				BookWormMain inst = new BookWormMain();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});

	}
	
	public BookWormMain() {
		super();
		initGUI();
	}

	private void initGUI() {
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
				    System.exit(0);
				  }
				});
		menuPanel = new JPanel();
		getContentPane().add(menuPanel);
		FlowLayout menuPanelLayout = new FlowLayout();
		menuPanelLayout.setAlignment(FlowLayout.RIGHT);
		menuPanel.setLayout(menuPanelLayout);
		menuPanel.setPreferredSize(new java.awt.Dimension(300, 400));

		/*Add Panel */		
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.WEST);
		mainPanel.setPreferredSize(new java.awt.Dimension(300, 400));
		
		//Add console
		console = new JTextArea(100,100);
		console.setVisible(true);
		console.setEditable(isDisplayable());
		JScrollPane consolePane = new JScrollPane(console);
		consolePane.setVisible(true);
		menuPanel.add(consolePane);
		
		setSize(600, 600);
	
		jMenuBar1 = new JMenuBar();
		setJMenuBar(jMenuBar1);
		
			jMenu3 = new JMenu();
			jMenuBar1.add(jMenu3);
			jMenu3.setText("File");
			{
				newFileMenuItem = new JMenuItem();
				jMenu3.add(newFileMenuItem);
				newFileMenuItem.setText("New");
			}
			{
				openFileMenuItem = new JMenuItem();
				openFileMenuItem.addActionListener(new OpenListener());
				jMenu3.add(openFileMenuItem);
				openFileMenuItem.setText("Open");
			}
			{
				saveMenuItem = new JMenuItem();
				jMenu3.add(saveMenuItem);
				saveMenuItem.setText("Save");
			}
			{
				saveAsMenuItem = new JMenuItem();
				jMenu3.add(saveAsMenuItem);
				saveAsMenuItem.setText("Save As ...");
			}
			{
				closeFileMenuItem = new JMenuItem();
				jMenu3.add(closeFileMenuItem);
				closeFileMenuItem.setText("Close");
			}
			{
				jSeparator2 = new JSeparator();
				jMenu3.add(jSeparator2);
			}
			{
				exitMenuItem = new JMenuItem();
				jMenu3.add(exitMenuItem);
				exitMenuItem.setText("Exit");
			}
		
			jMenu5 = new JMenu();
			jMenuBar1.add(jMenu5);
			jMenu5.setText("Help");
			{
				helpMenuItem = new JMenuItem();
				jMenu5.add(helpMenuItem);
				helpMenuItem.setText("Help");
			}
		
	}
	class OpenListener implements ActionListener {

	    public void actionPerformed(ActionEvent e) {
	    	if (e.getSource() == openFileMenuItem) {
	            int returnVal = fc.showOpenDialog(BookWormMain.this);

	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();
	                //Open File
	                
	            } else {
	                //Do something else
	            }
	       }
	    }
	  }
}
