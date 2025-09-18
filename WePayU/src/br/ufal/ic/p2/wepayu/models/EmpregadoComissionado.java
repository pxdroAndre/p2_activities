package br.ufal.ic.p2.wepayu.models;

import java.util.ArrayList;

public class EmpregadoComissionado extends Empregado
{
    private double comissao;
    private ArrayList<ResultadoDeVenda> vendas = new ArrayList<>();
    public EmpregadoComissionado(){}

    public EmpregadoComissionado(String nome, String endereco, String tipo, double salario, double comissao) {
        super(nome, endereco, tipo, salario);
        this.comissao = comissao;
    }
    public double getComissao(){return comissao;};

    public void setComissao(double comissao) {
        this.comissao = comissao;
    }

    public void lancaVenda (String valor, String data)
    {
        ResultadoDeVenda venda = new ResultadoDeVenda(data, valor); // criando um objeto de venda
        vendas.add(venda); // adicionando no array
    }
}
