package br.ufal.ic.p2.wepayu.models;

/**
 * Representa um empregado do tipo Assalariado.
 * <p>
 * Empregados assalariados recebem um salário mensal fixo. Esta classe herda
 * todas as suas características da classe {@link Empregado}.
 * </p>
 * @see Empregado
 * @author pxdroAndre
 * @version 1.0
 */
public class EmpregadoAssalariado extends Empregado
{
    /**
     * Construtor para criar um novo empregado assalariado com dados essenciais.
     *
     * @param nome O nome completo do empregado.
     * @param endereco O endereço residencial do empregado.
     * @param tipo O tipo de contrato, que deve ser "assalariado".
     * @param salario O valor do salário mensal fixo.
     */
    public EmpregadoAssalariado (String nome, String endereco, String tipo, double salario)
    {
        super(nome, endereco, tipo, salario);
    }

    /**
     * Construtor padrão.
     * <p>
     * Utilizado para a criação de instâncias via persistência XML.
     * </p>
     */
    public EmpregadoAssalariado(){}
}