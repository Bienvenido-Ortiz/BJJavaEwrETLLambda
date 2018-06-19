package gov.dot.nhtsa.odi.ewr.aws.data;

public class SimilarVehicles {

	/*
	 * 
	 * BJ, there is a table called products_stg_foreign in EWR oracle database.
	 * 
	 * DESC PRODUCTS_STG_FOREIGN; Name Null? Type
	 * ----------------------------------------- --------
	 * ----------------------------
	 * 
	 * EWR_ID NOT NULL NUMBER(6) EWR_REPORT_ID NOT NULL NUMBER(9) RECORD_ID NOT NULL
	 * NUMBER(9) FOREIGN_MAKE NOT NULL VARCHAR2(25) FOREIGN_MODEL NOT NULL
	 * VARCHAR2(25) FOREIGN_MODEL_YR NOT NULL VARCHAR2(4) MAKE NOT NULL VARCHAR2(25)
	 * MODEL NOT NULL VARCHAR2(25) MODEL_YR NOT NULL VARCHAR2(4) FOREIGN_MARKETS
	 * VARCHAR2(2048) FOREIGN_MFR VARCHAR2(40) FR_PROD_ID NUMBER(9) --We believe
	 * this is a SEQ [SEQ_FR_PROD_ID] on the DB, it is not coming from the XML
	 * 
	 * FILE_NAME added later This object would be used on the ReportName:
	 * SubstantiallySimilarVehicles
	 **/
	private int ewrId;
	private int ewrReportId;
	private int ewrRecordId;
	private String make;
	private String model;
	private String modelYear;
	private String foreignMake;
	private String foreignModel;
	private String foreignModelYear;
	private String foreignMarkets;
	private String foreignManufacturer;
	private int foreignProductId;
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
	 * @return the ewrRecordId
	 */
	public int getEwrRecordId() {
		return ewrRecordId;
	}

	/**
	 * @param ewrRecordId
	 *            the ewrRecordId to set
	 */
	public void setEwrRecordId(int ewrRecordId) {
		this.ewrRecordId = ewrRecordId;
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
	 * @return the foreignMake
	 */
	public String getForeignMake() {
		return foreignMake;
	}

	/**
	 * @param foreignMake
	 *            the foreignMake to set
	 */
	public void setForeignMake(String foreignMake) {
		this.foreignMake = foreignMake;
	}

	/**
	 * @return the foreignModel
	 */
	public String getForeignModel() {
		return foreignModel;
	}

	/**
	 * @param foreignModel
	 *            the foreignModel to set
	 */
	public void setForeignModel(String foreignModel) {
		this.foreignModel = foreignModel;
	}

	/**
	 * @return the foreignModelYear
	 */
	public String getForeignModelYear() {
		return foreignModelYear;
	}

	/**
	 * @param foreignModelYear
	 *            the foreignModelYear to set
	 */
	public void setForeignModelYear(String foreignModelYear) {
		this.foreignModelYear = foreignModelYear;
	}

	/**
	 * @return the foreignMarkets
	 */
	public String getForeignMarkets() {
		return foreignMarkets;
	}

	/**
	 * @param foreignMarkets
	 *            the foreignMarkets to set
	 */
	public void setForeignMarkets(String foreignMarkets) {
		this.foreignMarkets = foreignMarkets;
	}

	/**
	 * @return the foreignManufacturer
	 */
	public String getForeignManufacturer() {
		return foreignManufacturer;
	}

	/**
	 * @param foreignManufacturer
	 *            the foreignManufacturer to set
	 */
	public void setForeignManufacturer(String foreignManufacturer) {
		this.foreignManufacturer = foreignManufacturer;
	}

	/**
	 * @return the foreignProductId
	 */
	public int getForeignProductId() {
		return foreignProductId;
	}

	/**
	 * @param foreignProductId
	 *            the foreignProductId to set
	 */
	public void setForeignProductId(int foreignProductId) {
		this.foreignProductId = foreignProductId;
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
		return "SimilarVehicles [ewrId=" + ewrId + ", ewrReportId=" + ewrReportId + ", ewrRecordId=" + ewrRecordId
				+ ", make=" + make + ", model=" + model + ", modelYear=" + modelYear + ", foreignMake=" + foreignMake
				+ ", foreignModel=" + foreignModel + ", foreignModelYear=" + foreignModelYear + ", foreignMarkets="
				+ foreignMarkets + ", foreignManufacturer=" + foreignManufacturer + ", foreignProductId="
				+ foreignProductId + ", fileName=" + fileName + "]";
	}

}
