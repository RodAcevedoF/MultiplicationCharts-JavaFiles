package org.example.ui;

import org.example.controller.ChartController;
import org.example.service.FileService;
import org.example.exception.FileAlreadyExistsException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Clase que maneja la interfaz de consola para la aplicación de tablas de multiplicar.
 * Se encarga de mostrar opciones, recibir entradas del usuario y mostrar resultados.
 */
public class ConsoleUI {
    private final Scanner scanner;
    private final ChartController controller;
    private boolean active;

    /**
     * Constructor que inicializa la interfaz con un scanner y un controlador.
     *
     * @param scanner El scanner para leer la entrada del usuario
     * @param controller El controlador que maneja las operaciones de negocio
     */
    public ConsoleUI(Scanner scanner, ChartController controller) {
        this.scanner = scanner;
        this.controller = controller;
        this.active = true;
    }

    /**
     * Inicia el ciclo principal de la interfaz de usuario.
     *
     * @throws IOException si ocurre un error de E/S
     */
    public void start() throws IOException {
        while (active) {
            showOptions();
            int option = validateOption("Enter an option: ", 1, 4);
            processOption(option);
            System.out.println();
        }
    }

    /**
     * Muestra el menú de opciones disponibles para el usuario.
     */
    private void showOptions() {
        System.out.println("""
                Select an option:
                1. Generate a multiplication chart
                2. Read a multiplication chart
                3. Read a line of a multiplication chart
                4. Exit
                """);
    }

    /**
     * Procesa la opción seleccionada por el usuario.
     *
     * @param option La opción seleccionada
     * @throws IOException si ocurre un error de E/S
     */
    private void processOption(int option) throws IOException {
        switch (option) {
            case 1 -> generateChart();
            case 2 -> readChart();
            case 3 -> readChartLine();
            case 4 -> exit();
            default -> System.out.println("Invalid option");
        }
    }

    /**
     * Maneja la opción de generar una tabla de multiplicar.
     *
     * @throws IOException si ocurre un error de E/S
     */
    private void generateChart() throws IOException {
        int base = validateOption("Enter base number: ", 1, 10);

        try {
            boolean success = controller.generateChart(base);
            if (success) {
                System.out.println("Chart for base " + base + " generated successfully.");
            } else {
                System.out.println("Failed to generate chart for base " + base);
            }
        } catch (FileAlreadyExistsException e) {
            System.out.println("Error: A chart for base " + base + " already exists.");

            // Opcional: preguntar si desea sobrescribir
            System.out.print("Do you want to delete the existing file and create a new one? (y/n): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("y") || response.equals("yes")) {
                FileService fileService = null;
                fileService.deleteChart(base);
                boolean success = controller.generateChart(base);
                if (success) {
                    System.out.println("Chart for base " + base + " regenerated successfully.");
                }
            }
        }
    }
    /**
     * Maneja la opción de leer una tabla de multiplicar completa.
     *
     * @throws IOException si ocurre un error de E/S
     */
    private void readChart() throws IOException {
        int base = validateOption("Enter base number: ", 1, 10);
        List<String> lines = controller.readChart(base);

        if (lines.isEmpty()) {
            System.out.println("Chart for base " + base + " not found or empty.");
            return;
        }

        System.out.println("Multiplication chart for base " + base + ":");
        for (String line : lines) {
            System.out.println(line);
        }
    }

    /**
     * Maneja la opción de leer una línea específica de una tabla de multiplicar.
     *
     * @throws IOException si ocurre un error de E/S
     */
    private void readChartLine() throws IOException {
        int base = validateOption("Enter base number: ", 1, 10);
        int lineNum = validateOption("Enter line number: ", 1, 10);

        Optional<String> line = controller.readChartLine(base, lineNum);

        if (line.isPresent()) {
            System.out.println("Line found:");
            System.out.println(line.get());
        } else {
            System.out.println("Line " + lineNum + " for base " + base + " not found.");
        }
    }

    /**
     * Maneja la opción de salir de la aplicación.
     */
    private void exit() {
        active = false;
    }

    /**
     * Valida y obtiene una entrada numérica del usuario dentro de un rango específico.
     *
     * @param message El mensaje a mostrar al usuario
     * @param lower El límite inferior del rango (inclusivo)
     * @param upper El límite superior del rango (inclusivo)
     * @return El número validado
     */
    private int validateOption(String message, int lower, int upper) {
        int option;
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                option = Integer.parseInt(input);
                if (option >= lower && option <= upper) {
                    return option;
                } else {
                    System.out.println("Enter a number within the range (" + lower + "-" + upper + ")");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Please enter a valid number.");
                System.out.println();
            }
        }
    }
}