package saipTool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class commUtil {
	
	public static int skipZeroFF(final byte[] baBuffer, final int j, final int sLength)
    {
        int i = j;
        /**
         * if byte values 0x00 or 0xFF appear before, between, or after data objects, those bytes shall be ignored and not considered
         * errors.
         */
        for (; i < (int) (j + sLength); i++)
        {
            /**
             * if byte values 0x00 or 0xFF appear before, between, or after data objects, those bytes shall be ignored and not considered
             * errors.
             */
            if ((baBuffer[i] != 0x00) && ((int) (baBuffer[i] & 0x00FF) != (int) 0x00FF))
            {
                break;
            }// end if
        }
        return i;
    }
    
    
   
    public static String  skipFF(String strTmp)
    {
    	 int i = 0;
    	 int len = strTmp.length();
    	 if(len == 1)
    		 return '0'+strTmp;
         while (i < len) 
         {
        	 char c = strTmp.toUpperCase().charAt(i);
        	 if(c == 'F')
        	 {
        		 i++;
        	 }
        	 else
        	 {
        		 break;
        	 }
         }
    	return strTmp.substring(i,len);
    }
	
	public static String printHexString(  byte[] b,int i, int sTempLength) 
	{ 
	    String sRet = "";
		for (int j = i; j < i+ sTempLength; j++) 
	    { 
	        String hex = Integer.toHexString((byte)(b[j]) & 0xFF); 
	        if (hex.length() == 1) 
	        { 
	        	hex = '0' + hex; 
	        } 
	        sRet += hex.toUpperCase();
	        //System.out.print(hex.toUpperCase() + ""); 
	       // out.print(hex.toUpperCase() + "");
	   }
		//System.out.print(sRet); 
	   // out.print(sRet);
		return sRet;
		 

	} 
	  
	  
	/**
	 * Convert two bytes into short type
	*/
    public static final short bytesToShort(final byte b1, final byte b2) 
    {
        return (short) (b1 << 8 | (b2 & 0x00FF));
    }
    
    public static byte uniteBytes(byte src0, byte src1) 
    { 
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue(); 
        _b0 = (byte)(_b0 << 4); 
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue(); 
        byte ret = (byte)(_b0 ^ _b1); 
        return ret; 
    } 
    public static byte[] HexString2Bytes(String src)
    { 
        byte[] ret = new byte[src.length()/2]; 
    	byte[] tmp = src.getBytes(); 
    	for(int i=0; i<src.length()/2; i++)
    	{ 
    	    ret[i] = uniteBytes(tmp[i*2], tmp[i*2+1]); 
    	} 
    	return ret; 
    }
   
    public static String Byte2HexString(String sTmpVal)
    {
    	String sRet = sTmpVal;
    	if ((sTmpVal.length() %2) ==1)
    	{
    		sRet = "0" + sRet;
    	}
    	return sRet;
    }
    public static String Bytes2HexString(byte[] b) 
    { 
        String ret = ""; 
        for (int i = 0; i < b.length; i++) 
        { 
    	    String hex = Integer.toHexString((byte)(b[i]) & 0xFF); 
    	    if (hex.length() == 1) 
    	    { 
    	        hex = '0' + hex; 
    	    } 
    	    ret += hex.toUpperCase(); 
    	} 
    	return ret; 
    } 
	    
	/**
	 * Take two bytes from byte array and convert them to short type
	 */
	public static final short byteArrayToShort(final byte[] buf, final int bOff) 
	{
	    return bytesToShort((byte)buf[bOff], (byte)buf[bOff + 1]);
	}
	
	public static String readFile(String filename) throws IOException{
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		
		String nextLine = "";
		while((nextLine = br.readLine())!= null){
			System.out.println(nextLine);
			sb.append(nextLine.replaceAll(" ", ""));
		}
		br.close();
		return sb.toString();
	} 
	public static String getValLen(String sTmpTextVal)
	{
		String sRet = "";
        String sLen = "";
		int iLen = sTmpTextVal.length();
		if(((sTmpTextVal.length()) % 2) == 1)
		{
			sTmpTextVal = '0' +sTmpTextVal;
		}
		iLen = sTmpTextVal.length()/2;
		sLen = Integer.toHexString(sTmpTextVal.length()/2);
		if(sLen.length() %2  == 1)
		{
		    sLen = '0'+sLen;
		}
		if( iLen >255)
		{
			sLen = "82"+ sLen;
		}
		else if(iLen >127)
		{
			sLen = "81"+sLen;
		}
        sRet = sLen;
		return sRet;
	}
	
	public static byte searchPEType(String sTag)
	{
		byte bType = (byte)255;
		for (int iPEType = 0; iPEType < configFile.PEDefinition.length; iPEType++)
		{
			if(sTag.equals(configFile.PEDefinition[iPEType][0]))
			{
				bType = (byte) iPEType;
				break;
			}
		}
		return bType;
	}
	
	public static String readBinaryFile(String sFile)
	{
		String sCompleteFile = "";
		try {
	    	   InputStream inputStream = new FileInputStream(sFile);
	    	   int c;
	    	   try {
				    while((c = inputStream.read()) != -1){
					 System.out.print(Integer.toHexString(c) + " ")  ;
					 if((Integer.toHexString(c)).length() == 1)
						 sCompleteFile  += "0" +Integer.toHexString(c);
					 else 
						 sCompleteFile  += Integer.toHexString(c);
					 
			        }
				    System.out.println(" ");
				    System.out.print(sCompleteFile);
			    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    }
		 }
		 catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	     return   sCompleteFile;
	}
}
