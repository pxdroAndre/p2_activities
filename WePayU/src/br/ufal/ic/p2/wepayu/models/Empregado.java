package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.CampoValidoException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
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
public class Empregado {
    private String nome;
    private String endereco;
    private String tipo;
    private BigDecimal salario;
    private boolean sindicalizado;
    private String idSindicato;
    private BigDecimal taxaSindical;
    private ArrayList<TaxaServico> taxasServico = new ArrayList<>();
    private String metodoPagamento = "emMaos"; // Valor default conforme os testes
    private String banco;
    private String agencia;
    private String contaCorrente;
    private String ultimoPagamento;


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
    public Empregado(String nome, String endereco, String tipo, double salario)
    {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = BigDecimal.valueOf(salario);
        this.sindicalizado = false;
        if (!Objects.equals(tipo, "comissionado")) this.ultimoPagamento = "1/1/2005";
        else this.ultimoPagamento = "1/1/2005";
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
    public void setSalario(BigDecimal salario) {
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
    public BigDecimal getTaxaSindical() {
        return taxaSindical;
    }

    /**
     * Define o valor da taxa sindical.
     * @param taxaSindical O novo valor da taxa.
     */
    public void setTaxaSindical(BigDecimal taxaSindical) {
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
    public BigDecimal getSalario() {
        return salario;
    }

    /**
     * Retorna o status de sindicalização do empregado.
     * @return {@code true} se for sindicalizado, {@code false} caso contrário.
     * @deprecated Use o método {@link #isSindicalizado()} para seguir as convenções de nomenclatura Java para booleanos.
     */
    public boolean getSindicalizado(){ return sindicalizado;}


    /**
     * Retorna o salario do empregado
     * @param empregado Empregado a ter o salario analisado
     * @return retorna o valor do seu salario
     */
    /**
     * Retorna o salario do empregado com cálculos precisos usando BigDecimal.
     * @param empregado Empregado a ter o salario analisado.
     * @param data A data final do período de pagamento.
     * @return Retorna o valor do seu salario como um double.
     */
    /**
     * Retorna o salario do empregado com cálculos precisos usando BigDecimal.
     * @param empregado Empregado a ter o salario analisado.
     * @param data A data final do período de pagamento.
     * @return Retorna o valor do seu salario como um BigDecimal.
     */
    public static BigDecimal calculaSalarioBruto(Empregado empregado, String data) throws CampoValidoException {
        String tipo = empregado.getTipo();
        BigDecimal salarioFinal = BigDecimal.ZERO;

        switch (tipo) {
            case "horista" -> {
                EmpregadoHorista emp = (EmpregadoHorista) empregado;
                String normalStr = emp.getHorasNormaisTrabalhadas(emp.getUltimoPagamento(), data).replace(",", ".");
                String extrasStr = emp.getHorasExtrasTrabalhadas(emp.getUltimoPagamento(), data).replace(",", ".");

                BigDecimal horasNormais = new BigDecimal(normalStr);
                BigDecimal horasExtras = new BigDecimal(extrasStr);
                BigDecimal salarioHora = empregado.getSalario();
                BigDecimal multiplicadorExtra = new BigDecimal("1.5");

                BigDecimal pagamentoNormal = horasNormais.multiply(salarioHora);
                BigDecimal pagamentoExtra = horasExtras.multiply(salarioHora).multiply(multiplicadorExtra);

                salarioFinal = pagamentoNormal.add(pagamentoExtra);
            }
            case "comissionado" -> {
                EmpregadoComissionado com = (EmpregadoComissionado) empregado;
                String vendasStr = com.getVendas(com.getUltimoPagamento(), data).replace(",", ".");

                BigDecimal salarioMensal = com.getSalario();
                BigDecimal valorVendas = new BigDecimal(vendasStr);
                BigDecimal comissaoPercentual = com.getComissao();

                BigDecimal doze = new BigDecimal("12");
                BigDecimal vinteSeis = new BigDecimal("26");

                BigDecimal parteFixa = salarioMensal.multiply(doze)
                        .divide(vinteSeis, 2, RoundingMode.DOWN);

                BigDecimal valorComissao = valorVendas.multiply(comissaoPercentual);

                salarioFinal = parteFixa.add(valorComissao);
            }
            case "assalariado" -> {
                salarioFinal = empregado.getSalario();
            }
        }

        // Retorna o BigDecimal com 2 casas decimais
        return salarioFinal.setScale(2, RoundingMode.DOWN);
    }

}
