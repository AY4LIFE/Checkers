public class Game {
    Board board;
    public Game(){
        board = new Board();
    }

    public void start(){
        board.printBoard();
    }
}
