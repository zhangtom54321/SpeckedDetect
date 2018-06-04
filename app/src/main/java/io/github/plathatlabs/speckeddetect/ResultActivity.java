package io.github.plathatlabs.speckeddetect;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


    }
    public void shouldContinue(View v) {

        TextView tv1 = (TextView)findViewById(R.id.ResultText);
        if (tv1.getText().equals("No diabetic retinopathy")) {
            tv1.setText("Has diabetic retinopathy");
        }
        else {
            tv1.setText("No diabetic retinopathy");
        }

    }
}
