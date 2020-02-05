package pnote;

public class PNotepad 
{
	
	Gui gui;
	
	public static void main(String[] args)
	{
		PNotepad pn = new PNotepad();
		pn.gui = new Gui(pn);
	}
}
