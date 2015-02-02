import javax.swing.JFrame;  
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;  
import javax.swing.text.DefaultCaret;
  
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;  
  
public class TextView extends JPanel
{

	JTextArea textArea1;
	
	public TextView()  
	{  
		//Create JTextArea without initial text  
		textArea1=new JTextArea();  
		  
		//Create a JFrame with title ( Add JTextArea into JFrame )  
		  
		//Set JFrame layout  
		setLayout(new GridLayout());  
		//Add first JTextArea into JFrame  
		JScrollPane sp = new JScrollPane(textArea1); 
		add(sp);   
		  
		//Set default close operation for JFrame  
		  
		//Set JFrame size  
		setSize(400,500);  
		  
		//Make JFrame visible. So we can see it.  
		setVisible(true);  
		
		textArea1.setEditable(false);
		textArea1.setLineWrap(true);
		textArea1.setFont(new Font("Courier new", Font.PLAIN, 12));
		DefaultCaret caret = (DefaultCaret) textArea1.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}  
	
	/**
	 * Prints the String to the output appending a newline. 
	 * @param str
	 */
	public void println(String str) {
		print(str + " ");
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