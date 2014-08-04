package process;

import input.BoardDialog;
import input.OptionsDialog;
import model.Board;
import model.GameState;
import model.HorseToken;
import model.Position;
import output.LogViewer;

public class Agent {
    private Board board;
    private BoardDialog boardDialog;
    private LogViewer logViewer;
    private HorseToken.TokenColor player;
    private Position bestPosition;
    private Position initialPosition;
    
    public Agent(Board board, BoardDialog boardDialog, LogViewer logViewer, HorseToken.TokenColor player){
        this.board = board;
        this.boardDialog = boardDialog;
        this.logViewer = logViewer;
        this.player = player;
    }

    public Position getBestPosition() {
        return bestPosition;
    }

    public Position getInitialPosition() {
        return initialPosition;
    }
    
    public void executeToMove(OptionsDialog optionsDialog){
        MiniMaxSearch miniMaxSearch = new MiniMaxSearch(board, boardDialog);
        miniMaxSearch.makeDecision(this.player,false);
        if(!GameState.getInstance().isGameOver()){
            if((miniMaxSearch.getInitialPosition() != null)&&(miniMaxSearch.getBestPosition() != null)){
                HorseToken horseInitial = board.getHorseToken(miniMaxSearch.getInitialPosition());
                logViewer.refresh("Ficha "+horseInitial.getTokenColor()+" mueve de [" + horseInitial.getPosition().getRow()+ ", " +
                                        horseInitial.getPosition().getColumn()+"]");
               logViewer.refresh(" a [" + miniMaxSearch.getBestPosition().getRow() + ", " + miniMaxSearch.getBestPosition().getColumn()+ "]\n");

                if(board.getHorseToken(miniMaxSearch.getBestPosition()) != null){
                    MovementMaker.moveToEat(boardDialog, board.getHorseToken(miniMaxSearch.getInitialPosition()), board.getHorseToken(miniMaxSearch.getBestPosition()), miniMaxSearch.getBestPosition(), logViewer);
                }
                else{
                    MovementMaker.move(boardDialog, board.getHorseToken(miniMaxSearch.getInitialPosition()), miniMaxSearch.getBestPosition());
                }
                optionsDialog.togglePlayer();
                this.bestPosition = miniMaxSearch.getBestPosition();
                this.initialPosition = miniMaxSearch.getInitialPosition();
            }
        }
        else{
            GameState.getInstance().gameOver(player, logViewer);
        }
    }
    
    public void executeToShow(){
        MiniMaxSearch alphaBetaSearch = new MiniMaxSearch(board, boardDialog);
        alphaBetaSearch.makeDecision(this.player,true);
        //HorseToken horseInitial = board.getHorseToken(alphaBetaSearch.getInitialPosition());
        this.bestPosition = alphaBetaSearch.getBestPosition();
        this.initialPosition = alphaBetaSearch.getInitialPosition();
    }
    
}
