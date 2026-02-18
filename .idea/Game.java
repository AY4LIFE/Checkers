import java.util.Scanner;

/**
 * This is the Game class
 * It's for game loop logic such as switching turns and declaring when the game is over
 */
public class Game {
    private Board board;
    private int currentPlayer;
    private Scanner scanner;
    public Game(){
        board = new Board();
        currentPlayer = Board.BLACK;
        scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            board.printBoard();
            // Tell the player who is starting
            if (currentPlayer == Board.BLACK)
                System.out.println("Black's turn");
            else
                System.out.println("Red's turn");

            // Ask the user which piece they want to play and where they want to place it
            System.out.print("Enter start row: ");
            int startRow = scanner.nextInt();

            System.out.print("Enter start col: ");
            int startCol = scanner.nextInt();

            System.out.print("Enter end row: ");
            int endRow = scanner.nextInt();

            System.out.print("Enter end col: ");
            int endCol = scanner.nextInt();

            boolean moved = board.movePiece(startRow, startCol, endRow, endCol, currentPlayer);// Validate the move
            if (moved) {

                int winner = board.checkWin(); // Checks if there is already declared a winner
                if (winner != board.EMPTY){
                    if (winner == board.BLACK)
                        System.out.println("\nBLACK Wins!!!");
                    else
                        System.out.println("\nRED Wins!!!");
                    break;
                }
                // If there isn't, switch turns
                if (currentPlayer == Board.BLACK) currentPlayer = Board.RED;
                else currentPlayer = Board.BLACK;
            } else System.out.println("Invalid move"); // Also announce invalid move if necessary
        }
    }
}
