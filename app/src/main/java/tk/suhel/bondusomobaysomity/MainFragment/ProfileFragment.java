package tk.suhel.bondusomobaysomity.MainFragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import tk.suhel.bondusomobaysomity.Registration.LoginActivity;
import tk.suhel.bondusomobaysomity.MySharedPref.MySharedPref;
import tk.suhel.bondusomobaysomity.R;
import tk.suhel.bondusomobaysomity.VolleySingleton;

import static android.widget.Toast.LENGTH_SHORT;
import static tk.suhel.bondusomobaysomity.MySharedPref.Constant.BASE_URL;

public class ProfileFragment extends Fragment{

    private AlertDialog alertDialog;

    private MySharedPref mySharedPref;
    private TextView editProfile, changePassword, logoutProfile, profileName, profileEmail;
    private EditText updateName, updateUserName, updateEmail;
    private Button updateCancelButton, updateButton;

    private String name, username, email;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mySharedPref = new MySharedPref(view.getContext());
        Log.d("getid", ""+mySharedPref.getLoginUserId());

        profileName = view.findViewById(R.id.profile_name);
        profileEmail = view.findViewById(R.id.profile_email);
        editProfile = view.findViewById(R.id.edit_profile);
        changePassword = view.findViewById(R.id.change_password);
        logoutProfile = view.findViewById(R.id.logout_from_profile);

        profileName.setText(mySharedPref.getLoginName());
        profileEmail.setText(mySharedPref.getLoginEmail());

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Edit Profile", LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                @SuppressLint("InflateParams") View dialogView = getLayoutInflater().inflate(R.layout.update_user_dialog, null);

                updateName = dialogView.findViewById(R.id.update_profile_name);
                updateUserName = dialogView.findViewById(R.id.update_profile_username);
                updateEmail = dialogView.findViewById(R.id.update_profile_email);
                updateButton = dialogView.findViewById(R.id.update_profile_updatebtn);
                updateCancelButton = dialogView.findViewById(R.id.update_profile_cancel);

                updateName.setText(mySharedPref.getLoginName());
                updateUserName.setText(mySharedPref.getLoginUserName());
                updateEmail.setText(mySharedPref.getLoginEmail());

                builder.setView(dialogView);
                alertDialog = builder.create();
                alertDialog.show();

                updateCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        name = updateName.getText().toString().trim();
                        username = updateUserName.getText().toString().trim();
                        email = updateEmail.getText().toString().trim();

                        if (name.isEmpty() || email.isEmpty() || username.isEmpty()){
                            Toast.makeText(view.getContext(), "Enter All Information!!", LENGTH_SHORT).show();
                        }else if (name.equals(mySharedPref.getLoginName()) && username.equals(mySharedPref.getLoginUserName()) && email.equals(mySharedPref.getLoginEmail())){
                            Toast.makeText(view.getContext(), "No change found!! Nothing to update", LENGTH_SHORT).show();
                        }else {
                            confirmDialog(view.getContext());
                        }
                    }
                });

            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Change Password", LENGTH_SHORT).show();
            }
        });

        logoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySharedPref.deleteUserInformationFromSharedPref();
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).onBackPressed();

                Toast.makeText(view.getContext(), "LogOut from Profile", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void confirmDialog(final Context context){
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(context);
        confirmBuilder.setTitle("Confirmation!!");
        confirmBuilder.setIcon(R.drawable.ic_info_black_24dp);
        confirmBuilder.setMessage("Are you sure, you want to update your profile?");
        confirmBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateProfile();
            }
        });
        confirmBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        AlertDialog confirmDialog = confirmBuilder.create();
        confirmDialog.show();
    }

    private void updateProfile(){
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, BASE_URL + "user/" + mySharedPref.getLoginUserId() + "/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Update Successful!!", LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Errorooooooooooo", ""+error);
//                        Toast.makeText(this, "Registration Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("username", username);
                params.put("email", email);
                return params;
            }
        };
        VolleySingleton.getInstance(this.getContext()).addToRequestQueue(stringRequest);
    }
}
