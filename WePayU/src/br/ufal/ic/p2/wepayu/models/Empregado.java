package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.*;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Objects;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Representa a entidade base de um empregado no sistema de folha de pagamento.
 * <p>
 * Esta classe contém todos os dados essenciais de um empregado, como informações
 * pessoais, detalhes de pagamento, e afiliação sindical. É a superclasse para
 * tipos mais específicos de empregados como {@link EmpregadoHorista},
 * {@link EmpregadoAssalariado}, e {@link EmpregadoComissionado}.
 * </p>
 * @author pxdroAndre
 * @version 1.0
 */
public abstract class Empregado {
    private String nome;
    private String endereco;
    private String tipo;
    private String salario;
    private boolean sindicalizado;
    private String idSindicato;
    private String taxaSindical;
    private ArrayList<TaxaServico> taxasServico = new ArrayList<>();
    private String metodoPagamento = "emMaos"; // Valor default conforme os testes
    private String banco;
    private String agencia;
    private String contaCorrente;
    private String ultimoPagamento;
    private String agendaPagamento;


    /**
     * Retorna a data do último pagamento recebido pelo empregado.
     * @return A data do último pagamento em formato String.
     */
    public String getUltimoPagamento() {
        return ultimoPagamento;
    }

    /**
     * Define a data do último pagamento do empregado.
     * @param ultimoPagamento A data do último pagamento a ser definida.
     */
    public void setUltimoPagamento(String ultimoPagamento) {
        this.ultimoPagamento = ultimoPagamento;
    }

    /**
     * Retorna a agenda de pagamento do empregado.
     * @return A agenda de pagamento em formato String.
     */
    public String getAgendaPagamento() {
        return agendaPagamento;
    }

    /**
     * Define a agenda de pagamento do empregado.
     * @param agendaPagamento A nova agenda de pagamento.
     */
    public void setAgendaPagamento(String agendaPagamento) {
        this.agendaPagamento = agendaPagamento;
    }

    /**
     * Retorna o número da conta corrente do empregado.
     * @return O número da conta corrente.
     */
    public String getContaCorrente() {
        return contaCorrente;
    }

