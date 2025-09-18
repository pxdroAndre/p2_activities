package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDate;

public class CartaoPonto
{
    public CartaoPonto(){}

    String data;
    String horas;
    String horasExtras;

    public CartaoPonto(String data, String horas)
    {
        this.data = data;
        this.horas = horas;
        double h = Double.parseDouble(horas);
        if (h > 8.00)
        {
            this.horasExtras = String.valueOf(h % 8.00);
            this.horasExtras = this.horasExtras.replace(".", ",");
        }
        else
        {
            this.horasExtras = "0";
        }
    }

    public String getHoras()
    {
        return horas;
    }

    public String getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(String horasExtras) {
        this.horasExtras = horasExtras;
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
