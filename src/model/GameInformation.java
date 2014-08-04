package model;

import java.io.Serializable;

public class GameInformation implements Serializable {
   private Board board;
   private NodeStats nodeStats;
   private GameState gameState;
   private String logState;

   public GameInformation(Board board, NodeStats nodeStats, GameState gameState, String logState) {
      this.board = board;
      this.nodeStats = nodeStats;
      this.gameState = gameState;
      this.logState = logState;
   }

   public Board getBoard() {
      return board;
   }

   public NodeStats getNodeStats() {
      return nodeStats;
   }

   public GameState getGameState() {
      return gameState;
   }
   
   public String getLog() {
      return logState;
   }
}
