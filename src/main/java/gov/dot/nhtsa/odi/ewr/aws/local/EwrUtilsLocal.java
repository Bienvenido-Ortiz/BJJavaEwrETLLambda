package gov.dot.nhtsa.odi.ewr.aws.local;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import gov.dot.nhtsa.odi.ewr.aws.data.AggregateData;
import gov.dot.nhtsa.odi.ewr.aws.data.AggregateProduction;
import gov.dot.nhtsa.odi.ewr.aws.data.AggregateTireCommonGreen;
import gov.dot.nhtsa.odi.ewr.aws.data.AggregateTireProdOEVehApp;
import gov.dot.nhtsa.odi.ewr.aws.data.AggregateTireProdSkuTypeCode;
import gov.dot.nhtsa.odi.ewr.aws.data.AggregateTireProduction;
import gov.dot.nhtsa.odi.ewr.aws.data.AggregateTirePropertyDamageAndWarrantyAdjustments;
import gov.dot.nhtsa.odi.ewr.aws.data.DeathInjuryEquipment;
import gov.dot.nhtsa.odi.ewr.aws.data.DeathInjuryTire;
import gov.dot.nhtsa.odi.ewr.aws.data.DiRecord;
import gov.dot.nhtsa.odi.ewr.aws.data.ReportInfo;
import gov.dot.nhtsa.odi.ewr.aws.data.SimilarVehicles;
import gov.dot.nhtsa.odi.ewr.aws.utils.EwrConstants;

/*
 * @author Bienvenido Ortiz
 */
public class EwrUtilsLocal {

	
	/*
	 * @author Bienvenido Ortiz
	 * Method that maps only the Report Info tab. This tab is constant in all reports. 
	 * We are reusing this method for all reports.
	 */
	private static ReportInfo mapReportInfo(Element reportInfoElement, String filename) {
		//CREATING THE REPPORT INFO OBJECT AND POPULATING IT WITH THE DATA FROM THE XML
		ReportInfo reportInfo = new ReportInfo();
		
		//ALL THESE REPORT INFO VALUES ARE CONSTANT ON ALL REPORTS.
		String reportName = reportInfoElement.getChildTextTrim("ReportName");
		reportInfo.setFileName(filename);
		reportInfo.setEwrId(Integer.valueOf(reportInfo.getFileName().substring(0, 6)));
		reportInfo.setManufacturerName(reportInfoElement.getChildText("ManufacturerName"));
		reportInfo.setReportQuarter(Integer.parseInt(reportInfoElement.getChildText("ReportQuarter")));
		reportInfo.setReportYear(Integer.parseInt(reportInfoElement.getChildText("ReportYear")));
		reportInfo.setReportName(reportName);
		reportInfo.setReportVersion(reportInfoElement.getChildText("ReportVersion"));
		reportInfo.setReportGeneratedDate(reportInfoElement.getChildText("ReportGeneratedDate"));
		reportInfo.setReportContactName(reportInfoElement.getChildText("ReportContactName"));
		reportInfo.setReportContactEmail(reportInfoElement.getChildText("ReportContactEmail"));
		reportInfo.setReportContactPhone(reportInfoElement.getChildText("ReportContactPhone"));
		reportInfo.setNhtsaTemplateRevisionNo(reportInfoElement.getChildText("NHTSATemplateRevisionNo"));
		
		return reportInfo;
	}
	
