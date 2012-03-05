package com.tnicoll.apps.bookworm;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import javax.swing.*;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.*;
import org.apache.tika.sax.BodyContentHandler;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.tnicoll.apps.bookworm.gui.BookPanel;
import com.tnicoll.apps.bookworm.model.Book;
import com.tnicoll.apps.bookworm.model.Word;
import com.tnicoll.apps.bookworm.util.BookStats;



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

	private JMenuBar toolbar;


	private JMenuItem exitMenuItem;
	private JSeparator jSeparator2;
	private JMenuItem closeFileMenuItem;
	private JMenuItem openFileMenuItem;
	private JMenu fileMenu;
	private JMenu helpMenu;
	private JFileChooser fc = new JFileChooser();

	private JTabbedPane tabbedPane;

	private int width;
	private int height;
	public BodyContentHandler handler;
	public Metadata metadata;
	public AutoDetectParser parser;
	public ParseContext context;
	public boolean done;


	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		tabbedPane = new JTabbedPane();

		getContentPane().add(tabbedPane, BorderLayout.WEST);





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

					openFile();
				} else {
					//Do something else
				}
			}
		}
	}

	class CloseListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == closeFileMenuItem) {
				int index = tabbedPane.getSelectedIndex();
				if(index!=-1)
					tabbedPane.remove(index);
			}
		}
	}


	public void openFile()
	{
		File file = fc.getSelectedFile();
		int i=0;
		try (InputStream stream = new FileInputStream(file);){
		    
			handler = new BodyContentHandler(-1);
			metadata = new Metadata();
			parser = new AutoDetectParser();

			context = new ParseContext();

			parser.parse(stream, handler, metadata, context);
			
			String content = handler.toString();
			
			BookPanel bp = new BookPanel(width-20, height-40);


			bp.setConsoleText(content);

			Book b = new Book();

			Multiset <Word> words = b.readBook(content);

			Object [][] data = new Object [words.size()][5];

			BookModel model = bp.getModel();
			
	        StringBuffer syllable = new StringBuffer("");
	        
	        model.setRowCount(words.entrySet().size());
			for(Entry<Word> entry : words.entrySet())
			{
				
				data[i][0]=entry.getElement().getElement();
				data[i][1]=entry.getCount();
				data[i][2]=entry.getElement().isRecognised();
				data[i][3]=entry.getElement().getType();
				
				if(entry.getElement().getSyllables()!=-1)
					syllable= new StringBuffer(Integer.toString(entry.getElement().getSyllables()));
				data[i][4]=syllable;
				model.setValueAt(data[i][0], i, 0);
				model.setValueAt(data[i][1], i, 1);
				model.setValueAt(data[i][2], i, 2);
				model.setValueAt(data[i][3], i, 3);
				model.setValueAt(data[i][4], i, 4);
				i++;

			}
			
			bp.setModel(model);
			BookStats stats = b.getStats();

			bp.setParagraphCnt(stats.getParagraph_count());
			bp.setWordCnt(stats.getWord_count());
			bp.setAvgParagraphWordCnt(stats.getAvg_paragraph_word_count());
			bp.setFilesize(file.length());
			bp.setSentenceCnt(stats.getSentence_count());
			bp.setCharacterCnt(stats.getCharacter_count());
			bp.setLineCnt(stats.getLines());
			bp.setSpellingErrorCnt(stats.getSpelling_error_count());
			bp.setSpeechCnt(stats.getSpeech_count());
			bp.setNo_of_adjectivesCnt(stats.getNo_of_adjectives());
			bp.setNo_of_adverbsCnt(stats.getNo_of_adverbs());
			bp.setNo_of_nounsCnt(stats.getNo_of_nouns());
			bp.setNo_of_verbsCnt(stats.getNo_of_verbs());
			bp.setNo_of_unknownCnt(stats.getNo_of_unknown());
			tabbedPane.addTab(file.getName(),bp);
			

		}

		catch (Exception ex)
		{
			String message = "Unable to open file.  Sorry about that :-(";
			JOptionPane.showMessageDialog(new JFrame(), message, "Error",
					JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}

}
