package component;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OptionsPanel extends JPanel{
    private JButton save;
    private JButton load;
    private JButton move;
    private JButton hint;
    private JLabel infoText;
    private JButton reset;
    private boolean whiteMoves;
    
    public OptionsPanel() {
        super();        
        this.setLayout(new GridLayout(6,1));
        
        move = new JButton("Mueve WHITE");
        whiteMoves = false;
        
        hint = new JButton("Dame una pista");
        save = new JButton("Guardar partida");
        load = new JButton("Cargar partida");
        reset = new JButton("Reiniciar");
        infoText = new JLabel("", JLabel.CENTER);
                
        this.add(move);
        this.add(hint);
        this.add(save);
        this.add(load);
        this.add(reset);
        this.add(infoText);
    }
    
    public void refresh(String info) {
        infoText.setText(info);
    }
    
    public JButton getMoveButton() {
        return move;
    }
    
    public JButton getHintButton() {
        return hint;
    }
    
    public JButton getSaveButton() {
        return save;
    }
    
    public JButton getLoadButton() {
        return load;
    }
    
    public JButton getResetButton() {
       return reset;
    }    
    
    public void togglePlayer() {
       if(whiteMoves) {
          move.setText("Mueve WHITE");
          whiteMoves = false;
       }
       else {
          move.setText("Mueve BLACK");
          whiteMoves = true;
       }
    }
    
    public void resetPlayers() {
        whiteMoves = true;
        togglePlayer();
    }
}