import component.MainWindow;
import control.*;
import input.BoardDialog;
import input.OptionsDialog;
import model.GameState;
import model.HorseQueen;
import model.HorseToken.TokenColor;
import model.Position;
import output.LogViewer;
import process.MovementChecker;

public class Main {
    public static void main(String[] args) {
        final int DIMENSION = 8;
        final int STACK_AMOUNT = 5;
        final int LEVEL = 2;
        
        MainWindow mainWindow = new MainWindow(DIMENSION);
        
        BoardDialog boardDialog =
                new BoardDialog(mainWindow.getBoardPane());
        LogViewer logViewer =
                new LogViewer(mainWindow.getLogPane());
        
        logViewer.refresh("Nivel de dificultad: Media\n");
        logViewer.refresh("Nivel de profundidad: 2\n");
        logViewer.refresh("Dimension del tablero: 8\n");
        logViewer.refresh("-------------------------------------------------------------------\n\n");
        
        //GameState.getInstance().setLogViewer(logViewer);
        OptionsDialog optionsDialog =
                new OptionsDialog(mainWindow.getOptionsPane());
        MovementChecker.setOptionsDialog(optionsDialog);
        
        GameState.getInstance().setLevel(LEVEL);
        
        boardDialog.setItem(new HorseQueen(
                STACK_AMOUNT, new Position(1, DIMENSION/2), TokenColor.WHITE),
                new Position(1, DIMENSION/2));
        boardDialog.setItem(new HorseQueen(
                STACK_AMOUNT, new Position(DIMENSION,  DIMENSION/2+1),
                TokenColor.BLACK), new Position(DIMENSION,  DIMENSION/2+1)); 
        
        Command move = new Move(boardDialog, logViewer, optionsDialog);
        Command hint = new Hint(boardDialog, optionsDialog, logViewer);    
        Command save = new Save(boardDialog, optionsDialog, logViewer);
        Command load = new Load(boardDialog, optionsDialog, logViewer);
        Command reset = new Restart(boardDialog, logViewer, optionsDialog);
        
        boardDialog.refresh();        
        mainWindow.showFrame();
    }
}
