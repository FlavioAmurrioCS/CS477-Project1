package com.codeflavor.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Spinner qType, qSubType;
    TextView scoreText;
    public static int score = 0;
    public static int totalPossible = 0;
    public static int bitMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qType = findViewById(R.id.qType);
        qSubType = (Spinner) findViewById(R.id.qSubType);
        scoreText = findViewById(R.id.scoreText);

        ArrayAdapter<CharSequence> qTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.qType_array, android.R.layout.simple_spinner_item);
        qTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qType.setAdapter(qTypeAdapter);

        ArrayAdapter<CharSequence> qSubTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.qSubType_array, android.R.layout.simple_spinner_item);
        qSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qSubType.setAdapter(qSubTypeAdapter);
        updateScore();
    }


    public void startQuiz(View view) {
        bitMode = getBit();
        Intent intent = null;
        switch (qType.getSelectedItem().toString())
        {
            case "Hex To Decimal": //Decimal To Hex it was wrong on the demo app
                intent = new Intent(this, DecimalToHexActivity.class);
                break;
            case "Decimal To Hex Signed":
                intent = new Intent(this, HexToDecimalSignedActivity.class);
                break;
            case "Decimal To Hex Unsigned":
                intent = new Intent(this, HexToDecimalUnsignedActivity.class);
                break;
            default:
                Toast.makeText(this, "Error Decting Class", Toast.LENGTH_LONG).show();
                return;
        }
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateScore();
    }

    public void updateScore()
    {
        scoreText.setText("Score: " + score + "/" + totalPossible);
    }
    public int getBit()
    {
        int ret = 0;
        switch (qSubType.getSelectedItem().toString())
        {
            case "6 bits":
                ret = 6;
                break;
            case "8 bits":
                ret = 8;
                break;
            case "10 bits":
                ret = 10;
                break;
            default:
                Toast.makeText(this, "Error",Toast.LENGTH_LONG).show();
        }
        return ret;
    }

}
