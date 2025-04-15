package org.example.service;

import org.example.exception.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio que maneja todas las operaciones de archivos
 * relacionadas con las tablas de multiplicar.
 */
public class FileService {
    private static final Logger LOGGER = Logger.getLogger(FileService.class.getName());
    private static final String BASE_PATH = "./";
    private static final String FILE_PREFIX = "chart-";
    private static final String FILE_EXTENSION = ".txt";

    /**
     * Guarda una tabla de multiplicar en un archivo.
     *
     * @param baseNum El número base de la tabla
     * @param lines Las líneas a guardar
     * @return true si la operación fue exitosa, false en caso contrario
     * @throws IOException si ocurre un error de E/S
     */
    public boolean saveChart(int baseNum, List<String> lines) throws IOException, FileAlreadyExistsException {
        String fileName = getFileName(baseNum);
        Path filePath = Paths.get(fileName);

        // Verificar si el archivo ya existe
        if (Files.exists(filePath)) {
            LOGGER.log(Level.WARNING, "File already exists: {0}", fileName);
            throw new FileAlreadyExistsException(fileName);
        }

        // Crear directorios si no existen
        Files.createDirectories(filePath.getParent() != null ? filePath.getParent() : Paths.get("./"));

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            for (String line : lines) {
                writer.println(line);
            }
            LOGGER.log(Level.INFO, "Generated file: {0}", fileName);
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating the file: {0}", e.getMessage());
            throw e;
        }
    }
    /**
     * Lee un archivo de tabla de multiplicar completo.
     *
     * @param baseNum El número base de la tabla a leer
     * @return Lista con las líneas del archivo
     * @throws IOException si ocurre un error de E/S
     */
    public List<String> readChart(int baseNum) throws IOException {
        String fileName = getFileName(baseNum);
        File file = new File(fileName);
        List<String> lines = new ArrayList<>();

        if (!file.exists()) {
            LOGGER.log(Level.WARNING, "File not found: {0}", fileName);
            return lines;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            LOGGER.log(Level.INFO, "Successfully read file: {0}", fileName);
            return lines;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading file: {0}", e.getMessage());
            throw e;
        }
    }

    /**
     * Verifica si existe un archivo de tabla para el número base especificado.
     *
     * @param baseNum El número base a verificar
     * @return true si el archivo existe, false en caso contrario
     */
    public boolean chartExists(int baseNum) {
        String fileName = getFileName(baseNum);
        File file = new File(fileName);
        return file.exists() && file.isFile();
    }

    /**
     * Genera el nombre de archivo para una tabla de multiplicar.
     *
     * @param baseNum El número base de la tabla
     * @return El nombre del archivo completo
     */
    private String getFileName(int baseNum) {
        return BASE_PATH + FILE_PREFIX + baseNum + FILE_EXTENSION;
    }

    /**
     * Elimina un archivo de tabla si existe.
     *
     * @param baseNum El número base de la tabla a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean deleteChart(int baseNum) {
        String fileName = getFileName(baseNum);
        File file = new File(fileName);

        if (file.exists() && file.isFile()) {
            boolean deleted = file.delete();
            if (deleted) {
                LOGGER.log(Level.INFO, "File deleted: {0}", fileName);
            } else {
                LOGGER.log(Level.WARNING, "Could not delete file: {0}", fileName);
            }
            return deleted;
        }

        return false;
    }
}