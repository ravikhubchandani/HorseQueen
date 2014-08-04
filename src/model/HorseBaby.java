package model;

public class HorseBaby extends HorseToken {
    public HorseBaby(Position position, TokenColor tokenColor) {
        stackAmount = 1;
        this.position = position;
        this.tokenColor = tokenColor;
    }
}