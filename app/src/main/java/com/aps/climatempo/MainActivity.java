package com.aps.climatempo;


import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private TextView cidade;
    private List<ClimaTempo> climaTempoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        cidade = (TextView) findViewById(R.id.idCidade);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tarefa tarefa = new Tarefa();
                tarefa.execute("http://servicos.cptec.inpe.br/XML/cidade/7dias/244/previsao.xml");

            }
        });
    }

    private class Tarefa extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String retorno = Conexao.getDados(strings[0]);
            return retorno;
        }

        protected void onPostExecute(String s){
            climaTempoList = ConsumirClimaTempoXML.xmlDados(s);
            exibirDados();
        }
    }

    private void exibirDados() {
        ClimaTempo clima = new ClimaTempo();
        if(climaTempoList != null){
            for (ClimaTempo tempo :  climaTempoList){
                textView.append("data: " + tempo.getDia() + "\n");
                textView.append("Temperatura maxima: " + tempo.getMaxima() + "\n");
                textView.append("Temperatura minima: " + tempo.getMinima() + "\n\n");
            }
            cidade.setText(clima.getCidade());

        }
    }
}