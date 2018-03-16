package memory.game;

import java.awt.event.WindowEvent;

public class MemoryGame {

	//Runs a new instance of the game and if an older
	//one is already running, terminates it
	public static void RUN_GAME(GameGUI old){
		
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameGUI().setVisible(true);
            }
        });
		if(old != null){
			old.terminate();
		}
		
	}
	
	public static void main(String[] args) {
		RUN_GAME(null);
	}

}
