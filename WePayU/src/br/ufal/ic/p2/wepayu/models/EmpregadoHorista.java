package br.ufal.ic.p2.wepayu.models;
import br.ufal.ic.p2.wepayu.Exception.CampoValidoException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;

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

    public void lancaCartao (String data, String horas)
    {
        CartaoPonto novoCartao = new CartaoPonto(data, horas);
        cartoesDePonto.add(novoCartao);
    }

    public static boolean validarData(String dataStr) {
        // 1. Quebra a string "1/13/2005" em um array: ["1", "13", "2005"]
        String[] partes = dataStr.split("/");

        // 2. Pega a parte do mês (o elemento no índice 1)
        String diaStr = partes[0];
        String mesStr = partes[1];

        // 3. Converte a string do mês para um número inteiro
        int dia = Integer.parseInt(diaStr);
        int mes = Integer.parseInt(mesStr);

        if (dia <= 0 || dia >= 32) return false;

        // 4. Verifica se o mês está no intervalo válido
        if (mes >= 1 && mes <= 12)
        {
            if (Objects.equals(mes, 2))
            {
                return dia <= 29;
            }
            return true;
        }
        return false;
    }

    public String getHorasNormaisTrabalhadas (String inicio, String fim) throws CampoValidoException
    {
        double horasNormais = 0;
        // fazendo parsing das datas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        // formata as datas
        if (!EmpregadoHorista.validarData(inicio)) throw new CampoValidoException("Data inicial invalida.");
        if (!EmpregadoHorista.validarData(fim)) throw new CampoValidoException("Data final invalida.");
        LocalDate in, fi;
        try
        {
            in = LocalDate.parse(inicio, formatter);
        } catch (DateTimeParseException e) {
            throw new CampoValidoException("Data inicial invalida."); // Mensagem com ponto
        }

        try
        {
            fi = LocalDate.parse(fim, formatter);
        } catch (DateTimeParseException e) {
            throw new CampoValidoException("Data final invalida."); // Mensagem com ponto
        }

        if (fi.isBefore(in)) throw new CampoValidoException("Data inicial nao pode ser posterior aa data final.");

        // loop sobre a lista de cartões de ponto do empregado
        for (CartaoPonto cartao : cartoesDePonto) {
            String dataDoCartao = cartao.getData();
            LocalDate dataCartao = LocalDate.parse(dataDoCartao, formatter);

            // checa se a data do cartão está dentro do intervalo
            if (!dataCartao.isBefore(in) && dataCartao.isBefore(fi)) {
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

    public String getHorasExtrasTrabalhadas (String inicio, String fim) throws CampoValidoException
    {
        double horasExtras = 0;

        // fazendo parsing das datas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        // formata as datas
        LocalDate in, fi;
        // valida as datas
        if (!EmpregadoHorista.validarData(inicio)) throw new CampoValidoException("Data inicial invalida.");
        if (!EmpregadoHorista.validarData(fim)) throw new CampoValidoException("Data final invalida.");
        try
        {
            in = LocalDate.parse(inicio, formatter);
        } catch (DateTimeParseException e)
        {
            throw new CampoValidoException("Data inicial invalida."); // Mensagem com ponto
        }
        try
        {
            fi = LocalDate.parse(fim, formatter);
        } catch (DateTimeParseException e) {
            throw new CampoValidoException("Data final invalida."); // Mensagem com ponto
        }

        if (fi.isBefore(in)) throw new CampoValidoException("Data inicial nao pode ser posterior aa data final.");

        // loop sobre a lista de cartões de ponto do empregado
        for (CartaoPonto cartao : cartoesDePonto)
        {
            String dataDoCartao = cartao.getData();
            LocalDate dataCartao = LocalDate.parse(dataDoCartao, formatter);
            // checa se a data do cartão está dentro do intervalo
            if (!dataCartao.isBefore(in) && dataCartao.isBefore(fi)) {
                // Se estiver no intervalo, some as horas
                String horasExtrasDoDia = cartao.getHorasExtras();
                horasExtrasDoDia = horasExtrasDoDia.replace(",", ".");
                double h = Double.parseDouble(horasExtrasDoDia);
                horasExtras += h;
            }
        }
        // formatando para o retorno
        if (horasExtras % 1 == 0.0)
        {
            int h =((int) horasExtras);
            return String.valueOf(h);
        }
        else {
            String h = String.valueOf(horasExtras);
            return h.replace('.', ',');
        }
    }
}
