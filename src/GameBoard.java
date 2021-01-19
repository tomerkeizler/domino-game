//package domino;


public class GameBoard {
	
	private int maxNumOfTiles;
	private DominoPiece[] boardStateLeft;
	private DominoPiece[] boardStateRight;
	
	// in case of an empty builder the board size will be 0
	public GameBoard() {
		this.maxNumOfTiles = 10;
		this.boardStateLeft = new DominoPiece[this.maxNumOfTiles];
		this.boardStateRight = new DominoPiece[this.maxNumOfTiles];
		for (int i = 0; i < this.boardStateLeft.length; i = i + 1) {
			this.boardStateLeft[i] = new DominoPiece();
			this.boardStateRight[i] = new DominoPiece();
		}
	}
	
	public GameBoard(int maxNumOfTiles) {
		this.maxNumOfTiles = maxNumOfTiles;
		this.boardStateLeft = new DominoPiece[this.maxNumOfTiles];
		this.boardStateRight = new DominoPiece[this.maxNumOfTiles];
		// initialize the right and left DominoPiece arrays with empty DominoPeices with value of -1
		for (int i = 0; i < maxNumOfTiles; i = i + 1) {
			boardStateLeft[i] = new DominoPiece();
			boardStateRight[i] = new DominoPiece();
		}
	}
	
	public GameBoard(GameBoard other) {
		this.maxNumOfTiles = other.maxNumOfTiles;
		this.boardStateLeft = DuplicateArray(other.boardStateLeft);
		this.boardStateRight = DuplicateArray(other.boardStateRight);
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
		// First return the left array
		int i = getLeftIndex(this.boardStateLeft);
		if (i != -1) {
			while (i < this.boardStateLeft.length - 1) {
				ans = ans + this.boardStateLeft[i].toString() + ",";
				i = i + 1;
			}
		}
		// Then return the right array
		i = 0;
		while (i <= getRightIndex(this.boardStateRight)) {
			ans = ans + boardStateRight[i].toString() + ",";
			i = i + 1;
		}
		if (!ans.isEmpty())
			ans = ans.substring(0, ans.length() - 1);
		return ans;
	}
	
	public boolean equals(GameBoard other) {
		// First check if the arrays size is equal
		boolean ans = this.maxNumOfTiles == other.maxNumOfTiles;
		if (ans) {
			DominoPiece[] thisBoard = this.getBoardState();
			DominoPiece[] otherBoard = other.getBoardState();
			// Compare the cells of the arrays
			if (thisBoard.length == otherBoard.length) {
				for (int i = 0; i < thisBoard.length; i = i + 1)
					if (!thisBoard[i].equals(otherBoard[i]))
						ans = false;
			}
		}
		return ans;
	}
	
	// Return the index of the most left not empty cell of the array
	// if the whole array is empty the output will be -1
	private int getLeftIndex(DominoPiece[] arr) {
		int i = arr.length - 1;
		boolean isEnd = false;
		while (i >= 0 && !isEnd) {
			if (arr[i].getLeft() != -1 && arr[i].getRight() != -1)
				i = i - 1;
			else
				isEnd = true;
		}
		if (i == arr.length-1)
			return -1;
		else
			return i + 1;
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
	
	public int getRightDominoValue() {
		int index = getRightIndex(this.boardStateRight);
		if (index >= 0)
			return this.boardStateRight[index].getRight();
		else
			return -1;
	}
	
	public int getLeftDominoValue() {
		int index = getLeftIndex(this.boardStateLeft);
		if (index >= 0)
			return this.boardStateLeft[index].getLeft();
		else
			return -1;
	}
	
	public DominoPiece[] getBoardState() {
		DominoPiece[] allBoard = new DominoPiece[this.maxNumOfTiles];
		int k = 0;
		// copy the left array cells to the full board state
		for (int i = getLeftIndex(this.boardStateLeft); i < this.boardStateLeft.length - 1; i = i + 1) {
			allBoard[k] = this.boardStateLeft[i];
			k = k + 1;
		}
		// copy the right array cells to the full board state
		for (int i = 1; i <= getRightIndex(this.boardStateRight); i = i + 1) {
			allBoard[k] = this.boardStateRight[i];
			k = k + 1;
		}
		return allBoard;
	}
	
	// Add a domino piece in the middle of the board if it is empty
	public boolean AddToMiddle(DominoPiece piece) {
		if (this.getRightDominoValue() == -1 && this.getLeftDominoValue() == -1) {
			this.boardStateRight[0] = new DominoPiece(piece);
			this.boardStateLeft[this.boardStateLeft.length - 1] = new DominoPiece(piece);
			return true;
		}
		return false;
	}
	
	public boolean AddToRightEnd(DominoPiece piece) {
		// if the board is empty then add a domino piece in the middle of it
		if (AddToMiddle(piece))
			return true;
		else {
			boolean ans = false;
			// Check if the given DominoPiece is suitable
			if (this.getRightDominoValue() == piece.getLeft())
				ans = true;
			// Check if the given DominoPiece flip position is suitable
			else if (this.getRightDominoValue() == piece.getRight()) {
				piece.flipDominoPiece();
				ans = true;
			}
			int nextPlace = getRightIndex(this.boardStateRight) + 1;
			if (ans && nextPlace < this.boardStateRight.length)
				this.boardStateRight[nextPlace] = new DominoPiece(piece);
			return ans;
		}
	}
	
	public boolean AddToLeftEnd(DominoPiece piece) {
		// if the board is empty then add a domino piece in the middle of it
		if (AddToMiddle(piece))
			return true;
		else {
			boolean ans = false;
			// Check if the given DominoPiece is suitable
			if (this.getLeftDominoValue() == piece.getRight())
				ans = true;
			// Check if the given DominoPiece flip position is suitable
			else if (this.getLeftDominoValue() == piece.getLeft()) {
				piece.flipDominoPiece();
				ans = true;
			}
			int nextPlace = getLeftIndex(this.boardStateLeft) - 1;
			if (ans && nextPlace >= 0)
				this.boardStateLeft[nextPlace] = new DominoPiece(piece);
			return ans;
		}
	}
}
