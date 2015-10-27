package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Lixbearg on 10/25/2015.
 */
public class LocalidadesDBAdapter extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "localidadesdb";
    private static final int VERSAO_BANCO = 1;
    private static final String NOME_TABELA = "localidades";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_TIPO = "tipo";
    private static final String COLUNA_QRCODE = "qrcode";
    private static final String COLUNA_X = "coordX";
    private static final String COLUNA_Y = "coordY";
    private static final String CREATE_TABLE = "create table if not exists "+ NOME_TABELA +" (_id integer primary key autoincrement, "+ COLUNA_NOME +" varchar(50), "+ COLUNA_TIPO +" integer, "+ COLUNA_QRCODE +" varchar(10), "+ COLUNA_X +" integer, "+ COLUNA_Y +" integer);";
    private static final int TIPO_SALA = 1;
    private static final int TIPO_LAB = 2;
    private static final int TIPO_OUTROS = 3;
    private Context context;

    public LocalidadesDBAdapter(Context context){
        super(context, NOME_BANCO, null, VERSAO_BANCO);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            Alert.alert(context, "" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean carregarBase() {

        try {

            limparTabelaLocalidades();

            inserirLocalidades("Sala de Aula 301", TIPO_SALA, "S301", 505, 960);
            inserirLocalidades("Sala de Aula 304", TIPO_SALA, "S304", 780, 1835);
            inserirLocalidades("Sala de Aula 308", TIPO_SALA, "S308", 840, 1005);
            inserirLocalidades("Sala de Aula 309", TIPO_SALA, "S309", 840, 1260);
            inserirLocalidades("Sala de Aula 310", TIPO_SALA, "S310", 840, 1470);
            inserirLocalidades("Sala de Aula 313", TIPO_SALA, "S313", 1270, 1680);
            inserirLocalidades("Sala de Aula 314", TIPO_SALA, "S314", 1270, 1470);
            inserirLocalidades("Sala de Aula 315", TIPO_SALA, "S315", 1270, 1260);
            inserirLocalidades("Sala de Aula 316", TIPO_SALA, "S316", 1270, 1050);
            inserirLocalidades("Sala de Aula 320", TIPO_SALA, "S320", 1320, 1685);
            inserirLocalidades("Sala de Aula 321", TIPO_SALA, "S321", 1755, 1740);
            inserirLocalidades("Sala de Aula 322", TIPO_SALA, "S322", 1755, 1500);
            inserirLocalidades("Sala de Aula 326", TIPO_SALA, "S326", 5000, 5000);
            inserirLocalidades("Sala de Aula 327", TIPO_SALA, "S327", 5000, 5000);
            inserirLocalidades("Sala de Aula 330", TIPO_SALA, "S330", 5000, 5000);
            inserirLocalidades("Sala de Aula 331", TIPO_SALA, "S331", 5000, 5000);
            inserirLocalidades("Sala de Aula 333", TIPO_SALA, "S333", 5000, 5000);
            inserirLocalidades("Sala de Aula 334", TIPO_SALA, "S334", 5000, 5000);
            inserirLocalidades("Sala de Aula 335", TIPO_SALA, "S335", 5000, 5000);
            inserirLocalidades("Sala de Aula 336", TIPO_SALA, "S336", 5000, 5000);
            inserirLocalidades("Sala de Aula 337", TIPO_SALA, "S337", 5000, 5000);
            inserirLocalidades("Sala de Aula 338", TIPO_SALA, "S338", 5000, 5000);
            inserirLocalidades("Sala de Aula 339", TIPO_SALA, "S339", 5000, 5000);
            inserirLocalidades("Sala de Aula 340", TIPO_SALA, "S340", 5000, 5000);
            inserirLocalidades("Sala de Aula 341", TIPO_SALA, "S341", 5000, 5000);
            inserirLocalidades("Sala de Aula 342", TIPO_SALA, "S342", 5000, 5000);
            inserirLocalidades("Sala de Aula 345", TIPO_SALA, "S345", 5000, 5000);
            inserirLocalidades("Sala de Aula 346", TIPO_SALA, "S346", 5000, 5000);
            inserirLocalidades("Sala de Aula 347", TIPO_SALA, "S347", 5000, 5000);
            inserirLocalidades("Sala de Aula 348", TIPO_SALA, "S348", 5000, 5000);
            inserirLocalidades("Sala de Aula 349", TIPO_SALA, "S349", 5000, 5000);
            inserirLocalidades("Sala de Aula 350", TIPO_SALA, "S350", 5000, 5000);
            inserirLocalidades("Sala de Aula 351", TIPO_SALA, "S351", 5000, 5000);
            inserirLocalidades("Sala de Aula 352", TIPO_SALA, "S352", 5000, 5000);
            inserirLocalidades("Sala de Aula 353", TIPO_SALA, "S353", 5000, 5000);
            inserirLocalidades("Sala de Aula 354", TIPO_SALA, "S354", 5000, 5000);
            inserirLocalidades("Sala de Aula 355", TIPO_SALA, "S355", 5000, 5000);
            inserirLocalidades("Sala de Aula 356", TIPO_SALA, "S356", 5000, 5000);

            inserirLocalidades("Lab de Visagismo 305", TIPO_LAB, "L305", 475, 1817);
            inserirLocalidades("Lab de Anatomia 306", TIPO_LAB, "L306", 500, 1755);
            inserirLocalidades("Lab de Macas 307", TIPO_LAB, "L307", 500, 1600);
            inserirLocalidades("Lab de Informática 311", TIPO_LAB, "L311", 840, 1681);
            inserirLocalidades("Lab de Informática 312", TIPO_LAB, "L312", 890, 1960);
            inserirLocalidades("Lab de Física Elétrica 317", TIPO_LAB, "L317", 1325, 1045);
            inserirLocalidades("Lab de Informática 318", TIPO_LAB, "L318", 1325, 1261);
            inserirLocalidades("Lab de Informática 319", TIPO_LAB, "L319", 1325, 1475);
            inserirLocalidades("Lab de Informática 323", TIPO_LAB, "L323", 1755, 1265);
            inserirLocalidades("Lab de Informática 324", TIPO_LAB, "L324", 1755, 1110);
            inserirLocalidades("Lab de Informática 325", TIPO_LAB, "L325", 1755, 960);
            inserirLocalidades("Lab de Acionamentos 328", TIPO_LAB, "L328", 1810, 1265);
            inserirLocalidades("Lab de Física Eletromag. 329", TIPO_LAB, "L329", 1810, 1470);
            inserirLocalidades("Lab de Informática 332", TIPO_LAB, "L332", 2060, 1880);
            inserirLocalidades("Lab de Constr. Mecânica 343", TIPO_LAB, "L343", 360, 2000);
            inserirLocalidades("Lab Multidisciplinar 344", TIPO_LAB, "L344", 360, 2140);

            inserirLocalidades("Coordenação", TIPO_OUTROS, "CRD1", 780, 1170);
            inserirLocalidades("Núcleo de Sup. Informática", TIPO_OUTROS, "NSI1", 1155, 960);
            inserirLocalidades("Escada Rolante", TIPO_OUTROS, "ESR1", 2660, 900);
            inserirLocalidades("Escadas", TIPO_OUTROS, "ESC1", 270, 400);
            inserirLocalidades("Elevadores", TIPO_OUTROS, "ELV1", 2290, 1750);
            inserirLocalidades("Sanitários Oeste", TIPO_OUTROS, "SAN1", 410, 860);
            inserirLocalidades("Sanitários Leste", TIPO_OUTROS, "SAN2", 405, 1579);




        } catch (Exception e) {
            Alert.alert(context, "" + e);
        }
        return  true;
    }

    private void limparTabelaLocalidades() {

        SQLiteDatabase db = getWritableDatabase();
        try {
            String where = "name = ?";
            String[] whereArgs = new String[] {NOME_TABELA};
            db.delete(NOME_TABELA, null, null);
            db.delete("sqlite_sequence", where, whereArgs);

        } catch (Exception e){
            Alert.alert(context, "" + e);
        }
    }

    private void inserirLocalidades(String nome, int tipo, String qrcode, int x, int y){
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues localidade = new ContentValues();
            localidade.put(COLUNA_NOME, nome);
            localidade.put(COLUNA_TIPO, tipo);
            localidade.put(COLUNA_QRCODE, qrcode);
            localidade.put(COLUNA_X, x);
            localidade.put(COLUNA_Y, y);

            long id = db.insert("localidades", null, localidade);
            if (id < 0) {
                Alert.alert(context, "Falha ao inserir registro");
            }

        } finally {
            db.close();
        }
    }

    public Localidade selecionarLocalidade(String QRCode) {
        SQLiteDatabase db = getReadableDatabase();
        String[] colunas = {COLUNA_NOME, COLUNA_TIPO, COLUNA_X, COLUNA_Y};

        try {

            String where = COLUNA_QRCODE + " = ?";
            String[] whereArgs = new String[] {QRCode};

            Cursor cursor = db.query(NOME_TABELA, colunas, where, whereArgs, null, null, null);
            if (cursor.moveToNext()) {
                String nome = cursor.getString(cursor.getColumnIndex(COLUNA_NOME));
                int tipo = cursor.getInt(cursor.getColumnIndex(COLUNA_TIPO));
                int x = cursor.getInt(cursor.getColumnIndex(COLUNA_X));
                int y = cursor.getInt(cursor.getColumnIndex(COLUNA_Y));
                Localidade localidade = new Localidade(nome, tipo, QRCode, x, y);
                Alert.alert(context, "Você está aqui:\n" + nome);
                cursor.close();
                return localidade;
            }
        } catch (Exception e){
            Alert.alert(context, "" + e);
        }
        Alert.alert(context, "QRCode inválido!");
        return null;
    }

    public Localidade selecionarQRCode(String nome) {
        SQLiteDatabase db = getReadableDatabase();
        String[] colunas = {COLUNA_QRCODE, COLUNA_TIPO, COLUNA_X, COLUNA_Y};

        try {

            String where = COLUNA_NOME + " = ?";
            String[] whereArgs = new String[] {nome};

            Cursor cursor = db.query(NOME_TABELA, colunas, where, whereArgs, null, null, null);
            if (cursor.moveToNext()) {
                String QRCode = cursor.getString(cursor.getColumnIndex(COLUNA_QRCODE));
                int tipo = cursor.getInt(cursor.getColumnIndex(COLUNA_TIPO));
                int x = cursor.getInt(cursor.getColumnIndex(COLUNA_X));
                int y = cursor.getInt(cursor.getColumnIndex(COLUNA_Y));
                Localidade localidade = new Localidade(nome, tipo, QRCode, x, y);
                Alert.alert(context, nome);
                cursor.close();
                return localidade;
            }
        } catch (Exception e){
            Alert.alert(context, "" + e);
        }
        return null;
    }

    public String[] selecionarHeadings() {
        return new String[] {"Salas", "Labs", "Outros"};
    }

    public String[] selecionarSalas(int tipoSala) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> salas = new ArrayList<>();
        String[] colunas = {COLUNA_NOME};

        try {

            String where = COLUNA_TIPO + " = ?";
            String[] whereArgs = new String[] {String.valueOf(tipoSala)};

            Cursor cursor = db.query(NOME_TABELA, colunas, where, whereArgs, null, null, null);
            while (cursor.moveToNext()) {
                salas.add(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
            }
            cursor.close();
            return salas.toArray(new String[salas.size()]);

        } catch (Exception e){
            Alert.alert(context, "" + e);
        }
        return null;
    }
}
