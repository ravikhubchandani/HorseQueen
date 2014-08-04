package control;
import output.LogViewer;
import input.BoardDialog;
import input.OptionsDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.GameState;
import process.Agent;
import process.ClickableItemsProcessor;

public class Move extends Command {
    private BoardDialog boardDialog;
    private LogViewer logViewer;
    private OptionsDialog optionsDialog;
    private ClickableItemsProcessor processor;
    
    public Move(BoardDialog boardDialog, LogViewer logViewer,
            OptionsDialog optionsDialog) {
        this.boardDialog = boardDialog;
        this.logViewer = logViewer;
        this.optionsDialog = optionsDialog;
        execute();        
    }
    
    @Override
    public void execute() {
        processor = ClickableItemsProcessor.getInstance(
                boardDialog, logViewer, optionsDialog);
        
        optionsDialog.getMoveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                move();
            }
        });
    }
    
    private void move() {
        if(!GameState.getInstance().isGameOver()){
            Agent agent = new Agent(boardDialog.getBoard(), boardDialog, logViewer, boardDialog.getPlayerToMove());
            agent.executeToMove(optionsDialog);
            if((agent.getInitialPosition() != null) && (agent.getBestPosition() != null)) {
                boardDialog.changePlayerToMove();      
            }
            else {
                optionsDialog.refresh("No hay movimientos realizables");
                GameState.getInstance().gameOver(boardDialog.getPlayerToMove(), logViewer);
            }
        }
    }
}