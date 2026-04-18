package com.example;

import java.text.Normalizer;

/**
 * Clase utilitaria que proporciona funciones para normalizar y procesar cadenas de texto.
 * 
 * Ofrece métodos para:
 * - Normalizar cadenas (remover acentos y convertir a minúsculas)
 * - Comparar cadenas sin importar mayúsculas/minúsculas ni acentos
 * 
 * @author Alejandro Sagastume
 * @version 1.0
 */
public class StringUtils {

    /**
     * Normaliza una cadena de texto removiendo acentos y convirtiendo a minúsculas.
     * 
     * Este método:
     * 1. Convierte a minúsculas
     * 2. Descompone caracteres acentuados en sus componentes base
     * 3. Remueve los diacríticos (acentos, tildes, diéresis, etc.)
     * 
     * Ejemplos:
     * - "HÁBÍA" → "habia"
     * - "Español" → "espanol"
     * - "PAÍS" → "pais"
     * - "Tú" → "tu"
     * - "haBía UnA VEZ Un caRRO" → "habia una vez un carro"
     * 
     * @param text la cadena de texto a normalizar
     * @return la cadena normalizada (sin acentos, minúsculas)
     * 
     * @example
     * String normalizado = StringUtils.normalize("ESPAÑA");
     * System.out.println(normalizado); // Output: espana
     */
    public static String normalize(String text) {
        if (text == null) {
            return "";
        }

        // Convertir a minúsculas
        text = text.toLowerCase();

        // Descomponer caracteres acentuados (NFD = Decomposed Form)
        // Esto separa los acentos de las letras base
        text = Normalizer.normalize(text, Normalizer.Form.NFD);

        // Remover todos los diacríticos (acentos, tildes, etc.)
        // Usa expresión regular para remover caracteres de control diacrítico
        text = text.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        return text;
    }

    /**
     * Compara dos cadenas ignorando mayúsculas/minúsculas y acentos.
     * 
     * @param str1 la primera cadena a comparar
     * @param str2 la segunda cadena a comparar
     * @return true si las cadenas son iguales (sin importar mayúsculas ni acentos),
     *         false en caso contrario
     * 
     * @example
     * boolean iguales = StringUtils.equalsIgnoreCaseAndAccents("ESPAÑA", "españa");
     * System.out.println(iguales); // Output: true
     * 
     * boolean iguales2 = StringUtils.equalsIgnoreCaseAndAccents("Hábía", "habia");
     * System.out.println(iguales2); // Output: true
     */
    public static boolean equalsIgnoreCaseAndAccents(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 == null || str2 == null) {
            return false;
        }

        return normalize(str1).equals(normalize(str2));
    }

    /**
     * Verifica si una cadena contiene otra, ignorando mayúsculas/minúsculas y acentos.
     * 
     * @param text la cadena de texto donde buscar
     * @param search la subcadena a buscar
     * @return true si se encuentra la subcadena, false en caso contrario
     * 
     * @example
     * boolean encontrado = StringUtils.containsIgnoreCaseAndAccents("Hábía una vez", "habia");
     * System.out.println(encontrado); // Output: true
     */
    public static boolean containsIgnoreCaseAndAccents(String text, String search) {
        if (text == null || search == null) {
            return false;
        }

        return normalize(text).contains(normalize(search));
    }
}