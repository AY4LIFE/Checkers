/**
 * This is the Board class
 * It store pieces, validates move and knows piece counts
 */

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

    // These are the helper functions to help check whether a peace is Red, Black, or even a King
    private boolean isBlack(int piece){
        return piece == BLACK || piece == BLACK_KING;
    }

    private boolean isRed(int piece){
        return piece == RED || piece == RED_KING;
    }

    private boolean isKing(int piece){
        return piece == BLACK_KING || piece == RED_KING;
    }

    /**
     * This function is used to initialize the board when starting to play
     */
    public void initializeBoard(){
        for (int row = 0; row < SIZE; row++){
            for (int col = 0; col < SIZE; col++){
                if ((row + col) % 2 == 1){ // Making sure the pieces only appear at the black spots in the board
                    if (row < 3) board[row][col] = BLACK; // The upper row of the board belongs to Black pieces
                    else if (row > 4) board[row][col] = RED; // The lower row belongs to Red pieces
                    else board[row][col] = EMPTY;
                }
            else board[row][col] = EMPTY;
            }
        }
    }

    /**
     * This function prints the current state of the board
     */
    public void printBoard(){
        System.out.print("  ");
        for (int col = 0; col < SIZE; col++){
            System.out.print(col + " "); // First print the column numbers
        }
        System.out.print("\n");

        for (int row = 0; row < SIZE; row++){
            System.out.print(row + " "); // Then print the row numbers

            for (int col = 0; col < SIZE; col++){
                if (board[row][col] == EMPTY){System.out.print(". ");}
                if (board[row][col] == BLACK){System.out.print("b ");}
                if (board[row][col] == RED){System.out.print("r ");}
            }
            System.out.println();
        }
    }

    /**
     *
     * @param startRow is the starting Row of the pieces
     * @param startCol is the starting Column of the pieces
     * @param endRow is the ending row of the pieces
     * @param endCol is the ending column of the pieces
     * @param currentPlayer is the current piece colour
     * @return whether a move is valid or not
     */
    public boolean movePiece(int startRow, int startCol, int endRow,
                             int endCol, int currentPlayer) {

        // 1) Check boundaries
        if (startRow < 0 || startRow >= SIZE ||
                startCol < 0 || startCol >= SIZE ||
                endRow < 0 || endRow >= SIZE ||
                endCol < 0 || endCol >= SIZE) {
            return false;
        }
        int piece = board[startRow][startCol];

        // 2) Check if you are using the right pieces
        if (piece == EMPTY) return false;
        if (currentPlayer == BLACK && !isBlack(piece)) return false;
        if (currentPlayer == RED && !isRed(piece)) return false;

        // 3) Check if the end positions are empty
        if (board[endRow][endCol] != EMPTY) return false;

        int rowDiff = endRow - startRow;
        int colDiff = endCol - startCol;
        // 4) Check normal move and capture move for kings
        if (isKing(piece)) {
            // Normal move
            if ((Math.abs(rowDiff) == 1) && Math.abs(colDiff) == 1) {
                board[endRow][endCol] = piece;
                board[startRow][startCol] = EMPTY;
                return true;
            }
            // Capture move
            if ((Math.abs(rowDiff) == 2) && Math.abs(colDiff) == 2) {
                int capturedRow = (startRow + endRow) / 2;
                int capturedCol = (startCol + endCol) / 2;
                int capturedPiece = board[capturedRow][capturedCol];


            // If Captured move is valid, remove the captured pieces
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

        // 5) Check normal move and Captured move for normal pieces

        // Checking normal moves
        if (rowDiff == direction && Math.abs(colDiff) == 1){
            board[endRow][endCol] = piece;
            board[startRow][startCol] = EMPTY;
            // Checking possible promotions
            if (piece == BLACK && endRow == SIZE - 1)
                board[endRow][endCol] = BLACK_KING;

            if (piece == RED && endRow == 0)
                board[endRow][endCol] = RED_KING;

            return true;
        }
        // Checking capture moves
        if ((rowDiff == 2 * direction) && (Math.abs(colDiff) == 2)){
            int capturedRow = (startRow + endRow) / 2;
            int capturedCol = (startCol + endCol) / 2;
            int capturedPiece = board[capturedRow][capturedCol];
            // If captured move is valid, remove the captured pieces
            if ((isBlack(piece) && isRed(capturedPiece)) ||
                    (isRed(piece) && isBlack(capturedPiece))){
                board[endRow][endCol] = piece;
                board[startRow][startCol] = EMPTY;
                board[capturedRow][capturedCol] = EMPTY;
                // Checking possible promotions
                if (piece == BLACK && endRow == SIZE - 1)
                    board[endRow][endCol] = BLACK_KING;

                if (piece == RED && endRow == 0)
                    board[endRow][endCol] = RED_KING;

                return true;
            }
        }

        return false;
    }

    /**
     * This checks win by checking the number of red pieces and black pieces
     * @return the winner
     */
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

    /**
     *
     * @param player is the player whom you're checking if has valid moves
     * @return whether the player has any valid moves left or not
     */
    public boolean hasValidMove(int player){
        for (int row = 0; row < SIZE; row++){
            for (int col = 0; col < SIZE; col++){
                int piece = board[row][col];
                if ((player == BLACK && isBlack(piece)) ||
                        (player == RED && isRed(piece))){

                    int[] directions = new int[0];
                    // Store all possible directions for every pieces
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
                        int[] colSteps = {1,-1}; // These are the possible column steps
                        for (int colStep : colSteps){
                            int newRow = row + directions[direction]; // TODO: There is an error here. Trying to fix it
                            int newCol = col + colSteps[colStep];
                            // Check if the normal pieces have any moves left
                            if ((newRow >= 0 && newRow < SIZE) &&
                                    (newCol >= 0 && newCol < SIZE)){
                                if (board[newRow][newCol] == EMPTY)
                                    return true;
                            }

                        int jumpRow = row + (2 * directions[direction]);
                        int jumpCol = col + (2 * colSteps[colStep]);
                        int middleRow = row + directions[direction];
                        int middleCol = col + colSteps[colStep];
                            // Check if when you are in a captured situations, you have moves left
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
