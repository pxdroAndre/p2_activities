package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.CampoValidoException;
import br.ufal.ic.p2.wepayu.models.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.SistemaFolha;

public class Facade
{
    SistemaFolha sistema = new SistemaFolha();
    //zerando o sistema
    public void zerarSistema()
    {
        sistema.zerarSistema();
    }

    // metodo para pegar algum atributo do empregado
    public String getAtributoEmpregado (String emp, String atributo) throws EmpregadoNaoExisteException, CampoValidoException
    {
        return sistema.getAtributoEmpregado(emp, atributo);
    }

    // metodo para criar empregados nao comissionados
    public String criarEmpregado (String nome, String endereco, String tipo, String salario) throws CampoValidoException
    {
        return sistema.criarEmpregado(nome, endereco, tipo, salario);
    }
    // metodo para criar empregados comissionados
    public String criarEmpregado (String nome, String endereco, String tipo, String salario, String comissao) throws CampoValidoException
    {

        return sistema.criarEmpregado(nome, endereco, tipo, salario, comissao);
    }

    public void encerrarSistema ()
    {
        sistema.encerrarSistema();
    }

    public String getEmpregadoPorNome (String nome, int id) throws CampoValidoException
    {
        return sistema.getEmpregadoPorNome(nome, id);
    }

    public void removerEmpregado (String emp) throws EmpregadoNaoExisteException, CampoValidoException {
        sistema.removerEmpregado(emp);
    }
    public String getHorasNormaisTrabalhadas (String emp, String dataInicial, String dataFinal) throws CampoValidoException, EmpregadoNaoExisteException
    {
        return sistema.getHorasNormaisTrabalhadas(emp, dataInicial, dataFinal);
    }
    public String getHorasExtrasTrabalhadas (String emp, String dataInicial, String dataFinal) throws CampoValidoException, EmpregadoNaoExisteException
    {
        return sistema.getHorasExtrasTrabalhadas(emp, dataInicial, dataFinal);
    }
    public void lancaCartao (String emp, String data, String horas) throws CampoValidoException
    {
        sistema.lancaCartao(emp, data, horas);
    }
    public void lancaVenda (String emp, String data, String valor) throws CampoValidoException
    {
        sistema.lancaVenda(emp, data, valor);
    }

    public String getVendasRealizadas (String emp, String dataInicial, String dataFinal) throws CampoValidoException, EmpregadoNaoExisteException
    {
        return sistema.getVendas(emp, dataInicial, dataFinal);
    }

    // Para alterar atributos simples (nome, endereco, salario, etc.)
    public void alteraEmpregado(String emp, String atributo, String valor) throws Exception {
        sistema.alteraEmpregado(emp, atributo, valor, null, null, null, null, null, null);
    }

    // Para alterar o tipo para comissionado
    public void alteraEmpregado(String emp, String atributo, String valor, String comissao) throws Exception {
        sistema.alteraEmpregado(emp, atributo, valor, null, null, comissao, null, null, null);
    }

    // Para alterar o status de sindicalizado
    public void alteraEmpregado(String emp, String atributo, boolean valor, String idSindicato, String taxaSindical) throws Exception {
        sistema.alteraEmpregado(emp, atributo, String.valueOf(valor), idSindicato, taxaSindical, null, null, null, null);
    }

    // Para alterar o método de pagamento para banco
    public void alteraEmpregado(String emp, String atributo, String valor, String banco, String agencia, String contaCorrente) throws Exception {
        sistema.alteraEmpregado(emp, atributo, valor, null, null, null, banco, agencia, contaCorrente);
    }

    public void lancaTaxaServico(String membro, String data, String valor) throws CampoValidoException
    {
        sistema.lancaTaxaServico(membro, data, valor);
    }

    public String getTaxasServico (String emp, String dataInicial, String dataFinal) throws CampoValidoException
    {
        return sistema.getTaxasServico(emp, dataInicial, dataFinal);
    }
}

