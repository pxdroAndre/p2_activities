package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDate;

public class CartaoPonto
{
    public CartaoPonto(){}

    LocalDate data;
    String horas;

    public CartaoPonto(LocalDate data, String horas)
    {
        this.data = data;
        this.horas = horas;
    }

    public String getHoras() {
        return horas;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

}
