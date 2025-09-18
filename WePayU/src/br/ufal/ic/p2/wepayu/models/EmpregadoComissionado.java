package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.CampoValidoException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class EmpregadoComissionado extends Empregado
{
    private double comissao;
    private ArrayList<ResultadoDeVenda> vendas = new ArrayList<>();
    public EmpregadoComissionado(){}

    public EmpregadoComissionado(String nome, String endereco, String tipo, double salario, double comissao) {
        super(nome, endereco, tipo, salario);
        this.comissao = comissao;
    }
    public double getComissao(){return comissao;};

    public void setComissao(double comissao) {
        this.comissao = comissao;
    }

    public void lancaVenda (String valor, String data)
    {
        ResultadoDeVenda venda = new ResultadoDeVenda(data, valor); // criando um objeto de venda
        vendas.add(venda); // adicionando no array
    }

    public String getVendas (String inicio, String fim) throws CampoValidoException
    {
        double totalVendas = 0;
        // fazendo parsing das datas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        // formata as datas
        if (!EmpregadoHorista.validarData(inicio)) throw new CampoValidoException("Data inicial invalida.");
        if (!EmpregadoHorista.validarData(fim)) throw new CampoValidoException("Data final invalida.");
        LocalDate in, fi;
        try
        {
            in = LocalDate.parse(inicio, formatter);
        } catch (DateTimeParseException e) {
            throw new CampoValidoException("Data inicial invalida."); // Mensagem com ponto
        }

        try
        {
            fi = LocalDate.parse(fim, formatter);
        } catch (DateTimeParseException e) {
            throw new CampoValidoException("Data final invalida."); // Mensagem com ponto
        }

        if (fi.isBefore(in)) throw new CampoValidoException("Data inicial nao pode ser posterior aa data final.");

        // loop sobre a lista de vendas do empregado
        for (ResultadoDeVenda venda : vendas) {
            String dataDaVenda = venda.getData();
            LocalDate dataVenda = LocalDate.parse(dataDaVenda, formatter);

            // checa se a data da venda está dentro do intervalo
            if (!dataVenda.isBefore(in) && dataVenda.isBefore(fi)) {
                // Se estiver no intervalo, some as vendas
                String valorDaVenda = venda.getValor();
                valorDaVenda = valorDaVenda.replace('.', ',');
                double v = Double.parseDouble(valorDaVenda);
                totalVendas += v;
            }
        }
        // formatando para o retorno
        return String.format("%.2f", totalVendas);

    }
}
