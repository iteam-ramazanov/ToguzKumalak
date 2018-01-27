package com.iteam_ramazanov.toguzkumalak;

import java.io.IOException;

/**
 * Created by iteam on 1/26/18.
 */

public class Position {

    public class MyException extends IOException {
    }

    public final int WHITES = 0;
    public final int BLACKS = 1;

    public int[] small_pits = new int[18];
    public int[] big_pits = new int[2];

    public int

    public int whose_turn;

    public Position(String notation) throws MyException {
        if (notation == "") throw new MyException();
        String[] string_array1 = notation.split("/");
        if (string_array1.length != 5) throw new MyException();
        String[] string_array2 = string_array1[0].split("\\.");
        if (string_array2.length != 9) throw new MyException();
        for (int i = 0; i < 9; i++) {
            this.small_pits[17 - i] = Integer.parseInt(string_array2[i]);
        }
        this.big_pits[0] = Integer.parseInt(string_array1[1]);
        this.big_pits[1] = Integer.parseInt(string_array1[2]);
        string_array2 = string_array1[3].split("\\.", 0);
        if (string_array2.length != 9) throw new MyException();
        for (int i = 0; i < 9; i++) {
            this.small_pits[i] = Integer.parseInt(string_array2[i]);
        }
        if (string_array1[4].length() != 1) throw new MyException();
        switch (string_array1[4].charAt(0)) {
            case 'w':
                this.whose_turn = WHITES;
                break;
            case 'b':
                this.whose_turn = BLACKS;
                break;
            default:
                throw new MyException();
        }
    }

    public String getNotation() {
        String result = "";
        for (int i = 17; i > 9; i--) {
            result += small_pits[i] + ".";
        }
        result += small_pits[9] + "/";
        result += big_pits[0] + "/";
        result += big_pits[1] + "/";
        for (int i = 0; i < 8; i++) {
            result += small_pits[i] + ".";
        }
        result += small_pits[8] + "/";
        if (whose_turn == WHITES) {
            result += "w";
        } else {
            result += "b";
        }
        return result;
    }

    public boolean can_move_from_pit(int x) {
        if ((x < 0) || (x >= 18)) return false;
        if (x / 9 != whose_turn) return false;
        if (small_pits[x] <= 0) return false;
        return true;
    }

    public Position after_move(int x) {
        // TODO реализовать атсырау
        Position next_position = null;
        try {
            String notation = getNotation();
            next_position = new Position(notation);
            assert (next_position.small_pits[x] > 0);
            int seedsOnTheHand = next_position.small_pits[x] - 1;
            if (next_position.small_pits[x] == 1) seedsOnTheHand = 1;
            next_position.small_pits[x] -= seedsOnTheHand;
            while (seedsOnTheHand > 0) {
                x = (x + 1) % 18;
                if (next_position.small_pits[x] < 0) next_position.big_pits[1 - x / 9]++;
                else next_position.small_pits[x]++;
                seedsOnTheHand--;
            }
            if (x / 9 != next_position.whose_turn) {
                if (next_position.small_pits[x] % 2 == 0) {
                    next_position.big_pits[1 - x / 9] += next_position.small_pits[x];
                    next_position.small_pits[x] = 0;
                } else if (next_position.small_pits[x] == 3) {
                    int sacred_place = -1;
                    for (int i = 0; i < 9; i++) {
                        if (next_position.small_pits[9 - next_position.whose_turn * 9 + i] < 0) {
                            sacred_place = i;
                        }
                    }
                    if (((x % 9) != 8) && (sacred_place < 0)) {
                        sacred_place = -1;
                        for (int i = 0; i < 9; i++) {
                            if (next_position.small_pits[next_position.whose_turn * 9 + i] < 0) {
                                sacred_place = i;
                            }
                        }
                        if ((x % 9) != sacred_place) {
                            next_position.big_pits[1 - x / 9] += next_position.small_pits[x];
                            next_position.small_pits[x] = -1;
                        }
                    }
                }
            }
            next_position.whose_turn = 1 - next_position.whose_turn;

        } catch (MyException e) {
            e.printStackTrace();
        }
        return next_position;
    }
}
