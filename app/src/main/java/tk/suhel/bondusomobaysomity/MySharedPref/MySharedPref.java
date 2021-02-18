package tk.suhel.bondusomobaysomity.MySharedPref;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;
import java.util.Set;

import static tk.suhel.bondusomobaysomity.MySharedPref.Constant.*;

public class MySharedPref {
    private SharedPreferences sharedPreferences;
    private Context context;

    public MySharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_ID, Context.MODE_PRIVATE);
    }

    public void setLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGIN_STATUS, status);
        editor.apply();
    }

    public boolean getLoginStatus(){
        return sharedPreferences.getBoolean(LOGIN_STATUS, false);
    }

    public void setLoginUserId(int id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LOGIN_ID, id);
        editor.apply();
    }

    public int getLoginUserId(){
        return sharedPreferences.getInt(LOGIN_ID, -1);
    }

    public void setLoginName(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGIN_NAME, name);
        editor.apply();
    }

    public String getLoginName(){
        return sharedPreferences.getString(LOGIN_NAME, "null");
    }

    public void setLoginUserName(String userNamename){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGIN_USERNAME, userNamename);
        editor.apply();
    }

    public String getLoginUserName(){
        return sharedPreferences.getString(LOGIN_USERNAME, "null");
    }

    public void setLoginEmail(String email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGIN_EMAIL, email);
        editor.apply();
    }

    public String getLoginEmail(){
        return sharedPreferences.getString(LOGIN_EMAIL, "null");
    }

    public void setLoginToken(String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGIN_TOKEN, token);
        editor.apply();
    }

    public String getLoginToken(){
        return sharedPreferences.getString(LOGIN_TOKEN, "null");
    }

    public void setAllUserName(Set<String> name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(ALL_USER_NAME, name);
        editor.apply();
    }

    public Set<String> getAllUserName(){
        return sharedPreferences.getStringSet(ALL_USER_NAME, null);
    }

    public void setUserInformationToSharedPref(int id, String name, String userName, String email, String token){
        setLoginStatus(true);
        setLoginUserId(id);
        setLoginName(name);
        setLoginUserName(userName);
        setLoginEmail(email);
        setLoginToken(token);
    }

    public void deleteUserInformationFromSharedPref(){
        setLoginStatus(false);
        setLoginUserId(-1);
        setLoginName("null");
        setLoginUserName("null");
        setLoginEmail("null");
        setLoginToken("null");
    }

}
