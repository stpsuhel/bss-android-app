package tk.suhel.bondusomobaysomity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static tk.suhel.bondusomobaysomity.MySharedPref.Constant.BASE_URL;

public class AddAmountActivity extends AppCompatActivity {
    ArrayAdapter<String> adapterUser;
    List<UserModel> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_amount);

//        String[] list = getAllUser();
//        Log.d("list", ""+list[0]);

        Spinner spinnerMonth = findViewById(R.id.paymentMonthSpinner);
//        Spinner spinnerUser = findViewById(R.id.allUserSpinner);

        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);
//        adapterUser = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);

        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        adapterUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMonth.setAdapter(adapterMonth);
//        spinnerUser.setAdapter(adapterUser);
    }

    /*public String[] getAllUser(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, BASE_URL + "user/all", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject jsonObject = response.getJSONObject(i);

                                    userList.add(new UserModel(
                                            jsonObject.getInt("id"),
                                            jsonObject.getString("name"),
                                            jsonObject.getString("username"),
                                            jsonObject.getString("email"),
                                            jsonObject.getBoolean("is_staff"),
                                            jsonObject.getBoolean("is_superuser")
                                    ));
                                    Log.d("onResponse", "" + response);
//                                    Log.d("onResponse", "user name: "+userList);
                                }
                            }
                        } catch (JSONException e) {
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
        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);

        String[] usernameList = new String[userList.size()];
        for (int i=0; i<userList.size(); i++){
            usernameList[i] = userList.get(i).getUsername();
        }
        return usernameList;
    }*/
}
