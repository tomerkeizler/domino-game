//package domino;


public class DominoPiece {
	
	private int leftNum;
	private int rightNum;
	
	// in case of an empty constructor the numbers will be -1
	public DominoPiece() {
		this.leftNum = -1;
		this.rightNum = -1;
	}
	
	public DominoPiece(int leftNum, int rightNum) {
		this.leftNum = leftNum;
		this.rightNum = rightNum;
	}
	
	public DominoPiece(DominoPiece other) {
		this.leftNum = other.leftNum;
		this.rightNum = other.rightNum;
	}
	
	// flip the right and the left numbers
	public void flipDominoPiece() {
		int temp = this.leftNum;
		this.leftNum = this.rightNum;
		this.rightNum = temp;
	}
	
	public String toString() {
		return "[" + this.leftNum + "|" + this.rightNum + "]";
	}
	
	public boolean equals(DominoPiece other) {
		return (this.leftNum == other.leftNum) && (this.rightNum == other.rightNum);
	}
	
	public int getRight() {
		return this.rightNum;
	}
	
	public int getLeft() {
		return this.leftNum;
	}
	
}
