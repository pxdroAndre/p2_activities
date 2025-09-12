package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDate;

public class CartaoPonto
{
    public CartaoPonto(){}

    LocalDate data;
    int horas;

    public CartaoPonto(LocalDate data, int horas)
    {
        this.data = data;
        this.horas = horas;
    }

    public int getHoras() {
        return horas;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

}
