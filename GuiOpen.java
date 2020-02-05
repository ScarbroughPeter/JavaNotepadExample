package pnote;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GuiOpen 
{
	Gui gui;
	JFrame window;
	JFileChooser fileChooser;
	
	GuiOpen(Gui g)
	{
		//get reference to main handler
		gui = g;
		
		//initialize components
		window = new JFrame();
		fileChooser = new JFileChooser();
		
		//set component properties
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setTitle("Open");
		window.setSize(800, 400);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG );
		
		//add actionlisteners to components
		fileChooser.addActionListener(new FileChooserActionListener());
		
		//stitch components together
		window.getContentPane().add(fileChooser, BorderLayout.CENTER);
		
				
		//re-draw window, necessary to refresh after adding components post-making it visible
		window.revalidate();
	}
	
	public class FileChooserActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String s = event.getActionCommand();
			if(s.equals("ApproveSelection"))
			{
				File f = fileChooser.getSelectedFile();
				gui.f = f;
				String filename = f.getName();
				gui.window.setTitle("PNotepad -- [" + filename + "]");
				gui.textArea.setText("");
				try
				{
					FileReader fReader = new FileReader(f);
					BufferedReader bReader = new BufferedReader(fReader);
					String line;
					int itr = 0;
					while((line = bReader.readLine()) != null)
					{
						if(itr != 0) gui.textArea.append("\n");
						itr++;
						gui.textArea.append(line);
					}
					bReader.close();
					window.dispose();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				
			}
			if(s.equals("CancelSelection"))
			{
				window.dispose();
			}
		}
	}
}
