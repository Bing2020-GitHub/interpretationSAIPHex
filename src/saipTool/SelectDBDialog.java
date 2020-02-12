package saipTool;

import java.awt.FileDialog;
import java.awt.Frame;

public class SelectDBDialog extends FileDialog{

	
	private static final long serialVersionUID = 1L;

	public SelectDBDialog(Frame owner, String title) {
		super(owner, title);
		setFile("*.*");
	    setDirectory(".");
		 
		
	}

}
