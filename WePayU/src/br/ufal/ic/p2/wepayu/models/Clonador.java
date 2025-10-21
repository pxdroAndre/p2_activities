package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.models.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Classe utilitária para clonar objetos usando serialização XML.
 * Isso garante um "deep copy" (cópia profunda) para qualquer JavaBean.
 */
public class Clonador {

    /**
     * Clona um objeto (Empregado, etc.)
     *
     * @param original O objeto a ser clonado.
     * @return Um clone profundo (deep copy) do objeto.
     */
    public static Object clonar(Object original) {
        try {
            // 1. Prepara um "arquivo" em memória para escrever
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // 2. Serializa o objeto para XML na memória
            try (XMLEncoder encoder = new XMLEncoder(baos)) {
                encoder.writeObject(original);
            }

            // 3. Prepara para ler o XML que acabamos de criar
            byte[] bytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

            // 4. Desserializa o XML de volta para um NOVO objeto
            try (XMLDecoder decoder = new XMLDecoder(bais)) {
                Object clone = decoder.readObject();
                return clone;
            }

        } catch (Exception e) {
            // Em um caso real, tratar o erro (ex: lançar uma RuntimeException)
            e.printStackTrace();
            return null;
        }
    }
}