package com.tnicoll.apps.bookworm.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.tnicoll.apps.bookworm.BookModel;
import com.tnicoll.apps.bookworm.model.Word;

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
//	private JScrollPane statScroll;
//	private JTable statTable;
//	private BookModel statModel;
	private BookModel model;
	private JTextArea console;
	private JScrollPane consolePane;
	private JTextField paragraphCnt;
	private JTextField wordCnt;
	private JTextField avgParagraphWordCnt;
	private JTextField sentenceCnt;
	private JTextField speechCnt;
	private JTextField filesize;
	private JTextField characterCnt;
	private JTextField spellingErrorCnt;
	private JTextField lineCnt;
	private JTextField no_of_nounsCnt;
	private JTextField no_of_verbsCnt;
	private JTextField no_of_adverbsCnt;
	private JTextField no_of_adjectivesCnt;
	private JTextField no_of_unknownCnt;

	public JTextField getParagraphCnt() {
		return paragraphCnt;
	}






	public void setParagraphCnt(int paragraphCnt) {
		this.paragraphCnt.setText(Integer.toString(paragraphCnt));
	}

	public void setWordCnt(int wordCnt) {
		this.wordCnt.setText(Integer.toString(wordCnt));
	}
	public void setAvgParagraphWordCnt(int avgParagraphWordCnt) {
		this.avgParagraphWordCnt.setText(Integer.toString(avgParagraphWordCnt));
	}
	public void setSentenceCnt(int sentenceCnt) {
		this.sentenceCnt.setText(Integer.toString(sentenceCnt));
	}
	public void setSpeechCnt(int speechCnt) {
		this.speechCnt.setText(Integer.toString(speechCnt));
	}
	public void setFilesize(long filesize) {
		this.filesize.setText(Long.toString(filesize/1024) + "K");
	}
	public void setCharacterCnt(int characterCnt) {
		this.characterCnt.setText(Integer.toString(characterCnt));
	}
	public void setSpellingErrorCnt(int spellingErrorCnt) {
		this.spellingErrorCnt.setText(Integer.toString(spellingErrorCnt));
	}
	public void setLineCnt(int lineCnt) {
		this.lineCnt.setText(Integer.toString(lineCnt));
	}
	public void setNo_of_nounsCnt(int no_of_nounsCnt) {
		this.no_of_nounsCnt.setText(Integer.toString(no_of_nounsCnt));
	}
	public void setNo_of_verbsCnt(int no_of_verbsCnt) {
		this.no_of_verbsCnt.setText(Integer.toString(no_of_verbsCnt));
	}
	public void setNo_of_adverbsCnt(int no_of_adverbsCnt) {
		this.no_of_adverbsCnt.setText(Integer.toString(no_of_adverbsCnt));
	}
	public void setNo_of_adjectivesCnt(int no_of_adjectivesCnt) {
		this.no_of_adjectivesCnt.setText(Integer.toString(no_of_adjectivesCnt));
	}
	public void setNo_of_unknownCnt(int no_of_unknownCnt) {
		this.no_of_unknownCnt.setText(Integer.toString(no_of_unknownCnt));
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
		Object []columnNames = {"Word", "Count","Recognised", "Type", "Syllables"};
		Object [][] data = {{"",new Integer(0),new Boolean(false), Word.WordType.U, ""}};;
		model = new BookModel(data, columnNames);

		wordTable = new JTable(model);
		wordTable.setRowSelectionAllowed(true);
		ListSelectionModel cellSelectionModel = wordTable.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String selectedData = null;

				int[] selectedRow = wordTable.getSelectedRows();

				selectedData = (String) wordTable.getValueAt(selectedRow[0], 0);

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
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(text)));
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
		optionPanel = new JPanel(new SpringLayout());
		optionPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		optionPanel.setPreferredSize(new java.awt.Dimension((width/2)-10, (height/2)) );
		
		JLabel paragraphCntLabel = new JLabel("No of Paragraphs", JLabel.TRAILING);
		optionPanel.add(paragraphCntLabel);
		paragraphCnt = new JTextField(6);
		paragraphCntLabel.setLabelFor(paragraphCnt);
		optionPanel.add(paragraphCnt);
		
		JLabel wordCntLabel = new JLabel("Word Count", JLabel.TRAILING);
		optionPanel.add(wordCntLabel);
		wordCnt = new JTextField(6);
		wordCntLabel.setLabelFor(wordCnt);		
		optionPanel.add(wordCnt);
		
		JLabel avgParagraphWordCntLabel = new JLabel("Average Word Count per Paragraph", JLabel.TRAILING);
		optionPanel.add(avgParagraphWordCntLabel);
		avgParagraphWordCnt = new JTextField(6);
		avgParagraphWordCntLabel.setLabelFor(avgParagraphWordCnt);		
		optionPanel.add(avgParagraphWordCnt);
		
		JLabel sentenceCntLabel = new JLabel("No of Sentences", JLabel.TRAILING);
		optionPanel.add(sentenceCntLabel);
		sentenceCnt = new JTextField(6);
		sentenceCntLabel.setLabelFor(sentenceCnt);		
		optionPanel.add(sentenceCnt);
		
		JLabel speechCntLabel = new JLabel("No of lines of Dialogue", JLabel.TRAILING);
		optionPanel.add(speechCntLabel);
		speechCnt = new JTextField(6);
		speechCntLabel.setLabelFor(speechCnt);		
		optionPanel.add(speechCnt);
		
		JLabel filesizeLabel = new JLabel("Filesize", JLabel.TRAILING);
		optionPanel.add(filesizeLabel);
		filesize = new JTextField(6);
		filesizeLabel.setLabelFor(filesize);		
		optionPanel.add(filesize);
		
		JLabel characterCntLabel = new JLabel("No of characters", JLabel.TRAILING);
		optionPanel.add(characterCntLabel);
		characterCnt = new JTextField(6);
		characterCntLabel.setLabelFor(characterCnt);		
		optionPanel.add(characterCnt);
		
		JLabel spellingErrorCntLabel = new JLabel("No of Spelling Errors", JLabel.TRAILING);
		optionPanel.add(spellingErrorCntLabel);
		spellingErrorCnt = new JTextField(6);
		spellingErrorCntLabel.setLabelFor(spellingErrorCnt);		
		optionPanel.add(spellingErrorCnt);
		
		JLabel lineCntLabel = new JLabel("No of lines", JLabel.TRAILING);
		optionPanel.add(lineCntLabel);
		lineCnt = new JTextField(6);
		lineCntLabel.setLabelFor(lineCnt);		
		optionPanel.add(lineCnt);
		
		JLabel no_of_nounsCntLabel = new JLabel("No of nouns", JLabel.TRAILING);
		optionPanel.add(no_of_nounsCntLabel);
		no_of_nounsCnt = new JTextField(6);
		no_of_nounsCntLabel.setLabelFor(no_of_nounsCnt);		
		optionPanel.add(no_of_nounsCnt);
		
		JLabel no_of_verbsCntLabel = new JLabel("No of verbs", JLabel.TRAILING);
		optionPanel.add(no_of_verbsCntLabel);
		no_of_verbsCnt = new JTextField(6);
		no_of_verbsCntLabel.setLabelFor(no_of_verbsCnt);		
		optionPanel.add(no_of_verbsCnt);
		
		JLabel no_of_adverbsCntLabel = new JLabel("No of adverbs", JLabel.TRAILING);
		optionPanel.add(no_of_adverbsCntLabel);
		no_of_adverbsCnt = new JTextField(6);
		no_of_adverbsCntLabel.setLabelFor(no_of_adverbsCnt);		
		optionPanel.add(no_of_adverbsCnt);
		
		JLabel no_of_adjectivesCntLabel = new JLabel("No of adjectives", JLabel.TRAILING);
		optionPanel.add(no_of_adjectivesCntLabel);
		no_of_adjectivesCnt = new JTextField(6);
		no_of_adjectivesCntLabel.setLabelFor(no_of_adjectivesCnt);		
		optionPanel.add(no_of_adjectivesCnt);
		
		JLabel no_of_unknownCntLabel = new JLabel("No of unknown types", JLabel.TRAILING);
		optionPanel.add(no_of_unknownCntLabel);
		no_of_unknownCnt = new JTextField(6);
		no_of_unknownCntLabel.setLabelFor(no_of_unknownCnt);
		optionPanel.add(no_of_unknownCnt);
		
		SpringUtilities.makeCompactGrid(optionPanel,
                7, 4, //rows, cols
                6, 6,        //initX, initY
                6, 6);
		
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


}
