package model;

import java.io.Serializable;

public class Position implements Serializable{
    private int Row, Column;
    
    public Position(int X, int Y) {
        Row = X-1;
        Column = Y-1;
    }
    
    public int getRow() {
        return Row;
    }
    
    public int getColumn() {
        return Column;
    }
}