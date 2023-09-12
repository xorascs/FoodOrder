package com.example.foodorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import io.paperdb.Paper;

public class Cart extends Images {

    int[] images = getImages();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        try {
            loadAllProducts();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loadAllProducts() throws JSONException {

        int[] prods = paperProd();

            if (prods.length != 0) {

                Button buyView = new Button(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(400, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.leftMargin = 640;
                lp.topMargin = 150;
                buyView.setText("Pirkt");
                buyView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                buyView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Cart.this, Confirm_activity.class));
                    }
                });

                ArrayList<HashMap<String, String>> formList = getList();
                LinearLayout cardContainer = findViewById(R.id.cardContainer);

                for (int i = 0; i < prods.length; i++) {

                    int prod = prods[i];

                    CardView cardView = new CardView(this);
                    ImageView imageView = new ImageView(this);
                    TextView nameView = new TextView(this);
                    TextView costView = new TextView(this);
                    Button deleteView = new Button(this);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(16, 16, 16, 16);
                    cardView.setLayoutParams(layoutParams);

                    LinearLayout.LayoutParams imlp = new LinearLayout.LayoutParams(100, 100);

                    imageView.setImageResource(images[prod]);

                    nameView.setGravity(1);
                    nameView.setTextSize(24);
                    nameView.setPadding(16, 16, 16, 16);

                    costView.setGravity(0);
                    costView.setTextSize(16);
                    costView.setPadding(16, 192, 16, 16);

                    deleteView.setText("Dzēst");
                    deleteView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Paper.book().delete("product-" + prod);
                            finish();
                            startActivity(getIntent());
                        }
                    });

                    String nameText = formList.get(prod).get("name");
                    String costText = formList.get(prod).get("cost");

                    nameView.setText(nameText);
                    costView.setText(costText + " Eur");

                    cardView.addView(imageView, imlp);
                    cardView.addView(nameView);
                    cardView.addView(costView);
                    cardView.addView(deleteView, lp);

                    cardContainer.addView(cardView);
                }

                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(400, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp1.leftMargin = 640;
                lp1.topMargin = 80;
                lp1.bottomMargin = 100;

                cardContainer.addView(buyView, lp1);
            }
    }

    public ArrayList<HashMap<String, String>> getList() throws JSONException {
        ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();

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
        return formList;
    }

    public void backToSm(View v) {
        startActivity(new Intent(Cart.this, Supermarket.class));
    }

    public int[] paperProd() throws JSONException {
        ArrayList<HashMap<String, String>> formList = getList();

        ArrayList<Integer> resultList = new ArrayList<>();

        for(int j = 0; j < images.length + 1; j++) {
            for (int i = 0; i < formList.size(); i++) {
                // Check if the value stored in Paper.book() is "true" (String)
                if (Paper.book().read("product-" + j, "false").equals(formList.get(i).get("name"))) {
                    resultList.add(i);
                }
            }
        }

        int[] result = new int[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }

        return result;
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
