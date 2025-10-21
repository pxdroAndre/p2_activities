package br.ufal.ic.p2.wepayu.commands;
import br.ufal.ic.p2.wepayu.models.*;

public class RemoverEmpregadoCommand implements Command
{
    private SistemaFolha sistema;
    private String empregado;
    private Empregado removido;


    public RemoverEmpregadoCommand(String empregado)
    {
        this.empregado = empregado;
    }


    @Override
    public void execute() throws Exception
    {
        this.removido = this.sistema.removerEmpregado(empregado);
    }

    @Override
    public void undo()
    {

    }
}
