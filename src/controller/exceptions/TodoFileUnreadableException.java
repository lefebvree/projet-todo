package controller.exceptions;

// The file is corrupted or user does not have sufficient rights
public class TodoFileUnreadableException extends Exception {

    private final String filename;

    public TodoFileUnreadableException(String fn) {
        this.filename = fn;
    }

    public String getFilename() {
        return filename;
    }
}
