package paw;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;

import core.BigWordCollection;

/**
*
* @author surin.assa/Ben Watson
*/
public class ConfigPanel extends JPanel 
		implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	// Variables declaration - do not modify

	private PAWgui internalgui;
	
	private JLabel totalWordsLabel;
	private JLabel numberWordsFoundLabel;
	private JLabel levelLabel;
	private JLabel topicLabel;
	private JLabel numWordsLabel;
	private JLabel minLenLabel;
	private JLabel minStrengthLabel;
	private JLabel maxStrengthLabel;
	private JLabel allowDupLabel;
	private JLabel charOrderLabel;
	private JLabel allWordsLabel;
	private JLabel useCustomLabel;
	
	private JTextField showTotalWordsTF;
	private JTextField showNumberWordsFoundTF;
	private JComboBox<Integer> levelComboBox;
	private JComboBox<String> topicComboBox;
	private JFormattedTextField numWordsTF; 	
	private JFormattedTextField minLenTF; 	
	private JFormattedTextField minStrengthTF;
	private JFormattedTextField maxStrengthTF;
	
	private JRadioButton allowDupYes; 
	private JRadioButton allowDupNo;
	private ButtonGroup allowDupGroup;
	
	private JRadioButton charOrderYes; 
	private JRadioButton charOrderNo;
	private ButtonGroup charOrderGroup;
	
	private JRadioButton allWordsYes; 
	private JRadioButton allWordsNo;
	private ButtonGroup allWordsGroup;

	private JRadioButton useCustomYes; 
	private JRadioButton useCustomNo;
	private ButtonGroup useCustomGroup;
	
	private JButton setConfigBttn;
	
	private int levelValue;
	private String topicValue = "";

	private NumberFormat numWordsFormat;
	private static int minNumWords = 2;
	private int minNumWordsValue;
	private static int maxNumWords = Config.maxNumberWords;
	private int maxNumWordsValue = Config.maxNumberWords;
	
	
	private NumberFormat minLenFormat;
	private static int minLen = 2;
	private int minLenValue;
	private static int maxLen = 10;
	private int maxLenValue = 10;
	
	private NumberFormat minStrFormat;
	private NumberFormat maxStrFormat;
	private static int minStr = 1;
	private int minStrValue;
	private static int maxStr = 7;
	private int maxStrValue = 7;
	
	private boolean allowDupValue;
	private boolean charOrderValue;
	private boolean allWordsValue;
	private boolean useCustomValue;
	
	private int numWordsFound;
	private BigWordCollection allWords;
	private GameGenerator gg;
	private GameGenerator internalGG;
	private ArrayList<String> internalPool = new ArrayList<String>();
	private Font font;
	private Color bgColor;
	// End of variables declaration  

   /**
    * Creates new form NewJPanel
    */
	public ConfigPanel(Color color, PAWgui paw) {
		bgColor = color;
		this.internalgui = paw;
		allWords = new BigWordCollection();	
		font = internalgui.getFont();
		minLenFormat = NumberFormat.getNumberInstance();		 
		minStrFormat = NumberFormat.getNumberInstance();		 
		numWordsFormat = NumberFormat.getNumberInstance();		 
		if(internalgui.getCurrentGameGenerator() != null){
			internalGG = internalgui.getCurrentGameGenerator();
			levelValue = Integer.valueOf(internalGG.getLevel());
			minLenValue = Integer.valueOf(internalGG.getWordLength());
			minStrValue = Integer.valueOf(internalGG.getMinWordStrength());
			maxStrValue = Integer.valueOf(internalGG.getMaxWordStrength());
			allowDupValue = Boolean.valueOf(internalGG.getDuplicates());
			charOrderValue = Boolean.valueOf(internalGG.getCharOrder());
			minNumWordsValue = Integer.valueOf(internalGG.getNumWords());
		}else{
			levelValue = Integer.valueOf(internalgui.tmpConfigSettings.get(1));
			minLenValue = Integer.valueOf(internalgui.tmpConfigSettings.get(2));
			minStrValue = Integer.valueOf(internalgui.tmpConfigSettings.get(3));
			maxStrValue = Integer.valueOf(internalgui.tmpConfigSettings.get(4));
			allowDupValue = Boolean.valueOf(internalgui.tmpConfigSettings.get(5));
			charOrderValue = Boolean.valueOf(internalgui.tmpConfigSettings.get(6));
			minNumWordsValue = Integer.valueOf(internalgui.tmpConfigSettings.get(8));
		}
		if(internalgui.getCurrentConfigPool() != null){
			internalPool = internalgui.getCurrentConfigPool();
		}
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {
		
		setBackground(bgColor);
		
		// initialize components for the configuration panel
		Border border = BorderFactory.createEtchedBorder();
		
		// label "Total Number of words"
		totalWordsLabel = new JLabel("Total Number Input Words:");
		totalWordsLabel.setFont(font);
		showTotalWordsTF = new JTextField();
		if(useCustomValue){
			showTotalWordsTF.setText(String.valueOf(internalgui.customWords.size()));		
		}else{
			showTotalWordsTF.setText(String.valueOf(allWords.size()));		
		}
		showTotalWordsTF.setFont(font);
		showTotalWordsTF.setBorder(border);
		showTotalWordsTF.setEditable(false);
		
		// Total "Number words found" words
		numberWordsFoundLabel = new JLabel("Total Number this Configuration:");
		numberWordsFoundLabel.setFont(font);
		showNumberWordsFoundTF = new JTextField();
		if(useCustomValue){
			showNumberWordsFoundTF.setText(String.valueOf(internalGG.getCustomWords().size()));		
		}else{
			showNumberWordsFoundTF.setText(String.valueOf(internalPool.size()));		
		}
		showNumberWordsFoundTF.setFont(font);
		showNumberWordsFoundTF.setBorder(border);
		showNumberWordsFoundTF.setEditable(false);
		
		// Level
		levelLabel = new JLabel("Level");
		levelLabel.setFont(font);
		levelComboBox = new JComboBox<Integer>();
		for(int i = 1; i <= 4; i++){
			levelComboBox.addItem(i);
		}
		levelComboBox.setFont(font);
//		levelValue = Integer.valueOf(internalgui.tmpConfigSettings.get(1));
		levelComboBox.setSelectedItem(levelValue);
		levelComboBox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				levelValue = Integer.valueOf(levelComboBox.getSelectedItem().toString());
				if(levelValue == 1){
					numWordsTF.setValue(5);
					minLenTF.setValue(4);
					minStrengthTF.setValue(1);
					maxStrengthTF.setValue(7);
					allowDupYes.setSelected(true);
					charOrderYes.setSelected(true);
				}
				if(levelValue == 2){
					numWordsTF.setValue(5);
					minLenTF.setValue(6);
					minStrengthTF.setValue(1);
					maxStrengthTF.setValue(7);
					allowDupYes.setSelected(true);
					charOrderYes.setSelected(true);
				}
				if(levelValue == 3){
					numWordsTF.setValue(8);
					minLenTF.setValue(6);
					minStrengthTF.setValue(3);
					maxStrengthTF.setValue(7);
					allowDupNo.setSelected(true);
					charOrderYes.setSelected(true);
				}
				if(levelValue == 4){
					numWordsTF.setValue(5);
					minLenTF.setValue(6);
					minStrengthTF.setValue(4);
					maxStrengthTF.setValue(7);
					allowDupNo.setSelected(true);
					charOrderNo.setSelected(true);
				}
			}
		});
		
		// Topics
		topicLabel = new JLabel("Topic");
		topicLabel.setFont(font);
		topicComboBox = new JComboBox<String>();
		topicComboBox.addItem("Any");
		topicComboBox.addItem("Custom Topic");
		Set<String> keys = allWords.getBigWordsTopicsTable().keySet();		
		for(String val: keys)
			topicComboBox.addItem(val);		
		topicComboBox.setFont(font);
		topicValue = internalgui.tmpConfigSettings.get(0);
		topicComboBox.setSelectedItem(internalgui.tmpConfigSettings.get(0));
		topicComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				topicValue = topicComboBox.getSelectedItem().toString();
				if(!topicValue.equals("Custom Topic")){
					useCustomYes.setSelected(false);
					useCustomNo.setSelected(true);
				}else{
					useCustomYes.setSelected(true);
					useCustomNo.setSelected(false);
				}
			}
		});		
		
		//Number Of Words
		numWordsLabel = new JLabel("Number of Words (2 - 10)");
		numWordsLabel.setFont(font);
		numWordsTF = new JFormattedTextField(numWordsFormat);
		numWordsTF.setFont(font);
		numWordsTF.setValue(minNumWordsValue);
		numWordsTF.addPropertyChangeListener("value",this);
		
		//Word Length
		minLenLabel = new JLabel("Word Length (2 - 10)");
		minLenLabel.setFont(font);
		minLenTF = new JFormattedTextField(minLenFormat);
		minLenTF.setFont(font);
		minLenTF.setValue(minLenValue);
		minLenTF.addPropertyChangeListener("value",this);

		// Word Strength
		minStrengthLabel = new JLabel("Min Word Strength (1 - 7)");
		minStrengthLabel.setFont(font);
		
		minStrengthTF = new JFormattedTextField(minStrFormat);
		minStrengthTF.setFont(font);
		minStrengthTF.setValue(minStrValue);
		minStrengthTF.addPropertyChangeListener("value",this);
		
		maxStrengthLabel = new JLabel("Max Word Strength (1 - 7)");
		maxStrengthLabel.setFont(font);
		maxStrengthTF = new JFormattedTextField(maxStrFormat);
		maxStrengthTF.setFont(font);
		maxStrengthTF.setValue(maxStrValue);
		maxStrengthTF.addPropertyChangeListener("value",this);

		//allow duplicates
		allowDupLabel = new JLabel("Allow Duplicate Characters");
		allowDupLabel.setFont(font);
		allowDupYes = new JRadioButton("Yes");
		allowDupYes.setMnemonic(KeyEvent.VK_Y);
		allowDupYes.setActionCommand("Yes");
		allowDupNo = new JRadioButton("No");
		allowDupNo.setMnemonic(KeyEvent.VK_N);
		allowDupNo.setActionCommand("No");
		allowDupGroup = new ButtonGroup();
		allowDupGroup.add(allowDupYes);
		allowDupGroup.add(allowDupNo);
		if(allowDupValue){
			allowDupYes.setSelected(true);
		}else{
			allowDupNo.setSelected(true);
		}
		JPanel allowDupPanel = new JPanel(new FlowLayout());
		allowDupPanel.add(allowDupYes);
		allowDupPanel.add(allowDupNo);

		//characters in order
		charOrderLabel = new JLabel("Display Characters in Order");
		charOrderLabel.setFont(font);
		charOrderYes = new JRadioButton("Yes");
		charOrderYes.setMnemonic(KeyEvent.VK_Y);
		charOrderYes.setActionCommand("Yes");
		charOrderNo = new JRadioButton("No");
		charOrderNo.setMnemonic(KeyEvent.VK_N);
		charOrderNo.setActionCommand("No");
		charOrderGroup = new ButtonGroup();
		charOrderGroup.add(charOrderYes);
		charOrderGroup.add(charOrderNo);
		if(charOrderValue){
			charOrderYes.setSelected(true);
		}else{
			charOrderNo.setSelected(true);
		}
		JPanel charOrderPanel = new JPanel(new FlowLayout());
		charOrderPanel.add(charOrderYes);
		charOrderPanel.add(charOrderNo);
		
		//show all words returned
		allWordsLabel = new JLabel("Generate with Max Available (up to 10)");
		allWordsLabel.setFont(font);
		allWordsYes = new JRadioButton("Yes");
		allWordsYes.setMnemonic(KeyEvent.VK_Y);
		allWordsYes.setActionCommand("Yes");
		allWordsNo = new JRadioButton("No");
		allWordsNo.setMnemonic(KeyEvent.VK_N);
		allWordsNo.setActionCommand("No");
		allWordsGroup = new ButtonGroup();
		allWordsGroup.add(allWordsYes);
		allWordsGroup.add(allWordsNo);
		allWordsValue = Boolean.valueOf(internalgui.tmpConfigSettings.get(7));
		if(allWordsValue){
			allWordsYes.setSelected(true);
		}else{
			allWordsNo.setSelected(true);
		}
		JPanel allWordsPanel = new JPanel(new FlowLayout());
		allWordsPanel.add(allWordsYes);
		allWordsPanel.add(allWordsNo);
		
		//use the custom words
		useCustomLabel = new JLabel("Generate with Custom Words");
		useCustomLabel.setFont(font);
		useCustomYes = new JRadioButton("Yes");
		useCustomYes.setMnemonic(KeyEvent.VK_Y);
		useCustomYes.setActionCommand("Yes");
		useCustomNo = new JRadioButton("No");
		useCustomNo.setMnemonic(KeyEvent.VK_N);
		useCustomNo.setActionCommand("No");
		useCustomGroup = new ButtonGroup();
		useCustomGroup.add(useCustomYes);
		useCustomGroup.add(useCustomNo);
		useCustomValue = Boolean.valueOf(internalgui.customGame);
		if(useCustomValue){
			useCustomYes.setSelected(true);
			showTotalWordsTF.setText(String.valueOf(internalgui.customWords.size()));
		}else{
			useCustomNo.setSelected(true);
		}
		useCustomYes.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(!useCustomYes.isSelected()){
					internalgui.tmpConfigSettings.set(0, "Any");
					topicComboBox.setSelectedItem(internalgui.tmpConfigSettings.get(0));
					showTotalWordsTF.setText(String.valueOf(allWords.size()));
					showNumberWordsFoundTF.setText(String.valueOf(0));
				}
				if(useCustomYes.isSelected()){
					internalgui.tmpConfigSettings.set(0, "Custom Topic");
					topicComboBox.setSelectedItem(internalgui.tmpConfigSettings.get(0));
					showTotalWordsTF.setText(String.valueOf(internalgui.customWords.size()));
					showNumberWordsFoundTF.setText(String.valueOf(internalgui.customWords.size()));
				}
			}
		});
		JPanel useCustomPanel = new JPanel(new FlowLayout());
		useCustomPanel.add(useCustomYes);
		useCustomPanel.add(useCustomNo);

		// set configuration button
		setConfigBttn = new JButton("Set Configuration");
		setConfigBttn.setBackground(Config.CONFIG_PANEL_BUTTONS);
		setConfigBttn.setFont(font);
		setConfigBttn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
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
				if(allWordsYes.getModel() == allWordsGroup.getSelection()){
					allWordsValue = true;
				}
				if(allWordsNo.getModel() == allWordsGroup.getSelection()){
					allWordsValue = false;
				}
				if(useCustomYes.getModel() == useCustomGroup.getSelection()){
					useCustomValue = true;
				}
				if(useCustomNo.getModel() == useCustomGroup.getSelection()){
					useCustomValue = false;
				}
				int numWords = 0;
				if(useCustomValue){
					if(internalgui.customWords.size() == 0){
						internalgui.selectTabbedPaneIndex(4);
					}else{
						internalgui.customGame = true;
						String topic = internalgui.customTopic;
						// custom words need to have length determined before calling generator
						
						gg = new GameGenerator(topic, levelValue, minLenValue, minStrValue,
								maxStrValue, allowDupValue, charOrderValue, internalgui.customWords);
					}
				}else{
					internalgui.customGame = false;
					gg = new GameGenerator(topicValue, levelValue, minLenValue, 
							minStrValue, maxStrValue, allowDupValue, charOrderValue);
				}
				internalPool = gg.getReturnedWordList();
				internalgui.setCurrentConfigPool(internalPool);
				
				numWordsFound = gg.getReturnedWordList().size();
				if(allWordsValue){
					if(numWordsFound <= Config.maxNumberWords){
						numWords = numWordsFound;
					}else{
						numWords = Config.maxNumberWords;
					}
				}else{
					numWords = minNumWordsValue; 
				}
				gg.chooseNumberOfWords(numWords);
				gg.setColumnData();
				gg.setNewGame();
				
				internalgui.tmpConfigSettings.set(0, topicValue);
