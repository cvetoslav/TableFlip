package com.dm66.tableflip.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameState
{
    // Backgammon game state class

    // Upper and lower row of stacks (from white-player POV)
    // false -> white checker, true -> black checker
    public List< Stack<Boolean> > upperRow;
    public List< Stack<Boolean> > lowerRow;
    public int knockedWhiteCount = 0;
    public int knockedBlackCount = 0;
    public int outWhiteCount = 0;
    public int outBlackCount = 0;

    public static GameState init(GameType type)
    {
        GameState gs = new GameState();
        switch (type)
        {
            case BACKGAMMON_NORMAL -> {
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
                gs.upperRow = new ArrayList<>();
                Stack<Boolean> st;
                for(int i=0; i<11; i++)
                {
                    st = new Stack<>();
                    gs.upperRow.add(st);
                }
                st = new Stack<>();
                st.push(true); st.push(true);
                gs.upperRow.add(st);

                gs.lowerRow = new ArrayList<>();
                st = new Stack<>();
                st.push(false); st.push(false);
                gs.lowerRow.add(st);
                for(int i=0; i<11; i++)
                {
                    st = new Stack<>();
                    gs.lowerRow.add(st);
                }
                gs.outBlackCount = gs.outWhiteCount = 13;
            }
            case BACKGAMMON_TAPA -> {
                gs.upperRow = new ArrayList<>();
                Stack<Boolean> st;
                for(int i=0; i<11; i++)
                {
                    st = new Stack<>();
                    gs.upperRow.add(st);
                }
                st = new Stack<>();
                st.push(true); st.push(true);
                gs.upperRow.add(st);

                gs.lowerRow = new ArrayList<>();
                for(int i=0; i<11; i++)
                {
                    st = new Stack<>();
                    gs.lowerRow.add(st);
                }
                st = new Stack<>();
                st.push(false); st.push(false);
                gs.lowerRow.add(st);
                gs.outBlackCount = gs.outWhiteCount = 13;
            }
        }
        return gs;
    }
}