	/*
	 * @author Bienvenido Ortiz
	 * Method that maps the Aggregate Data from the XML on the following report names:
	 * 
	 * LightVehicles 
	 * BusesAndMediumHeavyVehicles
	 * Trailers
	 * MotorCycles 
	 * 
	 * ChildRestraints
	 
	 	 	LightVehicles
		*************
		 <Production>
	 		<PrLVehicle>
	 		</PrLVehicle>
	 		...
	 	 </Production>	

	 	BusesAndMediumHeavyVehicles
	 	***************************
	 	For this type of report please replace L in the inner child by BMH
	 	Ex:
		<Production>
	 		<PrBMHVehicle>
	 		</PrBMHVehicle>
	 		...
	 	 </Production>
	 
	 	Trailers
	 	********
	 	This one looks like this:
		<Production>
	 		<PrTrailer>
	 		</PrTrailer>
	 		...
	 	 </Production>
    	
    	MotorCycles
    	***********
    	Motorcycles look like this:
    	<Production>
    		<PrMCycle>
        	</PrMCycle>
        </Production>
    	
    	
    	ChildRestraints
    	***************
    	<Production>
			<PrRestraint>
		 	</PrRestraint>
		</Production>
	 * 
	 */
	public static ReportInfo mapReportInfoAggregateXMLData(InputStream file,  String filename) {
		System.out.println("Entering EwrUtils.mapReportInfoAggregateXMLData()");
		SAXBuilder saxBuilder = new SAXBuilder();
		org.jdom2.Document jdomDoc;
		try {
			jdomDoc = saxBuilder.build(file);

			Element root = jdomDoc.getRootElement();
			Element reportInfoElement = root.getChild("ReportInfo");
			//CREATING THE REPORT INFO OBJECT AND POPULATING IT WITH THE DATA FROM THE XML
			ReportInfo reportInfo = mapReportInfo(reportInfoElement, filename);
			String reportName = reportInfo.getReportName();
					
			List<Element> productionElements = null;
			Element productionRootElement = root.getChild("Production");//Pr
			
			List<Element> consumerComplaintsElements = null;
			Element consumerComplaintsRootElement = root.getChild("ConsumerComplaints");//Cc
			
			List<Element> propertyDamageElements = null;
			Element propertyDamageRootElement = root.getChild("PropertyDamage");//Pd
			
			List<Element> warrantyClaimsElements = null;
			Element warrantyClaimsRootElement = root.getChild("WarrantyClaims");//Wr
			
			List<Element> fieldReportsElements = null; //For Child Restraint
			Element fieldReportsRootElement = root.getChild("FieldReports");//Fr
				
			//Child Restraint
			List<Element> complaintsWarrantyClaimsElements = null;
			Element complaintsWarrantyClaimsRootElement = root.getChild("ComplaintsWarrantyClaims");//Cc	
			
			/* THIS METHOD SHOULD ALSO MAP THE DATA IN ALL THE TABS ON THE EXCEL OR TAGS ON THE XML **/
			if(reportName.equalsIgnoreCase("LightVehicles")) {
				productionElements = productionRootElement.getChildren("PrLVehicle");
				consumerComplaintsElements = consumerComplaintsRootElement.getChildren("CcLVehicle");
				propertyDamageElements = propertyDamageRootElement.getChildren("PdLVehicle");
				warrantyClaimsElements = warrantyClaimsRootElement.getChildren("WcLVehicle");
				fieldReportsElements = fieldReportsRootElement.getChildren("FrLVehicle");
				
				//First get the <Production> elements  
				reportInfo.setAggregateProduction(getAggregateProductionFromXML(reportInfo, reportName, productionElements));
				
				//Continue with the other elements: consumerComplaintsElements, propertyDamageElements, warrantyClaimsElements, fieldReportsElements
				//Use AggregateData object
				reportInfo.setAggregateDataConsumerComplaints(getAggregateDataFromXML(reportInfo,EwrConstants.AGGREGATE_TYPE_CD_FOR_CONSUMER_COMPLAINTS, EwrConstants.LIGHT_VEHICLES_REPORT_CATEGORY, consumerComplaintsElements));
				reportInfo.setAggregateDataPropertyDamage(getAggregateDataFromXML(reportInfo, EwrConstants.AGGREGATE_TYPE_CD_FOR_PROPERTY_DAMAGE, EwrConstants.LIGHT_VEHICLES_REPORT_CATEGORY, propertyDamageElements));
				reportInfo.setAggregateDataWarrantyClaims(getAggregateDataFromXML(reportInfo, EwrConstants.AGGREGATE_TYPE_CD_FOR_WARRANTY_CLAIMS, EwrConstants.LIGHT_VEHICLES_REPORT_CATEGORY, warrantyClaimsElements));
				reportInfo.setAggregateDataFieldReports(getAggregateDataFromXML(reportInfo, EwrConstants.AGGREGATE_TYPE_CD_FOR_FIELD_REPORTS, EwrConstants.LIGHT_VEHICLES_REPORT_CATEGORY, fieldReportsElements));
				
			}//Light Vehicles End
			
			if(reportName.equalsIgnoreCase("BusesAndMediumHeavyVehicles")) {
				productionElements = productionRootElement.getChildren("PrBMHVehicle");
				consumerComplaintsElements = consumerComplaintsRootElement.getChildren("CcBMHVehicle");
				propertyDamageElements = propertyDamageRootElement.getChildren("PdBMHVehicle");
				warrantyClaimsElements = warrantyClaimsRootElement.getChildren("WcBMHVehicle");
				fieldReportsElements = fieldReportsRootElement.getChildren("FrBMHVehicle");
				
				reportInfo.setAggregateProduction(getAggregateProductionFromXML(reportInfo, reportName, productionElements));
				
				//Continue with the other elements: consumerComplaintsElements, propertyDamageElements, warrantyClaimsElements, fieldReportsElements
				//Use AggregateData object
				reportInfo.setAggregateDataConsumerComplaints(getAggregateDataFromXML(reportInfo,EwrConstants.AGGREGATE_TYPE_CD_FOR_CONSUMER_COMPLAINTS, EwrConstants.BUSES_AND_MEDIUM_HEAVY_VEHICLES_REPORT_CATEGORY, consumerComplaintsElements));
				reportInfo.setAggregateDataPropertyDamage(getAggregateDataFromXML(reportInfo, EwrConstants.AGGREGATE_TYPE_CD_FOR_PROPERTY_DAMAGE, EwrConstants.BUSES_AND_MEDIUM_HEAVY_VEHICLES_REPORT_CATEGORY, propertyDamageElements));
				reportInfo.setAggregateDataWarrantyClaims(getAggregateDataFromXML(reportInfo, EwrConstants.AGGREGATE_TYPE_CD_FOR_WARRANTY_CLAIMS, EwrConstants.BUSES_AND_MEDIUM_HEAVY_VEHICLES_REPORT_CATEGORY, warrantyClaimsElements));
				reportInfo.setAggregateDataFieldReports(getAggregateDataFromXML(reportInfo, EwrConstants.AGGREGATE_TYPE_CD_FOR_FIELD_REPORTS, EwrConstants.BUSES_AND_MEDIUM_HEAVY_VEHICLES_REPORT_CATEGORY, fieldReportsElements));
			}
			
			if(reportName.equalsIgnoreCase("Trailers")) {
				productionElements = productionRootElement.getChildren("PrTrailer");
				consumerComplaintsElements = consumerComplaintsRootElement.getChildren("CcTrailer");
				propertyDamageElements = propertyDamageRootElement.getChildren("PdTrailer");
				warrantyClaimsElements = warrantyClaimsRootElement.getChildren("WcTrailer");
				fieldReportsElements = fieldReportsRootElement.getChildren("FrTrailer");
				reportInfo.setAggregateProduction(getAggregateProductionFromXML(reportInfo, reportName, productionElements));
				
				//Continue with the other elements: consumerComplaintsElements, propertyDamageElements, warrantyClaimsElements, fieldReportsElements
				//Use AggregateData object
				reportInfo.setAggregateDataConsumerComplaints(getAggregateDataFromXML(reportInfo,EwrConstants.AGGREGATE_TYPE_CD_FOR_CONSUMER_COMPLAINTS, EwrConstants.TRAILERS_REPORT_CATEGORY, consumerComplaintsElements));
				reportInfo.setAggregateDataPropertyDamage(getAggregateDataFromXML(reportInfo, EwrConstants.AGGREGATE_TYPE_CD_FOR_PROPERTY_DAMAGE, EwrConstants.TRAILERS_REPORT_CATEGORY, propertyDamageElements));
				reportInfo.setAggregateDataWarrantyClaims(getAggregateDataFromXML(reportInfo, EwrConstants.AGGREGATE_TYPE_CD_FOR_WARRANTY_CLAIMS, EwrConstants.TRAILERS_REPORT_CATEGORY, warrantyClaimsElements));
				reportInfo.setAggregateDataFieldReports(getAggregateDataFromXML(reportInfo, EwrConstants.AGGREGATE_TYPE_CD_FOR_FIELD_REPORTS, EwrConstants.TRAILERS_REPORT_CATEGORY, fieldReportsElements));
			}
			
			if(reportName.equalsIgnoreCase("MotorCycles")) {
				productionElements = productionRootElement.getChildren("PrMCycle");
				consumerComplaintsElements = consumerComplaintsRootElement.getChildren("CcMCycle");
				propertyDamageElements = propertyDamageRootElement.getChildren("PdMCycle");
				warrantyClaimsElements = warrantyClaimsRootElement.getChildren("WcMCycle");
				fieldReportsElements = fieldReportsRootElement.getChildren("FrMCycle");
				reportInfo.setAggregateProduction(getAggregateProductionFromXML(reportInfo, reportName, productionElements));
				
				//Continue with the other elements: consumerComplaintsElements, propertyDamageElements, warrantyClaimsElements, fieldReportsElements
				//Use AggregateData object
				reportInfo.setAggregateDataConsumerComplaints(getAggregateDataFromXML(reportInfo,EwrConstants.AGGREGATE_TYPE_CD_FOR_CONSUMER_COMPLAINTS, EwrConstants.MOTORYCLES_REPORT_CATEGORY, consumerComplaintsElements));
				reportInfo.setAggregateDataPropertyDamage(getAggregateDataFromXML(reportInfo, EwrConstants.AGGREGATE_TYPE_CD_FOR_PROPERTY_DAMAGE, EwrConstants.MOTORYCLES_REPORT_CATEGORY, propertyDamageElements));
				reportInfo.setAggregateDataWarrantyClaims(getAggregateDataFromXML(reportInfo, EwrConstants.AGGREGATE_TYPE_CD_FOR_WARRANTY_CLAIMS, EwrConstants.MOTORYCLES_REPORT_CATEGORY, warrantyClaimsElements));
				reportInfo.setAggregateDataFieldReports(getAggregateDataFromXML(reportInfo, EwrConstants.AGGREGATE_TYPE_CD_FOR_FIELD_REPORTS, EwrConstants.MOTORYCLES_REPORT_CATEGORY, fieldReportsElements));
			}
			/*I NEED TO REVIEW THE CHILD RESTRAINTS METHODS - PLEASE REVIEW THE XML AND EXCEL*/
			if(reportName.equalsIgnoreCase("ChildRestraints")) {
				productionElements = productionRootElement.getChildren("PrRestraint");
				complaintsWarrantyClaimsElements = complaintsWarrantyClaimsRootElement.getChildren("CcRestraint");
				fieldReportsElements = fieldReportsRootElement.getChildren("FrRestraint");
				
				reportInfo.setAggregateProduction(getAggregateProductionFromXML(reportInfo, reportName, productionElements));
				
				//ComplaintsWarrantyClaims
				reportInfo.setAggregateDataConsumerComplaints(getAggregateDataFromXMLForChildRestraint(reportInfo,EwrConstants.AGGREGATE_TYPE_CD_FOR_CONSUMER_COMPLAINTS, EwrConstants.CHILD_RESTRAINTS_REPORT_CATEGORY, complaintsWarrantyClaimsElements));
				//FieldReports
				reportInfo.setAggregateDataFieldReports(getAggregateDataFromXMLForChildRestraint(reportInfo, EwrConstants.AGGREGATE_TYPE_CD_FOR_FIELD_REPORTS, EwrConstants.CHILD_RESTRAINTS_REPORT_CATEGORY, fieldReportsElements));
				
			}
						
			return reportInfo;

		} catch (Exception e) {
			System.out.println("EXCEPTION IN mapReportInfoAggregateXMLData() " + e.getMessage());
			return null;
		}
	}
	
	
	public static ReportInfo mapReportInfoAggregateXMLDataForTires(InputStream file,  String filename) {
		System.out.println("Entering EwrUtils.mapReportInfoAggregateXMLDataForTires()");
		SAXBuilder saxBuilder = new SAXBuilder();
		org.jdom2.Document jdomDoc;
		try {
			jdomDoc = saxBuilder.build(file);

			Element root = jdomDoc.getRootElement();
			Element reportInfoElement = root.getChild("ReportInfo");
			//CREATING THE REPORT INFO OBJECT AND POPULATING IT WITH THE DATA FROM THE XML
			ReportInfo reportInfo = mapReportInfo(reportInfoElement, filename);
			String reportName = reportInfo.getReportName();
					
			List<Element> productionElements = null;
			Element productionRootElement = root.getChild("Production");//Pr
			
			List<Element> prodSkuTypeCodeElements = null;
			Element prodSkuTypeCodeRootElement = root.getChild("ProdSKUTypeCode");//Pst
			
			List<Element> prodOEVehAppElements = null;
			Element prodOEVehAppRootElement = root.getChild("ProdOEVehApp");//Pov
			
			List<Element> propertyDamageElements = null;
			Element propertyDamageRootElement = root.getChild("PropertyDamage");//Pd

			List<Element> warrantyAdjustmentsElements = null;
			Element warrantyAdjustmentsRootElement = root.getChild("WarrantyAdjustments");//Wa
			
			List<Element> commonGreenElements = null;
			Element commonGreenRootElement = root.getChild("CommonGreen");//Cg
			
				
			productionElements = productionRootElement.getChildren("PrTire");
			prodSkuTypeCodeElements = prodSkuTypeCodeRootElement.getChildren("PstTire");
			prodOEVehAppElements = prodOEVehAppRootElement.getChildren("PovTire");
			propertyDamageElements = propertyDamageRootElement.getChildren("PdTire");
			warrantyAdjustmentsElements = warrantyAdjustmentsRootElement.getChildren("WaTire");
			commonGreenElements = commonGreenRootElement.getChildren("CgTire");
			

			//First get the <Production> elements  
			reportInfo.setAggregateTireProduction(getAggregateProductionFromXMLForTires(reportInfo, reportName, productionElements));
			
			/* 
			 * I NEED TO CREATE A METHOD FOR EACH ONE OF THESE 
			 * I CAN USE ONE METHOD FOR PROERTY DAMAGE AND WARRANTY ADJUSTMENTS SINCE THE DATA IS THE SAME AND GO TO THE SAME TABLE 
			 * 
			 */
			reportInfo.setAggregateTireProdSkuTypeCode(getAggregateTireProdSkuTypeCodeDataFromXML(reportInfo, prodSkuTypeCodeElements));
			reportInfo.setAggregateTireProdOEVehApp(getAggregateTireProdOEVehAppDataFromXML(reportInfo, prodOEVehAppElements));
			reportInfo.setAggregateTirePropertyDamage(getAggregateTirePropertyDamageAndWarrantyAdjustmentsDataFromXML(reportInfo, EwrConstants.TIRES_AGGREGATE_TYPE_CD_FOR_PROPERTY_DAMAGE, propertyDamageElements));
			reportInfo.setAggregateTireWarrantyAdjustments(getAggregateTirePropertyDamageAndWarrantyAdjustmentsDataFromXML(reportInfo, EwrConstants.TIRES_AGGREGATE_TYPE_CD_FOR_WARRANTY_ADJUSTMENTS, warrantyAdjustmentsElements));
			//Please find a tire Excel or XML with PlantCode on the CommonGreen tab, I haven't seen it and it is on the DB table
			//need to verify the correct spelling. I am using PlantCode - see method below.
			reportInfo.setAggregateTireCommonGreen(getAggregateTireCommonGreenDataFromXML(reportInfo, commonGreenElements));

			return reportInfo;

		} catch (Exception e) {
			System.out.println("EXCEPTION IN mapReportInfoAggregateXMLData() " + e.getMessage());
			return null;
		}
	}	

	
	/*
	 * @author Bienvenido Ortiz Method that parses through a list of XML elements
	 * data, extract each element and create a list of AggregateTireCommonGreen objects
	 */
	public static List<AggregateTireCommonGreen> getAggregateTireCommonGreenDataFromXML(ReportInfo reportInfo, List<Element> aggregateElements) {
		List<AggregateTireCommonGreen> aggregateList = new ArrayList<AggregateTireCommonGreen>();

		int counter = 0;
		for (Element prodElement : aggregateElements) {

			AggregateTireCommonGreen aggrRecord = new AggregateTireCommonGreen();
			
			aggrRecord.setEwrId(reportInfo.getEwrId());
			aggrRecord.setEwrReportId(reportInfo.getEwrReportId());
			aggrRecord.setRecordId(counter);
						
			aggrRecord.setCommonGreenGroup(prodElement.getChildText("CGreenGroup"));
			aggrRecord.setTireLine(prodElement.getChildText("TireLine"));
			aggrRecord.setCommonGreenTypeCode(prodElement.getChildText("TypeCode"));
			aggrRecord.setSku(prodElement.getChildText("SKU"));
			aggrRecord.setBrandName(prodElement.getChildText("BrandName"));
			aggrRecord.setBrandOwner(prodElement.getChildText("BrandOwner"));
			aggrRecord.setPlantCode(prodElement.getChildText("PlantCode"));//Find Excel with this column 
			
			aggrRecord.setFileName(reportInfo.getFileName());

			aggregateList.add(aggrRecord);
			counter++;
		}		
		return aggregateList;
	}
	
	
	/*
	 * @author Bienvenido Ortiz Method that parses through a list of XML elements
	 * data, extract each element and create a list of AggregateTirePropertyDamageAndWarrantyAdjustments objects.
	 * This is used for PropertyDamaga and WarrantyAdjustments tabs.
	 */
	public static List<AggregateTirePropertyDamageAndWarrantyAdjustments> getAggregateTirePropertyDamageAndWarrantyAdjustmentsDataFromXML(ReportInfo reportInfo, String aggregateTypeCode, List<Element> aggregateElements) {
		List<AggregateTirePropertyDamageAndWarrantyAdjustments> aggregateList = new ArrayList<AggregateTirePropertyDamageAndWarrantyAdjustments>();

		int counter = 0;
		for (Element prodElement : aggregateElements) {

			AggregateTirePropertyDamageAndWarrantyAdjustments aggrRecord = new AggregateTirePropertyDamageAndWarrantyAdjustments();
			
			aggrRecord.setEwrId(reportInfo.getEwrId());
			aggrRecord.setEwrReportId(reportInfo.getEwrReportId());
			aggrRecord.setRecordId(counter);
			aggrRecord.setAggregateTypeCode(aggregateTypeCode);
			aggrRecord.setTireLine(prodElement.getChildText("TireLine"));
			aggrRecord.setTireSize(prodElement.getChildText("TireSize"));
			aggrRecord.setSku(prodElement.getChildText("SKU"));
			aggrRecord.setPlantCode(prodElement.getChildText("PlantCode"));
			aggrRecord.setProductionYear(prodElement.getChildText("ProdYear"));
			aggrRecord.setTreat71(Integer.valueOf(StringUtils.defaultIfBlank(prodElement.getChildText("Tread-71"), "0")));
			aggrRecord.setSidewall72(Integer.valueOf(StringUtils.defaultIfBlank(prodElement.getChildText("SideWall-72"), "0")));
			aggrRecord.setBead73(Integer.valueOf(StringUtils.defaultIfBlank(prodElement.getChildText("Bead-73"), "0")));
			aggrRecord.setOther98(Integer.valueOf(StringUtils.defaultIfBlank(prodElement.getChildText("Other-98"), "0")));
			aggrRecord.setFileName(reportInfo.getFileName());

			aggregateList.add(aggrRecord);
			counter++;
		}		
		return aggregateList;
	}
	
