package utils;

import android.widget.ImageView;

import com.qozix.tileview.TileView;

/**
 * Created by Lixbearg on 10/24/2015.
 */
public class MapaTileView {

    public static void criarMapa(TileView tileView) {

        tileView.setTransitionsEnabled(true);
        tileView.setSize(4000, 3500);
        tileView.addDetailLevel(1f, "tile-%col%_%row%.jpg", "samples/MapsSample.jpg");
        tileView.defineRelativeBounds(0, 0, 4000, 3500);
        tileView.setMarkerAnchorPoints(-0.5f, -1.0f);
        tileView.setScale(0);

    }

    public static void adicionarMarcador(TileView tileView, ImageView imageView, Localidade localidade) {

        tileView.removeMarker(imageView);
        tileView.addMarker(imageView, localidade.coordX, localidade.coordY);
        tileView.setScale(2);
    }


}
