package gov.dot.nhtsa.odi.ewr.aws.data;

/*
 * @author Bienvenido Ortiz
 */
public class AggregateProduction {

	
	/*
	 * This object is used for the following aggregate reports:
	 * 
	 * LightVehicles 
	 * BusesAndMediumHeavyVehicles
	 * Trailers
	 * MotorCycles 
	 * 
	 * ChildRestraints
	 * 
	 * They all shared the same attributes and persist to the same table. 
	 * Please note that there is NO aggregate data for Equipment or Low Volume Vehicles
	 * 
	 * This object maps to the Excel Tab and XML Tag "Production".
	 * Data from this tab/tag maps to the EWR_STG_PRODUCTION table.
	 
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
	 ***/
	
	private int ewrId;
	private int ewrReportId;
	private int recordId; // SEQ ID + 1 - but we don't have sequence ID on the aggregate reports, I will
							// need to use a counter for this
	private String make;
	private String model;
	private String modelYear;
	private String fuelSystem;
	private String breakSystem;
	private String typeCode;
	private String platform;
	private String totalProduction;
	private String fuelPropulsionSystem;
	private String fileName;

	/**
	 * @return the ewrId
	 */
	public int getEwrId() {
		return ewrId;
	}

	/**
	 * @param ewrId
	 *            the ewrId to set
	 */
	public void setEwrId(int ewrId) {
		this.ewrId = ewrId;
	}

	/**
	 * @return the ewrReportId
	 */
	public int getEwrReportId() {
		return ewrReportId;
	}

	/**
	 * @param ewrReportId
	 *            the ewrReportId to set
	 */
	public void setEwrReportId(int ewrReportId) {
		this.ewrReportId = ewrReportId;
	}

	/**
	 * @return the recordId
	 */
	public int getRecordId() {
		return recordId;
	}

	/**
	 * @param recordId
	 *            the recordId to set
	 */
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * @param make
	 *            the make to set
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the modelYear
	 */
	public String getModelYear() {
		return modelYear;
	}

	/**
	 * @param modelYear
	 *            the modelYear to set
	 */
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}

	/**
	 * @return the fuelSystem
	 */
	public String getFuelSystem() {
		return fuelSystem;
	}

	/**
	 * @param fuelSystem
	 *            the fuelSystem to set
	 */
	public void setFuelSystem(String fuelSystem) {
		this.fuelSystem = fuelSystem;
	}

	/**
	 * @return the breakSystem
	 */
	public String getBreakSystem() {
		return breakSystem;
	}

	/**
	 * @param breakSystem
	 *            the breakSystem to set
	 */
	public void setBreakSystem(String breakSystem) {
		this.breakSystem = breakSystem;
	}

	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * @param typeCode
	 *            the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}

	/**
	 * @param platform
	 *            the platform to set
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	/**
	 * @return the totalProduction
	 */
	public String getTotalProduction() {
		return totalProduction;
	}

	/**
	 * @param totalProduction
	 *            the totalProduction to set
	 */
	public void setTotalProduction(String totalProduction) {
		this.totalProduction = totalProduction;
	}

	/**
	 * @return the fuelPropulsionSystem
	 */
	public String getFuelPropulsionSystem() {
		return fuelPropulsionSystem;
	}

	/**
	 * @param fuelPropulsionSystem
	 *            the fuelPropulsionSystem to set
	 */
	public void setFuelPropulsionSystem(String fuelPropulsionSystem) {
		this.fuelPropulsionSystem = fuelPropulsionSystem;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AggregateProduction [ewrId=" + ewrId + ", ewrReportId=" + ewrReportId + ", recordId=" + recordId
				+ ", make=" + make + ", model=" + model + ", modelYear=" + modelYear + ", fuelSystem=" + fuelSystem
				+ ", breakSystem=" + breakSystem + ", typeCode=" + typeCode + ", platform=" + platform
				+ ", totalProduction=" + totalProduction + ", fuelPropulsionSystem=" + fuelPropulsionSystem
				+ ", fileName=" + fileName + "]";
	}
	
}
