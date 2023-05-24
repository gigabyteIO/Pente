import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Represents a graphical Pente board with an initial size of 324x324 pixels.
 * @author martin
 *
 */
public class PenteBoard extends Canvas {
	
	PenteData board;		// The data for the PenteBoard is kept here.
	boolean gameInProgress;	// Keeps track of if a game is in progress.
	boolean haveWon;		//
	String message;			// Displays messages to the players.
	
	/* These variables are valid only when game is in progress. */
	int currentPlayer;		// Keeps track of which players turn it is.
	int clickedRow, clickedCol;
	int clickedSquares;
	
	public PenteBoard() {
		super(324, 324); // canvas is 324-by-324 pixels.
		board = new PenteData(13, 13); // 13 rows and 13 columns.
		message = "Click \"New Game\" to start a new game.";
	}
	
	/**
	 * Resets variables, messages, and board for a new game. 
	 */
	public void doNewGame() {
		currentPlayer = PenteData.WHITE;
		gameInProgress = true;
		haveWon = false;
		board.resetBoard(13, 13);
		clickedSquares = 0;
		message = "Make a move WHITE!";
		drawBoard();
	}
	
	/**
	 * Resigns the current player from the game.
	 */
	public void doResign() {
		gameInProgress = false;
		if(currentPlayer == PenteData.WHITE) {
			message = "WHITE resigned! BLACK wins!";
		}
		else
			message = "BLACK resigned! WHITE wins!";
	}
	
	/**
	 * This updates the board and corresponding display messages based on the state of the board.
	 * @param row the clicked row.
	 * @param col the clicked column. 
	 */
	public void doClickSquare(int row, int col) {
		if(board.isLegalMove(row, col)) {
			board.makeMove(row, col, currentPlayer);	
			clickedSquares++;
			if(board.gameWon(row, col, currentPlayer)) {
				gameInProgress = false;
				haveWon = true;
				if(currentPlayer == PenteData.WHITE) 
					message = "WHITE has won!";
				else 
					message = "BLACK has won!";
				
				//System.out.println("Winning square: " + board.winningSquare.getX() + ", " + board.winningSquare.getY());
				//System.out.println("Final square: " + board.finalSquare.getX() + ", " + board.finalSquare.getY());
			}
			else if(clickedSquares == 169) {
				message = "The game is a DRAW!";
			}
			else {	
				// Switches current player after the current player has made a move.
				if(currentPlayer == PenteData.WHITE) {
					currentPlayer = PenteData.BLACK;
					message = "Make a move BLACK!";
				}
				else {
					currentPlayer = PenteData.WHITE;
					message = "Make a move WHITE!";
				}
			}
		}			
		drawBoard();
	}

	/**
	 * Draws the Pente board. 
	 */
	public void drawBoard() {
		GraphicsContext g = getGraphicsContext2D();
		g.setFont( Font.font(18) );

		/* Draw the squares of the PenteBoard. */
		g.setLineWidth(1);
		for (int row = 0; row < 13; row++) {
			for (int col = 0; col < 13; col++) {
				switch (board.pieceAt(row,col)) {
				case PenteData.EMPTY:
					g.setFill(Color.LIGHTGRAY);
					g.fillRect(col*25, row*25, 25, 25);
					g.setStroke(Color.BLACK);
					g.strokeRect(col*25,row*25, 25, 25);
					break;
				case PenteData.WHITE:
					g.setFill(Color.LIGHTGRAY);
					g.fillRect(col*25, row*25, 25, 25);
					g.setStroke(Color.BLACK);
					g.strokeRect(col*25,row*25, 25, 25);
					g.setFill(Color.WHITE);
					g.fillOval(5 + col*25, 5 + row*25, 16, 16);
					g.strokeOval(5 + col*25, 5 + row*25, 16, 16);
					break;
				case PenteData.BLACK:
					g.setFill(Color.LIGHTGRAY);
					g.fillRect(col*25, row*25, 25, 25);
					g.setStroke(Color.BLACK);
					g.strokeRect(col*25,row*25, 25, 25);
					g.setFill(Color.BLACK);
					g.fillOval(5 + col*25, 5 + row*25, 16, 16);
					g.strokeOval(5 + col*25, 5 + row*25, 16, 16);
					break;
				}
			}
		}
		
		// This draws a red line through the winning positions.
		if(haveWon) {
			g.setStroke(Color.RED);
			g.setLineWidth(3);
			g.strokeLine(12.5 + board.win_c1 * 25, 12.5 + board.win_r1 * 25, 12.5 + board.win_c2 * 25, 12.5 + board.win_r2 * 25);
		}
	}
	
	/**
     * Respond to a user click on the board.  If no game is in progress, show 
     * an error message.  Otherwise, find the row and column that the user 
     * clicked and call doClickSquare() to handle it.
     */
    public void mousePressed(MouseEvent evt) {
        if (gameInProgress == false)
            message = "Click \"New Game\" to start a new game.";
        else {
            int col = (int)((evt.getX() ) / 25);
            int row = (int)((evt.getY() ) / 25);
            if (col >= 0 && col < 13 && row >= 0 && row < 13)
                doClickSquare(row,col);
        }
    }
}
        
        

