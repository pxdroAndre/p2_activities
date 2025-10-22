package br.ufal.ic.p2.wepayu.commands;
import br.ufal.ic.p2.wepayu.models.*;
import java.util.*;

public class RodaFolhaCommand implements Command
{
    private SistemaFolha sistema;
    private String data;
    private String saida;
    private Map<String, Empregado> original;

    public RodaFolhaCommand (SistemaFolha sistema, String data, String saida)
    {
        this.sistema = sistema;
        this.data = data;
        this.saida = saida;
    }

    @Override
    public void execute() throws Exception
    {
        this.original = this.sistema.rodaFolha(data, saida);
    }
    @Override
    public void undo()
    {
        this.sistema.restaurarEmpregados(this.original);
    }
}
