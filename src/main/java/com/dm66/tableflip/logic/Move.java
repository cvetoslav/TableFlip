package com.dm66.tableflip.logic;

public class Move
{
    // Backgammon move class

    public int initialPosition;
    public int finalPosition;

    public Move(int initialPosition, int finalPosition)
    {
        this.initialPosition = initialPosition;
        this.finalPosition = finalPosition;
    }
}
