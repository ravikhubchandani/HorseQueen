package input;
import component.BoardItem;
import component.BoardPanel;
import model.Board;
import model.HorseQueen;
import model.HorseToken;
import model.Position;
import process.ClickableItemsProcessor;

public class BoardDialog {
    private BoardPanel boardPanel;
    private Board board;
    
    public BoardDialog(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
        board = new Board(boardPanel.getDimension());
    }

    public Board getBoard() {
        return board;
    }
    
    public int getDimension() {
        return boardPanel.getDimension();
    }
    
    public Position getBlackQueenPosition() {
        return board.getBlackQueenPosition();
    }
    
    public Position getWhiteQueenPosition() {
        return board.getWhiteQueenPosition();
    }
    
    public HorseToken.TokenColor getPlayerToMove(){
        return board.getPlayerToMove();
    }
    
    public void changePlayerToMove(){
        board.changePlayerToMove();
    }
    
    public void refresh() {
        BoardItem items[][] = ClickableItemsProcessor.getInstance().convert(board, boardPanel.getDimension());        
        boardPanel.setItems(items);
        boardPanel.refresh(items);
    }
    
    public void setItem(HorseToken token, Position position) {
        board.setHorseToken(token, position);
    }
    
    public void setBoard(Board board) {
       this.board = board;
    }
    
    public void deleteItem(HorseToken token, Position position){
        if(token.equals(board.getHorseToken(position))){
            board.deleteHorseToken(position);
        }
    }
    
    public void blinkCell(Position positionInitial, Position positionFinal){
        boardPanel.blinkCell(positionInitial);
        boardPanel.blinkCell(positionFinal);
    }
    
    public void reset(int dimension, int stack) {
       board.restart(dimension);
       boardPanel.restart(dimension);
       setItem(new HorseQueen(
                stack, new Position(1, dimension/2), HorseToken.TokenColor.WHITE),
                new Position(1, dimension/2));
       setItem(new HorseQueen(
                stack, new Position(dimension,  dimension/2+1),
                HorseToken.TokenColor.BLACK), new Position(dimension,  dimension/2+1));
       refresh();
    }
    
    public void reset(int dimension) {
       board.restart(dimension);
       boardPanel.restart(dimension);
       refresh();
    }
}