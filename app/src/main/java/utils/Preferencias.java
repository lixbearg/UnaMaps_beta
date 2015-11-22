package utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lixbearg on 10/25/2015.
 */
public class Preferencias {

    public static final String PREF_ID = "geral";

    public static void setBoolean(Context context, String chave, boolean on) {
        SharedPreferences pref = context.getSharedPreferences(PREF_ID, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(chave, on);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String chave) {
        SharedPreferences pref = context.getSharedPreferences(PREF_ID, 0);
        boolean b = pref.getBoolean(chave, false);
        return b;
    }

    public static void setInteger(Context context, String chave, int valor) {
        SharedPreferences pref = context.getSharedPreferences(PREF_ID, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(chave, valor);
        editor.commit();
    }

    public static int getInteger(Context context, String chave) {
        SharedPreferences pref = context.getSharedPreferences(PREF_ID, 0);
        int i = pref.getInt(chave, 0);
        return i;
    }

    public static void clearPrefs(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_ID, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public static void setEstagioJogo(Context context, String codigo){

        switch (codigo){
            case "INICIO":
                setInteger(context, "ESTAGIO_JOGO", 0);
                break;
            case "OVO1":
                setInteger(context, "ESTAGIO_JOGO", 1);
                break;
            case "OVO2":
                setInteger(context, "ESTAGIO_JOGO", 2);
                break;
            case "OVO3":
                setInteger(context, "ESTAGIO_JOGO", 3);
                break;
            case "OVO4":
                setInteger(context, "ESTAGIO_JOGO", 4);
                break;
            case "OVO5":
                setInteger(context, "ESTAGIO_JOGO", 5);
                break;
            case "OVO6":
                setInteger(context, "ESTAGIO_JOGO", 6);
                break;
            default:
                setInteger(context, "ESTAGIO_JOGO", 0);
                break;
        }
    }
}