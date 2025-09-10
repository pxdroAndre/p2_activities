package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;

import java.util.HashMap;
import java.util.Map;

public class SistemaFolha
{
    //criação do hashmap de empregados e id
    private int id = 1;
    private Map<String, Empregado> empregados = new HashMap<>();
    // função de zerar sistema
    public void zerarSistema ()
    {
        empregados.clear();
        id = 1;
    }

    // metodo para criar empregado
    public String criarEmpregado (String nome, String endereco, String tipo, int salario)
    {
        // transformando o id em string
        String novoID = String.valueOf(id);
        Empregado novoEmpregado = new Empregado(nome, endereco, tipo, salario);
        empregados.put(novoID, novoEmpregado); //adicionando no hashmap
        id++;
        return novoID;
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
                case "salario" -> String.valueOf(empregado.getSalario() + ",00");
                default -> "none";
            };
        }

    }
}