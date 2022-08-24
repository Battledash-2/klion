package com.kcreates.mods.klion.util;

public class Timer {
    private boolean over;
    private boolean autoReset;
    private int tick;
    private int length;

    public void setLength(int length) {
        this.length = length;
    }

    public Timer(int length, boolean autoReset) {
        this.length = length;
        this.autoReset = autoReset;
        this.over = false;
        this.tick = 0;
    }

    public void reset() {
        tick = 0;
        over = false;
    }
    public boolean isOver() {
        return over;
    }
    public boolean tick() {
        tick++;
        boolean res = over;
        if (tick > length) {
            over = true;
            res = true;
            if (autoReset) {
                this.reset();
            }
        }
        return res;
    }

    public static int getTicks(float seconds) {
        return (int)(seconds / 20);
    }
}
