package com.example;

public class Main {
    
    public static void main(String[] args) {
        DictionaryManager manager = new DictionaryManager();

        System.out.println("=== DICCIONARIO INGLÉS-ESPAÑOL ===\n");
        
        manager.loadDictionary("diccionario.txt");
        System.out.println("✓ Diccionario cargado exitosamente.\n");

        System.out.println("Diccionario ordenado (in-order):");
        System.out.println(manager.getDictionaryInOrder());
        System.out.println();
        
        System.out.println("Traduciendo archivo...");
        manager.translateFile("texto.txt", "texto_traducido.txt");
        System.out.println("✓ Archivo traducido y guardado en: texto_traducido.txt");
        
        System.out.println("\n✓ Proceso completado.");
    }
}