package br.com.gabriel.agenda.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Aluno implements Serializable {

    private int id;
    private String nome;
    private String telefone;
    private String email;

    public Aluno(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public Aluno(){}

    public void setId(int id) {this.id = id;}

    public void setNome(String nome) {this.nome = nome;}

    public void setTelefone(String telefone) {this.telefone = telefone;}

    public void setEmail(String email) {this.email = email;}

    @NonNull
    @Override
    public String toString() {
        return nome;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {return id;}

    public boolean verificarIdValido(){return this.id > 0;}
}
