package com.example.joe.smarttask.SignUp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.joe.smarttask.R;

/**
 * Created by joe on 11/02/2017.
 */

public class SignUpActivity extends AppCompatActivity {

    protected static boolean createdNewAccount = false;
    private Context context;
    private Intent intent;
    //Buttons and textviews
    private Button signUp;
    private EditText firstName, lastName, email, birthday, userName, password;

    //create String for data to upload (comma delimited)
    //private String[] dataString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = getIntent();
        super.onCreate(savedInstanceState);

        //set's the content (layout)
        setContentView(R.layout.sign_up_page);


        //assign button clicklistener and set Data
        signUp = (Button) findViewById(R.id.signupButton);
        signUp.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          sendData();
                                      }
                                  }
        );

        //assign textviews
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        birthday = (EditText) findViewById(R.id.birthday);
        email = (EditText) findViewById(R.id.email);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);


    }


    private void sendData() {
        String[] dataString = new String[6];
        dataString[0] = firstName.getText().toString();
        dataString[1] = lastName.getText().toString();
        dataString[2] = birthday.getText().toString();
        dataString[3] = email.getText().toString();
        dataString[4] = userName.getText().toString();
        dataString[5] = password.getText().toString();

        CheckData checkData = new CheckData(this);
        checkData.setData(dataString);


        ServerSettings serverSettings = new ServerSettings(this);
        serverSettings.setData(dataString);

        if (createdNewAccount) {
            finish();
        }

    }


}
