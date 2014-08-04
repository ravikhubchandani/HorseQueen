package component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import model.Position;

public class BoardPanel extends JPanel{
    private BoardItem board[][];
    private int dimension;
    
    public BoardPanel(int dimension) {
        super();
        
        int width = 75*dimension;
        
        this.dimension = dimension;
        this.setLayout(new GridLayout(dimension, dimension));        
        this.setPreferredSize(new Dimension(width, width));        
        board = new BoardItem[dimension][dimension];
    }
    
    public int getDimension() {
        return dimension;
    }
    
    public void setItems(BoardItem items[][]) {
        board = items;
    }
    
    public void refresh(BoardItem board[][]) {
        this.removeAll();
        for (int i=0; i<dimension; i++) {
            for (int j=0; j<dimension; j++) {                
                this.add(board[i][j]);
            }
        }
        this.revalidate();
    }
    
    public void blinkCell(Position position){
        BoardItem boardItem = board[position.getRow()][position.getColumn()];
        boardItem.changeColor(new Color(237,253,102));
    }
    
    public void restart(int dimension) {
        this.dimension = dimension;        
        this.setLayout(new GridLayout(dimension, dimension));
        board = new BoardItem[dimension][dimension];
    }
}