import javax.swing.JComponent;
import javax.swing.JFrame;  
import javax.swing.JScrollPane;
import javax.swing.JTextArea;  
import javax.swing.text.DefaultCaret;
  
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;  
  
public class TextView
{

	JTextArea textArea1;
	
	public TextView()  
	{  
		//Create JTextArea without initial text  
		textArea1=new JTextArea();  
		textArea1.setEditable(true);
		textArea1.setLineWrap(true);
		textArea1.setFont(new Font("Courier new", Font.PLAIN, 12));
		DefaultCaret caret = (DefaultCaret) textArea1.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		  
		//Create a JFrame with title ( Add JTextArea into JFrame )  
		JFrame frame=new JFrame("TextView");  
		  
		//Set JFrame layout  
		frame.setLayout(new GridLayout());  
		  
		//Add first JTextArea into JFrame  
		JScrollPane sp = new JScrollPane(textArea1); 
		frame.add(sp, BorderLayout.CENTER);   
		   
		//Set JFrame size  
		frame.setSize(400,500);  
		  
		//Make JFrame visible. So we can see it.  
		frame.setVisible(true);  
		
		
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
		this.textArea1.setText(null);
	}

}  