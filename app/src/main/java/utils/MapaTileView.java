package utils;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Region;
import android.widget.ImageView;

import com.qozix.layouts.ScalingLayout;
import com.qozix.tileview.TileView;
import com.qozix.tileview.hotspots.HotSpot;

import lixbearg.unamaps.R;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Lixbearg on 10/24/2015.
 */
public class MapaTileView {

    public static void criarMapa(TileView tileView) {

        tileView.setTransitionsEnabled(true);
        tileView.setSize(4000, 3500);
        tileView.addDetailLevel(1f, "tile-%col%_%row%.jpg", "samples/MapsSample.jpg");
        tileView.defineRelativeBounds(0, 0, 4000, 3500);
        tileView.setMarkerAnchorPoints(-0.5f, -0.5f);
        tileView.setScale(0);

    }

    public static void adicionarMarcador(TileView tileView, ImageView imageView, Localidade localidade) {

        tileView.removeMarker(imageView);
        tileView.addMarker(imageView, localidade.coordX, localidade.coordY);
        tileView.setScale(2);
    }

    public static void adicionarSpark(TileView tileView, GifImageView gifImageView, int X, int Y) {

        tileView.addMarker(gifImageView, X, Y);
    }

    public static void criarMapaMaroto(TileView tileView) {

        tileView.setTransitionsEnabled(true);
        tileView.setSize(4000, 3500);
        tileView.addDetailLevel(1f, "tile-maroto-%col%_%row%.jpg", "samples/MapsSample_maroto.jpg");
        tileView.defineRelativeBounds(0, 0, 4000, 3500);
        tileView.setMarkerAnchorPoints(-0.5f, -0.5f);
        tileView.setScale(0);

    }

    public static void criarHotspot(TileView tileView) {

        Path path = new Path();
        path.moveTo(2320, 2920);
        path.lineTo(2486, 2920);
        path.lineTo(2486, 3000);
        path.lineTo(2320, 3000);

        Region region = new Region();
        region.setPath(path, new Region(0, 0, 4000, 3500));

        HotSpot hotspot = new HotSpot(region);
        hotspot.setTag(0);
        tileView.addHotSpot(hotspot);

    }

    public static void criarFog(Context context, ScalingLayout fogLayer) {

        switch (Preferencias.getInteger(context, "ESTAGIO_JOGO")){
            case 0:
                fogLayer.setBackgroundResource(R.drawable.fog1);
                break;
            case 1:
                fogLayer.setBackgroundResource(R.drawable.fog1);
                break;
            case 2:
                fogLayer.setBackgroundResource(R.drawable.fog2);
                break;
            case 3:
                fogLayer.setBackgroundResource(R.drawable.fog3);
                break;
            case 4:
                fogLayer.setBackgroundResource(R.drawable.fog4);
                break;
            case 5:
                fogLayer.setBackgroundResource(R.drawable.fog5);
                break;
            case 6:
                fogLayer.setBackgroundResource(R.drawable.nofog);
                break;
            default:
                fogLayer.setBackgroundResource(R.drawable.fog1);
                break;
        }
    }
}
