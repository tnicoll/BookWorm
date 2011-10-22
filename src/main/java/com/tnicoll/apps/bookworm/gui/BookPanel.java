package com.tnicoll.apps.bookworm.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.tnicoll.apps.bookworm.BookModel;

public class BookPanel extends JPanel{

	/**
	 * A panel showing all the details for a single book
	 */
	private static final long serialVersionUID = -5675560527929949571L;
	private JPanel menuPanel;
	private JPanel optionPanel;
	private JTextField filterBox;
	private JPanel searchPanel;
	private JScrollPane wordScroll;
	private JTable wordTable;
	private BookModel model;
	private JTextArea console;
	private JScrollPane consolePane;
	private JTextField paragraphCnt;


	public JTextField getParagraphCnt() {
		return paragraphCnt;
	}



	public void setParagraphCnt(int paragraphCnt) {
		this.paragraphCnt.setText(Integer.toString(paragraphCnt));
	}



	public BookPanel(int width, int height)
	{
		searchPanel = new JPanel();
		searchPanel.setPreferredSize(new java.awt.Dimension(width/2, height));
		JLabel filterLabel = new JLabel("Filter: ");
		filterBox = new JTextField();
		filterBox.setEditable(true);
		filterBox.setColumns(50);
		filterBox.setSize((width/2)-50, 10);
		searchPanel.add(filterLabel, BorderLayout.NORTH);
		searchPanel.add(filterBox, BorderLayout.NORTH);
		add(searchPanel, BorderLayout.WEST);

		///Add Word Table
		Object []columnNames = {"Word", "Count"};
		Object [][] data = {{"",new Integer(0)}};;
		model = new BookModel(data, columnNames);

		wordTable = new JTable(model);
		wordTable.setRowSelectionAllowed(true);
		ListSelectionModel cellSelectionModel = wordTable.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String selectedData = null;

				int[] selectedRow = wordTable.getSelectedRows();
				int[] selectedColumns = wordTable.getSelectedColumns();

				for (int i = 0; i < selectedRow.length; i++) {
					for (int j = 0; j < selectedColumns.length; j++) {
						selectedData = (String) wordTable.getValueAt(selectedRow[i], selectedColumns[j]);
					}
				}
				System.out.println("Selected: " + selectedData);
			}

		});


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
		add(menuPanel, BorderLayout.EAST);
		FlowLayout menuPanelLayout = new FlowLayout();
		menuPanelLayout.setAlignment(FlowLayout.RIGHT);
		menuPanel.setLayout(menuPanelLayout);
		menuPanel.setPreferredSize(new java.awt.Dimension(width/2, height));

		//Add Right Hand Top Option Panel
		optionPanel = new JPanel();
		optionPanel.setLayout(menuPanelLayout);
		optionPanel.setPreferredSize(new java.awt.Dimension(width/2, (height/2)) );

		JLabel paragraphCntLabel = new JLabel("No of Paragraphs:");
		paragraphCnt = new JTextField(6);
		optionPanel.add(paragraphCntLabel);
		optionPanel.add(paragraphCnt);

		menuPanel.add(optionPanel, BorderLayout.NORTH);

		//Add text console
		console = new JTextArea();
		console.setVisible(true);
		console.setEditable(false);
		console.setLineWrap(true);
		console.setWrapStyleWord(true);

		consolePane = new JScrollPane(console);
		consolePane.setVisible(true);
		consolePane.setPreferredSize(new java.awt.Dimension((width/2)-20, (height/2)-70) );
		menuPanel.add(consolePane, BorderLayout.SOUTH);
		console.setText("");
	}



	public JPanel getMenuPanel() {
		return menuPanel;
	}

	public void setMenuPanel(JPanel menuPanel) {
		this.menuPanel = menuPanel;
	}

	public JPanel getOptionPanel() {
		return optionPanel;
	}

	public void setOptionPanel(JPanel optionPanel) {
		this.optionPanel = optionPanel;
	}

	public JTextField getFilterBox() {
		return filterBox;
	}

	public void setFilterBox(JTextField filterBox) {
		this.filterBox = filterBox;
	}

	public JPanel getSearchPanel() {
		return searchPanel;
	}

	public void setSearchPanel(JPanel searchPanel) {
		this.searchPanel = searchPanel;
	}

	public JScrollPane getWordScroll() {
		return wordScroll;
	}

	public void setWordScroll(JScrollPane wordScroll) {
		this.wordScroll = wordScroll;
	}

	public JTable getWordTable() {
		return wordTable;
	}

	public void setWordTable(JTable wordTable) {
		this.wordTable = wordTable;
	}

	public BookModel getModel() {
		return model;
	}

	public void setModel(BookModel model) {
		this.model = model;
	}

	public JTextArea getConsole() {
		return console;
	}

	public void setConsole(JTextArea console) {
		this.console = console;
	}
	public void setConsoleText(String text){
		this.console.setText(text);
	}

	public JScrollPane getConsolePane() {
		return consolePane;
	}

	public void setConsolePane(JScrollPane consolePane) {
		this.consolePane = consolePane;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	//	public class SelectListener implements ListSelectionListener {
	//	    JTable table;
	//
	//	    // It is necessary to keep the table since it is not possible
	//	    // to determine the table from the event's source
	//	    SelectListener(JTable table) {
	//	        this.table = table;
	//	    }
	//	    public void valueChanged(ListSelectionEvent e) {
	//	        // If cell selection is enabled, both row and column change events are fired
	//	    	int first = e.getFirstIndex();
	//            int last = e.getLastIndex();
	//            //System.out.println("first: " + first);
	//            System.out.println("last: " + last);
	////	        if (e.getSource() == table.getSelectionModel()
	////	              && table.getRowSelectionAllowed()) {
	////	            // Column selection changed
	////	            int first = e.getFirstIndex();
	////	            int last = e.getLastIndex();
	////	            //System.out.println("first: " + first);
	////	            System.out.println("last: " + last);
	////	        } else if (e.getSource() == table.getColumnModel().getSelectionModel()
	////	               && table.getColumnSelectionAllowed() ){
	////	            // Row selection changed
	////	            int first = e.getFirstIndex();
	////	            int last = e.getLastIndex();
	////	            System.out.println("first 2: " + first);
	////	            System.out.println("last 2: " + last);
	////	        }
	////
	////	        if (e.getValueIsAdjusting()) {
	////	            // The mouse button has not yet been released
	////	        }
	//	    }
	//	}

}
