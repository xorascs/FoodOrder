package com.example.foodorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class prodDetails extends Images {

    int[] images = getImages();

    TextView nameView;
    TextView costView;
    ImageView imageView;

    String prodName;
    String prodCost;
    int prodImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_details);

        nameView = findViewById(R.id.productName);
        costView = findViewById(R.id.productCost);
        imageView = findViewById(R.id.imageView);

        prodName = getIntent().getStringExtra("prodName");
        prodCost = getIntent().getStringExtra("prodCost");
        prodImage = getIntent().getIntExtra("prodImage", 0);

        nameView.setText(prodName);
        costView.setText(prodCost);
        imageView.setImageResource(images[prodImage]);
    }

    public void backToSm(View v) {
        startActivity(new Intent(prodDetails.this, Supermarket.class));
    }
}