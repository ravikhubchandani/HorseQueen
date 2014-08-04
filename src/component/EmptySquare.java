package component;
import java.awt.Color;
import model.HorseToken.TokenColor;
import model.Position;

public class EmptySquare extends BoardItem{
    private TokenColor color;
    
    public EmptySquare(TokenColor color, Position position) {
        super();
        this.position = position;
        this.color = color;
        if(color == TokenColor.BLACK)
            this.setBackground(Color.gray);
        else
            this.setBackground(Color.white);
    }

    @Override
    public Color getColor() {
        if(color == TokenColor.BLACK)
            return Color.gray;
        else
            return Color.white;
    }
}
