package xyz.myfur.word;

/**
 * Created by eugen on 31.08.2016.
 */
public class Point {
    private final int x;
    private final int y;
    private boolean isStart = false;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, boolean isStart) {
        this.x = x;
        this.y = y;
        this.isStart = isStart;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isStart() {
        return isStart;
    }
}
