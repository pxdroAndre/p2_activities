package br.ufal.ic.p2.wepayu.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmpregadoAssalariado extends Empregado {
    public EmpregadoAssalariado(String nome, String endereco, String tipo, double salario) {
        super(nome, endereco, tipo, SistemaFolha.doubleParaString(salario));
    }

    public EmpregadoAssalariado() {}

    public BigDecimal calculaSalarioBruto(LocalDate dataInicial, LocalDate dataFinal) {
        return BigDecimal.valueOf(Double.parseDouble(this.getSalario().replace(",", ".")));
    }
}