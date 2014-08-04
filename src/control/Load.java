package control;

import input.BoardDialog;
import input.OptionsDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.GameInformation;
import model.GameState;
import model.HorseToken;
import model.NodeStats;
import output.LogViewer;
import process.GameFileManager;

public class Load extends Command{
   private BoardDialog boardDialog;
   private LogViewer logViewer;
   private OptionsDialog optionsDialog;
   
   public Load(BoardDialog boardDialog, OptionsDialog optionsDialog, LogViewer logViewer) {
      this.boardDialog = boardDialog;
      this.logViewer = logViewer;
      this.optionsDialog = optionsDialog;
      
      optionsDialog.getLoadButton().addActionListener(
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
      JFileChooser fc = new JFileChooser();
      FileFilter filter = new FileNameExtensionFilter("Horse Queen File", "hq");
      fc.setAcceptAllFileFilterUsed(false);
      fc.addChoosableFileFilter(filter);      
      
      if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
         GameInformation loadedGame = GameFileManager.LoadGame(fc.getSelectedFile().getPath());
         HorseToken.TokenColor previousPlayer = boardDialog.getPlayerToMove();
         boardDialog.reset(loadedGame.getBoard().getDimension());
         boardDialog.setBoard(loadedGame.getBoard());
         NodeStats.getInstance().setNodeStats(loadedGame.getNodeStats());
         GameState.getInstance().setGameState(loadedGame.getGameState());
         
         if(boardDialog.getPlayerToMove() != previousPlayer) {
            optionsDialog.togglePlayer();
         }

         logViewer.clear();
         logViewer.refresh(loadedGame.getLog());
         boardDialog.refresh();
      }
   }
}
