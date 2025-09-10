package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.SistemaFolha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Facade
{
    SistemaFolha sistema = new SistemaFolha();
    //zerando o sistema
    public void zerarSistema()
    {
        sistema.zerarSistema();
    }

    // metodo para pegar algum atributo do empregado
    public String getAtributoEmpregado (String emp, String atributo) throws EmpregadoNaoExisteException
    {
        return sistema.getAtributoEmpregado(emp, atributo);
    }

    // metodo para zerar o sistema
    public void criarEmpregado (String nome, String endereco, String tipo, int salario)
    {

    }
}
