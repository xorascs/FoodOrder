package com.example.foodorder;

import androidx.appcompat.app.AppCompatActivity;

public abstract class Images extends AppCompatActivity {

    int[] images ={
            R.drawable.cola_01,
            R.drawable.cola_05,
            R.drawable.cola_03,
            R.drawable.pepsi_03,
            R.drawable.pepsi_05,
            R.drawable.pepsi_01
    };

    public int[] getImages() {
        return images;
    }
}
