package com.example.foodorder;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class User extends AppCompatActivity {

    public static void createNewUser(String name, String surname, String phone, String email, String password) {

        int number = Paper.book().read("allUsers", 0);
        String num = Integer.toString(number);

        Paper.book().write("name-" + num, name);
        Paper.book().write("surname-" + num, surname);
        Paper.book().write("phone-" + num, phone);
        Paper.book().write("email-" + num, email);
        Paper.book().write("password-" + num, password);

        Paper.book().write("allUsers", ++number);

        System.out.println("User successfully created!");
    }

    public static void showUserInf(int idU) {
        String id = Integer.toString(idU);

        String name = Paper.book().read("name-" + id);
        String surname = Paper.book().read("surname-" + id);
        String phone = Paper.book().read("phone-" + id);
        String email = Paper.book().read("email-" + id);
        String password = Paper.book().read("password-" + id);

        System.out.println("Name-" + id + ": " + name);
        System.out.println("Surname-" + id + ": " + surname);
        System.out.println("Phone-" + id + ": " + phone);
        System.out.println("Email-" + id + ": " + email);
        System.out.println("Password-" + id + ": " + password + "\n");
    }

    public static void showAllUsers() {
        for (int i = 0; i < Paper.book().read("allUsers", 0); i++) {
            showUserInf(i);
        }
    }

    public static void deleteUser(int idU) {
        String id = Integer.toString(idU);

        Paper.book().delete("name-" + id);
        Paper.book().delete("surname-" + id);
        Paper.book().delete("phone-" + id);
        Paper.book().delete("email-" + id);
        Paper.book().delete("password-" + id);

        System.out.println("User successfully deleted!");
    }

    public static void deleteAllUsers() {
        Paper.book().destroy();
        System.out.println("All users are deleted");
    }

    public boolean isUserExistRegister(String email, String phone) {
        boolean result = false;

        int allUsers = Paper.book().read("allUsers", 0);
            for (int i = 0; i < allUsers; i++) {
                String id = Integer.toString(i);

                String paperEmail = "";
                String paperPhone = "";

                String emailValue = Paper.book().read("email-" + id);
                String phoneValue = Paper.book().read("phone-" + id);

                if (emailValue != null && !emailValue.equals("")) {
                    paperEmail = emailValue;
                }
                if (phoneValue != null && !phoneValue.equals("")) {
                    paperPhone = phoneValue;
                }

                if (paperEmail.equals(email) || paperPhone.equals(phone)) {
                    result = true;
                    break;
                } else {
                    result = false;
                }
            }
        if (result) {System.out.println("User exist!");} else {System.out.println("User does not exist!");}
        return result;
    }

    public boolean logInUser(String email, String password) {
        boolean result = false;

        int allUsers = Paper.book().read("allUsers", 0);
            for (int i = 0; i < allUsers; i++) {
                String id = Integer.toString(i);

                String paperEmail = "";
                String paperPassword = "";

                String emailValue = Paper.book().read("email-" + id);
                String passwordValue = Paper.book().read("password-" + id);

                if (emailValue != null && !emailValue.equals("")) {
                    paperEmail = emailValue;
                }
                if (passwordValue != null && !passwordValue.equals("")) {
                    paperPassword = passwordValue;
                }

                if (paperEmail.equals(email) && paperPassword.equals(password)) {
                    result = true;
                    break;
                } else {
                    result = false;
                }
            }
        if (result) {Toast.makeText(this,"Jūs autorizējos!", Toast.LENGTH_SHORT).show();} else {Toast.makeText(this,"Nepareizs E-pasts vai parole", Toast.LENGTH_SHORT).show();}
        return result;
    }

}
