package tk.suhel.bondusomobaysomity.MainFragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tk.suhel.bondusomobaysomity.MainActivity;
import tk.suhel.bondusomobaysomity.MySharedPref.MySharedPref;
import tk.suhel.bondusomobaysomity.R;
import tk.suhel.bondusomobaysomity.Registration.LoginActivity;
import tk.suhel.bondusomobaysomity.UserPaymentDetails;
import tk.suhel.bondusomobaysomity.VolleySingleton;

import static tk.suhel.bondusomobaysomity.MySharedPref.Constant.BASE_URL;

public class HomeFragment extends Fragment {
    private MySharedPref mySharedPref;
    private TextView seeDetailsPayment, lastPaymentMonth, lastPaymentMoney, userTotalMoney, totalMoney;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        mySharedPref = new MySharedPref(view.getContext());

        seeDetailsPayment = view.findViewById(R.id.seeUserFullPayment);
        lastPaymentMonth = view.findViewById(R.id.lastPaymentMonth);
        lastPaymentMoney = view.findViewById(R.id.lastPaymentMoney);
        userTotalMoney = view.findViewById(R.id.userTotalMoney);
        totalMoney = view.findViewById(R.id.allTotalMoney);

        lastPaymentMonth.setText("---");
        lastPaymentMoney.setText("---");

        seeDetailsPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), UserPaymentDetails.class);
                startActivity(intent);
            }
        });
        getLastPayment();
        getTotalMoney();
        getTotalUserMoney();

        return view;
    }

    private void getLastPayment(){
        JsonObjectRequest jsonObject = new JsonObjectRequest(
                Request.Method.GET, BASE_URL + "amount/" + mySharedPref.getLoginUserId(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("onresponse", "onR"+response+mySharedPref.getLoginStatus());

                        if(response.length() != 0){
                            try {
                                lastPaymentMonth.setText(response.getString("paymentMonth"));
                                lastPaymentMoney.setText(response.getString("money"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
        VolleySingleton.getInstance(this.getContext()).addToRequestQueue(jsonObject);
    }

    private void getTotalMoney(){
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, BASE_URL + "amount/all-payment", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int money = 0;
                        try {
                            if (response != null) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    money += jsonObject.getInt("money");
                                    Log.d("money", ""+money);
                                }
                                totalMoney.append(String.valueOf(money));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Log.d("money", "error"+money);
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
        VolleySingleton.getInstance(this.getContext()).addToRequestQueue(jsonArrayRequest);
    }

    private void getTotalUserMoney(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, BASE_URL + "amount/all-payment/" + mySharedPref.getLoginUserId(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int money = 0;
                        try {
                            if (response != null) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    money += jsonObject.getInt("money");
                                    Log.d("money", ""+money);
                                }
                                userTotalMoney.append(String.valueOf(money));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Log.d("money", "error"+money);
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
        VolleySingleton.getInstance(this.getContext()).addToRequestQueue(jsonArrayRequest);
    }
}
