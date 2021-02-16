package br.com.gabriel.agenda.ui.activity;

import android.content.Intent;
import android.media.tv.TvContract;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.logging.Logger;

import br.com.gabriel.agenda.R;
import br.com.gabriel.agenda.dao.AlunoDao;
import br.com.gabriel.agenda.model.Aluno;

import static br.com.gabriel.agenda.ui.activity.ConstantesActivity.CHAVE_ITEM_SELECIONADO;
import static br.com.gabriel.agenda.ui.activity.ConstantesActivity.TEXTO_ALUNO_CADASTRADO;
import static br.com.gabriel.agenda.ui.activity.ConstantesActivity.TEXTO_ALUNO_EDITADO;
import static br.com.gabriel.agenda.ui.activity.ConstantesActivity.TEXTO_EM_BRANCO;
import static br.com.gabriel.agenda.ui.activity.ConstantesActivity.TTITULO_BAR_EDITA_ALUNO;
import static br.com.gabriel.agenda.ui.activity.ConstantesActivity.TTITULO_BAR_NOVO_ALUNO;

public class FormularioAlunosActivity extends AppCompatActivity {
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private Button botaoSalvar;
    private AlunoDao alunoDao = new AlunoDao();
    private Aluno aluno;

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_alunos);
        configurarCampos();
        Intent intent = getIntent();

        if(intent.hasExtra(CHAVE_ITEM_SELECIONADO)) {
            setTitle(TTITULO_BAR_EDITA_ALUNO);
            aluno = (Aluno) intent.getSerializableExtra(CHAVE_ITEM_SELECIONADO);
            preencherCampos();
        }else{
            setTitle(TTITULO_BAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
        this.salvarAluno();
    }

    private void configurarCampos(){
        this.campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        this.campoEmail = findViewById(R.id.activity_formulario_aluno_email);
        this.campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        this.botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao);
    }

    private void salvarAluno() {
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                if (verificarCamposEmBranco() == false) {
                    preencherAluno();
                    finalizarFormulario();
                } else
                    Toast.makeText(FormularioAlunosActivity.this, TEXTO_EM_BRANCO, Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean verificarCamposEmBranco() {
        if (campoNome.getText().toString().equals("")
                || campoEmail.getText().toString().equals("")
                || campoTelefone.getText().toString().equals("")) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void preencherAluno(){
        aluno.setNome(campoNome.getText().toString());
        aluno.setEmail(campoEmail.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
    }

    private void preencherCampos(){
        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        campoEmail.setText(aluno.getEmail());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void finalizarFormulario(){
        if(aluno.verificarIdValido()){
            alunoDao.editarAluno(aluno);
            Toast.makeText(FormularioAlunosActivity.this, TEXTO_ALUNO_EDITADO, Toast.LENGTH_SHORT).show();
        } else {
            alunoDao.salva(aluno);
            Toast.makeText(FormularioAlunosActivity.this, TEXTO_ALUNO_CADASTRADO, Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}