	/*
	 * @author Bienvenido Ortiz Method that parses through a list of XML elements
	 * data, extract each element and create a list of AggregateTireProdOEVehApp objects
	 */
	public static List<AggregateTireProdOEVehApp> getAggregateTireProdOEVehAppDataFromXML(ReportInfo reportInfo, List<Element> aggregateElements) {
		List<AggregateTireProdOEVehApp> aggregateList = new ArrayList<AggregateTireProdOEVehApp>();

		int counter = 0;
		for (Element prodElement : aggregateElements) {

			AggregateTireProdOEVehApp aggrRecord = new AggregateTireProdOEVehApp();
			
			aggrRecord.setEwrId(reportInfo.getEwrId());
			aggrRecord.setEwrReportId(reportInfo.getEwrReportId());
			aggrRecord.setRecordId(counter);
			aggrRecord.setSku(prodElement.getChildText("SKU"));
			aggrRecord.setMake(prodElement.getChildText("VehicleMake"));
			aggrRecord.setModel(prodElement.getChildText("VehicleModel"));
			aggrRecord.setModelYear(prodElement.getChildText("VehicleModelYear"));
			aggrRecord.setFileName(reportInfo.getFileName());

			aggregateList.add(aggrRecord);
			counter++;
		}		
		return aggregateList;
	}
	
	
	/*
	 * @author Bienvenido Ortiz Method that parses through a list of XML elements
	 * data, extract each element and create a list of AggregateTireProdSkuTypeCode objects
	 */
	public static List<AggregateTireProdSkuTypeCode> getAggregateTireProdSkuTypeCodeDataFromXML(ReportInfo reportInfo, List<Element> aggregateElements) {
		List<AggregateTireProdSkuTypeCode> aggregateList = new ArrayList<AggregateTireProdSkuTypeCode>();

		int counter = 0;
		for (Element prodElement : aggregateElements) {

			AggregateTireProdSkuTypeCode aggrRecord = new AggregateTireProdSkuTypeCode();
			
			aggrRecord.setEwrId(reportInfo.getEwrId());
			aggrRecord.setEwrReportId(reportInfo.getEwrReportId());
			aggrRecord.setRecordId(counter);
			aggrRecord.setSku(prodElement.getChildText("SKU"));
			aggrRecord.setTypeCode(prodElement.getChildText("TypeCode"));
			aggrRecord.setFileName(reportInfo.getFileName());

			aggregateList.add(aggrRecord);
			counter++;
		}		
		return aggregateList;
	}
	
