package com.hoangminhtai.mydrawview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnDrawView;
    EditText edtRow;

    Handler handler = new Handler();
    Random random = new Random();
    Integer number, checker;

    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,200);

    LinearLayout layout, mainLayout;

    Runnable UIThread = new Runnable() {
        @Override
        public void run() {
            params.setMargins(15,5,15,5);
            layout.setLayoutParams(params);
            layout.setWeightSum(3);
            TextView txt1 = new TextView(MainActivity.this);
            TextView txt2 = new TextView(MainActivity.this);
            if (checker % 2 == 0){
                number = random.nextInt(9);
                createTextView(txt1,1,number,layout);
                number = random.nextInt(9);
                createTextView(txt2,2,number,layout);
            }else{
                number = random.nextInt(9);
                createTextView(txt1,2,number,layout);
                number = random.nextInt(9);
                createTextView(txt2,1,number,layout);
            }
            mainLayout.addView(layout);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkViews();
        btnDrawView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtRow.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Nhap so hang", Toast.LENGTH_LONG).show();
                }else {
                    int rowNumb = Integer.parseInt(edtRow.getText().toString());
                    runInBackground(rowNumb);
                }
            }
        });
    }

    private void runInBackground(int rowNumb) {
        mainLayout.removeAllViews();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= rowNumb; i++){
                    layout = new LinearLayout(MainActivity.this);
                    checker = i;
                    handler.post(UIThread);
                    SystemClock.sleep(100);
                }
            }
        });
        thread.start();
    }

    private void linkViews() {
        btnDrawView = findViewById(R.id.btnDrawView);
        edtRow = findViewById(R.id.edtRow);
        mainLayout = findViewById(R.id.mainLayout);
    }
    @SuppressLint({"ResourceAsColor"})
    private void createTextView(TextView txt, Integer weight, Integer number, LinearLayout layout){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(3,3,3,3);
        params.weight = weight;
        txt.setLayoutParams(params);
        String text = number.toString();
        txt.setText(text);
        txt.setTextSize(20);
        txt.setGravity(Gravity.CENTER);
        if (number % 2 == 0)
            txt.setBackgroundColor(Color.BLUE);
        else
            txt.setBackgroundColor(Color.GRAY);
        layout.addView(txt);
    }
}