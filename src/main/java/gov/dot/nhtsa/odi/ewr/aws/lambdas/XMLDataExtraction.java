package gov.dot.nhtsa.odi.ewr.aws.lambdas;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang.StringUtils;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.gson.Gson;

import gov.dot.nhtsa.odi.ewr.aws.data.PersistEwrReportInfoOutput;
import gov.dot.nhtsa.odi.ewr.aws.data.ReportInfo;
import gov.dot.nhtsa.odi.ewr.aws.s3.AwsS3Utils;
import gov.dot.nhtsa.odi.ewr.aws.ses.AwsSESUtils;
import gov.dot.nhtsa.odi.ewr.aws.utils.EwrLogs;
import gov.dot.nhtsa.odi.ewr.aws.utils.EwrUtils;
/**
 * @author Bienvenido Ortiz
 */
public class XMLDataExtraction implements RequestHandler<S3Event, String> {

	private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();

	/**
	 * @author Bienvenido Ortiz
	 * Main function - this function responds to an S3 event PUT object on the /ewrdata/datavalidation /bucket/folder. 
	 * The Event is only defined for XML files. These XML files are files that have already passed filename validation and 
	 * are put on this bucket by the UI. At this point this function would:
	 * a) retrieve the XML file from the bucket
	 * b) reads the data on it
	 * c) load the data on Java objects
	 * d) persist the data into staging tables in RDS > PostGresSQL (Aurora) Database 
	 * e) move the processed file to /ewrdata/datavalidationprocessed folder  
	 * f) invoke another lambda function which would do the data validation on the data persisted on staging table and subsequently 
	 * move the data to the production (fact) tables    
	 */
	public String handleRequest(S3Event event, Context context) {
		EwrLogs.DEBUG("XMLDataExtraction", "handleRequest", "Entering Main function");
		EwrLogs.DEBUG("XMLDataExtraction", "handleRequest", "Received event: " + event.toJson());
        Gson myGson = new Gson();
        String actionResults = "";
        // GET THE OBJECT FROM THE EVENT
        String bucket = event.getRecords().get(0).getS3().getBucket().getName();
        String key = event.getRecords().get(0).getS3().getObject().getKey();
        
		EwrLogs.DEBUG("XMLDataExtraction", "handleRequest", "Attempting to get the S3 object [" + key + "] from Bucket [" + bucket +"]");
		S3Object xmlS3Object = s3
				.getObject(new GetObjectRequest(bucket, key));
		InputStream xmlInputStream = xmlS3Object.getObjectContent();
		
		ReportInfo reportInfoObject = new ReportInfo();
		
		//I NEED TO REMOVE THE BUCKET/FOLDER NAME FROM THE KEY TO GET THE FILENAME
		//THIS datavalidation/000003L173001DP.xml SHOULD BECOME 000003L173001DP.xml
		String filename 		= StringUtils.substringAfter(key, "/");
		String reportCategory 	= EwrUtils.getReportCategoryCode(filename);
		String reportType 		= EwrUtils.getReportTypeCode(filename);
		
		EwrLogs.DEBUG("XMLDataExtraction", "handleRequest", "Filename [" + filename + "] -Report Category [" + reportCategory + "] -Report Type [" +reportType +"]");
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
			
				reportInfoObject =  EwrUtils.mapReportInfoDeathAndInjuryXMLData(xmlInputStream, filename);				
			}else if(reportCategory.equalsIgnoreCase("T")){
				reportInfoObject =  EwrUtils.mapReportInfoDeathAndInjuryXMLDataForTires(xmlInputStream, filename);
			
			}else if(reportCategory.equalsIgnoreCase("E")){
				reportInfoObject =  EwrUtils.mapReportInfoDeathAndInjuryXMLDataForEquipments(xmlInputStream, filename);
			
			}
		/*A = AGGREGATE REPORTS*/	
		}else if(reportType.equalsIgnoreCase("A")) {
			
			if(reportCategory.equalsIgnoreCase("L") ||
					reportCategory.equalsIgnoreCase("H") ||
					reportCategory.equalsIgnoreCase("Y") ||
					reportCategory.equalsIgnoreCase("M") ||
					reportCategory.equalsIgnoreCase("C")) {

				reportInfoObject =  EwrUtils.mapReportInfoAggregateXMLData(xmlInputStream, filename);
				//This could get Big context.getLogger().log("XMLDataExtraction.handleRequest() -  ReportInfo [Aggregate]:" + myGson.toJson(reportInfoObject));
				
			}else if(reportCategory.equalsIgnoreCase("T")){
				reportInfoObject =  EwrUtils.mapReportInfoAggregateXMLDataForTires(xmlInputStream, filename);
				//This could get Big context.getLogger().log("XMLDataExtraction.handleRequest() -  ReportInfo [Tires]:" + myGson.toJson(reportInfoObject));
			}
			
		}else {//S = SUBSTANTIALLY SIMILAR VEHICLES
			reportInfoObject =  EwrUtils.mapReportInfoSimilarVehiclesXMLData(xmlInputStream, filename);
		}

		if(reportInfoObject != null) {
			reportInfoObject.setReportType(reportType);
			reportInfoObject.setReportCategory(reportCategory);
			
			/*CALLING THE METHOD THAT PERSIST THE DATA*/
			PersistEwrReportInfoOutput persistOutput = PersistEwrReportInfo.saveReportInfoAndData(reportInfoObject);
			persistOutput.setFilename(filename);
			actionResults = myGson.toJson(persistOutput);
			//OUTPUT PERSISTING RESULTS
			EwrLogs.DEBUG("XMLDataExtraction", "handleRequest", "Persist Report Info Results: " + myGson.toJson(persistOutput));
			
			
			if(persistOutput.getRestOfTheDataPersisted().equalsIgnoreCase("SUCCESS")
					&& persistOutput.getReportInfoDataPersisted().equalsIgnoreCase("SUCCESS") ) {
				//WE NEED TO MOVE THE SUCCESSFULLY PROCESSED XML FILE TO A **PROCESSED FOLDER, THEN REMOVE IT FROM ORIGINAL BUCKET
				AwsS3Utils.CopyObject(key, bucket, System.getenv("DV_XML_PROCESSED"), filename);
				AwsS3Utils.deleteObject(key, bucket);
				
				/*I NEED TO CALL THE METHOD THAT CALL THE VALIDATION PYTHON LAMBDA FUNCTION.*/ 
				validatePersistedEWRData(filename, reportType);
				AwsSESUtils.sendNotification(myGson.toJson(persistOutput));
				
			}else {
				EwrLogs.ERROR("XMLDataExtraction", "handleRequest", "AN ERROR HAPPENED WHILE PESISTING REPORT INFO AND/OR ITS DATA. WILL MOVE THE XML FILE TO THE FAILURE BUCKET FOR REVIEWING.");

				//MOVE XML TO FAILURE FOLDER FOR LATER REVIEW
				AwsS3Utils.CopyObject(key, bucket, System.getenv("DV_XML_FAILED"), filename);
				AwsS3Utils.deleteObject(key, bucket);
				/* I THINK WE SHOULD SEND A NOTIFICATION OF SOME SORTS AT THIS POINT */
			}
		}else {
			//PLEASE RETURN THE OBJECT WITH ERROR MESSAGE
			PersistEwrReportInfoOutput persistOutputError = new PersistEwrReportInfoOutput();
			persistOutputError.setFilename(filename);
			persistOutputError.setMessage("XML DATA COULD NOT BE EXTRACTED FROM DOCUMENT. XML DOCUMENT MOVED TO THE FAILURE FOLDER.");
			persistOutputError.setNumberOfDIRecordsPersisted(0);
			persistOutputError.setReportInfoDataPersisted("FAIL");
			persistOutputError.setReportInfoDataPersisted("FAIL");
			//MOVE XML TO FAILURE FOLDER
			AwsS3Utils.CopyObject(key, bucket, System.getenv("DV_XML_FAILED"), filename);
			AwsS3Utils.deleteObject(key, bucket);

			AwsSESUtils.sendNotification(myGson.toJson(persistOutputError));
			return myGson.toJson(persistOutputError);
		}
		
		return actionResults;
	}

	
	/**
	 * @author Bienvenido Ortiz
	 * A method that calls another Lambda function that would validate the (Death and Injured) and (Aggregate) Data 
	 * I just persisted into the database. The function is a Python function created by Joe/Shini.   
	 */
	private String validatePersistedEWRData(String  filename, String reportType) {
		AWSLambda lambdaClient = AWSLambdaClientBuilder.standard().build();
		String payload = "{\"fileName\": \""+filename+"\"}";
		
		String validationFunctionName = "";
		
		if(reportType.equalsIgnoreCase("A")) {
			validationFunctionName = "ewr_ag_datavalidation";
		}else {//Anything but Aggregate, in other words, D=Death and Injury and S=substantially Similar Vehicles

			//String functionARN = "arn:aws:lambda:us-east-1:893788733553:function:ewr_di_datavalidation";
			validationFunctionName = "ewr_di_datavalidation";
		}
		
		InvokeRequest request = new InvokeRequest()
				.withInvocationType(InvocationType.Event)//Event is an asynchronous execution - this caller lambda won't wait for the target lambda to finish 
				.withFunctionName(validationFunctionName)//Can use name or ARN 
				.withPayload(payload);

		EwrLogs.DEBUG("XMLDataExtraction", "validatePersistedEWRData", "Calling Validation Lambda Function ["+validationFunctionName+"] with value: " + payload);
		InvokeResult result = lambdaClient.invoke(request);
		//Since it is asynchronous execution I don't think we will get here 
		String stResult = StandardCharsets.UTF_8.decode(result.getPayload()).toString();
		return stResult;
	}
}
