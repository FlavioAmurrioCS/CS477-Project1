package com.codeflavor.project1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    Spinner qType, qSubType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qType = findViewById(R.id.qType);
        qSubType = findViewById(R.id.qSubType);

        ArrayAdapter<CharSequence> qTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.qType_array, android.R.layout.simple_spinner_item);
        qTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qType.setAdapter(qTypeAdapter);

        ArrayAdapter<CharSequence> qSubTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.qSubType_array, android.R.layout.simple_spinner_item);
        qSubTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qSubType.setAdapter(qSubTypeAdapter);
    }

    public void startQuiz(View view) {
    }
}
