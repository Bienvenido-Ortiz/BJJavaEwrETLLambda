package gov.dot.nhtsa.odi.ewr.aws.rds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;

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
import gov.dot.nhtsa.odi.ewr.aws.utils.EwrLogs;
import gov.dot.nhtsa.odi.ewr.aws.utils.EwrUtils;

/**
 * @author Bienvenido Ortiz
 */
public class RDSUtils {

	
	/**
	 * @author Bienvenido Ortiz
	 * @return conn A Connection object
	 */
	public static Connection getDBConnection() {
		Connection conn = null;
		try {
			
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://"+System.getenv("POSTGRES_URL");
			String username = System.getenv("POSTGRES_USERNAME");
			String password = System.getenv("POSTGRES_PASSWORD");

			conn = DriverManager.getConnection(url, username, password);      
			return conn;
			
		} catch (ClassNotFoundException | SQLException e) {
			EwrLogs.ERROR("RDSUtils", "getDBConnection", "ClassNotFoundException | SQLException: " + e.getMessage());
		}

		return conn;
	}
	
	
	/**
	 * @author Bienvenido Ortiz
	 * @return statement A Statement object
	 */
	public static Statement getDBStatement() {
		Statement statement = null;
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://"+System.getenv("POSTGRES_URL");
			String username = System.getenv("POSTGRES_USERNAME");
			String password = System.getenv("POSTGRES_PASSWORD");

			Connection conn = DriverManager.getConnection(url, username, password);      
			statement = conn.createStatement();
			
		} catch (ClassNotFoundException | SQLException e) {
			EwrLogs.ERROR("RDSUtils", "getDBStatement", "ClassNotFoundException | SQLException: " + e.getMessage());
		}

