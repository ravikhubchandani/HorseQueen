package control;
import input.BoardDialog;
import input.OptionsDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.GameState;
import output.LogViewer;
import process.Agent;

public class Hint extends Command {
    private BoardDialog boardDialog;
    private OptionsDialog optionsDialog;
    private LogViewer logViewer;
    
    public Hint(BoardDialog boardDialog, OptionsDialog optionsDialog, LogViewer logViewer) {
       this.boardDialog = boardDialog;
       this.optionsDialog = optionsDialog;
       this.logViewer = logViewer;
        
       optionsDialog.getHintButton().addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    execute();
                }                
            }
       );
    }
    
    @Override
    public void execute() {
        Agent agent = new Agent(boardDialog.getBoard(), boardDialog, logViewer, boardDialog.getPlayerToMove());
        agent.executeToShow();
        if((agent.getInitialPosition() != null) && (agent.getBestPosition() != null)) {
           boardDialog.blinkCell(agent.getInitialPosition(),agent.getBestPosition());      
        }
        else {
           optionsDialog.refresh("No hay movimientos realizables");
           GameState.getInstance().gameOver(boardDialog.getPlayerToMove(), logViewer);
        }
    }
}