	/*
	 * @author Bienvenido Ortiz
	 * Method that maps the data from the XML for Death and Injury on the following report names:
	 * 
	 * SubstantiallySimilarVehicles --> Substantially Similar Vehicles
	 * 
	 * DESC PRODUCTS_STG_FOREIGN;
	 Name                                      Null?    Type
	 ----------------------------------------- -------- ----------------------------
	
	 EWR_ID                                    NOT NULL NUMBER(6)
	 EWR_REPORT_ID                             NOT NULL NUMBER(9)
	 RECORD_ID                                 NOT NULL NUMBER(9)
	 FOREIGN_MAKE                              NOT NULL VARCHAR2(25)
	 FOREIGN_MODEL                             NOT NULL VARCHAR2(25)
	 FOREIGN_MODEL_YR                          NOT NULL VARCHAR2(4)
	 MAKE                                      NOT NULL VARCHAR2(25)
	 MODEL                                     NOT NULL VARCHAR2(25)
	 MODEL_YR                                  NOT NULL VARCHAR2(4)
	 FOREIGN_MARKETS                                    VARCHAR2(2048)
	 FOREIGN_MFR                                        VARCHAR2(40)
	 FR_PROD_ID                                         NUMBER(9) 
	 * 
	 */
	public static ReportInfo mapReportInfoSimilarVehiclesXMLData(InputStream file,  String filename) {
		System.out.println("Entering EwrUtils.mapReportInfoSimilarVehiclesXMLData()");
		SAXBuilder saxBuilder = new SAXBuilder();
		org.jdom2.Document jdomDoc;
		try {
			jdomDoc = saxBuilder.build(file);

			Element root = jdomDoc.getRootElement();
			Element reportInfoElement = root.getChild("ReportInfo");
			//CREATING THE REPORT INFO OBJECT AND POPULATING IT WITH THE DATA FROM THE XML
			ReportInfo reportInfo = mapReportInfo(reportInfoElement, filename);
			String reportName = reportInfo.getReportName();
					
			List<Element> similarVehiclesElements = null;
			Element deathsInjuriesElement = root.getChild("SimilarVehicles");
			
			if(reportName.equalsIgnoreCase("SubstantiallySimilarVehicles")) {
				similarVehiclesElements = deathsInjuriesElement.getChildren("SsVehicle");
			}
			
			List<SimilarVehicles> ssVehicleList = new ArrayList<SimilarVehicles>();
			int counter = 0;
			if(ssVehicleList != null) {
				for (Element diElement : similarVehiclesElements) {
	
					SimilarVehicles substantiallySimilarVehicleRecord = new SimilarVehicles();
					substantiallySimilarVehicleRecord.setEwrRecordId(counter);
					substantiallySimilarVehicleRecord.setMake(diElement.getChildText("Make"));
					substantiallySimilarVehicleRecord.setModel(diElement.getChildText("Model"));
					substantiallySimilarVehicleRecord.setModelYear(diElement.getChildText("ModelYear"));
					substantiallySimilarVehicleRecord.setForeignManufacturer(diElement.getChildText("ForeignManufacturer"));
					substantiallySimilarVehicleRecord.setForeignMake(diElement.getChildText("ForeignMake"));
					substantiallySimilarVehicleRecord.setForeignModel(diElement.getChildText("ForeignModel"));
					substantiallySimilarVehicleRecord.setForeignModelYear(diElement.getChildText("ForeignModelYear"));
					substantiallySimilarVehicleRecord.setForeignMarkets(diElement.getChildText("ForeignMarkets"));
	//				substantiallySimilarVehicleRecord.setForeignProductId(counter); THIS WOULD BE SET AT THE RDSUtils CLASS WHILE PERSISTING
					substantiallySimilarVehicleRecord.setFileName(reportInfo.getFileName());
					
					ssVehicleList.add(substantiallySimilarVehicleRecord);
					counter++;
				}
			}
			reportInfo.setSimilarVehicles(ssVehicleList);
			
			return reportInfo;

		} catch (Exception e) {
			System.out.println("EXCEPTION IN mapReportInfoSimilarVehiclesXMLData() " + e.getMessage());
			return null;
		}
	}
	
