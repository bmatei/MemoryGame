package memory.game;

import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Card extends JPanel{
	/* front and back images of the card */
	private BufferedImage front, back;
	/* image on display */
	private BufferedImage display;
	/* used to match cards of the same types. ranges in 1-27*/
	private int UID;
	/* used to check how many instances of a particular UID have been
	 * already created
	 */
	private static int[] instancesOfUID = {	0, 0, 0, 0, 0, 0, 0, 0, 0, 
											0, 0, 0, 0, 0,};
	//the mouse listener to trigger clicks
	private MouseListener listener;
	
	//id in ArrayList of Cards
	private int instance;
	
	//game instance
	private GameGUI parent;
	
	//constructor
	public Card(GameGUI parent, int id, int instance){
		UID = id;
		Card.instancesOfUID[id-1]++;
		this.instance = instance;
		this.parent = parent;
		//the mouse listener handling the clicks
		listener = new MouseListener(){
			@Override
			public void mouseEntered(MouseEvent ev){}
			@Override
			public void mouseClicked(MouseEvent ev){}
			@Override
			public void mousePressed(MouseEvent ev){}
			@Override
			public void mouseReleased(MouseEvent ev){
				switchImage();
			}
			@Override
			public void mouseExited(MouseEvent ev){}
			
		};
		
		this.addMouseListener(listener);
		
		
		front = parent.getImgs().get( ((UID-1) * 4) + instancesOfUID[UID-1]);
		back = parent.getImgs().get(0);
		display = back;
	}
	
	//block input on found pairs
	public void stopActions(){
		this.removeMouseListener(listener);
	}
	
	//an id of 1-13 means a regular card
	//an id of 14 means a joker
	public static boolean allow(int id){
		if(id == 14)
			if(instancesOfUID[13] == 2) return false;
			else return true;
		else 	
			if(instancesOfUID[id-1] == 4) return false;
			else return true;
	}
	
	//reset the instances
	public static void reset(){
		for(int i = 0; i < 14; i++)
			instancesOfUID[i] = 0;
	}
	
	//getter for UID
	public int getUID(){
		return UID;
	}
	
	//switch image on or off 
	private void switchImage(){
		GameMechanics.reveal(instance);
		if(display == back) display = front;
		else display = back;
		this.repaint();
	}
	
	//reset image
	public void switchBackImage(){
		display = back;
		this.repaint();
	}
	
	
	//paint the card with it's image
	@Override
	public void paintComponent(Graphics g){
		g.drawImage((Image)display, 0, 0, null);
		//g.drawString(String.valueOf(UID), 10, 10);//<--for debugging
	}

}
