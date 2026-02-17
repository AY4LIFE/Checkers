import java.util.Scanner;

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

            if (currentPlayer == Board.BLACK)
                System.out.println("Black's turn");
            else
                System.out.println("Red's turn");

            System.out.print("Enter start row: ");
            int startRow = scanner.nextInt();

            System.out.print("Enter start col: ");
            int startCol = scanner.nextInt();

            System.out.print("Enter end row: ");
            int endRow = scanner.nextInt();

            System.out.print("Enter end col: ");
            int endCol = scanner.nextInt();

            boolean moved = board.movePiece(startRow, startCol, endRow, endCol, currentPlayer);
            if (moved) {
                if (currentPlayer == Board.BLACK) currentPlayer = Board.RED;
                else currentPlayer = Board.BLACK;
            } else System.out.println("Invalid move");
        }
    }
}
