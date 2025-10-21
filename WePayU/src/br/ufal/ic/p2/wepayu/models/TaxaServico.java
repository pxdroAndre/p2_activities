package br.ufal.ic.p2.wepayu.models;

/**
 * Representa uma taxa de serviço avulsa cobrada por um sindicato a um empregado.
 * <p>
 * Esta classe armazena a data e o valor de uma taxa de serviço específica,
 * que será descontada do pagamento do empregado.
 * </p>
 * @see Empregado
 * @author pxdroAndre
 * @version 1.0
 */
public class TaxaServico {
    private String data;
    private String valor;

    /**
     * Construtor padrão.
     * <p>
     * Necessário para a persistência de dados via XML.
     * </p>
     */
    public TaxaServico() {}

    /**
     * Construtor que inicializa uma taxa de serviço com data and valor.
     *
     * @param data A data em que a taxa foi cobrada.
     * @param valor O valor monetário da taxa.
     */
    public TaxaServico(String data, String valor) {
        this.data = data;
        this.valor = valor;
    }

    public TaxaServico(TaxaServico taxa) {
    }

    /**
     * Retorna a data da cobrança da taxa.
     * @return A data no formato "d/M/yyyy".
     */
    public String getData() {
        return data;
    }

    /**
     * Retorna o valor da taxa de serviço.
     * @return O valor da taxa em formato String.
     */
    public String getValor() {
        return valor;
    }

    /**
     * Define o valor da taxa de serviço.
     * @param valor O novo valor da taxa.
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * Define a data da cobrança da taxa.
     * @param data A nova data da taxa.
     */
    public void setData(String data) {
        this.data = data;
    }
}