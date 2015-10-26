package utils;

/**
 * Created by Lixbearg on 10/25/2015.
 */
public class Localidade {

    public String nome;
    public int tipo;
    public String QRCode;
    public int coordX;
    public int coordY;

    public Localidade(String nome, int tipo, String QRCode, int x, int y) {

        this.nome = nome;
        this.tipo = tipo;
        this.QRCode = QRCode;
        this.coordX = x;
        this.coordY = y;

    }

}
