package br.ufal.ic.p2.wepayu.utilities;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Classe utilitária para clonar objetos que seguem o padrão JavaBeans
 * (construtor vazio, getters/setters) usando serialização XML.
 * * Isso garante um "deep copy" (cópia profunda) de objetos complexos,
 * como Empregados (incluindo suas listas internas) ou coleções inteiras (como Mapas).
 */
public class Clonador
{

    /**
     * Clona um objeto serializável (JavaBean).
     *
     * @param original O objeto a ser clonado (pode ser um Empregado, um Map, etc.).
     * @param <T>      O tipo genérico do objeto.
     * @return Um clone profundo (deep copy) do objeto.
     * @throws RuntimeException Se a clonagem falhar (ex: objeto não é um JavaBean).
     */
    @SuppressWarnings("unchecked")
    public static <T> T clonar(T original) {
        try {
            // 1. Prepara um "arquivo" em memória para onde escrever o XML.
            // ByteArrayOutputStream é um stream que escreve em um array de bytes na RAM.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // 2. Serializa o objeto para XML e o escreve no stream da memória.
            // O try-with-resources garante que o encoder será fechado.
            try (XMLEncoder encoder = new XMLEncoder(baos)) {
                encoder.writeObject(original);
            }

            // 3. Prepara para ler o XML que acabamos de criar na memória.
            // Pegamos os bytes do stream de saída e os usamos como entrada para um novo stream.
            InputStream bais = new ByteArrayInputStream(baos.toByteArray());

            // 4. Desserializa (lê) o XML de volta, criando um NOVO objeto.
            // O try-with-resources garante que o decoder será fechado.
            try (XMLDecoder decoder = new XMLDecoder(bais)) {
                // Lê o objeto do stream e faz o cast para o tipo original.
                return (T) decoder.readObject();
            }

        } catch (Exception e) {
            // Se algo der errado (ex: classe não tem construtor vazio),
            // a clonagem falha. Lançar uma RuntimeException ajuda a
            // identificar o problema rapidamente durante a depuração.
            throw new RuntimeException("Falha ao clonar objeto: " + e.getMessage(), e);
        }
    }
}