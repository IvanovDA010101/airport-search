package org.example;

public class Pair {

    private int lengthBeforeRow;
    private int lengthOfRow;

    public Pair(int lengthBefore, int lengthOf) {
        this.lengthBeforeRow = lengthBefore;
        this.lengthOfRow = lengthOf;
    }

    public int getLengthBeforeRow() {
        return lengthBeforeRow;
    }

    public int getLengthOfRow() {
        return lengthOfRow;
    }
}
