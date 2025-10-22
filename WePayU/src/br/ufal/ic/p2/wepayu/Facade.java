package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.CampoValidoException;
import br.ufal.ic.p2.wepayu.commands.CriarEmpregadoCommand;
import br.ufal.ic.p2.wepayu.commands.RemoverEmpregadoCommand;
import br.ufal.ic.p2.wepayu.models.*;
import br.ufal.ic.p2.wepayu.models.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.SistemaFolha;

/**
 * Fachada (Facade) para o sistema de folha de pagamento WePayU.
 * <p>
 * Esta classe serve como uma interface simplificada para todas as operações
 * do sistema. Ela delega as chamadas para a classe {@link SistemaFolha}, que
 * contém a lógica de negócio principal. É o único ponto de entrada para os
 * testes automatizados do EasyAccept.
 * </p>
 * @author Mr. Dude
 * @version 1.0
 */
public class Facade
{
    SistemaFolha sistema = new SistemaFolha();

    /**
     * Limpa todos os dados do sistema, reiniciando o estado da aplicação.
     */
    public void zerarSistema()
    {
        sistema.zerarSistema();
    }

    /**
     * Desfaz a última operação que modificou o estado do sistema.
     * @throws Exception se não houver comando para desfazer.
     */
    public void undo() throws Exception {
        sistema.undo();
    }

    /**
     * Refaz a última operação desfeita.
     * @throws Exception se não houver comando para refazer.
     */
    public void redo() throws Exception {
        sistema.redo();
    }

    /**
     * Recupera o valor de um atributo de um empregado específico.
     *
     * @param emp O ID do empregado a ser consultado.
     * @param atributo O nome do atributo desejado.
     * @return O valor do atributo como String.
     * @throws EmpregadoNaoExisteException se o empregado não for encontrado.
     * @throws CampoValidoException se o atributo não existir ou for inválido.
     */
    public String getAtributoEmpregado (String emp, String atributo) throws Exception
    {
        return sistema.getAtributoEmpregado(emp, atributo);
    }

    /**
     * Cria um novo empregado não comissionado (horista ou assalariado).
     *
     * @param nome O nome do empregado.
     * @param endereco O endereço do empregado.
     * @param tipo O tipo de contrato ("horista" ou "assalariado").
     * @param salario O salário ou valor-hora do empregado.
     * @return O ID único do novo empregado.
     * @throws CampoValidoException se algum dos parâmetros for inválido.
     */
    public String criarEmpregado (String nome, String endereco, String tipo, String salario) throws Exception
    {
        CriarEmpregadoCommand command = new CriarEmpregadoCommand(sistema, nome, endereco, tipo, salario);
        sistema.executarComando(command);
        return command.getIdCriado();
    }

    /**
     * Cria um novo empregado comissionado.
     *
     * @param nome O nome do empregado.
     * @param endereco O endereço do empregado.
     * @param tipo O tipo de contrato ("comissionado").
     * @param salario O salário base do empregado.
     * @param comissao A taxa de comissão sobre as vendas.
     * @return O ID único do novo empregado.
     * @throws CampoValidoException se algum dos parâmetros for inválido.
     */
    public String criarEmpregado (String nome, String endereco, String tipo, String salario, String comissao) throws Exception
    {
        CriarEmpregadoCommand command = new CriarEmpregadoCommand(sistema, nome, endereco, tipo, salario, comissao);
        sistema.executarComando(command);
        return command.getIdCriado();
    }

    /**
     * Salva o estado atual do sistema em um arquivo persistente e encerra.
     */
    public void encerrarSistema ()
    {
        sistema.encerrarSistema();
    }

    /**
     * Busca o ID de um empregado pelo seu nome.
     *
     * @param nome O nome do empregado a ser buscado.
     * @param id A ocorrência do nome (ex: 1 para o primeiro, 2 para o segundo).
     * @return O ID do empregado encontrado.
     * @throws CampoValidoException se nenhum empregado com o nome e índice for encontrado.
     */
    public String getEmpregadoPorNome (String nome, int id) throws Exception
    {
        return sistema.getEmpregadoPorNome(nome, id);
    }

    /**
     * Remove um empregado do sistema.
     *
     * @param emp O ID do empregado a ser removido.
     * @throws EmpregadoNaoExisteException se o empregado não for encontrado.
     * @throws CampoValidoException se o ID for nulo ou vazio.
     */
    public void removerEmpregado (String emp) throws Exception {
        RemoverEmpregadoCommand command = new RemoverEmpregadoCommand(emp, this.sistema);
        sistema.executarComando(command);
    }

    /**
     * Retorna o total de horas normais trabalhadas por um horista em um período.
     *
     * @param emp O ID do empregado.
     * @param dataInicial A data de início do período.
     * @param dataFinal A data de fim do período.
     * @return O total de horas normais como String.
     * @throws CampoValidoException se o empregado não for horista ou as datas forem inválidas.
     * @throws EmpregadoNaoExisteException se o empregado não for encontrado.
     */
    public String getHorasNormaisTrabalhadas (String emp, String dataInicial, String dataFinal) throws Exception
    {
        return sistema.getHorasNormaisTrabalhadas(emp, dataInicial, dataFinal);
    }

    /**
     * Retorna o total de horas extras trabalhadas por um horista em um período.
     *
     * @param emp O ID do empregado.
     * @param dataInicial A data de início do período.
     * @param dataFinal A data de fim do período.
     * @return O total de horas extras como String.
     * @throws CampoValidoException se o empregado não for horista ou as datas forem inválidas.
     * @throws EmpregadoNaoExisteException se o empregado não for encontrado.
     */
    public String getHorasExtrasTrabalhadas (String emp, String dataInicial, String dataFinal) throws Exception
    {
        return sistema.getHorasExtrasTrabalhadas(emp, dataInicial, dataFinal);
    }

