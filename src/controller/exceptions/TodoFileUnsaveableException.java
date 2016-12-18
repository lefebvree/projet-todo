package controller.exceptions;

// Cannot save todo to a file with this name
public class TodoFileUnsaveableException extends Exception {

    private final String filename;

    public TodoFileUnsaveableException(String f) {
        this.filename = f;
    }

    public String getFilename() {
        return filename;
    }
}