//				internalgui.tmpConfigSettings.set(7, String.valueOf(allWordsValue));
				showNumberWordsFoundTF.setText(String.valueOf(internalPool.size()));
				numWordsTF.setText(String.valueOf(numWords));
				
				internalgui.setCurrentGameGenerator(gg);
//				GeneratePanel.setNewGame(gg);
//				goToGenerate(2); // switches tab to Generate tab when clicking setConfig button
			}
		});

		// starts layout. 
		GroupLayout layout = new GroupLayout(this);
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup()
					.addGap(40)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(useCustomLabel, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
//						.addComponent(allWordsLabel, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
						.addComponent(charOrderLabel, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
						.addComponent(allowDupLabel, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
						.addComponent(maxStrengthLabel, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
						.addComponent(minStrengthLabel, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
						.addComponent(minLenLabel, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
						.addComponent(numWordsLabel, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
						.addComponent(levelLabel)
						.addComponent(topicLabel))
					.addGap(15)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(useCustomPanel, 0, 150, GroupLayout.PREFERRED_SIZE)
//						.addComponent(allWordsPanel, 0, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(charOrderPanel, 0, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(allowDupPanel, 0, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(maxStrengthTF, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(minStrengthTF, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(minLenTF, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(numWordsTF, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(levelComboBox, 0, 150, Short.MAX_VALUE)
						.addComponent(topicComboBox, 0, 150, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
					.addGap(40)
					.addComponent(totalWordsLabel, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
					.addGap(8)
					.addComponent(showTotalWordsTF, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
					.addComponent(numberWordsFoundLabel, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)
					.addGap(9)
					.addComponent(showNumberWordsFoundTF, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(40))
				.addGroup(layout.createSequentialGroup()
					.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
					.addComponent(setConfigBttn)
					.addGap(35))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(21)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(totalWordsLabel)
						.addComponent(showTotalWordsTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(numberWordsFoundLabel)
						.addComponent(showNumberWordsFoundTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(15)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(levelComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(levelLabel))
					.addGap(15)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(topicComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(topicLabel))
					.addGap(15)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(numWordsLabel)
						.addComponent(numWordsTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(20)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(minLenLabel)
						.addComponent(minLenTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(20)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(minStrengthLabel)
						.addComponent(minStrengthTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(maxStrengthLabel)
						.addComponent(maxStrengthTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(19)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(allowDupLabel)
						.addComponent(allowDupPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(charOrderLabel)
						.addComponent(charOrderPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
//					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
//						.addComponent(allWordsLabel)
//						.addComponent(allWordsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
//					.addGap(17)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(useCustomLabel)
						.addComponent(useCustomPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(setConfigBttn, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
					.addGap(20))
		);
		
		this.setLayout(layout);

		// ends layout
	}  // end initComponents
   
//	/**
//	 * this method switches the tab to Generate tab
//	 * @param index of generate tab only called internally
//	 */
//	private void goToGenerate(int index) {
//		if(index != -1)
//			internalgui.selectTabbedPaneIndex(index);		
//	}


	/**
	 * Used for field validation for the min/max length and min/max strength
	 * @author sean.ford	   
	 */
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		Object source = e.getSource();		
        if (source == minLenTF) {
        	minLenValue = ((Number)minLenTF.getValue()).intValue();
            minLenValue = minLenValue < minLen ? minLen : minLenValue;
            minLenValue = minLenValue > maxLen ? maxLen : minLenValue;
            minLenValue = minLenValue > maxLenValue ? maxLenValue : minLenValue;
            minLenTF.setValue(minLenValue);
        } else if (source == minStrengthTF) {
        	minStrValue = ((Number)minStrengthTF.getValue()).intValue();
        	minStrValue = minStrValue < minStr ? minStr : minStrValue;
            minStrValue = minStrValue > maxStr ? maxStr : minStrValue;
            minStrValue = minStrValue > maxStrValue ? maxStrValue : minStrValue;
            minStrengthTF.setValue(minStrValue);
        } else if (source == maxStrengthTF) {
        	maxStrValue = ((Number)maxStrengthTF.getValue()).intValue();
        	maxStrValue = maxStrValue > maxStr ? maxStr : maxStrValue;
        	maxStrValue = maxStrValue < minStr ? minStr : maxStrValue;
        	maxStrValue = maxStrValue < minStrValue ? minStrValue : maxStrValue;
        	maxStrengthTF.setValue(maxStrValue);
		} else if (source == numWordsTF) {
			minNumWordsValue = ((Number)numWordsTF.getValue()).intValue();
			minNumWordsValue = minNumWordsValue < minNumWords ? minNumWords : minNumWordsValue;
			minNumWordsValue = minNumWordsValue > maxNumWords ? maxNumWords : minNumWordsValue;
			minNumWordsValue = minNumWordsValue > maxNumWordsValue ? maxNumWordsValue : minNumWordsValue;
			numWordsTF.setValue(minNumWordsValue);
		} 
	}	
}


