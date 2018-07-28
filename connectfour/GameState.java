package connectfour;

import java.util.ArrayList;

public class GameState{
    
    int W;
    int H;
    int[][] board;
    ArrayList<GameState> boardchildren;
    static boolean leaf;
    int[] tracker;
    int turn;
    int previousmove;
    
    public GameState(int[][] b,ArrayList<GameState> arrl,int[] t, int w, int h, int trn, int prev){
        W=w;
        H=h;
        board=b;
        boardchildren=arrl;
        turn=trn;
        tracker=t;
        previousmove=prev;
    }
    
    public int[][] getBoard(){
        return board;
    }
    public ArrayList<GameState> getChildren(){
        return boardchildren;
    }
    public static boolean isLeaf(){
        return leaf;
    }
    public int[] getTracker(){
        return tracker;
    }
    public int getTurn(){
        return turn;
    }
    public int getPrev(){
        return previousmove;
    }
    
}