package process;

import input.BoardDialog;
import java.util.ArrayList;
import model.Board;
import model.GameState;
import model.HorseBaby;
import model.HorseQueen;
import model.HorseToken;
import model.NodeStats;
import model.Position;

public class MiniMaxSearch {
    
    private Position initialPosition;
    private Position bestPosition;
    private Board board;
    private BoardDialog boardDialog;
    private HorseToken horseToken;
    private boolean isHint;
    
    private int whiteHorses;
    private int blackHorses;
    
    public MiniMaxSearch(Board board, BoardDialog boardDialog) {
        this.board = board;
        this.boardDialog = boardDialog;
    }

    public Position getBestPosition() {
        return bestPosition;
    }

    public Position getInitialPosition() {
        return initialPosition;
    }

    public HorseToken getHorseToken() {
        return horseToken;
    }
    
    public void makeDecision(HorseToken.TokenColor player,boolean isHint){
        double minValue = Double.NEGATIVE_INFINITY;
        double maxValue = Double.POSITIVE_INFINITY;
        this.isHint = isHint;
        int depthLimit;
        switch(GameState.getInstance().getLevel()) {
           case 1:
              depthLimit = 1;
              break;
           case 2:
              depthLimit = 3;
              break;
           case 3:
              depthLimit = 5;
              break;
           default : depthLimit = 3;
        }
        
        boolean noMoves;
        double heuristicValue = -1;
        
        // Level 1 = depthLimit 1
        // Level 2 = depthLimit 3
        // Level 3 = depthLimit 5
        // Level 4 = depthLimit 7
        // Level 5 = depthLimit 9
        
        Position auxInitial = null;
        Board cloneBoard;
        ArrayList<HorseToken> horseTokenList = new ArrayList<HorseToken>();
        for(int row=1;row<=board.getDimension();row++){
            for(int column=1;column<=board.getDimension();column++){
                if(board.getHorseToken(new Position(row,column)) != null){
                    if(board.getHorseToken(new Position(row,column)).getTokenColor() == player){
                        horseTokenList.add(board.getHorseToken(new Position(row,column)));
                        horseTokenList.get(horseTokenList.size()-1).setPossibleMoves(
                                HorseToken.setPossibleMovementsList(board, board.getHorseToken(new Position(row,column)),false));
                    }    
                }
            }
        }
        if(this.isHint == false){
            NodeStats.getInstance().increaseExpandedTotal(horseTokenList.size());
        }
        
        if(justOneMove(horseTokenList) == 1){return;}
        
        noMoves = true;
        for(int i=0; i<horseTokenList.size(); i++){
            ArrayList<Position> movesCurrentHorseToken = horseTokenList.get(i).getPossibleMoves();
            
            if(this.isHint == false){
                NodeStats.getInstance().increaseExpandedTotal(movesCurrentHorseToken.size());
            }
            
            
            for(int j=0; j<movesCurrentHorseToken.size(); j++){
                
                // Pequeña heuristica
                if(board.getHorseToken(movesCurrentHorseToken.get(j)) != null){
                    if(board.getHorseToken(movesCurrentHorseToken.get(j)).getTokenColor() != player){
                        if(board.getHorseToken(movesCurrentHorseToken.get(j)) instanceof HorseQueen){
                            bestPosition = movesCurrentHorseToken.get(j);
                            initialPosition = horseTokenList.get(i).getPosition();
                            GameState.getInstance().reset();
                            return;
                        }
                    }
                }
                            
                noMoves = false;
                cloneBoard = board.clone();
                HorseToken currentHorseToken = cloneBoard.getHorseToken(horseTokenList.get(i).getPosition());
                keepGoing(player,cloneBoard, currentHorseToken, movesCurrentHorseToken.get(j));
                cloneBoard.deleteHorseToken(currentHorseToken.getPosition());
                cloneBoard.setHorseToken(currentHorseToken, movesCurrentHorseToken.get(j));
                if(currentHorseToken instanceof HorseQueen){
                    cloneBoard.setHorseToken(new HorseBaby(currentHorseToken.getPosition(),currentHorseToken.getTokenColor()),currentHorseToken.getPosition());
                }
                auxInitial = currentHorseToken.getPosition();
                currentHorseToken.setPosition(movesCurrentHorseToken.get(j));
                Position currentPosition = movesCurrentHorseToken.get(j);
             
                heuristicValue =  minVal(cloneBoard,minValue,maxValue,depthLimit,player, currentPosition);

                if(heuristicValue > minValue){
                    initialPosition = auxInitial;
                    bestPosition = movesCurrentHorseToken.get(j);
                    minValue = heuristicValue;
                }
                GameState.getInstance().reset();
                currentHorseToken.setPosition(auxInitial);
            }
        }
        if(noMoves == true) GameState.getInstance().setGameOver(true);
        GameState.getInstance().reset();
    }
    
