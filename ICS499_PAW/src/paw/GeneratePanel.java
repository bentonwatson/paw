package paw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import core.Game;
import core.WordProcessor;

/**
 * this tab will display the list of available words in left text type panel
 * the list may be from the config output or the manual input
 * field for number of words to select with a generate button
 * after game is displayed a save button is available to call saveGame
 * @author Ben
 *
 */
class GeneratePanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private PAWgui internalgui;
	private Game newGame;
	private JPanel gridPanel;
	private JPanel titlePanel;
	private JPanel wordListPanel;
	private JPanel buttonPanel;
	private JPanel columnPanel;
	private Font font;
	private Color tileColor;
	private Color bgColor;
	private boolean charOrderValue;
	private boolean allowDupValue;
	
	public GeneratePanel(Color color, PAWgui paw) {
		this.internalgui = paw;
		font = internalgui.getFont();
		tileColor = Config.PLAY_TILE_COLOR;
		bgColor = color;
		
		setMinimumSize(new Dimension(1000,550));
		setBackground(bgColor);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout());

		setNewGame(internalgui.getCurrentGameGenerator());
		
		generateWordListPanel();
		generateGridPanel();
		generateButtonPanel();
	}
	
	/**
	 * sets the newGame for the Generate Panel
	 */
	public void setNewGame(GameGenerator game){
		newGame = game.getNewGame();
	}

	/**
	 * generates the panel to display words returned from config panel
	 */
	public void generateWordListPanel(){
		wordListPanel = new JPanel();
		wordListPanel.setPreferredSize(new Dimension(200, 300));
		wordListPanel.setLayout(new BorderLayout());
		
		JEditorPane numWords = new JEditorPane();
		numWords.setFont(font);
		if(newGame != null){
			numWords.setText("Number of Words = " + String.valueOf(newGame.getNumberWords()));
		}else{
			numWords.setText("Number of Words = 0 \n You Have Found = 0");
		}		wordListPanel.add(numWords, BorderLayout.NORTH);
		
		JEditorPane words = new JEditorPane();
		words.setFont(font);
		String list = "";
		if(newGame!= null){
			ArrayList<String> wordList = newGame.getWordList();
			for(int i = 0; i < wordList.size(); i++){
				list += wordList.get(i) + "\n";
			}
		}
		words.setText(list);
		wordListPanel.add(words, BorderLayout.CENTER);
		
		add(wordListPanel, BorderLayout.WEST);
	}
	
	/**
	 * Creates the panel to display the columns
	 * includes the answer row
	 * @return 
	 */
	public void generateGridPanel(){
		gridPanel =  new JPanel();
		gridPanel.setBorder(new LineBorder(Color.black, 2));
		gridPanel.setLayout(new BorderLayout());
		
		titlePanel = new JPanel();
		if(newGame != null){
			JLabel titleLabel = new JLabel(newGame.getTitle() 
					+ " - (Duplicates = " + newGame.getDuplicate() + ")"
					+ " - (In Order = " + newGame.getCharOrder() + ")");
			titleLabel.setFont(font);
			titlePanel.add(titleLabel);
		}else{
			JLabel titleLabel = new JLabel(" No Game Selected ");
			titleLabel.setFont(font);
			titlePanel.add(titleLabel);
		}
		gridPanel.add(titlePanel, BorderLayout.NORTH);
		columnPanel = new JPanel();

		columnPanel.setLayout(new GridLayout(1, 10, 10, 0));
		columnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 5));

		ArrayList<ArrayList<String>> columnData = new ArrayList<ArrayList<String>>();
		if(newGame!= null){
			columnData = newGame.getColumnData();
		
			for(int i = 0; i < columnData.size(); i++){
				ArrayList<String> characters = columnData.get(i);
				JPanel column = new JPanel(new GridLayout(characters.size(), 1, 0, 5));
				for(int j = 0; j < characters.size(); j++){
					if(!newGame.getDuplicate()){
						String tmp = characters.get(j);
						if(tmp.length() > 1){
							WordProcessor wp = new WordProcessor(tmp);
							ArrayList<String> ch = wp.getLogicalChars();
							int count = Integer.valueOf(ch.get(1));
							GridTile newTile = new GridTile(wp.logicalCharAt(0), j);
							newTile.columnNum = i;
							newTile.repeat = count;
							column.add(newTile);
						}else{
							GridTile newTile = new GridTile("", j);
							newTile.columnNum = i;
							newTile.repeat = 1;
							newTile.setVisible(false);
							column.add(newTile);
						}
					}else{
						GridTile newTile = new GridTile(characters.get(j), j);
						column.add(newTile);
					}
				}
				columnPanel.add(column);
			}
		}
		gridPanel.add(columnPanel, BorderLayout.CENTER);
		add(gridPanel, BorderLayout.CENTER);
	}
	
	/**
	 * generates a panel to hold action buttons
	 */
	public void generateButtonPanel(){
		JPanel buttPanel = new JPanel();
//		buttonPanel = new JPanel(new SpringLayout());
		buttonPanel = new JPanel(new GridLayout(6,1));
		
		// generates the HTML
		JButton createHTMLBtn = new JButton("Create\nHTML");
		createHTMLBtn.setFont(font);
		createHTMLBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new CreateHTML(newGame);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		buttonPanel.add(createHTMLBtn);
		
		//calls gameSaver and displays saved message
		JButton saveGameBtn = new JButton("Save Game");
		saveGameBtn.setFont(font);
		saveGameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new GameSaver(newGame);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		buttonPanel.add(saveGameBtn);
		
		//sends the game to play Tab and opens play tab
		JButton playGameBtn = new JButton("Play Game");
		playGameBtn.setFont(font);
		playGameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					internalgui.setCurrentGame(newGame);
					internalgui.selectTabbedPaneIndex(1);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		buttonPanel.add(playGameBtn);
		
		//allow duplicates
		JLabel allowDupLabel = new JLabel("Duplicates");
		allowDupLabel.setFont(font);
		JRadioButton allowDupYes = new JRadioButton("Yes");
		allowDupYes.setMnemonic(KeyEvent.VK_Y);
		allowDupYes.setActionCommand("Yes");
		JRadioButton allowDupNo = new JRadioButton("No");
		allowDupNo.setMnemonic(KeyEvent.VK_N);
		allowDupNo.setActionCommand("No");
		ButtonGroup allowDupGroup = new ButtonGroup();
		allowDupGroup.add(allowDupYes);
		allowDupGroup.add(allowDupNo);
		allowDupValue = Boolean.valueOf(newGame.getDuplicate());
		if(allowDupValue){
			allowDupYes.setSelected(true);
		}else{
			allowDupNo.setSelected(true);
		}
		JPanel allowDupPanel = new JPanel(new FlowLayout());
		allowDupPanel.add(allowDupLabel);
		allowDupPanel.add(allowDupYes);
		allowDupPanel.add(allowDupNo);
		
		//characters in order
		JLabel charOrderLabel = new JLabel("In Order");
		charOrderLabel.setFont(font);
		JRadioButton charOrderYes = new JRadioButton("Yes");
		charOrderYes.setMnemonic(KeyEvent.VK_Y);
		charOrderYes.setActionCommand("Yes");
		JRadioButton charOrderNo = new JRadioButton("No");
		charOrderNo.setMnemonic(KeyEvent.VK_N);
		charOrderNo.setActionCommand("No");
		ButtonGroup charOrderGroup = new ButtonGroup();
		charOrderGroup.add(charOrderYes);
		charOrderGroup.add(charOrderNo);
		charOrderValue = Boolean.valueOf(newGame.getCharOrder());
		if(charOrderValue){
			charOrderYes.setSelected(true);
		}else{
			charOrderNo.setSelected(true);
		}
		JPanel charOrderPanel = new JPanel(new FlowLayout());
		charOrderPanel.add(charOrderLabel);
		charOrderPanel.add(charOrderYes);
		charOrderPanel.add(charOrderNo);
		
		JPanel optionPanel = new JPanel(new GridLayout(2,3));
		optionPanel.add(allowDupLabel);
		optionPanel.add(allowDupYes);
		optionPanel.add(allowDupNo);
		optionPanel.add(charOrderLabel);
		optionPanel.add(charOrderYes);
		optionPanel.add(charOrderNo);
		buttonPanel.add(optionPanel);
		
		JButton changeConfigBttn = new JButton("Update Game");
		changeConfigBttn.setFont(font);
		changeConfigBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// updates the already existing gg and pushes the update to internalgui.setCurrentGameGenerator
				if(allowDupYes.getModel() == allowDupGroup.getSelection()){
					allowDupValue = true;
				}
				if(allowDupNo.getModel() == allowDupGroup.getSelection()){
					allowDupValue = false;
				}
				if(charOrderYes.getModel() == charOrderGroup.getSelection()){
					charOrderValue = true;
				}
				if(charOrderNo.getModel() == charOrderGroup.getSelection()){
					charOrderValue = false;
				}
				GameGenerator old = internalgui.getCurrentGameGenerator();
				GameGenerator gg = new GameGenerator(old.getTopic(), old.getLevel(), old.getWordLength(), 
								old.getMinWordStrength(), old.getMaxWordStrength(),
								allowDupValue, charOrderValue, old.getWordList());
				internalgui.setCurrentGameGenerator(gg);
				setNewGame(gg);
				internalgui.selectTabbedPaneIndex(3);
				internalgui.selectTabbedPaneIndex(2);
			}
		});
		buttonPanel.add(changeConfigBttn);

//		SpringUtility.makeGrid(buttonPanel, 5, 1, 25, 15, 15, 15);
		
		buttPanel.add(buttonPanel);
		add(buttPanel, BorderLayout.EAST);
		
	}
	
	/**
	 * This defines the basic tile used to hold the logical characters in the game
	 *
	 */
	class Tile extends JButton {
		private static final long serialVersionUID = 1L;
		int clickedPosition = -1;
		int tileId = -1;
		
		Tile(){
			setFont(font);
		}

	}
	
	/**
	 * extends tile to further define the tiles in the game
	 * sets text to the tile and colors
	 *
	 */
	class GridTile extends Tile {
		private static final long serialVersionUID = 1L;
		int clickedPosition = -1;
		int tileId = -1;
		int columnNum;
		int repeat;
		String character;

		GridTile(String character, int iD) {
			super();
			this.character = character;
			setText(character);
			if(character.equals("")){
				setVisible(false);
			}
			setBackground(tileColor);
			tileId = iD;
		}

	}
}
