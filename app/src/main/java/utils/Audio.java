package utils;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Lixbearg on 10/29/2015.
 */
public class Audio {

    public static void tocarSFX(Context context, int arquivoAudio) {

        MediaPlayer mp = MediaPlayer.create(context, arquivoAudio);
        mp.start();

    }

}
