package pnote;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class Gui 
{
	
	PNotepad pn;
	JFrame window;
	JScrollPane scrollPane;
	JTextArea textArea;
	JMenuBar menuBar;
	JMenu menuFile;
	JMenu menuEdit;
	JMenu menuFormat;
	JMenuItem fileNew;
	JMenuItem fileOpen;
	JMenuItem fileSave;
	JMenuItem fileSaveAs;
	JMenuItem editCopy;
	JMenuItem editPaste;
	JMenuItem formatFont;
	File f;
	ClipboardManager clipboardManager;
	Font font;
	
	GuiSave guiSave;
	GuiOpen guiOpen;
	GuiFont guiFont;
	
	Gui(PNotepad p)
	{
		//get reference to main handler
		pn = p;
		
		//initialize components
		window = new JFrame();
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea);
		menuBar = new JMenuBar();
		menuFile = new JMenu();
		menuEdit = new JMenu();
		menuFormat = new JMenu();
		fileNew = new JMenuItem();
		fileOpen = new JMenuItem();
		fileSave = new JMenuItem();
		fileSaveAs = new JMenuItem();
		editCopy = new JMenuItem();
		editPaste = new JMenuItem();
		formatFont = new JMenuItem();
		clipboardManager = new ClipboardManager();
		font = textArea.getFont();
		
		//set component properties
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setTitle("PNotepad -- [Untitled]");
		window.setSize(400, 400);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		menuFile.setText("File");
		menuEdit.setText("Edit");
		menuFormat.setText("Format");
		fileNew.setText("New");
		fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		fileOpen.setText("Open");
		fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		fileSave.setText("Save");
		fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		fileSaveAs.setText("Save As");
		fileSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
		editCopy.setText("Copy");
		editCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		editPaste.setText("Paste");
		editPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		formatFont.setText("Font");
		formatFont.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textArea.setLineWrap(true);
		
		//add actionlisteners to components
		fileNew.addActionListener(new FileNewActionListener());
		fileOpen.addActionListener(new FileOpenActionListener(this));
		fileSave.addActionListener(new FileSaveActionListener(this));
		fileSaveAs.addActionListener(new FileSaveAsActionListener(this));
		editCopy.addActionListener(new EditCopyActionListener());
		editPaste.addActionListener(new EditPasteActionListener());
		formatFont.addActionListener(new FormatFontActionListener(this));
		
		
		//stitch components together
		window.getContentPane().add(menuBar, BorderLayout.NORTH);
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		menuBar.add(menuFormat);
		menuFile.add(fileNew);
		menuFile.add(fileOpen);
		menuFile.add(fileSave);
		menuFile.add(fileSaveAs);
		menuEdit.add(editCopy);
		menuEdit.add(editPaste);
		menuFormat.add(formatFont);
		window.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
	
		//re-draw window, necessary to refresh after adding components post-making it visible
		window.revalidate();
	}
	
	public class FileNewActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			window.setTitle("PNotepad -- [Untitled]");
			textArea.setText("");
		}
	}
	
	public class FileOpenActionListener implements ActionListener
	{
		
		Gui gui;
		
		FileOpenActionListener(Gui g)
		{
			gui = g;
		}
		
		public void actionPerformed(ActionEvent event)
		{
			gui.guiOpen = new GuiOpen(gui);
		}
	}
	
	public class FileSaveActionListener implements ActionListener
	{
		
		Gui gui;
		
		FileSaveActionListener(Gui g)
		{
			gui = g;
		}
		
		public void actionPerformed(ActionEvent event)
		{
			String title = window.getTitle();
			if(title.equals("PNotepad -- [Untitled]"))
			{
				gui.guiSave = new GuiSave(gui);
			}
			else
			{
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
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}	
			}
		}
	}
	
	public class FileSaveAsActionListener implements ActionListener
	{
		
		Gui gui;
		
		FileSaveAsActionListener(Gui g)
		{
			gui = g;
		}
		
		public void actionPerformed(ActionEvent event)
		{
			gui.guiSave = new GuiSave(gui);
		}
	}
	
	public class EditCopyActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String s = textArea.getSelectedText();
			if(s.equals(null)) return;
			clipboardManager.setClipboardContents(s);
		}
	}
	
	public class EditPasteActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String s = clipboardManager.getClipboardContents();
			if(s.equals("")) return;
			int pos = textArea.getCaretPosition();
			textArea.insert(s, pos);
		}
	}
	
	public class FormatFontActionListener implements ActionListener
	{
		
		Gui gui;
		
		FormatFontActionListener(Gui g)
		{
			gui = g;
		}
		
		public void actionPerformed(ActionEvent event)
		{
			gui.guiFont = new GuiFont(gui);
		}
	}
}
