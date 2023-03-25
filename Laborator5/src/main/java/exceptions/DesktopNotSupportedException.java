package exceptions;

public class DesktopNotSupportedException extends Exception {
    public DesktopNotSupportedException() {
        super("Desktop not supported");
    }
}
