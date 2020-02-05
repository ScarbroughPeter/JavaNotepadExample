package pnote;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.awt.Toolkit;

public class ClipboardManager implements ClipboardOwner
{
	public void lostOwnership(Clipboard c, Transferable  t)
	{
	}
	
	public String getClipboardContents()
	{
		String result = "";
		
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable clipboardContents = clipboard.getContents(null);
		if(clipboardContents == null) return(result);
		boolean validText = clipboardContents.isDataFlavorSupported(DataFlavor.stringFlavor);
	    if(validText) 
	    {
	        try 
	        {
	          result = (String) clipboardContents.getTransferData(DataFlavor.stringFlavor);
	        }
	        catch(UnsupportedFlavorException e)
	        {
	          e.printStackTrace();
	        }
	        catch(IOException e)
	        {
	        	e.printStackTrace();
	        }
	    }
		return(result);
	}
	
	public void setClipboardContents(String s)
	{
		StringSelection sSelection = new StringSelection(s);
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		c.setContents(sSelection, this);
	}
}
