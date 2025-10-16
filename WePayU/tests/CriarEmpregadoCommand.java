package br.ufal.ic.p2.wepayu.models;

/**
 * Comando para criar um novo empregado.
 * Encapsula a lógica de criação e a lógica inversa (remoção) para o undo.
 */
public class CriarEmpregadoCommand implements Command {

    private SistemaFolha sistema;
    private String nome;
    private String endereco;
    private String tipo;
    private String salario;
    private String comissao;
    private String idCriado; // Guarda o estado para o undo

    // Construtor para empregados não-comissionados
    public CriarEmpregadoCommand(SistemaFolha sistema, String nome, String endereco, String tipo, String salario) {
        this(sistema, nome, endereco, tipo, salario, null);
    }

    // Construtor para empregados comissionados
    public CriarEmpregadoCommand(SistemaFolha sistema, String nome, String endereco, String tipo, String salario, String comissao) {
        this.sistema = sistema;
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.comissao = comissao;
    }

    @Override
    public void execute() throws Exception {
        if (comissao == null) {
            this.idCriado = sistema.criarEmpregado(nome, endereco, tipo, salario);
        } else {
            this.idCriado = sistema.criarEmpregado(nome, endereco, tipo, salario, comissao);
        }
    }

    @Override
    public void undo() throws Exception {
        if (idCriado != null) {
            sistema.removerEmpregado(idCriado);
        }
    }

    public String getIdCriado() {
        return idCriado;
    }
}