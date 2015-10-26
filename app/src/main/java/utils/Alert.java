package utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Lixbearg on 10/25/2015.
 */
public class Alert {

    public static void alert(Context context, String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }
}
