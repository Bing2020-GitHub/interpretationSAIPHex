package saipTool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class asn1Text2Hex {

	
	public asn1Text2Hex(String fileName)
	{
	
		configFile.asnHexFile = fileName.substring(0, fileName.lastIndexOf('.'))+".out2";
		configFile.asnHexFile2 = fileName.substring(0, fileName.lastIndexOf('.')) +".txt2";
		try {
			
			
			File f_asnhex = new File(configFile.asnHexFile);
			FileWriter fw_asnhex = new FileWriter(f_asnhex);
			fw_asnhex = new FileWriter(f_asnhex, true);
			BufferedWriter bw_asnhex = new BufferedWriter(fw_asnhex);
			configFile.asnHex = new PrintWriter(bw_asnhex);
			
			File f_asnhex2 = new File(configFile.asnHexFile2);
			FileWriter fw_asnhex2 = new FileWriter(f_asnhex2);
			fw_asnhex2 = new FileWriter(f_asnhex2, true);
			BufferedWriter bw_asnhex2 = new BufferedWriter(fw_asnhex2);
			configFile.asnHex2 = new PrintWriter(bw_asnhex2);
		
		    readASNTextFile(fileName);
		 
		    configFile.asnHex.close();
		    configFile.asnHex2.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}

	
	  private void readASNTextFile(String sAsnFile) throws IOException
	  {
	    	BufferedReader br = new BufferedReader(new FileReader(sAsnFile));
			String nextLine = "";
			String sMainTag = "";
			int    numOfIteration = 1;
			int    recNum = 0;
			int    index_seperator = 0;
			

			
			while((nextLine = br.readLine())!= null)
			{
				if(((index_seperator =nextLine.indexOf(configFile.sSeperator))) != -1)
				{ 
					sMainTag = (nextLine.substring(index_seperator+configFile.sSeperator.length(), nextLine.length())).trim();
				    System.out.println(sMainTag);
				    configFile.asnHex.println("record : " +recNum++);
				    
				    if((configFile.bPEType =commUtil.searchPEType(sMainTag)) == (byte)0xFF)
				        return;
				   
				    String sMainTagVal = scanASNTextFile(br,br.readLine(),numOfIteration,"");
				    if(sMainTagVal.length() == 0)
				    {
				    	break;
				    }
				    System.out.println(sMainTagVal);
				    configFile.asnHex.println(sMainTagVal.toLowerCase());
				    configFile.asnHex.println();
				    configFile.asnHex2.print(sMainTagVal.toUpperCase());
				    

				}
			}
			br.close();
			
		}
		
	  private String getASNUInt8(String sTmpTextVal)
	  {
		    String sRet = "";
		    int    iRet = 0;
		    
		    iRet = Integer.parseInt(sTmpTextVal.trim());
		    if (iRet >=128 && iRet <= 255)
		    {
		    	sRet = "00" + Integer.toHexString(iRet);
		    }
		    else
		    { 
		    	sRet = Integer.toHexString(iRet);
		    }
		    if(((sRet.length()) % 2) == 1)
		    {
		    	sRet = '0' +sRet;
		    }
		    String sLen = commUtil.getValLen(sRet);
		    sRet = sLen +sRet;
		    
			return sRet;
	  }

		
		 private String getASNOctetStr(String sTmpTextVal) 
		    {
				String sRet = "";
				String sLen = "";
				sTmpTextVal = sTmpTextVal.trim();
				sTmpTextVal = (sTmpTextVal.replaceAll("'", "")).replaceAll("H", "");
				System.out.println(sTmpTextVal);
				sLen = commUtil.getValLen(sTmpTextVal);
				
				
				sRet = sLen + sTmpTextVal;
				return sRet;
			}
		    
		    private String getASNUTF8Str(String sTmpTextVal) 
		    {
				String sRet = "";
				String sLen = "";
				sTmpTextVal = (sTmpTextVal.replaceAll("\"", ""));
				for(int j = 0; j <sTmpTextVal.length(); j++)
				{
					//sRet += Character.toChars(bArray[j]);
					sRet += Integer.toHexString((int)(sTmpTextVal.charAt(j)));
				}	
				
				sLen = commUtil.getValLen(sRet);
				sRet = sLen+sRet;
				
				return sRet;
			}
		    
		    private String scanASNTextFile(BufferedReader br, String sTmpLine,int iNumOfInteration,String sSearchTag) throws IOException {
				String sNextLine = "";
				String sTLV = "";
				int index_seperator = 0;
				String sTextName = "",sTextVal = "";
				sNextLine = sTmpLine.trim();//(br.readLine()).trim();	
				System.out.println(sNextLine);
				if(sNextLine == null)
					return sTLV;
				//1. find the tag name
				System.out.println("interationNO"+iNumOfInteration);
				if(iNumOfInteration == 1)
				{
					 sTextName +=configFile.PEDefinition[configFile.bPEType][1];
				}
				else
				{
					if(sNextLine.indexOf("{") >=0)
					{
						index_seperator= sNextLine.indexOf("{");
					}
					else if(sNextLine.indexOf(",") >=0 && sNextLine.length() == 1)
					{
						return sTLV;
					}
					else
					{
						index_seperator= sNextLine.indexOf(" ");
								
					}	
					sTextName = (sNextLine.substring(0, index_seperator)).trim();		
				}
				String sTagName = searchASNTagName(configFile.bPEType,sTextName,sSearchTag);
			    String sPETagName = "";
				//asnHex.println("sSearchTag: " + sSearchTag + " sTagName :" +sTagName);
				sPETagName = sTagName.replaceFirst(sSearchTag, "");
				//sPETagName = getPETagName(sTagName, sSearchTag);
				//asnHex.println("remove  sSearchTag " + sSearchTag + " TagName :" +sTagName + " TLV="+sTLV);
				//2. find value of the tag
				if(( sNextLine.indexOf("{")) ==-1 )
				{
					sTextVal = (sNextLine.substring(index_seperator, sNextLine.length()));
					sTextVal = (sTextVal.trim()).replaceAll(":","").replaceAll(",", "");
					System.out.println(sTextVal);
					sTLV = sPETagName  +searchASNVal(configFile.bPEType,sTagName, sTextVal);
					//System.out.println(sTLV);
					//configFile.asnHex.println("primitive :" + sTLV+ " sPETagName :"+sPETagName+ " sTextVal : "+sTextVal);
					
				}
				else
				{
					
					sSearchTag += sPETagName;
					
					while((sNextLine = br.readLine()) !=null )
					{
						if(sNextLine.indexOf("{") >=0 && sNextLine.indexOf("}")>=0)
						{
							sNextLine = (sNextLine.replaceAll("\\{", "")).replaceAll("\\}", "");
						}
						if(sNextLine.indexOf("}") >=0)
						{
							break;
						}
						System.out.println(sNextLine);
					    sTLV +=scanASNTextFile(br, sNextLine,++iNumOfInteration,sSearchTag);
					    
					}
					if(sNextLine.indexOf("}") >=0)
					{
						
						sTLV = sPETagName +commUtil.getValLen(sTLV)+sTLV;
						//configFile.asnHex.println("Constructed : "+sTLV);

					}
					
				}
				return sTLV;
				
			}

			private String searchASNVal(Byte bTmpPEType,String sTmpTagName, String sTmpTextVal) {
				String sRet = "";
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
					if(sTmpTagName.equals(sTagTemplate))
					{
						
					    //sTagName = sPESubString.substring(index_firstComma+1, index_secondComma);
					    sTagDataType = sPESubString.substring(index_secondComma+1, sPESubString.length());
					    
					    switch(sTagDataType)
					    {
			 		        case "UInt8":
			 		        case "UInt16":
			 		    	    sRet = getASNUInt8(sTmpTextVal); 
						        break;
			 		        case "null":
			 		    	    sRet = "00";  
			 		    	    break;
			 		        case "OCTETString":
			 		    	    sRet = getASNOctetStr(sTmpTextVal);
			 		    	    break;
			 		        case "UTF8String":
			 		    	    sRet =  getASNUTF8Str(sTmpTextVal);
			 		    	    break;
			 		        case "OID":
			 		        	sRet =  getASNOID(sTmpTextVal);
			 		    	     break;
			 		        case "pinRef":
			 		        	sRet = getPinRef(sTmpTextVal);
			 		        	break;
			 		        case "pukRef":
			 		        	sRet = getPukRef(sTmpTextVal);
			 		        	break;
			 		        default:
			 		        	sRet = sTmpTextVal;
			 		    	     break;
					     }
					    break;
				    }
					
				}
				
				return sRet;
			}

			

			private String searchASNTagName(byte bTmpPEType, String sTmpTextName,String sParentTag) 
			{
				String[] sPEString = configFile.PEDefinition[bTmpPEType];
				String   sPESubString = "";
				int index_firstComma, index_secondComma;
				String sTagTemplate, sTagName;
				String sRet = "";
				
				for (int iPESubType = 2; iPESubType < sPEString.length; iPESubType++)
				{
					sPESubString = sPEString[iPESubType];
					index_firstComma = sPESubString.indexOf(",");
					index_secondComma = sPESubString.indexOf(",", index_firstComma + 1);

					sTagTemplate = sPESubString.substring(0, index_firstComma);
					
					sTagName = sPESubString.substring(index_firstComma+1, index_secondComma);
					
					if(sTmpTextName.equals(sTagName) && sTagTemplate.indexOf(sParentTag)>=0)
					{
						return sTagTemplate;
						
					}
				}
				return sRet;
			}
			private String getASNOID(String sTmpTextVal) 
			{
				String sRet ="";
				String[] sTmpArray = sTmpTextVal.split("\\.");
				int iOffset = 0, iHeadArcNum, iTmpArcNum, iArcNum,len;
				iHeadArcNum = Integer.parseInt(sTmpArray[iOffset])*40 +Integer.parseInt(sTmpArray[++iOffset]);
				iTmpArcNum = iHeadArcNum;
				for(len = 0; (iTmpArcNum>>=7)!=0; len++);

				for(int i = 0; i < len; i++)
				{
					sRet += commUtil.Byte2HexString(Integer.toHexString((0x80|(iHeadArcNum >>((len-i)*7)))));
				}
				sRet += commUtil.Byte2HexString(Integer.toHexString((0x7f & iHeadArcNum)));
				
				for(++iOffset;iOffset < sTmpArray.length;iOffset++ )
				{
					iArcNum = Integer.parseInt(sTmpArray[iOffset]);
					iTmpArcNum = iArcNum;
					for(len = 0; (iTmpArcNum>>=7)!=0; len++);
					for(int i = 0; i < len; i++)
					{
						sRet += commUtil.Byte2HexString(Integer.toHexString((0x80|(iArcNum >>((len-i)*7)))));
					}
					sRet += commUtil.Byte2HexString(Integer.toHexString((0x7f & iArcNum)));
					
				}
				
				String sLen = commUtil.getValLen(sRet);
				sRet = sLen + sRet;
				
				return sRet;
			}
			private String getPukRef(String sTmpTextVal) {
				String sRet = "";
				String sTmpRef = "";
				String sTmpRefVal = "", sTmpRefText = "";
				int index_firstComma;
			    for(int i = 0; i < configFile.PUKReferenceVal.length; i++)
				{
					sTmpRef = configFile.PUKReferenceVal[i];
					index_firstComma = sTmpRef.indexOf(",");;
					sTmpRefVal = sTmpRef.substring(0, index_firstComma);
					sTmpRefText= sTmpRef.substring(index_firstComma+1, sTmpRef.length());
					if(sTmpTextVal.equals(sTmpRefText))
				    {
					     sRet = sTmpRefVal;
					     break;
				    }
				}
			    int iRet = Integer.parseInt(sRet);
			    sRet = Integer.toHexString(iRet);
			 
			    if(sRet.length() %2 == 1)
				{
					sRet = "0" + sRet;
				}
			    if(iRet >127)
			    	sRet = "0200"+ sRet;
			    else 
			    	sRet = "01" + sRet;
			   // sRet = commUtil.getValLen(sRet) + sRet;
			    		
				return sRet;
			}

			private String getPinRef(String sTmpTextVal)
			{
				String sRet = "";
				String sTmpRef = "";
				String sTmpRefVal = "", sTmpRefText = "";
				int index_firstComma;
				
				for(int i = 0; i < configFile.PINReferenceVal.length; i++)
				{
					sTmpRef = configFile.PINReferenceVal[i];
					index_firstComma = sTmpRef.indexOf(",");;
					sTmpRefVal = sTmpRef.substring(0, index_firstComma);
					sTmpRefText= sTmpRef.substring(index_firstComma+1, sTmpRef.length());
					if(sTmpTextVal.equals(sTmpRefText))
				    {
					     sRet = sTmpRefVal;
					     break;
				    }
				}
                
				int iRet = Integer.parseInt(sRet);
				sRet = Integer.toHexString(iRet);
				 
				if(sRet.length() %2 == 1)
				{
						sRet = "0" + sRet;
				}
				if(iRet >127)
				    sRet = "0200"+ sRet;
				else 
				    sRet = "01" + sRet;
				//sRet = commUtil.getValLen(sRet) + sRet;
				return sRet;
			}

			
			

		    

			
		
}
