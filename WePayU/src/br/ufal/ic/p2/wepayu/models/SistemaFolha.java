package br.ufal.ic.p2.wepayu.models;

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
    public String getAtributoEmpregado (String emp, String atributo)
    {
        Empregado empregado = empregados.get(emp);
        switch (atributo)
        {
            case "nome":
                return empregado.getNome();
            case "endereco":
                return empregado.getEndereco();
            case "tipo":
                return empregado.getTipo();
            case "salario":
                return String.valueOf(empregado.getSalario());
            default:
                throw new Exception()
        }
    }
}
