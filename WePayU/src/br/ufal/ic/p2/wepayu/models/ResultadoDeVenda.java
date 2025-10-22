package br.ufal.ic.p2.wepayu.models;

/**
 * Representa o registro de uma única venda realizada por um empregado comissionado.
 * <p>
 * Esta classe armazena a data e o valor de uma venda, sendo utilizada para
 * calcular a comissão devida ao empregado.
 * </p>
 * @see EmpregadoComissionado
 * @author pxdroAndre
 * @version 1.0
 */
public class ResultadoDeVenda
{
    private String data;
    private String valor;

    /**
     * Construtor padrão.
     * Utilizado para a criação de instâncias via persistência XML.
     */
    public ResultadoDeVenda(){}

    public ResultadoDeVenda(ResultadoDeVenda venda)
    {
        this.data = venda.data;
        this.valor = venda.valor;

    }

    /**
     * Retorna o valor da venda.
     * @return O valor da venda em formato String.
     */
    public String getValor() {
        return valor;
    }

    /**
     * Define o valor da venda.
     * @param valor O novo valor da venda.
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * Retorna a data em que a venda foi realizada.
     * @return A data da venda.
     */
    public String getData() {
        return data;
    }

    /**
     * Define a data da venda.
     * @param data A nova data da venda.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Construtor que inicializa uma venda com data e valor.
     *
     * @param data A data da venda no formato "d/M/yyyy".
     * @param valor O valor monetário da venda.
     */
    public ResultadoDeVenda(String data, String valor)
    {
        this.data = data;
        this.valor = valor;
    }
}