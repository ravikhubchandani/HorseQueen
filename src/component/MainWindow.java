package component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;

public class MainWindow extends JFrame{
    private Container content;
    private BoardPanel boardPanel;
    private LogPanel logPanel;
    private OptionsPanel optionsPanel;
    
    public MainWindow(int dimension) {
        super("Horse Queen");
        content = this.getContentPane();       
        
        createBoardPanel(dimension);
        createLogPanel();
        createOptionsPanel();
        //createToolBarPanel();
        
        setContents();
    }
    
    private void createBoardPanel(int dimension) {
        boardPanel = new BoardPanel(dimension);
        
    }
    
    private void createLogPanel() {
        logPanel = new LogPanel();
    }
    
    private void createOptionsPanel() {        
        optionsPanel = new OptionsPanel();
    }
    
    private void setContents() {
        content.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        content.add(boardPanel, constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        content.add(logPanel, constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        content.add(optionsPanel, constraints);
        
        this.pack();
        //this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public BoardPanel getBoardPane() {
        return boardPanel;
    }
    
    public LogPanel getLogPane() {
        return logPanel;
    }
    
    public OptionsPanel getOptionsPane() {
        return optionsPanel;
    }
    
    public void showFrame() {
        this.setVisible(true);
    }
}