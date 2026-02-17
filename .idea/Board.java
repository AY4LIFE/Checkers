public class Board {
    private int[][] board;
    private final int SIZE = 8;
    private final int EMPTY = 0;
    private final int BLACK = 1;
    private final int RED = 2;

    public Board(){
        board = new int[SIZE][SIZE];
        initializeBoard();
    }

    public void initializeBoard(){
        for (int row = 0; row < SIZE; row++){
            for (int col = 0; col < SIZE; col++){
                if ((row + col) % 2 == 1){
                    if (row < 3) board[row][col] = BLACK;
                    else if (row > 4) board[row][col] = RED;
                    else board[row][col] = EMPTY;
                }
            else board[row][col] = EMPTY;
            }
        }
    }
    public void printBoard(){
        System.out.print(" ");
        for (int col = 0; col < SIZE; col++){
            System.out.print(col + " ");
        }
        System.out.print("\n");

        for (int row = 0; row < SIZE; row++){
            System.out.print(row + " ");

            for (int col = 0; col < SIZE; col++){
                if (board[row][col] == EMPTY){System.out.print(". ");}
                if (board[row][col] == BLACK){System.out.print("b ");}
                if (board[row][col] == RED){System.out.print("r ");}
            }
            System.out.println();
        }

    }
}
