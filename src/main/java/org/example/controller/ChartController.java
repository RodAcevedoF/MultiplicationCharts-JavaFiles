package org.example.controller;

import org.example.exception.FileAlreadyExistsException;
import org.example.model.MultiplicationChart;
import org.example.service.FileService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Controlador que maneja las operaciones relacionadas con las tablas de multiplicar.
 * Actúa como intermediario entre la interfaz de usuario y los servicios de negocio.
 */
public class ChartController {
    private static final Logger LOGGER = Logger.getLogger(ChartController.class.getName());

    private final FileService fileService;
    private final MultiplicationChart model;

    /**
     * Constructor que inicializa los servicios necesarios.
     */
    public ChartController() {
        this.fileService = new FileService();
        this.model = new MultiplicationChart();
    }

    /**
     * Genera una tabla de multiplicar para el número base especificado.
     *
     * @param baseNum Número base para la tabla de multiplicar (1-10)
     * @return true si la generación fue exitosa, false en caso contrario
     * @throws IOException si ocurre un error de E/S
     */
    public boolean generateChart(int baseNum) throws IOException {
        if (baseNum < 1 || baseNum > 10) {
            return false;
        }

        model.setBaseNum(baseNum);
        List<String> lines = model.generateLines();

        try {
            return fileService.saveChart(baseNum, lines);
        } catch (FileAlreadyExistsException e) {
            LOGGER.log(Level.INFO, "Chart already exists: {0}", e.getMessage());
            throw e; // Re-lanzamos la excepción para que la UI pueda manejarla
        }
    }
    /**
     * Lee y devuelve una tabla de multiplicar completa.
     *
     * @param baseNum Número base de la tabla a leer
     * @return Lista con las líneas de la tabla, o una lista vacía si no se encuentra
     * @throws IOException si ocurre un error de E/S
     */
    public List<String> readChart(int baseNum) throws IOException {
        if (baseNum < 1 || baseNum > 10) {
            return List.of();
        }

        return fileService.readChart(baseNum);
    }

    /**
     * Lee una línea específica de una tabla de multiplicar.
     *
     * @param baseNum Número base de la tabla
     * @param lineNum Número de línea a leer (1-10)
     * @return La línea solicitada, o vacío si no se encuentra
     * @throws IOException si ocurre un error de E/S
     */
    public Optional<String> readChartLine(int baseNum, int lineNum) throws IOException {
        if (baseNum < 1 || baseNum > 10 || lineNum < 1 || lineNum > 10) {
            return Optional.empty();
        }

        List<String> chart = fileService.readChart(baseNum);
        if (chart.size() >= lineNum) {
            return Optional.of(chart.get(lineNum - 1));
        }

        return Optional.empty();
    }
}