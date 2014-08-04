package process;
import input.OptionsDialog;
import java.util.ArrayList;
import model.Board;
import model.HorseBaby;
import model.HorseQueen;
import model.HorseToken;
import model.Position;

public class MovementChecker {
    private static OptionsDialog optionsDialog;
    public static boolean check(Board board,HorseToken.TokenColor playerToMove,int dimension,HorseToken token,Position position1, Position position2,
                                Position queenBlackPosition, Position queenWhitePosition, boolean showHint){
        boolean move = false;
        Position queenPosition = null;
        if(playerToMove != token.getTokenColor()) {
           if(showHint) {
            optionsDialog.refresh("Es el turno de " + playerToMove);
           }
           return false;
        }
        if(checkRanges(position1,dimension) && checkRanges(position2,dimension)){
            if(checkHorseLMovement(position1, position2)){
                if(board.getHorseToken(position2) != null){
                    if(board.getHorseToken(position2).getTokenColor() != playerToMove){
                        return true;
                    }
                }
                
                if(token instanceof HorseBaby){
                    ArrayList<Position> possibleMovementsList = new ArrayList<Position>();
                    Position[] possiblePositionArray = new Position[8];

                    possiblePositionArray[0] = new Position(position2.getRow() + 2 +1,position2.getColumn() + 1 +1);
                    possiblePositionArray[1] = new Position(position2.getRow() + 2 +1,position2.getColumn() - 1 +1);
                    possiblePositionArray[2] = new Position(position2.getRow() + 1 +1,position2.getColumn() + 2 +1);
                    possiblePositionArray[3] = new Position(position2.getRow() - 1 +1,position2.getColumn() + 2 +1);
                    possiblePositionArray[4] = new Position(position2.getRow() - 2 +1,position2.getColumn() + 1 +1);
                    possiblePositionArray[5] = new Position(position2.getRow() - 2 +1,position2.getColumn() - 1 +1);
                    possiblePositionArray[6] = new Position(position2.getRow() + 1 +1,position2.getColumn() - 2 +1);
                    possiblePositionArray[7] = new Position(position2.getRow() - 1 +1,position2.getColumn() - 2 +1);

                    for(Position position : possiblePositionArray){
                        if(MovementChecker.checkHazard(board,board.getDimension(), token, position2, position)){
                            possibleMovementsList.add(position);
                        }
                    }

                    for(Position position : possibleMovementsList){
                        if(playerToMove == HorseToken.TokenColor.BLACK){
                            if((position.getColumn() == queenWhitePosition.getColumn())&&(position.getRow() == queenWhitePosition.getRow())){
                                return true;
                            }                      
                        }
                        if(playerToMove == HorseToken.TokenColor.WHITE){
                            if((position.getColumn() == queenBlackPosition.getColumn())&&(position.getRow() == queenBlackPosition.getRow())){
                                return true;
                            }                      
                        }
                    }   
                }
                
                if(token.getTokenColor() == HorseToken.TokenColor.BLACK){
                    queenPosition = queenWhitePosition;
                }
                else queenPosition = queenBlackPosition;
               
                if(token instanceof HorseBaby){
                    if(checkEuclideanDistance(position1,position2,queenPosition)) {
                       move = true;            
                    }
                    else if(showHint){
                       optionsDialog.refresh("La distancia euclídea es mayor");
                    }                  
                }
                else move = true;
    
                if(checkQueenStack(token)) {
                   if(showHint) {
                     optionsDialog.refresh("Stack insuficiente");
                   }
                   move = false;
                }
                return move;
            }
            else if(showHint){
               optionsDialog.refresh("El movimiento no tiene forma de L");
               return false;
            }
        }        
        if(showHint) {
           optionsDialog.refresh("El movimiento no es válido");
        }
        return false;
    }
    
    public static boolean checkAI(Board board,HorseToken.TokenColor playerToMove,int dimension,HorseToken token,Position position1, Position position2,
                                Position queenBlackPosition, Position queenWhitePosition){
        if(check(board,playerToMove,dimension,token,position1,position2,queenBlackPosition,queenWhitePosition, false)){
            if(board.getHorseToken(position2) == null) return true;
            else if(board.getHorseToken(position2).getTokenColor() != playerToMove) return true;
        }
        return false;
    }
    
    public static void setOptionsDialog(OptionsDialog optionsDialog) {
       MovementChecker.optionsDialog = optionsDialog;
    }
    
    public static boolean checkHazard(Board board,int dimension,HorseToken token,Position position1, Position position2){
        boolean move = false;
        if(checkRanges(position1,dimension) && checkRanges(position2,dimension)){
            if(checkHorseLMovement(position1, position2)){
                if(board.getHorseToken(position2) == null) return true;
                else 
                    if(board.getHorseToken(position2).getTokenColor() == token.getTokenColor()) return false;
                    else return true;
                
            }
        }
        return false;
    }
    
    private static boolean checkRanges(Position position, int dimension){
        if ((position.getColumn() < dimension) && (position.getRow() < dimension)){
            if ((position.getColumn() >= 0) && (position.getRow() >= 0)){
                return true;
            }
        }
        return false;
    }

    private static boolean checkQueenStack(HorseToken token) {
        if (token instanceof HorseQueen) {
            if (token.getStackAmount() <= 2) {
                return true;
            }
        }
        return false;
    }
        
    private static boolean checkHorseLMovement(Position position1, Position position2) {
        int limitRowUpTwo = position1.getRow() + 2;
        int limitRowDownTwo = position1.getRow() - 2;
        int limitRowUpOne = position1.getRow() + 1;
        int limitRowDownOne = position1.getRow() - 1;
        
        int limitColumnRightOne = position1.getColumn() + 1;
        int limitColumnLeftOne = position1.getColumn() - 1;
        int limitColumnRightTwo = position1.getColumn() + 2;
        int limitColumnLeftTwo = position1.getColumn() - 2;
        
        if((limitRowUpTwo == position2.getRow())&&(limitColumnRightOne == position2.getColumn())) return true;
        if((limitRowUpTwo == position2.getRow())&&(limitColumnLeftOne == position2.getColumn())) return true;
        if((limitRowDownTwo == position2.getRow())&&(limitColumnRightOne == position2.getColumn())) return true;
        if((limitRowDownTwo == position2.getRow())&&(limitColumnLeftOne == position2.getColumn())) return true;
        if((limitRowUpOne == position2.getRow())&&(limitColumnRightTwo == position2.getColumn())) return true;
        if((limitRowUpOne == position2.getRow())&&(limitColumnLeftTwo == position2.getColumn())) return true;
        if((limitRowDownOne == position2.getRow())&&(limitColumnRightTwo == position2.getColumn())) return true;
        if((limitRowDownOne == position2.getRow())&&(limitColumnLeftTwo == position2.getColumn())) return true;
        
        return false;
    }
    
    private static boolean checkEuclideanDistance(Position position1, Position position2,Position queenPosition){
        double euclideanToPosition = calculateEuclideanDistance(position1,queenPosition);
        double euclideanToQueen = calculateEuclideanDistance(position2,queenPosition);
        return (euclideanToPosition > euclideanToQueen);
    }
    
    private static double calculateEuclideanDistance(Position position1, Position position2){
        return Math.sqrt(Math.pow(position1.getColumn()-position2.getColumn(), 2) + 
                         Math.pow(position1.getRow()-position2.getRow(), 2));
    }
}

