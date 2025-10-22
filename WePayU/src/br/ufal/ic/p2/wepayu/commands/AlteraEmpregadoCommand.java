package br.ufal.ic.p2.wepayu.commands;
import br.ufal.ic.p2.wepayu.models.*;

import java.util.Map;

public class AlteraEmpregadoCommand implements Command
{
    private SistemaFolha sistema;
    private String emp, atributo, valor, idSindicato,  taxaSindical, comissao, banco, agencia, contaCorrente;
    private Map<String, Empregado> original;

    /**
     * Construtor do Command de alterar um empregado
     * @param sistema
     * @param emp
     * @param atributo
     * @param valor
     */
    public AlteraEmpregadoCommand(SistemaFolha sistema, String emp, String atributo, String valor)
    {
        this.sistema = sistema;
        this.emp = emp;
        this.atributo = atributo;
        this.valor = valor;
        this.idSindicato = this.taxaSindical = this.comissao = this.banco = this.agencia = this.contaCorrente = null;
    }

    /**
     * Construtor para de AlteraEmpregadoCommand para alterar empregado comissionado
     * @param sistema
     * @param emp
     * @param atributo
     * @param valor
     * @param comissao
     */
    public AlteraEmpregadoCommand(SistemaFolha sistema, String emp, String atributo, String valor, String comissao)
    {
        this.sistema = sistema;
        this.emp = emp;
        this.atributo = atributo;
        this.valor = valor;
        this.comissao = comissao;
        this.idSindicato = this.taxaSindical = this.banco = this.agencia = this.contaCorrente = null;
    }


    /**
     * construtor de AlteraEmpregadoCommand para alterar a taxa de sindicalização
     * @param emp
     * @param atributo
     * @param valor
     * @param idSindicato
     * @param taxaSindical
     */
    public AlteraEmpregadoCommand(SistemaFolha sistema, String emp, String atributo, String valor, String idSindicato, String taxaSindical)
    {
        this.sistema = sistema;
        this.emp = emp;
        this.atributo = atributo;
        this.valor = valor;
        this.idSindicato = idSindicato;
        this.taxaSindical = taxaSindical;
        this.comissao =  this.banco = this.agencia = this.contaCorrente = null;
    }

    /**
     * Construtor para alterar o metodo de pagamento do empregado
     * @param sistema
     * @param emp
     * @param atributo
     * @param valor
     * @param banco
     * @param agencia
     * @param contaCorrente
     */
    public AlteraEmpregadoCommand(SistemaFolha sistema, String emp, String atributo, String valor, String banco, String agencia, String contaCorrente)
    {
        this.sistema = sistema;
        this.emp = emp;
        this.atributo = atributo;
        this.valor = valor;
        this.banco = banco;
        this.agencia = agencia;
        this.contaCorrente = contaCorrente;
        this.idSindicato = this.taxaSindical = this.comissao = null;

    }

    /**
     * Executa o comando de alterar chamando o método no Sistema
     * @throws Exception
     */
    public void execute() throws Exception
    {
        this.original = this.sistema.alteraEmpregado(emp, atributo, valor, idSindicato, taxaSindical, comissao, banco, agencia, contaCorrente);
    }

    /**
     * desfaz a alteração do empregado
     */
    public void undo()
    {
        this.sistema.apagaRestauraEmpregados(this.original);
    }

}
