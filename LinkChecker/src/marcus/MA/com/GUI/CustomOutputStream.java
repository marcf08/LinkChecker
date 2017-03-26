package marcus.MA.com.GUI;
import java.io.IOException;
import java.io.OutputStream;
 
import javax.swing.JTextArea;
 
/**
 * This class extends from OutputStream to redirect output to a JTextArrea.
 * This is that the Links class can output directly to the text area without changing
 * any code. All credit goes to www.codejava.net for this class/example.
 * @author www.codejava.net
 */
public class CustomOutputStream extends OutputStream {
    private JTextArea textArea;
     
    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }
     
    @Override
    public void write(int b) throws IOException {
        // Redirects data to the text area
        textArea.append(String.valueOf((char)b));
        // Scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }


}
