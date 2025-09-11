package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.NomeNuloException;

import java.util.HashMap;
import java.util.Map;
import java.util.Locale;
import java.text.NumberFormat;
import java.util.Objects;

public class SistemaFolha
{
    //criação do hashmap de empregados e id
    private int id = 1;
    private Map<String, Empregado> empregados = new HashMap<>();

    //locale do BR para formatar o número
    Locale localeBrasil = new Locale("pt", "BR");
    NumberFormat formatador = NumberFormat.getNumberInstance(localeBrasil);

    // função de zerar sistema
    public void zerarSistema ()
    {
        empregados.clear();
        id = 1;
    }

    // metodo para criar empregado nao comissionado
    public String criarEmpregado (String nome, String endereco, String tipo, String sal) throws NomeNuloException
    {
        if (Objects.equals(nome, "")) throw new NomeNuloException();
        // transformando o id em string
        String novoID = String.valueOf(id);
        // corrige a formatação do double
        sal = sal.replace(',', '.');
        double salario = Double.parseDouble(sal);
        // cria o empregado
        switch (tipo)
        {
            case "assalariado":
                EmpregadoAssalariado novoAssalariado = new EmpregadoAssalariado(nome, endereco, tipo, salario);
                empregados.put(novoID, novoAssalariado); //adicionando no hashmap
                break;
            case "horista":
                EmpregadoHorista novoHorista = new EmpregadoHorista(nome, endereco, tipo, salario);
                empregados.put(novoID, novoHorista); //adicionando no hashmap
                break;
        };
        id++;
        return novoID;
    }
    // metodo para criar empregado comissionado
    public String criarEmpregado (String nome, String endereco, String tipo, String sal, String comissao) throws NomeNuloException
    {
        // checando nome nulo
        if (Objects.equals(nome, "")) throw new NomeNuloException();

        // transformando o id em string
        String novoID = String.valueOf(id);
        // corrige a formatação do double
        sal = sal.replace(',', '.');
        comissao = comissao.replace(',', '.');

        double salario = Double.parseDouble(sal);
        double com = Double.parseDouble(comissao);
        // cria o empregado
        EmpregadoComissionado novoComissionado = new EmpregadoComissionado(nome, endereco, tipo, salario, com);
        empregados.put(novoID, novoComissionado); //adicionando no hashmap

        id++;
        return novoID;
    }
    // funcao para pegar atributo do empregado
    public String getAtributoEmpregado (String emp, String atributo) throws EmpregadoNaoExisteException
    {
        //ajustando a formatação dos numeros
        formatador.setGroupingUsed(false);
        formatador.setMinimumFractionDigits(2);

        // lendo o dado do empregado e verificando se existe
        Empregado empregado = empregados.get(emp);
        if (empregado == null) throw new EmpregadoNaoExisteException();

        // verificando se eh comissionado
        if (Objects.equals(empregado.getTipo(), "comissionado") && (Objects.equals(atributo, "comissao"))
                && (empregado instanceof EmpregadoComissionado comissionado))
        {
            return String.valueOf(formatador.format(comissionado.getComissao()));
        }
        else
        {
            return switch (atributo)
            {
                case "nome" -> empregado.getNome();
                case "endereco" -> empregado.getEndereco();
                case "tipo" -> empregado.getTipo();
                case "salario" -> String.valueOf(formatador.format(empregado.getSalario()));
                case "sindicalizado" -> String.valueOf(empregado.getSindicalizado());

                default -> "none";
            };
        }

    }
}