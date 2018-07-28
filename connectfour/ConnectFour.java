/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connectfour;

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

/**
 *
 * @author Matthew
 */
public class ConnectFour {
    
    static int W;
    static int H;
    static int TOWIN;
    static int[] tracker;
    static int[][] board;
    static GameState root;
    static int teammulti=2;
    static int OFWmulti=2;
    static int difficulty = 6;
    static int diffcount=0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("What is the width of the board? (Regular Size is 7)");
        W = scan.nextInt();
        System.out.println("What is the height of the board? (Regular Size is 6)");
        H = scan.nextInt();
        System.out.println("How many in a row do you need to win? (Regular is 4)");
        TOWIN = scan.nextInt();
        tracker = new int[W];
        for(int i=0;i<W;i++){
            tracker[i]=H-1;
        }
        board = new int[H][W];
        System.out.println("What type of game do you want to play?");
        System.out.println("(1) Player vs. Player");
        System.out.println("(2) Player vs. AI");
        System.out.println("(3) AI vs. Player");
        System.out.println("(4) AI vs AI");
        int gamemode = scan.nextInt();
        if(gamemode==1){
            System.out.println("You have started a Player vs. Player game. First player will be O and second player will be X. Good Luck!");
            printBoard(board);
            System.out.println();
            boolean notover = true;
            while(notover){
            System.out.println("First player's move? ");
            int moveA = scan.nextInt();
            board = dropChecker(moveA,3,board,tracker,true);
            gameOver(3);
            System.out.println("Second player's move? ");
            int moveB = scan.nextInt();
            board = dropChecker(moveB,7,board,tracker,true);
            gameOver(7);
            }
        }
        if(gamemode==2){
            System.out.println("You have started a Player vs. AI game. First player will be O and second player will be X. Good Luck!");
            printBoard(board);
            System.out.println();
            boolean notover = true;
            while(notover){
                
            //human    
            System.out.println("First player's move? ");
            int moveA = scan.nextInt();
            board = dropChecker(moveA,3,board,tracker,true);
            gameOver(3);
            
            
            
            //AI
            root = new GameState(board,buildTree(board,tracker,7,difficulty),tracker,W,H,3,0);
            //System.out.println("size: " + root.getChildren().size());
            //System.out.println("minimax move: " + minimax(root,0,0));         
            //printRoot(root);
            int move = minimax(root, 0, 0,3);
            board = dropChecker(move, 7, board, tracker,true);
            gameOver(7);
            
            
            }
        }
        if(gamemode==3){
            System.out.println("You have started an AI vs. player game. First player will be O and second player will be X. Good Luck!");
            printBoard(board);
            System.out.println();
            boolean notover = true;
            while(notover){
                
            //AI
            root = new GameState(board,buildTree(board,tracker,3,difficulty),tracker,W,H,7,0);
            //System.out.println("size: " + root.getChildren().size());
            //System.out.println("minimax move: " + minimax(root,0,0));         
            //printRoot(root);
            int move = minimax(root, 0, 0,7);
            board = dropChecker(move, 3, board, tracker,true);
            gameOver(3);
            
            //human    
            System.out.println("Second player's move? ");
            int moveA = scan.nextInt();
            board = dropChecker(moveA,7,board,tracker,true);
            gameOver(7);
            
            }
        }
        if(gamemode==4){
            System.out.println("You have started an AI vs. AI game. First player will be O and second player will be X. Good Luck!");
            printBoard(board);
            System.out.println();
            boolean notover = true;
            while(notover){
                
            //AI
            root = new GameState(board,buildTree(board,tracker,3,difficulty),tracker,W,H,7,0);
            //System.out.println("size: " + root.getChildren().size());
            //System.out.println("minimax move: " + minimax(root,0,0));         
            //printRoot(root);
            int move = minimax(root, 0, 0,7);
            board = dropChecker(move, 3, board, tracker,true);
            gameOver(3);
            
            //AI
            root = new GameState(board,buildTree(board,tracker,7,difficulty),tracker,W,H,3,0);
            //System.out.println("size: " + root.getChildren().size());
            //System.out.println("minimax move: " + minimax(root,0,0));         
            //printRoot(root);
            int move2 = minimax(root, 0, 0,3);
            board = dropChecker(move2, 7, board, tracker,true);
            gameOver(7);
            
            }
        }
        
        
        
        
        
    }
    
    public static int howGood(int[][] b,int team){
        int total=0;
        int good=0;
        int bad=0;
        int otherteam=0;
        int halfway = (W/2);
        if(team==3){
            otherteam=7;
        }
        else if(team==7){
            otherteam=3;
        }
        
        
        for(int i=0;i<H;i++){
            for(int j=0;j<W;j++){
                if(b[i][j]==team){
                    int midbonus = Math.abs(j-halfway);
                    good=(good-midbonus);
                    int hor=1;
                    int ver=1;
                    int diagA=1;
                    int diagB=1;
                    //horizontal
                    //left  
                    int lblock = 0;
                    int lzero=0;
                    int ltoadd=0;
                    for(int l=1;l<TOWIN;l++){
                        try{
                            if(b[i][j-l]==team){
                                if(lzero==1){
                                    ltoadd++;
                                }
                                if(lzero==0){
                                hor++;
                                }
                            }
                            else if(b[i][j-l]==0){
                                lzero++;                              
                            }
                            else{
                                l=TOWIN;
                            }
                            if(b[i][j-l]==otherteam){
                                lblock=1;
                            }
                        }
                        catch(Exception e){
                            l=TOWIN;
                            lblock=1;
                        }
                    }
                    
                    //right
                    int rblock=0;
                    int rzero=0;
                    int rtoadd=0;
                    for(int r=1;r<TOWIN;r++){
                        try{
                            if(b[i][j+r]==team){
                                if(rzero==1){
                                    rtoadd++;
                                }
                                if(rzero==0){
                                hor++;
                                }
                            }
                            else if(b[i][j+r]==0){
                                rzero++;
                            }
                            else{
                                r=TOWIN;
                            }
                            if(b[i][j+r]==otherteam){
                                rblock=1;
                            }
                        }
                        catch(Exception e){
                            r=TOWIN;
                            rblock=1;
                        }
                    }
                    int hortoadd=0;
                    if(ltoadd>rtoadd){
                        hortoadd=ltoadd;
                    }
                    else{
                        hortoadd=rtoadd;
                    }
                    hor=hor+hortoadd;
                    
                    if(hor>=TOWIN){
                        //System.out.println("You win horizontally at: (" + i + ", " + j + ")");
                        good=good+6000000;
                    }
                    else if(lblock==0 || rblock==0){
                        //int multiplier=0;
                        //multiplier=(hor-TOWIN);
                        //System.out.println("hor: " + hor);
                        double tmp = Math.pow(hor, teammulti);            
                        int toadd = (int)tmp;
                        good = (toadd+good);
                    }
                    //horizontal end
                    
                    //vertical
                    //down
                    int dblock=0;
                    int dzero=0;
                    int dtoadd=0;
                    for(int d=1;d<TOWIN;d++){
                        try{
                            if(b[i-d][j]==team){
                                if(dzero==1){
                                    dtoadd++;
                                }
                                if(dzero==0){
                                ver++;
                                }
                            }
                            else if(b[i-d][j]==0){
                                dzero++;
                            }
                            else{
                                d=TOWIN;
                            }
                            if(b[i-d][j]==otherteam){
                                dblock=1;
                            }
                        }
                        catch(Exception e){
                            d=TOWIN;
                            dblock=1;
                        }
                    }
                    
                    //up
                    int ublock=0;
                    int uzero=0;
                    int utoadd=0;
                    for(int u=1;u<TOWIN;u++){
                        try{
                            if(b[i+u][j]==team){
                                if(uzero==1){
                                    utoadd++;
                                }
                                if(uzero==0){
                                ver++;
                                }
                            }
                            else if(b[i+u][j]==0){
                                uzero++;
                            }
                            else{
                                u=TOWIN;
                            }
                            if(b[i+u][j]==otherteam){
                                ublock=1;
                            }
                        }
                        catch(Exception e){
                            u=TOWIN;
                            ublock=1;
                        }
                    }
                    
                    int vertoadd=0;
                    if(utoadd>dtoadd){
                        vertoadd=utoadd;
                    }
                    else{
                        vertoadd=dtoadd;
                    }
                    ver=ver+vertoadd;
                    
                    if(ver>=TOWIN){
                        //System.out.println("You win vertically at: (" + i + ", " + j + ")");
                        good=good+6000000;
                    }
                    else if(dblock==0 || ublock==0){
                        //int multiplier=0;
                        //multiplier=(ver-TOWIN);
                        double tmp = Math.pow(ver, teammulti);
                        int toadd = (int)tmp;
                        good = (toadd+good);
                    }
                    //vertical end
                    
                    //diagA
                    //bottomleft
                    int blblock=0;
                    int bltoadd=0;
                    int blzero=0;
                    for(int bl=1;bl<TOWIN;bl++){
                        try{
                            if(b[i-bl][j-bl]==team){
                                if(blzero==1){
                                    bltoadd++;
                                }
                                if(blzero==0){
                                diagA++;
                                }
                            }
                            else if(b[i-bl][j-bl]==0){
                                blzero++;
                            }
                            else{
                                bl=TOWIN;
                            }
                            if(b[i-bl][j-bl]==otherteam){
                                blblock=1;
                            }
                        }
                        catch(Exception e){
                            bl=TOWIN;
                            blblock=1;
                        }
                    }
                    
                    //upperright
                    int urblock=0;
                    int urtoadd=0;
                    int urzero=0;
                    for(int ur=1;ur<TOWIN;ur++){
                        try{
                            if(b[i+ur][j+ur]==team){
                                if(urzero==1){
                                    urtoadd++;
                                }
                                if(urzero==0){
                                diagA++;
                                }
                            }
                            if(b[i+ur][j+ur]==0){
                                urzero++;
                            }
                            else{
                                ur=TOWIN;
                            }
                            if(b[i+ur][j+ur]==otherteam){
                                urblock=1;
                            }
                        }
                        catch(Exception e){
                            ur=TOWIN;
                            urblock=1;
                        }
                    }
                    
                    int diagAtoadd=0;
                    if(urtoadd>bltoadd){
                        diagAtoadd=urtoadd;
                    }
                    else{
                        diagAtoadd=bltoadd;
                    }
                    diagA=diagA+diagAtoadd;
                    
                    if(diagA>=TOWIN){
                        //System.out.println("You win diagonally at: (" + i + ", " + j + ")");
                        good=good+6000000;
                    }
                    else if(blblock==0 || urblock==0){
                        //int multiplier=0;
                        //multiplier=(diagA-TOWIN);
                        double tmp = Math.pow(diagA, teammulti);
                        int toadd = (int)tmp;
                        good = (toadd+good);
                    }
                    //diagA end
                    
                    //diagB
                    //upperleft
                    int ulblock=0;
                    int ulzero=0;
                    int ultoadd=0;
                    for(int ul=1;ul<TOWIN;ul++){
                        try{
                            if(b[i+ul][j-ul]==team){
                                if(ulzero==1){
                                    ultoadd++;
                                }
                                if(ulzero==0){
                                diagB++;
                                }
                            }
                            else if(b[i+ul][j-ul]==0){
                                ulzero++;
                            }
                            else{
                                ul=TOWIN;
                            }
                            if(b[i+ul][j-ul]==otherteam){
                                ulblock=1;
                            }
                        }
                        catch(Exception e){
                            ul=TOWIN;
                            ulblock=1;
                        }
                    }
                    
                    //bottomright
                    int brblock=0;
                    int brzero=0;
                    int brtoadd=0;
                    for(int br=1;br<TOWIN;br++){
                        try{
                            if(b[i-br][j+br]==team){
                                if(brzero==1){
                                    brtoadd++;
                                }
                                if(brzero==0){
                                diagB++;
                                }
                            }
                            else if(b[i-br][j+br]==0){
                                brzero++;
                            }
                            else{
                                br=TOWIN;
                            }
                            if(b[i-br][j+br]==otherteam){
                                brblock=1;
                            }
                        }
                        catch(Exception e){
                            br=TOWIN;
                            brblock=1;
                        }
                    }
                    
                    int diagBtoadd=0;
                    if(ultoadd>brtoadd){
                        diagBtoadd=ultoadd;
                    }
                    else{
                        diagBtoadd=brtoadd;
                    }
                    diagB=diagB+diagBtoadd;
                    
                    if(diagB>=TOWIN){
                        //System.out.println("You win diagonally at: (" + i + ", " + j + ")");
                        good=good+6000000;
                    }
                    else if(ulblock==0 || brblock==0){
                        //int multiplier=0;
                        //multiplier=(diagB-TOWIN);
                        double tmp = Math.pow(diagB, teammulti);
                        int toadd = (int)tmp;
                        good = (toadd+good);
                    }
                    //diagB end
                }
                if(b[i][j]==otherteam){
                    int midbonus = Math.abs(j-halfway);
                    bad=(bad-midbonus);
                    int hor=1;
                    int ver=1;
                    int diagA=1;
                    int diagB=1;
                    //horizontal
                    //left  
                    int lblock = 0;
                    int lzero=0;
                    int ltoadd=0;
                    for(int l=1;l<TOWIN;l++){
                        try{
                            if(b[i][j-l]==otherteam){
                                if(lzero==1){
                                    ltoadd++;
                                }
                                if(lzero==0){
                                hor++;
                                }
                            }
                            else if(b[i][j-l]==0){
                                lzero++;                              
                            }
                            else{
                                l=TOWIN;
                            }
                            if(b[i][j-l]==team){
                                lblock=1;
                            }
                        }
                        catch(Exception e){
                            l=TOWIN;
                            lblock=1;
                        }
                    }
                    
                    //right
                    int rblock=0;
                    int rzero=0;
                    int rtoadd=0;
                    for(int r=1;r<TOWIN;r++){
                        try{
                            if(b[i][j+r]==otherteam){
                                if(rzero==1){
                                    rtoadd++;
                                }
                                if(rzero==0){
                                hor++;
                                }
                            }
                            else if(b[i][j+r]==0){
                                rzero++;
                            }
                            else{
                                r=TOWIN;
                            }
                            if(b[i][j+r]==team){
                                rblock=1;
                            }
                        }
                        catch(Exception e){
                            r=TOWIN;
                            rblock=1;
                        }
                    }
                    int hortoadd=0;
                    if(ltoadd>rtoadd){
                        hortoadd=ltoadd;
                    }
                    else{
                        hortoadd=rtoadd;
                    }
                    hor=hor+hortoadd;
                    
                    if(hor>=TOWIN){
                        //System.out.println("You win horizontally at: (" + i + ", " + j + ")");
                        bad=bad+6000000;
                    }
                    else if(lblock==0 || rblock==0){
                        //int multiplier=0;
                        //multiplier=(hor-TOWIN);
                        //System.out.println("hor: " + hor);
                        double tmp = Math.pow(hor, teammulti);            
                        int toadd = (int)tmp;
                        bad = (toadd+bad);
                    }
                    //horizontal end
                    
                    //vertical
                    //down
                    int dblock=0;
                    int dzero=0;
                    int dtoadd=0;
                    for(int d=1;d<TOWIN;d++){
                        try{
                            if(b[i-d][j]==otherteam){
                                if(dzero==1){
                                    dtoadd++;
                                }
                                if(dzero==0){
                                ver++;
                                }
                            }
                            else if(b[i-d][j]==0){
                                dzero++;
                            }
                            else{
                                d=TOWIN;
                            }
                            if(b[i-d][j]==team){
                                dblock=1;
                            }
                        }
                        catch(Exception e){
                            d=TOWIN;
                            dblock=1;
                        }
                    }
                    
                    //up
                    int ublock=0;
                    int uzero=0;
                    int utoadd=0;
                    for(int u=1;u<TOWIN;u++){
                        try{
                            if(b[i+u][j]==otherteam){
                                if(uzero==1){
                                    utoadd++;
                                }
                                if(uzero==0){
                                ver++;
                                }
                            }
                            else if(b[i+u][j]==0){
                                uzero++;
                            }
                            else{
                                u=TOWIN;
                            }
                            if(b[i+u][j]==team){
                                ublock=1;
                            }
                        }
                        catch(Exception e){
                            u=TOWIN;
                            ublock=1;
                        }
                    }
                    
                    int vertoadd=0;
                    if(utoadd>dtoadd){
                        vertoadd=utoadd;
                    }
                    else{
                        vertoadd=dtoadd;
                    }
                    ver=ver+vertoadd;
                    
                    if(ver>=TOWIN){
                        //System.out.println("You win vertically at: (" + i + ", " + j + ")");
                        bad=bad+6000000;
                    }
                    else if(dblock==0 || ublock==0){
                        //int multiplier=0;
                        //multiplier=(ver-TOWIN);
                        double tmp = Math.pow(ver, teammulti);
                        int toadd = (int)tmp;
                        bad = (toadd+bad);
                    }
                    //vertical end
                    
                    //diagA
                    //bottomleft
                    int blblock=0;
                    int bltoadd=0;
                    int blzero=0;
                    for(int bl=1;bl<TOWIN;bl++){
                        try{
                            if(b[i-bl][j-bl]==otherteam){
                                if(blzero==1){
                                    bltoadd++;
                                }
                                if(blzero==0){
                                diagA++;
                                }
                            }
                            else if(b[i-bl][j-bl]==0){
                                blzero++;
                            }
                            else{
                                bl=TOWIN;
                            }
                            if(b[i-bl][j-bl]==team){
                                blblock=1;
                            }
                        }
                        catch(Exception e){
                            bl=TOWIN;
                            blblock=1;
                        }
                    }
                    
                    //upperright
                    int urblock=0;
                    int urtoadd=0;
                    int urzero=0;
                    for(int ur=1;ur<TOWIN;ur++){
                        try{
                            if(b[i+ur][j+ur]==otherteam){
                                if(urzero==1){
                                    urtoadd++;
                                }
                                if(urzero==0){
                                diagA++;
                                }
                            }
                            if(b[i+ur][j+ur]==0){
                                urzero++;
                            }
                            else{
                                ur=TOWIN;
                            }
                            if(b[i+ur][j+ur]==team){
                                urblock=1;
                            }
                        }
                        catch(Exception e){
                            ur=TOWIN;
                            urblock=1;
                        }
                    }
                    
                    int diagAtoadd=0;
                    if(urtoadd>bltoadd){
                        diagAtoadd=urtoadd;
                    }
                    else{
                        diagAtoadd=bltoadd;
                    }
                    diagA=diagA+diagAtoadd;
                    
                    if(diagA>=TOWIN){
                        //System.out.println("You win diagonally at: (" + i + ", " + j + ")");
                        bad=bad+6000000;
                    }
                    else if(blblock==0 || urblock==0){
                        //int multiplier=0;
                        //multiplier=(diagA-TOWIN);
                        double tmp = Math.pow(diagA, teammulti);
                        int toadd = (int)tmp;
                        bad = (toadd+bad);
                    }
                    //diagA end
                    
                    //diagB
                    //upperleft
                    int ulblock=0;
                    int ulzero=0;
                    int ultoadd=0;
                    for(int ul=1;ul<TOWIN;ul++){
                        try{
                            if(b[i+ul][j-ul]==otherteam){
                                if(ulzero==1){
                                    ultoadd++;
                                }
                                if(ulzero==0){
                                diagB++;
                                }
                            }
                            else if(b[i+ul][j-ul]==0){
                                ulzero++;
                            }
                            else{
                                ul=TOWIN;
                            }
                            if(b[i+ul][j-ul]==team){
                  
                          ulblock=1;
                            }
                        }
                        catch(Exception e){
                            ul=TOWIN;
                            ulblock=1;
                        }
                    }
                    
                    //bottomright
                    int brblock=0;
                    int brzero=0;
                    int brtoadd=0;
                    for(int br=1;br<TOWIN;br++){
                        try{
                            if(b[i-br][j+br]==otherteam){
                                if(brzero==1){
                                    brtoadd++;
                                }
                                if(brzero==0){
                                diagB++;
                                }
                            }
                            else if(b[i-br][j+br]==0){
                                brzero++;
                            }
                            else{
                                br=TOWIN;
                            }
                            if(b[i-br][j+br]==team){
                                brblock=1;
                            }
                        }
                        catch(Exception e){
                            br=TOWIN;
                            brblock=1;
                        }
                    }
                    
                    int diagBtoadd=0;
                    if(ultoadd>brtoadd){
                        diagBtoadd=ultoadd;
                    }
                    else{
                        diagBtoadd=brtoadd;
                    }
                    diagB=diagB+diagBtoadd;
                    
                    if(diagB>=TOWIN){
                        //System.out.println("You win diagonally at: (" + i + ", " + j + ")");
                        bad=bad+6000000;
                    }
                    else if(ulblock==0 || brblock==0){
                        //int multiplier=0;
                        //multiplier=(diagB-TOWIN);
                        double tmp = Math.pow(diagB, teammulti);
                        int toadd = (int)tmp;
                        bad = (toadd+bad);
                    }
                    //diagB end
                }
            }
        }
        total=good-bad;
        
        //System.out.println("good: " + good + "         bad: " + bad + "       total: " + total + "      team: " + team + "       otherteam: " + otherteam);
        //printBoard(b);
        
        
        
        return total;
    }
    
    
    public static int minimax(GameState g, int minormax, int start,int testeam){
        start++;
        int toreturn=0;
        int actualteam=0;
        if(minormax==1){
            minormax=0;
        }
        else if(minormax==0){
            minormax=1;
        }
        if(g.getTurn()==3){
            actualteam=7;
        }
        else if(g.getTurn()==7){
            actualteam=3;
        }
        
        int t=testeam;;
        if(start%2==1){
            if(t==7){
                t=3;
            }
            else if(t==3){
                t=7;
            }
        }
        
        
        
        ArrayList<aMove> arr = new ArrayList<aMove>();
        //int[] arrr = new int[W];
        
        for(int i=0;i<g.getChildren().size();i++){          
            //System.out.println("\nThe score for this board: " + howGood(g.getChildren().get(i).getBoard(), g.getChildren().get(i).getTurn()) + "       for the team: " + g.getChildren().get(i).getTurn());
            aMove curr;
            int tmp = minimax(g.getChildren().get(i),minormax,start, testeam);
            curr = new aMove(tmp, g.getChildren().get(i).getPrev());
            arr.add(curr);      
        }
        
        
        if(g.getChildren().size()==0){
            //System.out.println("actual team: " + actualteam);
            int value = howGood(g.getBoard(),testeam);
            return value;
        }
        
        if(minormax==0){
            int max=-2147000000;
            int loc=0;
            //System.out.println("NEW THING~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~start:" + start);
            for(int i=0;i<arr.size();i++){
                //System.out.print("i: " + i + "        tracker: ");
                //printTracker(g.getTracker());
                //if(g.getTracker()[i]>-1){
                    
                if(arr.get(i).getValue()>max){
                    max=arr.get(i).getValue();
                    loc=arr.get(i).getMove();
                }
                //}
                
                //if(start==1){
                //System.out.print("  | " + arr.get(i).getValue() + " |    " );
                //}
            }
            if(start==1){               
                System.out.println("Minimax: " + max);
                return loc;
            }
            return max;
        }
        if(minormax==1){
            int min=2147000000;
            int loc=0;
            //System.out.println("NEW THING~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~start:" + start);
            for(int i=0;i<arr.size();i++){
                //if(g.getTracker()[i]>-1){
                if(arr.get(i).getValue()<min){
                    min=arr.get(i).getValue();
                    loc=arr.get(i).getMove();
                }
                //}
                
                //System.out.print("  | " + arr.get(i).getValue() + " |    " );
            }
            if(start==1){               
                System.out.println("Minimax: " + min);
                return loc;
            }
            return min;
        }
        
        
        return toreturn;
    }
    

    
    public static ArrayList<GameState> buildTree(int[][] b,int[] t,int team,int times){
        times--;
        ArrayList<GameState> children = new ArrayList<GameState>();
        //if(!isGameOver(team,b)){
        if(times>-1){
        for(int i=0;i<W;i++){
            
            GameState gs;
            int tmpteam=0;
            if(team==3){
                tmpteam=7;
            }
            else if(team==7){
                tmpteam=3;
            }
            
            int[] tmpt = new int[W];
            for(int j=0;j<t.length;j++){
                tmpt[j] = t[j];
            }
            
            //printTracker(tmpt);
            if(tmpt[i]>=0){
                int[][] tmpb = new int[H][W];
            for(int k=0;k<H;k++){
                for(int m=0;m<W;m++){
                    tmpb[k][m]=b[k][m];
                }
            }
            if((!isGameOver(tmpteam,tmpb))){
            tmpb = dropChecker(i,team,tmpb,tmpt,false);
            //printBoard(b);
            
            
            //printTracker(tmpt);
            //printBoard(tmpb);
            
            
            
            
            
            gs = new GameState(tmpb,buildTree(tmpb,tmpt,tmpteam,times),tmpt,W,H,team,i);
            //printBoard(gs.getBoard());
            children.add(gs);
            //printBoard(children.get(i).getBoard());
            //System.out.println("but did u get here");
            }
        }
            //System.out.println("got here,     i: "  + i);
        }
        //}
    }
        
    
        
        //System.out.println("size: " + children.size());
        return children;
    }
    
    public static void printRoot(GameState g){
        
        for(int i=0;i<g.getChildren().size();i++){
            System.out.println("     ");
            System.out.println("\nThe score for this board: " + howGood(g.getChildren().get(i).getBoard(), g.getChildren().get(i).getTurn()) + "       for the team: " + g.getChildren().get(i).getTurn());
            printBoard(g.getChildren().get(i).getBoard());
            printRoot(g.getChildren().get(i));
        }
    }
    
    
    public static void printBoard(int[][] a){
        for(int i=0;i<H;i++){
            System.out.println();
            System.out.print("| ");
            for(int j=0;j<W;j++){
                if(a[i][j]==0){
                    System.out.print(" " + " | ");
                }
                else if(a[i][j]==3){
                    System.out.print("O" + " | ");
                }
                else if(a[i][j]==7){
                    System.out.print("X" + " | ");
                }
            }
        }
        System.out.println();
        System.out.print("-");
        for(int i=0;i<W;i++){
            System.out.print("----");
        }
        System.out.println();
        System.out.print("| ");
        for(int i=0;i<W;i++){
            if(i<9){
            System.out.print(i + " | ");
            }
            else if(i<99){
                System.out.print(i + " |");
                
            }
            else{
                if(i==99){
                    System.out.print(" ");
                }
                System.out.print(i + "|");
            }
        }
        
    }
    
    public static void printTracker(int[] a){
        System.out.println();
        for(int i=0;i<W;i++){
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }
    
    public static int[][] dropChecker(int locy, int team, int[][] b, int[]track, boolean print){
        if(print){
            diffcount++;
            if(diffcount==6){
                //difficulty++;
                diffcount=0;
            }
            System.out.println("diff: " + difficulty);
        }
        if(print){
        System.out.println();
        System.out.print("Checker dropped at row " + locy);
        }
        int locx = track[locy];
        track[locy]=locx-1;
        b[locx][locy]=team; 
        if(print){
        printBoard(b);
        System.out.println();
        }
        return b;
    }
    
    public static void gameOver(int player){
        int over = 0;
        int tie = 0;
        for(int i=0;i<H;i++){
            for(int j=0;j<W;j++){
                if(board[i][j]==player){
                    int hor=1;
                    int ver=1;
                    int diagA=1;
                    int diagB=1;
                    //horizontal
                    //left
                    for(int l=1;l<TOWIN;l++){
                        try{
                            if(board[i][j-l]==player){
                                hor++;
                            }
                            else{
                                l=TOWIN;
                            }
                        }
                        catch(Exception e){
                            l=TOWIN;
                        }
                    }
                    
                    //right
                    for(int r=1;r<TOWIN;r++){
                        try{
                            if(board[i][j+r]==player){
                                hor++;
                            }
                            else{
                                r=TOWIN;
                            }
                        }
                        catch(Exception e){
                            r=TOWIN;
                        }
                    }
                    if(hor>=TOWIN){
                        //System.out.println("You win horizontally at: (" + i + ", " + j + ")");
                        over++;
                    }
                    //horizontal end
                    
                    //vertical
                    //down
                    for(int d=1;d<TOWIN;d++){
                        try{
                            if(board[i-d][j]==player){
                                ver++;
                            }
                            else{
                                d=TOWIN;
                            }
                        }
                        catch(Exception e){
                            d=TOWIN;
                        }
                    }
                    
                    //up
                    for(int u=1;u<TOWIN;u++){
                        try{
                            if(board[i+u][j]==player){
                                ver++;
                            }
                            else{
                                u=TOWIN;
                            }
                        }
                        catch(Exception e){
                            u=TOWIN;
                        }
                    }
                    if(ver>=TOWIN){
                        //System.out.println("You win vertically at: (" + i + ", " + j + ")");
                        over++;
                    }
                    //vertical end
                    
                    //diagA
                    //bottomleft
                    for(int bl=1;bl<TOWIN;bl++){
                        try{
                            if(board[i-bl][j-bl]==player){
                                diagA++;
                            }
                            else{
                                bl=TOWIN;
                            }
                        }
                        catch(Exception e){
                            bl=TOWIN;
                        }
                    }
                    
                    //upperright
                    for(int ur=1;ur<TOWIN;ur++){
                        try{
                            if(board[i+ur][j+ur]==player){
                                diagA++;
                            }
                            else{
                                ur=TOWIN;
                            }
                        }
                        catch(Exception e){
                            ur=TOWIN;
                        }
                    }
                    if(diagA>=TOWIN){
                        //System.out.println("You win diagonally at: (" + i + ", " + j + ")");
                        over++;
                    }
                    //diagA end
                    
                    //diagB
                    //upperleft
                    for(int ul=1;ul<TOWIN;ul++){
                        try{
                            if(board[i+ul][j-ul]==player){
                                diagB++;
                            }
                            else{
                                ul=TOWIN;
                            }
                        }
                        catch(Exception e){
                            ul=TOWIN;
                        }
                    }
                    
                    //bottomright
                    for(int br=1;br<TOWIN;br++){
                        try{
                            if(board[i-br][j+br]==player){
                                diagB++;
                            }
                            else{
                                br=TOWIN;
                            }
                        }
                        catch(Exception e){
                            br=TOWIN;
                        }
                    }
                    if(diagB>=TOWIN){
                        //System.out.println("You win diagonally at: (" + i + ", " + j + ")");
                        over++;
                    }
                    //diagB end
                }
            }
        }
        for(int i=0;i<W;i++){
            if(tracker[i]==-1){
                tie++;
            }
        }
        if(tie==W){
            System.out.println("There are no more possible moves to take. We have outselves a tie game.");
            System.exit(0);
        }
        else if(over!=0){
            if(player==3){
                System.out.println("First Player Wins!");
            }
            if(player==7){
                System.out.println("Second Player Wins!");
            }
            System.exit(0);
        }
        //System.out.println("Tie: " + tie);
    }
    
    public static boolean isGameOver(int player,int[][] b){
        boolean over = false;
        int tie = 0;
        for(int i=0;i<H;i++){
            for(int j=0;j<W;j++){
                if(b[i][j]==player){
                    int hor=1;
                    int ver=1;
                    int diagA=1;
                    int diagB=1;
                    //horizontal
                    //left
                    for(int l=1;l<TOWIN;l++){
                        try{
                            if(b[i][j-l]==player){
                                hor++;
                            }
                            else{
                                l=TOWIN;
                            }
                        }
                        catch(Exception e){
                            l=TOWIN;
                        }
                    }
                    
                    //right
                    for(int r=1;r<TOWIN;r++){
                        try{
                            if(b[i][j+r]==player){
                                hor++;
                            }
                            else{
                                r=TOWIN;
                            }
                        }
                        catch(Exception e){
                            r=TOWIN;
                        }
                    }
                    if(hor>=TOWIN){
                        //System.out.println("You win horizontally at: (" + i + ", " + j + ")");
                        over=true;
                    }
                    //horizontal end
                    
                    //vertical
                    //down
                    for(int d=1;d<TOWIN;d++){
                        try{
                            if(b[i-d][j]==player){
                                ver++;
                            }
                            else{
                                d=TOWIN;
                            }
                        }
                        catch(Exception e){
                            d=TOWIN;
                        }
                    }
                    
                    //up
                    for(int u=1;u<TOWIN;u++){
                        try{
                            if(b[i+u][j]==player){
                                ver++;
                            }
                            else{
                                u=TOWIN;
                            }
                        }
                        catch(Exception e){
                            u=TOWIN;
                        }
                    }
                    if(ver>=TOWIN){
                        //System.out.println("You win vertically at: (" + i + ", " + j + ")");
                        over=true;
                    }
                    //vertical end
                    
                    //diagA
                    //bottomleft
                    for(int bl=1;bl<TOWIN;bl++){
                        try{
                            if(b[i-bl][j-bl]==player){
                                diagA++;
                            }
                            else{
                                bl=TOWIN;
                            }
                        }
                        catch(Exception e){
                            bl=TOWIN;
                        }
                    }
                    
                    //upperright
                    for(int ur=1;ur<TOWIN;ur++){
                        try{
                            if(b[i+ur][j+ur]==player){
                                diagA++;
                            }
                            else{
                                ur=TOWIN;
                            }
                        }
                        catch(Exception e){
                            ur=TOWIN;
                        }
                    }
                    if(diagA>=TOWIN){
                        //System.out.println("You win diagonally at: (" + i + ", " + j + ")");
                        over=true;
                    }
                    //diagA end
                    
                    //diagB
                    //upperleft
                    for(int ul=1;ul<TOWIN;ul++){
                        try{
                            if(b[i+ul][j-ul]==player){
                                diagB++;
                            }
                            else{
                                ul=TOWIN;
                            }
                        }
                        catch(Exception e){
                            ul=TOWIN;
                        }
                    }
                    
                    //bottomright
                    for(int br=1;br<TOWIN;br++){
                        try{
                            if(b[i-br][j+br]==player){
                                diagB++;
                            }
                            else{
                                br=TOWIN;
                            }
                        }
                        catch(Exception e){
                            br=TOWIN;
                        }
                    }
                    if(diagB>=TOWIN){
                        //System.out.println("You win diagonally at: (" + i + ", " + j + ")");
                        over=true;
                    }
                    //diagB end
                }
            }
        }
        for(int i=0;i<W;i++){
            if(tracker[i]==-1){
                tie++;
            }
        }
        if(tie==W){
            over=true;
        }
        
        //System.out.println("Tie: " + tie);
        return over;
    }
}
