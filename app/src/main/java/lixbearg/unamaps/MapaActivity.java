package lixbearg.unamaps;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.qozix.layouts.ScalingLayout;
import com.qozix.tileview.TileView;
import com.qozix.tileview.hotspots.HotSpot;
import com.qozix.tileview.hotspots.HotSpotEventListener;
import com.qozix.tileview.markers.MarkerEventListener;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;
import utils.Alert;
import utils.AlertDialogs;
import utils.Audio;
import utils.CodeScanner;
import utils.Localidade;
import utils.LocalidadesDBAdapter;
import utils.MapaTileView;
import utils.Preferencias;
import utils.ShakeDetector;
import utils.TileViewActivity;

public class MapaActivity extends TileViewActivity {

    private TileView mapaUna;
    private ImageView marcadorUsuario;
    private ImageView marcadorLocalidade;
    private GifImageView marcadorSpark;
    private static final String TITLE_NORMAL = "UnaMaps Beta";
    private static final String TITLE_MAROTO = "UnaMaps do Maroto";
    private static final int LOCALIDADE_REQUEST = 1;
    private static final int SPEECH_REQUEST = 2;
    private static final String CONJURACAO_ABRIR = "eu juro solenemente não fazer nada de bom";
    private static final String CONJURACAO_FECHAR = "malfeito feito";
    private static final String PROMPT_ABRIR = "Você encontrou um mapa mágico criado por um grupo de alunos, mas ele está em branco...";
    private static final String PROMPT_FECHAR = "... tudo feito?";
    private static boolean MAROTO_ON = false;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;
    private ScalingLayout fogLayer;
    private static boolean MOSTRAUNA_ON = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Preferencias.getBoolean(this, "FIRST_RUN")) {

            Intent intent = new Intent(this, BetaHelpActivity.class);
            startActivity(intent);

            LocalidadesDBAdapter db = new LocalidadesDBAdapter(this);
            if (!db.carregarBase()) {
                Alert.alert(this, "Erro ao carregar base de dados!");
            }

            Preferencias.setBoolean(this, "FIRST_RUN", true);
            Preferencias.setEstagioJogo(this, "INICIO");
            Preferencias.setInteger(this, "PONTUACAO", 0);
        }

        setContentView(R.layout.activity_mapa);
        mapaUna = (TileView) findViewById(R.id.mapaUna);
        MapaTileView.criarMapa(mapaUna);
        MAROTO_ON = false;
        inicializarMarcadores();
        inicializarSensores();

        if (MOSTRAUNA_ON) {
            iniciarJogo();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
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
                break;
            case R.id.action_listaLocalidades:
                receberLocalidade();
                break;
            case R.id.action_help:
                Intent intentHelp = new Intent(this, BetaHelpActivity.class);
                startActivity(intentHelp);
                break;
            case R.id.action_sobre:
                AlertDialogs.exibirSobre(this);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        LocalidadesDBAdapter db = new LocalidadesDBAdapter(this);
        IntentResult resultadoScanner = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (requestCode == LOCALIDADE_REQUEST) {
            if (data != null) {
                Localidade localidade = db.selecionarQRCode(data.getStringExtra("QRCODE"));
                MapaTileView.adicionarMarcador(mapaUna, marcadorLocalidade, localidade);
                mapaUna.moveToAndCenter(localidade.coordX, localidade.coordY);
                Audio.tocarSFX(this, R.raw.pop);
            }
        } else if (requestCode == SPEECH_REQUEST) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> textoSpeech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (textoSpeech.contains(CONJURACAO_ABRIR) && !MAROTO_ON) {
                    this.setTitle(TITLE_MAROTO);
                    MAROTO_ON = true;
                    mapaUna = new TileView(this);
                    MapaTileView.criarMapaMaroto(mapaUna);
                    setContentView(mapaUna);
                    inicializarMarcadores();
                    inicializarSensores();
                    marcadorLocalidade.setImageResource(R.drawable.flag);
                    marcadorUsuario.setImageResource(R.drawable.footprints);
                    Audio.tocarSFX(this, R.raw.accept);
                } else if (textoSpeech.contains(CONJURACAO_FECHAR) && MAROTO_ON) {
                    this.setTitle(TITLE_NORMAL);
                    MAROTO_ON = false;
                    mapaUna = new TileView(this);
                    MapaTileView.criarMapa(mapaUna);
                    setContentView(mapaUna);
                    inicializarMarcadores();
                    inicializarSensores();
                    marcadorLocalidade.setImageResource(R.drawable.pin_red);
                    marcadorUsuario.setImageResource(R.drawable.pegman);
                    Audio.tocarSFX(this, R.raw.accept);
                } else {
                    Audio.tocarSFX(this, R.raw.error);
                }
            }
        } else {
            if (resultadoScanner != null) {
                if (resultadoScanner.getContents() == null) {
                    Alert.alert(this, "Cancelado");
                } else {
                    if (resultadoScanner.getContents().substring(0, 3).equals("OVO")) {
                        iniciarJogo();
                        AlertDialogs.exibirQuestionario(this, Integer.parseInt(resultadoScanner.getContents().substring(3)));
                        Preferencias.setEstagioJogo(this, resultadoScanner.getContents());
                        MapaTileView.criarFog(this, fogLayer);
                    } else if (db.selecionarLocalidade(resultadoScanner.getContents()) != null) {
                        Localidade localidade = db.selecionarLocalidade(resultadoScanner.getContents());
                        MapaTileView.adicionarMarcador(mapaUna, marcadorUsuario, localidade);
                        mapaUna.moveToAndCenter(localidade.coordX, localidade.coordY);
                        Audio.tocarSFX(this, R.raw.menu);
                    }
                }
            } else {
                Alert.alert(this, "Cancelado");
            }
        }
    }

    private MarkerEventListener markerEventListener = new MarkerEventListener() {
        @Override
        public void onMarkerTap(View v, int x, int y) {
            if (v.getTag() == "Usuário") {
                Alert.alert(getApplicationContext(), "Você está aqui!");
                mapaUna.moveToMarker(marcadorUsuario, true);
            }
        }
    };

    private HotSpotEventListener hotSpotEventListener = new HotSpotEventListener() {

        @Override
        public void onHotSpotTap(HotSpot hotSpot, int i, int i1) {
            inicializarVoiceRecognition();
        }
    };

    private void receberLocalidade() {
        Intent receberLocalidadeIntent = new Intent(this, ListaLocalidadesActivity.class);
        startActivityForResult(receberLocalidadeIntent, LOCALIDADE_REQUEST);
    }

    private void inicializarMarcadores() {

        marcadorUsuario = new ImageView(this);
        marcadorUsuario.setImageResource(R.drawable.pegman);
        marcadorUsuario.setTag("Usuário");

        marcadorLocalidade = new ImageView(this);
        marcadorLocalidade.setImageResource(R.drawable.pin_red);
        marcadorLocalidade.setTag("Localidade");

        marcadorSpark = new GifImageView(this);
        marcadorSpark.setImageResource(R.drawable.spark);
        marcadorLocalidade.setTag("Spark");
        MapaTileView.adicionarSpark(mapaUna, marcadorSpark, 2400, 2960);

        mapaUna.addMarkerEventListener(markerEventListener);

        MapaTileView.criarHotspot(mapaUna);
        mapaUna.addHotSpotEventListener(hotSpotEventListener);
    }

    private void inicializarSensores() {

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                if (count > 5) {
                    inicializarVoiceRecognition();
                }
            }
        });
    }

    private void inicializarVoiceRecognition() {

        Intent intentmaroto = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentmaroto.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "pt-BR");
        intentmaroto.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
        intentmaroto.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, true);
        if (!MAROTO_ON) {
            intentmaroto.putExtra(RecognizerIntent.EXTRA_PROMPT, PROMPT_ABRIR);
        } else {
            intentmaroto.putExtra(RecognizerIntent.EXTRA_PROMPT, PROMPT_FECHAR);
        }

        try {
            startActivityForResult(intentmaroto, SPEECH_REQUEST);
        } catch (ActivityNotFoundException a) {
            Alert.alert(getApplicationContext(), "Seu aparelho não suporta esta função, que pena :(");
        }
    }

    private void exibirPontuacao() {

        Button btnPontuacao = (Button) findViewById(R.id.btnPontuacao);
        btnPontuacao.setVisibility(View.VISIBLE);
        btnPontuacao.setText("x" + Preferencias.getInteger(this, "PONTUACAO"));
        btnPontuacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogs.exibirPontuacao(MapaActivity.this);
            }
        });
    }

    private void iniciarJogo() {

        exibirPontuacao();
        fogLayer = new ScalingLayout(this);
        mapaUna.addView(fogLayer);
        MapaTileView.criarFog(this, fogLayer);
        AlertDialogs.exibirTutorial(this);

    }

}
