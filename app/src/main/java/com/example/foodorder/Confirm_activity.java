package com.example.foodorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Confirm_activity extends AppCompatActivity {

    private EditText editTextExpiryDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        editTextExpiryDate = findViewById(R.id.editTextExpiryDate);

        editTextExpiryDate.addTextChangedListener(new TextWatcher() {
            private static final String DATE_SEPARATOR = "/";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();

                if (input.length() == 2 && !input.contains(DATE_SEPARATOR)) {
                    input = input.substring(0, 2) + DATE_SEPARATOR + input.substring(2);
                    editTextExpiryDate.setText(input);
                    editTextExpiryDate.setSelection(input.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
