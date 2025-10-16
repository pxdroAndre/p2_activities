package br.ufal.ic.p2.wepayu.commands;

/**
 * Interface que representa o padrão de projeto Command.
 * <p>
 * Cada comando encapsula uma ação que pode ser executada e desfeita.
 * </p>
 */
public interface Command {
    void execute() throws Exception;

    void undo() throws Exception;
}