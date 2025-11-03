import java.io.IOException;
public class EmptyFileException extends IOException {
    public EmptyFileException(String e) {
        super(e);
    }
}