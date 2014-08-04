package output;
import component.LogPanel;
import model.NodeStats;

public class LogViewer {
    private LogPanel logPanel;
    private NodeStats stats;
    
    public LogViewer(LogPanel logPanel) {
        this.logPanel = logPanel;
    }
    
    public void refresh(String info) {
        logPanel.refresh(info);
    }
    
    public String getLog() {
       return logPanel.getLog();
    }

   public void clear() {
      logPanel.clear();
   }
}