package tk.suhel.bondusomobaysomity.Registration;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

public class LoginActivity extends AppCompatActivity {

    EditText signInUserName, signInPassword;
    String userName, password;

    MySharedPref mySharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInUserName = findViewById(R.id.signin_username);
        signInPassword = findViewById(R.id.signin_password);

        mySharedPref = new MySharedPref(LoginActivity.this);
        checkLogIn();


    }

    public void goSignUpPage(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    public void checkSignInInformation(View view){
        userName = signInUserName.getText().toString().trim();
        password = signInPassword.getText().toString().trim();

        if(userName.isEmpty() || password.isEmpty()){
            signInUserName.setError("Enter User Name");
            signInPassword.setError("Enter Password");
        }
        else {
            logIn();
        }
    }

    public void logIn(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL + "user/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Log.d("Token", ""+jsonObject.getString("token"));
                            setUserSharedPref(jsonObject.getString("token"));

                            Toast.makeText(LoginActivity.this, "LogIn Successful!!", Toast.LENGTH_SHORT).show();
                        }catch (JSONException e){
                            e.printStackTrace();
                            Log.d("catch", ""+e);
                            Toast.makeText(LoginActivity.this, "LogIn Error...", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", ""+error);
                        Toast.makeText(LoginActivity.this, "LogIn Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", userName);
                params.put("password", password);
                return params;
            }
        };
        VolleySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void checkLogIn(){
        if (mySharedPref.getLoginStatus()){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void setUserSharedPref(final String token){
        JsonObjectRequest jsonObject = new JsonObjectRequest(
                Request.Method.GET, BASE_URL + "user/" + userName, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mySharedPref.setUserInformationToSharedPref(
                                    response.getInt("id"),
                                    response.getString("name"), userName,
                                    response.getString("email"), token
                            );

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            Log.d("responselogin", "onR"+response+mySharedPref.getLoginUserId());
                        } catch (JSONException e) {
                            Log.d("response", "catch"+e.toString());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("response", "Error is called"+error.getMessage());
                    }
                }
        );
        VolleySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonObject);
    }
}
