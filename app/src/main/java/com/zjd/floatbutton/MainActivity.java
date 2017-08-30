package com.zjd.floatbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private CustomFloatButton floatBtn;
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatBtn= (CustomFloatButton) findViewById(R.id.floatBtn);
        btn1= (Button) findViewById(R.id.btn1);

        floatBtn.setOnBtnClickListener(new CustomFloatButton.OnBtnClickListener() {
            @Override
            public void onBtnClick(int position) {
                btn1.setText(position+"");
            }
        });
    }

    public void onClick(View view) {

    }
}
