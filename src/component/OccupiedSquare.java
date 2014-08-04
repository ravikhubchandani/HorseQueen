package component;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import model.HorseToken.TokenColor;
import model.Position;

public class OccupiedSquare extends BoardItem{    
    private JLabel icon;
    private TokenColor tokenColor;
    private JTextField stackAmountField;
    
    public OccupiedSquare(TokenColor tokenColor, int stackAmount, Position position) {
        super();
        
        this.position = position;
        this.tokenColor = tokenColor;
        if(TokenColor.WHITE == tokenColor) {
            icon = new JLabel(new ImageIcon("images/white_horse.jpg"));
        }
        else {
            icon = new JLabel(new ImageIcon("images/black_horse.jpg"));
        }
        
        stackAmountField = new JTextField(Integer.toString(stackAmount));
        stackAmountField.setFont(
                new Font(stackAmountField.getFont().getName(), Font.BOLD, 18));
        stackAmountField.setEditable(false);
        
        this.add(icon);
        this.add(stackAmountField);
    }
    
    @Override
    public Color getColor() {
        if(tokenColor == TokenColor.BLACK)
            return Color.gray;
        else
            return Color.white;
    }
}
