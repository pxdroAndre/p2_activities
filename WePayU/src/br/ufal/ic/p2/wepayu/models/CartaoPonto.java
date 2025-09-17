package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDate;

public class CartaoPonto
{
    public CartaoPonto(){}

    String data;
    String horas;

    public CartaoPonto(String data, String horas)
    {
        this.data = data;
        this.horas = horas;
    }

    public String getHoras() {
        return horas;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

}
