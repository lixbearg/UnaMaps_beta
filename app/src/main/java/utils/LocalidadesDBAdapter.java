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
            Alert.alert(context, "Banco criado!");
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

            inserirLocalidades("Sala de Aula 301", TIPO_SALA, "S301", 2483, 2353);
            inserirLocalidades("Sala de Aula 304", TIPO_SALA, "S304", 2483, 2353);
            inserirLocalidades("Sala de Aula 308", TIPO_SALA, "S308", 2483, 2353);
            inserirLocalidades("Sala de Aula 309", TIPO_SALA, "S309", 2483, 2353);
            inserirLocalidades("Sala de Aula 310", TIPO_SALA, "S310", 2483, 2353);
            inserirLocalidades("Sala de Aula 313", TIPO_SALA, "S313", 2483, 2353);
            inserirLocalidades("Sala de Aula 314", TIPO_SALA, "S314", 2483, 2353);
            inserirLocalidades("Sala de Aula 315", TIPO_SALA, "S315", 2483, 2353);
            inserirLocalidades("Sala de Aula 316", TIPO_SALA, "S316", 2483, 2353);
            inserirLocalidades("Sala de Aula 320", TIPO_SALA, "S320", 2483, 2353);
            inserirLocalidades("Sala de Aula 321", TIPO_SALA, "S321", 2483, 2353);
            inserirLocalidades("Sala de Aula 322", TIPO_SALA, "S322", 2483, 2353);
            inserirLocalidades("Sala de Aula 326", TIPO_SALA, "S326", 2483, 2353);
            inserirLocalidades("Sala de Aula 327", TIPO_SALA, "S327", 2483, 2353);
            inserirLocalidades("Sala de Aula 330", TIPO_SALA, "S330", 2483, 2353);
            inserirLocalidades("Sala de Aula 331", TIPO_SALA, "S331", 2483, 2353);
            inserirLocalidades("Sala de Aula 333", TIPO_SALA, "S333", 2483, 2353);
            inserirLocalidades("Sala de Aula 334", TIPO_SALA, "S334", 2483, 2353);
            inserirLocalidades("Sala de Aula 335", TIPO_SALA, "S335", 2483, 2353);
            inserirLocalidades("Sala de Aula 336", TIPO_SALA, "S336", 2483, 2353);
            inserirLocalidades("Sala de Aula 337", TIPO_SALA, "S337", 2483, 2353);
            inserirLocalidades("Sala de Aula 338", TIPO_SALA, "S338", 2483, 2353);
            inserirLocalidades("Sala de Aula 339", TIPO_SALA, "S339", 2483, 2353);
            inserirLocalidades("Sala de Aula 340", TIPO_SALA, "S340", 2483, 2353);
            inserirLocalidades("Sala de Aula 341", TIPO_SALA, "S341", 2483, 2353);
            inserirLocalidades("Sala de Aula 342", TIPO_SALA, "S342", 2483, 2353);
            inserirLocalidades("Sala de Aula 345", TIPO_SALA, "S345", 2483, 2353);
            inserirLocalidades("Sala de Aula 346", TIPO_SALA, "S346", 2483, 2353);
            inserirLocalidades("Sala de Aula 347", TIPO_SALA, "S347", 2483, 2353);
            inserirLocalidades("Sala de Aula 348", TIPO_SALA, "S348", 2483, 2353);
            inserirLocalidades("Sala de Aula 349", TIPO_SALA, "S349", 2483, 2353);
            inserirLocalidades("Sala de Aula 350", TIPO_SALA, "S350", 2483, 2353);
            inserirLocalidades("Sala de Aula 351", TIPO_SALA, "S351", 2483, 2353);
            inserirLocalidades("Sala de Aula 352", TIPO_SALA, "S352", 2483, 2353);
            inserirLocalidades("Sala de Aula 353", TIPO_SALA, "S353", 2483, 2353);
            inserirLocalidades("Sala de Aula 354", TIPO_SALA, "S354", 2483, 2353);
            inserirLocalidades("Sala de Aula 355", TIPO_SALA, "S355", 2483, 2353);
            inserirLocalidades("Sala de Aula 356", TIPO_SALA, "S356", 2483, 2353);

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

            inserirLocalidades("Coordenação", TIPO_OUTROS, "CRD1", 2598, 933);
            inserirLocalidades("Escada Rolante", TIPO_OUTROS, "ESR1", 2598, 933);
            inserirLocalidades("Escadas", TIPO_OUTROS, "ESC1", 2598, 933);
            inserirLocalidades("Elevadores", TIPO_OUTROS, "ELV1", 2598, 933);
            inserirLocalidades("Sanitários Oeste", TIPO_OUTROS, "SAN1", 2598, 933);
            inserirLocalidades("Sanitários Leste", TIPO_OUTROS, "SAN2", 2598, 933);




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

        Alert.alert(context, "Limpando tabelas");
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
                return localidade;
            }
        } catch (Exception e){
            Alert.alert(context, "" + e);
        }
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
                return localidade;
            }
        } catch (Exception e){
            Alert.alert(context, "" + e);
        }
        return null;
    }

    public String[] selecionarHeadings() {
        String[] headings = new String[] {"Salas", "Labs", "Outros"};
        return headings;
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
            return salas.toArray(new String[salas.size()]);

        } catch (Exception e){
            Alert.alert(context, "" + e);
        }
        return null;
    }
}