	/*
	 * @author Bienvenido Ortiz
	 * Method that maps the data from the XML for Death and Injury on the following report names:
	 * 
	 * EquipmentDI --> Death and Injury for Equipments
	 * 
	 */
	public static ReportInfo mapReportInfoDeathAndInjuryXMLDataForEquipments(InputStream file,  String filename) {
		System.out.println("Entering EwrUtils.mapReportInfoDeathAndInjuryXMLDataForEquipments()");
		SAXBuilder saxBuilder = new SAXBuilder();
		org.jdom2.Document jdomDoc;
		try {
			jdomDoc = saxBuilder.build(file);

			Element root = jdomDoc.getRootElement();
			Element reportInfoElement = root.getChild("ReportInfo");
			//CREATING THE REPORT INFO OBJECT AND POPULATING IT WITH THE DATA FROM THE XML
			ReportInfo reportInfo = mapReportInfo(reportInfoElement, filename);
			String reportName = reportInfo.getReportName();
					
			List<Element> diEquipmentListElements = null;
			Element deathsInjuriesElement = root.getChild("DeathsInjuries");
			
			if(reportName.equalsIgnoreCase("EquipmentDI")) {//No need to check for this, but doing it just in case
				diEquipmentListElements = deathsInjuriesElement.getChildren("DiEquip");
			}
			
			List<DeathInjuryEquipment> diEquipmentList = new ArrayList<DeathInjuryEquipment>();
			if(diEquipmentListElements != null) {
				for (Element diElement : diEquipmentListElements) {
	
					DeathInjuryEquipment diEquipmentRecord = new DeathInjuryEquipment();
					if (! diElement.getChildText("SeqID").isEmpty()) {
						diEquipmentRecord.setSeqId(Integer.parseInt(diElement.getChildText("SeqID")));
					}
					diEquipmentRecord.setEwrId(reportInfo.getEwrId());
					diEquipmentRecord.setEwrReportId(reportInfo.getEwrReportId());
					diEquipmentRecord.setManUniqueId(diElement.getChildText("ManUniqueID"));
					diEquipmentRecord.setMake(diElement.getChildText("Make"));
					diEquipmentRecord.setModel(diElement.getChildText("Model"));
					diEquipmentRecord.setModelYear(diElement.getChildText("ProdYear"));// Maps to ProdYear on the XML/XSL
					diEquipmentRecord.setProductIdentifier(diElement.getChildText("ProductIdentifier"));
					diEquipmentRecord.setIncidentDate(diElement.getChildText("IncidentDate"));
					if (! diElement.getChildText("NumDeaths").isEmpty()) {
						diEquipmentRecord.setNumDeaths(Integer.parseInt(diElement.getChildText("NumDeaths")));
					}
					if (! diElement.getChildText("NumInjuries").isEmpty()) {
						diEquipmentRecord.setNumInjuries(Integer.parseInt(diElement.getChildText("NumInjuries")));
					}
					diEquipmentRecord.setStateOrFCntry(diElement.getChildText("StateOrFCntry"));
					String compDefaultVal = "00";
					if (! diElement.getChildText("SysOrCompA").isEmpty()) {
						diEquipmentRecord.setSysOrCompA(StringUtils.leftPad(diElement.getChildText("SysOrCompA"), 2, "0"));
					} else {
						diEquipmentRecord.setSysOrCompA(compDefaultVal);
					}
					if (! diElement.getChildText("SysOrCompB").isEmpty()) {
						diEquipmentRecord.setSysOrCompB(StringUtils.leftPad(diElement.getChildText("SysOrCompB"), 2, "0"));
					} else {
						diEquipmentRecord.setSysOrCompB(compDefaultVal);
					}
					if (! diElement.getChildText("SysOrCompC").isEmpty()) {
						diEquipmentRecord.setSysOrCompC(StringUtils.leftPad(diElement.getChildText("SysOrCompC"), 2, "0"));
					} else {
						diEquipmentRecord.setSysOrCompC(compDefaultVal);
					}
					if (! diElement.getChildText("SysOrCompD").isEmpty()) {
						diEquipmentRecord.setSysOrCompD(StringUtils.leftPad(diElement.getChildText("SysOrCompD"), 2, "0"));
					} else {
						diEquipmentRecord.setSysOrCompD(compDefaultVal);
					}
					if (! diElement.getChildText("SysOrCompE").isEmpty()) {
						diEquipmentRecord.setSysOrCompE(StringUtils.leftPad(diElement.getChildText("SysOrCompE"), 2, "0"));
					} else {
						diEquipmentRecord.setSysOrCompE(compDefaultVal);
					}
					
					diEquipmentRecord.setFileName(reportInfo.getFileName());
					diEquipmentList.add(diEquipmentRecord);
				}
			}
			reportInfo.setDeathsInjuriesEquipment(diEquipmentList);
			
			return reportInfo;

		} catch (Exception e) {
			System.out.println("EXCEPTION IN mapReportInfoDeathAndInjuryXMLDataForEquipments() " + e.getMessage());
			return null;
		}
	}
	
