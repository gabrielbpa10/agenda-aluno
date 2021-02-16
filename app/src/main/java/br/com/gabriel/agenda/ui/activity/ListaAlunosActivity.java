package br.com.gabriel.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import br.com.gabriel.agenda.R;
import br.com.gabriel.agenda.dao.AlunoDao;
import br.com.gabriel.agenda.model.Aluno;

import static br.com.gabriel.agenda.ui.activity.ConstantesActivity.CHAVE_ITEM_SELECIONADO;
import static br.com.gabriel.agenda.ui.activity.ConstantesActivity.TITULO_BAR;

public class ListaAlunosActivity extends AppCompatActivity {

    private FloatingActionButton botaoAdicionarAluno;
    private ListView listaAlunos;
    private TextView semCadastroAlunos;
    private ArrayAdapter<Aluno> adapter;
    private AlunoDao alunoDao = new AlunoDao();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_alunos);
        setTitle(TITULO_BAR);
        this.configurarCampos();
        this.adicionarNovoAluno();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.listarAlunos();
        this.editarAluno();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Remover");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Aluno alunoSelecionado = adapter.getItem(menuInfo.position);
        this.removerAluno(alunoSelecionado);
        return super.onContextItemSelected(item);
    }

    private void removerAluno(Aluno alunoSelecionado){
        alunoDao.removerAluno(alunoSelecionado);
        adapter.remove(alunoSelecionado);
        verificarSemAlunosCadastrados();
    }

    private void configurarCampos(){
        listaAlunos = findViewById(R.id.activity_lista_de_alunos);
        botaoAdicionarAluno = findViewById(R.id.activity_lista_de_alunos_fab_novo_aluno);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
    }

    private void listarAlunos(){
        this.verificarSemAlunosCadastrados();
        adapter.clear();
        adapter.addAll(alunoDao.getListagemAlunos());
        listaAlunos.setAdapter(adapter);
        registerForContextMenu(listaAlunos);
    }

    private void adicionarNovoAluno() {
        botaoAdicionarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaAlunosActivity.this, FormularioAlunosActivity.class));
            }
        });
    }

    private void verificarSemAlunosCadastrados(){
        semCadastroAlunos = findViewById(R.id.activity_lista_alunos_nenhum_cadastro);
        if (alunoDao.verificarListaVazia() == false) {
            semCadastroAlunos.setVisibility(View.INVISIBLE);
        } else{
            semCadastroAlunos.setVisibility(View.VISIBLE);
        }
    }

    private void editarAluno(){
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioAlunosActivity.class);
                Aluno alunoSelecionado = (Aluno) adapterView.getItemAtPosition(posicao);
                intent.putExtra(CHAVE_ITEM_SELECIONADO,alunoSelecionado);
                startActivity(intent);
            }
        });
    }
}
