package paw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class WelcomePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PAWgui internalgui;
	private Color bgColor;
	private Color btnColor;
	
	public WelcomePanel(Color color, PAWgui paw) {
		bgColor = color;
		btnColor = Config.WELCOME_PANEL_BUTTONS;
		internalgui = paw;
		
		setMinimumSize(new Dimension(640,480));
		setBackground(bgColor);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new GridLayout(1, 2));
		
		JPanel pic = new JPanel();
		pic.setBackground(bgColor);
		JLabel logoImage = new JLabel("", new ImageIcon(Config.LOGO_FILE), SwingConstants.CENTER);
		pic.add(logoImage);
		add(pic);
		
		JPanel text = new JPanel();
		text.setBackground(bgColor);
		text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
		text.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		JTextPane title = new JTextPane();
		title.setBackground(bgColor);
		title.setText(Config.WELCOME_TITLE);
		title.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 30));
		text.add(title);
		
		JTextPane body = new JTextPane();
		body.setBackground(bgColor);
		if(Config.DEFAULTMODE.equals("admin")){
			body.setText(Config.ADMIN_WELCOME);
		}else{
			body.setText(Config.WELCOME_MSG);
		}
		body.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 30));
		text.add(body);
		
		
		JPanel button = new JPanel();
		button.setBackground(bgColor);
		button.setLayout(new FlowLayout());
//		gameLevel = getGameLevel();
		if(Config.DEFAULTMODE.equals("user")){
			JButton setLevelOne = new JButton("Easy");
			setLevelOne.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						internalgui.setUserGameLevel(1);
						internalgui.selectTabbedPaneIndex(1);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			setLevelOne.setSize(100, 100);
			setLevelOne.setFont(new Font("Sitka Display", Font.BOLD, 16));
			setLevelOne.setBackground(btnColor);
			button.add(setLevelOne);
			
			JButton setLevelTwo = new JButton("Medium");
			setLevelTwo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						internalgui.setUserGameLevel(2);
						internalgui.selectTabbedPaneIndex(1);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			setLevelTwo.setSize(100, 50);
			setLevelTwo.setFont(new Font("Sitka Display", Font.BOLD, 16));
			setLevelTwo.setBackground(btnColor);
			button.add(setLevelTwo);
			
			JButton setLevelThree = new JButton("Hard");
			setLevelThree.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						internalgui.setUserGameLevel(3);
						internalgui.selectTabbedPaneIndex(1);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			setLevelThree.setSize(100, 50);
			setLevelThree.setFont(new Font("Sitka Display", Font.BOLD, 16));
			setLevelThree.setBackground(btnColor);
			button.add(setLevelThree);
			
			JButton setLevelFour = new JButton("Impossible");
			setLevelFour.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						internalgui.setUserGameLevel(4);
						internalgui.selectTabbedPaneIndex(1);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			setLevelFour.setSize(100, 50);
			setLevelFour.setFont(new Font("Sitka Display", Font.BOLD, 16));
			setLevelFour.setBackground(btnColor);
			button.add(setLevelFour);
			
		}else{
			JButton goToConfig = new JButton("Ready to Configure New Games!");
			goToConfig.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						internalgui.selectTabbedPaneIndex(3);		
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			goToConfig.setSize(100, 50);
			goToConfig.setFont(new Font("Sitka Display", Font.BOLD, 16));
			goToConfig.setBackground(btnColor);
			button.add(goToConfig);
		}
		text.add(button);
		add(text);
	}


}