package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.CampoValidoException;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Representa um empregado do tipo Comissionado.
 * <p>
 * Empregados comissionados recebem um salário base acrescido de uma comissão
 * percentual sobre suas vendas. Esta classe gerencia os dados de comissão e
 * a lista de vendas realizadas.
 * </p>
 * @see Empregado
 * @see ResultadoDeVenda
 * @author pxdroAndre
 * @version 1.0
 */
public class EmpregadoComissionado extends Empregado
{
    private BigDecimal comissao;
    private ArrayList<ResultadoDeVenda> vendas = new ArrayList<>();
    Locale localeBrasil = new Locale("pt", "BR");
    NumberFormat formatador = NumberFormat.getNumberInstance(localeBrasil);

    /**
     * Construtor padrão.
     * <p>
     * Utilizado para a criação de instâncias via persistência XML.
     * </p>
     */
    public EmpregadoComissionado(){}

    /**
     * Construtor para criar um novo empregado comissionado.
     *
     * @param nome O nome completo do empregado.
     * @param endereco O endereço do empregado.
     * @param tipo O tipo de contrato, que deve ser "comissionado".
     * @param salario O salário base do empregado.
     * @param comissao A taxa de comissão sobre as vendas (ex: 0.05 para 5%).
     */
    public EmpregadoComissionado(String nome, String endereco, String tipo, double salario, double comissao) {
        super(nome, endereco, tipo, salario);
        this.comissao = BigDecimal.valueOf(comissao);
    }

    /**
     * Retorna a lista de todas as vendas realizadas pelo empregado.
     * @return Uma {@code ArrayList} de objetos {@link ResultadoDeVenda}.
     */
    public ArrayList<ResultadoDeVenda> getVendas() {
        return vendas;
    }

    /**
     * Define a lista de vendas do empregado.
     * @param vendas A nova lista de vendas.
     */
    public void setVendas(ArrayList<ResultadoDeVenda> vendas) {
        this.vendas = vendas;
    }

    /**
     * Retorna a taxa de comissão do empregado.
     * @return A taxa de comissão como um {@code double}.
     */
    public BigDecimal getComissao(){return comissao;};

    /**
     * Define a taxa de comissão do empregado.
     * @param comissao A nova taxa de comissão (ex: 0.05 para 5%).
     */
    public void setComissao(double comissao) {
        this.comissao = BigDecimal.valueOf(comissao);
    }

    /**
     * Adiciona um novo registro de venda à lista do empregado.
     *
     * @param valor O valor da venda realizada.
     * @param data  A data em que a venda ocorreu.
     */
    public void lancaVenda (String valor, String data)
    {
        ResultadoDeVenda venda = new ResultadoDeVenda(data, valor); // criando um objeto de venda
        vendas.add(venda); // adicionando no array
    }

    /**
     * Calcula e retorna o valor total de vendas realizadas em um determinado período.
     *
     * @param inicio A data inicial do período, em formato "d/M/yyyy".
     * @param fim    A data final do período, em formato "d/M/yyyy".
     * @return O valor total das vendas no período, formatado como String com duas casas decimais.
     * @throws CampoValidoException Se as datas forem inválidas ou se a data inicial for posterior à final.
     */
    public String getVendas (String inicio, String fim) throws CampoValidoException
    {
        double totalVendas = 0;
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

        // loop sobre a lista de vendas do empregado
        for (ResultadoDeVenda venda : vendas) {
            String dataDaVenda = venda.getData();
            LocalDate dataVenda = LocalDate.parse(dataDaVenda, formatter);

            // checa se a data da venda está dentro do intervalo
            if (!dataVenda.isBefore(in) && dataVenda.isBefore(fi)) {
                // Se estiver no intervalo, some as vendas
                String valorDaVenda = venda.getValor();
                valorDaVenda = valorDaVenda.replace(',', '.');
                double v = Double.parseDouble(valorDaVenda);
                totalVendas += v;
            }
        }
        // formata o retorno para string
        formatador.setGroupingUsed(false);
        formatador.setMinimumFractionDigits(2);
        formatador.setMaximumFractionDigits(2);
        return formatador.format(totalVendas);
    }


    /**
     * Calcula o salario do comissionado
     * @param dataInicial data inicial de analise
     * @param dataFinal data final da analise
     * @return retorna um double com o valor total a ser recebido naquela data
     * @throws CampoValidoException
     */
    public BigDecimal calculaSalarioBruto(LocalDate dataInicial, LocalDate dataFinal) throws CampoValidoException {
        // Lógica de cálculo correta e precisa
        BigDecimal salarioMensal = getSalario();
        BigDecimal doze = new BigDecimal("12");
        BigDecimal vinteSeis = new BigDecimal("26");

        // Parte fixa quinzenal
        BigDecimal parteFixa = salarioMensal.multiply(doze).divide(vinteSeis, 2, RoundingMode.HALF_UP);

        // Comissão sobre as vendas do período
        BigDecimal totalVendas = BigDecimal.ZERO;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        for (ResultadoDeVenda venda : vendas) {
            LocalDate dataVenda = LocalDate.parse(venda.getData(), formatter);
            if (!dataVenda.isBefore(dataInicial) && !dataVenda.isAfter(dataFinal)) {
                totalVendas = totalVendas.add(new BigDecimal(venda.getValor().replace(',', '.')));
            }
        }
        BigDecimal valorComissao = totalVendas.multiply(getComissao()).setScale(2, RoundingMode.HALF_DOWN);

        return parteFixa.add(valorComissao);
    }

}