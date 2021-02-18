package tk.suhel.bondusomobaysomity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tk.suhel.bondusomobaysomity.MySharedPref.MySharedPref;

import static android.graphics.Color.RGBToHSV;
import static android.graphics.Color.rgb;
import static tk.suhel.bondusomobaysomity.MySharedPref.Constant.BASE_URL;

public class UserPaymentDetails extends AppCompatActivity {

    MySharedPref mySharedPref;
//    TextView paymentMonth, paymentDate, paymentMoney;
    TableLayout paymentTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payment_details);
/*
        paymentMonth = findViewById(R.id.paymentDetailsMonth);
        paymentDate = findViewById(R.id.paymentDetailsDate);
        paymentMoney = findViewById(R.id.paymentDetailsMoney);*/
        paymentTable = findViewById(R.id.paymentDetailsTable);

        mySharedPref = new MySharedPref(UserPaymentDetails.this);

        getUserAllPayment();
    }

    public void getUserAllPayment(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, BASE_URL + "amount/all-payment/" + mySharedPref.getLoginUserId(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("jsonarray", "onR"+response);
                        for(int i=0;i<=response.length();i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                TableRow row = new TableRow(UserPaymentDetails.this);
                                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                                row.setLayoutParams(lp);
                                TextView month = new TextView(UserPaymentDetails.this);
                                TextView date = new TextView(UserPaymentDetails.this);
                                TextView money = new TextView(UserPaymentDetails.this);
                                month.setText(jsonObject.getString("paymentMonth"));
                                date.setText(jsonObject.getString("paymentDate"));
                                money.setText(jsonObject.getString("money"));

                                styleTextView(month, i);
                                styleTextView(date, i);
                                styleTextView(money, i);

                                row.addView(month);
                                row.addView(date);
                                row.addView(money);
                                paymentTable.setStretchAllColumns(true);
                                paymentTable.addView(row, i+1);

                                /*paymentMonth.setText(jsonObject.getString("paymentMonth"));
                                paymentDate.setText(jsonObject.getString("paymentDay"));
                                paymentMoney.setText(jsonObject.getString("money"));*/
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("jsonarray", "Error is called"+error.getMessage());
                    }
                }
        );
        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    public void styleTextView(TextView textView, int index){
        if (index % 2 != 0) {
            textView.setBackgroundColor(rgb(230, 230, 230));
        }
        textView.setPadding(0, 20, 0, 30);
        textView.setGravity(1);
        textView.setTextSize(20);
        textView.setTextScaleX(1);
    }
}
