public class Board {
    private int[][] board;
    private final int SIZE = 8;
    public static int EMPTY = 0;
    public static final int BLACK = 1;
    public static final int RED = 2;

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
        System.out.print("   ");
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
    public boolean movePiece(int startRow, int startCol, int endRow,
                             int endCol, int currentPlayer){

        if (startRow < 0 || startRow >= SIZE ||
                startCol < 0 || startCol >= SIZE ||
                endRow < 0 || endRow >= SIZE ||
                endCol < 0 || endCol >= SIZE) {
            return false;

    }else if (board[startRow][startCol] == EMPTY) return false;
        else if (board[startRow][startCol] != currentPlayer) return false;
        else if (board[endRow][endCol] != EMPTY) return false;

        else{
            if (currentPlayer == BLACK){
                if ((endRow == startRow + 1) && (Math.abs(endCol - startCol) == 1)){
                    board[endRow][endCol] = board[startRow][startCol];
                    board[startRow][startCol] = EMPTY;
                    return true;
                }
                else if ((endRow == startRow + 2) && (Math.abs(endCol - startCol) == 2)){
                    int capturedRow = (startRow + endRow) / 2;
                    int capturedCol = (startCol + endCol) / 2;

                    if (board[capturedRow][capturedCol] == RED){
                        board[endRow][endCol] = board[startRow][startCol];
                        board[startRow][startCol] = EMPTY;
                        board[capturedRow][capturedCol] = EMPTY;
                        return true;
                    }
                }
                else return false;
            }
            else if (currentPlayer == RED){
                if ((endRow == startRow - 1) && (Math.abs(endCol - startCol) == 1)){
                    board[endRow][endCol] = board[startRow][startCol];
                    board[startRow][startCol] = EMPTY;
                    return true;
                }
                else if ((endRow == startRow - 2) && (Math.abs(endCol - startCol) == 2)){
                    int capturedRow = (startRow + endRow) / 2;
                    int capturedCol = (startCol + endCol) / 2;

                    if (board[capturedRow][capturedCol] == BLACK){
                        board[endRow][endCol] = board[startRow][startCol];
                        board[startRow][startCol] = EMPTY;
                        board[capturedRow][capturedCol] = EMPTY;
                        return true;
                    }
                }
                else return false;
            }
        }
        return false;
    }

}
