package com.svalero.toplaptop.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.svalero.toplaptop.R;

public class MainMenuView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void addComputer(View view) {
        Intent intent = new Intent(this, AddComputerView.class);
        startActivity(intent);
    }

    public void addUser (View view){
        Intent intent = new Intent(this, AddUserView.class);
        startActivity(intent);
    }

    public void addOrder (View view){
        Intent intent = new Intent(this, AddOrderView.class);
        startActivity(intent);
    }

    public void viewComputer (View view){
        Intent intent = new Intent(this, ComputerListView.class);
        startActivity(intent);
    }

    public void viewUser (View view){
        Intent intent = new Intent(this, UserListView.class);
        startActivity(intent);
    }

    public void viewOrder (View view){
        Intent intent = new Intent(this, OrderListView.class);
        startActivity(intent);
    }

}