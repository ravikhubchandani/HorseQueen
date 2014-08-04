package component;
import java.awt.Color;
import javax.swing.JPanel;
import model.Position;

public abstract class BoardItem extends JPanel{
    protected Position position;
    
    public BoardItem() {
        super();
    }
    
    public Position getPosition() {
        return position;
    }
    
    public abstract Color getColor();
    
    public void changeColor(Color color){
        this.setBackground(color);
    }
}