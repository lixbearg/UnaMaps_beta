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
import java.util.Random;

import utils.Alert;
import utils.AlertDialogs;
import utils.Audio;
import utils.Callout;
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
    private ImageView marcadorSpark;
    private static final String TITLE_NORMAL = "UnaMaps Beta";
    private static final String TITLE_MAROTO = "UnaMaps do Maroto";
    private static final int LOCALIDADE_REQUEST = 1;
    private static final int SPEECH_REQUEST = 2;
    private static final String CONJURACAO_ABRIR = "eu juro solenemente não fazer nada de bom";
    private static final String CONJURACAO_FECHAR = "malfeito feito";
    private static final String PROMPT_ABRIR = "Você encontrou um jovem com um mapa em branco nas mãos. " +
            "Segundo ele, é preciso dizer algumas palavras mágicas para que seu conteúdo seja revelado...";
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

            AlertDialogs.exibirTutorial(this);

        }

        setContentView(R.layout.activity_mapa);
        mapaUna = (TileView) findViewById(R.id.mapaUna);
        MapaTileView.criarMapa(mapaUna);
        MAROTO_ON = false;

        if (Preferencias.getEstagioJogo(this) < 6) {
            iniciarJogo();
            MOSTRAUNA_ON = true;
        } else {
            MOSTRAUNA_ON = false;
        }

        inicializarMarcadores();
        inicializarSensores();
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
                    if (resultadoScanner.getContents().substring(0, 3).equals("FOG")) {
                        if (!resultadoScanner.getContents().endsWith(String.valueOf(Preferencias.getInteger(this, "ESTAGIO_JOGO") + 1))) {
                            Alert.alert(this, "Ops! Esse não é o código que estamos procurando!");
                        } else if (resultadoScanner.getContents().endsWith("6")) {
                            Preferencias.setBoolean(this, "UMASTER", true);
                            encerrarJogo();
                        } else {
                        Preferencias.setEstagioJogo(this, resultadoScanner.getContents());
                        AlertDialogs.exibirDica(this);
                        MapaTileView.criarFog(this, fogLayer);
                        atualizarPontuacao();
                        }
                        Localidade localidade = db.selecionarLocalidade(resultadoScanner.getContents());
                        MapaTileView.adicionarMarcador(mapaUna, marcadorUsuario, localidade);
                        mapaUna.moveToAndCenter(localidade.coordX, localidade.coordY);
                        Audio.tocarSFX(this, R.raw.menu);
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
            } else if (v.getTag() == "Spark") {
                inicializarVoiceRecognition();
            }
        }
    };

    private HotSpotEventListener hotSpotEventListener = new HotSpotEventListener() {

        @Override
        public void onHotSpotTap(HotSpot hotSpot, int i, int i1) {

            // Preguiça de fazer SWITCH CASE com string...
            if (hotSpot.getTag() ==  "Maroto") {
                inicializarVoiceRecognition();
            } else if (hotSpot.getTag() == "NSI"){
                criarCallout(1052, 1033, -0.5f, 0f, "NSI", "Núcleo de Suporte à Informática. Responsável pela área de tecnologia, comunicação e infraestrutura de T.I. do campus.");
            } else if (hotSpot.getTag() == "ESR"){
                criarCallout(2660, 776, -0.5f, 0, "Escadas rolantes", "Acesso ao estacionamento, rua Benedito dos Santos e ViaShopping.");
            } else if (hotSpot.getTag() == "ESC"){
                criarCallout(200, 440, 0f, 0f, "Escadas", "Acesso ao segundo piso do campus.");
            } else if (hotSpot.getTag() == "ELV"){
                criarCallout(2411, 1826, -0.5f, 0f, "Elevadores", "Acesso aos 5 pisos do prédio: Viabrasil, lojas, estacionamento, Una.");

            } else if (hotSpot.getTag() == "Mesas1"){
                criarCallout(228, 755, -0f, 0, "Mesas", "Área de convivência do campus. Utilizado para estudo e confraternização entre alunos e convidados.");
            } else if (hotSpot.getTag() == "Mesas2"){
                criarCallout(1731, 908, -0.5f, 0f, "Mesas", "Área de convivência do campus. Utilizado para estudo e confraternização entre alunos e convidados.");
            } else if (hotSpot.getTag() == "Mesas3") {
                criarCallout(340, 1994, -0f, 0f, "Mesas", "Área de convivência do campus. Utilizado para estudo e confraternização entre alunos e convidados.");
            }  else if (hotSpot.getTag() == "Info"){
                criarCallout(2407, 1996, -0.5f, 0, "Bancada de informações", "Disponibiliza informações referentes à localização no campus." +
                        "Os professores à utilizam para pegar as chaves das salas e o controle dos seus respectivos aparelhos de ar-condicionado.\n" +
                        "Funciona também como achados e perdidos.");
            } else if (hotSpot.getTag() == "Carregadores"){
                criarCallout(2240, 2111, -0.5f, 0f, "Carregadores", "Totem com vários conectores que possibilitam o carregamento de aparelhos celulares.");
            } else if (hotSpot.getTag() == "Impressoras") {
                criarCallout(1572, 980, -0.5f, 0f, "Impressoras", "Através do RA e senha do aluno, possibilita a impressão de documentos contidos na fila de espera da rede.");
            } else if (hotSpot.getTag() == "Banheiro1"){
                criarCallout(197, 964, -0f, 0f, "Sanitários", "Área de higiene pessoal.");
            } else if (hotSpot.getTag() == "Banheiro2") {
                criarCallout(215, 1706, -0f, 0f, "Sanitários", "Área de higiene pessoal.");
            }
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

        inicializarSpark();

        mapaUna.addMarkerEventListener(markerEventListener);

//        MapaTileView.criarHotspotMaroto(mapaUna);
        MapaTileView.criarHotspotNSI(mapaUna);
        MapaTileView.criarHotspotEscada(mapaUna);
        MapaTileView.criarHotspotEscadaRolante(mapaUna);
        MapaTileView.criarHotspotElevadores(mapaUna);
        MapaTileView.criarHotspotInformacoes(mapaUna);
        mapaUna.addHotSpotEventListener(hotSpotEventListener);
    }

    private void inicializarSpark() {

        if (!MOSTRAUNA_ON) {
            marcadorSpark = new ImageView(this);
            marcadorSpark.setImageResource(R.drawable.harry);
            marcadorSpark.setTag("Spark");
            MapaTileView.adicionarSpark(mapaUna, marcadorSpark, 2700, 3300);

            if (Preferencias.getBoolean(this, "UMASTER")) {
                marcadorUsuario.setImageResource(R.drawable.pegmanking);
            }
        }
    }

    private void inicializarSensores() {

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                if (count > 4) {
                    if (MOSTRAUNA_ON) {
                        encerrarJogo();
                        Alert.alert(getApplicationContext(), "HEY! Isso não vale! :(");
                    } else {
                        String[] array = getApplicationContext().getResources().getStringArray(R.array.woop);
                        String randomStr = array[new Random().nextInt(array.length)];
                        Alert.alert(getApplicationContext(), randomStr);
                    }
                    Audio.tocarSFX(getApplicationContext(), R.raw.woop);
                }
            }
        });
    }

    private void inicializarVoiceRecognition() {

        Intent intentmaroto = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentmaroto.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "pt-BR");
        intentmaroto.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "pt-BR");
        intentmaroto.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, true);
        intentmaroto.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 10000);
        intentmaroto.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);
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
        btnPontuacao.setText("x" + Preferencias.getInteger(this, "ESTAGIO_JOGO"));
        btnPontuacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogs.exibirPontuacao(MapaActivity.this);
            }
        });
    }

    private void atualizarPontuacao() {

        Button btnPontuacao = (Button) findViewById(R.id.btnPontuacao);
        btnPontuacao.setText("x" + Preferencias.getInteger(this, "ESTAGIO_JOGO"));

    }

    private void iniciarJogo() {

        exibirPontuacao();
        fogLayer = new ScalingLayout(this);
        mapaUna.addView(fogLayer, 2);
        MapaTileView.criarFog(this, fogLayer);

    }

    private void encerrarJogo() {

        Button btnPontuacao = (Button) findViewById(R.id.btnPontuacao);
        btnPontuacao.setVisibility(View.GONE);
        Preferencias.setEstagioJogo(this, "FOG6");
        MapaTileView.criarFog(this, fogLayer);
        MOSTRAUNA_ON = false;
        inicializarSpark();
        AlertDialogs.exibirFinal(this);

    }

    private void criarCallout(double X, double Y, float anchorX, float anchorY, String titulo, String texto) {

        Callout callout = new Callout(getApplicationContext());
        mapaUna.addCallout(callout, X, Y, anchorX, anchorY);
        callout.transitionIn();
        callout.setTitle(titulo);
        callout.setSubtitle(texto);
        if (anchorX == 0 && anchorY == 0){
            mapaUna.moveTo((X - 100), (Y - 500));
        } else {
            mapaUna.moveToAndCenter(X, Y);
        }
    }
}
