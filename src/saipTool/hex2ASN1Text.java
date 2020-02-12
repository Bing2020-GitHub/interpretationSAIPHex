package saipTool;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;




public class hex2ASN1Text {

    protected static int scanTLV( final byte[] baBuffer, final int sOffset,  final int sLength,int numOfIteration, String sSearchTag)
    {
        short sTempTag = 0x00;
        int sTempLength = 0x00;
        int i = 0x00;
        int j = 0x00;
        byte bTagFirstByte = 0x00;
        byte bTagSecondByte = 0x00;

 

        //i = commUtil.skipZeroFF(baBuffer, (int)sOffset, sLength);
        i = sOffset;
        if (i > (int)(sOffset+ sLength))
        {
           return 0;
        }
        if (i == (int)(sOffset+ sLength))
        {
            return i;
        }
        
        bTagFirstByte = (byte)(baBuffer[i]);
        bTagSecondByte = (byte)(baBuffer[(int)(i+1)]);
        
        if ((byte)(bTagFirstByte & 0x1F) != 0x1F)
        {
            // one byte tag
            sTempTag = commUtil.bytesToShort((byte) 0x00, bTagFirstByte);
            // compare the tags
            i++; // jump to length
        }
        else if ((byte)(bTagSecondByte & 0x10) != (byte)0x80)
        {
            sTempTag = commUtil.byteArrayToShort(baBuffer, i);
            i += (short) 2; // jump to tag length
        }
        else 
        {
        	i += (short) 3; //jump to tag length
        }
        System.out.print(commUtil.skipFF(Integer.toHexString(sTempTag)));
        sSearchTag += commUtil.skipFF(Integer.toHexString(sTempTag));
        if(numOfIteration == 1 )
        {  
           configFile.sMainTag = sSearchTag;
           configFile.asn.println(configFile.sSeperator + configFile.sMainTag);
           configFile.bPEType = commUtil.searchPEType(configFile.sMainTag);
          
        }
        if(configFile.bPEType != (byte)0xFF)
        {
            printASNTagName(configFile.bPEType, sSearchTag);
        }
        configFile.out.print(commUtil.skipFF(Integer.toHexString(sTempTag)));
        
        if (baBuffer[i] == (byte) 0x82)
        { // three byte value
            sTempLength = commUtil.byteArrayToShort(baBuffer, (int) (i + 1));
            i += 3;
        }
        else
        {
            if (baBuffer[i] == (byte) 0x81)
            { // two byte value
                i++;
            }
            // one byte value
            sTempLength = commUtil.bytesToShort((byte) 0, (byte)(baBuffer[i]));
            i++;
        }
        
        System.out.print(" ");
        configFile.out.print(" ");
        if(sTempLength <16)
        {
        	System.out.print("0"+Integer.toHexString(sTempLength));
        	configFile.out.print("0"+Integer.toHexString(sTempLength));
        }   
        else if(sTempLength >= 16 && sTempLength <128)
        {
            System.out.print(Integer.toHexString(sTempLength));
            configFile.out.print(Integer.toHexString(sTempLength));
        }
        else if(sTempLength > 127 && sTempLength < 256)
        {
            System.out.print("81"+ Integer.toHexString(sTempLength));
            configFile.out.print(Integer.toHexString(sTempLength));
        }
        else if(sTempLength > 255 && sTempLength <65536)
        {
        	System.out.print("82" + ((Integer.toHexString(sTempLength).length() ==3) ? ("0"+Integer.toHexString(sTempLength)) : (Integer.toHexString(sTempLength)) ));
        	configFile.out.print("82" + ((Integer.toHexString(sTempLength).length() ==3) ? ("0"+Integer.toHexString(sTempLength)) : (Integer.toHexString(sTempLength)) ));
        }
        System.out.print(" ");
        configFile.out.print(" ");
        configFile.asn.print(" ");
        configFile.iaLen[numOfIteration] = i+sTempLength;
       
       
        /*if(bPEType == 255)
        {
        	return (short)(i + sTempLength);
        }*/
        if (((byte)(bTagFirstByte & 0x20) != 0x20))
        {
        	String sValue = commUtil.printHexString(baBuffer, i, sTempLength);
    		System.out.print(sValue); 
    	    configFile.out.print(sValue);
        	
        	//asn.print(sValue);
        	if(configFile.bPEType != (byte)0xFF)
        	{
        	   printASNTagValue(configFile.bPEType, sSearchTag, baBuffer, i, sTempLength);
        	}
        	if((i + sTempLength) < configFile.iaLen[numOfIteration-1] )
        	{
        		configFile.asn.print(",");
        	}
        	
        }
        
        
        if ((sTempLength < (int)0) || 
            (int) (sTempLength + i) > (int) (sOffset+ sLength))
        {
            return 0;
        }
        if (((byte)(bTagFirstByte & 0x20) == 0x20))
        {
        	configFile.asn.print("{");
            for (j = i; j < (int) (i + sTempLength);)
            {
            	System.out.println("");
            	configFile.out.println("");
            	configFile.asn.println();
            	
            	for(int l=0;l<numOfIteration;l++)
            	{
                	System.out.printf("%2s","");
                	configFile.out.printf("%2s", "");
                	configFile.asn.printf("%2s","");
            	}
                j = scanTLV(baBuffer, j, (int) (sTempLength - (int) (j - i)),++numOfIteration,sSearchTag);
                numOfIteration--;
                
            }
            configFile.asn.println();
            for(int l=1;l<numOfIteration;l++)
        	{
            	configFile.asn.printf("%2s","");
        	}
            
            if( numOfIteration == 1 || j >= configFile.iaLen[numOfIteration-1])
            {  
            	configFile.asn.print("}" );
            }
            else 
            {
            	configFile.asn.print("},");
            }
        }
        
        
        return (int)(i + sTempLength);
    }
	  