	/*
	 * @author Bienvenido Ortiz
	 * Method that maps the data from the XML for Death and Injury on the following report names:
	 * 
	 * TiresDI --> Death and Injury for Tires
	 * 
	 */
	public static ReportInfo mapReportInfoDeathAndInjuryXMLDataForTires(InputStream file,  String filename) {
		System.out.println("Entering EwrUtils.mapReportInfoDeathAndInjuryXMLDataForTires()");
		SAXBuilder saxBuilder = new SAXBuilder();
		org.jdom2.Document jdomDoc;
		try {
			jdomDoc = saxBuilder.build(file);

			Element root = jdomDoc.getRootElement();
			Element reportInfoElement = root.getChild("ReportInfo");
			//CREATING THE REPORT INFO OBJECT AND POPULATING IT WITH THE DATA FROM THE XML
			ReportInfo reportInfo = mapReportInfo(reportInfoElement, filename);
			String reportName = reportInfo.getReportName();
					
			List<Element> diTireListElements = null;
			Element deathsInjuriesElement = root.getChild("DeathsInjuries");
			
			if(reportName.equalsIgnoreCase("TiresDI")) {//No need to check for this, but doing it just in case
				diTireListElements = deathsInjuriesElement.getChildren("DiTire");
			}
			
			List<DeathInjuryTire> diTireList = new ArrayList<DeathInjuryTire>();
			if(diTireList != null) {
			for (Element diElement : diTireListElements) {

				DeathInjuryTire diTireRecord = new DeathInjuryTire();
				if (! diElement.getChildText("SeqID").isEmpty()) {
					diTireRecord.setSeqId(Integer.parseInt(diElement.getChildText("SeqID")));
				}
				diTireRecord.setEwrId(reportInfo.getEwrId());
				diTireRecord.setEwrReportId(reportInfo.getEwrReportId());
				diTireRecord.setManUniqueId(diElement.getChildText("ManUniqueID"));
				diTireRecord.setTireLine(diElement.getChildText("TireLine"));
				diTireRecord.setTireSize(diElement.getChildText("TireSize"));
				diTireRecord.setProdYear(diElement.getChildText("ProdYear"));
				diTireRecord.setTin(diElement.getChildText("TIN"));
				diTireRecord.setIncidentDate(diElement.getChildText("IncidentDate"));
				if (! diElement.getChildText("NumDeaths").isEmpty()) {
					diTireRecord.setNumDeaths(Integer.parseInt(diElement.getChildText("NumDeaths")));
				}
				if (! diElement.getChildText("NumInjuries").isEmpty()) {
					diTireRecord.setNumInjuries(Integer.parseInt(diElement.getChildText("NumInjuries")));
				}
				diTireRecord.setStateOrFCntry(diElement.getChildText("StateOrFCntry"));
				diTireRecord.setVehicleMake(diElement.getChildText("VehicleMake"));
				diTireRecord.setVehicleModel(diElement.getChildText("VehicleModel"));
				diTireRecord.setVehicleModelYear(diElement.getChildText("VehicleModelYear"));
				String compDefaultVal = "00";
				if (! diElement.getChildText("CompA").isEmpty()) {
					diTireRecord.setCompA(StringUtils.leftPad(diElement.getChildText("CompA"), 2, "0"));
				} else {
					diTireRecord.setCompA(compDefaultVal);
				}
				if (! diElement.getChildText("CompB").isEmpty()) {
					diTireRecord.setCompB(StringUtils.leftPad(diElement.getChildText("CompB"), 2, "0"));
				} else {
					diTireRecord.setCompB(compDefaultVal);
				}
				if (! diElement.getChildText("CompC").isEmpty()) {
					diTireRecord.setCompC(StringUtils.leftPad(diElement.getChildText("CompC"), 2, "0"));
				} else {
					diTireRecord.setCompC(compDefaultVal);
				}
				if (! diElement.getChildText("CompD").isEmpty()) {
					diTireRecord.setCompD(StringUtils.leftPad(diElement.getChildText("CompD"), 2, "0"));
				} else {
					diTireRecord.setCompD(compDefaultVal);
				}
				if (! diElement.getChildText("CompE").isEmpty()) {
					diTireRecord.setCompE(StringUtils.leftPad(diElement.getChildText("CompE"), 2, "0"));
				} else {
					diTireRecord.setCompE(compDefaultVal);
				}
				
				diTireRecord.setFileName(reportInfo.getFileName());
				diTireList.add(diTireRecord);
				}
			}
			reportInfo.setDeathsInjuriesTire(diTireList);
			
			return reportInfo;

		} catch (Exception e) {
			System.out.println("EXCEPTION IN mapReportInfoDeathAndInjuryXMLDataForTires() " + e.getMessage());
			return null;
		}
	}


	/*
	 * @author Bienvenido Ortiz
	 * Method that maps the data from the XML for Death and Injury on the following report types:
	 * 
	 * LightVehiclesDI
	 * BusesAndMediumHeavyVehiclesDI 
	 * LowVolumeVehiclesDI
	 * TrailersDI
	 * MotorCyclesDI
	 * ChildRestraintsDI
	 * 
	 * They all share the same data / tags on the XML/XLS files.
	 *  
	 */
	public static ReportInfo mapReportInfoDeathAndInjuryXMLData(InputStream file,  String filename) {
		System.out.println("Entering EwrUtils.mapReportInfoDeathAndInjuryXMLData()");
		SAXBuilder saxBuilder = new SAXBuilder();
		org.jdom2.Document jdomDoc;
		try {
			jdomDoc = saxBuilder.build(file);

			Element root = jdomDoc.getRootElement();
			Element reportInfoElement = root.getChild("ReportInfo");
			
			//CREATING THE REPORT INFO OBJECT AND POPULATING IT WITH THE DATA FROM THE XML
			ReportInfo reportInfo = mapReportInfo(reportInfoElement, filename);
			String reportName = reportInfo.getReportName();
					
			List<Element> diListElements = null;
			Element deathsInjuriesElement = root.getChild("DeathsInjuries");
			
			//I CAN USE THE REPORT NAME OR FILE NAME TO IDENTIFY THE TYPE OF REPORT WE ARE PROCESSING 
			//SUCH THAT I KNOW WHAT XML ELEMENT TO RETRIEVE TO GET THE DEATHS AND INJURIES RECORDS. 
			if(reportName.equalsIgnoreCase("LightVehiclesDI")) {
				diListElements = deathsInjuriesElement.getChildren("DiLVehicle");
			}
			
			if(reportName.equalsIgnoreCase("BusesAndMediumHeavyVehiclesDI")) {
				diListElements = deathsInjuriesElement.getChildren("DiBMHVehicle");
			}
			
			if(reportName.equalsIgnoreCase("LowVolumeVehiclesDI")) {
				diListElements = deathsInjuriesElement.getChildren("DiLVVehicle");
			}
			
			if(reportName.equalsIgnoreCase("TrailersDI")) {
				diListElements = deathsInjuriesElement.getChildren("DiTrailer");
			}
			
			if(reportName.equalsIgnoreCase("MotorCyclesDI")) {
				diListElements = deathsInjuriesElement.getChildren("DiMCycle");
			}
			
			if(reportName.equalsIgnoreCase("ChildRestraintsDI")) {
				diListElements = deathsInjuriesElement.getChildren("DiRestraint");
			}
			
			List<DiRecord> diList = new ArrayList<DiRecord>();
			for (Element diElement : diListElements) {

				DiRecord diRecord = new DiRecord();
				if (! diElement.getChildText("SeqID").isEmpty()) {
					diRecord.setSeqId(Integer.parseInt(diElement.getChildText("SeqID")));
				}
				diRecord.setEwrId(reportInfo.getEwrId());
				diRecord.setEwrReportId(reportInfo.getEwrReportId());
				diRecord.setManUniqueId(diElement.getChildText("ManUniqueID"));
				diRecord.setMake(diElement.getChildText("Make"));
				diRecord.setModel(diElement.getChildText("Model"));
				if(reportName.equalsIgnoreCase("ChildRestraintsDI")) {
					diRecord.setModelYear(diElement.getChildText("ProdYear"));
				}else {
					diRecord.setModelYear(diElement.getChildText("ModelYear"));
				}

				diRecord.setType(diElement.getChildText("Type"));
				diRecord.setFuelPropulsionSystem(diElement.getChildText("FuelPropulsionSystem"));
				diRecord.setVin(diElement.getChildText("VIN"));
				diRecord.setIncidentDate(diElement.getChildText("IncidentDate"));
				diRecord.setFileName(reportInfo.getFileName());
				
				if (! diElement.getChildText("NumDeaths").isEmpty()) {
					diRecord.setNumDeaths(Integer.parseInt(diElement.getChildText("NumDeaths")));
				}
				if (! diElement.getChildText("NumInjuries").isEmpty()) {
					diRecord.setNumInjuries(Integer.parseInt(diElement.getChildText("NumInjuries")));
				}
				String compDefaultVal = "00";
				diRecord.setStateOrFCntry(diElement.getChildText("StateOrFCntry"));
				if (! diElement.getChildText("SysOrCompA").isEmpty()) {
					diRecord.setSysOrCompA(StringUtils.leftPad(diElement.getChildText("SysOrCompA"), 2, "0"));
				} else {
					diRecord.setSysOrCompA(compDefaultVal);
				}
				if (! diElement.getChildText("SysOrCompB").isEmpty()) {
					diRecord.setSysOrCompB(StringUtils.leftPad(diElement.getChildText("SysOrCompB"), 2, "0"));
				} else {
					diRecord.setSysOrCompB(compDefaultVal);
				}
				if (! diElement.getChildText("SysOrCompC").isEmpty()) {
					diRecord.setSysOrCompC(StringUtils.leftPad(diElement.getChildText("SysOrCompC"), 2, "0"));
				} else {
					diRecord.setSysOrCompC(compDefaultVal);
				}
				if (! diElement.getChildText("SysOrCompD").isEmpty()) {
					diRecord.setSysOrCompD(StringUtils.leftPad(diElement.getChildText("SysOrCompD"), 2, "0"));
				} else {
					diRecord.setSysOrCompD(compDefaultVal);
				}
				if (! diElement.getChildText("SysOrCompE").isEmpty()) {
					diRecord.setSysOrCompE(StringUtils.leftPad(diElement.getChildText("SysOrCompE"), 2, "0"));
				} else {
					diRecord.setSysOrCompE(compDefaultVal);
				}

				diList.add(diRecord);
			}
			reportInfo.setDeathsInjuries(diList);
			
			return reportInfo;

		} catch (Exception e) {
			System.out.println("EXCEPTION IN mapReportInfoDeathAndInjuryXMLData() " + e.getMessage());
			return null;
		}
	}

