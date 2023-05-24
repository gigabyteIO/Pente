import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * This setups up and runs the GUI for Pente.
 * @author martin
 *
 */
public class Pente extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	//-----------------------------------------------------------------------
	
	PenteBoard board;
	
	private Button newGameButton;
	private Button resignButton;
	
	private Label message;
	
	public void start(Stage stage) throws Exception {
		
		/* Create the buttons and the board. The buttons MUST be created first. */
		
		newGameButton = new Button("New Game");
		resignButton = new Button ("Resign");
		
		board = new PenteBoard(); // A subclass of canvas.
		board.drawBoard(); // draws the contents of the board.
		
		/* Create the label that will show the message. */
		
		message = new Label(board.message);
		message.setTextFill(Color.rgb(100, 255, 100)); // Light green.
		message.setFont(Font.font(null, FontWeight.BOLD, 18));
		
		/* Set up ActionEvent handlers for the buttons and a MousePressed handler for the board. */
		/* The handlers call instance methods in the board object. */
		
		newGameButton.setOnAction( e -> {
			board.doNewGame();
			updateButtons();
			message.setText(board.message);
		} );
		resignButton.setOnAction( e -> {
			board.doResign();
			updateButtons();
			message.setText(board.message);
		} );
		board.setOnMousePressed( e -> {
			board.mousePressed(e);
			updateButtons();
			message.setText(board.message);
		} );
		
		/* Set the location of each child by calling its relocate() method. */
		
		board.relocate(20, 20);
		newGameButton.relocate(370, 120);
        resignButton.relocate(370, 200);
        message.relocate(20, 370);
        
        /* Set the sizes of the buttons.  For this to have an effect, make
         * the butons "unmanaged."  If they are managed, the Pane will set
         * their sizes. */
        
        resignButton.setManaged(false);
        resignButton.resize(100,30);
        resignButton.setDisable(true);
        newGameButton.setManaged(false);
        newGameButton.resize(100,30);
        
        /* Create the Pane and give it a preferred size. If the preferred size
         * were not set, the unmanaged buttons would not be included in the Pane's
         * computed preferred size. */
        
        Pane root = new Pane();
        root.setPrefWidth(500);
        root.setPrefHeight(420);
        
        /* Add the child nodes to the Pane and set up the rest of the GUI. */
        
        root.getChildren().addAll(board, newGameButton, resignButton, message);
        root.setStyle("-fx-background-color: darkgreen; -fx-border-color: darkred; -fx-border-width:3");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Pente");
        stage.show();
		
	} // end start()
	
	/**
	 * Enables and disabled buttons based upon whether a game is currently in progress.
	 */
	private void updateButtons() {	
		if(board.gameInProgress) {
			newGameButton.setDisable(true);
			resignButton.setDisable(false);
		}
		else {
			newGameButton.setDisable(false);
			resignButton.setDisable(true);
		}	
	} // end updateButtons()

} // end Pente class
