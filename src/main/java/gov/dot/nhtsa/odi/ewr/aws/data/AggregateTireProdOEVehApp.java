package gov.dot.nhtsa.odi.ewr.aws.data;

/*
 * @author Bienvenido Ortiz
 */
public class AggregateTireProdOEVehApp {

	
	/*
	 * This object is used for the following aggregate reports:
	 * 
	 * Tires 
	 * 
	 * This object maps to the Excel Tab and XML Tag "ProdOEVehApp".
	 * Data from this tab/tag maps to the EWR_STG_PRODOE_VEHAPP table.
	 * 
	 * 
	 <ProdOEVehApp>
		<PovTire>
  			<SKU>9N</SKU>
			<VehicleMake>KIA MOTORS</VehicleMake>
			<VehicleModel>SOUL</VehicleModel>
			<VehicleModelYear>2009</VehicleModelYear>
 		</PovTire>
 	 <ProdOEVehApp>
	 ***/
	
	private int ewrId;
	private int ewrReportId;
	private int recordId; // SEQ ID + 1 - but we don't have sequence ID on the aggregate reports, I will
							// need to use a counter for this
	private String sku;
	private String make;
	private String model;
	private String modelYear;
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
	 * @return the sku
	 */
	public String getSku() {
		return sku;
	}

	/**
	 * @param sku
	 *            the sku to set
	 */
	public void setSku(String sku) {
		this.sku = sku;
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
		return "AggregateTireProdOEVehApp [ewrId=" + ewrId + ", ewrReportId=" + ewrReportId + ", recordId=" + recordId
				+ ", sku=" + sku + ", make=" + make + ", model=" + model + ", modelYear=" + modelYear + ", fileName="
				+ fileName + "]";
	}

}
