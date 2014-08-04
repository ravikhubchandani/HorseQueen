package model;

import java.io.Serializable;

public class NodeStats implements Serializable {
    private int expandNodesMove;
    private int expandNodesTotal;
    private int visitedNodes;
    private static NodeStats instance;
    
    private NodeStats() {
        expandNodesMove = 0;
        expandNodesTotal = 0;
        visitedNodes = 0;
    }
    
    public static NodeStats getInstance(){
        if(instance == null){
            instance = new NodeStats();
        }
        return instance;
    }
    
    public void increaseExpandedTotal(int num){
        expandNodesTotal += num;
    }
    
    public void increaseExpandedMove(int num){
        expandNodesMove += num;
    }
    
    public void increaseVisited(){
        visitedNodes++;
    }

    public int getExpandNodesMove() {
        return expandNodesMove;
    }

    public int getExpandNodesTotal() {
        return expandNodesTotal;
    }

    public int getVisitedNodes() {
        return visitedNodes;
    }
    
    public void setNodeStats(NodeStats newInstance) {
       this.instance = newInstance;
    }
    
    public void reset() {
       expandNodesMove = 0;
        expandNodesTotal = 0;
        visitedNodes = 0;
    }
}