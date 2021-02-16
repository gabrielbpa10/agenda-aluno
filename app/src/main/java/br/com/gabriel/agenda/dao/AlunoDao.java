package br.com.gabriel.agenda.dao;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.gabriel.agenda.model.Aluno;

public class AlunoDao {

    private static int contadorId = 1;
    private static ArrayList<Aluno> alunos = new ArrayList<>();

    public void salva(Aluno aluno) {
        aluno.setId(contadorId);
        alunos.add(aluno);
        novoId();
    }

    private void novoId(){
        contadorId++;
    }

    @NonNull
    public List<Aluno> getListagemAlunos() {return alunos;}

    public boolean verificarListaVazia(){
        return alunos.isEmpty();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void editarAluno(Aluno aluno){
        alunos.forEach(al ->{
            if(al.getId() == aluno.getId()){
                int posicaoAluno = alunos.indexOf(al);
                alunos.set(posicaoAluno,aluno);
            }
        });
    }

    public void removerAluno(Aluno aluno){
        if(aluno != null){alunos.remove(aluno);}
    }

}
