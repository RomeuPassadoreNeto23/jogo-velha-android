package br.senai.sp.cotia.jogodavelhaapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class PrefsUtil {
    public static void salvarSimbolojogado1(String simbolo, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("simb_jog_1",simbolo);
        editor.commit();
    }
    public  static  String getSimbolojogo1(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return  preferences.getString("simb_jogo_1","x");

    }
    public  static  String getSimbolojogo2(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return  preferences.getString("simb_jogo_2","o");

    }
}
