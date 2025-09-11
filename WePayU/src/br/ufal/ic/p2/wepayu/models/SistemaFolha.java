package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.CampoValidoException;

import java.util.HashMap;
import java.util.Map;
import java.util.Locale;
import java.text.NumberFormat;
import java.util.Objects;

public class SistemaFolha
{
    //criação do hashmap de empregados e id
    private int id = 1;
    private final Map<String, Empregado> empregados = new HashMap<>();

    //locale do BR para formatar o número
    Locale localeBrasil = new Locale("pt", "BR");
    NumberFormat formatador = NumberFormat.getNumberInstance(localeBrasil);

    // função de zerar sistema
    public void zerarSistema ()
    {
        empregados.clear();
        id = 1;
    }
    // metodo para achar as excecoes especificas dos tipos nao comissionados
    public static void acharExcecoes (String nome, String endereco, String tipo, String sal) throws CampoValidoException
    {
        // checando campo nulo
        if (Objects.equals(nome, "")) throw new CampoValidoException("Nome nao pode ser nulo.");
        if (Objects.equals(endereco, "")) throw new CampoValidoException("Endereco nao pode ser nulo.");
        if (Objects.equals(sal, "")) throw new CampoValidoException("Salario nao pode ser nulo.");
        // checa se o salario eh numerico
        try
        {
            // corrige a formatação do double
            sal = sal.replace(',', '.');
            double salario = Double.parseDouble(sal);
            if (salario < 0) throw new CampoValidoException("Salario deve ser nao-negativo.");
        }
        catch (NumberFormatException e)
        {
            throw new CampoValidoException("Salario deve ser numerico.");
        }


        // checando validez do tipo
        if (Objects.equals(tipo, "comissionado")) throw new CampoValidoException("Tipo nao aplicavel.");
        if ((!Objects.equals(tipo, "horista")) &&
                (!Objects.equals(tipo, "assalariado")) )
        {
            throw new CampoValidoException("Tipo invalido.");
        }
    }

    // metodo para criar empregado nao comissionado
    public String criarEmpregado (String nome, String endereco, String tipo, String sal)
            throws CampoValidoException
    {
        // checando excecoes
        SistemaFolha.acharExcecoes(nome, endereco, tipo, sal);

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
    // metodo para achar excecoes do tipo comissionado
    public static void acharExcecoes (String nome, String endereco, String tipo, String sal, String comissao)
            throws CampoValidoException
    {
        // valida a comissao
        if (Objects.equals(comissao, "")) throw new CampoValidoException("Comissao nao pode ser nula.");
        try
        {
            // corrige a formatação do double
            comissao = comissao.replace(',', '.');
            double com = Double.parseDouble(comissao);
            if (com < 0.00) throw new CampoValidoException("Comissao deve ser nao-negativa.");
        }
        catch (NumberFormatException e)
        {
            throw new CampoValidoException("Comissao deve ser numerica.");
        }
        // valida o tipo
        if ((!Objects.equals(tipo, "comissionado")))
        {
            throw new CampoValidoException("Tipo nao aplicavel.");
        }
        // valida as demais excecoes
        try
        {
            SistemaFolha.acharExcecoes(nome, endereco, tipo, sal);
        }
        catch (Exception e)
        {
            return;
        }
    }

    // metodo para criar empregado comissionado
    public String criarEmpregado (String nome, String endereco, String tipo, String sal, String comissao) throws CampoValidoException
    {
        // checando exceções
        SistemaFolha.acharExcecoes(nome, endereco, tipo, sal, comissao);

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
    public String getAtributoEmpregado (String emp, String atributo)
            throws EmpregadoNaoExisteException, CampoValidoException
    {
        // checando excecoes
        if (Objects.equals(emp, "")) throw new CampoValidoException("Identificacao do empregado nao pode ser nula.");
        if
        (
            (!Objects.equals(atributo, "nome")) && (!Objects.equals(atributo, "endereco"))
            &&
            (!Objects.equals(atributo, "tipo")) && (!Objects.equals(atributo, "salario"))
            &&
            (!Objects.equals(atributo, "sindicalizado")) && (!Objects.equals(atributo, "comissao"))
        )
        {
            throw new CampoValidoException("Atributo nao existe.");
        }
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