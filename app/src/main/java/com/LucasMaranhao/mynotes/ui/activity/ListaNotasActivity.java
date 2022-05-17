package com.LucasMaranhao.mynotes.ui.activity;

import static com.LucasMaranhao.mynotes.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static com.LucasMaranhao.mynotes.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static com.LucasMaranhao.mynotes.ui.activity.NotaActivityConstantes.CODIGO_RESULTADO_NOTA_CRIADA;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.LucasMaranhao.mynotes.R;
import com.LucasMaranhao.mynotes.dao.NotaDAO;
import com.LucasMaranhao.mynotes.model.Nota;
import com.LucasMaranhao.mynotes.ui.recyclerview.adapter.ListaNotaAdapter;

import java.util.List;

public class ListaNotasActivity extends AppCompatActivity {

    private ListaNotaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        List<Nota> todasNotas = pegaTodasNotas();
        configuraRecyclerView(todasNotas);
        configuraBotaoInsereNota();
    }

    private void configuraBotaoInsereNota() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaFormularioNotaActivity();
            }
        });
    }

    private void vaiParaFormularioNotaActivity() {
        Intent iniciaFormularioNota = new Intent(ListaNotasActivity.this,
                FormularioNotaActivity.class);
        startActivityForResult(iniciaFormularioNota, CODIGO_REQUISICAO_INSERE_NOTA);
    }

    private List<Nota> pegaTodasNotas() {
        NotaDAO dao = new NotaDAO();
        List<Nota> todasNotas = dao.todos();
        return todasNotas;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ehResultadoComNota(requestCode, resultCode, data)) {
            Nota notaRecebida = (Nota) data.getSerializableExtra("nota");
            adiciona(notaRecebida);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void adiciona(Nota nota) {
        new NotaDAO().insere(nota);
        adapter.adiciona(nota);
    }

    private boolean ehResultadoComNota(int requestCode, int resultCode, @Nullable Intent data) {
        return ehCodigoRequisicaoInsereNota(requestCode)
                && ehCodigoResultadoNotaCriada(resultCode)
                && temNota(data);
    }

    private boolean temNota(@Nullable Intent data) {
        return data.hasExtra(CHAVE_NOTA);
    }

    private boolean ehCodigoResultadoNotaCriada(int resultCode) {
        return resultCode == CODIGO_RESULTADO_NOTA_CRIADA;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        RecyclerView listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotaAdapter(this, todasNotas);
        listaNotas.setAdapter(adapter);
    }
}