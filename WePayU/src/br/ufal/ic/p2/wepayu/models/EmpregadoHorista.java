package br.ufal.ic.p2.wepayu.models;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmpregadoHorista extends Empregado
{
    private ArrayList <CartaoPonto> cartoesDePonto = new ArrayList<>();
    private int horasNormais;
    private int horasExtras;

    public EmpregadoHorista (){}
    public EmpregadoHorista (String nome, String endereco, String tipo, double salario)
    {
        super(nome, endereco, tipo, salario);
    }

    public int getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(int horasExtras) {
        this.horasExtras = horasExtras;
    }

    public int getHorasNormais() {
        return horasNormais;
    }

    public void setHorasNormais(int horasNormais) {
        this.horasNormais = horasNormais;
    }

    public void lancaCartao (LocalDate data, double horas)
    {
        CartaoPonto novoCartao = new CartaoPonto(data, horas);
        cartoesDePonto.add(novoCartao);
    }

    public int getHorasNormaisTrabalhadas (String inicio, String fim)
    {

        return 1;
    }
}
