//package domino;


public class DominoPlayer {
	
	private String name;
	private DominoPiece[] pieces;
	
	public DominoPlayer() {
		this.name = "Default Player";
		this.pieces = new DominoPiece[55];
		for (int i = 0; i < this.pieces.length; i = i + 1) {
			this.pieces[i] = new DominoPiece();
		}
	}
	
	public DominoPlayer(String name, DominoPiece[] pieces) {
		this.name = name;
		this.pieces = new DominoPiece[55];
		// initialize DominoPiece array with empty DominoPeices with value of -1
		for (int i = 0; i < this.pieces.length; i = i + 1) {
			this.pieces[i] = new DominoPiece();
		}
		// give the player the DominoPieces he should start the game with
		this.giveDominoPieces(pieces);
	}
	
	public DominoPlayer(String name) {
		this.name = name;
		this.pieces = new DominoPiece[55];
		// initialize DominoPiece array with empty DominoPeices with value of -1
		for (int i = 0; i < this.pieces.length; i = i + 1) {
			this.pieces[i] = new DominoPiece();
		}
	}
	
	public DominoPlayer(DominoPlayer other) {
		this.name = other.name;
		this.pieces = DuplicateArray(other.pieces);
	}
	
	// Duplicate a given array into a new array
	private DominoPiece[] DuplicateArray(DominoPiece[] other) {
		DominoPiece[] newArray = new DominoPiece[other.length];
		for (int i = 0; i < newArray.length; i = i + 1)
			newArray[i] = other[i];
		return newArray;
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
	
	// Gives the player more Domino Pieces
	public void giveDominoPieces(DominoPiece[] pieces) {
		int k = 0;
		for (int i = getRightIndex(this.pieces) + 1; (i < this.pieces.length) && (k < pieces.length); i = i + 1) {
			this.pieces[i] = pieces[k];
			k = k + 1;
		}
	}
	
	public boolean playMove(GameBoard gameBoard) {
		boolean isMove = false;
		// Check all the cases of a possible move
		for (int i = 0; i < this.pieces.length && !isMove; i = i + 1) {
			// Check if there is a possible move on the left or right side of the board
			isMove = gameBoard.AddToLeftEnd(this.pieces[i]) || gameBoard.AddToRightEnd(this.pieces[i]);
			// update the DominoPiece array of the player if a move has been played
			if (isMove)
				updatePieces(i);
		}
		return isMove;
	}
	
	// Update the DominoPiece array of the player according to the move he played
	private void updatePieces(int index) {
		for (int i = index; i < getRightIndex(this.pieces); i = i + 1)
			this.pieces[i] = this.pieces[i+1];
		this.pieces[getRightIndex(this.pieces)] = new DominoPiece();
	}
	
	// Count the sum of the Domino Pieces of the player
	public int countScore() {
		int counter = 0;
		int rightEdge = getRightIndex(this.pieces);
		if (rightEdge >= 0) {
			for (int i = 0; i <= rightEdge; i = i + 1)
				counter = counter + this.pieces[i].getLeft() + this.pieces[i].getRight();
		}
		return counter;
	}
	
	public boolean hasMoreDominoPieces() {
		if (getRightIndex(this.pieces) >= 0)
			return true;
		else
			return false;
	}
	
	// Two players are equal if they have the same score
	public boolean equals(DominoPlayer other) {
		return this.countScore() == other.countScore();
	}
	
	public String toString() {
		// First return the player name
		String ans = this.name + " ";
		// Then return the domino pieces of the player
		boolean isEnd = false;
		for (int i = 0; i < this.pieces.length && !isEnd; i = i + 1) {
			if (this.pieces[i].getRight() == -1)
				isEnd = true;
			else
				ans = ans + this.pieces[i].toString() + ",";
		}
		if (!ans.isEmpty())
			ans = ans.substring(0, ans.length() - 1);
		return ans;
	}
}

