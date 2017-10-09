package com.codeflavor.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class HexToDecimal extends AppCompatActivity {
    TextView hQuestion, hAnswer, hRoundScore;
    Button hCheck;
    TextView hSigned, hUnsigned;
    int totalRoundPossible = 0;
    boolean hAsking = true;
    int roundScore = 0;

    int oriSigned;
    int oriUnsigned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hex_to_decimal);
        hAnswer = findViewById(R.id.hAnswer);
        hQuestion = findViewById(R.id.hQuestion);
        hCheck = findViewById(R.id.hCheck);
        hSigned = findViewById(R.id.hSigned);
        hUnsigned = findViewById(R.id.hUnsigned);
        hRoundScore = findViewById(R.id.hRoundScore);
        generateQuestion();
    }

    public void generateNumber() {
        Random random = new Random(System.currentTimeMillis());
        int num = random.nextInt((int) Math.pow(2, MainActivity.bitMode - 1)) - 1;
        int sign = random.nextInt() % 2 == 1 ? 1 : -1;
        oriSigned = num * sign;
        oriUnsigned = oriSigned >= 0 ? oriSigned : (oriSigned + (int) Math.pow(2, MainActivity.bitMode));
    }

    public void generateQuestion() {
        generateNumber();
        String hex = Integer.toHexString(oriSigned);
        hex = hex.substring(hex.length()-3, hex.length());
        String str = "What are the signed and unsigned values for the " + MainActivity.bitMode + "-bit value 0x" + hex + "?";
        hQuestion.setText(str);
    }

    public void updateScore() {
        hRoundScore.setText("Score: " + roundScore + "/" + totalRoundPossible);
    }

    public void hCheckAnswer(View view) {
        int ansSigned = 0;
        int ansUnsigned = 0;
        try {
            ansSigned = Integer.parseInt(hSigned.getText().toString());
            ansUnsigned = Integer.parseInt(hUnsigned.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_LONG).show();
            return;
        }
        if (hAsking) {
            totalRoundPossible += 2;
            if (oriSigned == ansSigned && oriUnsigned == ansUnsigned) {
                roundScore += 2;
                displayAnswer(true);
            } else {
                displayAnswer(false);
            }
        } else {
            generateQuestion();
            hCheck.setText("Check My Answer");
            hAsking = true;
            hAnswer.setVisibility(View.INVISIBLE);
            hUnsigned.setText("");
            hSigned.setText("");
        }
    }

    public void displayAnswer(boolean correct) {
        if (correct) {
            hAnswer.setText("Correct!!");
        } else {
            String str = "Unsigned: " + oriUnsigned + "\nSigned: " + oriSigned;
            hAnswer.setText(str);
        }
        hAsking = false;
        hCheck.setText("Click for new question");
        updateScore();
        hAnswer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.totalPossible += totalRoundPossible;
        MainActivity.score += roundScore;
    }
}
