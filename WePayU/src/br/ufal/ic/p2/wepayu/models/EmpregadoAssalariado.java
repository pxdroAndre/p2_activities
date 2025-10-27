package br.ufal.ic.p2.wepayu.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class EmpregadoAssalariado extends Empregado {
    public EmpregadoAssalariado(String nome, String endereco, String tipo, BigDecimal salario) {
        super(nome, endereco, tipo, salario.setScale(2, RoundingMode.DOWN).toPlainString().replace('.', ','));
    }

    public EmpregadoAssalariado(EmpregadoAssalariado empregado)
    {
        super(empregado);
    }
    public EmpregadoAssalariado() {}

    @Override
    public BigDecimal calculaSalarioBruto(String dataFinal) {
        BigDecimal salarioBase = new BigDecimal(this.getSalario().replace(",", "."));
        String agenda = this.getAgendaPagamento();

        if (agenda.startsWith("semanal")) {
            String[] partes = agenda.split(" ");
            int frequencia = partes.length == 2 ? 1 : Integer.parseInt(partes[1]);

            BigDecimal salarioAnual = salarioBase.multiply(new BigDecimal("12"));
            BigDecimal salarioSemanal = salarioAnual.divide(new BigDecimal("52"), 2, RoundingMode.DOWN);

            return salarioSemanal.multiply(new BigDecimal(frequencia)).setScale(2, RoundingMode.DOWN);
        }

        // Se for mensal, retorna o salário cheio
        return salarioBase.setScale(2, RoundingMode.DOWN);
    }
}