    private double minVal(Board cloneBoard, double beta, double alpha, int depthLimit, HorseToken.TokenColor player, Position currentPositionMin){
        HorseToken.TokenColor currentPlayer = null;
        if(player == HorseToken.TokenColor.BLACK){
            currentPlayer = HorseToken.TokenColor.WHITE;
        }
        else if(player == HorseToken.TokenColor.WHITE){
            currentPlayer = HorseToken.TokenColor.BLACK;
        }
        
        
        if((GameState.getInstance().isTerminal())||(depthLimit < 1)){
            if(player == HorseToken.TokenColor.BLACK){
                //return getOffensiveHeuristic(cloneBoard,currentPositionMin,player);
            }
            else if(player == HorseToken.TokenColor.WHITE){
               return DefensiveHeuristic(cloneBoard, player);
                //return getDefensiveHeuristic(cloneBoard,currentPositionMin,player);
            }
        } 
        
        double maxValue;
        maxValue = alpha;
        Board cloneBoardNext;
        ArrayList<HorseToken> horseTokenList = new ArrayList<HorseToken>();
        GameState.getInstance().reset();
        for(int row=0;row<cloneBoard.getDimension();row++){
            for(int column=0;column<cloneBoard.getDimension();column++){
                if(cloneBoard.getHorseToken(new Position(row+1,column+1)) != null){
                    if(cloneBoard.getHorseToken(new Position(row+1,column+1)).getTokenColor() == currentPlayer){
                        horseTokenList.add(cloneBoard.getHorseToken(new Position(row+1,column+1)));
                        horseTokenList.get(horseTokenList.size()-1).setPossibleMoves(
                                HorseToken.setPossibleMovementsList(cloneBoard, cloneBoard.getHorseToken(new Position(row+1,column+1)),false));
                    }    
                }
            }
        }
        
        for(int i=0; i<horseTokenList.size(); i++){
            if(this.isHint == false){
                NodeStats.getInstance().increaseExpandedTotal(1);
            }
            ArrayList<Position> movesCurrentHorseToken = horseTokenList.get(i).getPossibleMoves();
            for(int j=0; j<movesCurrentHorseToken.size(); j++){
                if(this.isHint == false){
                    NodeStats.getInstance().increaseExpandedTotal(1);
                }
                cloneBoardNext = cloneBoard.clone();
                HorseToken currentHorseToken = cloneBoardNext.getHorseToken(horseTokenList.get(i).getPosition());
                keepGoing(player,cloneBoardNext, currentHorseToken, movesCurrentHorseToken.get(j));
                cloneBoardNext.deleteHorseToken(currentHorseToken.getPosition());
                if(cloneBoardNext.getHorseToken(movesCurrentHorseToken.get(j)) != null){
                    cloneBoardNext.deleteHorseToken(movesCurrentHorseToken.get(j));
                }
                cloneBoardNext.setHorseToken(currentHorseToken, movesCurrentHorseToken.get(j));

                if(currentHorseToken instanceof HorseQueen){
                    cloneBoardNext.setHorseToken(new HorseBaby(currentHorseToken.getPosition(),currentHorseToken.getTokenColor()),currentHorseToken.getPosition());
                }
                currentHorseToken.setPosition(movesCurrentHorseToken.get(j));
                
                Position currentPositionMax = movesCurrentHorseToken.get(j);
                
                maxValue =  Math.min(maxValue, maxVal(cloneBoardNext,beta,alpha,depthLimit - 1,player,currentPositionMax));
                if(maxValue <= alpha){
                    alpha = maxValue;
                    //return alpha;
                }
                else{
                    beta = Math.min(beta, maxValue);
                }
            }
        }
        return alpha;
    }
    
