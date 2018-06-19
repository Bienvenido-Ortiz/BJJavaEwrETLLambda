package gov.dot.nhtsa.odi.ewr.aws.lambdas;

import gov.dot.nhtsa.odi.ewr.aws.data.AggregateDataPersistResults;
import gov.dot.nhtsa.odi.ewr.aws.data.AggregateTireDataPersistResults;
import gov.dot.nhtsa.odi.ewr.aws.data.PersistEwrReportInfoOutput;
import gov.dot.nhtsa.odi.ewr.aws.data.ReportInfo;
import gov.dot.nhtsa.odi.ewr.aws.rds.RDSUtils;
import gov.dot.nhtsa.odi.ewr.aws.utils.EwrLogs;

/**
 * @author Bienvenido Ortiz
 */
public class PersistEwrReportInfo  {


	public static PersistEwrReportInfoOutput saveReportInfoAndData(ReportInfo reportInfo) {
		EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData", "Entering Method");
			
		PersistEwrReportInfoOutput output 							= new PersistEwrReportInfoOutput();
		AggregateDataPersistResults aggDataPersistResult 			= new AggregateDataPersistResults();
		AggregateTireDataPersistResults aggTireDataPersistResult 	= new AggregateTireDataPersistResults();
		
		output.setAggDataPersistResult(aggDataPersistResult);
		output.setAggTireDataPersistResult(aggTireDataPersistResult);
		
		//FIRST PERSIST REPORT INFO DATA
		String result = RDSUtils.persistReportInfoData(reportInfo);
		int numberOfRowsInserted = 0;
		output.setReportInfoDataPersisted(result);
        if(result.equalsIgnoreCase("SUCCESS")) {//SUCCESS OR FAIL OR ERROR MESSAGES
        	String reportName = reportInfo.getReportName();
        	if(reportInfo.getReportType().equalsIgnoreCase("D")) {//DEATH AND INJURY
	    		
	    		/* DEATH AND INJURY METHODS */
	    		//ALL OF THESE PERSIST TO THE SAME TABLE							//WE COULD USE REPORT CATEGORY AS WELL
	    		if(reportName.equalsIgnoreCase("LightVehiclesDI") ||				//L			
		    		reportName.equalsIgnoreCase("BusesAndMediumHeavyVehiclesDI") ||	//H
		    		reportName.equalsIgnoreCase("LowVolumeVehiclesDI") ||			//Z
		    		reportName.equalsIgnoreCase("TrailersDI")  ||					//Y
		    		reportName.equalsIgnoreCase("MotorCyclesDI") ||					//M
		    		reportName.equalsIgnoreCase("ChildRestraintsDI")) {				//C
		        	//GO AHEAD AND PERSIST THE DI VEHICLE RECORDS AS A BATCH TRANSACTION
		        	numberOfRowsInserted = RDSUtils.persistDeathsAndInjuries(reportInfo);
		        	output.setNumberOfDIRecordsPersisted(numberOfRowsInserted);
	    		}
	    		if(reportName.equalsIgnoreCase("TiresDI")){
		        	numberOfRowsInserted = RDSUtils.persistDeathsAndInjuriesForTires(reportInfo);
		        	output.setNumberOfDIRecordsPersisted(numberOfRowsInserted);    	
	    		}
	    		if(reportName.equalsIgnoreCase("EquipmentDI")){
		        	numberOfRowsInserted = RDSUtils.persistDeathsAndInjuriesForEquipment(reportInfo);
		        	output.setNumberOfDIRecordsPersisted(numberOfRowsInserted);
	    		}
	    		
	    		//DEATH AND INJURY RESULTS - OUTPUT
	        	if(output.getNumberOfDIRecordsPersisted()>0) {
	        		output.setRestOfTheDataPersisted("SUCCESS");
	        		output.setMessage("REPORT INFO AND DEATH AND INJURY DATA INSERTED SUCCESSFULLY");
	        	}else {
	        		output.setRestOfTheDataPersisted("FAIL");
	        		output.setMessage("UNABLE TO SAVE RECORDS");
	        		EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","UNABLE TO SAVE DEATH AND INJURY RECORDS - WILL ATTEMPT TO REMOVE THE REPORT INFO DATA ASSOCIATED WITH THESE RECORDS.");
	        		//AT THIS POINT I SHOULD REMOVE THE REPORT INFO DATA FROM THE TABLE SINCE THE DI DATA WAS NOT PERSISTED
	        		RDSUtils.deleteReportInfoData(reportInfo);
	        	}
	        /*SUBSTANTIALLY SIMILAR VEHICLES*/
        	}if(reportInfo.getReportType().equalsIgnoreCase("S")){
        	
	    		if(reportName.equalsIgnoreCase("SubstantiallySimilarVehicles")){//NO NEED FOR THIS CHECK BUT SAFETY FIRST
		        	numberOfRowsInserted = RDSUtils.persistSubstantiallySimilarVehicles(reportInfo);
		        	output.setNumberOfDIRecordsPersisted(numberOfRowsInserted);
	    		}
	    		
	    		//DEATH AND INJURY RESULTS - OUTPUT
	        	if(output.getNumberOfDIRecordsPersisted()>0) {
	        		output.setRestOfTheDataPersisted("SUCCESS");
	        		output.setMessage("REPORT INFO AND DEATH AND INJURY DATA INSERTED SUCCESSFULLY");
	        	}else {
	        		output.setRestOfTheDataPersisted("FAIL");
	        		output.setMessage("UNABLE TO SAVE RECORDS");
	        		EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","UNABLE TO SAVE DEATH AND INJURY RECORDS - WILL ATTEMPT TO REMOVE THE REPORT INFO DATA ASSOCIATED WITH THESE RECORDS.");
	        		//AT THIS POINT I SHOULD REMOVE THE REPORT INFO DATA FROM THE TABLE SINCE THE DI DATA WAS NOT PERSISTED
	        		RDSUtils.deleteReportInfoData(reportInfo);
	        	}
        		
        	}else {

				/* AGGREGATE DATA PERSISTING METHODS - V - H - Y - M - C */
    			if(reportName.equalsIgnoreCase("LightVehicles") ||
    	    		reportName.equalsIgnoreCase("BusesAndMediumHeavyVehicles") ||
    	    		reportName.equalsIgnoreCase("Trailers")  ||
    	    		reportName.equalsIgnoreCase("MotorCycles") ||
    	    		reportName.equalsIgnoreCase("ChildRestraints")) {
    				
    				EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","Attempting to persist Aggregate Data for report name: " + reportInfo.getFileName());
    				EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","Aggregate Production Data");
    				numberOfRowsInserted = RDSUtils.persistAggregateProductionData(reportInfo);
    	        	output.getAggDataPersistResult().setNumberOfProductionRecordsPersisted(numberOfRowsInserted);

    	        	
    	        	if(reportInfo.getAggregateDataConsumerComplaints() != null && !reportInfo.getAggregateDataConsumerComplaints().isEmpty()) {
	    	        	EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","Aggregate [ConsumerComplaints] Data");
	    	        	numberOfRowsInserted = RDSUtils.persistAggregateData(reportInfo, reportInfo.getAggregateDataConsumerComplaints());
	    	        	output.getAggDataPersistResult().setNumberOfConsumerCompliantsRecordsPersisted(numberOfRowsInserted);
    	        	}
    	        	
    	        	if(reportInfo.getAggregateDataPropertyDamage() != null && !reportInfo.getAggregateDataPropertyDamage().isEmpty()) {
	    	        	EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","Aggregate [PropertyDamage] Data");
	    	        	numberOfRowsInserted = RDSUtils.persistAggregateData(reportInfo, reportInfo.getAggregateDataPropertyDamage());
	    	        	output.getAggDataPersistResult().setNumberOfPropertyDamageRecordsPersisted(numberOfRowsInserted);
    	        	}
    	        	
    	        	if(reportInfo.getAggregateDataWarrantyClaims() != null && !reportInfo.getAggregateDataWarrantyClaims().isEmpty()) {
	    	        	EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","Aggregate [WarrantyClaims or ComplaintsWarrantyClaims] Data");
	    	        	numberOfRowsInserted = RDSUtils.persistAggregateData(reportInfo, reportInfo.getAggregateDataWarrantyClaims());
	    	        	output.getAggDataPersistResult().setNumberOfWarrantyClaimsRecordsPersisted(numberOfRowsInserted);
    	        	}
    	        	
    	        	if(reportInfo.getAggregateDataFieldReports() != null && !reportInfo.getAggregateDataFieldReports().isEmpty()) {
	    	        	EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","Aggregate [FieldReports] Data");
	    	        	numberOfRowsInserted = RDSUtils.persistAggregateData(reportInfo, reportInfo.getAggregateDataFieldReports());
	    	        	output.getAggDataPersistResult().setNumberOfFieldReportsRecordsPersisted(numberOfRowsInserted);
    	        	}
        		
    		  		//AGGREGATE RESULTS - OUTPUT
    		    	if(output.getAggDataPersistResult().getNumberOfProductionRecordsPersisted() > 0 ||
    		    			output.getAggDataPersistResult().getNumberOfConsumerCompliantsRecordsPersisted() > 0 || 
    		    			output.getAggDataPersistResult().getNumberOfPropertyDamageRecordsPersisted() > 0 ||
    		    			output.getAggDataPersistResult().getNumberOfWarrantyClaimsRecordsPersisted() > 0 ||
    		    			output.getAggDataPersistResult().getNumberOfFieldReportsRecordsPersisted() > 0) { 
    		    			//SOME OF THE TABS MIGHT NOT HAVE DATA - AS LONG AS ONE OF THE TABS HAS DATA WE ARE GOOD 
    		    			output.setRestOfTheDataPersisted("SUCCESS");
    		    			output.setMessage("REPORT INFO AND AGGREGATE DATA INSERTED SUCCESSFULLY");
    		    	}else {
    		    		output.setRestOfTheDataPersisted("FAIL");
    		    		output.setMessage("UNABLE TO SAVE AGGREGATE DATA RECORDS");
    		    		EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","UNABLE TO SAVE AGGREGATE DATA RECORDS - WILL ATTEMPT TO REMOVE THE REPORT INFO DATA ASSOCIATED WITH THESE RECORDS.");
    		    		// AT THIS POINT I SHOULD REMOVE THE REPORT INFO DATA FROM THE TABLE SINCE THE AGGREGATE DATA WAS NOT PERSISTED 
    		    		RDSUtils.deleteReportInfoData(reportInfo);
    		    	}
    			
    			}else if(reportName.equalsIgnoreCase("Tires")){
    				/* AGGREGATE DATA PERSISTING METHODS - TIRES (T) */
    				EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","Attempting to persist Aggregate Data (Tires) for report name: " + reportInfo.getFileName());
    				EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","Aggregate Tires [Production] Data");
    				numberOfRowsInserted = RDSUtils.persistAggregateProductionDataForTires(reportInfo);
    				output.getAggTireDataPersistResult().setNumOfTireProductionRecordsPersisted(numberOfRowsInserted);
    				
    				EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","Aggregate Tires [ProdSkuTypeCode] Data");
    				numberOfRowsInserted = RDSUtils.persistAggregateProdSkuTypeCodeDataForTires(reportInfo);
    				output.getAggTireDataPersistResult().setNumOfTireProdSkyTypeCodeRecordsPersisted(numberOfRowsInserted);

    				EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","Aggregate Tires [ProdOEVehApp] Data");
    				numberOfRowsInserted = RDSUtils.persistAggregateProdOEVehAppDataForTires(reportInfo);
    				output.getAggTireDataPersistResult().setNumOfTireProdOEVehAppRecordsPersisted(numberOfRowsInserted);
    				
    				EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","Aggregate Tires [PropertyDamage] Data");
    				numberOfRowsInserted = RDSUtils.persistAggregateDataForTires(reportInfo, reportInfo.getAggregateTirePropertyDamage());
    				output.getAggTireDataPersistResult().setNumOfTirePropDamagaRecordsPersisted(numberOfRowsInserted);
    				
    				EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","Aggregate Tires [WarrantyAdjustments] Data");
    				numberOfRowsInserted = RDSUtils.persistAggregateDataForTires(reportInfo, reportInfo.getAggregateTireWarrantyAdjustments());
    				output.getAggTireDataPersistResult().setNumOfTireWarrantyAdjustmentsRecordsPersisted(numberOfRowsInserted);
    				
    				EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","Aggregate Tires [CommonGreen] Data");
    				numberOfRowsInserted = RDSUtils.persistAggregateCommonGreenDataForTires(reportInfo);
    				output.getAggTireDataPersistResult().setNumOfTireCommonGreenRecordsPersisted(numberOfRowsInserted);
    				
    				//AGGREGATE TIRES RESULTS - OUTPUT
    		    	if(output.getAggTireDataPersistResult().getNumOfTireProductionRecordsPersisted() > 0 ||
    		    			output.getAggTireDataPersistResult().getNumOfTireProdSkyTypeCodeRecordsPersisted() > 0 || 
    		    			output.getAggTireDataPersistResult().getNumOfTireProdOEVehAppRecordsPersisted()> 0 || 
    		    			output.getAggTireDataPersistResult().getNumOfTirePropDamagaRecordsPersisted()> 0 ||
    		    			output.getAggTireDataPersistResult().getNumOfTireWarrantyAdjustmentsRecordsPersisted() > 0 ||
    		    			output.getAggTireDataPersistResult().getNumOfTireCommonGreenRecordsPersisted()> 0) { 
    		    			//SOME OF THE TABS MIGHT NOT HAVE DATA - AS LONG AS ONE OF THE TABS HAS DATA WE ARE GOOD 
    		    			output.setRestOfTheDataPersisted("SUCCESS");
    		    			output.setMessage("REPORT INFO AND AGGREGATE DATA FOR TIRES INSERTED SUCCESSFULLY");
    		    	}else {
    		    		output.setRestOfTheDataPersisted("FAIL");
    		    		output.setMessage("UNABLE TO SAVE AGGREGATE TIRES DATA RECORDS");
    		    		EwrLogs.DEBUG("PersistEwrReportInfo", "saveReportInfoAndData","UNABLE TO SAVE AGGREGATE TIRES DATA RECORDS - WILL ATTEMPT TO REMOVE THE REPORT INFO DATA ASSOCIATED WITH THESE RECORDS.");
    		    		// AT THIS POINT I SHOULD REMOVE THE REPORT INFO DATA FROM THE TABLE SINCE THE AGGREGATE DATA WAS NOT PERSISTED 
    		    		RDSUtils.deleteReportInfoData(reportInfo);
    		    	}
        		}
        	}//END OF AGGREGATE
        }else {
        	output.setReportInfoDataPersisted("FAIL");
        	output.setRestOfTheDataPersisted("FAIL");
        	output.setMessage("ERROR PERSISTING REPORT INFO DATA - NO NEED TO TRY TO PERSIST THE REST OF THE DATA - DETAIL: " + result);
        }
        
		return output;
	}
}
