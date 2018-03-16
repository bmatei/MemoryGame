package memory.game;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.util.ArrayList;
import java.util.Random;

public class GameGUI extends JFrame{
	/* LayoutManager for this Graphical User interface */
	private GridLayout layout;
	/* This holds all the cards on the screen (54 cards) */
	private ArrayList<Card> cards;
	/* Images needed for the cards */
	public ArrayList<BufferedImage> imgs;
	/* Height and Width values for the cards to be shown */
	public static int CARD_WIDTH = 40, CARD_HEIGHT =50;
	/* Container for the score */
	private JPanel scoreContainer;
	/* Will present the score */
	private JLabel movesLabel, foundLabel;
	/* Reset button */
	private JButton reset;
	
	
	
	//constructor
	public GameGUI(){
		//init JFrame
		this.setTitle("Memory Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		//init LayoutManager
		layout = new GridLayout(10, 6); //10 rows, 6 collumns
		layout.setHgap(5); //5 pixels horizontal gap
		layout.setVgap(5); //5 pixels vertical gap	
		this.setLayout(layout);
		
		//load data into memory
		initImages();
		
		//initialize cards
		initCards();
		int sz = cards.size();
		for(int i = 0; i < sz; ++i){
			this.add(cards.get(i));
			cards.get(i).setVisible(true);
		}
		
		//initialize the score board
		scoreContainer = new JPanel();
		scoreContainer.setBorder(BorderFactory.createLineBorder(Color.black));
		scoreContainer.setLayout(new GridLayout(2, 1));
		movesLabel = new JLabel("Moves: 0");
		movesLabel.setVisible(true);
		movesLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
		scoreContainer.add(movesLabel);
		foundLabel = new JLabel("Found: 0");
		foundLabel.setVisible(true);
		foundLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
		scoreContainer.add(foundLabel);
		this.add(new JPanel()); //dummy panel for alignment
		this.add(new JPanel()); //dummy panel for alignment
		this.add(scoreContainer);
		scoreContainer.setVisible(true);
		//handle closing events
		addWindowListener(new WindowListener() {
            public void windowClosing(WindowEvent e) {
                terminate();
            }
            public void windowClosed(WindowEvent e){}
            public void windowOpened(WindowEvent e){}
            public void windowDeactivated(WindowEvent e){}
            public void windowDeiconified(WindowEvent e){}
            public void windowIconified(WindowEvent e){}
            public void windowActivated(WindowEvent e){}
        });
		 
		
		reset = new JButton("Reset");
		reset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ev){
				run_game();
			}
		});
		reset.setVisible(true);
		this.add(reset);
		
		this.pack();
		GameMechanics.init(this);

	}
	
	//terminate the JFrame
	public void terminate(){
		this.setVisible(false);
		dispose();
	}
	
	//reset button action
	private void run_game(){
		Card.reset();
		MemoryGame.RUN_GAME(this);
	}
	
	//initialize the cards array
	private void initCards(){
		cards = new ArrayList<Card>();
		for(int i = 0; i < 54; i++){
			int test = 0;
			do{
				test = randomID();
			} while(!Card.allow(test));
			Card c = new Card(this, test, i);
			c.setPreferredSize(new Dimension(GameGUI.CARD_WIDTH, GameGUI.CARD_HEIGHT));
			cards.add(c);
		}
	}
	
	//getter for the moves label
	public JLabel getMovesLabel(){
		return movesLabel;
	}
	
	//getter for the found label
	public JLabel getFoundLabel(){
		return foundLabel;
	}
	
	//reset card indexed at id
	public void unreveal(int id){
		cards.get(id).switchBackImage();
	}
	
	//get the UID of the card indexed at id
	public int getCardId(int id){
		return cards.get(id).getUID();
	}
	
	//get the card with index id
	public Card getCard(int id){
		return cards.get(id);
	}
	
	//generate random id for card
	private int randomID(){
		Random gen = new Random();
		return gen.nextInt(14)+1;
	}
	
	//getter for the images array
	public ArrayList<BufferedImage> getImgs(){
		return imgs;
	}
	
	//initialize an array with the images
	private void initImages(){
		imgs = new ArrayList<BufferedImage>();
		char[] type = {'s', 'c', 'd', 'h'};
		String url = "cards/b.gif";
		imgs.add(ImageUtilities.resizeImage(
				ImageUtilities.readImageFromURL(url),
				GameGUI.CARD_WIDTH, GameGUI.CARD_HEIGHT, true));
		for(int i = 1; i <= 52; ++i){
			url = "cards/" + String.valueOf((i-1)/4 + 1) + type[i%4] + ".gif";
			imgs.add(ImageUtilities.resizeImage(
					ImageUtilities.readImageFromURL(url),
					GameGUI.CARD_WIDTH, GameGUI.CARD_HEIGHT, true));
		}
		url = "cards/j.gif";
		BufferedImage joker = ImageUtilities.resizeImage(
				ImageUtilities.readImageFromURL(url),
				GameGUI.CARD_WIDTH, GameGUI.CARD_HEIGHT, true);
		imgs.add(joker);
		joker = ImageUtilities.resizeImage(
				ImageUtilities.readImageFromURL(url),
				GameGUI.CARD_WIDTH, GameGUI.CARD_HEIGHT, true);
		imgs.add(joker);
	}
	
	
}
