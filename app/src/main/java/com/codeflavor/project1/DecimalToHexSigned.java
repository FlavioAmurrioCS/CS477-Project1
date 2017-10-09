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

public class DecimalToHexSigned extends AppCompatActivity {

    TextView uQuestion, uAnswer, uScore;
    RadioGroup uRadioGroup;
    Spinner uOne, uTwo, uThree;
    Button uCheck;

    int decNumber = 0;
    int uPossible = 0;
    int uTotal = 0;
    boolean uAsking = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decimal_to_hex_signed);
        uQuestion = findViewById(R.id.sQuestion);
        uAnswer = findViewById(R.id.sAnswer);
        uScore = findViewById(R.id.sScore);
        uRadioGroup = findViewById(R.id.sRadioGroup);
        uOne = findViewById(R.id.sOne);
        uTwo = findViewById(R.id.sTwo);
        uThree = findViewById(R.id.sThree);
        uCheck = findViewById(R.id.sCheck);
        if (MainActivity.bitMode != 10) {
            uOne.setEnabled(false);
        }

        ArrayAdapter<CharSequence> hexAdapter = ArrayAdapter.createFromResource(this,
                R.array.hex_array, android.R.layout.simple_spinner_item);
        hexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        uOne.setAdapter(hexAdapter);
        uTwo.setAdapter(hexAdapter);
        uThree.setAdapter(hexAdapter);
        uGenerateQuestion();
    }

    public void displayAnswer(boolean correct) {
        if (correct) {
            uAnswer.setText("Correct!!");
        } else {
            uAnswer.setText("Answer is: 0x" + Integer.toHexString(decNumber));
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
        Random random = new Random();
        decNumber = random.nextInt((int) Math.pow(2, MainActivity.bitMode + 2));
        int sign = random.nextInt() % 2 == 0 ? -1 : 1;
        decNumber *= sign;
        String question = "For Signed(two's complement) numbers, what is the " + MainActivity.bitMode + "-bit hex value for the decimal value " + decNumber + "?";
        uQuestion.setText(question);
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

    public void sCheckMyAnswer(View view) {
        if (uAsking) {
            int upperRange = (int) Math.pow(2, MainActivity.bitMode - 1);
            int lowerRange = upperRange * -1;
            uPossible += 2;
            switch (uRadioGroup.getCheckedRadioButtonId()) {
                case R.id.sSmall:
                    if (decNumber < lowerRange) {
                        uTotal += 2;
                        displayAnswer(true);
                    } else {
                        displayAnswer(false);
                    }
                    break;
                case R.id.sLarge:
                    if (decNumber > upperRange) {
                        uTotal += 2;
                        displayAnswer(true);
                    } else {
                        displayAnswer(false);
                    }
                    break;
                case R.id.sValue:
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
}
