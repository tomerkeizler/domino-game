//package domino;


public class DominoTeam {
	
	private String teamName;
	private int nextPlayer;
	private DominoPlayer[] players;

	public DominoTeam() {
		this.teamName = "Default Team";
		this.nextPlayer = 0;
		this.players = new DominoPlayer[2];
		for (int i = 0; i < this.players.length; i = i + 1) {
			this.players[i] = new DominoPlayer();
		}
	}

	public DominoTeam(String name, DominoPlayer[] players) {
		this.teamName = name;
		this.nextPlayer = 0;
		this.players = DuplicateArray(players);
	}
	
	public DominoTeam(DominoTeam other) {
		this.teamName = other.teamName;
		this.nextPlayer = 0;
		this.players = DuplicateArray(other.players);
	}
	
	public DominoPlayer[] getPlayers() {
		return this.players;
	}
	
	// Duplicate a given array into a new array
	private DominoPlayer[] DuplicateArray(DominoPlayer[] other) {
		DominoPlayer[] newArray = new DominoPlayer[other.length];
		for (int i = 0; i < newArray.length; i = i + 1)
			newArray[i] = other[i];
		return newArray;
	}
		
	public String getName() {
		return this.teamName;
	}
	
	public boolean playMove(GameBoard board) {
		boolean played = false;
		// try to play a move by the players numbered after the current player
		for (int i = this.nextPlayer; i < this.getNumberOfPlayers() && !played; i = i + 1) {
			if (this.players[i].playMove(board)) {
				played = true;
				this.nextPlayer = i + 1;
			}
		}
		// try to play a move by the players numbered before the current player
		for (int i = 0; i < this.nextPlayer && !played; i = i + 1) {
			if (this.players[i].playMove(board)) {
				played = true;
				this.nextPlayer = i + 1;
			}
		}
		if (played)
			return true;
		else
			return false;
	}
	
	// Count the sum of the Domino Pieces of the teams players
	public int countTeamScore() {
		int sum = 0;
		for (int i = 0; i < this.players.length; i = i + 1)
			sum = sum + this.players[i].countScore();
		return sum;
	}
	
	// Check if at least one of the players has domino pieces of his own
	public boolean hasMorePieces() {
		for (int i = 0; i < this.players.length; i = i + 1) {
			if (this.players[i].hasMoreDominoPieces())
				return true;
		}
		return false;
	}
	
	public int getNumberOfPlayers() {
		return this.players.length;
	}
	
	// Assign domino pieces to players using the giveDominoPieces method from class DominoPlayer
	public void assignPiecesToPlayers(DominoPiece[][] playersPieces) {
		for (int i = 0; i < this.players.length; i = i + 1)
			this.players[i].giveDominoPieces(playersPieces[i]);
	}
	
	// Two teams are equal if they have the same number of players and the same overall score
	public boolean equals(DominoTeam other) {
		return (this.getNumberOfPlayers() == other.getNumberOfPlayers()) && (this.countTeamScore() == other.countTeamScore());
	}
	
	public String toString() {
		// First return the team name
		String ans = this.teamName + "\n";
		// Then return the domino players of the team
		for (int i = 0; i < this.players.length; i = i + 1)
			ans = ans + this.players[i].toString() + "\n";
		return ans;
	}
}
