package org.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo que representa una tabla de multiplicar.
 * Se encarga de la lógica de generación de las filas de la tabla.
 */
public class MultiplicationChart {
    private int baseNum;
    private int lineNum;

    /**
     * Constructor por defecto.
     */
    public MultiplicationChart() {
        this.baseNum = 1;
        this.lineNum = 1;
    }

    /**
     * Constructor con parámetros.
     *
     * @param baseNum Número base para la tabla
     * @param lineNum Número de línea específica
     */
    public MultiplicationChart(int baseNum, int lineNum) {
        this.baseNum = baseNum;
        this.lineNum = lineNum;
    }

    /**
     * Retorna el número base.
     *
     * @return El número base
     */
    public int getBaseNum() {
        return baseNum;
    }

    /**
     * Establece el número base.
     *
     * @param baseNum El número base a establecer
     */
    public void setBaseNum(int baseNum) {
        this.baseNum = baseNum;
    }

    /**
     * Retorna el número de línea actual.
     *
     * @return El número de línea
     */
    public int getLineNum() {
        return lineNum;
    }

    /**
     * Establece el número de línea.
     *
     * @param lineNum El número de línea a establecer
     */
    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    /**
     * Genera las líneas para la tabla de multiplicar del número base actual.
     *
     * @return Lista de cadenas con todas las líneas de la tabla
     */
    public List<String> generateLines() {
        List<String> lines = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            lines.add(formatLine(i));
        }
        return lines;
    }

    /**
     * Genera una única línea de la tabla de multiplicar.
     *
     * @param multiplier El multiplicador para esta línea
     * @return Cadena formateada con la operación y su resultado
     */
    public String formatLine(int multiplier) {
        return String.format("%d * %d = %d", baseNum, multiplier, baseNum * multiplier);
    }

    /**
     * Genera una línea específica de la tabla actual.
     *
     * @param lineNumber El número de línea a generar
     * @return La línea formateada o null si el número está fuera de rango
     */
    public String generateSpecificLine(int lineNumber) {
        if (lineNumber < 1 || lineNumber > 10) {
            return null;
        }
        return formatLine(lineNumber);
    }
}