    /**
     * Define o número da conta corrente do empregado.
     * @param contaCorrente O número da conta a ser definido.
     */
    public void setContaCorrente(String contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    /**
     * Retorna o número da agência bancária do empregado.
     * @return O número da agência.
     */
    public String getAgencia() {
        return agencia;
    }

    /**
     * Define o número da agência bancária do empregado.
     * @param agencia O número da agência a ser definido.
     */
    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    /**
     * Retorna o nome do banco do empregado.
     * @return O nome do banco.
     */
    public String getBanco() {
        return banco;
    }

    /**
     * Define o nome do banco do empregado.
     * @param banco O nome do banco a ser definido.
     */
    public void setBanco(String banco) {
        this.banco = banco;
    }

    /**
     * Retorna o método de pagamento preferido do empregado.
     * @return O método de pagamento (ex: "emMaos", "banco", "correios").
     */
    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    /**
     * Define o método de pagamento do empregado.
     * @param metodoPagamento O novo método de pagamento a ser definido.
     */
    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    /**
     * Construtor padrão.
     * <p>
     * Utilizado para a criação de instâncias via persistência XML.
     * </p>
     */
    public Empregado(){}

    /**
     * Construtor para criar um novo empregado com os dados essenciais.
     *
     * @param nome O nome completo do empregado.
     * @param endereco O endereço residencial do empregado.
     * @param tipo O tipo de contrato (ex: "horista", "assalariado").
     * @param salario O valor base do salário ou da hora de trabalho.
     */
    public Empregado(String nome, String endereco, String tipo, String salario)
    {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.sindicalizado = false;
        if (!Objects.equals(tipo, "comissionado")) this.ultimoPagamento = "1/1/2005";
        else this.ultimoPagamento = "1/1/2005";
    }

    public Empregado(Empregado original)
    {
        this.nome = original.nome;
        this.endereco = original.endereco;
        this.tipo = original.tipo;
        this.salario = original.salario; // BigDecimal é imutável, então ok
        this.sindicalizado = original.sindicalizado;
        this.idSindicato = original.idSindicato;
        this.taxaSindical = original.taxaSindical;
        this.metodoPagamento = original.metodoPagamento;
        this.banco = original.banco;
        this.agencia = original.agencia;
        this.contaCorrente = original.contaCorrente;
        this.ultimoPagamento = original.ultimoPagamento;
        this.agendaPagamento = original.agendaPagamento;

        // Copiar Taxas de Serviço
        this.taxasServico = new ArrayList<>();
        for (TaxaServico taxa : original.taxasServico)
        {
            // A classe TaxaServico também precisa de um construtor de cópia
            this.taxasServico.add(new TaxaServico(taxa));
        }
    }

    public ArrayList<TaxaServico> backupTaxasServico ()
    {
        ArrayList<TaxaServico> backup = new ArrayList<>(this.taxasServico);
        return backup;
    }

    /**
     * Metodo que restaura um array de taxa de servico para uma versao anterior
     * @param original
     */
    public void restauraTaxasServico(ArrayList<TaxaServico> original) {
        this.taxasServico.clear();
        taxasServico.addAll(original);
    }

    /**
     * Define o nome do empregado.
     * @param nome O novo nome do empregado.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define o endereço do empregado.
     * @param endereco O novo endereço do empregado.
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Define o tipo de contrato do empregado.
     * @param tipo O novo tipo de contrato (ex: "horista").
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Define o salário do empregado.
     * @param salario O novo valor de salário.
     */
    public void setSalario(String salario)
    {
        this.salario = salario;
    }

    /**
     * Verifica se o empregado é sindicalizado.
     * @return {@code true} se o empregado for sindicalizado, {@code false} caso contrário.
     */
    public boolean isSindicalizado() {
        return sindicalizado;
    }

    /**
     * Define o status de sindicalização do empregado.
     * @param sindicalizado O novo status de sindicalização.
     */
    public void setSindicalizado(boolean sindicalizado) {
        this.sindicalizado = sindicalizado;
    }

    /**
     * Retorna o ID do empregado no sindicato.
     * @return O ID do sindicato.
     */
    public String getIdSindicato() {
        return idSindicato;
    }

    /**
     * Define o ID do empregado no sindicato.
     * @param idSindicato O novo ID do sindicato.
     */
    public void setIdSindicato(String idSindicato) {
        this.idSindicato = idSindicato;
    }

    /**
     * Retorna o valor da taxa sindical.
     * @return O valor da taxa.
     */
    public String getTaxaSindical() {
        return taxaSindical;
    }

    /**
     * Define o valor da taxa sindical.
     * @param taxaSindical O novo valor da taxa.
     */
    public void setTaxaSindical(String taxaSindical) {
        this.taxaSindical = taxaSindical;
    }

    /**
     * Retorna a lista de taxas de serviço adicionais do sindicato.
     * @return Uma {@code ArrayList} de objetos {@link TaxaServico}.
     */
    public ArrayList<TaxaServico> getTaxasServico() {
        return taxasServico;
    }

    /**
     * Define a lista de taxas de serviço.
     * @param taxasServico A nova lista de taxas.
     */
    public void setTaxasServico(ArrayList<TaxaServico> taxasServico) {
        this.taxasServico = taxasServico;
    }

    /**
     * Retorna o nome do empregado.
     * @return O nome do empregado.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o endereço do empregado.
     * @return O endereço do empregado.
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Retorna o tipo de contrato do empregado.
     * @return O tipo de contrato.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Retorna o salário base do empregado.
     * @return O salário base.
     */
    public String getSalario() {
        return salario;
    }

    /**
     * Retorna o status de sindicalização do empregado.
     * @return {@code true} se for sindicalizado, {@code false} caso contrário.
     * @deprecated Use o método {@link #isSindicalizado()} para seguir as convenções de nomenclatura Java para booleanos.
     */
    public boolean getSindicalizado(){ return sindicalizado;}


    /**
     * Retorna o salario do empregado com cálculos precisos usando BigDecimal.
     * @param empregado Empregado a ter o salario analisado.
     * @param data A data final do período de pagamento.
     * @return Retorna o valor do seu salario como um BigDecimal.
     */
    public abstract BigDecimal calculaSalarioBruto(String dataFinal) throws DataInicialInvalidaException, DataFinalInvalidaException, DataInicialNaoPodeSerPosteriorADataFinalException;


}
