package br.ufal.ic.p2.wepayu.models;

public class ResultadoDeVenda
{
    private String data;
    private String valor;

    public ResultadoDeVenda(){}

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ResultadoDeVenda(String data, String valor)
    {
        this.data = data;
        this.valor = valor;
    }
}
