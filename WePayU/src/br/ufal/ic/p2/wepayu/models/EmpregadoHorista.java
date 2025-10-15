package br.ufal.ic.p2.wepayu.models;
import br.ufal.ic.p2.wepayu.Exception.CampoValidoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Representa um empregado do tipo Horista.
 * <p>
 * Empregados horistas são remunerados com base no número de horas trabalhadas,
 * registradas através de {@link CartaoPonto}. Esta classe gerencia os cartões de ponto
 * e calcula as horas normais e extras trabalhadas em um período.
 * </p>
 * @see Empregado
 * @see CartaoPonto
 * @author pxdroAndre
 * @version 1.0
 */
public class EmpregadoHorista extends Empregado
{
    private ArrayList <CartaoPonto> cartoesDePonto = new ArrayList<>();

    /**
     * Define a lista de cartões de ponto do empregado.
     * @param cartoesDePonto A nova lista de cartões de ponto.
     */
    public void setCartoesDePonto(ArrayList<CartaoPonto> cartoesDePonto) {
        this.cartoesDePonto = cartoesDePonto;
    }

    /**
     * Construtor padrão.
     * Utilizado para a criação de instâncias via persistência XML.
     */
    public EmpregadoHorista (){}

    /**
     * Construtor para criar um novo empregado horista.
     *
     * @param nome O nome completo do empregado.
     * @param endereco O endereço do empregado.
     * @param tipo O tipo de contrato, que deve ser "horista".
     * @param salario O valor da remuneração por hora de trabalho.
     */
    public EmpregadoHorista (String nome, String endereco, String tipo, String salario)
    {
        super(nome, endereco, tipo, salario);
    }

    /**
     * Retorna a lista de todos os cartões de ponto associados a este empregado.
     * @return Uma {@code ArrayList} de objetos {@link CartaoPonto}.
     */
    public ArrayList<CartaoPonto> getCartoesDePonto() {
        return cartoesDePonto;
    }


    /**
     * Cria e adiciona um novo cartão de ponto à lista do empregado.
     *
     * @param data A data do registro de trabalho.
     * @param horas O total de horas trabalhadas no dia.
     */
    public void lancaCartao (String data, String horas)
    {
        if(cartoesDePonto.isEmpty())
        {
            this.setUltimoPagamento(data);
        }
        CartaoPonto novoCartao = new CartaoPonto(data, horas);
        cartoesDePonto.add(novoCartao);
    }

    /**
     * Valida se uma string de data está em um formato plausível (d/M/yyyy).
     * <p>
     * Verifica se o dia e o mês estão dentro de intervalos válidos.
     * </p>
     * @param dataStr A data em formato de String a ser validada.
     * @return {@code true} se a data for válida, {@code false} caso contrário.
     */
    public static boolean validarData(String dataStr) {
        String[] partes = dataStr.split("/");
        if (partes.length != 3) return false;

        String diaStr = partes[0];
        String mesStr = partes[1];

        int dia = Integer.parseInt(diaStr);
        int mes = Integer.parseInt(mesStr);

        if (dia <= 0 || dia > 31) return false;

        if (mes >= 1 && mes <= 12)
        {
            if (mes == 2)
            {
                return dia <= 29; // Simplificação para anos bissextos
            }
            return true;
        }
        return false;
    }

