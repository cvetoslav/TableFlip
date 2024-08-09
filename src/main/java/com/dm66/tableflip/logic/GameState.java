package com.dm66.tableflip.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameState
{
    // Backgammon game state class
    // TODO: rename class and/or create interface common for different game types

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
                gs.knockedWhiteCount = gs.knockedBlackCount = 13;
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
                gs.knockedWhiteCount = gs.knockedBlackCount = 13;
            }
        }
        return gs;
    }


    // TODO: (de)serialization - implement GameState-specific data serialization
    public byte[] serialize()
    {
        return new byte[1];
    }

    public static GameState reconstruct(byte[] serial)
    {
        return init(GameType.BACKGAMMON_NORMAL);
    }
}
