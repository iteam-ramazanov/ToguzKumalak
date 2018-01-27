package com.iteam_ramazanov.toguzkumalak;

import android.app.Activity;

/**
 * Created by iteam on 1/26/18.
 */

public class Game {

    private MyErrorHandler errorHandler;
    public Position position;

    public Game(MyErrorHandler errorHandler) throws Position.MyException {
        this.errorHandler = errorHandler;
        this.position = new Position("9.9.9.9.9.9.9.9.9/0/0/9.9.9.9.9.9.9.9.9/w");
    }

    public void move(int x) {
        if (this.position.can_move_from_pit(x)) {
            this.position = this.position.after_move(x);
        }
    }
}
