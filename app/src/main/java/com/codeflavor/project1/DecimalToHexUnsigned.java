package com.codeflavor.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

public class DecimalToHexUnsigned extends AppCompatActivity {

    TextView uQuestion, uAnswer, uScore;
    RadioGroup uRadioGroup;
    Spinner uOne, uTwo, uThree;
    Button uCheck;

    int uPossible = 0;
    int uTotal = 0;
    boolean uAsking = true;
    int decNumber = 0;
    int upperRange = 0;
    int lowerRange = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decimal_to_hex_unsigned);
        uQuestion = findViewById(R.id.uQuestion);
        uAnswer = findViewById(R.id.uAnswer);
        uScore = findViewById(R.id.uScore);
        uRadioGroup = findViewById(R.id.uRadioGroup);
        uOne = findViewById(R.id.uOne);
        uTwo = findViewById(R.id.uTwo);
        uThree = findViewById(R.id.uThree);
        uCheck = findViewById(R.id.uCheck);

        ArrayAdapter<CharSequence> hexAdapter = ArrayAdapter.createFromResource(this,
                R.array.hex_array, android.R.layout.simple_spinner_item);
        hexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uOne.setAdapter(hexAdapter);
        uTwo.setAdapter(hexAdapter);
        uThree.setAdapter(hexAdapter);
        uGenerateQuestion();
        if (MainActivity.bitMode != 10) {
            uOne.setEnabled(false);
        }
    }

    public void uCheckMyAnswer(View view) {
        if (uAsking) {
            uPossible += 2;
            switch (uRadioGroup.getCheckedRadioButtonId()) {
                case R.id.uSmall:
                    if (decNumber < lowerRange) {
                        uTotal += 2;
                        displayAnswer(true);
                    } else {
                        displayAnswer(false);
                    }
                    break;
                case R.id.uLarge:
                    if (decNumber > upperRange) {
                        uTotal += 2;
                        displayAnswer(true);
                    } else {
                        displayAnswer(false);
                    }
                    break;
                case R.id.uValue:
                    int value = uSpinnerToInt();
                    if (value == decNumber) {
                        uTotal += 2;
                        displayAnswer(true);
                    } else {
                        displayAnswer(false);
                    }
                    break;
            }
        } else {
            uGenerateQuestion();
            uCheck.setText("Check My Answer");
            uAsking = true;
            uAnswer.setVisibility(View.INVISIBLE);
        }
    }

    public void displayAnswer(boolean correct) {
        if (correct) {
            uAnswer.setText("Correct!!");
        } else {
            String hex = Integer.toHexString(decNumber);
            hex = hex.substring(hex.length() - 3, hex.length());
            uAnswer.setText("Answer is: 0x" + hex);
        }
        uAsking = false;
        uCheck.setText("Click for new question");
        updateScore();
        uAnswer.setVisibility(View.VISIBLE);
    }

    public int uSpinnerToInt() {
        String one = uOne.getSelectedItem().toString();
        String two = uTwo.getSelectedItem().toString();
        String three = uThree.getSelectedItem().toString();
        String ans = one + two + three;
        return Integer.parseInt(ans, 16);
    }

    public void uGenerateQuestion() {
        uGenerateNumber();
        String question = "For unsigned numbers, what is the " + MainActivity.bitMode + "-bit hex value for the decimal value " + decNumber + "?";
        uQuestion.setText(question);
    }

    public void uGenerateNumber()
    {
        Random random = new Random();
        decNumber = random.nextInt((int) Math.pow(2, MainActivity.bitMode + 2));
        int sign = random.nextInt() % 2 == 0 ? -1 : 1;
        decNumber *= sign;
        lowerRange = 0;
        upperRange = (int)Math.pow(2,MainActivity.bitMode)-1;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.totalPossible += uPossible;
        MainActivity.score += uTotal;
    }

    public void updateScore() {
        uScore.setText("Score: " + uTotal + "/" + uPossible);
    }
}
