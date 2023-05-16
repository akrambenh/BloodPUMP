package com.example.bloodpump;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    private  static  final String IS_LOGGED = "isLoggedIn";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    public SessionManager(Context _context){
        context = _context;
        userSession = context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = userSession.edit();
    }
    public void createLoginSession(String email ,String password){
        editor.putBoolean(IS_LOGGED, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);

        editor.commit();
    }
    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> userData = new HashMap<String, String>();
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        return userData;
    }
    public boolean checkLogin(){
        if(userSession.getBoolean(IS_LOGGED, true)){
            return true;
        }else
            return false;
    }
    public void logout(){
        editor.clear();
        editor.commit();
    }

}
