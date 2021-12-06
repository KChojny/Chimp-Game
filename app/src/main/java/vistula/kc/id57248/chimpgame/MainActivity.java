package vistula.kc.id57248.chimpgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    ConstraintSet set;
    ConstraintLayout.LayoutParams params;

    int left, top;

    Button[][] buttons;
    int[][] id;
    int side, total;
    int[] numbers;
    int[] playerNum;
    int[] permutation;
    int current;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.constraintLayout);
        left = 220;
        top = 330;
        side = 3;
        total = side * side;
        numbers = new int[total];
        for (int k=0;k<total;k++){
            numbers[k] = k+1;
        }
        permutation = numbers;
        playerNum = new int[total];
        for (int k=0;k<total;k++){
            playerNum[k] = k-1;
        }
        buttons = new Button[side][side];
        id = new int[side][side];
        startButtons();
    }

    public void startButtons(){
        for(int i = 0; i<side;i++){
            for(int j = 0; j<side;j++){
                buttons[i][j] = new Button(this);
                buttons[i][j].setId(View.generateViewId());
                id[i][j] = buttons[i][j].getId();
                params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                buttons[i][j].setLayoutParams(params);
                constraintLayout.addView(buttons[i][j]);
                set = new ConstraintSet();
                set.clone(constraintLayout);
                if(j==0){
                    set.connect(buttons[i][j].getId(), ConstraintSet.LEFT,constraintLayout.getId(),ConstraintSet.LEFT,left);
                }else{
                    set.connect(buttons[i][j].getId(), ConstraintSet.LEFT,buttons[i][j-1].getId(),ConstraintSet.RIGHT,0);
                }
                if(i==0){
                    set.connect(buttons[i][j].getId(), ConstraintSet.TOP,constraintLayout.getId(),ConstraintSet.TOP,top);
                }else{
                    set.connect(buttons[i][j].getId(), ConstraintSet.TOP,buttons[i-1][j].getId(),ConstraintSet.BOTTOM,0);
                }

                set.applyTo(constraintLayout);
                buttons[i][j].setOnClickListener(listener);
            }
        }
        numbersButton();
    }

    final View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            if(current < total){
                b.setText(Integer.toString(++current));
            }
        }
    };

    private void numbersButton(){
        for (int i = 0; i < side; i++){
            for (int j = 0; j < side; j++){
                buttons[i][j].setText("*");
            }
        }
        current = 0;
    }

    public void hideNumbersButtons(View view){
        for (int i = 0; i< side; i++){
            for (int j = 0; j < side; j++){
                buttons[i][j].setText("*");
            }
        }
    }

    public void makePermutation(View view){
        permutation = ArrayPermutation.shuffleFisherYears(numbers);
        numbersButton();
    }

    public void resultGame(View view){
        Button button;
        int result = 0;
        for(int i = 0; i < side; i++){
            for(int j = 0; j < side; j++){
                button = findViewById(id[i][j]);
                String num = button.getText().toString();
                playerNum[i*side+j] = Integer.parseInt(num);
                result += (playerNum[i*side+j] == permutation[i*side+j])?1:-1;

            }
        }
        for(int i = 0; i < side; i++){
            for(int j = 0; j < side; j++){
                button = (Button) findViewById(id[i][j]);
                String res = Integer.toString(permutation[i*side+j])+" "+ Integer.toString(playerNum[i*side+j]);
                button.setText(res);
            }
        }
        TextView text = findViewById(R.id.result);
        text.setText(Integer.toString(result));
    }
}