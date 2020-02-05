package pnote;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GuiSave 
{
	
	Gui gui;
	JFrame window;
	JFileChooser fileChooser;
	
	GuiSave(Gui g)
	{	
		//get reference to main handler
		gui = g;
		
		//initialize components
		window = new JFrame();
		fileChooser = new JFileChooser();
		
		//set component properties
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setTitle("Save As");
		window.setSize(800, 400);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG );
		
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
				try
				{
					StringReader sReader = new StringReader(gui.textArea.getText());
					BufferedReader bReader = new BufferedReader(sReader);
					FileWriter fWriter = new FileWriter(f);
					BufferedWriter bWriter = new BufferedWriter(fWriter);
					String line;
					int itr = 0;
					while((line = bReader.readLine()) != null)
					{
						if(itr != 0) bWriter.append(System.lineSeparator());
						itr++;
						bWriter.append(line);
					}
					bReader.close();
					bWriter.close();
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
