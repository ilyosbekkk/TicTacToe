package ilyosbek.com.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "message";
    ImageView[] image_o;
    TextView textView;
    boolean transition = true;
    String text = null;
    int progress;
    int[] fullBoxes = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image_o = new ImageView[]{
                findViewById(R.id.imageView1),
                findViewById(R.id.imageView2),
                findViewById(R.id.imageView3),
                findViewById(R.id.imageView4),
                findViewById(R.id.imageView5),
                findViewById(R.id.imageView6),
                findViewById(R.id.imageView7),
                findViewById(R.id.imageView8),
                findViewById(R.id.imageView9)};
        textView = findViewById(R.id.winner);
        ImageView i = findViewById(R.id.test);

    }


    public void click(View view) {
        String t = (String) view.getTag();
        int tag = Integer.parseInt(t);
        boolean isDraw = true;
        if (fullBoxes[tag] == 0) {
            if (progress == 2) {
                Log.d(TAG, "click: " + tag);
                image_o[tag].setImageResource(R.drawable.red_x);
                progress = 1;
                fullBoxes[tag] = 1;
            } else if (progress == 1) {
                Log.d(TAG, "click: " + tag);
                image_o[tag].setImageResource(R.drawable.yellow_o);
                progress = 2;
                fullBoxes[tag] = 2;
            }
        }
        for (int i = 0; i < 8; i++) {


            if (fullBoxes[winningPositions[i][0]] == fullBoxes[winningPositions[i][1]] && fullBoxes[winningPositions[i][1]] == fullBoxes[winningPositions[i][2]] && fullBoxes[winningPositions[i][0]] != 0) {
                if (fullBoxes[winningPositions[i][0]] == 1)
                    text = "X";
                else if (fullBoxes[winningPositions[i][0]] == 2) text = "O";
                setFullBoxes();
                isDraw = false;
                break;

            }
        }
        if (checkForFull() && isDraw) {
            text = "DRAW!";
            setFullBoxes();

        }
    }


    @SuppressLint("SetTextI18n")
    public void setFullBoxes() {
        for (int i = 0; i < 9; i++) {
            fullBoxes[i] = 3;
        }
        if (text.equalsIgnoreCase("DRAW!"))
            textView.setText("DRAW!");
        else
            textView.setText(text + " has won!");
        textView.setVisibility(View.VISIBLE);
        if (transition) {
            textView.animate().translationYBy(720).setDuration(100);
            transition = false;
        }


    }

    public void reset(View view) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim);
        view.startAnimation(animation);
        for (int i = 0; i < 9; i++) {
            image_o[i].setImageResource(0);
            fullBoxes[i] = 0;
            textView.setText(null);
            textView.setVisibility(View.INVISIBLE);
            if (!transition) {
                textView.animate().translationYBy(-720).setDuration(100);
                transition = true;
            }
        }
    }

    public boolean checkForFull() {
        boolean isFull = true;

        for (int i = 0; i < 9; i++) {
            if (fullBoxes[i] == 0) {
                isFull = false;

                break;
            }
        }
        return isFull;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                progress = 2;
                for (int i = 0; i < 9; i++) {
                    fullBoxes[i] = 0;
                    image_o[i].setImageResource(0);
                }
                break;
            case R.id.item2:
                progress = 1;
                for (int i = 0; i < 9; i++) {
                    fullBoxes[i] = 0;
                    image_o[i].setImageResource(0);
                }
                break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", textView.getText().toString());


    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        textView.setText(savedInstanceState.getString("text"));
        textView.setVisibility(View.VISIBLE);

        super.onRestoreInstanceState(savedInstanceState);


    }
}





























