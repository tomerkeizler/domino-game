//package domino;

public class PlayGame {

	public static void main(String[] args) {
		DominoPlayer[] t1 = {new DominoPlayer("ran")};
		DominoPlayer[] t2 = {new DominoPlayer("efrat")};
		
		DominoTeam team1 = new DominoTeam("t1", t1);
		DominoTeam team2 = new DominoTeam("t2", t2);
		
		DominoTeam teams[] = {team1,team2};
		
		GameManager game = new GameManager(teams, 10);
		game.play();
	}
}


