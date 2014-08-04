package model;

import java.io.Serializable;
import java.util.ArrayList;
import model.HorseToken.TokenColor;

public class Board implements Serializable {
    private HorseToken board[][];
    private Position blackQueenPosition;
    private Position whiteQueenPosition;
    private TokenColor playerToMove;    
    private int dimension;
    
    public Board(int dimension) {
        board = new HorseToken[dimension][dimension];
        this.dimension = dimension;
        playerToMove = TokenColor.WHITE;
    }
   
    @Override
    public Board clone(){
        Board cloneBoard = new Board(this.getDimension());
        cloneBoard.setBlackQueenPosition(this.getBlackQueenPosition());
        cloneBoard.setWhiteQueenPosition(this.getWhiteQueenPosition());
        cloneBoard.playerToMove = this.getPlayerToMove();
        for(int i=0;i<cloneBoard.dimension;i++){
            for(int j=0;j<cloneBoard.dimension;j++){
                if(board[i][j] != null){
                    if(board[i][j] instanceof HorseQueen){
                        cloneBoard.board[i][j] = new HorseQueen(board[i][j].getStackAmount(),board[i][j].getPosition(),board[i][j].getTokenColor());
                    }
                    if(board[i][j] instanceof HorseBaby){
                        cloneBoard.board[i][j] = new HorseBaby(board[i][j].getPosition(),board[i][j].getTokenColor());
                    }
                    if(board[i][j].getPossibleMoves() != null){
                        ArrayList<Position> possibleMoves = new ArrayList<Position>();
                        for(int x=0;x<board[i][j].getPossibleMoves().size();x++){
                            int row = board[i][j].getPossibleMoves().get(x).getRow();
                            int column = board[i][j].getPossibleMoves().get(x).getColumn();
                            possibleMoves.add(new Position(row+1,column+1));
                        }
                        cloneBoard.board[i][j].setPossibleMoves(possibleMoves);
                     }
                }
                else{
                    cloneBoard.board[i][j] = null;
                }
            }
        }
        return cloneBoard;
    }
    
    public Position getBlackQueenPosition() {
        return blackQueenPosition;
    }
    
    public Position getWhiteQueenPosition() {
        return whiteQueenPosition;
    }

    public void setBlackQueenPosition(Position blackQueenPosition) {
        this.blackQueenPosition = blackQueenPosition;
    }

    public void setWhiteQueenPosition(Position whiteQueenPosition) {
        this.whiteQueenPosition = whiteQueenPosition;
    }
    
    public HorseToken getHorseToken(Position position) {        
        return board[position.getRow()][position.getColumn()];
    }
    
    public void setHorseToken(HorseToken horseToken, Position position) {
        board[position.getRow()][position.getColumn()] = horseToken;
        if(horseToken instanceof HorseQueen){
            if(((HorseQueen) horseToken).getTokenColor() == TokenColor.WHITE){
                setWhiteQueenPosition(position);
            }
            else setBlackQueenPosition(position);
        }
    }
    
    public void deleteHorseToken(Position position){
        board[position.getRow()][position.getColumn()] = null;
    }
    
    public int getDimension(){
        return dimension;
    }

    public TokenColor getPlayerToMove() {
        return playerToMove;
    }

    public void changePlayerToMove() {
        if(playerToMove == TokenColor.BLACK)
            playerToMove = TokenColor.WHITE;
        else playerToMove = TokenColor.BLACK;
    }
    
    public void restart(int dimension) {
        board = new HorseToken[dimension][dimension];
        this.dimension = dimension;
        playerToMove = TokenColor.WHITE;
        blackQueenPosition = whiteQueenPosition = null;
    }
}
