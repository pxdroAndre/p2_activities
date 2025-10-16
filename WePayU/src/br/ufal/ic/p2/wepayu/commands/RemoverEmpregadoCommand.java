package br.ufal.ic.p2.wepayu.commands;
import br.ufal.ic.p2.wepayu.models.SistemaFolha;

public class RemoverEmpregadoCommand implements Command
{
    private SistemaFolha sistema;
    private String empregado;


    public RemoverEmpregadoCommand(String empregado)
    {
        this.empregado = empregado;
    }


    @Override
    public void execute() throws Exception
    {
        this.sistema.removerEmpregado(empregado);
    }

    @Override
    public void undo()
    {

    }
}
