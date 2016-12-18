package controller.exceptions;

// The file associated with this name cannot be found
public class TodoFileDoesNotExistException extends Exception {

    private final String filename;

    public TodoFileDoesNotExistException(String fn) {
        this.filename = fn;
    }

    public String getFilename() {
        return filename;
    }
}
