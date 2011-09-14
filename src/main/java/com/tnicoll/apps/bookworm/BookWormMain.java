package com.tnicoll.apps.bookworm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.*;
import org.apache.tika.sax.BodyContentHandler;

import com.tnicoll.apps.bookworm.model.Book;
import com.tnicoll.apps.bookworm.model.MutableInt;
import com.tnicoll.apps.bookworm.model.Word;



/**
 * Main Class
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
	private JMenuBar toolbar;
	
	private JPanel optionPanel;
	private JMenuItem exitMenuItem;
	private JSeparator jSeparator2;
	private JMenuItem closeFileMenuItem;
	private JMenuItem openFileMenuItem;
	private JMenu fileMenu;
	private JMenu helpMenu;
	
	private JTextArea console;
	private JFileChooser fc = new JFileChooser();
	
	private JTextField filterBox;
	private JPanel searchPanel;
	private JScrollPane wordScroll;
	private JTable wordTable;
	private DefaultTableModel model;
	
	private int width;
	private int height;
	
	
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
		GraphicsConfiguration gc = getGraphicsConfiguration( ); 
		Rectangle screenRect = gc.getBounds( ); // screen dimensions  
		Toolkit tk = Toolkit.getDefaultToolkit( ); 
		Insets desktopInsets = tk.getScreenInsets(gc);  
		Insets frameInsets = getInsets( ); // only works after pack( )  
		width = screenRect.width - (desktopInsets.left + desktopInsets.right) - 
				(frameInsets.left + frameInsets.right); 
		height = screenRect.height - (desktopInsets.top + desktopInsets.bottom) - 
				(frameInsets.top + frameInsets.bottom);
		this.setSize(width, height);
		this.setResizable(true);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
				    System.exit(0);
				  }
				});
				
		//Add filter box
		searchPanel = new JPanel();
		searchPanel.setPreferredSize(new java.awt.Dimension(width/2, height));
		JLabel filterLabel = new JLabel("Filter: ");
		filterBox = new JTextField();
		filterBox.setEditable(true);
		filterBox.setColumns(50);
		filterBox.setSize((width/2)-50, 10);
		searchPanel.add(filterLabel, BorderLayout.NORTH);
		searchPanel.add(filterBox, BorderLayout.NORTH);
		getContentPane().add(searchPanel, BorderLayout.WEST);
		
		///Add Word Table
		Object []columnNames = {"Word", "Count"};
		Object [][] data = {{"",new Integer(0)}};;
		model = new BookModel(data, columnNames);
		
		wordTable = new JTable(model);
		wordTable.setAutoCreateRowSorter(true);
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		wordTable.setRowSorter(sorter);
		filterBox.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filterText();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				filterText();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				filterText();
			}
		      public void filterText() {
		          String text = filterBox.getText();
		          if (text.length() == 0) {
		            sorter.setRowFilter(null);
		          } else {
		            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
		          }
		        }
			
		      });
		wordScroll = new JScrollPane(wordTable);
		wordScroll.setPreferredSize(new java.awt.Dimension(width/2, height-90));
		searchPanel.add(wordScroll, BorderLayout.SOUTH);
		
		
		///Add Right Hand Panel
		menuPanel = new JPanel();
		getContentPane().add(menuPanel, BorderLayout.EAST);
		FlowLayout menuPanelLayout = new FlowLayout();
		menuPanelLayout.setAlignment(FlowLayout.RIGHT);
		menuPanel.setLayout(menuPanelLayout);
		menuPanel.setPreferredSize(new java.awt.Dimension(width/2, height));
		
		//Add Right Hand Top Option Panel
		optionPanel = new JPanel();
		optionPanel.setLayout(menuPanelLayout);
		optionPanel.setPreferredSize(new java.awt.Dimension(width/2, (height/2)) );
		menuPanel.add(optionPanel, BorderLayout.NORTH);
		
		//Add text console
		console = new JTextArea();
		console.setVisible(true);
		console.setEditable(isDisplayable());
		console.setLineWrap(true);
		console.setWrapStyleWord(true);
		
		JScrollPane consolePane = new JScrollPane(console);
		consolePane.setVisible(true);
		consolePane.setPreferredSize(new java.awt.Dimension((width/2)-20, (height/2)-70) );
		menuPanel.add(consolePane, BorderLayout.SOUTH);
		console.setText("");	
		
		//Add toolbar menu
		toolbar = new JMenuBar();
		setJMenuBar(toolbar);
		
			fileMenu = new JMenu();
			toolbar.add(fileMenu);
			fileMenu.setText("File");
			{
				openFileMenuItem = new JMenuItem();
				openFileMenuItem.addActionListener(new OpenListener());
				fileMenu.add(openFileMenuItem);
				openFileMenuItem.setText("Open");
			}
			
			{
				closeFileMenuItem = new JMenuItem();
				closeFileMenuItem.addActionListener(new CloseListener());
				fileMenu.add(closeFileMenuItem);
				closeFileMenuItem.setText("Close");
			}
			{
				jSeparator2 = new JSeparator();
				fileMenu.add(jSeparator2);
			}
			{
				exitMenuItem = new JMenuItem();
				fileMenu.add(exitMenuItem);
				exitMenuItem.setText("Exit");
			}
		
			helpMenu = new JMenu();
			toolbar.add(helpMenu);
			helpMenu.setText("Help");
			{
				helpMenuItem = new JMenuItem();
				helpMenu.add(helpMenuItem);
				helpMenuItem.setText("Help");
			}
		
		
	}
	 
	
	class OpenListener implements ActionListener {

	    public void actionPerformed(ActionEvent e) {
	    	if (e.getSource() == openFileMenuItem) {
	            int returnVal = fc.showOpenDialog(BookWormMain.this);

	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	            	File file = fc.getSelectedFile();
	            	
					try (InputStream stream = new FileInputStream(file);){

	            	BodyContentHandler handler = new BodyContentHandler();
	            	Metadata metadata = new Metadata();
	                AutoDetectParser parser = new AutoDetectParser();
	                parser.parse(stream, handler, metadata);
	                String content = handler.toString();
//	                System.out.println("Mime: " + metadata.get(Metadata.CONTENT_TYPE));
//	                System.out.println("Title: " + metadata.get(Metadata.TITLE));
//	                System.out.println("Word count: " + metadata.get(Metadata.WORD_COUNT));
//	                System.out.println("Paragraph count: " +  metadata.get(Metadata.PARAGRAPH_COUNT));
	                
	                resetList();
	                
	                console.setText(content);
	              
	                Book b = new Book();
	                Map <Word,MutableInt> words = b.readBook(content);
	                
	               // String []columnNames = {"Word", "Count"};
            		Object [][] data = new Object [words.size()][2];
            		
            		int i=0;
	                for (Map.Entry<Word,MutableInt> entry : words.entrySet())
	                {
	                	
	                    System.out.println(entry.getKey() + "/" + entry.getValue().get());
	                    data[i][0]=entry.getKey().getToken();
	                    data[i][1]=new Integer(entry.getValue().get());
	                    
	            		model.setValueAt(entry.getKey().getToken(), i, 0);
	            		model.setValueAt(entry.getValue().get(), i, 1);
	            		i++;
	            		if(i<words.size())
	            			model.addRow(data);
	                }

					}
	                
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
	                
	            } else {
	                //Do something else
	            }
	       }
	    }
	    }
	
	class CloseListener implements ActionListener {

	    public void actionPerformed(ActionEvent e) {
	    	if (e.getSource() == closeFileMenuItem) {
	            resetList();
	    		
	       }
	    }
	    }
	
	public void resetList()
	{
		console.setText("");
        while (model.getRowCount()>0){
        	model.removeRow(0);
        	}
		Object [][] data = {{"",new Integer(0)}};;
		model.addRow(data);
		model.setValueAt("", 0, 0);
		model.setValueAt(0, 0, 1);
	}

}
