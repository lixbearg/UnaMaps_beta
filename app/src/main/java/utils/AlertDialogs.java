package utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import lixbearg.unamaps.R;

/**
 * Created by Lixbearg on 11/8/2015.
 */
public class AlertDialogs {

    public static void exibirTutorial(Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.tutorial_titulo);
        builder.setMessage(R.string.tutorial_texto);
        builder.setPositiveButton("Entendi!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public static void exibirQuestionario(final Context context, int numeroQuestao){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_questionario, null);

        Questao questao = new Questao(numeroQuestao);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_menu_help);
        builder.setTitle("Questão " + String.valueOf(numeroQuestao));
        builder.setMessage(questao.enunciado);
//        builder.setCancelable(false);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final Button resposta1 = (Button) view.findViewById (R.id.btnResposta1);
        Button resposta2 = (Button) view.findViewById (R.id.btnResposta2);
        final Button resposta3 = (Button) view.findViewById (R.id.btnResposta3);
        Button resposta4 = (Button) view.findViewById (R.id.btnResposta4);

        resposta1.setText(questao.opcao1);
        resposta2.setText(questao.opcao2);
        resposta3.setText(questao.opcao3);
        resposta4.setText(questao.opcao4);

        resposta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert.alert(context, "1!");
            }
        });

        resposta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert.alert(context, "2!");
            }
        });

        resposta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resposta3.setBackgroundColor(Color.RED);
                resposta1.setBackgroundColor(Color.GREEN);
                Preferencias.setInteger(context, "PONTUACAO", (Preferencias.getInteger(context, "PONTUACAO") + 1));
//                dialog.dismiss();
            }
        });

        resposta4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert.alert(context, "4!");
            }
        });
    }

    public static void exibirPontuacao(final Context context){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_pontuacao, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        Resources res = context.getResources();
        TextView txtPontuacao = (TextView) view.findViewById (R.id.txtPontuacao);
        txtPontuacao.setText(String.format(res.getString(R.string.pontuacao_texto), Preferencias.getInteger(context, "PONTUACAO")));

        TextView txtDica = (TextView) view.findViewById (R.id.txtDica);
        txtDica.setText(R.string.pontuacao_dica_exemplo);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public static void exibirSobre(final Context context){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_sobre, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        TextView txtBiliotecas = (TextView) view.findViewById(R.id.txtBiliotecas);
        txtBiliotecas.setMovementMethod(LinkMovementMethod.getInstance());
        TextView txtFonte = (TextView) view.findViewById(R.id.txtFonte);
        txtFonte.setMovementMethod(LinkMovementMethod.getInstance());

        TextView txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"maps.unabarreiro@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback UnaMaps ;D");
                intent.setType("message/rfc822");
                context.startActivity(Intent.createChooser(intent, "Enviar email"));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
