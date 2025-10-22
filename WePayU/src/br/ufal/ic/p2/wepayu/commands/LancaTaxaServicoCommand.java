package br.ufal.ic.p2.wepayu.commands;

import br.ufal.ic.p2.wepayu.Exception.CampoValidoException;
import br.ufal.ic.p2.wepayu.models.*;
import java.util.*;
public class LancaTaxaServicoCommand implements Command
{
    private SistemaFolha sistema;
    String membro, data, valor;
    ArrayList<TaxaServico> original;

    /**
     * Construtor para LancaTaxaServicoCommand
     * @param sistema
     * @param membro
     * @param data
     * @param valor
     */
    public LancaTaxaServicoCommand (SistemaFolha sistema, String membro, String data, String valor)
    {
        this.sistema = sistema;
        this.membro = membro;
        this.data = data;
        this.valor = valor;
    }

    @Override
    public void execute () throws CampoValidoException
    {
        this.original = sistema.lancaTaxaServico(membro, data, valor);
    }

    @Override
    public void undo()
    {
        sistema.restauraTaxasServico(membro, original);
    }
}
