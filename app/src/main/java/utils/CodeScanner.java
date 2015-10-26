package utils;

import android.app.Activity;

import com.google.zxing.integration.android.IntentIntegrator;

/**
 * Created by Lixbearg on 10/25/2015.
 */
public class CodeScanner {

    public static void lerQrCode(Activity activity){
        IntentIntegrator scanIntegrator = new IntentIntegrator(activity);
        scanIntegrator.setPrompt("Centralize o QRCode no leitor");
        scanIntegrator.setOrientationLocked(false);
        scanIntegrator.initiateScan();
    }
}