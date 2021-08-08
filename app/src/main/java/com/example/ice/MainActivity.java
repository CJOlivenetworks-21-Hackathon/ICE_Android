package com.example.ice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FilterShape shape1 = new FilterShape(this, 0,20,400,400,false);
        FilterShape shape2 = new FilterShape(this, 600,20,400,400,true);

        setContentView(shape1);
        setContentView(shape2);
    }
}