package tk.suhel.bondusomobaysomity.Registration;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import tk.suhel.bondusomobaysomity.MainActivity;
import tk.suhel.bondusomobaysomity.MySharedPref.MySharedPref;
import tk.suhel.bondusomobaysomity.R;
import tk.suhel.bondusomobaysomity.VolleySingleton;

import static tk.suhel.bondusomobaysomity.MySharedPref.Constant.BASE_URL;

public class SignUpActivity extends AppCompatActivity {

    EditText signUpName, signUpUserName, signUpEmail, signUpPassword, signUpConfirmPassword;
    Button registrationButton;
    MySharedPref mySharedPref;

    String name, userName, email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mySharedPref = new MySharedPref(SignUpActivity.this);

        signUpName = findViewById(R.id.signup_name);
        signUpUserName = findViewById(R.id.signup_username);
        signUpEmail = findViewById(R.id.signup_email);
        signUpPassword = findViewById(R.id.signup_password);
        signUpConfirmPassword = findViewById(R.id.signup_confirm_password);

        registrationButton = findViewById(R.id.signup_button);

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = signUpName.getText().toString().trim();
                userName = signUpUserName.getText().toString().trim();
                email = signUpEmail.getText().toString().trim();
                password = signUpPassword.getText().toString().trim();
                confirmPassword = signUpConfirmPassword.getText().toString().trim();

                signUpVerification();
            }
        });
    }

    public void signUpVerification(){
        if (name.isEmpty() || userName.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this.getApplicationContext(), "Enter all Information", Toast.LENGTH_LONG).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this.getApplicationContext(), "Enter a valid Email", Toast.LENGTH_LONG).show();
        }
        else if (!password.equals(confirmPassword)){
            Toast.makeText(this.getApplicationContext(), "Password must match", Toast.LENGTH_LONG).show();
        }
        else {
            createUser();
        }
    }

    public void createUser(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL + "user/registration",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            mySharedPref.setUserInformationToSharedPref(
                                    jsonObject.getInt("id"),
                                    name, userName, email,
                                    jsonObject.getString("token")
                            );

                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(SignUpActivity.this, "Registration Successful!!", Toast.LENGTH_SHORT).show();
                        }catch (JSONException e){
                            e.printStackTrace();
                            Log.d("catch", ""+e);
                            Toast.makeText(SignUpActivity.this, "Registration Error. User already exist!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", ""+error);
                        Toast.makeText(SignUpActivity.this, "Registration Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("username", userName);
                params.put("email", email);
                params.put("password", password);
                params.put("password2", confirmPassword);
                return params;
            }
        };
        VolleySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void goSignInPage(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
