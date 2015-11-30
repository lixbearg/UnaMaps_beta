package utils;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Region;
import android.widget.ImageView;

import com.qozix.layouts.ScalingLayout;
import com.qozix.tileview.TileView;
import com.qozix.tileview.hotspots.HotSpot;

import lixbearg.unamaps.R;

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

    public static void adicionarSpark(TileView tileView, ImageView imageView, int X, int Y) {

        tileView.addMarker(imageView, X, Y);
    }

    public static void criarMapaMaroto(TileView tileView) {

        tileView.setTransitionsEnabled(true);
        tileView.setSize(4000, 3500);
        tileView.addDetailLevel(1f, "tile-maroto-%col%_%row%.jpg", "samples/MapsSample_maroto.jpg");
        tileView.defineRelativeBounds(0, 0, 4000, 3500);
        tileView.setMarkerAnchorPoints(-0.5f, -0.5f);
        tileView.setScale(0);

    }

    public static void criarHotspotInformacoes(TileView tileView) {

        Path path = new Path();
        Region region = new Region();


        path.moveTo(220, 626);
        path.lineTo(340, 626);
        path.lineTo(340, 730);
        path.lineTo(220, 760);
        region = new Region();
        region.setPath(path, new Region(0, 0, 4000, 3500));

        HotSpot hotspot = new HotSpot(region);
        hotspot.setTag("Mesas1");
        tileView.addHotSpot(hotspot);

        path = new Path();
        path.moveTo(1675, 767);
        path.lineTo(1790, 767);
        path.lineTo(1790, 880);
        path.lineTo(1675, 880);
        region = new Region();
        region.setPath(path, new Region(0, 0, 4000, 3500));

        hotspot = new HotSpot(region);
        hotspot.setTag("Mesas2");
        tileView.addHotSpot(hotspot);

        path = new Path();
        path.moveTo(342, 1870);
        path.lineTo(450, 1870);
        path.lineTo(450, 1980);
        path.lineTo(342, 1980);
        region = new Region();
        region.setPath(path, new Region(0, 0, 4000, 3500));

        hotspot = new HotSpot(region);
        hotspot.setTag("Mesas3");
        tileView.addHotSpot(hotspot);

        path = new Path();
        path.moveTo(2350, 1865);
        path.lineTo(2455, 1865);
        path.lineTo(2455, 1975);
        path.lineTo(2350, 1975);
        region = new Region();
        region.setPath(path, new Region(0, 0, 4000, 3500));

        hotspot = new HotSpot(region);
        hotspot.setTag("Info");
        tileView.addHotSpot(hotspot);

        path = new Path();
        path.moveTo(2177, 1975);
        path.lineTo(2295, 1975);
        path.lineTo(2295, 2090);
        path.lineTo(2177, 2090);
        region = new Region();
        region.setPath(path, new Region(0, 0, 4000, 3500));

        hotspot = new HotSpot(region);
        hotspot.setTag("Carregadores");
        tileView.addHotSpot(hotspot);

        path = new Path();
        path.moveTo(1516, 848);
        path.lineTo(1628, 848);
        path.lineTo(1628, 960);
        path.lineTo(1516, 960);
        region = new Region();
        region.setPath(path, new Region(0, 0, 4000, 3500));

        hotspot = new HotSpot(region);
        hotspot.setTag("Impressoras");
        tileView.addHotSpot(hotspot);

        path = new Path();
        path.moveTo(201, 827);
        path.lineTo(264, 789);
        path.lineTo(325, 827);
        path.lineTo(325, 897);
        path.lineTo(264, 933);
        path.lineTo(201, 897);
        region = new Region();
        region.setPath(path, new Region(0, 0, 4000, 3500));

        hotspot = new HotSpot(region);
        hotspot.setTag("Banheiro1");
        tileView.addHotSpot(hotspot);

        path = new Path();
        path.moveTo(216, 1565);
        path.lineTo(280, 1529);
        path.lineTo(341, 1565);
        path.lineTo(341, 1636);
        path.lineTo(280, 1673);
        path.lineTo(216, 1635);
        region = new Region();
        region.setPath(path, new Region(0, 0, 4000, 3500));

        hotspot = new HotSpot(region);
        hotspot.setTag("Banheiro2");
        tileView.addHotSpot(hotspot);
    }

    public static void criarHotspotNSI(TileView tileView) {

        Path path = new Path();
        path.moveTo(860, 922);
        path.lineTo(1250, 922);
        path.lineTo(1250, 1005);
        path.lineTo(1055, 1005);
        path.lineTo(1055, 965);
        path.lineTo(860, 965);

        Region region = new Region();
        region.setPath(path, new Region(0, 0, 4000, 3500));

        HotSpot hotspot = new HotSpot(region);
        hotspot.setTag("NSI");
        tileView.addHotSpot(hotspot);

    }

    public static void criarHotspotEscada(TileView tileView) {

        Path path = new Path();
        path.moveTo(313, 158);
        path.lineTo(406, 104);
        path.lineTo(500, 158);
        path.lineTo(500, 265);
        path.lineTo(406, 317);
        path.lineTo(313, 265);

        Region region = new Region();
        region.setPath(path, new Region(0, 0, 4000, 3500));

        HotSpot hotspot = new HotSpot(region);
        hotspot.setTag("ESC");
        tileView.addHotSpot(hotspot);

    }

    public static void criarHotspotEscadaRolante(TileView tileView) {

        Path path = new Path();
        path.moveTo(2410, 480);
        path.lineTo(2500, 425);
        path.lineTo(2590, 480);
        path.lineTo(2590, 585);
        path.lineTo(2500, 640);
        path.lineTo(2410, 585);

        Region region = new Region();
        region.setPath(path, new Region(0, 0, 4000, 3500));

        HotSpot hotspot = new HotSpot(region);
        hotspot.setTag("ESR");
        tileView.addHotSpot(hotspot);

    }

    public static void criarHotspotElevadores(TileView tileView) {

        Path path = new Path();
        path.moveTo(2314, 1418);
        path.lineTo(2407, 1364);
        path.lineTo(2499, 1418);
        path.lineTo(2499, 1522);
        path.lineTo(2407, 1578);
        path.lineTo(2314, 1522);

        Region region = new Region();
        region.setPath(path, new Region(0, 0, 4000, 3500));

        HotSpot hotspot = new HotSpot(region);
        hotspot.setTag("ELV");
        tileView.addHotSpot(hotspot);

    }

    public static void criarFog(Context context, ScalingLayout fogLayer) {

        switch (Preferencias.getInteger(context, "ESTAGIO_JOGO")){
            case 0:
                fogLayer.setBackgroundResource(R.drawable.fog1);
                break;
            case 1:
                fogLayer.setBackgroundResource(R.drawable.fog2);
                break;
            case 2:
                fogLayer.setBackgroundResource(R.drawable.fog3);
                break;
            case 3:
                fogLayer.setBackgroundResource(R.drawable.fog4);
                break;
            case 4:
                fogLayer.setBackgroundResource(R.drawable.fog5);
                break;
            case 5:
                fogLayer.setBackgroundResource(R.drawable.fog6);
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
