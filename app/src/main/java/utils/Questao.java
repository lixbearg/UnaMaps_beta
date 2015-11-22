package utils;

/**
 * Created by Lixbearg on 11/8/2015.
 */
public class Questao {

    public int numeroQuestao;
    public String enunciado;
    public String opcao1;
    public String opcao2;
    public String opcao3;
    public String opcao4;
    public int opcaoCorreta;

    public Questao(int numeroQuestao){

        this.numeroQuestao = numeroQuestao;

        this.enunciado = "Qual fruta é ressecada para se tornar uma ameixa seca?";

        this.opcao1 = "Ameixa";
        this.opcao2 = "Uva";
        this.opcao3 = "Pêssego";
        this.opcao4 = "Melão";

        this.opcaoCorreta = 1;

    }

}
