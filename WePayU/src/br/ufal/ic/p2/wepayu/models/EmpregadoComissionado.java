package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.*;

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
    private String comissao;
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

    public EmpregadoComissionado(EmpregadoComissionado empregado)
    {
        super(empregado);
        this.vendas = new ArrayList<>();
        for (ResultadoDeVenda venda : empregado.vendas)
        {
            this.vendas.add(new ResultadoDeVenda(venda));
        }
    }

    /**
     * Construtor para criar um novo empregado comissionado.
     *
     * @param nome O nome completo do empregado.
     * @param endereco O endereço do empregado.
     * @param tipo O tipo de contrato, que deve ser "comissionado".
     * @param salario O salário base do empregado.
     * @param comissao A taxa de comissão sobre as vendas (ex: 0.05 para 5%).
     */
    public EmpregadoComissionado(String nome, String endereco, String tipo, String salario, double comissao) {
        super(nome, endereco, tipo, salario);
        this.comissao = SistemaFolha.doubleParaString(comissao);
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
    public String getComissao(){return comissao;};

    /**
     * Define a taxa de comissão do empregado.
     * @param comissao A nova taxa de comissão (ex: 0.05 para 5%).
     */
    public void setComissao(String comissao) {
        this.comissao = comissao;
    }

    /**
     * Metodo para restaurar vendas deste comissionado
     * @param original
     */
    public void restaurarVendas (ArrayList<ResultadoDeVenda> original)
    {
        this.vendas.clear();
        this.vendas.addAll(original);
    }

    /**
     * Adiciona um novo registro de venda à lista do empregado.
     *
     * @param valor O valor da venda realizada.
     * @param data  A data em que a venda ocorreu.
     */
    public ArrayList<ResultadoDeVenda> lancaVenda (String valor, String data)
    {
        ArrayList<ResultadoDeVenda> original = new ArrayList<>(vendas);
        ResultadoDeVenda venda = new ResultadoDeVenda(data, valor); // criando um objeto de venda
        vendas.add(venda); // adicionando no array
        return original;
    }

    /**
     * Calcula e retorna o valor total de vendas realizadas em um determinado período.
     *
     * @param inicio A data inicial do período, em formato "d/M/yyyy".
     * @param fim    A data final do período, em formato "d/M/yyyy".
     * @return O valor total das vendas no período, formatado como String com duas casas decimais.
     * @throws CampoValidoException Se as datas forem inválidas ou se a data inicial for posterior à final.
     */
    public String getVendas (String inicio, String fim) throws DataInicialInvalidaException, DataFinalInvalidaException, DataInicialNaoPodeSerPosteriorADataFinalException
    {
        double totalVendas = 0;
        // fazendo parsing das datas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        // formata as datas
        if (!EmpregadoHorista.validarData(inicio)) throw new DataInicialInvalidaException();
        if (!EmpregadoHorista.validarData(fim)) throw new DataFinalInvalidaException();
        LocalDate in, fi;
        try
        {
            in = LocalDate.parse(inicio, formatter);
        } catch (DateTimeParseException e) {
            throw new DataInicialInvalidaException(); // Mensagem com ponto
        }

        try
        {
            fi = LocalDate.parse(fim, formatter);
        } catch (DateTimeParseException e) {
            throw new DataFinalInvalidaException(); // Mensagem com ponto
        }

        if (fi.isBefore(in)) throw new DataInicialNaoPodeSerPosteriorADataFinalException();

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
     * @param dataFinal data atual para ser calculado
     * @return retorna um bigDecimal com o valor do salario do comissionado naquela data
     * @throws CampoValidoException necessario para chamar o metodo getVendas
     */
    public BigDecimal calculaSalarioBruto(String dataFinal) throws DataInicialInvalidaException, DataFinalInvalidaException, DataInicialNaoPodeSerPosteriorADataFinalException
    {
        String vendasStr = this.getVendas(this.getUltimoPagamento(), dataFinal).replace(",", ".");

        BigDecimal salarioMensal = BigDecimal.valueOf(Double.parseDouble(this.getSalario().replace(",", ".")));
        BigDecimal valorVendas = new BigDecimal(vendasStr);
        BigDecimal comissaoPercentual = BigDecimal.valueOf(SistemaFolha.stringParaDouble(this.getComissao()));

        BigDecimal doze = new BigDecimal("12");
        BigDecimal vinteSeis = new BigDecimal("26");

        BigDecimal parteFixa = salarioMensal.multiply(doze)
                .divide(vinteSeis, 2, RoundingMode.DOWN);

        BigDecimal valorComissao = valorVendas.multiply(comissaoPercentual);

        return parteFixa.add(valorComissao);
    }

}