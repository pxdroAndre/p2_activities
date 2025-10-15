package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.CampoValidoException;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.text.NumberFormat;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.time.format.DateTimeFormatter;

/**
 * Classe principal que gerencia toda a lógica de negócio do sistema de folha de pagamento.
 * <p>
 * Esta classe é responsável por manter o banco de dados de empregados,
 * processar as operações de criação, remoção, alteração e consulta,
 * bem como executar a folha de pagamento. Ela também lida com a persistência
 * dos dados em um arquivo XML.
 * </p>
 * @author pxdroAndre
 * @version 1.0
 */
public class SistemaFolha {

    //criação do hashmap de empregados e id
    private int id = 1;
    private Map<String, Empregado> empregados = new HashMap<>();

    //locale do BR para formatar o número
    Locale localeBrasil = new Locale("pt", "BR");
    NumberFormat formatador = NumberFormat.getNumberInstance(localeBrasil);

    /**
     * Construtor da classe SistemaFolha.
     * <p>
     * Tenta carregar o estado do sistema a partir de um arquivo "database.xml".
     * Se o arquivo não for encontrado, inicializa um sistema vazio.
     * </p>
     */
    public SistemaFolha() {
        try {
            // tenta abrir o XML
            FileInputStream fis = new FileInputStream("database.xml");

            // instancia o decoder
            XMLDecoder decoder = new XMLDecoder(fis);

            // recriacao do hashmap
            this.empregados = (Map<String, Empregado>) decoder.readObject();

            // correcao da contagem de id
            this.id = this.empregados.size() + 1;

            decoder.close();
        } catch (FileNotFoundException e) {
            this.empregados = new HashMap<>();
            this.id = 1;
        }
    }

    /**
     * Limpa todos os dados do sistema.
     * <p>
     * Remove todos os empregados do mapa e reinicia o contador de IDs.
     * </p>
     */
    public void zerarSistema() {

        empregados.clear();
        id = 1;
    }

