package com.dm66.tableflip.logic;

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

    public void init(GameType type)
    {
        switch (type)
        {
            case BACKGAMMON_NORMAL -> {
                // TODO: set initial placement for normal game
            }
            case BACKGAMMON_GUL_BARA -> {
                // TODO: set initial placement for gul bara game
            }
            case BACKGAMMON_TAPA -> {
                // TODO: set initial placement for tapa game
            }
        }
    }
}
