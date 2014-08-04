package component;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogPanel extends JPanel{
    private JTextArea logArea;
    private JScrollPane scrollArea;
    
    public LogPanel() {
        super();
        
        logArea = new JTextArea(25, 25);
        logArea.setEditable(false);
        scrollArea = new JScrollPane(logArea);
        scrollArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        this.add(scrollArea);
    }
    
    public void refresh(String info) {
        logArea.append(info);
    }
    
    public String getLog() {
       return logArea.getText();
    }

   public void clear() {
      logArea.setText("");
   }
}