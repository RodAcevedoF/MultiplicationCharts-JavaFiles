package org.example.exception;

public class FileAlreadyExistsException extends RuntimeException {
    public FileAlreadyExistsException(String filename) {
        super("File already exists " + filename);
    }
}
