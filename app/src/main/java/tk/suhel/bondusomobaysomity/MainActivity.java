package tk.suhel.bondusomobaysomity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tk.suhel.bondusomobaysomity.MainFragment.HomeFragment;
import tk.suhel.bondusomobaysomity.MainFragment.MessageFragment;
import tk.suhel.bondusomobaysomity.MainFragment.ProfileFragment;
import tk.suhel.bondusomobaysomity.MySharedPref.MySharedPref;
import tk.suhel.bondusomobaysomity.Registration.LoginActivity;

import static tk.suhel.bondusomobaysomity.MySharedPref.Constant.BASE_URL;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    List<UserModel> userList;
    ArrayAdapter<String> adapterUser;
    NavigationView navigationView;

    FloatingActionButton fabAddButton;
    private DrawerLayout mNavDrawer;
    MySharedPref mySharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mySharedPref = new MySharedPref(this.getApplicationContext());
        checkLogIn();

        fabAddButton = findViewById(R.id.addAmountForUser);

        fabAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getAllUser();
                addUserAmount();
                /*Intent intent = new Intent(MainActivity.this, AddAmountActivity.class);
                startActivity(intent);*/
            }
        });

        mNavDrawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mNavDrawer, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close
        );
        mNavDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        updateNavHeaderInformation();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
            setTitle("Home");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.logout){
            mySharedPref.deleteUserInformationFromSharedPref();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "LogOut from Profile", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if(mNavDrawer.isDrawerOpen(GravityCompat.START)){
            mNavDrawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                setTitle("Home");
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                setTitle("Profile");
                break;
            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MessageFragment()).commit();
                setTitle("Message");
                break;
        }

        mNavDrawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void checkLogIn(){
        if (!mySharedPref.getLoginStatus()){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void addUserAmount(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.add_amount_dialog, null);

        Spinner spinnerMonth = view.findViewById(R.id.paymentMonthSpinner);
//        Spinner spinnerUser = view.findViewById(R.id.allUserSpinner);

        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);

        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        adapterUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerMonth.setAdapter(adapterMonth);
//        spinnerUser.setAdapter(adapterUser);

        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /*public void getAllUser(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, BASE_URL + "user/all", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response.length() > 0) {
                                Set<String> usernameList = null;
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject jsonObject = response.getJSONObject(i);
                                    usernameList.add(jsonObject.getString("username"));

                                    *//*userList.add(new UserModel(
                                            jsonObject.getInt("id"),
                                            jsonObject.getString("name"),
                                            jsonObject.getString("username"),
                                            jsonObject.getString("email"),
                                            jsonObject.getBoolean("is_staff"),
                                            jsonObject.getBoolean("is_superuser")
                                    ));*//*
                                    Log.d("onResponse", "" + response);
                                    Log.d("onResponse", "user name: "+userList);
                                }
                                mySharedPref.setAllUserName(usernameList);
                                *//*for (int i=0; i<userList.size(); i++){
                                    usernameList[i] = userList.get(i).getUsername();
                                }*//*

                                *//*try {
                                    adapterUser = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, usernameList);
                                }catch (Exception e){
                                    e.printStackTrace();
                                    Log.d("exceptiontry", ""+e.toString());
                                }*//*
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
    }*/

    public void updateNavHeaderInformation(){
        View headerView = navigationView.getHeaderView(0);
        TextView headerName = headerView.findViewById(R.id.nav_header_name);
        TextView headerEmail = headerView.findViewById(R.id.nav_header_email);

        headerName.setText(mySharedPref.getLoginName());
        headerEmail.setText(mySharedPref.getLoginEmail());
    }
}