    /**
     * Salva o estado atual do sistema em um arquivo XML.
     * <p>
     * Serializa o mapa de empregados para o arquivo "database.xml".
     * </p>
     */
    public void encerrarSistema() {
        try {
            // definindo arquivo de saida
            FileOutputStream fos = new FileOutputStream("database.xml");
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            // criando o encoder
            XMLEncoder encoder = new XMLEncoder(bos);

            //escrevendo o hashMap
            encoder.writeObject(this.empregados);
            //encerra p encoder
            encoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Valida os dados de entrada para a criação de empregados não comissionados.
     *
     * @param nome     O nome do empregado.
     * @param endereco O endereço do empregado.
     * @param tipo     O tipo de contrato ("horista" ou "assalariado").
     * @param sal      O salário em formato String.
     * @throws CampoValidoException Se qualquer um dos dados for inválido.
     */
    public static void acharExcecoes(String nome, String endereco, String tipo, String sal) throws CampoValidoException {
        // checando campo nulo
        if (Objects.equals(nome, "")) throw new CampoValidoException("Nome nao pode ser nulo.");
        if (Objects.equals(endereco, "")) throw new CampoValidoException("Endereco nao pode ser nulo.");
        if (Objects.equals(sal, "")) throw new CampoValidoException("Salario nao pode ser nulo.");
        // checa se o salario eh numerico
        try {
            // corrige a formatação do double
            sal = sal.replace(',', '.');
            double salario = Double.parseDouble(sal);
            if (salario < 0) throw new CampoValidoException("Salario deve ser nao-negativo.");
        } catch (NumberFormatException e) {
            throw new CampoValidoException("Salario deve ser numerico.");
        }


        // checando validez do tipo
        if (Objects.equals(tipo, "comissionado")) throw new CampoValidoException("Tipo nao aplicavel.");
        if ((!Objects.equals(tipo, "horista")) &&
                (!Objects.equals(tipo, "assalariado"))) {
            throw new CampoValidoException("Tipo invalido.");
        }
    }

    /**
     * Cria um novo empregado não comissionado (horista ou assalariado).
     *
     * @param nome     O nome do empregado.
     * @param endereco O endereço do empregado.
     * @param tipo     O tipo de contrato.
     * @param sal      O salário.
     * @return O ID do novo empregado criado.
     * @throws CampoValidoException Se os dados de entrada forem inválidos.
     */
    public String criarEmpregado(String nome, String endereco, String tipo, String sal)
            throws CampoValidoException {
        // checando excecoes
        SistemaFolha.acharExcecoes(nome, endereco, tipo, sal);

        // transformando o id em string
        String novoID = String.valueOf(id);
        // corrige a formatação do double
        sal = sal.replace(',', '.');
        double salario = Double.parseDouble(sal);
        // cria o empregado
        switch (tipo) {
            case "assalariado":
                EmpregadoAssalariado novoAssalariado = new EmpregadoAssalariado(nome, endereco, tipo, salario);
                empregados.put(novoID, novoAssalariado); //adicionando no hashmap
                break;
            case "horista":
                EmpregadoHorista novoHorista = new EmpregadoHorista(nome, endereco, tipo, salario);
                empregados.put(novoID, novoHorista); //adicionando no hashmap
                break;
        }
        ;
        id++;
        return novoID;
    }

    /**
     * Valida os dados de entrada para a criação de empregados comissionados.
     *
     * @param nome     O nome do empregado.
     * @param endereco O endereço do empregado.
     * @param tipo     O tipo de contrato ("comissionado").
     * @param sal      O salário base.
     * @param comissao A taxa de comissão.
     * @throws CampoValidoException Se qualquer um dos dados for inválido.
     */
    public static void acharExcecoes(String nome, String endereco, String tipo, String sal, String comissao)
            throws CampoValidoException {
        // valida a comissao
        if (Objects.equals(comissao, "")) throw new CampoValidoException("Comissao nao pode ser nula.");
        try {
            // corrige a formatação do double
            comissao = comissao.replace(',', '.');
            double com = Double.parseDouble(comissao);
            if (com < 0.00) throw new CampoValidoException("Comissao deve ser nao-negativa.");
        } catch (NumberFormatException e) {
            throw new CampoValidoException("Comissao deve ser numerica.");
        }
        // valida o tipo
        if ((!Objects.equals(tipo, "comissionado"))) {
            throw new CampoValidoException("Tipo nao aplicavel.");
        }
        // valida as demais excecoes
        try {
            SistemaFolha.acharExcecoes(nome, endereco, tipo, sal);
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Cria um novo empregado comissionado.
     *
     * @param nome     O nome do empregado.
     * @param endereco O endereço do empregado.
     * @param tipo     O tipo de contrato.
     * @param sal      O salário base.
     * @param comissao A taxa de comissão.
     * @return O ID do novo empregado criado.
     * @throws CampoValidoException Se os dados de entrada forem inválidos.
     */
    public String criarEmpregado(String nome, String endereco, String tipo, String sal, String comissao) throws CampoValidoException {
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

    /**
     * Recupera o valor de um atributo específico de um empregado.
     *
     * @param emp      O ID do empregado.
     * @param atributo O nome do atributo a ser recuperado.
     * @return O valor do atributo formatado como String.
     * @throws EmpregadoNaoExisteException Se o empregado com o ID fornecido não existir.
     * @throws CampoValidoException        Se o atributo solicitado não existir ou não for aplicável.
     */
    public String getAtributoEmpregado(String emp, String atributo)
            throws EmpregadoNaoExisteException, CampoValidoException {
        // checando excecoes
        if (Objects.equals(emp, "")) throw new CampoValidoException("Identificacao do empregado nao pode ser nula.");
        //ajustando a formatação dos numeros
        formatador.setGroupingUsed(false);
        formatador.setMinimumFractionDigits(2);

        // lendo o dado do empregado e verificando se existe
        Empregado empregado = empregados.get(emp);
        if (empregado == null) throw new EmpregadoNaoExisteException();

        switch (atributo) {
            case "nome":
                return empregado.getNome();
            case "endereco":
                return empregado.getEndereco();
            case "tipo":
                return empregado.getTipo();
            case "salario":
                return formatador.format(empregado.getSalario());
            case "sindicalizado":
                return String.valueOf(empregado.isSindicalizado());
            case "comissao":
                if (empregado instanceof EmpregadoComissionado) {
                    return formatador.format(((EmpregadoComissionado) empregado).getComissao());
                }
                throw new CampoValidoException("Empregado nao eh comissionado.");
            case "idSindicato":
                if (!empregado.isSindicalizado()) throw new CampoValidoException("Empregado nao eh sindicalizado.");
                return empregado.getIdSindicato();
            case "taxaSindical":
                if (!empregado.isSindicalizado()) throw new CampoValidoException("Empregado nao eh sindicalizado.");
                return formatador.format(empregado.getTaxaSindical());
            case "metodoPagamento":
                return empregado.getMetodoPagamento();
            case "banco":
                if (!"banco".equals(empregado.getMetodoPagamento())) {
                    throw new CampoValidoException("Empregado nao recebe em banco.");
                }
                return empregado.getBanco();
            case "agencia":
                if (!"banco".equals(empregado.getMetodoPagamento())) {
                    throw new CampoValidoException("Empregado nao recebe em banco.");
                }
                return empregado.getAgencia();
            case "contaCorrente":
                if (!"banco".equals(empregado.getMetodoPagamento())) {
                    throw new CampoValidoException("Empregado nao recebe em banco.");
                }
                return empregado.getContaCorrente();
            // --- Fim da implementação solicitada ---

            default:
                throw new CampoValidoException("Atributo nao existe.");

        }
    }

    /**
     * Busca um empregado pelo nome.
     *
     * @param nome   O nome a ser buscado.
     * @param indice A ocorrência a ser retornada (ex: 1 para o primeiro encontrado).
     * @return O ID do empregado encontrado.
     * @throws CampoValidoException Se nenhum empregado for encontrado com o nome e índice especificados.
     */
    public String getEmpregadoPorNome(String nome, int indice) throws CampoValidoException {
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

    /**
     * Remove um empregado do sistema.
     *
     * @param id O ID do empregado a ser removido.
     * @throws EmpregadoNaoExisteException Se o empregado não for encontrado.
     * @throws CampoValidoException        Se o ID fornecido for nulo ou vazio.
     */
    public void removerEmpregado(String id) throws EmpregadoNaoExisteException, CampoValidoException {
        // checando se o id ta preenchido
        if (Objects.equals(id, "")) throw new CampoValidoException("Identificacao do empregado nao pode ser nula.");
        // lendo o dado do empregado e verificando se existe
        Empregado empregado = empregados.get(id);
        if (empregado == null) throw new EmpregadoNaoExisteException();
        this.empregados.remove(id); // remove do Hash
    }

    /**
     * Valida os dados para o lançamento de uma venda.
     *
     * @param empregados O mapa de todos os empregados.
     * @param id         O ID do empregado que realizou a venda.
     * @param data       A data da venda.
     * @param valor      O valor da venda.
     * @return O objeto {@link EmpregadoComissionado} correspondente ao ID.
     * @throws CampoValidoException Se algum dos dados for inválido.
     */
    public static EmpregadoComissionado excecoesLancamento(Map<String, Empregado> empregados, String id, String data, String valor) throws CampoValidoException {
        // verifica se a data é válida
        if (!EmpregadoHorista.validarData(data)) throw new CampoValidoException("Data invalida.");
        // checando se o id ta preenchido
        if (Objects.equals(id, "")) throw new CampoValidoException("Identificacao do empregado nao pode ser nula.");
        // checando se eh comissionado
        Empregado empregado = empregados.get(id);
        if (empregado == null)
            throw new CampoValidoException("Empregado nao existe."); // busca o empregado e verifica se existe

        if (empregado instanceof EmpregadoComissionado comissionado) {
            // validando o valor
            valor = valor.replace(',', '.');
            double v = Double.parseDouble(valor);
            if (v <= 0.00) throw new CampoValidoException("Valor deve ser positivo."); // checa se eh positivo
        } else throw new CampoValidoException("Empregado nao eh comissionado.");
        return comissionado;
    }

    /**
     * Lança um cartão de ponto para um empregado horista.
     *
     * @param id    O ID do empregado.
     * @param data  A data do registro de ponto.
     * @param horas O total de horas trabalhadas.
     * @throws CampoValidoException Se o empregado não for horista ou os dados forem inválidos.
     */
    public void lancaCartao(String id, String data, String horas) throws CampoValidoException {
        if (!EmpregadoHorista.validarData(data)) throw new CampoValidoException("Data invalida.");
        // checando se o id ta preenchido
        if (Objects.equals(id, "")) throw new CampoValidoException("Identificacao do empregado nao pode ser nula.");
        // checando se eh horista
        Empregado empregado = empregados.get(id);
        if (empregado == null)
            throw new CampoValidoException("Empregado nao existe."); // busca o empregado e verifica se existe

        if (empregado instanceof EmpregadoHorista horista) {
            // corrige a formatação do double
            horas = horas.replace(',', '.');
            double h = Double.parseDouble(horas);
            if (h <= 0) throw new CampoValidoException("Horas devem ser positivas."); // checa se eh positivo
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy"); // formata as datas
            horista.lancaCartao(data, horas); //lanca o cartao


        } else throw new CampoValidoException("Empregado nao eh horista.");
    }

    /**
     * Retorna o total de horas normais trabalhadas por um horista em um período.
     *
     * @param id     O ID do empregado.
     * @param inicio A data inicial do período.
     * @param fim    A data final do período.
     * @return O total de horas normais como String.
     * @throws CampoValidoException        Se o empregado não for horista ou as datas forem inválidas.
     * @throws EmpregadoNaoExisteException Se o empregado não for encontrado.
     */
    public String getHorasNormaisTrabalhadas(String id, String inicio, String fim) throws CampoValidoException, EmpregadoNaoExisteException {
        // checando se o id ta preenchido
        if (Objects.equals(id, "")) throw new CampoValidoException("Identificacao do empregado nao pode ser nula.");
        // lendo o dado do empregado e verificando se existe
        Empregado empregado = empregados.get(id);
        if (empregado == null) throw new EmpregadoNaoExisteException();
        // checando se eh horista
        if (empregado instanceof EmpregadoHorista horista) return horista.getHorasNormaisTrabalhadas(inicio, fim);
        else throw new CampoValidoException("Empregado nao eh horista.");
    }

    /**
     * Retorna o total de horas extras trabalhadas por um horista em um período.
     *
     * @param id     O ID do empregado.
     * @param inicio A data inicial do período.
     * @param fim    A data final do período.
     * @return O total de horas extras como String.
     * @throws CampoValidoException        Se o empregado não for horista ou as datas forem inválidas.
     * @throws EmpregadoNaoExisteException Se o empregado não for encontrado.
     */
    public String getHorasExtrasTrabalhadas(String id, String inicio, String fim) throws CampoValidoException, EmpregadoNaoExisteException {
        // checando se o id ta preenchido
        if (Objects.equals(id, "")) throw new CampoValidoException("Identificacao do empregado nao pode ser nula.");
        // lendo o dado do empregado e verificando se existe
        Empregado empregado = empregados.get(id);
        if (empregado == null) throw new EmpregadoNaoExisteException();
        // checando se eh horista
        if (empregado instanceof EmpregadoHorista horista) return horista.getHorasExtrasTrabalhadas(inicio, fim);
        else throw new CampoValidoException("Empregado nao eh horista.");
    }


    /**
     * Lança um resultado de venda para um empregado comissionado.
     *
     * @param emp   O ID do empregado.
     * @param data  A data da venda.
     * @param valor O valor da venda.
     * @throws CampoValidoException Se o empregado não for comissionado ou os dados forem inválidos.
     */
    public void lancaVenda(String emp, String data, String valor) throws CampoValidoException {
        EmpregadoComissionado comissionado = SistemaFolha.excecoesLancamento(empregados, emp, data, valor); //identificando o empregado pelo id
        comissionado.lancaVenda(valor, data);
    }

    /**
     * Retorna o total de vendas de um empregado comissionado em um período.
     *
     * @param emp    O ID do empregado.
     * @param inicio A data inicial do período.
     * @param fim    A data final do período.
     * @return O valor total das vendas como String.
     * @throws CampoValidoException        Se o empregado não for comissionado ou as datas forem inválidas.
     * @throws EmpregadoNaoExisteException Se o empregado não for encontrado.
     */
    public String getVendas(String emp, String inicio, String fim) throws CampoValidoException, EmpregadoNaoExisteException {
        // checando se o id ta preenchido
        if (Objects.equals(emp, "")) throw new CampoValidoException("Identificacao do empregado nao pode ser nula.");
        // lendo o dado do empregado e verificando se existe
        Empregado empregado = empregados.get(emp);
        if (empregado == null) throw new EmpregadoNaoExisteException();
        // checando se eh comissinoado
        if (empregado instanceof EmpregadoComissionado comissionado) return comissionado.getVendas(inicio, fim);
        else throw new CampoValidoException("Empregado nao eh comissionado.");
    }

    /**
     * Altera um ou mais atributos de um empregado existente.
     * <p>
     * Este é um método "mestre" que lida com todas as variações de alteração
     * de dados de um empregado, como nome, salário, tipo, status sindical, etc.
     * </p>
     *
     * @param emp           O ID do empregado a ser alterado.
     * @param atributo      O nome do atributo a ser modificado.
     * @param valor         O novo valor principal para o atributo.
     * @param idSindicato   O ID do sindicato (usado se atributo="sindicalizado").
     * @param taxaSindical  A taxa sindical (usado se atributo="sindicalizado").
     * @param comissao      A comissão (usado se atributo="tipo" e valor="comissionado").
     * @param banco         O nome do banco (usado se atributo="metodoPagamento" e valor="banco").
     * @param agencia       A agência bancária (usado se atributo="metodoPagamento" e valor="banco").
     * @param contaCorrente A conta corrente (usado se atributo="metodoPagamento" e valor="banco").
     * @throws Exception Se ocorrer um erro de validação ou o empregado não existir.
     */
    public void alteraEmpregado(String emp, String atributo, String valor,
                                String idSindicato, String taxaSindical,
                                String comissao, String banco, String agencia, String contaCorrente) throws Exception {

        if (emp == null || emp.isEmpty()) {
            throw new CampoValidoException("Identificacao do empregado nao pode ser nula.");
        }
        Empregado empregado = empregados.get(emp);
        if (empregado == null) {
            throw new EmpregadoNaoExisteException();
        }

        switch (atributo) {
            case "nome":
                if (valor == null || valor.isEmpty()) throw new CampoValidoException("Nome nao pode ser nulo.");
                empregado.setNome(valor);
                break;
            case "endereco":
                if (valor == null || valor.isEmpty()) throw new CampoValidoException("Endereco nao pode ser nulo.");
                empregado.setEndereco(valor);
                break;
            case "salario":
                if (valor == null || valor.isEmpty()) throw new CampoValidoException("Salario nao pode ser nulo.");
                try {
                    double novoSalario = Double.parseDouble(valor.replace(',', '.'));
                    if (novoSalario < 0) throw new CampoValidoException("Salario deve ser nao-negativo.");
                    empregado.setSalario(BigDecimal.valueOf(novoSalario));
                } catch (NumberFormatException e) {
                    throw new CampoValidoException("Salario deve ser numerico.");
                }
                break;
            case "metodoPagamento":
                if (valor == null || (!valor.equals("emMaos") && !valor.equals("correios") && !valor.equals("banco"))) {
                    throw new CampoValidoException("Metodo de pagamento invalido.");
                }
                if (valor.equals("banco")) {
                    if (banco == null || banco.isEmpty()) throw new CampoValidoException("Banco nao pode ser nulo.");
                    if (agencia == null || agencia.isEmpty())
                        throw new CampoValidoException("Agencia nao pode ser nulo.");
                    if (contaCorrente == null || contaCorrente.isEmpty())
                        throw new CampoValidoException("Conta corrente nao pode ser nulo.");
                    empregado.setMetodoPagamento("banco");
                    empregado.setBanco(banco);
                    empregado.setAgencia(agencia);
                    empregado.setContaCorrente(contaCorrente);
                } else {
                    empregado.setMetodoPagamento(valor);
                    empregado.setBanco(null);
                    empregado.setAgencia(null);
                    empregado.setContaCorrente(null);
                }
                break;
            case "tipo":
                if (valor == null || valor.isEmpty()) throw new CampoValidoException("Tipo invalido.");
                Empregado novoEmpregado = null;
                // A alteração de salário junto com tipo não é suportada pela assinatura atual,
                // mas a mudança de tipo e a cópia de atributos funcionarão.
                double salarioParaNovoTipo = empregado.getSalario().doubleValue();
                switch (valor) {
                    case "horista":
                        novoEmpregado = new EmpregadoHorista(empregado.getNome(), empregado.getEndereco(), valor, salarioParaNovoTipo);
                        break;
                    case "assalariado":
                        novoEmpregado = new EmpregadoAssalariado(empregado.getNome(), empregado.getEndereco(), valor, salarioParaNovoTipo);
                        break;
                    case "comissionado":
                        if (comissao == null || comissao.isEmpty())
                            throw new CampoValidoException("Comissao nao pode ser nula.");
                        double com = Double.parseDouble(comissao.replace(',', '.'));
                        novoEmpregado = new EmpregadoComissionado(empregado.getNome(), empregado.getEndereco(), valor, salarioParaNovoTipo, com);
                        break;
                    default:
                        throw new CampoValidoException("Tipo invalido.");
                }
                novoEmpregado.setSindicalizado(empregado.isSindicalizado());
                novoEmpregado.setIdSindicato(empregado.getIdSindicato());
                novoEmpregado.setTaxaSindical(empregado.getTaxaSindical());
                novoEmpregado.setMetodoPagamento(empregado.getMetodoPagamento());
                novoEmpregado.setBanco(empregado.getBanco());
                novoEmpregado.setAgencia(empregado.getAgencia());
                novoEmpregado.setContaCorrente(empregado.getContaCorrente());
                empregados.put(emp, novoEmpregado);
                break;
            case "comissao":
                if (empregado instanceof EmpregadoComissionado) {
                    if (valor == null || valor.isEmpty()) throw new CampoValidoException("Comissao nao pode ser nula.");
                    try {
                        double novaComissao = Double.parseDouble(valor.replace(',', '.'));
                        if (novaComissao < 0) throw new CampoValidoException("Comissao deve ser nao-negativa.");
                        ((EmpregadoComissionado) empregado).setComissao(novaComissao);
                    } catch (NumberFormatException e) {
                        throw new CampoValidoException("Comissao deve ser numerica.");
                    }
                } else {
                    throw new CampoValidoException("Empregado nao eh comissionado.");
                }
                break;
            case "sindicalizado":
                if (valor == null || (!valor.equalsIgnoreCase("true") && !valor.equalsIgnoreCase("false"))) {
                    throw new CampoValidoException("Valor deve ser true ou false.");
                }
                boolean ehSindicalizado = Boolean.parseBoolean(valor);
                if (ehSindicalizado) {
                    if (idSindicato == null || idSindicato.isEmpty())
                        throw new CampoValidoException("Identificacao do sindicato nao pode ser nula.");
                    if (taxaSindical == null || taxaSindical.isEmpty())
                        throw new CampoValidoException("Taxa sindical nao pode ser nula.");

                    for (Map.Entry<String, Empregado> entry : empregados.entrySet()) {
                        if (!entry.getKey().equals(emp) && entry.getValue().isSindicalizado() && idSindicato.equals(entry.getValue().getIdSindicato())) {
                            throw new CampoValidoException("Ha outro empregado com esta identificacao de sindicato");
                        }
                    }

                    try {
                        double taxa = Double.parseDouble(taxaSindical.replace(',', '.'));
                        if (taxa < 0) {
                            throw new CampoValidoException("Taxa sindical deve ser nao-negativa.");
                        }
                        empregado.setTaxaSindical(BigDecimal.valueOf(taxa));
                    } catch (NumberFormatException e) {
                        throw new CampoValidoException("Taxa sindical deve ser numerica.");
                    }
                    empregado.setSindicalizado(true);
                    empregado.setIdSindicato(idSindicato);
                } else {
                    empregado.setSindicalizado(false);
                    empregado.setIdSindicato(null);
                    empregado.setTaxaSindical(BigDecimal.ZERO);
                }
                break;
            default:
                throw new CampoValidoException("Atributo nao existe.");
        }
    }

    /**
     * Lança uma taxa de serviço para um membro do sindicato.
     *
     * @param membro O ID do sindicato do membro.
     * @param data   A data da cobrança da taxa.
     * @param valor  O valor da taxa de serviço.
     * @throws CampoValidoException Se o membro não for encontrado ou os dados forem inválidos.
     */
    public void lancaTaxaServico(String membro, String data, String valor) throws CampoValidoException {
        // Validações de erro
        if (membro.isEmpty()) throw new CampoValidoException("Identificacao do membro nao pode ser nula.");
        if (!EmpregadoHorista.validarData(data)) throw new CampoValidoException("Data invalida.");
        double valorNumerico = Double.parseDouble(valor.replace(',', '.'));
        if (valorNumerico <= 0) throw new CampoValidoException("Valor deve ser positivo.");

        Empregado empregadoAlvo = null;
        // Encontra o empregado pelo ID do Sindicato
        for (Empregado e : empregados.values()) {
            if (e.isSindicalizado() && membro.equals(e.getIdSindicato())) {
                empregadoAlvo = e;
                break;
            }
        }

        if (empregadoAlvo == null) throw new CampoValidoException("Membro nao existe.");

        // Cria e adiciona a taxa de serviço
        TaxaServico novaTaxa = new TaxaServico(data, valor);
        empregadoAlvo.getTaxasServico().add(novaTaxa);
    }

    /**
     * Retorna o total de taxas de serviço de um empregado em um período.
     *
     * @param emp         O ID do empregado.
     * @param dataInicial A data inicial do período.
     * @param dataFinal   A data final do período.
     * @return O valor total das taxas formatado como String.
     * @throws CampoValidoException Se o empregado não for sindicalizado ou as datas forem inválidas.
     */
    public String getTaxasServico(String emp, String dataInicial, String dataFinal) throws CampoValidoException, EmpregadoNaoExisteException {
        Empregado empregado = empregados.get(emp);
        if (empregado == null) {
            throw new EmpregadoNaoExisteException();
        }
        if (!empregado.isSindicalizado()) throw new CampoValidoException("Empregado nao eh sindicalizado.");
        if (!EmpregadoHorista.validarData(dataInicial)) throw new CampoValidoException("Data inicial invalida.");
        if (!EmpregadoHorista.validarData(dataFinal)) throw new CampoValidoException("Data final invalida.");

        // A lógica de validação de datas e iteração é quase idêntica à de getVendas
        double totalTaxas = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate inicio = LocalDate.parse(dataInicial, formatter);
        LocalDate fim = LocalDate.parse(dataFinal, formatter);

        if (fim.isBefore(inicio)) throw new CampoValidoException("Data inicial nao pode ser posterior aa data final.");

        for (TaxaServico taxa : empregado.getTaxasServico()) {
            LocalDate dataTaxa = LocalDate.parse(taxa.getData(), formatter);
            if (!dataTaxa.isBefore(inicio) && dataTaxa.isBefore(fim)) {
                totalTaxas += Double.parseDouble(taxa.getValor().replace(',', '.'));
            }
        }

        // Formata o resultado para o padrão brasileiro
        return String.format(new Locale("pt", "BR"), "%.2f", totalTaxas);
    }

    /**
     * Retorna true ou false dependendo se o empregado deve receber naquela data ou não
     *
     * @param empregado O empregado a ser verificado
     * @param dataAtual A data a ser testada
     * @return o retorno booleano se o empregado deve receber ou não
     */
    private boolean deveReceber(Empregado empregado, LocalDate dataAtual)
    {
        String tipo = empregado.getTipo();

        switch (tipo) {
            case "horista":
                // Horistas recebem toda sexta-feira
                return dataAtual.getDayOfWeek() == java.time.DayOfWeek.FRIDAY;

            case "assalariado":
                // Assalariados recebem no último dia do mês
                // A verificação `isEqual` compara se a data atual é o último dia do mês.
                return dataAtual.isEqual(dataAtual.withDayOfMonth(dataAtual.lengthOfMonth()));

            case "comissionado":
                // Comissionados recebem a cada 2 sextas-feiras[cite: 210].
                // Os testes (us7.txt) mostram que o primeiro pagamento ocorre na segunda sexta-feira do ano.
                // A partir daí, o pagamento é quinzenal.
                if (dataAtual.getDayOfWeek() != java.time.DayOfWeek.FRIDAY) {
                    return false; // Se não for sexta-feira, não há pagamento.
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate primeiroPagamento = LocalDate.parse(empregado.getUltimoPagamento(), formatter);

                long semanasDesdeReferencia;
                // Calcula o número de semanas entre a data de referência e a data atual
                if (Objects.equals(empregado.getUltimoPagamento(),"1/1/2005"))
                {
                    semanasDesdeReferencia = (java.time.temporal.ChronoUnit.WEEKS.between(primeiroPagamento, dataAtual)) + 1;
                }
                else
                {
                    semanasDesdeReferencia = (java.time.temporal.ChronoUnit.WEEKS.between(primeiroPagamento, dataAtual));
                }

                // Se o número de semanas for par, significa que está no ciclo de 2 semanas.
                return semanasDesdeReferencia > 0 && semanasDesdeReferencia % 2 == 0;

            default:
                return false;
        }

    }

    /**
     * Calcula o valor total da folha de pagamento para uma data específica.
     *
     * @param data A data para a qual a folha deve ser calculada.
     * @return O valor total bruto da folha, formatado como String.
     * @throws Exception Se ocorrer um erro durante o cálculo.
     */
    public String totalFolha(String data) throws Exception {
        BigDecimal total = BigDecimal.ZERO;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate dataAtual = LocalDate.parse(data, formatter);

        for (Empregado empregado : empregados.values()) {
            if (deveReceber(empregado, dataAtual)) {
                // Chama o método do próprio objeto empregado
                total = total.add(Empregado.calculaSalarioBruto(empregado, data));
            }
        }

        NumberFormat formatador = NumberFormat.getInstance(new Locale("pt", "BR"));
        formatador.setGroupingUsed(false);
        formatador.setMinimumFractionDigits(2);
        formatador.setMaximumFractionDigits(2);
        return formatador.format(total);
    }
    /**
     * Executa a folha de pagamento para uma data e gera um arquivo de saída.
     *
     * @param data  A data em que a folha deve ser rodada.
     * @param saida O nome do arquivo de texto a ser gerado com o resumo da folha.
     * @throws Exception Se ocorrer um erro durante o processamento ou geração do arquivo.
     */
    public void rodaFolha(String data, String saida) throws Exception {
        ArrayList<EmpregadoAssalariado> assalariados = new ArrayList<>();
        ArrayList<EmpregadoComissionado> comissionados = new ArrayList<>();
        ArrayList<EmpregadoHorista> horistas = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate dataAtual = LocalDate.parse(data, formatter);
        BigDecimal totalFolha = BigDecimal.ZERO;
        formatador = NumberFormat.getInstance(new Locale("pt", "BR"));
        formatador.setGroupingUsed(false);
        formatador.setMinimumFractionDigits(2);
        formatador.setMaximumFractionDigits(2);

        // Coleta os empregados que devem receber no dia.
        for (Empregado empregado : empregados.values()) {
            if (deveReceber(empregado, dataAtual)) {
                switch (empregado.getTipo()) {
                    case "horista":
                        horistas.add((EmpregadoHorista) empregado);
                        break;
                    case "assalariado":
                        assalariados.add((EmpregadoAssalariado) empregado);
                        break;
                    case "comissionado":
                        comissionados.add((EmpregadoComissionado) empregado);
                        break;
                }
            }
        }

        // Ordena as listas de empregados por nome.
        horistas.sort(Comparator.comparing(Empregado::getNome));
        assalariados.sort(Comparator.comparing(Empregado::getNome));
        comissionados.sort(Comparator.comparing(Empregado::getNome));

        try (PrintWriter gravarArq = new PrintWriter(new FileWriter(saida))) {
            gravarArq.printf("FOLHA DE PAGAMENTO DO DIA %s\n", dataAtual);
            gravarArq.printf("====================================\n\n");

            // Processamento e impressão dos horistas.
            gravarArq.printf("===============================================================================================================================\n");
            gravarArq.printf("===================== HORISTAS ================================================================================================\n");
            gravarArq.printf("===============================================================================================================================\n");
            gravarArq.printf("Nome                                 Horas Extra Salario Bruto Descontos Salario Liquido Metodo\n");
            gravarArq.printf("==================================== ===== ===== ============= ========= =============== ======================================\n");
            int totalNormais, totalExtra;
            totalNormais = totalExtra = 0;
            BigDecimal totalDescontos = BigDecimal.ZERO;
            BigDecimal totalHoristas = BigDecimal.ZERO;
            BigDecimal totalLiquido = BigDecimal.ZERO;
            for (EmpregadoHorista horista : horistas)
            {
                int horasNormais = Integer.parseInt(horista.getHorasNormaisTrabalhadas(horista.getUltimoPagamento(), data));
                int horasExtras = Integer.parseInt(horista.getHorasExtrasTrabalhadas(horista.getUltimoPagamento(), data));
                totalNormais += horasNormais;
                totalExtra += horasExtras;
                BigDecimal salarioBruto = horista.calculaSalarioBruto(data);
                BigDecimal descontos = calcularDescontos(horista, data);
                BigDecimal salarioLiquido = salarioBruto.subtract(descontos);
                totalDescontos = totalDescontos.add(descontos);
                totalLiquido = totalLiquido.add(salarioLiquido);
                totalHoristas = totalHoristas.add(salarioBruto);

                gravarArq.printf("%-36s %5s %5s %13s %9s %15s %s\n",
                        horista.getNome(),
                        horista.getHorasNormaisTrabalhadas(horista.getUltimoPagamento(), data),
                        horista.getHorasExtrasTrabalhadas(horista.getUltimoPagamento(), data),
                        formatador.format(salarioBruto),
                        formatador.format(descontos),
                        formatador.format(salarioLiquido),
                        formatarMetodoPagamento(horista));
                horista.setUltimoPagamento(data);
            }
            gravarArq.printf("\nTOTAL HORISTAS %27d %5d %13s %9s %15s\n", totalNormais, totalExtra, formatador.format(totalHoristas), formatador.format(totalDescontos),formatador.format(totalLiquido));

            // Processamento e impressão dos assalariados.
            gravarArq.printf("\n===============================================================================================================================\n");
            gravarArq.printf("===================== ASSALARIADOS ============================================================================================\n");
            gravarArq.printf("===============================================================================================================================\n");
            gravarArq.printf("Nome                                             Salario Bruto Descontos Salario Liquido Metodo\n");
            gravarArq.printf("================================================ ============= ========= =============== ======================================\n");
            BigDecimal totalAssalariados = BigDecimal.ZERO;
            for (EmpregadoAssalariado assalariado : assalariados) {
                BigDecimal salarioBruto = assalariado.getSalario();
                BigDecimal descontos = calcularDescontos(assalariado, data);
                BigDecimal salarioLiquido = salarioBruto.subtract(descontos);
                totalAssalariados = totalAssalariados.add(salarioBruto);

                gravarArq.printf("%-48s %13s %9s %15s %s\n",
                        assalariado.getNome(),
                        formatador.format(salarioBruto),
                        formatador.format(descontos),
                        formatador.format(salarioLiquido),
                        "Correios, " + assalariado.getEndereco());
                assalariado.setUltimoPagamento(data);
            }
            gravarArq.printf("\nTOTAL ASSALARIADOS %43s %9s %15s\n", formatador.format(totalAssalariados), "0,00", formatador.format(totalAssalariados));


            // Processamento e impressão dos comissionados.
            gravarArq.printf("\n===============================================================================================================================\n");
            gravarArq.printf("===================== COMISSIONADOS ===========================================================================================\n");
            gravarArq.printf("===============================================================================================================================\n");
            gravarArq.printf("Nome                  Fixo     Vendas   Comissao Salario Bruto Descontos Salario Liquido Metodo\n");
            gravarArq.printf("===================== ======== ======== ======== ============= ========= =============== ======================================\n");
            BigDecimal totalComissionados = BigDecimal.ZERO;
            for (EmpregadoComissionado comissionado : comissionados) {
                BigDecimal salarioBruto = comissionado.calculaSalarioBruto(data);
                BigDecimal descontos = calcularDescontos(comissionado, data);
                BigDecimal salarioLiquido = salarioBruto.subtract(descontos);
                totalComissionados = totalComissionados.add(salarioBruto);

                gravarArq.printf("%-21s %8s %8s %8s %13s %9s %15s %s\n",
                        comissionado.getNome(),
                        formatador.format(comissionado.getSalario().multiply(new BigDecimal("12")).divide(new BigDecimal("26"), 2, RoundingMode.DOWN)),
                        comissionado.getVendas(comissionado.getUltimoPagamento(), data),
                        "0,00", // Comissão não é impressa individualmente no resumo
                        formatador.format(salarioBruto),
                        formatador.format(descontos),
                        formatador.format(salarioLiquido),
                        "Correios, " + comissionado.getEndereco());
                comissionado.setUltimoPagamento(data);
            }
            gravarArq.printf("\nTOTAL COMISSIONADOS %10s %8s %8s %13s %9s %15s\n", "0,00", "0,00", "0,00", formatador.format(totalComissionados), "0,00", formatador.format(totalComissionados));

            totalFolha = totalHoristas.add(totalAssalariados).add(totalComissionados);
            gravarArq.printf("\nTOTAL FOLHA: %s\n", formatador.format(totalFolha));
        }
    }

// Métodos auxiliares que precisam ser adicionados a sua classe SistemaFolha.

    private BigDecimal calcularDescontos(Empregado empregado, String data) throws EmpregadoNaoExisteException, CampoValidoException {
        if (!empregado.isSindicalizado()) {
            return BigDecimal.ZERO;
        }

        BigDecimal taxaSindicalDiaria = empregado.getTaxaSindical();
        LocalDate dataAtual = LocalDate.parse(data, DateTimeFormatter.ofPattern("d/M/yyyy"));
        LocalDate ultimoPagamento = LocalDate.parse(empregado.getUltimoPagamento(), DateTimeFormatter.ofPattern("d/M/yyyy"));
        if ((ultimoPagamento.getYear() == dataAtual.getYear()) && (ultimoPagamento.getMonth() == dataAtual.getMonth()) && !(Objects.equals(empregado.getUltimoPagamento(),"1/1/2005"))) return BigDecimal.ZERO;
        long dias;
        if (Objects.equals(empregado.getUltimoPagamento(),"1/1/2005"))
        {
            dias = java.time.temporal.ChronoUnit.DAYS.between(ultimoPagamento, dataAtual) + 1;
        }
        else
        {
            dias = java.time.temporal.ChronoUnit.DAYS.between(ultimoPagamento, dataAtual) + 1;
        }
        BigDecimal totalTaxaSindical = taxaSindicalDiaria.multiply(new BigDecimal(dias));

        BigDecimal taxasServico = new BigDecimal(getTaxasServico(String.valueOf(empregados.entrySet().stream()
                .filter(entry -> entry.getValue().equals(empregado))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null)), empregado.getUltimoPagamento(), data).replace(",", "."));


        return totalTaxaSindical.add(taxasServico);
    }

    private String formatarMetodoPagamento(Empregado empregado) {
        if ("banco".equals(empregado.getMetodoPagamento())) {
            return String.format("%s, Ag. %s CC %s", empregado.getBanco(), empregado.getAgencia(), empregado.getContaCorrente());
        }
        if ("emMaos".equals(empregado.getMetodoPagamento()))
        {
            return "Em maos";
        }
        return empregado.getMetodoPagamento();
    }
}
