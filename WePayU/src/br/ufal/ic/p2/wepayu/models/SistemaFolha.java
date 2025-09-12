package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.CampoValidoException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Locale;
import java.text.NumberFormat;
import java.util.Objects;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.time.format.DateTimeFormatter;

public class SistemaFolha
{

    //criação do hashmap de empregados e id
    private int id = 1;
    private Map<String, Empregado> empregados = new HashMap<>();

    //locale do BR para formatar o número
    Locale localeBrasil = new Locale("pt", "BR");
    NumberFormat formatador = NumberFormat.getNumberInstance(localeBrasil);

    // construtor
    public SistemaFolha()
    {
        try
        {
            // tenta abrir o XML
            FileInputStream fis = new FileInputStream("database.xml");

            // instancia o decoder
            XMLDecoder decoder = new XMLDecoder(fis);

            // recriacao do hashmap
            this.empregados = (Map<String, Empregado>) decoder.readObject();

            // correcao da contagem de id
            this.id = this.empregados.size() + 1;

            decoder.close();
        }
        catch (FileNotFoundException e)
        {
            this.empregados = new HashMap<>();
            this.id = 1;
        }
    }

    // função de zerar sistema
    public void zerarSistema ()
    {

        empregados.clear();
        id = 1;
    }

    // metodo de encerrar sistema
    public void encerrarSistema ()
    {
        try
        {
            // definindo arquivo de saida
            FileOutputStream fos = new FileOutputStream("database.xml");
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            // criando o encoder
            XMLEncoder encoder = new XMLEncoder(bos);

            //escrevendo o hashMap
            encoder.writeObject(this.empregados);
            //encerra p encoder
            encoder.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
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
            String retorno = String.valueOf(formatador.format(comissionado.getComissao()));
            return retorno;
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

    public String getEmpregadoPorNome (String nome, int indice) throws CampoValidoException
    {
        // Cria uma lista para armazenar os IDs dos empregados que correspondem ao nome fornecido.
        java.util.List<String> matchingIds = new java.util.ArrayList<>();

        // Itera sobre o mapa de empregados para encontrar correspondências de nome.
        for (java.util.Map.Entry<String, Empregado> entry : empregados.entrySet()) {
            if (entry.getValue().getNome().equals(nome)) {
                matchingIds.add(entry.getKey());
            }
        }

        // Ordena a lista de IDs em ordem crescente. Isso garante que a seleção pelo índice
        // seja previsível e consistente, baseada na ordem de criação dos empregados.
        matchingIds.sort((id1, id2) -> Integer.compare(Integer.parseInt(id1), Integer.parseInt(id2)));

        // Verifica se o índice fornecido eh valido
        if (indice > matchingIds.size() || indice <= 0) {
            throw new CampoValidoException("Nao ha empregado com esse nome.");
        }

        // Retorna o ID do empregado na posição do índice solicitado (ajustado para 0-based).
        return matchingIds.get(indice - 1);
    }

    public void removerEmpregado (String id) throws EmpregadoNaoExisteException, CampoValidoException
    {
        // checando se o id ta preenchido
        if (Objects.equals(id, "")) throw new CampoValidoException("Identificacao do empregado nao pode ser nula.");
        // lendo o dado do empregado e verificando se existe
        Empregado empregado = empregados.get(id);
        if (empregado == null) throw new EmpregadoNaoExisteException();
        this.empregados.remove(id); // remove do Hash
    }

    public void lancaCartao (String id, String data, String horas) throws CampoValidoException
    {
        // checando se o id ta preenchido
        if (Objects.equals(id, "")) throw new CampoValidoException("Identificacao do empregado nao pode ser nula.");
        // checando se eh horista
        Empregado empregado = empregados.get(id);
        if (empregado == null) throw new CampoValidoException("Empregado nao existe."); // busca o empregado e verifica se existe

        if (empregado instanceof EmpregadoHorista horista)
        {
            // corrige a formatação do double
            horas = horas.replace(',', '.');
            double h = Double.parseDouble(horas);
            if (h <= 0) throw new CampoValidoException("Horas devem ser positivas."); // checa se eh positivo
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy"); // formata as datas
            try {
                LocalDate d = LocalDate.parse(data, formatter);
                horista.lancaCartao(d, horas); //lanca o cartao
            }
            catch (DateTimeParseException e)
            {
                throw new CampoValidoException("Data invalida.");
            }
        }
        else throw new CampoValidoException("Empregado nao eh horista.");
    }

    public String getHorasNormaisTrabalhadas (String id, String inicio, String fim) throws  CampoValidoException, EmpregadoNaoExisteException
    {
        // checando se o id ta preenchido
        if (Objects.equals(id, "")) throw new CampoValidoException("Identificacao do empregado nao pode ser nula.");
        // lendo o dado do empregado e verificando se existe
        Empregado empregado = empregados.get(id);
        if (empregado == null) throw new EmpregadoNaoExisteException();
        // checando se eh horista
        if (empregado instanceof EmpregadoHorista horista) return horista.getHorasNormaisTrabalhadas(inicio, fim);
        else throw new CampoValidoException("Empregado nao eh horista.");
    }
}
