package br.ufal.ic.p2.wepayu.models;
import br.ufal.ic.p2.wepayu.Exception.CampoValidoException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static java.time.format.ResolverStyle.STRICT;

public class EmpregadoHorista extends Empregado
{
    private ArrayList <CartaoPonto> cartoesDePonto = new ArrayList<>();
    private int horasNormais;
    private int horasExtras;

    public void setCartoesDePonto(ArrayList<CartaoPonto> cartoesDePonto) {
        this.cartoesDePonto = cartoesDePonto;
    }

    public EmpregadoHorista (){}
    public EmpregadoHorista (String nome, String endereco, String tipo, double salario)
    {
        super(nome, endereco, tipo, salario);
    }

    public int getHorasExtras() {
        return horasExtras;
    }

    public ArrayList<CartaoPonto> getCartoesDePonto() {
        return cartoesDePonto;
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

    public void lancaCartao (LocalDate data, String horas)
    {
        CartaoPonto novoCartao = new CartaoPonto(data, horas);
        cartoesDePonto.add(novoCartao);
    }

    public String getHorasNormaisTrabalhadas (String inicio, String fim) throws CampoValidoException
    {
        double horasNormais = 0;
        // fazendo parsing das datas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d/M/yyyy").withResolverStyle(STRICT); // formata as datas
        // formata as datas
        LocalDate in, fi;
        try {
            in = LocalDate.parse(inicio, formatter);
        } catch (DateTimeParseException e) {
            throw new CampoValidoException("Data inicial invalida."); // Mensagem com ponto
        }

        try {
            fi = LocalDate.parse(fim, formatter);
        } catch (DateTimeParseException e) {
            throw new CampoValidoException("Data final invalida."); // Mensagem com ponto
        }
        // loop sobre a lista de cartões de ponto do empregado
        for (CartaoPonto cartao : cartoesDePonto) {
            LocalDate dataDoCartao = cartao.getData();

            // checa se a data do cartão está dentro do intervalo
            if (!dataDoCartao.isBefore(in) && dataDoCartao.isBefore(fi)) {
                // Se estiver no intervalo, some as horas
                String horasDoDia = cartao.getHoras();
                double h = Double.parseDouble(horasDoDia);
                if (h > 8) {
                    horasNormais += 8;
                } else {
                    horasNormais += h;
                }
            }
        }
        // formatando para o retorno
        if (horasNormais % 1 == 0.0)
        {
            Integer h =((int) horasNormais);
            return String.valueOf(h);
        }
        else {
            String h = String.valueOf(horasNormais);
            return h.replace('.', ',');
        }
    }
}
