public class Board {
    private int[][] board;
    private final int SIZE = 8;
    public static int EMPTY = 0;
    public static final int BLACK = 1;
    public static final int RED = 2;
    public static final int BLACK_KING = 3;
    public static final int RED_KING = 4;

    public Board(){
        board = new int[SIZE][SIZE];
        initializeBoard();
    }

    private boolean isBlack(int piece){
        return piece == BLACK || piece == BLACK_KING;
    }

    private boolean isRed(int piece){
        return piece == RED || piece == RED_KING;
    }

    private boolean isKing(int piece){
        return piece == BLACK_KING || piece == RED_KING;
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
        System.out.print("  ");
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
                             int endCol, int currentPlayer) {

        if (startRow < 0 || startRow >= SIZE ||
                startCol < 0 || startCol >= SIZE ||
                endRow < 0 || endRow >= SIZE ||
                endCol < 0 || endCol >= SIZE) {
            return false;
        }
        int piece = board[startRow][startCol];

        if (piece == EMPTY) return false;
        if (currentPlayer == BLACK && !isBlack(piece)) return false;
        if (currentPlayer == RED && !isRed(piece)) return false;
        if (board[endRow][endCol] != EMPTY) return false;

        int rowDiff = endRow - startRow;
        int colDiff = endCol - startCol;

        if (isKing(piece)) {
            if ((Math.abs(rowDiff) == 1) && Math.abs(colDiff) == 1) {
                board[endRow][endCol] = piece;
                board[startRow][startCol] = EMPTY;
                return true;
            }

            if ((Math.abs(rowDiff) == 2) && Math.abs(colDiff) == 2) {
                int capturedRow = (startRow + endRow) / 2;
                int capturedCol = (startCol + endCol) / 2;
                int capturedPiece = board[capturedRow][capturedCol];



            if ((isBlack(piece) && isRed(capturedPiece)) ||
                    (isRed(piece) && isBlack(capturedPiece))){
                board[endRow][endCol] = piece;
                board[startRow][startCol] = EMPTY;
                board[capturedRow][capturedCol] = EMPTY;
                return true;
            }
        }
            return false;
    }
        int direction;
        if (isBlack(piece)){direction = 1;} else {direction = -1;}

        if (rowDiff == direction && Math.abs(colDiff) == 1){
            board[endRow][endCol] = piece;
            board[startRow][startCol] = EMPTY;

            if (piece == BLACK && endRow == SIZE - 1)
                board[endRow][endCol] = BLACK_KING;

            if (piece == RED && endRow == 0)
                board[endRow][endCol] = RED_KING;

            return true;
        }

        if ((rowDiff == 2 * direction) && (Math.abs(colDiff) == 2)){
            int capturedRow = (startRow + endRow) / 2;
            int capturedCol = (startCol + endCol) / 2;
            int capturedPiece = board[capturedRow][capturedCol];

            if ((isBlack(piece) && isRed(capturedPiece)) ||
                    (isRed(piece) && isBlack(capturedPiece))){
                board[endRow][endCol] = piece;
                board[startRow][startCol] = EMPTY;
                board[capturedRow][capturedCol] = EMPTY;

                if (piece == BLACK && endRow == SIZE - 1)
                    board[endRow][endCol] = BLACK_KING;

                if (piece == RED && endRow == 0)
                    board[endRow][endCol] = RED_KING;

                return true;
            }
        }

        return false;
    }


    public int checkWin(){
        int blackCount = 0;
        int redCount = 0;

        for (int row = 0; row < SIZE; row++){
            for (int col = 0; col < SIZE; col++){
                if (isBlack(board[row][col])) blackCount++;
                else if (isRed(board[row][col])) redCount++;
            }
        }
        if ((blackCount == 0) || !hasValidMove(BLACK)) return RED;
        else if ((redCount == 0) || !hasValidMove(RED)) return BLACK;
        else return EMPTY;
    }

    public boolean hasValidMove(int player){
        for (int row = 0; row < SIZE; row++){
            for (int col = 0; col < SIZE; col++){
                int piece = board[row][col];
                if ((player == BLACK && isBlack(piece)) ||
                        (player == RED && isRed(piece))){

                    int[] directions = new int[0];
                    if ((piece == BLACK_KING) || (piece == RED_KING)) {
                        directions = new int[]{1, -1};
                    }
                    else if (piece == BLACK) {
                        directions = new int[]{1};
                    }
                    else if (piece == RED) {
                        directions = new int[]{-1};
                    }

                    for (int direction : directions){
                        int[] colSteps = {1,-1};
                        for (int colStep : colSteps){
                            int newRow = row + directions[direction];
                            int newCol = col + colSteps[colStep];

                            if ((newRow >= 0 && newRow < SIZE) &&
                                    (newCol >= 0 && newCol < SIZE)){
                                if (board[newRow][newCol] == EMPTY)
                                    return true;
                            }

                        int jumpRow = row + (2 * directions[direction]);
                        int jumpCol = col + (2 * colSteps[colStep]);
                        int middleRow = row + directions[direction];
                        int middleCol = col + colSteps[colStep];

                            if ((jumpRow >= 0 && jumpRow < SIZE) &&
                                    (jumpCol >= 0 && jumpCol < SIZE)) {
                                if (board[jumpRow][jumpCol] == EMPTY) {
                                    int middlePiece = board[middleRow][middleCol];

                                    if (board[jumpRow][jumpCol] == EMPTY &&
                                            ((isBlack(piece) && isRed(middlePiece)) ||
                                                    (isRed(piece) && isBlack(middlePiece)))) {
                                        return true;
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
