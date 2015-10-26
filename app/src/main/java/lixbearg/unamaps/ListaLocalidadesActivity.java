package lixbearg.unamaps;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.ListaLocalidadesAdapter;
import utils.LocalidadesDBAdapter;

public class ListaLocalidadesActivity extends AppCompatActivity {

    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_localidades);

        LocalidadesDBAdapter db = new LocalidadesDBAdapter(this);
        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        expandableListView = (ExpandableListView)findViewById(R.id.expListaSalas);

        List<String> Headings = new ArrayList<String>();
        List<String> Salas = new ArrayList<String>();
        List<String> Labs = new ArrayList<String>();
        List<String> Outros = new ArrayList<String>();
        HashMap<String, List<String>> ChildList = new HashMap<String, List<String>>();
        String heading_itens[] = db.selecionarHeadings();
        String salas_itens[] = db.selecionarSalas(1);
        String labs_itens[] = db.selecionarSalas(2);
        String outros_itens[] = db.selecionarSalas(3);

        for(String title : heading_itens){
            Headings.add(title);
        }
        for(String title : salas_itens){
            Salas.add(title);
        }
        for(String title : labs_itens){
            Labs.add(title);
        }
        for(String title : outros_itens){
            Outros.add(title);
        }

        ChildList.put(Headings.get(0), Salas);
        ChildList.put(Headings.get(1), Labs);
        ChildList.put(Headings.get(2), Outros);

        final ListaLocalidadesAdapter listaAdapter = new ListaLocalidadesAdapter(this, Headings, ChildList);
        expandableListView.setAdapter(listaAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String QRCodeLocalidade = (String)listaAdapter.getChild(groupPosition, childPosition);
                Intent intent = new Intent();
                intent.putExtra("QRCODE", QRCodeLocalidade);
                setResult(1 ,intent);
                finish();
                return true;
            }
        });
    }

    private Context getContext() {
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_localidades, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
