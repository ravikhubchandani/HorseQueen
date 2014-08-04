package input;
import component.OptionsPanel;
import javax.swing.JButton;

public class OptionsDialog {
    private OptionsPanel optionsPanel;
    
    public OptionsDialog(OptionsPanel optionsPanel) {        
        this.optionsPanel = optionsPanel;
    }
    
    public void refresh(String info) {
        optionsPanel.refresh(info);
    }
    
    public void togglePlayer() {
       optionsPanel.togglePlayer();
    }
    
    public void resetPlayers() {
        optionsPanel.resetPlayers();
    }
    
    public JButton getMoveButton() {
        return optionsPanel.getMoveButton();
    }
    
    public JButton getHintButton() {
        return optionsPanel.getHintButton();
    }

   public JButton getSaveButton() {
       return optionsPanel.getSaveButton();
   }
   
   public JButton getLoadButton() {
       return optionsPanel.getLoadButton();
   }
   
   public JButton getResetButton() {
      return optionsPanel.getResetButton();
   }
}