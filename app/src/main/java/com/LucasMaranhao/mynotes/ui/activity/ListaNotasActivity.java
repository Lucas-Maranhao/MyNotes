package com.LucasMaranhao.mynotes.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.LucasMaranhao.mynotes.R;
import com.LucasMaranhao.mynotes.dao.NotaDAO;
import com.LucasMaranhao.mynotes.model.Nota;
import com.LucasMaranhao.mynotes.ui.adapter.ListaNotasAdapter;

import java.util.List;

public class ListaNotasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        ListView listaNotas = findViewById(R.id.listView);

        NotaDAO dao = new NotaDAO();
        for (int i = 1; i <= 10000; i++){
            dao.insere(new Nota("Título" + i, "Descrição" + i));
        }
        List<Nota> todasNotas = dao.todos();

        listaNotas.setAdapter(new ListaNotasAdapter(this, todasNotas));
    }
}