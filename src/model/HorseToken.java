package model;

import java.io.Serializable;
import java.util.ArrayList;
import process.MovementChecker;

public abstract class HorseToken implements Serializable{
    public enum TokenColor {WHITE, BLACK};
    
    protected Position position;
    protected int stackAmount;
    protected HorseQueen.TokenColor tokenColor;
    protected ArrayList<Position> possibleMoves;
    
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    public int getStackAmount() {
        return stackAmount;
    }
    
    public TokenColor getTokenColor() {
        return tokenColor;
    }

    public ArrayList<Position> getPossibleMoves() {
        return possibleMoves;
    }
    
    public void setPossibleMoves(ArrayList<Position> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }
    
    public static ArrayList<Position> setPossibleMovementsList(Board board, HorseToken horseToken, boolean hazard){
        ArrayList<Position> possibleMovementsList = new ArrayList<Position>();
        Position[] possiblePositionArray = new Position[8];
        Position currentPosition = new Position(horseToken.getPosition().getRow()+1,horseToken.getPosition().getColumn()+1);
        
        possiblePositionArray[0] = new Position(currentPosition.getRow() + 2 +1,currentPosition.getColumn() + 1 +1);
        possiblePositionArray[1] = new Position(currentPosition.getRow() + 2 +1,currentPosition.getColumn() - 1 +1);
        possiblePositionArray[2] = new Position(currentPosition.getRow() + 1 +1,currentPosition.getColumn() + 2 +1);
        possiblePositionArray[3] = new Position(currentPosition.getRow() - 1 +1,currentPosition.getColumn() + 2 +1);
        possiblePositionArray[4] = new Position(currentPosition.getRow() - 2 +1,currentPosition.getColumn() + 1 +1);
        possiblePositionArray[5] = new Position(currentPosition.getRow() - 2 +1,currentPosition.getColumn() - 1 +1);
        possiblePositionArray[6] = new Position(currentPosition.getRow() + 1 +1,currentPosition.getColumn() - 2 +1);
        possiblePositionArray[7] = new Position(currentPosition.getRow() - 1 +1,currentPosition.getColumn() - 2 +1);
     
        for(Position position : possiblePositionArray){
            if(hazard){
                if(MovementChecker.checkHazard(board, board.getDimension(), horseToken, horseToken.getPosition(), position)){
                    possibleMovementsList.add(position);
                }
            }
            else if(MovementChecker.checkAI(board,horseToken.getTokenColor(), board.getDimension(), horseToken, horseToken.getPosition(), position, board.getBlackQueenPosition(), board.getWhiteQueenPosition())){
                possibleMovementsList.add(position);
            }
        }
        return possibleMovementsList;
    }
}