    /**
     * Lança um cartão de ponto para um empregado horista.
     *
     * @param emp O ID do empregado.
     * @param data A data do registro.
     * @param horas O total de horas trabalhadas.
     * @throws CampoValidoException se os dados forem inválidos.
     */
    public void lancaCartao (String emp, String data, String horas) throws Exception
    {
        sistema.lancaCartao(emp, data, horas);
    }

    /**
     * Lança um resultado de venda para um empregado comissionado.
     *
     * @param emp O ID do empregado.
     * @param data A data da venda.
     * @param valor O valor da venda.
     * @throws CampoValidoException se os dados forem inválidos.
     */
    public void lancaVenda (String emp, String data, String valor) throws Exception
    {
        sistema.lancaVenda(emp, data, valor);
    }

    /**
     * Retorna o total de vendas realizadas por um comissionado em um período.
     *
     * @param emp O ID do empregado.
     * @param dataInicial A data de início do período.
     * @param dataFinal A data de fim do período.
     * @return O valor total das vendas como String.
     * @throws CampoValidoException se o empregado não for comissionado ou as datas forem inválidas.
     * @throws EmpregadoNaoExisteException se o empregado não for encontrado.
     */
    public String getVendasRealizadas (String emp, String dataInicial, String dataFinal) throws Exception
    {
        return sistema.getVendas(emp, dataInicial, dataFinal);
    }

    /**
     * Altera um atributo simples de um empregado.
     *
     * @param emp O ID do empregado.
     * @param atributo O nome do atributo a ser alterado.
     * @param valor O novo valor do atributo.
     * @throws Exception se ocorrer um erro de validação.
     */
    public void alteraEmpregado(String emp, String atributo, String valor) throws Exception {
        sistema.alteraEmpregado(emp, atributo, valor, null, null, null, null, null, null);
    }

    /**
     * Altera o tipo de um empregado para comissionado.
     *
     * @param emp O ID do empregado.
     * @param atributo Deve ser "tipo".
     * @param valor Deve ser "comissionado".
     * @param comissao A taxa de comissão a ser definida.
     * @throws Exception se ocorrer um erro de validação.
     */
    public void alteraEmpregado(String emp, String atributo, String valor, String comissao) throws Exception {
        sistema.alteraEmpregado(emp, atributo, valor, null, null, comissao, null, null, null);
    }

    /**
     * Altera o status de sindicalização de um empregado.
     *
     * @param emp O ID do empregado.
     * @param atributo Deve ser "sindicalizado".
     * @param valor {@code true} para sindicalizar, {@code false} para remover.
     * @param idSindicato O ID do sindicato (se valor for true).
     * @param taxaSindical A taxa sindical (se valor for true).
     * @throws Exception se ocorrer um erro de validação.
     */
    public void alteraEmpregado(String emp, String atributo, boolean valor, String idSindicato, String taxaSindical) throws Exception {
        sistema.alteraEmpregado(emp, atributo, String.valueOf(valor), idSindicato, taxaSindical, null, null, null, null);
    }

    /**
     * Altera o método de pagamento para banco.
     *
     * @param emp O ID do empregado.
     * @param atributo Deve ser "metodoPagamento".
     * @param valor Deve ser "banco".
     * @param banco O nome do banco.
     * @param agencia O número da agência.
     * @param contaCorrente O número da conta corrente.
     * @throws Exception se ocorrer um erro de validação.
     */
    public void alteraEmpregado(String emp, String atributo, String valor, String banco, String agencia, String contaCorrente) throws Exception {
        sistema.alteraEmpregado(emp, atributo, valor, null, null, null, banco, agencia, contaCorrente);
    }

    /**
     * Lança uma taxa de serviço para um membro do sindicato.
     *
     * @param membro O ID do sindicato do membro.
     * @param data A data da cobrança.
     * @param valor O valor da taxa.
     * @throws CampoValidoException se os dados forem inválidos.
     */
    public void lancaTaxaServico(String membro, String data, String valor) throws Exception
    {
        sistema.lancaTaxaServico(membro, data, valor);
    }

    /**
     * Retorna o total de taxas de serviço de um empregado em um período.
     *
     * @param emp O ID do empregado.
     * @param dataInicial A data de início do período.
     * @param dataFinal A data de fim do período.
     * @return O total das taxas de serviço como String.
     * @throws CampoValidoException se o empregado não for sindicalizado ou as datas forem inválidas.
     */
    public String getTaxasServico (String emp, String dataInicial, String dataFinal) throws Exception
    {
        return sistema.getTaxasServico(emp, dataInicial, dataFinal);
    }

    /**
     * Calcula o valor total bruto da folha de pagamento para uma data específica.
     *
     * @param data A data para a qual a folha será calculada.
     * @return O valor total formatado como String.
     * @throws Exception se ocorrer um erro durante o cálculo.
     */
    public String totalFolha(String data) throws Exception
    {
        return sistema.totalFolha(data);
    }

    /**
     * Roda a folha de pagamento para uma data e gera um arquivo de saída.
     *
     * @param data A data do pagamento.
     * @param saida O nome do arquivo a ser gerado com o resumo.
     * @throws Exception se ocorrer um erro durante o processamento.
     */
    public void rodaFolha(String data, String saida) throws Exception {
        sistema.rodaFolha(data, saida);
    }
}