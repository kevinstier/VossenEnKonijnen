import javax.swing.JComponent;
import javax.swing.JFrame;  
import javax.swing.JTextArea;  
  
import java.awt.Font;
import java.awt.GridLayout;  
  
public class TextView
{

JTextArea textArea1;

public TextView()  
{  
//Create JTextArea without initial text  
JTextArea textArea1=new JTextArea();  
  
//Create a JFrame with title ( Add JTextArea into JFrame )  
JFrame frame=new JFrame("TextView");  
  
//Set JFrame layout  
frame.setLayout(new GridLayout());  
  
//Add first JTextArea into JFrame  
frame.add(textArea1);   
  
//Set default close operation for JFrame  
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
  
//Set JFrame size  
frame.setSize(400,500);  
  
//Make JFrame visible. So we can see it.  
frame.setVisible(true);  

textArea1.setEditable(false);
textArea1.setLineWrap(true);
textArea1.setFont(new Font("Courier new", Font.PLAIN, 12));
}  

/**
 * Prints the String to the output appending a newline. 
 * @param str
 */
public void println(String str) {
	print(str + "\n");
}

/**
 * Prints the String to the output appending a newline. 
 * @param str
 */
public void print(String str) {
	// Als er teveel regels in staan wat weggooien.
	if (this.textArea1.getLineCount() >= 50) {
		this.textArea1.replaceRange(null, 0, str.length());
	}
	this.textArea1.append(str);
}

public void reset()
{
	textArea1.setText(null);
}

}  
