package br.ufal.ic.p2.wepayu.commands;
import br.ufal.ic.p2.wepayu.models.*;

import java.util.ArrayList;

public class LancaCartaoCommand implements Command
{
    private SistemaFolha sistema;
    private String emp, data, horas;
    private ArrayList<CartaoPonto> cartoesDePontoOriginal = new ArrayList<>();

    /**
     * Construtor de LancaCartaoCommand
     * @param sistema
     * @param emp
     * @param data
     * @param horas
     */
    public LancaCartaoCommand(SistemaFolha sistema, String emp, String data, String horas)
    {
        this.sistema = sistema;
        this.emp = emp;
        this.data = data;
        this.horas = horas;
    }

    /**
     * Metodo para executar o lancamento de cartao
     * @throws Exception
     */
    @Override
    public void execute() throws Exception
    {
        this.cartoesDePontoOriginal = sistema.lancaCartao(emp, data, horas);
    }

    /**
     * Metodo para desfazer o lancamento de cartao
     * @throws Exception
     */
    @Override
    public void undo() throws Exception
    {
        sistema.restauraCartoes(emp, cartoesDePontoOriginal);
    }
}
