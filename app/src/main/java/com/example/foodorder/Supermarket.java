package com.example.foodorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import io.paperdb.Paper;

public class Supermarket extends Images {

    ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();

    int[] images = getImages();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarket);

        try {
            loadAllProducts();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loadAllProducts() throws JSONException {

        LinearLayout cardContainer = findViewById(R.id.cardContainer);

        JSONObject obj = new JSONObject(getJsonFromAssets(this, "products.json"));
        JSONArray m_jArry = obj.getJSONArray("products");

        HashMap<String, String> m_li;

        for (int i = 0; i < m_jArry.length(); i++) {

            JSONObject jo_inside = m_jArry.getJSONObject(i);
            String productName = jo_inside.getString("name");
            String productCost = jo_inside.getString("cost");

            // Add your values to the ArrayList
            m_li = new HashMap<String, String>();
            m_li.put("name", productName);
            m_li.put("cost", productCost);

            formList.add(m_li);
        }

        for (int i = 0; i < formList.size(); i++) {
            int finalI = i;

            CardView cardView = new CardView(this);
            ImageView imageView = new ImageView(this);
            TextView nameView = new TextView(this);
            TextView costView = new TextView(this);
            Button buyView = new Button(this);

            cardView.setCardBackgroundColor(5);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(16, 16, 16, 16);
            cardView.setLayoutParams(layoutParams);

            LinearLayout.LayoutParams imlp = new LinearLayout.LayoutParams(100, 100);

            imageView.setImageResource(images[i]);

            nameView.setGravity(1);
            nameView.setTextSize(24);
            nameView.setPadding(16, 16, 16, 16);
            nameView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(Supermarket.this, prodDetails.class);
                    intent.putExtra("prodName",nameView.getText().toString());
                    intent.putExtra("prodImage",finalI);
                    intent.putExtra("prodCost",costView.getText().toString());
                    startActivity(intent);
                }
            });

            costView.setGravity(0);
            costView.setTextSize(16);
            costView.setPadding(16, 192, 16, 16);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(400, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = 640;
            lp.topMargin = 150;
            buyView.setText("Pirkt");
            buyView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            String nameText = formList.get(i).get("name");
            String costText = formList.get(i).get("cost");

            nameView.setText(nameText);
            costView.setText(costText + " Eur");

            buyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paper.book().write("product-" + finalI, nameView.getText().toString());
                    Toast.makeText(Supermarket.this, "Jūs pievienojat produktu grozā!", Toast.LENGTH_SHORT).show();
                }
            });

            cardView.addView(imageView, imlp);
            cardView.addView(nameView);
            cardView.addView(costView);
            cardView.addView(buyView, lp);

            cardContainer.addView(cardView);
        }
    }

    public void deleteAllProdFromCart() {
        for(int i = 0; i < formList.size(); i++) {
            Paper.book().delete("product-" + i);
        }
    }

    public void moveToCart(View v) {
        startActivity(new Intent(Supermarket.this, Cart.class));
    }


    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }

}
