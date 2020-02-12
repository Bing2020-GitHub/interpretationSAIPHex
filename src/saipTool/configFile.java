package saipTool;

import java.io.PrintWriter;

public class configFile {
	public final static String PUKReferenceVal[] ={
		"1,pukAppl1","2,pukAppl2","3,pukAppl3","4,pukAppl4","5,pukAppl5","6,pukAppl6","7,pukAppl7","8,pukAppl8",
		"129,secondPUKAppl1","130,secondPUKAppl2","131,secondPUKAppl3","132,secondPUKAppl4","133,secondPUKAppl5",
		"134,secondPUKAppl6","135,secondPUKAppl7","136,secondPUKAppl8"
	};
	public final static String PINReferenceVal[] = {
		"1,pinAppl1","2,pinAppl2","3,pinAppl3","4,pinAppl4","5,pinAppl5","6,pinAppl6","7,pinAppl7","8,pinAppl8",
		"10,adm1","11,adm2","12,adm3","13,adm4","14,adm5","138,adm6","139,adm7","140,adm8","141,adm9","141,adm10",
		"129,secondPINAppl1","130,secondPINAppl2","131,secondPINAppl3","132,secondPINAppl4","133,secondPINAppl5",
		"134,secondPINAppl6","135,secondPINAppl7","136,secondPINAppl8"
	};
	public final static String PEDefinition[][] = {
		{"a0","headerVal ProfileElement ::= header :", "a0,headerVal ProfileElement ::= header :,construct", 
		 "a080,major-version,UInt8","a081,minor-version,UInt8","a082,profileType,UTF8String", "a083,iccid,OCTETString", 
		 "a0a5,eUICC-Mandatory-services,construct",
		 "a0a581,usim,null","a0a582,isim,null","a0a584,milenage,null","a0a58b,javacard,null",
		 "a0a6,eUICC-Mandatory-GFSTEList,construct", "a0a606,oid,OID",
		 "a087,Connectivity,OCTETString"
		 },
		 {   "b0","mfVal ProfileElement ::= mf :","b0,mfVal ProfileElement ::= mf :,construct",
			 "b0a0,mf-header,construct","b0a080,mandated,null","b0a081,identification,UInt8",
			 "b081,templateID,OID",
			 "b0a2,mf,construct","b0a2a1,fileDescriptor :,construct","b0a2a182,fileDescriptor,OCTETString","b0a2a183,fileID,OCTETString",
			 "b0a2a184,dfName,String","b0a2a18b,securityAttributesReferenced,OCTETString","b0a2a1c6,pinStatusTemplateDO,OCTETString","b0a2a188,shortEFID,OCTETString",
			 "b0a3,ef_pl,construct","b0a3a1,fileDescriptor :,construct","b0a3a182,fileDescriptor,OCTETString","b0a3a183,fileID,OCTETString","b0a3a180,efFileSize,OCTETString",
			 "b0a3a184,dfName,String","b0a3a18b,securityAttributesReferenced,OCTETString","b0a3a1c6,pinStatusTemplateDO,OCTETString","b0a3a188,shortEFID,OCTETString",
			 "b0a383,fillFileContent,OCTETString","b0a382,fillFileOffset,UInt16",
			 "b0a4,ef_iccid,construct","b0a4a1,fileDescriptor :,construct","b0a4a182,fileDescriptor,OCTETString","b0a4a183,fileID,OCTETString","b0a4a180,efFileSize,OCTETString",
			 "b0a4a184,dfName,String","b0a4a18b,securityAttributesReferenced,OCTETString","b0a4a1c6,pinStatusTemplateDO,OCTETString","b0a4a188,shortEFID,OCTETString",
			 "b0a483,fillFileContent,OCTETString","b0a482,fillFileOffset,UInt16",
			 "b0a5,ef_dir,construct","b0a5a1,fileDescriptor :,construct","b0a5a182,fileDescriptor,OCTETString","b0a5a183,fileID,OCTETString","b0a5a180,efFileSize,OCTETString",
			 "b0a5a184,dfName,String","b0a5a18b,securityAttributesReferenced,OCTETString","b0a5a1c6,pinStatusTemplateDO,OCTETString","b0a5a188,shortEFID,OCTETString",
			 "b0a583,fillFileContent,OCTETString","b0a582,fillFileOffset,UInt16",
			 "b0a6,ef_arr,construct","b0a6a1,fileDescriptor :,construct","b0a6a182,fileDescriptor,OCTETString","b0a6a183,fileID,OCTETString","b0a6a180,efFileSize,OCTETString",
			 "b0a6a184,dfName,String","b0a6a18b,securityAttributesReferenced,OCTETString","b0a6a1c6,pinStatusTemplateDO,OCTETString","b0a6a188,shortEFID,OCTETString",
			 "b0a683,fillFileContent,OCTETString","b0a682,fillFileOffset,UInt16",
			 "b0a7,ef-umpc,construct","b0a7a1,fileDescriptor :,construct","b0a7a182,fileDescriptor,OCTETString","b0a7a183,fileID,OCTETString","b0a7a180,efFileSize,OCTETString",
			 "b0a7a184,dfName,String","b0a7a18b,securityAttributesReferenced,OCTETString","b0a7a1c6,pinStatusTemplateDO,OCTETString","b0a7a188,shortEFID,OCTETString",
			 "b0a783,fillFileContent,OCTETString","b0a782,fillFileOffset,UInt16"
		 },
		 {
			 "a2","pinVal ProfileElement ::= pinCodes : ","a2,pinVal ProfileElement ::= pinCodes : ,construct","a2a0,pin_Header,construct","a2a080,mandated,null","a2a081,identification,UInt8",
			 "a2a1,pinconfig :,construct","a2a1a0,pinCodes,construct","a2a1a030,Pin Code,construct","a2a1a03080,keyReference,pinRef","a2a1a03081,pinValue,OCTETString","a2a1a03082,unblockingPINReference,pukRef",
			 "a2a1a03083,pinAttributes,UInt8","a2a1a03084,maxNumOfAttemps_retryNumLeft,UInt8","a2a181,filePath,OCTETString"
		 },
		 {
			"a3","pukVal ProfileElement ::= pukCodes :","a3,pukVal ProfileElement ::= pukCodes :,construct","a3a0,puk-Header,construct", "a3a080,mandated,null","a3a081,identification,UInt8",
			"a3a1,pukCodes,construct","a3a130,Puk Code,construct","a3a13080,keyReference,pukRef","a3a13081,pukValue,OCTETString","a3a13082,maxNumOfAttemps_retryNumLeft,UInt8"
		 },
		 {
			 "a1","ProfileElement ::= genericFileManagement :","a1,ProfileElement ::= genericFileManagement :,construct",
			 "a1a0,gfm-header,construct","a1a080,mandated,null","a1a081,identification,UInt8",
			 "a1a1,fileManagementCMD,construct","a1a130,File,construct","a1a13080,filePath,OCTETString","a1a13081,fillFileContent,OCTETString","a1a13002,fillFileOffset,UInt16",
			 "a1a13062,createFCP,construct","a1a13062,createFCP :,construct","a1a1306282,fileDescriptor,OCTETString","a1a1306283,fileID,OCTETString","a1a1306284,dfName,OCTETString","a1a13062c6,pinStatusTemplateDO,OCTETString","a1a130628b,securityAttributesReferenced,OCTETString","a1a1306280,efFileSize,OCTETString",
			 "a1a1306288,shortEFID,OCTETString","a1a13062c7,linkPath,OCTETString",
			 "a1a13062a5,proprietaryEFInfo,construct","a1a13062a5c0,specialFileInformation,OCTETString","a1a13062a5c1,fillPattern,OCTETString","a1a13062a5c2,repeatPattern,OCTETString",
		 },
		 {
			 "a4","akaMilenage ProfileElement ::= akaParameter :","a4,akaMilenage ProfileElement ::= akaParameter :,construct","a4a0,aka-header,construct","a4a080,mandated,null","a4a081,identification,UInt8",
			 "a4a1,AlgoParameter :,construct","a4a1a1,Parameter :,construct","a4a1a180,algorithmID,OCTETString","a4a1a181,algorithmOptions,OCTETString","a4a1a182,key,OCTETString","a4a1a182,key,OCTETString","a4a1a183,opc,OCTETString","a4a1a186,authCounterMax,OCTETString",
			 "a482,sqnOptions,OCTETString","a483,sqnDelta,OCTETString","a484,sqnAgeLimit,OCTETString",
		 },
		 {   
			 "a6","ssdValue ProfileElement ::= securityDomain :","a6,ssdValue ProfileElement ::= securityDomain :,construct","a6a0,sd-Header,construct","a6a080,mandated,null","a6a081,identification,UInt8",
			 "a6a1,instance,construct","a6a14f,AID,OCTETString","a6a182,applicationPrivileges,OCTETString","a6a183,lifeCycleState,OCTETString","a6a1c9,applicationSpecificParametersC9,OCTETString",
			 "a6a1ea,applicationParameters,construct","a6a1ea80,uiccToolkitApplicationSpecificParametersField,OCTETString",
			 "a6a2,keyList,construct","a6a230,SDKey,construct","a6a23095,keyUsageQualifier,OCTETString","a6a23081,keyAccess,OCTETString","a6a23082,keyIdentifier,OCTETString","a6a23083,keyVersionNumber,OCTETString",
			 "a6a23030,keyCompontents,construct","a6a2303030,keyComponent,construct","a6a230303080,keyType,OCTETString","a6a230303086,keyData,OCTETString",
			 "a6a3,sdPersoData,construct","a6a304,PersoData,OCTETString"
		 },
		 {
			 "a8","ProfileElement ::= application :","a8,ProfileElement ::= application :,construct","a8a0,app-Header,construct","a8a080,mandated,null","a8a081,identification,UInt8", 
			 "a8a1,loadBlock,construct","a8a14f,AID,OCTETString","a8a1c4,loadBlockObject,OCTETString", "a8a1c6,nonVolatileCodeLimitC6,OCTETString", "a8a1c1,hashValue,OCTETString",
			 "a8a2,instanceList,construct", "a8a230,Application Instance,construct","a8a2304f,AID,OCTETString","a8a23082,applicationPrivileges,OCTETString","a8a23083,lifeCycleState,OCTETString",
			 "a8a230c9,applicationSpecificParametersC9,OCTETString",  "a8a230ef,systemSpecificParameters,construct","a8a230ea,applicationParameters,construct",
			 "a8a230efc7,volatileMemoryQuotaC7,OCTETString", "a8a230efc8,nonvolatileMemoryQuotaC8,OCTETString","a8a230efca,ts102226SIMFileAccessToolkitParameter,OCTETString",
			 "a8a230ea80,uiccToolkitApplicationSpecificParametersField,OCTETString","a8a230ea81,uiccAccessApplicationSpecificParametersField,OCTETString","a8a230ea82,uiccAdministrativeAccessApplicationSpecificParametersField,OCTETString",
			 "a8a23030,processData,construct","a8a2303004,processDataItem,OCTETString"
		 },
		 
		 {
			 "a7","ProfileElement ::= rfm :","a7,ProfileElement ::= rfm :,construct","a7a0,rfm-header,construct","a7a080,mandated,null","a7a081,identification,UInt8",
			 "a74f,instanceAID,OCTETString","a7a0,tarList,construct","a7a004,RFMTar,OCTETString","a781,minimumSecurityLevel,OCTETString","a704,uiccAccessDomain,OCTETString",
			 "a730,adfRFMAccess,construct","a73080,adfAID,OCTETString","a73081,adfAccessDomain,OCTETString","a73082,adfAdminAccessDomain,OCTETString"
		 },
		 {
			 "aa", "endVal ProfileElement ::= end :","aa,endVal ProfileElement ::= end :,construct","aaa0,end-header,construct","aaa080,mandated,null","aaa081,identification,UInt8"
		 }
		 
	};
	public static String sSeperator = "mainTag : ";
	public static String sASNHexLenFlag  = "length";
	public static String outFile = "";
	public static String asnFile = "";
	public  static String asnHexFile = "";
	public static String asnHexFile2 = "";
	public static PrintWriter out = null;
	public static PrintWriter asn = null;
	public static PrintWriter asnHex= null;
	public static PrintWriter asnHex2  = null;
	public static String sMainTag = "";
	public static byte bPEType = 0;
	public static int[] iaLen = {0,0,0,0,0,0,0};

}
