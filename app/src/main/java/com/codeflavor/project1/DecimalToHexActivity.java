package com.codeflavor.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class DecimalToHexActivity extends AppCompatActivity {
    TextView dthQuestion, dthAnswer, dthRoundScore;
    Button dthButton;
    TextView dthSigned, dthUnsigned;
    String qValue;
    int totalRoundPossible = 0;
    boolean asking = true;
    int roundScore = 0;
    int oriSigned;
    int oriUnsigned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decimal_to_hex);
        dthAnswer = findViewById(R.id.dthAnswer);
        dthQuestion = findViewById(R.id.dthQuestion);
        dthButton = findViewById(R.id.dhcButton);
        dthSigned = findViewById(R.id.dthSigned);
        dthUnsigned = findViewById(R.id.dthUnsigned);
        dthRoundScore = findViewById(R.id.dthRoundScore);
        generateQuestion();
    }

    public void generateQuestion() {
        Random random = new Random();
        int newValue = random.nextInt((int) Math.pow(2, MainActivity.bitMode - 1));
//        int sign = random.nextInt() % 2 == 1 ? 1 : -1;
        oriSigned = newValue;
        if (oriSigned > 0) {
            oriUnsigned = oriSigned;
        } else {
            oriUnsigned = oriSigned + (int) Math.pow(2, MainActivity.bitMode);
        }
        qValue = Integer.toHexString(oriSigned);
        String str = "What are the signed and unsigned values for the " + MainActivity.bitMode + "-bit value 0x" + qValue + "?";
        dthQuestion.setText(str);
    }

    public void displayAnswer() {
        String str = "Unsigned: " + oriSigned + "\nSigned: " + oriUnsigned;
        dthAnswer.setText(str);
        dthAnswer.setVisibility(View.VISIBLE);
    }

    public void updateScore() {
        dthRoundScore.setText("Score: " + roundScore + "/" + totalRoundPossible);
    }

    public void dthCheckAnswer(View view) {
        int ansSigned = 0;
        int ansUnsigned = 0;
        try {
            ansSigned = Integer.parseInt(dthSigned.getText().toString());
            ansUnsigned = Integer.parseInt(dthUnsigned.getText().toString());
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_LONG).show();
            return;
        }
        if (asking) {
            totalRoundPossible += 2;

            if (oriSigned == ansSigned && oriUnsigned == ansUnsigned) {
                roundScore += 2;
                dthAnswer.setText("Correct!!");
                dthAnswer.setVisibility(View.VISIBLE);
                updateScore();
            } else {
                displayAnswer();
                updateScore();
            }
            asking = false;
            dthButton.setText("Click for a new Question");
        } else {
            generateQuestion();
            dthButton.setText("Check My Answer");
            asking = true;
            dthAnswer.setVisibility(View.INVISIBLE);
            dthUnsigned.setText("");
            dthSigned.setText("");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.totalPossible += totalRoundPossible;
        MainActivity.score += roundScore;
    }
}
