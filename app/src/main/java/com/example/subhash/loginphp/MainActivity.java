package com.example.subhash.loginphp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText e_email,e_password;
    Button b_login,b_register;
    String mail="",pass="";
    ProgressDialog progressDialog;

    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult="";
    String HttpURL = "https://192.168.0.103/login/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_login=findViewById(R.id.btnLogin);
        b_register=findViewById(R.id.btnLinkToRegisterScreen);
        e_email=findViewById(R.id.email);
        e_password=findViewById(R.id.password);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.INTERNET},
                1);



        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail=e_email.getText().toString();
                pass=e_password.getText().toString();
                if (mail.equals(""))
                {
                    e_email.setError("Please Enter Email");
//                    Toast.makeText(MainActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                }
                else if (pass.equals(""))
                {
                    e_password.setError("Please Enter Password");
//                    Toast.makeText(MainActivity.this, "Enter  Password", Toast.LENGTH_SHORT).show();
                }
                else if (!mail.equals("")&&!pass.equals(""))
                {
//                    Toast.makeText(MainActivity.this, "Welcome to login", Toast.LENGTH_SHORT).show();
                    UserLoginFunction(mail,pass);
                }
            }
        });

    }



    private void UserLoginFunction(String mail, String pass) {
        class UserLoginClass extends AsyncTask<String, Void, String> {




            @Override
            protected void onPreExecute () {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(MainActivity.this, "Loading...", null, true, true);
            }


            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("Data Matched")){

                    finish();

//                    Intent intent = new Intent(MainActivity.this, Welcome.class);

//                    intent.putExtra(UserEmail,email);

//                    startActivity(intent);

                }
                else{

                    Toast.makeText(MainActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("email",params[0]);

                hashMap.put("password",params[1]);
                Log.e("Myapp","i am in doin background");

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }


        }
        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(mail,pass);
    }



}
