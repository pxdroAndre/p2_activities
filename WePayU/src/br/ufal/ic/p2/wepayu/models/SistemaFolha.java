package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;

import java.util.HashMap;
import java.util.Map;

public class SistemaFolha
{
    //criação do hashmap de empregados
    private Map<String, Empregado> empregados = new HashMap<>();
    // função de zerar sistema
    public void zerarSistema ()
    {
        empregados.clear();
    }

    // funcao para pegar atributo do empregado
    public String getAtributoEmpregado (String emp, String atributo) throws EmpregadoNaoExisteException
    {
        Empregado empregado = empregados.get(emp);
        if (empregado == null) throw new EmpregadoNaoExisteException();
        else
        {
            return switch (atributo)
            {
                case "nome" -> empregado.getNome();
                case "endereco" -> empregado.getEndereco();
                case "tipo" -> empregado.getTipo();
                case "salario" -> String.valueOf(empregado.getSalario());
                default -> "none";
            };
        }

    }
}