	private static String printPukRef(byte[] baBuffer, int iOffset, int iTempLength) {
		String sRet = "";
		String sTmpRef = "";
		String sTmpRefVal = "", sTmpRefText = "";
		int index_firstComma;
		String sPukRef = Integer.toString(printUInt16(baBuffer, iOffset, iTempLength));
		
		for(int i = 0; i < configFile.PUKReferenceVal.length; i++)
		{
			sTmpRef = configFile.PUKReferenceVal[i];
			index_firstComma = sTmpRef.indexOf(",");;
			sTmpRefVal = sTmpRef.substring(0, index_firstComma);
			sTmpRefText= sTmpRef.substring(index_firstComma+1, sTmpRef.length());
			if(sPukRef.equals(sTmpRefVal))
		    {
			     sRet = sTmpRefText;
			     break;
		    }
		}
		return sRet;
	}

	private static String printPinRef(byte[] baBuffer, int iOffset,int iTempLength) 
	{
		String sRet = "";
		String sTmpRef = "";
		String sTmpRefVal = "", sTmpRefText = "";
		int index_firstComma;
		String sPukRef = Integer.toString(printUInt16(baBuffer, iOffset, iTempLength));
		
		for(int i = 0; i < configFile.PINReferenceVal.length; i++)
		{
			sTmpRef = configFile.PINReferenceVal[i];
			index_firstComma = sTmpRef.indexOf(",");;
			sTmpRefVal = sTmpRef.substring(0, index_firstComma);
			sTmpRefText= sTmpRef.substring(index_firstComma+1, sTmpRef.length());
			if(sPukRef.equals(sTmpRefVal))
		    {
			     sRet = sTmpRefText;
			     break;
		    }
		}
		return sRet;
	}

   
	private static short printUInt16(byte[] baBuffer, int iOffset,int iTempLength)
	{
		Byte b1 = 0;
		Byte b2 = 0;
		
		if(iTempLength ==1) b2 = (byte)(baBuffer[iOffset]);
		if(iTempLength == 2)
		{
			b1 = (byte)(baBuffer[iOffset++]);
			b2 = (byte)(baBuffer[iOffset]);
		}
		
		return commUtil.bytesToShort(b1, b2);
		
		
	}
	
	

	private static String printASNOID(byte[] bArray, int i, int iTempLen)
	{
		String sRet = "";
	    int arcNum;
		int j;
		for (arcNum = 0, j=0; ((j < iTempLen) && ((byte)(bArray[i]) & 0x80)>0);j++,i++)
		    arcNum = (arcNum << 7) + ((byte)(bArray[i]) & 0x7f);

		arcNum = (arcNum << 7) + ((byte)(bArray[i]) & 0x7f);
		
		i++;
		j++;
		int iFirstArcNum = arcNum/40;
		if (iFirstArcNum > 2)
		    iFirstArcNum = 2;
		sRet = Integer.toString(iFirstArcNum) +"."+ Integer.toString(arcNum - (iFirstArcNum *40)) ;
		for(; j < iTempLen;)
		{
			for(arcNum = 0; j< iTempLen && (bArray[i]& 0x80) >0; j++,i++)
			{
				arcNum = (arcNum << 7) + (bArray[i] & 0x7f);
			}
			arcNum = (arcNum << 7) + (bArray[i] & 0x7f);
			i++;
			j++;
			sRet += "."+Integer.toString(arcNum) ;
		}
		return sRet;
	}
	private static String printAscString(byte[] bArray, int i, int iTempLen)
	{
		String sRet = "";
		for(int j = i; j <i + iTempLen; j++)
		{
			//sRet += Character.toChars(bArray[j]);
			sRet += (char)((byte)(bArray[j]));
		}	
		return sRet;
	}
	