		return statement;
	}
	
	/**
	 * @author Bienvenido Ortiz
	 */
	public static String persistReportInfoData(ReportInfo reportInfo) {
		EwrLogs.DEBUG("RDSUtils", "persistReportInfoData", "Entering Method");
		Connection conn = RDSUtils.getDBConnection();
		int affectedRecord = 0;
		String result = "FAIL";
		Statement statement = null;
		PreparedStatement sqlStatement = null;
		
		try {
			statement = conn.createStatement();
		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "persistReportInfoData", "SQLException - " + e.getMessage());
		}
		
		reportInfo.setEwrReportId(getEwrReportId(statement, reportInfo.getEwrId()));
		
		//BJ COULD GET BIG - Gson gsonObject = new Gson();
		//BJ COULD GET BIG - context.getLogger().log("RDSUtils.persistReportInfoData() - Report Info Data provided to persist: " + gsonObject.toJson(reportInfo));
		
        String insertSql = "INSERT INTO EWR.EWR_STG_REPORT_INFO ( "   
                + "EWR_ID, "
                + "EWR_REPORT_ID, "
                + "REPORT_CATEGORY_CD, "
                + "REPORT_YR,"
                + "REPORT_QTR, "
                + "REPORT_VERSION, "
                + "CONFIDENTIALITY_CD, "
                + "FILE_NAME,"
                + "REPORT_TYPE_CD, "
                + "REPORT_GENERATED_DT, "
                + "REPORT_CONTACT_NAME, "
                + "REPORT_CONTACT_EMAIL,"
                + "REPORT_CONTACT_PHONE, "
                + "NHTSA_TEMPLATE_REVISION_NO, "
                + "MFR_NAME, "
                + "REPORT_NAME "
                + ") "
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try {

        	sqlStatement = conn.prepareStatement(insertSql);
			sqlStatement.setInt(1, reportInfo.getEwrId());
			sqlStatement.setInt(2, reportInfo.getEwrReportId());
			sqlStatement.setString(3, EwrUtils.getReportCategoryCode(reportInfo.getFileName()));
			sqlStatement.setString(4, String.valueOf(reportInfo.getReportYear()));
			sqlStatement.setString(5, String.valueOf(reportInfo.getReportQuarter()));
			sqlStatement.setString(6, reportInfo.getReportVersion());
			sqlStatement.setString(7, EwrUtils.getConfidentialityCode(reportInfo.getFileName()));
			sqlStatement.setString(8, reportInfo.getFileName());
			sqlStatement.setString(9, EwrUtils.getReportTypeCode(reportInfo.getFileName()));
			sqlStatement.setDate(10, java.sql.Date.valueOf(reportInfo.getReportGeneratedDate())); 
			sqlStatement.setString(11, reportInfo.getReportContactName());
			sqlStatement.setString(12, reportInfo.getReportContactEmail());
			sqlStatement.setString(13, reportInfo.getReportContactPhone());
			sqlStatement.setString(14, reportInfo.getNhtsaTemplateRevisionNo());
			sqlStatement.setString(15, reportInfo.getManufacturerName());
			sqlStatement.setString(16, reportInfo.getReportName());
			
            affectedRecord = sqlStatement.executeUpdate();
            if(affectedRecord == 1) {
            	result = "SUCCESS";
            }
            EwrLogs.DEBUG("RDSUtils", "persistReportInfoData", "Results of Inserting Report Info Tab Data: [" + result + "]");
            			
		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "persistReportInfoData", "SQLException - " + e.getMessage());
			result = e.getMessage();
		}finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistReportInfoData", "SQLException - " + e.getMessage());
				}
			}
			if(sqlStatement != null) {
				try {
					sqlStatement.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistReportInfoData", "SQLException - " + e.getMessage());
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistReportInfoData", "SQLException - " + e.getMessage());
				}
			}
		}
        
		return result;
	}	

	/**
	 * @author Bienvenido Ortiz
	 * This method would be use to persist Aggregate Data for Tires (T)
	 * For CommonGreen Tab
	*/
	public static int persistAggregateCommonGreenDataForTires(ReportInfo reportInfo ) {
		int[] results;
		int insertedRecords = 0;
		Connection conn = RDSUtils.getDBConnection();
		PreparedStatement sqlStatement = null;
		
		
		String insertSql = "INSERT INTO EWR.EWR_STG_TIRE_CG   ("  
			+ "EWR_ID, "                      
			+ "EWR_REPORT_ID, "               
			+ "RECORD_ID, "    
	        + "CG_GROUP, "
	        + "CG_TYPE_CD, "
	        + "TIRE_LINE, "
            + "SKU, "
            + "BRAND_NAME, "
            + "BRAND_OWNER, "
            + "PLANT_CD, "
			+ "FILE_NAME "    
			+ ") "
			+ "VALUES"
			+ "(?,?,?,?,?,?,?,?,?,?,?)";//11 FIELDS

        
        try {
          	//LOOP THROUGH THE AGGREGATE [CommonGreen] DATA FOR TIRES
			sqlStatement = conn.prepareStatement(insertSql);
			EwrLogs.DEBUG("RDSUtils", "persistAggregateCommonGreenDataForTires", "About to persist " + reportInfo.getAggregateTireCommonGreen().size() + " records.");
			for(AggregateTireCommonGreen aggCgRecord: reportInfo.getAggregateTireCommonGreen())
			{

				sqlStatement.setInt(1, reportInfo.getEwrId());
				sqlStatement.setInt(2, reportInfo.getEwrReportId());
				sqlStatement.setInt(3, aggCgRecord.getRecordId());
				sqlStatement.setString(4, aggCgRecord.getCommonGreenGroup());
				sqlStatement.setString(5, aggCgRecord.getCommonGreenTypeCode()); 
				sqlStatement.setString(6, aggCgRecord.getTireLine());
				sqlStatement.setString(7, aggCgRecord.getSku());
				sqlStatement.setString(8, aggCgRecord.getBrandName());
				sqlStatement.setString(9, aggCgRecord.getBrandOwner());
				sqlStatement.setString(10, aggCgRecord.getPlantCode());
				sqlStatement.setString(11, reportInfo.getFileName());
				
				//AT THE END OF EACH LOOP ADD TO THE BATCH
				sqlStatement.addBatch();
			}
			results = sqlStatement.executeBatch();
			insertedRecords = results.length;
			
		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "persistAggregateCommonGreenDataForTires", "SQLException - " + e.getMessage());
		}finally {
			if(sqlStatement != null) {
				try {
					sqlStatement.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistAggregateCommonGreenDataForTires", "SQLException - " + e.getMessage());
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistAggregateCommonGreenDataForTires", "SQLException - " + e.getMessage());
				}
			}
		}
        
		return insertedRecords;
	}
	
	/**
	 * @author Bienvenido Ortiz
	 * This method would be use to persist Aggregate Data for Tires (T)
	 * For PropertyDamage and WarrantyAdjustments Tabs - they shared the same table
	*/
	public static int persistAggregateDataForTires(ReportInfo reportInfo, List<AggregateTirePropertyDamageAndWarrantyAdjustments> aggregateList) {
		int[] results;
		int insertedRecords = 0;
		Connection conn = RDSUtils.getDBConnection();
		PreparedStatement sqlStatement = null;
		
		
		String insertSql = "INSERT INTO EWR.EWR_STG_AGGR_TIRE   ("  
			+ "EWR_ID, "                      
			+ "EWR_REPORT_ID, "               
			+ "RECORD_ID, "
			+ "AGGR_TYPE_CD, "   
            + "TIRE_LINE, "
            + "TIRE_SIZE, "
            + "SKU, "
            + "PLANT_CD, "
            + "PRODUCTION_YR, "
            + "TREAD_71, "
			+ "SIDEWALL_72, "                        
			+ "BEAD_73, "                      
			+ "OTHER_98, "
			+ "FILE_NAME "    
			+ ") "
			+ "VALUES"
			+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";//14 FIELDS

        
        try {
          	//LOOP THROUGH THE AGGREGATE [PropertyDamage and WarrantyAdjustments] DATA FOR TIRES
			sqlStatement = conn.prepareStatement(insertSql);
			EwrLogs.DEBUG("RDSUtils", "persistAggregateDataForTires", "About to persist " + aggregateList.size() + " records.");
			for(AggregateTirePropertyDamageAndWarrantyAdjustments aggregateRecord: aggregateList)
			{

				sqlStatement.setInt(1, reportInfo.getEwrId());
				sqlStatement.setInt(2, reportInfo.getEwrReportId());
				sqlStatement.setInt(3, aggregateRecord.getRecordId());
				sqlStatement.setString(4, aggregateRecord.getAggregateTypeCode().toUpperCase(Locale.ENGLISH));
				sqlStatement.setString(5, aggregateRecord.getTireLine());
				sqlStatement.setString(6, aggregateRecord.getTireSize()); 
				sqlStatement.setString(7, aggregateRecord.getSku());
				sqlStatement.setString(8, aggregateRecord.getPlantCode());
				sqlStatement.setString(9, aggregateRecord.getProductionYear());
				sqlStatement.setInt(10, aggregateRecord.getTreat71());
				sqlStatement.setInt(11, aggregateRecord.getSidewall72());
				sqlStatement.setInt(12, aggregateRecord.getBead73());
				sqlStatement.setInt(13, aggregateRecord.getOther98());
				sqlStatement.setString(14, reportInfo.getFileName());
				
				//AT THE END OF EACH LOOP ADD TO THE BATCH
				sqlStatement.addBatch();
			}
			results = sqlStatement.executeBatch();
			insertedRecords = results.length;
			
		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "persistAggregateDataForTires", "SQLException - " + e.getMessage() + "||" + e.getNextException());
		}finally {
			if(sqlStatement != null) {
				try {
					sqlStatement.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistAggregateDataForTires", "SQLException - " + e.getMessage());
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistAggregateDataForTires", "SQLException - " + e.getMessage());
				}
			}
		}
        
		return insertedRecords;
	}
	
	/**
	 * @author Bienvenido Ortiz
	 * This method would be use to persist Aggregate Data for Tires (T)
	 * For ProdOEVehApp Tab
	*/
	public static int persistAggregateProdOEVehAppDataForTires(ReportInfo reportInfo ) {
		int[] results;
		int insertedRecords = 0;
		Connection conn = RDSUtils.getDBConnection();
		PreparedStatement sqlStatement = null;
		
		
		String insertSql = "INSERT INTO EWR.EWR_STG_PRODOE_VEHAPP   ("  
			+ "EWR_ID, "                      
			+ "EWR_REPORT_ID, "               
			+ "RECORD_ID, "                   
            + "SKU, "
			+ "MAKE, "                        
			+ "MODEL, "                      
			+ "MODEL_YR, "
			+ "FILE_NAME "    
			+ ") "
			+ "VALUES"
			+ "(?,?,?,?,?,?,?,?)";//8 FIELDS

        
        try {
          	//LOOP THROUGH THE AGGREGATE [ProdOEVehApp] DATA FOR TIRES
			sqlStatement = conn.prepareStatement(insertSql);
			EwrLogs.DEBUG("RDSUtils", "persistAggregateProdOEVehAppDataForTires", "About to persist " + reportInfo.getAggregateTireProdOEVehApp().size() + " records.");
			for(AggregateTireProdOEVehApp aggregateRecord: reportInfo.getAggregateTireProdOEVehApp())
			{

				sqlStatement.setInt(1, reportInfo.getEwrId());
				sqlStatement.setInt(2, reportInfo.getEwrReportId());
				sqlStatement.setInt(3, aggregateRecord.getRecordId());
				sqlStatement.setString(4, aggregateRecord.getSku());
				sqlStatement.setString(5, aggregateRecord.getMake());
				sqlStatement.setString(6, aggregateRecord.getModel());
				sqlStatement.setString(7, aggregateRecord.getModelYear());
				sqlStatement.setString(8, reportInfo.getFileName());
				
				//AT THE END OF EACH LOOP ADD TO THE BATCH
				sqlStatement.addBatch();
			}
			results = sqlStatement.executeBatch();
			insertedRecords = results.length;
			
		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "persistAggregateProdOEVehAppDataForTires", "SQLException - " + e.getMessage());
		}finally {
			if(sqlStatement != null) {
				try {
					sqlStatement.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistAggregateProdOEVehAppDataForTires", "SQLException - " + e.getMessage());
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistAggregateProdOEVehAppDataForTires", "SQLException - " + e.getMessage());
				}
			}
		}
        
		return insertedRecords;
	}
	
	/**
	 * @author Bienvenido Ortiz
	 * This method would be use to persist Aggregate Data for Tires (T)
	 * For ProdSkuTypeCode Tab
	*/
	public static int persistAggregateProdSkuTypeCodeDataForTires(ReportInfo reportInfo ) {
		int[] results;
		int insertedRecords = 0;
		Connection conn = RDSUtils.getDBConnection();
		PreparedStatement sqlStatement = null;
		
		
		String insertSql = "INSERT INTO EWR.EWR_STG_SKU_TYPECD   ("  
			+ "EWR_ID, "                      
			+ "EWR_REPORT_ID, "               
			+ "RECORD_ID, "                   
            + "SKU, "
            + "SKU_TYPE_CD, "
			+ "FILE_NAME "    
			+ ") "
			+ "VALUES"
			+ "(?,?,?,?,?,?)";//6 FIELDS

        
        try {
          	//LOOP THROUGH THE AGGREGATE [ProdSkuTypeCode] DATA FOR TIRES
			sqlStatement = conn.prepareStatement(insertSql);
			EwrLogs.DEBUG("RDSUtils", "persistAggregateProdSkuTypeCodeDataForTires", "About to persist " + reportInfo.getAggregateTireProdSkuTypeCode().size() + " records.");
			for(AggregateTireProdSkuTypeCode aggRecord: reportInfo.getAggregateTireProdSkuTypeCode())
			{

				sqlStatement.setInt(1, reportInfo.getEwrId());
				sqlStatement.setInt(2, reportInfo.getEwrReportId());
				sqlStatement.setInt(3, aggRecord.getRecordId());
				sqlStatement.setString(4, aggRecord.getSku());
				sqlStatement.setString(5, aggRecord.getTypeCode()); 
				sqlStatement.setString(6, reportInfo.getFileName());
				
				//AT THE END OF EACH LOOP ADD TO THE BATCH
				sqlStatement.addBatch();
			}
			results = sqlStatement.executeBatch();
			insertedRecords = results.length;
			
		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "persistAggregateProdSkuTypeCodeDataForTires", "SQLException - " + e.getMessage());
		}finally {
			if(sqlStatement != null) {
				try {
					sqlStatement.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistAggregateProdSkuTypeCodeDataForTires", "SQLException - " + e.getMessage());
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistAggregateProdSkuTypeCodeDataForTires", "SQLException - " + e.getMessage());
				}
			}
		}
        
		return insertedRecords;
	}
	
	
	/**
	 * @author Bienvenido Ortiz
	 * This method would be use to persist Aggregate Data for Tires (T)
	 * For Production Tab
	*/
	public static int persistAggregateProductionDataForTires(ReportInfo reportInfo ) {
		int[] results;
		int insertedRecords = 0;
		Connection conn = RDSUtils.getDBConnection();
		PreparedStatement sqlStatement = null;
		
		
		String insertSql = "INSERT INTO EWR.EWR_STG_PRODUCTION_TIRE   ("  
			+ "EWR_ID, "                      
			+ "EWR_REPORT_ID, "               
			+ "RECORD_ID, "                   
            + "TIRE_LINE, "
            + "TIRE_SIZE, "
            + "SKU, "
            + "PRODUCTION_YR, "
            + "PLANT_CD, "
			+ "ORIG_EQUIP_YN, "
			+ "WARRANTY_PRODUCTION, "
			+ "TOTAL_PRODUCTION, "
			+ "FILE_NAME "    
			+ ") "
			+ "VALUES"
			+ "(?,?,?,?,?,?,?,?,?,?,?,?)";//12 FIELDS

        
        try {
          	//LOOP THROUGH THE AGGREGATE [Production] DATA FOR TIRES
			sqlStatement = conn.prepareStatement(insertSql);
			EwrLogs.DEBUG("RDSUtils", "persistAggregateProductionDataForTires", "About to persist " + reportInfo.getAggregateTireProduction().size() + " records.");
			for(AggregateTireProduction aggProdTireRecord: reportInfo.getAggregateTireProduction())
			{

				sqlStatement.setInt(1, reportInfo.getEwrId());
				sqlStatement.setInt(2, reportInfo.getEwrReportId());
				sqlStatement.setInt(3, aggProdTireRecord.getRecordId());
				sqlStatement.setString(4, aggProdTireRecord.getTireLine());
				sqlStatement.setString(5, aggProdTireRecord.getTireSize()); 
				sqlStatement.setString(6, aggProdTireRecord.getSku());
				sqlStatement.setString(7, aggProdTireRecord.getProductionYear());
				sqlStatement.setString(8, aggProdTireRecord.getPlantCode());
				sqlStatement.setString(9, aggProdTireRecord.getOriginalEquipYN());
				sqlStatement.setInt(10, Integer.valueOf(aggProdTireRecord.getWarrantyProduction()));
				sqlStatement.setInt(11, Integer.valueOf(aggProdTireRecord.getTotalProduction()));
				sqlStatement.setString(12, reportInfo.getFileName());
				
				//AT THE END OF EACH LOOP ADD TO THE BATCH
				sqlStatement.addBatch();
			}
			results = sqlStatement.executeBatch();
			insertedRecords = results.length;
			
		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "persistAggregateProductionDataForTires", "SQLException - " + e.getMessage());
		}finally {
			if(sqlStatement != null) {
				try {
					sqlStatement.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistAggregateProductionDataForTires", "SQLException - " + e.getMessage());
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistAggregateProductionDataForTires", "SQLException - " + e.getMessage());
				}
			}
		}
        
		return insertedRecords;
	}
	
	
	/**
	 * @author Bienvenido Ortiz
	 * This method would be use to persist Aggregate Data for Vehicles (L, H, M, Y) and Child Restraints (C)
	 * For Production Tab
	*/
	public static int persistAggregateProductionData(ReportInfo reportInfo ) {
		int[] results;
		int insertedRecords = 0;
		Connection conn = RDSUtils.getDBConnection();
		PreparedStatement sqlStatement = null;
		
		String insertSql = "INSERT INTO EWR.EWR_STG_PRODUCTION   ("  
			+ "EWR_ID, "                      
			+ "EWR_REPORT_ID, "               
			+ "RECORD_ID, "                   
			+ "MAKE, "                        
			+ "MODEL, "                      
			+ "MODEL_YR, "
			+ "FUEL_SYSTEM, "
			+ "BRAKE_SYSTEM, "
			+ "TYPE_CD, "
			+ "PLATFORM, "
			+ "TOTAL_PRODUCTION, "
			+ "FUEL_PROPULSION_SYSTEM, "      
			+ "FILE_NAME "    
			+ ") "
			+ "VALUES"
			+ "(?,?,?,?,?,?,?,?,?,?,?,?,?)";//13 FIELDS

        
        try {
          	//LOOP THROUGH THE AGGREGATE PRODUCTION DATA
			sqlStatement = conn.prepareStatement(insertSql);
			EwrLogs.DEBUG("RDSUtils", "persistAggregateProductionData", "About to persist " + reportInfo.getAggregateProduction().size() + " records.");
			for(AggregateProduction aggProdRecord: reportInfo.getAggregateProduction())
			{

				sqlStatement.setInt(1, reportInfo.getEwrId());
				sqlStatement.setInt(2, reportInfo.getEwrReportId());
				sqlStatement.setInt(3, aggProdRecord.getRecordId());
				sqlStatement.setString(4, aggProdRecord.getMake());
				sqlStatement.setString(5, aggProdRecord.getModel()); 
				sqlStatement.setString(6, aggProdRecord.getModelYear());//In Child Restraint this is ProdYear
				sqlStatement.setString(7, aggProdRecord.getFuelSystem());
				sqlStatement.setString(8, aggProdRecord.getBreakSystem());
				sqlStatement.setString(9, aggProdRecord.getTypeCode());
				sqlStatement.setString(10, aggProdRecord.getPlatform());
				sqlStatement.setInt(11, Integer.valueOf(aggProdRecord.getTotalProduction()));
				sqlStatement.setString(12, aggProdRecord.getFuelPropulsionSystem());
				sqlStatement.setString(13, reportInfo.getFileName());
				
				//AT THE END OF EACH LOOP ADD TO THE BATCH
				sqlStatement.addBatch();
			}
			results = sqlStatement.executeBatch();
			insertedRecords = results.length;
			
		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "persistAggregateProductionData", "SQLException - " + e.getMessage());
		}finally {
			if(sqlStatement != null) {
				try {
					sqlStatement.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistAggregateProductionData", "SQLException - " + e.getMessage());
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistAggregateProductionData", "SQLException - " + e.getMessage());
				}
			}
		}
        
		return insertedRecords;
	}
	
	
	/**
	 * @author Bienvenido Ortiz
	 * This method would be use to persist Aggregate Data for Vehicles (L, H, M, Y) and Child Restraints (C)
	 * Data is coming from ConsumerComplaints, PropertyDamage, WarrantyClaims and FieldReports for (L, H, M, Y)
	 * and ComplaintsWarrantyClaims and FieldReports for (C)
	*/
	public static int persistAggregateData(ReportInfo reportInfo, List<AggregateData> aggregateDataList) {
		int[] results;
		int insertedRecords = 0;
		Connection conn = RDSUtils.getDBConnection();
		PreparedStatement sqlStatement = null;
		
		String insertSql = "INSERT INTO EWR.EWR_STG_AGGR ("  
			+ "EWR_ID, "                      
			+ "EWR_REPORT_ID, "               
			+ "RECORD_ID, "                   
			+ "AGGR_TYPE_CD, "                
			+ "MAKE, "                        
			+ "MODEL, "                      
			+ "MODEL_YR, "                    
			+ "STEERING_01, "                 
			+ "SUSPENSION_02, "               
			+ "SERVICE_BRAKE_03, "            
			+ "SERVICE_BRAKE_AIR_04, "        
			+ "PARKING_BRAKE_05, "            
			+ "ENG_AND_ENG_COOLING_06, "      
			+ "FUEL_SYSTEM_07, "              
			+ "FUEL_SYSTEM_DIESEL_08, "       
			+ "FUEL_SYSTEM_OTHER_09, "        
			+ "POWER_TRAIN_10, "              
			+ "ELECTRICAL_11, "               
			+ "EXTLIGHTING_12, "              
			+ "VISIBILITY_13, "               
			+ "AIRBAGS_14, "                  
			+ "SEATBELTS_15, "                
			+ "STRUCTURE_16, "                
			+ "LATCH_17, "                    
			+ "SPEED_CONTROL_18, "            
			+ "TIRES_RELATED_19, "            
			+ "WHEELS_20, "                   
			+ "TRAILER_HITCH_21, "            
			+ "SEATS_22, "                    
			+ "FIRE_RELATED_23, "             
			+ "ROLLOVER_24, "                 
			+ "ELECTRONIC_STABILITY_CTRL_25, "
			+ "FORWARD_COLLISION_26, "        
			+ "LANE_DEPARTURE_27, "           
			+ "BACKOVER_PREVENTION_28, "     
			//child restraint
			+ "BUCKLE_51, "                   
			+ "SEATSHELL_52, "                
			+ "HANDLE_53, "                   
			+ "BASE_54, "                     
	
			+ "TYPE_CD, "                     
			+ "FUEL_PROPULSION_SYSTEM, "      
			+ "FILE_NAME "    
			+ ") "
			+ "VALUES"
			+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";//42 FIELDS

		/* code below to be updated for aggregate data */
        
        try {
        	//LOOP THROUGH THE AGGREGATE DATA
			sqlStatement = conn.prepareStatement(insertSql);
			EwrLogs.DEBUG("RDSUtils", "persistAggregateData", "About to persist " + aggregateDataList.size() + " records.");
			for(AggregateData anAggregateDataRecord: aggregateDataList)
			{

				sqlStatement.setInt(1, reportInfo.getEwrId());
				sqlStatement.setInt(2, reportInfo.getEwrReportId());
				sqlStatement.setInt(3, anAggregateDataRecord.getRecordId());
				sqlStatement.setString(4, anAggregateDataRecord.getAggregateTypeCode().toUpperCase(Locale.ENGLISH));
				sqlStatement.setString(5, anAggregateDataRecord.getMake());
				sqlStatement.setString(6, anAggregateDataRecord.getModel());
				sqlStatement.setString(7, anAggregateDataRecord.getModelYear());
				sqlStatement.setInt(8, anAggregateDataRecord.getSteering01());
				sqlStatement.setInt(9, anAggregateDataRecord.getSuspension02());
				sqlStatement.setInt(10, anAggregateDataRecord.getServiceBreak03());
				sqlStatement.setInt(11, anAggregateDataRecord.getServiceBreakAir04());
				sqlStatement.setInt(12, anAggregateDataRecord.getParkingBreak05());
				sqlStatement.setInt(13, anAggregateDataRecord.getEngAndEngCooling06());
				sqlStatement.setInt(14, anAggregateDataRecord.getFuelSystem07());
				sqlStatement.setInt(15, anAggregateDataRecord.getFuelSystemDiesel08());
				sqlStatement.setInt(16, anAggregateDataRecord.getFuelSystemOther09());
				sqlStatement.setInt(17, anAggregateDataRecord.getPowerTrain10());
				sqlStatement.setInt(18, anAggregateDataRecord.getElectrical11());
				sqlStatement.setInt(19, anAggregateDataRecord.getExtLighting12());
				sqlStatement.setInt(20, anAggregateDataRecord.getVisibility13());
				sqlStatement.setInt(21, anAggregateDataRecord.getAirbags14());
				sqlStatement.setInt(22, anAggregateDataRecord.getSeatbelts15());
				sqlStatement.setInt(23, anAggregateDataRecord.getStructure16());
				sqlStatement.setInt(24, anAggregateDataRecord.getLatch17());
				sqlStatement.setInt(25, anAggregateDataRecord.getSpeedControl18());
				sqlStatement.setInt(26, anAggregateDataRecord.getTiresRelated19());
				sqlStatement.setInt(27, anAggregateDataRecord.getWheels20());
				sqlStatement.setInt(28, anAggregateDataRecord.getTrailerHitch21());
				sqlStatement.setInt(29, anAggregateDataRecord.getSeats22());		
				sqlStatement.setInt(30, anAggregateDataRecord.getFireRelated23());
				sqlStatement.setInt(31, anAggregateDataRecord.getRollover24());
				//These four are for Child Restraints				
				sqlStatement.setInt(32, anAggregateDataRecord.getBuckle51());				
				sqlStatement.setInt(33, anAggregateDataRecord.getSeatshell52());
				sqlStatement.setInt(34, anAggregateDataRecord.getHandle53());
				sqlStatement.setInt(35, anAggregateDataRecord.getBase54());
				
				sqlStatement.setInt(36, anAggregateDataRecord.getElectronicStabilityCtrl25());
				sqlStatement.setInt(37, anAggregateDataRecord.getForwardCollision26());
				sqlStatement.setInt(38, anAggregateDataRecord.getLaneDeparture27());
				sqlStatement.setInt(39, anAggregateDataRecord.getBackoverPrevention28());
				sqlStatement.setString(40, anAggregateDataRecord.getTypeCode());
				sqlStatement.setString(41, anAggregateDataRecord.getFuelPropulsionSystem());
				sqlStatement.setString(42, reportInfo.getFileName());
				
				//AT THE END OF EACH LOOP ADD TO THE BATCH
				sqlStatement.addBatch();
			}
			results = sqlStatement.executeBatch();
			insertedRecords = results.length;
			
		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "persistAggregateData", "SQLException - " + e.getMessage());
		}finally {
			if(sqlStatement != null) {
				try {
					sqlStatement.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistAggregateData", "SQLException - " + e.getMessage());
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistAggregateData", "SQLException - " + e.getMessage());
				}
			}
		}
        
		return insertedRecords;
	}
	
	
	/**
	 * @author Bienvenido Ortiz
	 * @param reportInfo
	 * @return insertedRecords the number of records inserted at the database
	 */
	public static int persistDeathsAndInjuries(ReportInfo reportInfo ) {
		EwrLogs.DEBUG("RDSUtils", "persistDeathsAndInjuries", "Entering Method");
		int[] results;
		int insertedRecords = 0;
		Connection conn = RDSUtils.getDBConnection();
		PreparedStatement sqlStatement = null;
		
        String insertSql = "INSERT INTO EWR.EWR_STG_DEATH_INJURY ("   
                + "EWR_ID, "
                + "EWR_REPORT_ID, "
                + "MFR_UNIQUE_ID, "
                + "DI_ID,"
                + "RECORD_ID, "
                + "MAKE, "
                + "MODEL, "
                + "MODEL_YR,"
                + "VIN, "
                + "INCIDENT_DT, "
                + "DEATHS, "
                + "INJURIES,"
                + "STATE_OR_FCNTRY, "
                + "COMP_A, "
                + "COMP_B, "
                + "COMP_C, "
                + "COMP_D, "
                + "COMP_E, "
                + "TYPE_CD, "
                + "FUEL_PROPULSION_SYSTEM, "
                + "FILE_NAME "
                + ") "
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";//21 FIELDS
        
        try {
        	//LOOP THROUGH THE DI VEHICLES
			sqlStatement = conn.prepareStatement(insertSql);
			EwrLogs.DEBUG("RDSUtils", "persistDeathsAndInjuries", "About to persist " + reportInfo.getDeathsInjuries().size() + " records.");
			for(DiRecord diVehicle: reportInfo.getDeathsInjuries())
			{

				sqlStatement.setInt(1, reportInfo.getEwrId());
				sqlStatement.setInt(2, reportInfo.getEwrReportId());
				sqlStatement.setString(3, diVehicle.getManUniqueId());
				sqlStatement.setInt(4, diVehicle.getSeqId());
				sqlStatement.setInt(5, diVehicle.getSeqId()+1);//We believe this record id is used to identify the row number on the Excel file. 
				sqlStatement.setString(6, diVehicle.getMake());
				sqlStatement.setString(7, diVehicle.getModel());
				sqlStatement.setString(8, diVehicle.getModelYear());//In Child Restraint this is ProdYear; set on EwrUtils.mapReportInfoDeathAndInjuryXMLData()
				sqlStatement.setString(9, diVehicle.getVin());
				if(!diVehicle.getIncidentDate().trim().isEmpty()) {
					sqlStatement.setDate(10, java.sql.Date.valueOf(diVehicle.getIncidentDate())); 
				}else{
					sqlStatement.setDate(10, null);
				}
				sqlStatement.setInt(11, diVehicle.getNumDeaths());
				sqlStatement.setInt(12, diVehicle.getNumInjuries());
				sqlStatement.setString(13, diVehicle.getStateOrFCntry());
				sqlStatement.setString(14, diVehicle.getSysOrCompA());
				sqlStatement.setString(15, diVehicle.getSysOrCompB());
				sqlStatement.setString(16, diVehicle.getSysOrCompC());
				sqlStatement.setString(17, diVehicle.getSysOrCompD());
				sqlStatement.setString(18, diVehicle.getSysOrCompE());
				sqlStatement.setString(19, diVehicle.getType());
				sqlStatement.setString(20, diVehicle.getFuelPropulsionSystem());
				sqlStatement.setString(21, reportInfo.getFileName());
				
				//AT THE END OF EACH LOOP ADD TO THE BATCH
				sqlStatement.addBatch();
			}
			results = sqlStatement.executeBatch();
			insertedRecords = results.length;
			
		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "persistDeathsAndInjuries", "SQLException - " + e.getMessage());
		}finally {
			if(sqlStatement != null) {
				try {
					sqlStatement.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistDeathsAndInjuries", "SQLException - " + e.getMessage());
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistDeathsAndInjuries", "SQLException - " + e.getMessage());
				}
			}
		}
        
		return insertedRecords;
	}
	

	/**
	 * @author Bienvenido Ortiz
	*/
	public static int persistDeathsAndInjuriesForEquipment(ReportInfo reportInfo) {
		EwrLogs.DEBUG("RDSUtils", "persistDeathsAndInjuriesForEquipment", "Entering Method");
		int[] results;
		int insertedRecords = 0;
		Connection conn = RDSUtils.getDBConnection();
		PreparedStatement sqlStatement = null;
		
        String insertSql = "INSERT INTO EWR.EWR_STG_DEATH_INJURY_EQUIP ("   
                + "EWR_ID, "
                + "EWR_REPORT_ID, "
                + "MFR_UNIQUE_ID, "
                + "DI_ID,"
                + "RECORD_ID, "
                + "MAKE, "
                + "MODEL, "
                + "MODEL_YR,"
                + "PRODUCT_IDENTIFIER, "
                + "INCIDENT_DT, "
                + "DEATHS, "
                + "INJURIES,"
                + "STATE_OR_FCNTRY, "
                + "COMP_A, "
                + "COMP_B, "
                + "COMP_C, "
                + "COMP_D, "
                + "COMP_E, "
                + "FILE_NAME "
                + ") "
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";//19 FIELDS
        
        try {
        	//LOOP THROUGH THE DI EQUIPEMENT
			sqlStatement = conn.prepareStatement(insertSql);
			EwrLogs.DEBUG("RDSUtils", "persistDeathsAndInjuriesForEquipment", "About to persist " + reportInfo.getDeathsInjuriesEquipment().size() + " records.");
			for(DeathInjuryEquipment diEquipment: reportInfo.getDeathsInjuriesEquipment())
			{

				sqlStatement.setInt(1, reportInfo.getEwrId());
				sqlStatement.setInt(2, reportInfo.getEwrReportId());
				sqlStatement.setString(3, diEquipment.getManUniqueId());
				sqlStatement.setInt(4, diEquipment.getSeqId());
				sqlStatement.setInt(5, diEquipment.getSeqId()+1);//We believe this record id is used to identify the row number on the Excel file. 
				sqlStatement.setString(6, diEquipment.getMake());
				sqlStatement.setString(7, diEquipment.getModel());
				sqlStatement.setString(8, diEquipment.getModelYear());
				sqlStatement.setString(9, diEquipment.getProductIdentifier());
				if(!diEquipment.getIncidentDate().trim().isEmpty()) {
					sqlStatement.setDate(10, java.sql.Date.valueOf(diEquipment.getIncidentDate())); 
				}else{
					sqlStatement.setDate(10, null);
				}
				sqlStatement.setInt(11, diEquipment.getNumDeaths());
				sqlStatement.setInt(12, diEquipment.getNumInjuries());
				sqlStatement.setString(13, diEquipment.getStateOrFCntry());
				sqlStatement.setString(14, diEquipment.getSysOrCompA());
				sqlStatement.setString(15, diEquipment.getSysOrCompB());
				sqlStatement.setString(16, diEquipment.getSysOrCompC());
				sqlStatement.setString(17, diEquipment.getSysOrCompD());
				sqlStatement.setString(18, diEquipment.getSysOrCompE());
				sqlStatement.setString(19, reportInfo.getFileName());
				
				//AT THE END OF EACH LOOP ADD TO THE BATCH
				sqlStatement.addBatch();
			}
			results = sqlStatement.executeBatch();
			insertedRecords = results.length;
			
		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "persistDeathsAndInjuriesForEquipment", "SQLException - " + e.getMessage());
		}finally {
			if(sqlStatement != null) {
				try {
					sqlStatement.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistDeathsAndInjuriesForEquipment", "SQLException - " + e.getMessage());
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistDeathsAndInjuriesForEquipment", "SQLException - " + e.getMessage());
				}
			}
		}
        
		return insertedRecords;
	}
	
	
	/**
	 * @author Bienvenido Ortiz
	*/
	public static int persistDeathsAndInjuriesForTires(ReportInfo reportInfo) {
		EwrLogs.DEBUG("RDSUtils", "persistDeathsAndInjuriesForTires", "Entering Method");
		int[] results;
		int insertedRecords = 0;
		Connection conn = RDSUtils.getDBConnection();
		PreparedStatement sqlStatement = null;
		
        String insertSql = "INSERT INTO EWR.EWR_STG_DEATH_INJURY_TIRE ("   
                + "EWR_ID, "
                + "EWR_REPORT_ID, "
                + "MFR_UNIQUE_ID, "
                + "DI_ID,"
                + "RECORD_ID, "
                + "TIRE_LINE, "
                + "TIRE_SIZE, "
                + "SKU, "
                + "TIRE_PRODUCTION_YR, "
                + "TIN, "
                + "INCIDENT_DT, "
                + "DEATHS, "
                + "INJURIES,"
                + "STATE_OR_FCNTRY, "
                + "VEH_MAKE, "
                + "VEH_MODEL, "
                + "VEH_MODEL_YR, "
                + "COMP_A, "
                + "COMP_B, "
                + "COMP_C, "
                + "COMP_D, "
                + "COMP_E, "
                + "FILE_NAME "
                + ") "
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";//23 FIELDS
        
        try {
        	//LOOP THROUGH THE DI TIRES
			sqlStatement = conn.prepareStatement(insertSql);
			EwrLogs.DEBUG("RDSUtils", "persistDeathsAndInjuriesForTires", "About to persist " + reportInfo.getDeathsInjuriesTire().size() + " records.");
			for(DeathInjuryTire diTire: reportInfo.getDeathsInjuriesTire())
			{

				sqlStatement.setInt(1, reportInfo.getEwrId());
				sqlStatement.setInt(2, reportInfo.getEwrReportId());
				sqlStatement.setString(3, diTire.getManUniqueId());
				sqlStatement.setInt(4, diTire.getSeqId());
				sqlStatement.setInt(5, diTire.getSeqId()+1);//We believe this record id is used to identify the row number on the Excel file. 
				sqlStatement.setString(6, diTire.getTireLine());
				sqlStatement.setString(7, diTire.getTireSize());
				sqlStatement.setString(8, diTire.getSku());
				sqlStatement.setString(9, diTire.getProdYear());
				sqlStatement.setString(10, diTire.getTin());
				if(!diTire.getIncidentDate().trim().isEmpty()) {
					sqlStatement.setDate(11, java.sql.Date.valueOf(diTire.getIncidentDate())); 
				}else{
					sqlStatement.setDate(11, null);
				}
				sqlStatement.setInt(12, diTire.getNumDeaths());
				sqlStatement.setInt(13, diTire.getNumInjuries());
				sqlStatement.setString(14, diTire.getStateOrFCntry());
				sqlStatement.setString(15, diTire.getVehicleMake());
				sqlStatement.setString(16, diTire.getVehicleModel());
				sqlStatement.setString(17, diTire.getVehicleModelYear());
				sqlStatement.setString(18, diTire.getCompA());
				sqlStatement.setString(19, diTire.getCompB());
				sqlStatement.setString(20, diTire.getCompC());
				sqlStatement.setString(21, diTire.getCompD());
				sqlStatement.setString(22, diTire.getCompE());
				sqlStatement.setString(23, reportInfo.getFileName());
				
				//AT THE END OF EACH LOOP ADD TO THE BATCH
				sqlStatement.addBatch();
			}
			results = sqlStatement.executeBatch();
			insertedRecords = results.length;
			
		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "persistDeathsAndInjuriesForTires", "SQLException - " + e.getMessage());
		}finally {
			if(sqlStatement != null) {
				try {
					sqlStatement.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistDeathsAndInjuriesForTires", "SQLException - " + e.getMessage());
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistDeathsAndInjuriesForTires", "SQLException - " + e.getMessage());
				}
			}
		}
        
		return insertedRecords;
	}
	

	/**
	 * @author Bienvenido Ortiz
	*/
	public static int persistSubstantiallySimilarVehicles(ReportInfo reportInfo) {
		EwrLogs.DEBUG("RDSUtils", "persistSubstantiallySimilarVehicles", "Entering Method");
		int[] results;
		int insertedRecords = 0;
		Connection conn = RDSUtils.getDBConnection();
		PreparedStatement sqlStatement = null;
		
        String insertSql = "INSERT INTO EWR.PRODUCTS_STG_FOREIGN ("   
                + "EWR_ID, "
                + "EWR_REPORT_ID, "
                + "RECORD_ID, "
                + "FOREIGN_MAKE, "
                + "FOREIGN_MODEL, "
                + "FOREIGN_MODEL_YR, "
                + "MAKE, "
                + "MODEL, "
                + "MODEL_YR, "
                + "FOREIGN_MARKETS, "
                + "FOREIGN_MFR,"
                + "FR_PROD_ID, "
                + "FILE_NAME "
                + ") "
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?)";//13 FIELDS
        
        try {
        	//LOOP THROUGH THE DI TIRES
			sqlStatement = conn.prepareStatement(insertSql);
			EwrLogs.DEBUG("RDSUtils", "persistSubstantiallySimilarVehicles", "About to persist " + reportInfo.getSimilarVehicles().size() + " records.");
			for(SimilarVehicles aSimilarVehicle: reportInfo.getSimilarVehicles())
			{

				
				sqlStatement.setInt(1, reportInfo.getEwrId());
				sqlStatement.setInt(2, reportInfo.getEwrReportId());
				sqlStatement.setInt(3, aSimilarVehicle.getEwrRecordId());//I need to identify what is this (RECORD_ID). We are using a counter for now since this value is not coming form the XML
				sqlStatement.setString(4, aSimilarVehicle.getForeignMake());
				sqlStatement.setString(5, aSimilarVehicle.getForeignModel()); 
				sqlStatement.setString(6, aSimilarVehicle.getForeignModelYear());
				sqlStatement.setString(7, aSimilarVehicle.getMake());
				sqlStatement.setString(8, aSimilarVehicle.getModel());
				sqlStatement.setString(9, aSimilarVehicle.getModelYear());
				sqlStatement.setString(10, aSimilarVehicle.getForeignMarkets());
				sqlStatement.setString(11, aSimilarVehicle.getForeignManufacturer());
				sqlStatement.setInt(12, getNextSeqID("SEQ_FR_PROD_ID"));
				sqlStatement.setString(13, reportInfo.getFileName());
				
				//AT THE END OF EACH LOOP ADD TO THE BATCH
				sqlStatement.addBatch();
			}
			results = sqlStatement.executeBatch();
			insertedRecords = results.length;
			
		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "persistSubstantiallySimilarVehicles", "SQLException - " + e.getMessage());
		}finally {
			if(sqlStatement != null) {
				try {
					sqlStatement.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistSubstantiallySimilarVehicles", "SQLException - " + e.getMessage());
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "persistSubstantiallySimilarVehicles", "SQLException - " + e.getMessage());
				}
			}
		}
        
		return insertedRecords;
	}

	
	
	/**
	 * @author Bienvenido Ortiz
	 */
	private static int getEwrReportId(Statement statement, Integer ewrId) {
		ResultSet resultSet;
		int nextEwrReportId = 1;
		String query = "select max(efri.ewr_report_id) + 1 as next_ewr_report_id" + " from ewr.ewr_f_report_info efri"
				+ " where efri.ewr_id =" + ewrId;

		//context.getLogger().log("ABOUT TO RUN QUERY: " + query.toString().toUpperCase() + "\n");
		try {
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				nextEwrReportId = (resultSet.getInt("next_ewr_report_id"));
			}

		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "getEwrReportId", "SQLException - " + e.getMessage());
		}

		return nextEwrReportId;
	}	
	
	
	/**
	 * @author Bienvenido Ortiz
	 */
	public static String deleteReportInfoData(ReportInfo reportInfo) {
		EwrLogs.DEBUG("RDSUtils", "deleteReportInfoData", "Entering Method");
		Connection conn = RDSUtils.getDBConnection();
		int affectedRecord = 0;
		String result = "FAIL";
		Statement statement = null;
		PreparedStatement sqlStatement = null;
		
		try {
			statement = conn.createStatement();
		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "deleteReportInfoData", e.getMessage());
		}
		
		
        String deleteSql = "DELETE FROM EWR.EWR_STG_REPORT_INFO WHERE EWR_ID = " + reportInfo.getEwrId() +";" ;
        
        try {

        	sqlStatement = conn.prepareStatement(deleteSql);
            affectedRecord = sqlStatement.executeUpdate();
            if(affectedRecord > 0) { //Or == 1
            	result = "SUCCESS";
            }
            EwrLogs.DEBUG("RDSUtils", "deleteReportInfoData", "STATUS FOR DELETION OF REPORT INFO TAB DATA FOR EWR_ID: " + reportInfo.getEwrId() + " [" + result + "]");
            			
		} catch (SQLException e) {
			EwrLogs.ERROR("RDSUtils", "deleteReportInfoData", e.getMessage());
			result = e.getMessage();
		}finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "deleteReportInfoData", e.getMessage());
				}
			}
			if(sqlStatement != null) {
				try {
					sqlStatement.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "deleteReportInfoData", e.getMessage());
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					EwrLogs.ERROR("RDSUtils", "deleteReportInfoData", e.getMessage());
				}
			}
		}
        
		return result;
	}	
	
	
    /**
     * @author Bienvenido.Ortiz.CTR
     * Method that gets the next sequence ID for the a given sequence name
     * @return nextVal An int with the next value of the sequence
    */
    public static int getNextSeqID(String sequenceName){
        int nextVal = 0;
        Statement stmt  = null;
        Connection conn = null;
        ResultSet rs    = null;
        try {
            conn = getDBConnection(); //SEQ_FR_PROD_ID
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT NEXTVAL('EWR."+sequenceName+"');");
            
            if (rs.next()) {
                nextVal = rs.getInt(1);
                rs.close();
                stmt.close();
            }

        } catch (SQLException ex) {
        	EwrLogs.ERROR("RDSUtils", "getNextSeqID",  ex.getMessage());
        }finally{
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                	EwrLogs.ERROR("RDSUtils", "getNextSeqID",  ex.getMessage());
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                	EwrLogs.ERROR("RDSUtils", "getNextSeqID",  ex.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {
                	EwrLogs.ERROR("RDSUtils", "getNextSeqID",  ignore.getMessage());
                }
            }
        }
        return nextVal;
    } 	
	
}
