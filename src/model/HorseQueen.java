package model;

public class HorseQueen extends HorseToken{    
    private boolean moreBabies;
    
    public HorseQueen(int stackAmount, Position position, TokenColor tokenColor) {
        this.stackAmount = stackAmount;        
        this.position = position;
        this.tokenColor = tokenColor;
        this.moreBabies = true;
    }
    
    public void decreaseStack() {
        stackAmount--;
    }
}