    private double maxVal(Board cloneBoard, double beta, double alpha, int depthLimit, HorseToken.TokenColor player, Position currentPositionMax){
        HorseToken.TokenColor currentPlayer = null;
        // Player es el jugador que realiza el makeDesicions.
        if(player == HorseToken.TokenColor.BLACK){
            currentPlayer = HorseToken.TokenColor.WHITE;
        }
        else if(player == HorseToken.TokenColor.WHITE){
            currentPlayer = HorseToken.TokenColor.BLACK;
        }
        
        if((GameState.getInstance().isTerminal())||(depthLimit < 1)){
            if(player == HorseToken.TokenColor.BLACK){
               return DefensiveHeuristic(cloneBoard,player);
                //return getOffensiveHeuristic(cloneBoard,currentPositionMax,currentPlayer);
            }
            else if(player == HorseToken.TokenColor.WHITE){
               return DefensiveHeuristic(cloneBoard,player);
               //return getDefensiveHeuristic(cloneBoard,currentPositionMax,currentPlayer);
            }
        }       
        
        double minValue;
        minValue = beta;
        Board cloneBoardNext;
        ArrayList<HorseToken> horseTokenList = new ArrayList<HorseToken>();
        GameState.getInstance().reset();
        for(int row=0;row<cloneBoard.getDimension();row++){
            for(int column=0;column<cloneBoard.getDimension();column++){
                if(cloneBoard.getHorseToken(new Position(row+1,column+1)) != null){
                    if(cloneBoard.getHorseToken(new Position(row+1,column+1)).getTokenColor() == player){
                        horseTokenList.add(cloneBoard.getHorseToken(new Position(row+1,column+1)));
                        horseTokenList.get(horseTokenList.size()-1).setPossibleMoves(
                                HorseToken.setPossibleMovementsList(cloneBoard, cloneBoard.getHorseToken(new Position(row+1,column+1)),false));
                    }    
                }
            }
        }
        for(int i=0; i<horseTokenList.size(); i++){
            if(this.isHint == false){
                NodeStats.getInstance().increaseExpandedTotal(1);
            }
            ArrayList<Position> movesCurrentHorseToken =  horseTokenList.get(i).getPossibleMoves();
            for(int j=0; j<movesCurrentHorseToken.size(); j++){
                if(this.isHint == false){
                    NodeStats.getInstance().increaseExpandedTotal(1);
                }
                cloneBoardNext = cloneBoard.clone();
                HorseToken currentHorseToken = cloneBoardNext.getHorseToken(horseTokenList.get(i).getPosition());
                keepGoing(currentPlayer,cloneBoardNext, currentHorseToken, movesCurrentHorseToken.get(j));
                cloneBoardNext.deleteHorseToken(currentHorseToken.getPosition());
                cloneBoardNext.setHorseToken(currentHorseToken, movesCurrentHorseToken.get(j));
                if(cloneBoardNext.getHorseToken(movesCurrentHorseToken.get(j)) != null){
                    cloneBoardNext.deleteHorseToken(movesCurrentHorseToken.get(j));
                }
                if(currentHorseToken instanceof HorseQueen){
                    cloneBoardNext.setHorseToken(new HorseBaby(currentHorseToken.getPosition(),currentHorseToken.getTokenColor()),currentHorseToken.getPosition());
                }
                currentHorseToken.setPosition(movesCurrentHorseToken.get(j));
  
                Position currentPositionMin = movesCurrentHorseToken.get(j);
                minValue =  Math.max(minValue, minVal(cloneBoardNext,beta,alpha,depthLimit - 1 ,player,currentPositionMin));
                if(minValue >= beta){
                    beta = minValue;
                    //return beta;
                }
                else{
                    alpha = Math.max(alpha, minValue);
                }
            }
        }
        return beta;
    }
    
