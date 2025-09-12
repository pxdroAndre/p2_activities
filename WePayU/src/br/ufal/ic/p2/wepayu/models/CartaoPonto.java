package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDate;

public class CartaoPonto
{
    public CartaoPonto(){}

    LocalDate data;
    double horas;

    public CartaoPonto(LocalDate data, double horas)
    {
        this.data = data;
        this.horas = horas;
    }

    public double getHoras() {
        return horas;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setHoras(double horas) {
        this.horas = horas;
    }

}
