package lixbearg.unamaps;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.qozix.tileview.TileView;
import com.qozix.tileview.markers.MarkerEventListener;

import utils.Alert;
import utils.CodeScanner;
import utils.Localidade;
import utils.LocalidadesDBAdapter;
import utils.MapaTileView;
import utils.Preferencias;
import utils.TileViewActivity;

public class MapaActivity extends TileViewActivity {

    private TileView mapaUna;
    private ImageView marcadorUsuario;
    private ImageView marcadorLocalidade;
    private final int LOCALIDADE_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Preferencias.getBoolean(this, "FIRST_RUN")) {

            Alert.alert(this, "Primeira utilização!");
            LocalidadesDBAdapter db = new LocalidadesDBAdapter(this);
            if (db.carregarBase()) {
                Alert.alert(this, "Base carregada!");
            } else {
                Alert.alert(this, "Erro ao carregar base de dados!");
            }

            //Intent intent = new Intent(this, WelcomeActivity.class);
            //startActivity(intent);

            Preferencias.setBoolean(this, "FIRST_RUN", true);
        }

        mapaUna = new TileView(this);
        MapaTileView.criarMapa(mapaUna);
        setContentView(mapaUna);
        mapaUna.addMarkerEventListener(markerEventListener);

        marcadorUsuario = new ImageView(this);
        marcadorUsuario.setImageResource(R.drawable.pegman);
        marcadorUsuario.setTag("Usuário");

        marcadorLocalidade = new ImageView(this);
        marcadorLocalidade.setImageResource(R.drawable.pin_red);
        marcadorLocalidade.setTag("Localidade");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mapa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case (R.id.action_lerqrcode):
                CodeScanner.lerQrCode(this);
                return true;
            case R.id.action_listaLocalidades:
                receberLocalidade();
                return true;
            case R.id.action_resetarprefs:
                Preferencias.setBoolean(this, "FIRST_RUN", false);
                Alert.alert(this, "First run resetado");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        LocalidadesDBAdapter db = new LocalidadesDBAdapter(this);
        IntentResult resultadoScanner = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (requestCode == LOCALIDADE_REQUEST) {
            if (data != null){
                Localidade localidade = db.selecionarQRCode(data.getStringExtra("QRCODE"));
                MapaTileView.adicionarMarcador(mapaUna, marcadorLocalidade, localidade);
                mapaUna.moveToAndCenter(localidade.coordX, localidade.coordY);
            }
        } else {
            if (resultadoScanner != null) {
                if (resultadoScanner.getContents() == null) {
                    Alert.alert(this, "Cancelado");
                } else {
                    if (db.selecionarLocalidade(resultadoScanner.getContents()) != null) {
                        Localidade localidade = db.selecionarLocalidade(resultadoScanner.getContents());
                        MapaTileView.adicionarMarcador(mapaUna, marcadorUsuario, localidade);
                        mapaUna.moveToAndCenter(localidade.coordX, localidade.coordY);
                    }
                }
            } else {
                Alert.alert(this, "Cancelado");
            }
        }
    }

    private MarkerEventListener markerEventListener = new MarkerEventListener() {
        @Override
        public void onMarkerTap( View v, int x, int y ) {
            Alert.alert(getApplicationContext(), "Você está aqui!");
            mapaUna.moveToMarker(marcadorUsuario, true);
        }
    };

    private void receberLocalidade(){
        Intent receberLocalidadeIntent = new Intent(this, ListaLocalidadesActivity.class);
        startActivityForResult(receberLocalidadeIntent, LOCALIDADE_REQUEST);
    }
}
