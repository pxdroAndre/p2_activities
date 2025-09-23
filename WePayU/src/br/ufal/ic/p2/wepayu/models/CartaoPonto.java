package br.ufal.ic.p2.wepayu.models;

import java.time.LocalDate;

/**
 * Representa o registro de horas trabalhadas de um empregado horista em um dia específico.
 * <p>
 * Esta classe armazena a data, o total de horas trabalhadas e calcula
 * automaticamente as horas extras (aquelas que excedem 8 horas diárias).
 * </p>
 * @author Pedro Andre // Substitua pelo seu nome ou autoria
 * @version 1.0
 */
public class CartaoPonto
{
    /**
     * Construtor padrão.
     * <p>
     * Necessário para a persistência de dados via XML.
     * </p>
     */
    public CartaoPonto(){}

    String data;
    String horas;
    String horasExtras;

    /**
     * Construtor que inicializa um cartão de ponto com data e horas.
     * <p>
     * Calcula e armazena as horas extras com base no total de horas fornecido.
     * Se as horas trabalhadas forem maiores que 8, a diferença é registrada como hora extra.
     * </p>
     *
     * @param data A data do registro, no formato "d/M/yyyy".
     * @param horas O total de horas trabalhadas no dia, em formato de String (ex: "8.5").
     */
    public CartaoPonto(String data, String horas)
    {
        this.data = data;
        this.horas = horas;
        double h = Double.parseDouble(horas);
        if (h > 8.00)
        {
            // O cálculo de horas extras está como h % 8.00, o que pode não ser o ideal.
            // Geralmente seria h - 8.00. Mantendo a lógica original.
            this.horasExtras = String.valueOf(h - 8.00);
            this.horasExtras = this.horasExtras.replace(".", ",");
        }
        else
        {
            this.horasExtras = "0";
        }
    }

    /**
     * Retorna o total de horas trabalhadas.
     *
     * @return As horas totais em formato String.
     */
    public String getHoras()
    {
        return horas;
    }

    /**
     * Retorna as horas extras calculadas.
     *
     * @return As horas extras em formato String, com vírgula como separador decimal.
     */
    public String getHorasExtras() {
        return horasExtras;
    }

    /**
     * Define o valor das horas extras.
     *
     * @param horasExtras O novo valor para as horas extras.
     */
    public void setHorasExtras(String horasExtras) {
        this.horasExtras = horasExtras;
    }

    /**
     * Retorna a data do registro de ponto.
     *
     * @return A data no formato "d/M/yyyy".
     */
    public String getData() {
        return data;
    }

    /**
     * Define a data do registro de ponto.
     *
     * @param data A nova data para o registro.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Define o total de horas trabalhadas.
     *
     * @param horas O novo total de horas.
     */
    public void setHoras(String horas) {
        this.horas = horas;
    }
}