	/*
	 * @author Bienvenido Ortiz Method that parses through a list of XML elements
	 * data, extract each element and create a list of AggregateProduction objects
	 */
	public static List<AggregateProduction> getAggregateProductionFromXML(ReportInfo reportInfo, String reportName, List<Element> productionElements) {
		List<AggregateProduction> aggregateProductionList = new ArrayList<AggregateProduction>();
		
		//Not all the below items would be set for all report types; in the case of child restraint, ModelYear becomes ProdYear, etc. 
		//Can I used the same code below for all? I think ONLY ProdYear is the difference. Ask GREG!!!
		int counter = 0;
		for (Element prodElement : productionElements) {

			AggregateProduction aggrProdRecord = new AggregateProduction();
			aggrProdRecord.setEwrId(reportInfo.getEwrId());
			aggrProdRecord.setEwrReportId(reportInfo.getEwrReportId());
			aggrProdRecord.setRecordId(counter);
			aggrProdRecord.setMake(prodElement.getChildText("Make"));
			aggrProdRecord.setModel(prodElement.getChildText("Model"));
			if(reportName.equalsIgnoreCase("ChildRestraints")) {
				aggrProdRecord.setModelYear(prodElement.getChildText("ProdYear"));
			}else {
				aggrProdRecord.setModelYear(prodElement.getChildText("ModelYear"));
			}
			aggrProdRecord.setFuelSystem(prodElement.getChildText("FuelSystem"));
			aggrProdRecord.setBreakSystem(prodElement.getChildText("BreakSystem"));
			aggrProdRecord.setTypeCode(prodElement.getChildText("Type"));
			aggrProdRecord.setPlatform(prodElement.getChildText("Platform"));
			aggrProdRecord.setTotalProduction(prodElement.getChildText("TotalProduction"));
			aggrProdRecord.setFuelPropulsionSystem(prodElement.getChildText("FuelPropulsionSystem"));
			aggrProdRecord.setFileName(reportInfo.getFileName());

			aggregateProductionList.add(aggrProdRecord);
			counter++;
		}		
		return aggregateProductionList;
	}	
	

	/*
	 * @author Bienvenido Ortiz Method that parses through a list of XML elements
	 * data, extract each element and create a list of AggregateTireProduction objects
	 */
	public static List<AggregateTireProduction> getAggregateProductionFromXMLForTires(ReportInfo reportInfo, String reportName, List<Element> productionElements) {
		List<AggregateTireProduction> aggregateProductionList = new ArrayList<AggregateTireProduction>();

		int counter = 0;
		for (Element prodElement : productionElements) {

			AggregateTireProduction aggrProdRecord = new AggregateTireProduction();
			aggrProdRecord.setEwrId(reportInfo.getEwrId());
			aggrProdRecord.setEwrReportId(reportInfo.getEwrReportId());
			aggrProdRecord.setRecordId(counter);
			aggrProdRecord.setTireLine(prodElement.getChildText("TireLine"));
			aggrProdRecord.setTireSize(prodElement.getChildText("TireSize"));
			aggrProdRecord.setSku(prodElement.getChildText("SKY"));
			aggrProdRecord.setProductionYear(prodElement.getChildText("ProdYear"));
			aggrProdRecord.setPlantCode(prodElement.getChildText("PlantCode"));
			aggrProdRecord.setOriginalEquipYN(prodElement.getChildText("OrigEquip"));
			aggrProdRecord.setWarrantyProduction(prodElement.getChildText("WarrantyProduction"));
			aggrProdRecord.setTotalProduction(prodElement.getChildText("TotalProduction"));
			aggrProdRecord.setFileName(reportInfo.getFileName());

			aggregateProductionList.add(aggrProdRecord);
			counter++;
		}		
		return aggregateProductionList;
	}
	
