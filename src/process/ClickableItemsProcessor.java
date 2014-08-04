package process;
import component.BoardItem;
import component.EmptySquare;
import component.OccupiedSquare;
import input.BoardDialog;
import input.OptionsDialog;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import model.Board;
import model.GameState;
import model.HorseToken;
import model.HorseToken.TokenColor;
import model.Position;
import output.LogViewer;

public class ClickableItemsProcessor {
    private static ClickableItemsProcessor instance;
    
    private HorseToken currentHorseToken;
    private Position targetCell;
    private BoardDialog boardDialog;
    private LogViewer logViewer;
    private OptionsDialog optionsDialog;
    private Agent agent;
    
    private ClickableItemsProcessor(BoardDialog boardDialog,
            LogViewer logViewer, OptionsDialog optionsDialog) {
        this.boardDialog = boardDialog;
        this.logViewer = logViewer;
        this.optionsDialog = optionsDialog;
    }
    
    public static ClickableItemsProcessor getInstance (BoardDialog boardDialog,
            LogViewer logViewer, OptionsDialog optionsDialog) {
        if(instance == null) {
            instance = new ClickableItemsProcessor(
                    boardDialog, logViewer, optionsDialog);
        }
        return instance;
    }
    
    public static ClickableItemsProcessor getInstance() {
        if (instance != null) {
            return instance;
        }
        else return null;
    } 
    
    public BoardItem[][] convert(Board tokenBoard, int dimension){
        BoardItem itemBoard[][] = new BoardItem[dimension][dimension];
        
        for (int i=0; i<dimension; i++) {
            for (int j=0; j<dimension; j++) {
                if(tokenBoard.getHorseToken(new Position(i+1,j+1)) == null) {
                    BoardItem item;
                    if ((i + j) % 2 == 0)
                        item = new EmptySquare(TokenColor.WHITE, new Position(i+1,j+1));
                    else
                        item = new EmptySquare(TokenColor.BLACK, new Position(i+1,j+1));
                    
                    itemBoard[i][j] = item;
                    createEmptyCellClickListener(item, tokenBoard);
                }
                else {
                    BoardItem item = new OccupiedSquare(
                            tokenBoard.getHorseToken(new Position(i+1,j+1)).getTokenColor(),
                            tokenBoard.getHorseToken(new Position(i+1,j+1)).getStackAmount(),
                            new Position(i+1,j+1));
                    itemBoard[i][j] = item;
                    createItemClickListener(item, tokenBoard);                    
                }
            }
        }
        
        return itemBoard;
    }
    
    private void createItemClickListener(BoardItem item, final Board tokenBoard) {
        item.addMouseListener(
            new MouseListener() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if(!GameState.getInstance().isGameOver()){
                        if (currentHorseToken == null) {
                            currentHorseToken = (HorseToken) tokenBoard.getHorseToken(
                                ((BoardItem) (e.getSource())).getPosition());

                            optionsDialog.refresh("Seleccionado: " +
                                    currentHorseToken.getPosition().getRow()+ ", " +
                                    currentHorseToken.getPosition().getColumn());

                        }
                        else{ 
                            if(currentHorseToken == (HorseToken) tokenBoard.getHorseToken(
                                ((BoardItem) (e.getSource())).getPosition())) {
                                optionsDialog.refresh("Elemento deseleccionado");
                                reset();
                            }
                            else{
                                if(currentHorseToken.getTokenColor() == 
                                    ((HorseToken) tokenBoard.getHorseToken(
                                    ((BoardItem) (e.getSource())).getPosition())).getTokenColor()){

                                    currentHorseToken = (HorseToken) tokenBoard.getHorseToken(
                                        ((BoardItem) (e.getSource())).getPosition());

                                    optionsDialog.refresh("Seleccionado: " +
                                        currentHorseToken.getPosition().getRow()+ ", " +
                                        currentHorseToken.getPosition().getColumn());
                                }
                                else{
                                    targetCell = (Position)
                                        ((BoardItem) (e.getSource())).getPosition();

                                    if(MovementChecker.check(boardDialog.getBoard(),boardDialog.getPlayerToMove(),boardDialog.getDimension(),currentHorseToken,
                                       currentHorseToken.getPosition(), targetCell, 
                                       boardDialog.getBlackQueenPosition(),boardDialog.getWhiteQueenPosition(), true)){

                                       logViewer.refresh("Ficha "+currentHorseToken.getTokenColor()+" mueve de [" + currentHorseToken.getPosition().getRow()+ ", " +
                                       currentHorseToken.getPosition().getColumn()+"]");
                                       logViewer.refresh(" a [" +
                                       targetCell.getRow() + ", " + targetCell.getColumn()+ "]\n");
                                       MovementMaker.moveToEat(boardDialog,currentHorseToken,tokenBoard.getHorseToken(targetCell),targetCell, logViewer);
                                       boardDialog.changePlayerToMove();
                                       optionsDialog.togglePlayer();
                                       /*agent = new Agent(tokenBoard, boardDialog, logViewer);
                                       boardDialog.changePlayerToMove();*/
                                    }
                                    reset();
                                }
                            }
                        }
                    }
                }
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseClicked(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            }
        );
    }
    
    private void createEmptyCellClickListener(BoardItem item, final Board tokenBoard) {
        item.addMouseListener(
            new MouseListener() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if(!GameState.getInstance().isGameOver()){
                        // borrar esto luego
                        targetCell = (Position)
                                    ((BoardItem) (e.getSource())).getPosition();
                        optionsDialog.refresh("Seleccionado: " +
                                    targetCell.getRow() + ", " + targetCell.getColumn());
                        targetCell = null;
                        // borrar hasta aqui

                        if ((currentHorseToken != null) && (targetCell == null)) {

                            targetCell = (Position)
                                    ((BoardItem) (e.getSource())).getPosition();

                            if(MovementChecker.check(boardDialog.getBoard(),boardDialog.getPlayerToMove(),boardDialog.getDimension(),currentHorseToken,
                                                     currentHorseToken.getPosition(), targetCell, 
                                                     boardDialog.getBlackQueenPosition(),boardDialog.getWhiteQueenPosition(), true)){

                                logViewer.refresh("Ficha "+currentHorseToken.getTokenColor()+" mueve de [" + currentHorseToken.getPosition().getRow()+ ", " +
                                    currentHorseToken.getPosition().getColumn()+"]");
                                MovementMaker.move(boardDialog,currentHorseToken, targetCell);
                                logViewer.refresh(" a [" +
                                    targetCell.getRow() + ", " + targetCell.getColumn()+ "]\n");
                                boardDialog.changePlayerToMove();
                                optionsDialog.togglePlayer();
                                //agent = new Agent(tokenBoard, boardDialog, logViewer);
                                //boardDialog.changePlayerToMove();
                            }

                            // MOVER ELEMENTO
                            reset();
                        }
                    }
                }
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseClicked(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            }
        );
    }
    
    /*public static void move() {
        agent = new Agent(tokenBoard, boardDialog, logViewer);
    }*/
    
    public void reset() {
        currentHorseToken = null;
        targetCell = null;
    }
}