    private void keepGoing(HorseToken.TokenColor player, Board cloneBoard, HorseToken horseToken, Position position){
        if(player == HorseToken.TokenColor.BLACK){
            if(cloneBoard.getHorseToken(position) != null){
                if(cloneBoard.getHorseToken(position).getTokenColor() == player){
                    if(cloneBoard.getHorseToken(position) instanceof HorseQueen){
                        GameState.getInstance().setKillHorseQueenBlack(true);
                    }
                    else if(cloneBoard.getHorseToken(position) instanceof HorseBaby){
                        GameState.getInstance().setKillHorseBabyBlack(true);
                    }
                }
            }
        }
        else if(player == HorseToken.TokenColor.WHITE){
            if(cloneBoard.getHorseToken(position) != null){
                if(cloneBoard.getHorseToken(position).getTokenColor() == player){
                    if(cloneBoard.getHorseToken(position) instanceof HorseQueen){
                        GameState.getInstance().setKillHorseQueenWhite(true);
                    }
                    if(cloneBoard.getHorseToken(position) instanceof HorseBaby){
                        GameState.getInstance().setKillHorseBabyWhite(true);
                    }
                }
            }
        }
    }
    
    private double DefensiveHeuristic(Board state, HorseToken.TokenColor player) {
      // Hacemos una copia de valores antiguos de cantidad
      int whiteTokens = whiteHorses;
      int blackTokens = blackHorses;
      
      // Recalculamos valores de cantidad
      countTokens(state);
      System.out.println("Antes habian "+blackTokens+" negras y " + whiteTokens+" blancas");
      System.out.println("Ahora hay "+blackHorses+" negras y " + whiteHorses+" blancas");
      
      if(player == HorseToken.TokenColor.BLACK) {
         if((whiteTokens == whiteHorses) && (blackTokens < blackHorses)) {
            return 3;
         }
         if((whiteTokens == whiteHorses) && (blackTokens > blackHorses)) {
            if(blackQueenAlive(state) == false) {
               return 10;
            }
            else {
               return 9;
            }
         }
         if((whiteTokens > whiteHorses) && (blackTokens == blackHorses)) {
            if(whiteQueenAlive(state) == false) {
               return 1;
            }
            else {
               return 2;
            }
         }
         return 5;
      }
      else {
         if((whiteTokens < whiteHorses) && (blackTokens == blackHorses)) {
            return 3;
         }
         if((whiteTokens > whiteHorses) && (blackTokens == blackHorses)) {
            if(whiteQueenAlive(state) == false) {
               return 10;
            }
            else {
               return 9;
            }
         }
         if((whiteTokens == whiteHorses) && (blackTokens > blackHorses)) {
            if(blackQueenAlive(state) == false) {
               return 1;
            }
            else {
               return 2;
            }
         }
         return 5;
      }
   }
   
   private double OffensiveHeuristic(Board state) {
      return 0;
   }
   
