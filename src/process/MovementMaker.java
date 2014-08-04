package process;
import input.BoardDialog;
import model.GameState;
import model.HorseBaby;
import model.HorseQueen;
import model.HorseToken;
import model.NodeStats;
import model.Position;
import output.LogViewer;

public class MovementMaker {
    public static void move(BoardDialog boardDialog,HorseToken token, Position positionToMove) {
        boardDialog.setItem(token, positionToMove);
        boardDialog.deleteItem(token, token.getPosition());
        
        if (token instanceof HorseQueen){
            if(token.getStackAmount() > 2){
                ((HorseQueen) token).decreaseStack();
                boardDialog.setItem(new HorseBaby(token.getPosition(), token.getTokenColor()),token.getPosition());
            }
        }

        token.setPosition(positionToMove);
        NodeStats.getInstance().increaseVisited();
        boardDialog.refresh();
    }

    public static void moveToEat(BoardDialog boardDialog, HorseToken token, HorseToken targetHorseToken,Position positionToMove, LogViewer logViewer) {
        NodeStats.getInstance().increaseVisited();
        logViewer.refresh("Muere ficha " + targetHorseToken.getTokenColor() + "\n");
        if(boardDialog.getBoard().getHorseToken(positionToMove) instanceof HorseQueen){
            GameState.getInstance().setGameOver(true);
            GameState.getInstance().gameOver(boardDialog.getBoard().getHorseToken(positionToMove).getTokenColor(), logViewer);
        }
        boardDialog.deleteItem(targetHorseToken, positionToMove);
        
        boardDialog.setItem(token, positionToMove);
        boardDialog.deleteItem(token, token.getPosition());
        
        /*if (token instanceof HorseQueen){
            if(token.getStackAmount() > 2){
                ((HorseQueen) token).decreaseStack();
                boardDialog.setItem(new HorseBaby(token.getPosition(), token.getTokenColor()),token.getPosition());
            }
        }*/
        token.setPosition(positionToMove);
        boardDialog.refresh();        
    }
}