	/*
	 * @author Bienvenido Ortiz
	 * Method that parses through a list of XML elements data, extract each element and create a list of AggregateData objects
	 */
	public static List<AggregateData> getAggregateDataFromXML(ReportInfo reportInfo, String aggregateTypeCode, String reportCategory, List<Element> aggregateDataElements ){
		System.out.println("Entering  EwrUtils.getAggregateDataFromXML()");
		List<AggregateData> aggregateDataList = new ArrayList<AggregateData>();
		int counter = 0;
		for (Element anAggregateDataElement : aggregateDataElements) {

			AggregateData aggregateRecord = new AggregateData();
			aggregateRecord.setEwrId(reportInfo.getEwrId());
			aggregateRecord.setEwrReportId(reportInfo.getEwrReportId());
			aggregateRecord.setRecordId(counter);
			aggregateRecord.setAggregateTypeCode(aggregateTypeCode);
			
			aggregateRecord.setMake(anAggregateDataElement.getChildText("Make"));
			aggregateRecord.setModel(anAggregateDataElement.getChildText("Model"));
			aggregateRecord.setModelYear(anAggregateDataElement.getChildText("ModelYear"));
			
			aggregateRecord.setSteering01(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("Steering-01"), "0")));
			aggregateRecord.setSuspension02(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("Suspension-02"), "0")));
			
			//H, M and Y reports use: ServiceBrake-03	ServiceBrakeAir-04
			//L reports use: FoundationBrake-03	AutomaticBrake-04
			if(reportCategory.equalsIgnoreCase(EwrConstants.LIGHT_VEHICLES_REPORT_CATEGORY)) {
				aggregateRecord.setServiceBreak03(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("FoundationBrake-03"), "0")));
				aggregateRecord.setServiceBreakAir04(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("AutomaticBrake-04"), "0")));
			}else {
				aggregateRecord.setServiceBreak03(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("ServiceBrake-03"), "0")));
				aggregateRecord.setServiceBreakAir04(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("ServiceBrakeAir-04"), "0")));
			}
			
			aggregateRecord.setParkingBreak05(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("ParkingBrake-05"), "0")));
			aggregateRecord.setEngAndEngCooling06(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("EngAndEngCooling-06"), "0")));
			aggregateRecord.setFuelSystem07(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("FuelSys-07"), "0")));
			aggregateRecord.setFuelSystemDiesel08(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("FuelSysDiesel-08"), "0")));
			aggregateRecord.setFuelSystemOther09(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("FuelSysOther-09"), "0")));
			aggregateRecord.setPowerTrain10(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("PowerTrain-10"), "0")));
			aggregateRecord.setElectrical11(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("Electrical-11"), "0")));
			aggregateRecord.setExtLighting12(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("ExtLighting-12"), "0")));
			aggregateRecord.setVisibility13(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("Visibility-13"), "0")));
			aggregateRecord.setAirbags14(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("AirBags-14"), "0")));
			aggregateRecord.setSeatbelts15(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("SeatBelts-15"), "0")));
			aggregateRecord.setStructure16(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("Structure-16"), "0")));
			aggregateRecord.setLatch17(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("Latch-17"), "0")));
			aggregateRecord.setSpeedControl18(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("SpeedControl-18"), "0")));
			aggregateRecord.setTiresRelated19(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("TiresRelated-19"), "0")));
			aggregateRecord.setWheels20(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("Wheels-20"), "0")));
			if(reportCategory.equalsIgnoreCase(EwrConstants.TRAILERS_REPORT_CATEGORY) || reportCategory.equalsIgnoreCase(EwrConstants.BUSES_AND_MEDIUM_HEAVY_VEHICLES_REPORT_CATEGORY) ) {
				aggregateRecord.setTrailerHitch21(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("TrailerHitch-21"), "0")));
			}
			aggregateRecord.setSeats22(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("Seats-22"), "0")));
			aggregateRecord.setFireRelated23(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("FireRelated-23"), "0")));
			aggregateRecord.setRollover24(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("Rollover-24"), "0")));

			if(reportCategory.equalsIgnoreCase(EwrConstants.BUSES_AND_MEDIUM_HEAVY_VEHICLES_REPORT_CATEGORY)) {
				aggregateRecord.setElectronicStabilityCtrl25(Integer.valueOf(StringUtils.defaultIfBlank(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("ESC_RSC-25"), "0"), "0")));
			}else {
				aggregateRecord.setElectronicStabilityCtrl25(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("ESC-25"), "0")));
			}
			
			aggregateRecord.setForwardCollision26(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("ForwardCollision-26"), "0")));
			aggregateRecord.setLaneDeparture27(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("LaneDeparture-27"), "0")));
			aggregateRecord.setBackoverPrevention28(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("BackoverPrevention-28"), "0")));
			
			//THESE ARE FOR CHILD RESTRAINTS
			//aggregateRecord.setBuckle51(Integer.valueOf(anAggregateDataElement.getChildText("Buckle-51")));
			//aggregateRecord.setSeatshell52(Integer.valueOf(anAggregateDataElement.getChildText("SeatShell-52")));
			//aggregateRecord.setHandle53(Integer.valueOf(anAggregateDataElement.getChildText("Handle-53")));
			//aggregateRecord.setBase54(Integer.valueOf(anAggregateDataElement.getChildText("Base-54")));
			
			aggregateRecord.setTypeCode(anAggregateDataElement.getChildText("Type"));//Please verify this with Greg!!!!!!
			aggregateRecord.setFuelPropulsionSystem(anAggregateDataElement.getChildText("FuelPropulsionSystem"));
			aggregateRecord.setFileName(reportInfo.getFileName());
			
			aggregateDataList.add(aggregateRecord);
			counter++;
		}
			return aggregateDataList;
	}


	/*
	 * @author Bienvenido Ortiz
	 * Method that parses through a list of XML elements data, extract each element and create a list of AggregateData objects
	 */
	public static List<AggregateData> getAggregateDataFromXMLForChildRestraint(ReportInfo reportInfo, String aggregateTypeCode, String reportCategory, List<Element> aggregateDataElements ){
		
		List<AggregateData> aggregateDataList = new ArrayList<AggregateData>();
		int counter = 0;
		for (Element anAggregateDataElement : aggregateDataElements) {

			AggregateData aggregateRecord = new AggregateData();
			aggregateRecord.setEwrId(reportInfo.getEwrId());
			aggregateRecord.setEwrReportId(reportInfo.getEwrReportId());
			aggregateRecord.setRecordId(counter);
			aggregateRecord.setAggregateTypeCode(aggregateTypeCode);
			
			aggregateRecord.setMake(anAggregateDataElement.getChildText("Make"));
			aggregateRecord.setModel(anAggregateDataElement.getChildText("Model"));
			aggregateRecord.setModelYear(anAggregateDataElement.getChildText("ProdYear"));

			//THESE ARE FOR CHILD RESTRAINTS
			aggregateRecord.setBuckle51(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("Buckle-51"), "0")));
			aggregateRecord.setSeatshell52(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("SeatShell-52"), "0")));
			aggregateRecord.setHandle53(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("Handle-53"), "0")));
			aggregateRecord.setBase54(Integer.valueOf(StringUtils.defaultIfBlank(anAggregateDataElement.getChildText("Base-54"), "0")));
			
			aggregateRecord.setFileName(reportInfo.getFileName());
			
			aggregateDataList.add(aggregateRecord);
			counter++;
		}
			return aggregateDataList;
	}
	
	/*
	 * @author Bienvenido Ortiz
	 */
	public static String getReportCategoryCode(String filename) {
	/*
	000003L173001DP.xml	

	000003 	- EWR ID			- first 6 characters
	L		- Report Category	- 7th char
	17		- Report Year		- 8th and 9th characters
	3		- Report Quarter	- 10th 
	001 	- Version			- 11th, 12th and 13th characters
	D		- Death and Injury 	- Report Type - 14th char
	P		- Confidentiality 	- 15th char
	
	*/
		return String.valueOf(filename.charAt(6));//charAt uses zero based indexing
	}
	
	
	/*
	 * @author Bienvenido Ortiz
	 */
	public static String getConfidentialityCode(String filename) {
		return String.valueOf(filename.charAt(14));//charAt uses zero based indexing
	}
	
	
	/*
	 * @author Bienvenido Ortiz
	 */
	public static String getReportTypeCode(String filename) {
		return String.valueOf(filename.charAt(13));//charAt uses zero based indexing
	}
		
}
