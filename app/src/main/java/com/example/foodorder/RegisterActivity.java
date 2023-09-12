package com.example.foodorder;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import io.paperdb.Paper;

public class RegisterActivity extends User {

    public EditText nameInput;
    public EditText surnameInput;
    public EditText phoneInput;
    public EditText emailInput;
    public EditText passwordInput;
    public CheckBox rulesCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Paper.init(this);

        for (int i = 0; i < 7; i++) {
            Paper.book().delete("product-" + i);
        }

        nameInput = findViewById(R.id.nameInputRegister);
        surnameInput = findViewById(R.id.surnameInputRegister);
        phoneInput = findViewById(R.id.phoneInputRegister);
        emailInput = findViewById(R.id.emailInputRegister);
        passwordInput = findViewById(R.id.passwordInputRegister);
        rulesCheck = findViewById(R.id.rulesCheckRegister);

        errorIfEditBlank(nameInput, "vārdu");
        errorIfEditBlank(surnameInput, "uzvārdu");
        errorIfEditBlank(phoneInput, "tālruņa numuru");
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
        if (nameInput.getText().toString().equals("") || surnameInput.getText().toString().equals("")
                || phoneInput.getText().toString().equals("") || emailInput.getText().toString().equals("")
                || passwordInput.getText().toString().equals("") || !rulesCheck.isChecked() || !isEmail(emailInput)) {
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
                if ((!t.getText().toString().equals("") && text.equals("tālruņa numuru") && !isValidCellPhone(t))
                        || t.getText().toString().equals("")) {
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

    static boolean isValidCellPhone(EditText number) {
        CharSequence numText = number.getText().toString();
        if (numText.length() < 6 || numText.length() > 13) {
            return false;
        }
        else {
            return android.util.Patterns.PHONE.matcher(numText).matches();
        }
    }


    public void register(View v) {
        if (!isBlankGaps() && !isUserExistRegister(emailInput.getText().toString(), phoneInput.getText().toString())) {
            String name = nameInput.getText().toString();
            String surname = surnameInput.getText().toString();
            String phone = phoneInput.getText().toString();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();

            createNewUser(name,surname,phone,email,password);
            Toast.makeText(this, "Veiksmīga reģistrācija!", Toast.LENGTH_SHORT).show();

            nameInput.setText("");
            surnameInput.setText("");
            phoneInput.setText("");
            emailInput.setText("");
            passwordInput.setText("");
            rulesCheck.setChecked(false);
        }
        else {
            if (isBlankGaps()) {
                Toast.makeText(this, "Jūs ievadījāt nepareizus datus!", Toast.LENGTH_SHORT).show();
            }
            if (isUserExistRegister(emailInput.getText().toString(), phoneInput.getText().toString())) {
                Toast.makeText(this, "Lietotājs ar tādu tālr. nr. vai e-pastu jau eksistē!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void moveToLogin(View v) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}