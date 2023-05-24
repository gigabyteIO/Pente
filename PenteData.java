import java.awt.Point;

/**
 * This class represents a Pente board which is a 2D array of integers.
 * Each position can be empty(0), white(1), or black(2).
 * @author martin
 *
 */
public class PenteData {
	
	/* This array represents directional moves in a 2D grid. */
	Point[] direction = new Point[] { new Point(0, -1),  new Point(0, 1),     /* LEFT, RIGHT */
								   	  new Point(1, 0),   new Point(-1, 0),	  /* TOP, BOTTOM */
								      new Point(1, -1),  new Point(1, 1),	  /* UPPER LEFT, UPPER RIGHT */
								      new Point(-1, -1), new Point(-1, 1) };  /* LOWER LEFT, LOWER RIGHT */
	
	/* These allow drawing of a line through the winning squares. */
	/* They are set to points off the board initially. */
	
	int win_r1, win_c1, win_r2, win_c2;

	/* The three states a tile can be in. */
	static final int
				EMPTY = 0,
				WHITE = 1,
				BLACK = 2;
	
	/* 2D array of int that represents the board. */
	int[][] board;
	
	/**
	 * Constructor which allows any amount of rows and columns. 
	 * @param rows
	 * @param columns
	 */
	public PenteData(int rows, int columns) {
		board = new int[rows][columns];
	}
				
	/**
     * Return the contents of the square in the specified row and column.
     */
	public int pieceAt(int row, int col) {
		return board[row][col];
	}
	
	/**
	 * Returns true if the square is empty, false otherwise(the square is occupied).
	 */
	public boolean isLegalMove(int row, int col) {
		if(pieceAt(row, col) == EMPTY)
			return true;
		else
			return false;
	}
	
	/**
	 * Changes the square state to the given player color if it is a legal move(the square is empty).
	 */
	public void makeMove(int row, int col, int playerColor) {	
		if(isLegalMove(row, col)) 
			board[row][col] = playerColor;	
	}
	
	/**
	 * This method checks to see if a player has won the game by getting 5 or more squares in a row.
	 * 
	 * @param row the row the player clicked on.
	 * @param col the column the player clicked on.
	 * @param player the player(black or white).
	 * @return true if the player has won the game.
	 */
	public boolean gameWon(int row, int col, int player) {
		int ct;  	 // Number of pieces in a row belonging to the player.
		int r, c;    // A row and column to be examined.
		int dirX, dirY;	// The current direction of the x-axis and y-axis(can be either -1, 0, +1).

		//winningSquare = new Point(row, col);
		for(int i = 0; i < direction.length; i++) {
			ct = 1;
			dirX = (int) direction[i].getX();
			dirY = (int) direction[i].getY();
			
			/* Look at square in specified direction. */
			r = row + dirX;  
			c = col + dirY;
			while ( r >= 0 && r < 13 && c >= 0 && c < 13 
					&& board[r][c] == player ) {
				// Square is on the board, and it 
				// contains one of the player's pieces.
				ct++;
				if(ct >= 5) {
					// Game is over. Return true.			
					// The next lines determine
					win_r1 = r;
					win_c1 = c;
					win_r2 =  (r -= dirX * 4);  
					win_c2 =  (c -= dirY * 4);
					System.out.println("1: R1: " + win_r1 + ", C1: " + win_c1);
					System.out.println("1: R2: " + win_r2 + ", C2: " + win_c2);
					return true;
				}
				r += dirX;  // Go on to next square in this direction.
				c += dirY;
			}

			/* Now, look in the opposite direction. */
			r = row - dirX; 
			c = col - dirY;
			while ( r >= 0 && r < 13 && c >= 0 && c < 13 
					&& board[r][c] == player ) {
			   ct++;
			   if(ct >= 5) {
				   // Game is over. Return true.
				   // Last in the winning row. 
				   win_r1 = r;
				   win_c1 = c;
				   win_r2 = (r += dirX * 4);  
				   win_c2 = (c += dirY * 4);
				   System.out.println("2: R1: " + win_r1 + ", C1: " + win_c1);
				   System.out.println("2: R2: " + win_r2 + ", C2: " + win_c2);
				   return true;
				}
			   r -= dirX;   // Go on to next square in this direction.
			   c -= dirY;
			}
		}		
		return false;
	}
	
	
	
	/**
	 * Resets the board to an empty state. 
	 */
	public void resetBoard(int rows, int columns) {
		board = new int[rows][columns];
	}
	
}
