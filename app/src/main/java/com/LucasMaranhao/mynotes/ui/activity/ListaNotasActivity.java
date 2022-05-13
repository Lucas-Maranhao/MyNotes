package com.LucasMaranhao.mynotes.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.LucasMaranhao.mynotes.R;
import com.LucasMaranhao.mynotes.dao.NotaDAO;
import com.LucasMaranhao.mynotes.model.Nota;
import com.LucasMaranhao.mynotes.ui.adapter.ListaNotasAdapter;
import com.LucasMaranhao.mynotes.ui.recyclerview.adapter.ListaNotaAdapter;

import java.util.List;

public class ListaNotasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);

        NotaDAO dao = new NotaDAO();
        for (int i = 1; i <= 10000; i++){
            dao.insere(new Nota("Título " + i, "Descrição " + i));
        }
        List<Nota> todasNotas = dao.todos();

        listaNotas.setAdapter(new ListaNotaAdapter(this, todasNotas));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaNotas.setLayoutManager(layoutManager);
    }
}