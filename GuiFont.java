package pnote;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class GuiFont
{
	Gui gui;
	JFrame window;
    JCheckBox boldCheckBox;
    JButton cancelButton;
    JLabel fontLabel;
    JScrollPane fontScrollPane;
    JLabel fontSizeLabel;
    JScrollPane fontSizeScrollPane;
    JCheckBox italicsCheckBox;
    JButton okButton;
    JLabel sampleLabel;
    JLabel sampleText;
    JPanel sampleTextPane;
    JList<String> fontNamesList;
    JList<String> fontSizesList;
    Font testFont;
	
	GuiFont(Gui g)
	{
		//initialize components
		gui = g;
		window = new JFrame();
        fontLabel = new JLabel();
        String[] fontNamesArray = getFontNamesArray();
        String[] fontSizesArray = getFontSizesArray();
        fontNamesList = new JList<String>(fontNamesArray);
        fontSizesList = new JList<String>(fontSizesArray);
        fontScrollPane = new JScrollPane(fontNamesList);
        fontSizeScrollPane = new JScrollPane(fontSizesList);
        fontSizeLabel = new JLabel();
        boldCheckBox = new JCheckBox();
        italicsCheckBox = new JCheckBox();
        sampleLabel = new JLabel();
        cancelButton = new JButton();
        okButton = new JButton();
        sampleTextPane = new JPanel();
        sampleText = new JLabel();
        testFont = gui.textArea.getFont();

        //set component parameters
        window.setSize(600, 400);
        window.setTitle("Font");
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fontLabel.setText("Font");
        fontSizeLabel.setText("Font Size");
        boldCheckBox.setText("Bold");
        italicsCheckBox.setText("Italics");
        sampleLabel.setText("Sample");
        cancelButton.setText("Cancel");
        okButton.setText("OK");
        sampleTextPane.setBackground(new java.awt.Color(204, 204, 204));
        sampleTextPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sampleText.setText("ABC abc 123");
        sampleText.setFont(testFont);
        boldCheckBox.setSelected(testFont.isBold());
        italicsCheckBox.setSelected(testFont.isItalic());
        
        //give components actionlisteners
        okButton.addActionListener(new OkButtonActionListener());
        cancelButton.addActionListener(new CancelButtonActionListener());
        boldCheckBox.addActionListener(new BoldCheckBoxActionListener());
        italicsCheckBox.addActionListener(new ItalicsCheckBoxActionListener());
        fontNamesList.addListSelectionListener(new FontNamesListSelectionListener());
        fontSizesList.addListSelectionListener(new FontSizesListSelectionListener());
        
        //stich components together, set layout (done in netbeans)
        setLayout();
        
        //finish up 
        window.setVisible(true);
        window.revalidate();
	}
	
	public class FontNamesListSelectionListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent event)
		{
			String fontName = fontNamesList.getSelectedValue();
			int fontStyle = testFont.getStyle();
			int fontSize = testFont.getSize();
			testFont = new Font(fontName, fontStyle, fontSize);
			sampleText.setFont(testFont);
		}
	}
	
	public class FontSizesListSelectionListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent event)
		{
			String fontName = testFont.getName();
			int fontStyle = testFont.getStyle();
			int fontSize = Integer.parseInt(fontSizesList.getSelectedValue());
			testFont = new Font(fontName, fontStyle, fontSize);
			sampleText.setFont(testFont);
		}
	}
	
	public class BoldCheckBoxActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String fontName = testFont.getName();
			int fontSize = testFont.getSize();
			boolean isBold = testFont.isBold();
			boolean isItalic = testFont.isItalic();
			if(!isBold && !isItalic)
			{
				testFont = new Font(fontName, Font.BOLD, fontSize);
			}
			else if(!isBold && isItalic)
			{
				testFont = new Font(fontName, Font.BOLD | Font.ITALIC, fontSize);
			}
			else if(isBold && !isItalic)
			{
				testFont = new Font(fontName, Font.PLAIN, fontSize);
			}
			else if(isBold && isItalic)
			{
				testFont = new Font(fontName, Font.ITALIC, fontSize);
			}
			sampleText.setFont(testFont);
		}
	}
	
	public class ItalicsCheckBoxActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String fontName = testFont.getName();
			int fontSize = testFont.getSize();
			boolean isBold = testFont.isBold();
			boolean isItalic = testFont.isItalic();
			if(!isBold && !isItalic)
			{
				testFont = new Font(fontName, Font.ITALIC, fontSize);
			}
			else if(!isBold && isItalic)
			{
				testFont = new Font(fontName, Font.PLAIN, fontSize);
			}
			else if(isBold && !isItalic)
			{
				testFont = new Font(fontName, Font.BOLD | Font.ITALIC, fontSize);
			}
			else if(isBold && isItalic)
			{
				testFont = new Font(fontName, Font.BOLD, fontSize);
			}
			sampleText.setFont(testFont);
		}
	}
	
	public class OkButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			gui.textArea.setFont(testFont);
			window.dispose();
		}
	}
	
	public class CancelButtonActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			window.dispose();
		}
	}
	
	public String[] getFontNamesArray()
	{
		Font[] fontArray = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		String[] fontNamesArray = new String[fontArray.length];
		for(int i = 0; i < fontArray.length; i++)
		{
			fontNamesArray[i] = fontArray[i].getName();
		}
		return(fontNamesArray);
	}
	
	public String[] getFontSizesArray()
	{
		String[] fontSizesArray = new String[100];
		for(int i = 0; i < 100; i++)
		{
			fontSizesArray[i] = Integer.toString(i);
		}
		return(fontSizesArray);
	}
	
	public void setLayout()
	{
        javax.swing.GroupLayout sampleTextPaneLayout = new javax.swing.GroupLayout(sampleTextPane);
        sampleTextPane.setLayout(sampleTextPaneLayout);
        sampleTextPaneLayout.setHorizontalGroup(
            sampleTextPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sampleTextPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sampleText)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        sampleTextPaneLayout.setVerticalGroup(
            sampleTextPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sampleTextPaneLayout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(sampleText)
                .addContainerGap())
        );

        GroupLayout layout = new javax.swing.GroupLayout(window.getContentPane());
        window.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(italicsCheckBox)
                            .addComponent(boldCheckBox)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fontScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fontLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fontSizeLabel)
                                    .addComponent(fontSizeScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(sampleLabel))
                        .addGap(0, 121, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sampleTextPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fontLabel)
                    .addComponent(fontSizeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fontScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fontSizeScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boldCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(italicsCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sampleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cancelButton)
                            .addComponent(okButton)))
                    .addComponent(sampleTextPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
	}
}