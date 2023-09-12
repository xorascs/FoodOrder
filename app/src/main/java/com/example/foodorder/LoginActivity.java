package com.example.foodorder;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends User {

    public EditText emailInput;
    public EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInputLogin);
        passwordInput = findViewById(R.id.passwordInputLogin);

        errorIfEditBlank(passwordInput, "paroli");
        if (!isEmail(emailInput)) {
            emailInput.setError("Ievadiet pareizu e-pastu!");
        }
        emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!isEmail(emailInput)) {
                    emailInput.setError("Ievadiet pareizu e-pastu!");
                }
            }
        });
    }

    boolean isBlankGaps() {
        if (emailInput.getText().toString().equals("") || passwordInput.getText().toString().equals("") || !isEmail(emailInput)) {
            return true;
        }
        else {
            return false;
        }
    }

    static void errorIfEditBlank(EditText t, String text) {
        if (t.getText().toString().equals("")) {
            t.setError("Ievadiet savu " + text + "!");
        }
        t.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (t.getText().toString().equals("")) {
                    t.setError("Ievadiet pareizu " + text + "!");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }


    public void login(View v) {
        if (!isBlankGaps() && logInUser(emailInput.getText().toString(), passwordInput.getText().toString())) {
            startActivity(new Intent(LoginActivity.this, Supermarket.class));
        }
    }

    public void moveToRegistration(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}