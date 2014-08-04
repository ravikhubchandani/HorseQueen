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
import model.NodeStats;
import output.LogViewer;
import process.GameFileManager;

public class Save extends Command{
   private BoardDialog boardDialog;
   private LogViewer logViewer;
   
   public Save(BoardDialog boardDialog, OptionsDialog optionsDialog, LogViewer logViewer) {
      this.boardDialog = boardDialog;
      this.logViewer = logViewer;
      
      optionsDialog.getSaveButton().addActionListener(
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
      
      if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
         GameFileManager.SaveGame(
                 new GameInformation(boardDialog.getBoard(),
                 NodeStats.getInstance(),
                 GameState.getInstance(),
                 logViewer.getLog()),
                 fc.getSelectedFile().getPath() + ".hq");
      }
   }
}
