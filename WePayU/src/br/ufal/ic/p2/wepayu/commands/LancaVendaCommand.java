package br.ufal.ic.p2.wepayu.commands;
import br.ufal.ic.p2.wepayu.models.*;

import java.util.ArrayList;

public class LancaVendaCommand implements Command
{
    private SistemaFolha sistema;
    private String emp, data, valor;
    private ArrayList<ResultadoDeVenda> vendasOriginal = new ArrayList<>();

    /**
     * Construtor de LancaCartaoCommand
     * @param sistema
     * @param emp
     * @param data
     * @param valor
     */
    public LancaVendaCommand(SistemaFolha sistema, String emp, String data, String valor)
    {
        this.sistema = sistema;
        this.emp = emp;
        this.data = data;
        this.valor = valor;
    }

    /**
     * Metodo para executar o lancamento de cartao
     * @throws Exception
     */
    @Override
    public void execute() throws Exception
    {
        this.vendasOriginal = sistema.lancaVenda(emp, data, valor);
    }

    /**
     * Metodo para desfazer o lancamento de cartao
     * @throws Exception
     */
    @Override
    public void undo() throws Exception
    {
        sistema.restauraVendas(emp, vendasOriginal);
    }
}