	private static void printASNTagName(byte bTmpPEType, String sTmpTag )
	{
		String[] sPEString = configFile.PEDefinition[bTmpPEType];
		String   sPESubString = "";
		int index_firstComma, index_secondComma;
		String sTagTemplate, sTagName;
		
		for (int iPESubType = 2; iPESubType < sPEString.length; iPESubType++)
		{
			sPESubString = sPEString[iPESubType];
			index_firstComma = sPESubString.indexOf(",");
			index_secondComma = sPESubString.indexOf(",", index_firstComma + 1);

			sTagTemplate = sPESubString.substring(0, index_firstComma);
			
			sTagName = sPESubString.substring(index_firstComma+1, index_secondComma);
			
			if(sTmpTag.equals(sTagTemplate))
			{
				configFile.asn.print(sTagName);
				break;
			}
		}
	}
	
	private static void printASNTagValue(byte bTmpPEType, String sTmpTag,byte[] baBuffer, int iOffset, int iTempLength) 
	{
		String[] sPEString = configFile.PEDefinition[bTmpPEType];
		String   sPESubString = "";
		int index_firstComma, index_secondComma;
		String sTagTemplate, sTagDataType;
		
		for (int iPESubType = 2; iPESubType < sPEString.length; iPESubType++)
		{
			sPESubString = sPEString[iPESubType];
			index_firstComma = sPESubString.indexOf(",");
			index_secondComma = sPESubString.indexOf(",", index_firstComma + 1);

			sTagTemplate = sPESubString.substring(0, index_firstComma);
			if(sTmpTag.equals(sTagTemplate))
			{
				
			    //sTagName = sPESubString.substring(index_firstComma+1, index_secondComma);
			    sTagDataType = sPESubString.substring(index_secondComma+1, sPESubString.length());
			    
			    switch(sTagDataType)
			    {
	 		        case "UInt8":
	 		    	    //asn.print(baBuffer[iOffset]);	 		    	
				        //break;
	 		        case "UInt16":
	 		        	configFile.asn.print(printUInt16(baBuffer,iOffset,iTempLength));
	 		        	break;
	 		        case "UTF8String":
	 		        	configFile.asn.print("\""+ printAscString(baBuffer, iOffset, iTempLength)+"\"" );
	 		    	    break;
	 		        case "OCTETString":
	 		        	configFile.asn.print("'"+commUtil.printHexString(baBuffer, iOffset, iTempLength) +"'H");
	 		    	    break;
	 		        case "null":
	 		        	configFile.asn.print("NULL");
	 		    	    break;
	 		        case "OID" :
	 		        	configFile.asn.print("{"+printASNOID(baBuffer, iOffset, iTempLength) +"}");
	 		    	     break;
	 		        case "pinRef":
	 		        	configFile.asn.print(printPinRef(baBuffer, iOffset, iTempLength));
	 		        	break;
	 		        case "pukRef":
	 		        	configFile.asn.print(printPukRef(baBuffer, iOffset, iTempLength));
	 		        	break;
	 		        default:
	 		    	     break;
			     }
			    break;
		    }
			
		}
	}
	
 
	
	private static void clearLenArray()
	{
		for(int i = 0; i < configFile.iaLen.length; i++)
		{
			configFile.iaLen[i] = 0;
		}
	}
	
	public hex2ASN1Text(String fileName)  {
		String strCompleteFile = "";
		
		try {
			strCompleteFile = commUtil.readFile(fileName);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}	
        
		configFile.outFile = fileName.substring(0, fileName.lastIndexOf('.'))+".out1";
		configFile.asnFile = fileName.substring(0, fileName.lastIndexOf('.'))+".asn1";
		try {
			File f_out = new File(configFile.outFile);
			FileWriter fw_out = new FileWriter(f_out);
			fw_out = new FileWriter(f_out, true);
			BufferedWriter bw_out = new BufferedWriter(fw_out);
			configFile.out = new PrintWriter(bw_out);
			
			
			File f_asn = new File(configFile.asnFile);
			FileWriter fw_asn = new FileWriter(f_asn);
			fw_asn = new FileWriter(f_asn, true);
			BufferedWriter bw_asn = new BufferedWriter(fw_asn);
			configFile.asn = new PrintWriter(bw_asn);
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//byte[] baTLVList = HexString2Bytes("A142A0058000810103A139301F80027FDE621282024121830213B08B032F060A8001058800810501017FF1013016621482044621000883026FFF8B032F060B8001088800A225A0058000810106A11CA01A301880020081810831323334FFFFFFFF82020081830103840155");
		
   
		byte[] baTLVList = commUtil.HexString2Bytes(strCompleteFile);
    	System.out.println("String To beintepreted: "+commUtil.Bytes2HexString(baTLVList));
    	int i = 0;
    	int recNum = 0;
        String sSearchTag = "";
    	do {  	
    		configFile.out.println("Record :" +recNum);
        	System.out.println("Record :" + recNum);
        	configFile.asn.print("Record :" + recNum + " ");
        	recNum = recNum +1;
            i = scanTLV( baTLVList,(int)i, (int) baTLVList.length,1,sSearchTag);
            clearLenArray();
            sSearchTag ="";
            System.out.println();
            configFile.out.println(""); 
            configFile.asn.println("");
            
    	}while(i < baTLVList.length);
    	configFile.out.close();
    	configFile.asn.close();

	 
}

	


   
	


}
