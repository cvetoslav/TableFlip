package com.dm66.tableflip.logic;

import java.util.ArrayList;
import java.util.List;

public interface GameEngine
{
    static boolean isValidMove(GameState gs, Move move)
    {
        if(move.initialPosition < 12) return gs.upperRow.get(move.initialPosition).size() > 0;
        return gs.lowerRow.get(move.initialPosition - 12).size() > 0;
    }

    static List<Move> getValidMoves(GameState gs)
    {
        return new ArrayList<>();
    }

    static GameState makeMove(GameState gs, Move move)
    {
        Boolean x = true;
        if(move.initialPosition >= 0 && move.initialPosition < 12)
        {
            x = gs.upperRow.get(move.initialPosition).pop();
        }
        else if(move.initialPosition >= 12)
        {
            x = gs.lowerRow.get(move.initialPosition - 12).pop();
        }

        if(move.finalPosition >= 0 && move.finalPosition < 12)
        {
            gs.upperRow.get(move.finalPosition).push(x);
        }
        else if(move.finalPosition >= 12)
        {
            gs.lowerRow.get(move.finalPosition - 12).push(x);
        }
        return gs;
    }

}
