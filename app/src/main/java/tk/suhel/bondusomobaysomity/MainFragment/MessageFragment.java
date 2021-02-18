package tk.suhel.bondusomobaysomity.MainFragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tk.suhel.bondusomobaysomity.ArrayAdapter;
import tk.suhel.bondusomobaysomity.R;
import tk.suhel.bondusomobaysomity.UserModel;
import tk.suhel.bondusomobaysomity.VolleySingleton;

import static tk.suhel.bondusomobaysomity.MySharedPref.Constant.BASE_URL;

public class MessageFragment extends Fragment {

    public RecyclerView recyclerView;
    public ArrayAdapter myArrayAdapter;
    public List<UserModel> userModelList;

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_message, container, false);

        userModelList = new ArrayList<>();
        getAllUser();

        recyclerView = view.findViewById(R.id.user_list_adapter);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(view.getContext(), 1);
        recyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    public void getAllUser(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, BASE_URL + "user/all", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject jsonObject = response.getJSONObject(i);
                                    UserModel userModel = new UserModel(
                                            jsonObject.getString("name"),
                                            jsonObject.getString("username"),
                                            jsonObject.getString("email")
                                    );
                                    userModelList.add(userModel);
                                    myArrayAdapter = new ArrayAdapter(userModelList, getContext());
                                    recyclerView.setAdapter(myArrayAdapter);
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
        VolleySingleton.getInstance(this.getContext()).addToRequestQueue(jsonArrayRequest);
    }
}
