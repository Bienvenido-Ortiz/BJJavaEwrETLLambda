package gov.dot.nhtsa.odi.ewr.aws.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.gson.Gson;

import gov.dot.nhtsa.odi.ewr.aws.data.ReportInfo;
/**
 * @author Bienvenido Ortiz
 */
public class XMLDataExtractionLocal  {

	
	public static void main(String [] args) {
	
		XMLDataExtractionLocal app = new XMLDataExtractionLocal();
		app.handleRequest();
		
	}
	
	
	/**
	 * @author Bienvenido Ortiz
	 */
	public String handleRequest(){
		System.out.println("Entering Main function XMLDataExtraction.handleRequest()");
        Gson myGson = new Gson();
        String actionResults = "";

//        String filename ="000092Y144001AC.xml";
//		File xmlFile = new File("C:\\___AWS\\_EWR_DATA\\_AGGREGATE_DATA\\LightVehicles_L_H_Y_M\\"+filename);
//        String filename ="000027C123001AC.xml";
//		File xmlFile = new File("C:\\___AWS\\_EWR_DATA\\_AGGREGATE_DATA\\ChildRestraints\\"+filename);
        
        String filename ="000133M114001AP";
        String extension= ".xml";
        String path = "C:\\BJDevelopmentItems\\EWR_DATA\\AGGREGATE\\VEHICLES_L_H_M_Y\\M\\";
		File xmlFile = new File(path+filename+extension);
		
		
		InputStream xmlInputStream = null;
		try {
			xmlInputStream = new FileInputStream(xmlFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ReportInfo reportInfoObject = new ReportInfo();

		String reportCategory 	= EwrUtilsLocal.getReportCategoryCode(filename);
		String reportType 		= EwrUtilsLocal.getReportTypeCode(filename);
		System.out.println("XMLDataExtraction.handleRequest() - Filename [" + filename + "] -Report Category [" + reportCategory + "] -Report Type [" +reportType +"]");
		/*
		 * <REPORT CATEGORIES>
		 * 1) LIGHT VEHICLES 					= L 
		 * 2) BUSES, MEDIUM AND HEAVY VEHICLES 	= H
		 * 3) LOW VOLUME VEHICLES 				= Z
		 * 4) TRAILERS 							= Y
		 * 5) MOTORCYCLES 						= M
		 * 6) CHILD RESTRAINT					= C
		 * 
		 * 7) TIRES								= T
		 * 8) EQUIPMENT 						= E
		 * 
		 * PLEASE CREATE THREE MORE METHODS, 
		 * A) FOR TIRES, 
		 * B) FOR SUBSTANTIALLY SIMILAR VEHICLES 
		 * C) EQUIPMENTS
		 * 
		*/
		
		/*D = DEATH AND INJURY*/
		if(reportType.equalsIgnoreCase("D")) {
			if(reportCategory.equalsIgnoreCase("L") ||
				reportCategory.equalsIgnoreCase("H") ||
				reportCategory.equalsIgnoreCase("Z") ||
				reportCategory.equalsIgnoreCase("Y")  ||
				reportCategory.equalsIgnoreCase("M") ||
				reportCategory.equalsIgnoreCase("C")) {
			
				reportInfoObject =  EwrUtilsLocal.mapReportInfoDeathAndInjuryXMLData(xmlInputStream,  filename);
			
			}else if(reportCategory.equalsIgnoreCase("T")){
				reportInfoObject =  EwrUtilsLocal.mapReportInfoDeathAndInjuryXMLDataForTires(xmlInputStream,  filename);
			
			}else if(reportCategory.equalsIgnoreCase("E")){
				reportInfoObject =  EwrUtilsLocal.mapReportInfoDeathAndInjuryXMLDataForEquipments(xmlInputStream,  filename);
			
			}
		/*A = AGGREGATE REPORTS*/	
		}else if(reportType.equalsIgnoreCase("A")) {
			
			if(reportCategory.equalsIgnoreCase("L") ||
					reportCategory.equalsIgnoreCase("H") ||
					reportCategory.equalsIgnoreCase("Y") ||
					reportCategory.equalsIgnoreCase("M") ||
					reportCategory.equalsIgnoreCase("C")) {

				reportInfoObject =  EwrUtilsLocal.mapReportInfoAggregateXMLData(xmlInputStream, filename);
				
				
			}else if(reportCategory.equalsIgnoreCase("T")){
				reportInfoObject =  EwrUtilsLocal.mapReportInfoAggregateXMLDataForTires(xmlInputStream, filename);
			}
			
			
		}else {//S = SUBSTANTIALLY SIMILAR VEHICLES
			
			reportInfoObject =  EwrUtilsLocal.mapReportInfoSimilarVehiclesXMLData(xmlInputStream, filename);
		}

		System.out.println(myGson.toJson(reportInfoObject));
	    String data = myGson.toJson(reportInfoObject);
		FileOutputStream out;
		try {
			out = new FileOutputStream(path+filename+".json");
		    out.write(data.getBytes());
		    out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return actionResults;
	}
	
}