    /**
     * Calcula o total de horas normais trabalhadas em um determinado período.
     * <p>
     * Horas normais são limitadas a 8 por dia. O que excede é considerado hora extra.
     * </p>
     * @param inicio A data inicial do período no formato "d/M/yyyy".
     * @param fim A data final do período no formato "d/M/yyyy".
     * @return O total de horas normais formatado como String.
     * @throws CampoValidoException se as datas forem inválidas ou se a data inicial for posterior à final.
     */
    public String getHorasNormaisTrabalhadas (String inicio, String fim) throws CampoValidoException
    {
        double horasNormais = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        if (!EmpregadoHorista.validarData(inicio)) throw new CampoValidoException("Data inicial invalida.");
        if (!EmpregadoHorista.validarData(fim)) throw new CampoValidoException("Data final invalida.");
        LocalDate in, fi;
        try
        {
            in = LocalDate.parse(inicio, formatter);
        } catch (DateTimeParseException e) {
            throw new CampoValidoException("Data inicial invalida.");
        }

        try
        {
            fi = LocalDate.parse(fim, formatter);
        } catch (DateTimeParseException e) {
            throw new CampoValidoException("Data final invalida.");
        }

        if (fi.isBefore(in)) throw new CampoValidoException("Data inicial nao pode ser posterior aa data final.");

        for (CartaoPonto cartao : cartoesDePonto) {
            String dataDoCartao = cartao.getData();
            LocalDate dataCartao = LocalDate.parse(dataDoCartao, formatter);

            if (!dataCartao.isBefore(in) && dataCartao.isBefore(fi)) {
                String horasDoDia = cartao.getHoras().replace(',', '.');
                double h = Double.parseDouble(horasDoDia);
                if (h > 8) {
                    horasNormais += 8;
                } else {
                    horasNormais += h;
                }
            }
        }
        if (horasNormais % 1 == 0.0)
        {
            return String.valueOf((int) horasNormais);
        }
        else {
            return String.valueOf(horasNormais).replace('.', ',');
        }
    }

    /**
     * Calcula o total de horas extras trabalhadas em um determinado período.
     * <p>
     * Horas extras são as horas trabalhadas que excedem 8 horas em um único dia.
     * </p>
     * @param inicio A data inicial do período no formato "d/M/yyyy".
     * @param fim A data final do período no formato "d/M/yyyy".
     * @return O total de horas extras formatado como String.
     * @throws CampoValidoException se as datas forem inválidas ou se a data inicial for posterior à final.
     */
    public String getHorasExtrasTrabalhadas (String inicio, String fim) throws CampoValidoException
    {
        double horasExtras = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate in, fi;

        if (!EmpregadoHorista.validarData(inicio)) throw new CampoValidoException("Data inicial invalida.");
        if (!EmpregadoHorista.validarData(fim)) throw new CampoValidoException("Data final invalida.");
        try
        {
            in = LocalDate.parse(inicio, formatter);
        } catch (DateTimeParseException e)
        {
            throw new CampoValidoException("Data inicial invalida.");
        }
        try
        {
            fi = LocalDate.parse(fim, formatter);
        } catch (DateTimeParseException e) {
            throw new CampoValidoException("Data final invalida.");
        }

        if (fi.isBefore(in)) throw new CampoValidoException("Data inicial nao pode ser posterior aa data final.");

        for (CartaoPonto cartao : cartoesDePonto)
        {
            String dataDoCartao = cartao.getData();
            LocalDate dataCartao = LocalDate.parse(dataDoCartao, formatter);

            if (!dataCartao.isBefore(in) && dataCartao.isBefore(fi)) {
                String horasExtrasDoDia = cartao.getHorasExtras().replace(",", ".");
                double h = Double.parseDouble(horasExtrasDoDia);
                horasExtras += h;
            }
        }
        if (horasExtras % 1 == 0.0)
        {
            return String.valueOf((int) horasExtras);
        }
        else {
            return String.valueOf(horasExtras).replace('.', ',');
        }
    }

    public BigDecimal calculaSalarioBruto(String dataFinal) throws CampoValidoException
    {
        String normalStr = this.getHorasNormaisTrabalhadas(this.getUltimoPagamento(), dataFinal).replace(",", ".");
        String extrasStr = this.getHorasExtrasTrabalhadas(this.getUltimoPagamento(), dataFinal).replace(",", ".");

        BigDecimal horasNormais = new BigDecimal(normalStr);
        BigDecimal horasExtras = new BigDecimal(extrasStr);
        BigDecimal salarioHora = BigDecimal.valueOf(Double.parseDouble(this.getSalario().replace(",", ".")));
        BigDecimal multiplicadorExtra = new BigDecimal("1.5");

        BigDecimal pagamentoNormal = horasNormais.multiply(salarioHora);
        BigDecimal pagamentoExtra = horasExtras.multiply(salarioHora).multiply(multiplicadorExtra);
        return pagamentoNormal.add(pagamentoExtra);
    }
}