   private void countTokens(Board state) {
      whiteHorses = blackHorses = 0;
      for(int i = 1; i <= state.getDimension(); i++) {
         for(int j = 1; j <= state.getDimension(); j++) {
            if(state.getHorseToken(new Position(i, j)) != null) {
               if(state.getHorseToken(new Position(i, j)).getTokenColor() == HorseToken.TokenColor.BLACK)
                  blackHorses++;
               else whiteHorses++;
            }
         }
      }
   }   
   private boolean blackQueenAlive(Board state) {
      for(int i = 1; i <= state.getDimension(); i++) {
         for(int j = 1; j <= state.getDimension(); j++) {
            if(state.getHorseToken(new Position(i, j)) != null) {
               HorseToken aux = state.getHorseToken(new Position(i, j));
               if((aux.getTokenColor() == HorseToken.TokenColor.BLACK) &&
                  (aux instanceof HorseQueen))
                  return true;
            }
         }
      }
      return false;
   }   
   private boolean whiteQueenAlive(Board state) {
      for(int i = 1; i <= state.getDimension(); i++) {
         for(int j = 1; j <= state.getDimension(); j++) {
            if(state.getHorseToken(new Position(i, j)) != null) {
               HorseToken aux = state.getHorseToken(new Position(i, j));
               if((aux.getTokenColor() == HorseToken.TokenColor.WHITE) &&
                  (aux instanceof HorseQueen))
                  return true;
            }
         }
      }
      return false;      
   }
    
