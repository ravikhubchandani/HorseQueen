package control;

import input.BoardDialog;
import input.OptionsDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.GameState;
import model.HorseToken;
import model.NodeStats;
import output.LogViewer;

public class Restart extends Command{
   private BoardDialog boardDialog;
   private LogViewer logViewer;
   private OptionsDialog optionsDialog;
   private int dimension;
   private int stack;
   private int level;
   
   public Restart(BoardDialog boardDialog, LogViewer logViewer, OptionsDialog optionsDialog) {
      this.boardDialog = boardDialog;
      this.optionsDialog = optionsDialog;
      this.logViewer = logViewer;
      
      optionsDialog.getResetButton().addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                   try {
                   
                      Object[] niveles = {"Fácil", "Medio", "Difícil"};
                      String nivel = (String)JOptionPane.showInputDialog(
                       null, "Selecciona nivel de dificultad", "Nuevo Juego",
                       JOptionPane.QUESTION_MESSAGE, null, niveles, niveles[1]);
                      
                      switch (nivel) {
                         case "Fácil" :
                            level = 1;
                            break;
                         case "Medio":
                            level = 2;
                            break;
                         case "Difícil":
                            level = 3;
                            break;                            
                      }

                      String dimensionString = (String)JOptionPane.showInputDialog("Inserta dimension del tablero", "8");
                      dimension = Integer.parseInt(dimensionString);

                      String stackString = (String)JOptionPane.showInputDialog("Inserta tamaño del stack", "5");
                      stack = Integer.parseInt(stackString);

                      execute();
                   
                   } catch(NumberFormatException e) {
                      JOptionPane.showMessageDialog(null, "Los datos introducidos no son válidos");
                   } catch(Exception e2) {}
                }                
            }
       );
   }
   
   @Override
   public void execute() {
      logViewer.clear();
      
      switch (level) {
        case 1 :
           logViewer.refresh("Nivel de dificultad: Fácil\n");
           logViewer.refresh("Nivel de profundidad: 1\n");
           break;
        case 2:
           logViewer.refresh("Nivel de dificultad: Medio\n");
           logViewer.refresh("Nivel de profundidad: 2\n");
           break;
        case 3:
           logViewer.refresh("Nivel de dificultad: Difícil\n");
           logViewer.refresh("Nivel de profundidad: 3\n");
           break;                            
      }     
      
      logViewer.refresh("Dimension del tablero: "+dimension+"\n");
      logViewer.refresh("-------------------------------------------------------------------\n\n");
      
      boardDialog.reset(dimension, stack);
      optionsDialog.refresh("");
      GameState.getInstance().restartGame();
      GameState.getInstance().setLevel(level);
      NodeStats.getInstance().reset();
      optionsDialog.resetPlayers();
   }
}
