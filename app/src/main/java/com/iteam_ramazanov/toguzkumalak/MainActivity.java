package com.iteam_ramazanov.toguzkumalak;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyErrorHandler {

    private Game game = null;

    private TextView[] smallPits = new TextView[18];
    private TextView[] bigPits = new TextView[2];

    private void updatePosition() {
        for (int i = 0; i < 18; i++) {
            if (game.position.small_pits[i] > 0) {
                smallPits[i].setText(Integer.toString(game.position.small_pits[i]));
            } else if (game.position.small_pits[i] == 0) {
                smallPits[i].setText("");
            } else {
                smallPits[i].setText("x");
            }
            smallPits[i].invalidate();
        }
        for (int i = 0; i < 2; i++) {
            bigPits[i].setText(Integer.toString(game.position.big_pits[i]));
            bigPits[i].invalidate();
        }
    }

    private void startNewGame() {
        try {
            game = new Game(this);
            //updatePosition();
        } catch (Position.MyException e) {
            e.printStackTrace();
        }
    }

    private List<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {

            View child = viewGroup.getChildAt(i);

            //Do not add any parents, just add child elements
            result.addAll(getAllChildren(child));
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.game_activity);
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        List<View> allChildren = getAllChildren(viewGroup);
        for (int i = 0; i < allChildren.size(); i++) {
            View child = allChildren.get(i);
            int tag;
            try {
                tag = Integer.parseInt((String) child.getTag());
            } catch (NumberFormatException e) {
                continue;
            }
            if ((tag >= 0) && (tag <= 17)) {
                smallPits[tag] = (TextView) child;
            }
            if ((tag >= 20) && (tag <= 21)) {
                bigPits[tag-20] = (TextView) child;
            }
        }
        startNewGame();
    }

    public void smallPitOnClick(View v) {
        int index = Integer.parseInt((String) v.getTag());
        game.move(index);
        updatePosition();
    }
}
