package com.example;

/**
 * Clase genérica que representa una asociación clave-valor.
 * Se utiliza para almacenar pares de palabras (inglés-español) en el diccionario.
 * 
 * La comparación es insensible a mayúsculas/minúsculas y acentos.
 * 
 * @param <M> el tipo de dato de la clave (key)
 * @param <J> el tipo de dato del valor (value)
 * 
 * @author Alejandro Sagastume
 * @version 3.0
 */
public class Association<M extends Comparable<M>, J> implements Comparable<Association<M, J>> {
    
    /** La clave de la asociación (palabra en inglés) */
    private M key;
    
    /** El valor de la asociación (palabra en español) */
    private J value;

    /**
     * Constructor que crea una nueva asociación con la clave y valor especificados.
     * 
     * @param key la clave de la asociación
     * @param value el valor de la asociación
     */
    public Association(M key, J value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Obtiene la clave de la asociación.
     * 
     * @return la clave almacenada en esta asociación
     */
    public M getKey() {
        return key;
    }

    /**
     * Obtiene el valor de la asociación.
     * 
     * @return el valor almacenado en esta asociación
     */
    public J getValue() {
        return value;
    }

    /**
     * Compara esta asociación con otra basándose en la clave.
     * La comparación es insensible a mayúsculas/minúsculas y acentos.
     * 
     * Utiliza la clase StringUtils para normalizar las claves antes de comparar.
     * 
     * @param other la otra asociación a comparar
     * @return un valor negativo si esta clave es menor que la otra,
     *         cero si son iguales, o un valor positivo si es mayor
     */
    @Override
    public int compareTo(Association<M, J> other) {
        // Convertir las claves a String y normalizarlas
        String thisKey = StringUtils.normalize(this.key.toString());
        String otherKey = StringUtils.normalize(other.key.toString());
        return thisKey.compareTo(otherKey);
    }

    /**
     * Compara dos asociaciones por igualdad.
     * Dos asociaciones son iguales si sus claves normalizadas son iguales.
     * 
     * @param obj el objeto a comparar
     * @return true si las asociaciones son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        Association<?, ?> other = (Association<?, ?>) obj;
        
        // Comparar las claves normalizadas
        String thisKey = StringUtils.normalize(this.key.toString());
        String otherKey = StringUtils.normalize(other.key.toString());
        
        return thisKey.equals(otherKey);
    }

    /**
     * Genera un código hash para la asociación basado en la clave normalizada.
     * 
     * @return el código hash de la asociación
     */
    @Override
    public int hashCode() {
        String normalizedKey = StringUtils.normalize(this.key.toString());
        return normalizedKey.hashCode();
    }
    
    /**
     * Devuelve una representación en string de la asociación.
     * Formato: (clave: valor)
     * 
     * @return una cadena que representa esta asociación
     */
    @Override
    public String toString() {
        return "(" + key + ": " + value + ")";
    }
}