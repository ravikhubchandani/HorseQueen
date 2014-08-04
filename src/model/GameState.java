package model;

import java.io.Serializable;
import javax.swing.JOptionPane;
import output.LogViewer;

public class GameState implements Serializable {
    private static GameState instance;
    
    private boolean killHorseQueenWhite;
    private boolean killHorseBabyWhite;
    private boolean killHorseQueenBlack;
    private boolean killHorseBabyBlack;
    private boolean noMoreMovesWhite;
    private boolean noMoreMovesBlack;
    private boolean gameOver;
    private HorseToken.TokenColor playerWin;
    //private LogViewer logViewer;
    private int level;
    
    
    private GameState() {
        killHorseQueenWhite = false;
        killHorseBabyWhite = false;
        killHorseQueenBlack = false;
        killHorseBabyBlack = false;
        noMoreMovesWhite = false;
        noMoreMovesBlack = false;
        gameOver = false;
    }

    public static GameState getInstance() {
        if(instance == null){
            instance = new GameState();
        }
        return instance;
    }
    
    public void setLevel(int level) {
       this.level = level;
    }
    
    public int getLevel() {
       return level;
    }

    public boolean isKillHorseQueenBlack() {
        return killHorseQueenBlack;
    }
    
    public boolean isKillHorseBabyBlack() {
        return killHorseBabyBlack;
    }

    public boolean isKillHorseQueenWhite() {
        return killHorseQueenWhite;
    }

    public boolean isKillHorseBabyWhite() {
        return killHorseBabyWhite;
    }

    public boolean isNoMoreMovesWhite() {
        return noMoreMovesWhite;
    }

    public boolean isNoMoreMovesBlack() {
        return noMoreMovesBlack;
    }
    
    public void setKillHorseQueenWhite(boolean killHorseQueen) {
        this.killHorseQueenWhite = killHorseQueen;
    }

    public void setKillHorseBabyWhite(boolean killHorseBaby) {
        this.killHorseBabyWhite = killHorseBaby;
    }

    public void setKillHorseQueenBlack(boolean killHorseQueenBlack) {
        this.killHorseQueenBlack = killHorseQueenBlack;
    }

    public void setKillHorseBabyBlack(boolean killHorseBabyBlack) {
        this.killHorseBabyBlack = killHorseBabyBlack;
    }

    public void setNoMoreMovesWhite(boolean noMoreMovesWhite) {
        this.noMoreMovesWhite = noMoreMovesWhite;
    }

    public void setNoMoreMovesBlack(boolean noMoreMovesBlack) {
        this.noMoreMovesBlack = noMoreMovesBlack;
    }
    
    public boolean isTerminal(){
        // Aqui borramos las condiciones de que se terminara cuando un hijo muriera.
        if((killHorseQueenBlack == true)||(killHorseQueenWhite == true)||(noMoreMovesBlack == true)||(noMoreMovesWhite == true)){
            return true;
        }
        return false;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
    public void reset(){
        killHorseQueenBlack = false;
        killHorseBabyBlack = false;
        killHorseQueenWhite = false;
        killHorseBabyWhite = false;
        noMoreMovesBlack = false;
        noMoreMovesWhite = false;
    }
    
    public void restartGame() {
       reset();
       gameOver = false;
    }

    public void setPlayerWin(HorseToken.TokenColor playerWin) {
        this.playerWin = playerWin;
    }

    public HorseToken.TokenColor getPlayerWin() {
        return playerWin;
    }
    
    public void gameOver(HorseToken.TokenColor player, LogViewer logViewer){
        if(player == HorseToken.TokenColor.BLACK){
            GameState.getInstance().setPlayerWin(HorseToken.TokenColor.WHITE);
        }
        else{
            GameState.getInstance().setPlayerWin(HorseToken.TokenColor.BLACK);
        }
        JOptionPane.showMessageDialog(null, "GAME OVER\nJUGADOR "+GameState.getInstance().getPlayerWin()+ " GANA");
        logViewer.refresh("\n\n------------------------------");
        logViewer.refresh("\n     GAME OVER     \n");
        logViewer.refresh("------------------------------\n");
        logViewer.refresh("\nResultados: \n");
        logViewer.refresh("   Número de nodos expandidos: " + NodeStats.getInstance().getExpandNodesTotal() + "\n");
        logViewer.refresh("   Número de nodos visitados: " + NodeStats.getInstance().getVisitedNodes() + "\n");
    }
    
    public void setGameState(GameState newInstance) {
       this.instance = newInstance;
    }    
}
