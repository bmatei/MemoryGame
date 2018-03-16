package memory.game;

public class GameMechanics {
	//counts currently revealed cards
	private static int revealed;
	//stores the UIDs of the currently revealed cards
	private static int[] cardsRevealed;
	//counts how many pairs were found
	private static int found;
	//counts how many moves were made
	private static int moves;
	//false if game still running, true if game over
	private static boolean gameOver;
	//the instance of the game
	private static GameGUI game;
	
	public static void init(GameGUI arg){
		revealed = 0;
		cardsRevealed = new int[2];
		found = 0;
		moves = 0;
		gameOver = false;
		game = arg;
	}
	
	public static void reveal(int id){
		moves++;
		String str = "Moves: " + String.valueOf(moves);
		game.getMovesLabel().setText(str);
		if(canReveal()){
			cardsRevealed[revealed] = id;
			if(revealed == 1){
				if(game.getCardId(cardsRevealed[0]) == game.getCardId(cardsRevealed[1])){
					if(cardsRevealed[0] != cardsRevealed[1])
						find();
					else
						revealed++;
				}
				else
					revealed++;
			} else
				revealed++;
			
		} else {
			game.unreveal(cardsRevealed[0]);
			game.unreveal(cardsRevealed[1]);
			resetRevealed();
			reveal(id);
		}
	}
	
	public static boolean canReveal(){
		return (revealed != 2);
	}
	
	public static int getMoves(){
		return moves;
	}
	
	private static void resetRevealed(){
		cardsRevealed[0] = 0;
		cardsRevealed[1] = 0;
		revealed = 0;
	}
	
	public static void find(){
		found++;
		String str = "Found: " + String.valueOf(found);
		game.getFoundLabel().setText(str);
		game.getCard(cardsRevealed[0]).stopActions();
		game.getCard(cardsRevealed[1]).stopActions();
		resetRevealed();
		if(found == 27)
			gameOver = true;
	}
	
	public static int getFound(){
		return found;
	}
	
	public static boolean isGameOver(){
		return gameOver;
	}
	
}