    /*private int getOffensiveHeuristic(Board cloneBoard, Position currentPosition, HorseToken.TokenColor currentPlayer){
        ArrayList<Position> possibleMoves = null;
        if(currentPlayer == HorseToken.TokenColor.WHITE){
            if(GameState.getInstance().isKillHorseQueenBlack()) {
                GameState.getInstance().reset();
                return 1;
            }
            if(GameState.getInstance().isKillHorseBabyBlack()) {
                if(cloneBoard.getHorseToken(currentPosition) != null){
                    cloneBoard.getHorseToken(currentPosition).setPossibleMoves(
                        HorseToken.setPossibleMovementsList(cloneBoard,cloneBoard.getHorseToken(currentPosition),true));   
                    possibleMoves = cloneBoard.getHorseToken(currentPosition).getPossibleMoves();
                }
                if(possibleMoves != null){
                    for(Position position : possibleMoves){
                        if(cloneBoard.getHorseToken(position) != null){
                            if(cloneBoard.getHorseToken(position).getTokenColor() != currentPlayer){
                                if(cloneBoard.getHorseToken(currentPosition) instanceof HorseQueen){
                                    GameState.getInstance().reset();
                                    return 10;
                                }
                                else if(cloneBoard.getHorseToken(currentPosition) instanceof HorseBaby){
                                    GameState.getInstance().reset();
                                    return 2;
                                }
                            }
                        }
                    }
                }
                GameState.getInstance().reset();
                return 1;
            }
            else{
                if(cloneBoard.getHorseToken(currentPosition) != null){
                    cloneBoard.getHorseToken(currentPosition).setPossibleMoves(
                        HorseToken.setPossibleMovementsList(cloneBoard,cloneBoard.getHorseToken(currentPosition),true));
                    possibleMoves = cloneBoard.getHorseToken(currentPosition).getPossibleMoves();
                }
                if(possibleMoves != null){
                    for(Position position : possibleMoves){
                        if(cloneBoard.getHorseToken(position) != null){
                            if(cloneBoard.getHorseToken(position).getTokenColor() != currentPlayer){
                                if(cloneBoard.getHorseToken(currentPosition) instanceof HorseQueen){
                                    GameState.getInstance().reset();
                                    return 10;
                                }
                                else if(cloneBoard.getHorseToken(currentPosition) instanceof HorseBaby){
                                    GameState.getInstance().reset();
                                    return 5;
                                }
                            }
                        }
                    }
                }
                GameState.getInstance().reset();
                return 3;
            }
        }
        else if(currentPlayer == HorseToken.TokenColor.BLACK){
            if(GameState.getInstance().isKillHorseQueenWhite()){
                GameState.getInstance().reset();
                return 1;
            }
            if(GameState.getInstance().isKillHorseBabyWhite()) {
                if(cloneBoard.getHorseToken(currentPosition) != null){
                    cloneBoard.getHorseToken(currentPosition).setPossibleMoves(
                            HorseToken.setPossibleMovementsList(cloneBoard,cloneBoard.getHorseToken(currentPosition),true));
                    possibleMoves = cloneBoard.getHorseToken(currentPosition).getPossibleMoves();
                }
                if(possibleMoves != null){
                    for(Position position : possibleMoves){
                        if(cloneBoard.getHorseToken(position) != null){
                            if(cloneBoard.getHorseToken(position).getTokenColor() != currentPlayer){
                                if(cloneBoard.getHorseToken(currentPosition) instanceof HorseQueen){
                                    GameState.getInstance().reset();
                                    return 10;
                                }
                                else if(cloneBoard.getHorseToken(currentPosition) instanceof HorseBaby){
                                    GameState.getInstance().reset();
                                    return 2;
                                }
                            }
                        }
                    }
                }
                GameState.getInstance().reset();
                return 1;
            }
            else{
                if(cloneBoard.getHorseToken(currentPosition) != null){
                    cloneBoard.getHorseToken(currentPosition).setPossibleMoves(
                        HorseToken.setPossibleMovementsList(cloneBoard,cloneBoard.getHorseToken(currentPosition),true));   
                    possibleMoves = cloneBoard.getHorseToken(currentPosition).getPossibleMoves();
                }

                if(possibleMoves != null){
                    for(Position position : possibleMoves){
                        if(cloneBoard.getHorseToken(position) != null){
                            if(cloneBoard.getHorseToken(position).getTokenColor() != currentPlayer){
                                if(cloneBoard.getHorseToken(currentPosition) instanceof HorseQueen){
                                    GameState.getInstance().reset();
                                    return 10;
                                }
                                else if(cloneBoard.getHorseToken(currentPosition) instanceof HorseBaby){
                                    GameState.getInstance().reset();
                                    return 5;
                                }
                            }
                        }
                    }
                }
                GameState.getInstance().reset();
                return 3;
            }
        }
        GameState.getInstance().reset();
        return -1;
    }
    
    private int getDefensiveHeuristic(Board cloneBoard, Position currentPosition, HorseToken.TokenColor currentPlayer){
        ArrayList<Position> possibleMoves = null;
        if(currentPlayer == HorseToken.TokenColor.WHITE){
            if(GameState.getInstance().isKillHorseQueenBlack()) {
                GameState.getInstance().reset();
                return 1;
            }
            if(GameState.getInstance().isKillHorseBabyBlack()) {
                if(cloneBoard.getHorseToken(currentPosition) != null){
                    cloneBoard.getHorseToken(currentPosition).setPossibleMoves(
                        HorseToken.setPossibleMovementsList(cloneBoard,cloneBoard.getHorseToken(currentPosition),true));   
                    possibleMoves = cloneBoard.getHorseToken(currentPosition).getPossibleMoves();
                }
                if(possibleMoves != null){
                    for(Position position : possibleMoves){
                        if(cloneBoard.getHorseToken(position) != null){
                            if(cloneBoard.getHorseToken(position).getTokenColor() != currentPlayer){
                                if(cloneBoard.getHorseToken(currentPosition) instanceof HorseQueen){
                                    GameState.getInstance().reset();
                                    return 10;
                                }
                                else if(cloneBoard.getHorseToken(currentPosition) instanceof HorseBaby){
                                    GameState.getInstance().reset();
                                    return 8;
                                }
                            }
                        }
                    }
                }
                GameState.getInstance().reset();
                return 1;
            }
            else{
                if(cloneBoard.getHorseToken(currentPosition) != null){
                    cloneBoard.getHorseToken(currentPosition).setPossibleMoves(
                        HorseToken.setPossibleMovementsList(cloneBoard,cloneBoard.getHorseToken(currentPosition),true));
                    possibleMoves = cloneBoard.getHorseToken(currentPosition).getPossibleMoves();
                }
                if(possibleMoves != null){
                    for(Position position : possibleMoves){
                        if(cloneBoard.getHorseToken(position) != null){
                            if(cloneBoard.getHorseToken(position).getTokenColor() != currentPlayer){
                                if(cloneBoard.getHorseToken(currentPosition) instanceof HorseQueen){
                                    GameState.getInstance().reset();
                                    return 10;
                                }
                                else if(cloneBoard.getHorseToken(currentPosition) instanceof HorseBaby){
                                    GameState.getInstance().reset();
                                    return 9;
                                }
                            }
                        }
                    }
                }
                GameState.getInstance().reset();
                return 3;
            }
        }
        else if(currentPlayer == HorseToken.TokenColor.BLACK){
            if(GameState.getInstance().isKillHorseQueenWhite()){
                GameState.getInstance().reset();
                return 1;
            }
            if(GameState.getInstance().isKillHorseBabyWhite()) {
                if(cloneBoard.getHorseToken(currentPosition) != null){
                    cloneBoard.getHorseToken(currentPosition).setPossibleMoves(
                            HorseToken.setPossibleMovementsList(cloneBoard,cloneBoard.getHorseToken(currentPosition),true));
                    possibleMoves = cloneBoard.getHorseToken(currentPosition).getPossibleMoves();
                }
                if(possibleMoves != null){
                    for(Position position : possibleMoves){
                        if(cloneBoard.getHorseToken(position) != null){
                            if(cloneBoard.getHorseToken(position).getTokenColor() != currentPlayer){
                                if(cloneBoard.getHorseToken(currentPosition) instanceof HorseQueen){
                                    GameState.getInstance().reset();
                                    return 10;
                                }
                                else if(cloneBoard.getHorseToken(currentPosition) instanceof HorseBaby){
                                    GameState.getInstance().reset();
                                    return 8;
                                }
                            }
                        }
                    }
                }
                GameState.getInstance().reset();
                return 1;
            }
            else{
                if(cloneBoard.getHorseToken(currentPosition) != null){
                    cloneBoard.getHorseToken(currentPosition).setPossibleMoves(
                        HorseToken.setPossibleMovementsList(cloneBoard,cloneBoard.getHorseToken(currentPosition),true));   
                    possibleMoves = cloneBoard.getHorseToken(currentPosition).getPossibleMoves();
                }
                if(possibleMoves != null){
                    for(Position position : possibleMoves){
                        if(cloneBoard.getHorseToken(position) != null){
                            if(cloneBoard.getHorseToken(position).getTokenColor() != currentPlayer){
                                if(cloneBoard.getHorseToken(currentPosition) instanceof HorseQueen){
                                    GameState.getInstance().reset();
                                    return 10;
                                }
                                else if(cloneBoard.getHorseToken(currentPosition) instanceof HorseBaby){
                                    GameState.getInstance().reset();
                                    return 9;
                                }
                            }
                        }
                    }
                }
                GameState.getInstance().reset();
                return 3;
            }
        }
        GameState.getInstance().reset();
        return -1;
    }*/

    private int justOneMove(ArrayList<HorseToken> horseTokenList) {
        int numMoves = 0;
        ArrayList<Position> possibleMoves;
        for(int i=0; i<horseTokenList.size(); i++){
            possibleMoves = horseTokenList.get(i).getPossibleMoves();
            for(int j=0; j<possibleMoves.size(); j++){
                numMoves++;
                if(numMoves == 1){
                    bestPosition = possibleMoves.get(j);
                    initialPosition = horseTokenList.get(i).getPosition();
                }
                else{
                    bestPosition = null;
                    initialPosition = null;
                }
            }   
        }
        return numMoves;
    }
}
