package org.example;

import org.example.controller.ChartController;
import org.example.ui.ConsoleUI;
import java.io.IOException;
import java.util.Scanner;

/**
 * Clase principal que inicia la aplicación de tablas de multiplicar.
 * Implementa el patrón MVC separando la lógica de interfaz de usuario del controlador.
 */
public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Inicializar el controlador y la interfaz de usuario
            ChartController controller = new ChartController();
            ConsoleUI ui = new ConsoleUI(scanner, controller);

            // Iniciar la aplicación
            System.out.println("Welcome to chart generators");
            ui.start();

            System.out.println("Bye, see you soon.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}