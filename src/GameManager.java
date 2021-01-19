//package domino;

public class GameManager {

	private DominoPiece[] pieces;
	private DominoTeam[] teams;
	private GameBoard board;
	
	public GameManager() {
		this.pieces = CreateAllPieces();
		this.teams = new DominoTeam[2];
		this.board = new GameBoard();
	}
		
	public GameManager(DominoTeam[] teams, int numOfPiecesPerPlayer) {
		this.pieces = CreateAllPieces();
		this.teams = DuplicateArray(teams);
		dealDominoPieces(numOfPiecesPerPlayer);
		this.board = new GameBoard();
	}
	
	public GameManager (DominoTeam[] teams) {
		this.pieces = CreateAllPieces();
		for (int i = 0; i < this.pieces.length; i = i + 1) {
			this.pieces[i] = new DominoPiece();
		}
		this.teams = DuplicateArray(teams);
		this.board = new GameBoard();
	}
	
	public GameManager (GameManager other) {
		this.pieces = DuplicateArray(other.pieces);
		this.teams = DuplicateArray(teams);
		this.board = new GameBoard(other.board);
	}
	
	private DominoPiece[] CreateAllPieces() {
		DominoPiece [] allPieces = new DominoPiece[55];
		int index = 0;
		for (int i = 0; i <= 9; i = i + 1) {
			for (int k = i; k <= 9; k = k + 1) {
				allPieces[index] = new DominoPiece(i,k);
				index = index + 1;
			}
		}
		return allPieces;
	}
	
	// Duplicate a given array into a new array
	private DominoTeam[] DuplicateArray(DominoTeam[] other) {
		DominoTeam[] newArray = new DominoTeam[other.length];
		for (int i = 0; i < newArray.length; i = i + 1)
			newArray[i] = other[i];
			return newArray;
	}
	
	// Duplicate a given array into a new array
	private DominoPiece[] DuplicateArray(DominoPiece[] other) {
		DominoPiece[] newArray = new DominoPiece[other.length];
		for (int i = 0; i < newArray.length; i = i + 1)
			newArray[i] = other[i];
		return newArray;
	}
	
	public String toString() {
		String ans = "";
		for (int i = 0; i < this.pieces.length; i = i + 1)
			ans = ans + this.pieces[i].toString() + ",";
		ans = ans + "\n";
		for (int i = 0; i < this.teams.length; i = i + 1)
			ans = ans + this.teams[i].toString();
		ans = ans + this.board.toString();
		return ans;
	}
	
	public boolean equals(GameManager other) {
		boolean ans = true;
		for (int i = 0; i < this.teams.length; i = i + 1)
			if (!this.teams[i].equals(other.teams[i]))
				ans = false;
		return this.board.equals(other.board) && ans;
	}
	
	// Give every player from every team a specific quantity of new DominoPieces
	private void dealDominoPieces (int dominoPiecesPerPlayer) {
		// run on all teams
		for (int i = 0; i < this.teams.length; i = i + 1) {
			DominoPlayer[] players = this.teams[i].getPlayers();
			// give every player new DominoPieces
			for (int j = 0; j < this.teams[i].getNumberOfPlayers(); j = j + 1)
				players[j].giveDominoPieces(RandomPieces(dominoPiecesPerPlayer));
		}
	}
	
	// gets a quantity of pieces each player will get and returns an array of random pieces
	private DominoPiece[] RandomPieces (int numOfPiecesToRandom)	{
		DominoPiece[] randomPieces = new DominoPiece[numOfPiecesToRandom];
		for (int i = 0; i < numOfPiecesToRandom; i = i + 1){
			int rnd=RandomIndex();
			randomPieces[i] = this.pieces[rnd];
			updatePieces(rnd);
		}
		return randomPieces;
	}
	
	// returns a random index of a piece
	private int RandomIndex() {
		int randomIndex = (int) Math.round(Math.random() * getRightIndex(this.pieces));
		return randomIndex;
		
		
	}
	
	// Update the DominoPiece array
	private void updatePieces(int index) {
		for (int i = index; i < getRightIndex(this.pieces); i = i + 1)
			this.pieces[i] = this.pieces[i+1];
		this.pieces[getRightIndex(this.pieces)] = new DominoPiece();
	}
	
	// Return the index of the most right not empty cell of the array
	// if the whole array is empty the output will be -1
	private int getRightIndex(DominoPiece[] arr) {
		int i = 0;
		boolean isEnd = false;
		while (i < arr.length && !isEnd) {
			if (arr[i].getLeft() != -1 && arr[i].getRight() != -1)
				i = i + 1;
			else
				isEnd = true;
		}
		if (i == 0)
			return -1;
		else
			return i - 1;
	}

	public void play() {
		int countStuckTeam = 0;
		boolean stillPieces = true;
		// play while the game is not over
		while (countStuckTeam != this.teams.length  && stillPieces) {
			countStuckTeam = 0;
			// play a round of the game
			for (int i = 0; i < this.teams.length && stillPieces; i = i + 1) {
				// check if the current player has no move
				if (!teams[i].playMove(this.board)) {
					System.out.println(this.teams[i].getName() + ", " + "pass" + ": " + this.board.toString());
					countStuckTeam = countStuckTeam + 1;
				}
				else {
					System.out.println(this.teams[i].getName() + ", " + "success" + ": " + this.board.toString());
					// check if the current player won
					if (!this.teams[i].hasMorePieces())
						stillPieces = false;
				}
			}
			
		}
		Result();
	}
	
	// prints the result of the game
	private void Result() {
	DominoTeam lessPointTeam = this.teams[0];
	int minPoints = this.teams[0].countTeamScore();
	// check which team is the leader
	for (int i = 1; i < this.teams.length; i = i + 1 ) {
		if (minPoints > this.teams[i].countTeamScore()) {
			minPoints = this.teams[i].countTeamScore();
			lessPointTeam = this.teams[i];
		}
	}
	// check if it's a draw
	boolean isDraw = false;
	for (int j = 0; j < this.teams.length && !isDraw; j = j + 1 ) {
		if (minPoints == this.teams[j].countTeamScore() && !lessPointTeam.equals(this.teams[j]) )
			isDraw = true;
	}
	// print the name and score of the teams
	for (int i = 0; i < this.teams.length ; i = i + 1 )
		System.out.println(this.teams[i].getName() +", score: " + this.teams[i].countTeamScore());
	if(isDraw)
		System.out.println("It's a tie!");
	else
		System.out.println(lessPointTeam.getName() + " won the game!");	
	}
}

