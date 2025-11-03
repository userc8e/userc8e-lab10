import java.io.IOException;
public class TooSmallText extends IOException {
    public TooSmallText(String e) {
        super(e);
    }

    @Override
    public String toString() {
        return "TooSmallText: " + getMessage();
    }
}
