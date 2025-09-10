package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;

import java.util.HashMap;
import java.util.Map;

public class Empregado {
    private String nome;
    private String endereco;
    private String tipo;
    private int salario;
    private boolean sindicalizado;


    public Empregado(String nome, String endereco, String tipo, int salario)
    {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        sindicalizado = false;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTipo() {
        return tipo;
    }

    public int getSalario() {
        return salario;
    }

    public boolean getSindicalizado(){ return sindicalizado;}
}
