package br.ufal.ic.p2.wepayu.commands;

import br.ufal.ic.p2.wepayu.models.*;

import java.util.Map;

public class ZerarSistemaCommand implements Command
{
    SistemaFolha sistema;
    Map<String, Empregado> empregados;

    public ZerarSistemaCommand(SistemaFolha sistema)
    {
        this.sistema = sistema;
    }
    public void execute()
    {
        this.empregados = this.sistema.zerarSistema();
    }

    public void undo()
    {
        this.sistema.restaurarEmpregados(empregados);
    }

}
