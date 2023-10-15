package com.dm66.tableflip.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameState
{
    // Backgammon game state class

    // Upper and lower row of stacks (from white-player POV)
    // true -> white checker, false -> black checker
    public List< Stack<Boolean> > upperRow;
    public List< Stack<Boolean> > lowerRow;
    public int knockedWhiteCount;
    public int knockedBlackCount;

    public static GameState init(GameType type)
    {
        GameState gs = new GameState();
        switch (type)
        {
            case BACKGAMMON_NORMAL -> {
                // TODO: set initial placement for normal game
                gs.upperRow = new ArrayList<>();

                Stack<Boolean> st = new Stack<>();
                st.push(false);st.push(false);st.push(false);st.push(false);st.push(false);
                gs.upperRow.add(st);

                st = new Stack<>();
                gs.upperRow.add(st);

                st = new Stack<>();
                gs.upperRow.add(st);

                st = new Stack<>();
                gs.upperRow.add(st);

                st = new Stack<>();
                st.push(true);st.push(true);st.push(true);
                gs.upperRow.add(st);

                st = new Stack<>();
                gs.upperRow.add(st);

                st = new Stack<>();
                st.push(true);st.push(true);st.push(true);st.push(true);st.push(true);
                gs.upperRow.add(st);

                st = new Stack<>();
                gs.upperRow.add(st);

                st = new Stack<>();
                gs.upperRow.add(st);

                st = new Stack<>();
                gs.upperRow.add(st);

                st = new Stack<>();
                gs.upperRow.add(st);

                st = new Stack<>();
                st.push(false);st.push(false);
                gs.upperRow.add(st);

                // lower row
                gs.lowerRow = new ArrayList<>();

                st = new Stack<>();
                st.push(true);st.push(true);st.push(true);st.push(true);st.push(true);
                gs.lowerRow.add(st);

                st = new Stack<>();
                gs.lowerRow.add(st);

                st = new Stack<>();
                gs.lowerRow.add(st);

                st = new Stack<>();
                gs.lowerRow.add(st);

                st = new Stack<>();
                st.push(false);st.push(false);st.push(false);
                gs.lowerRow.add(st);

                st = new Stack<>();
                gs.lowerRow.add(st);

                st = new Stack<>();
                st.push(false);st.push(false);st.push(false);st.push(false);st.push(false);
                gs.lowerRow.add(st);

                st = new Stack<>();
                gs.lowerRow.add(st);

                st = new Stack<>();
                gs.lowerRow.add(st);

                st = new Stack<>();
                gs.lowerRow.add(st);

                st = new Stack<>();
                gs.lowerRow.add(st);

                st = new Stack<>();
                st.push(true);st.push(true);
                gs.lowerRow.add(st);
            }
            case BACKGAMMON_GUL_BARA -> {
                // TODO: set initial placement for gul bara game
            }
            case BACKGAMMON_TAPA -> {
                // TODO: set initial placement for tapa game
            }
        }
        return gs;
    }
}
