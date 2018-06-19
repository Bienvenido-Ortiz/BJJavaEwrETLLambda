package gov.dot.nhtsa.odi.ewr.aws.data;

/*
 * @author Bienvenido Ortiz
 */
public class DiRecord {

	/*
	 * This object is used for the following reports:
	 * 
	 * LightVehiclesDI 
	 * BusesAndMediumHeavyVehiclesDI 
	 * LowVolumeVehiclesDI 
	 * TrailersDI
	 * MotorCyclesDI 
	 * ChildRestraintsDI
	 * 
	 * They all shared the same attributes and persist to the same table
	 ***/

	private Integer seqId;
	private int ewrId;
	private int ewrReportId;
	private String manUniqueId;
	private String make;
	private String model;
	private String modelYear; // This would map to ProdYear in ChildRestraintsDI report
	private String type;
	private String fuelPropulsionSystem;
	private String vin;
	private String incidentDate;
	private Integer numDeaths;
	private Integer numInjuries;
	private String stateOrFCntry;
	private String sysOrCompA;
	private String sysOrCompB;
	private String sysOrCompC;
	private String sysOrCompD;
	private String sysOrCompE;
	private String fileName;

	/**
	 * @return the seqId
	 */
	public Integer getSeqId() {
		return seqId;
	}

	/**
	 * @param seqId
	 *            the seqId to set
	 */
	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}

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
	 * @return the manUniqueId
	 */
	public String getManUniqueId() {
		return manUniqueId;
	}

	/**
	 * @param manUniqueId
	 *            the manUniqueId to set
	 */
	public void setManUniqueId(String manUniqueId) {
		this.manUniqueId = manUniqueId;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the vin
	 */
	public String getVin() {
		return vin;
	}

	/**
	 * @param vin
	 *            the vin to set
	 */
	public void setVin(String vin) {
		this.vin = vin;
	}

	/**
	 * @return the incidentDate
	 */
	public String getIncidentDate() {
		return incidentDate;
	}

	/**
	 * @param incidentDate
	 *            the incidentDate to set
	 */
	public void setIncidentDate(String incidentDate) {
		this.incidentDate = incidentDate;
	}

	/**
	 * @return the numDeaths
	 */
	public Integer getNumDeaths() {
		return numDeaths;
	}

	/**
	 * @param numDeaths
	 *            the numDeaths to set
	 */
	public void setNumDeaths(Integer numDeaths) {
		this.numDeaths = numDeaths;
	}

	/**
	 * @return the numInjuries
	 */
	public Integer getNumInjuries() {
		return numInjuries;
	}

	/**
	 * @param numInjuries
	 *            the numInjuries to set
	 */
	public void setNumInjuries(Integer numInjuries) {
		this.numInjuries = numInjuries;
	}

	/**
	 * @return the stateOrFCntry
	 */
	public String getStateOrFCntry() {
		return stateOrFCntry;
	}

	/**
	 * @param stateOrFCntry
	 *            the stateOrFCntry to set
	 */
	public void setStateOrFCntry(String stateOrFCntry) {
		this.stateOrFCntry = stateOrFCntry;
	}

	/**
	 * @return the sysOrCompA
	 */
	public String getSysOrCompA() {
		return sysOrCompA;
	}

	/**
	 * @param sysOrCompA
	 *            the sysOrCompA to set
	 */
	public void setSysOrCompA(String sysOrCompA) {
		this.sysOrCompA = sysOrCompA;
	}

	/**
	 * @return the sysOrCompB
	 */
	public String getSysOrCompB() {
		return sysOrCompB;
	}

	/**
	 * @param sysOrCompB
	 *            the sysOrCompB to set
	 */
	public void setSysOrCompB(String sysOrCompB) {
		this.sysOrCompB = sysOrCompB;
	}

	/**
	 * @return the sysOrCompC
	 */
	public String getSysOrCompC() {
		return sysOrCompC;
	}

	/**
	 * @param sysOrCompC
	 *            the sysOrCompC to set
	 */
	public void setSysOrCompC(String sysOrCompC) {
		this.sysOrCompC = sysOrCompC;
	}

	/**
	 * @return the sysOrCompD
	 */
	public String getSysOrCompD() {
		return sysOrCompD;
	}

	/**
	 * @param sysOrCompD
	 *            the sysOrCompD to set
	 */
	public void setSysOrCompD(String sysOrCompD) {
		this.sysOrCompD = sysOrCompD;
	}

	/**
	 * @return the sysOrCompE
	 */
	public String getSysOrCompE() {
		return sysOrCompE;
	}

	/**
	 * @param sysOrCompE
	 *            the sysOrCompE to set
	 */
	public void setSysOrCompE(String sysOrCompE) {
		this.sysOrCompE = sysOrCompE;
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
		return "DiRecord [seqId=" + seqId + ", ewrId=" + ewrId + ", ewrReportId=" + ewrReportId + ", manUniqueId="
				+ manUniqueId + ", make=" + make + ", model=" + model + ", modelYear=" + modelYear + ", type=" + type
				+ ", fuelPropulsionSystem=" + fuelPropulsionSystem + ", vin=" + vin + ", incidentDate=" + incidentDate
				+ ", numDeaths=" + numDeaths + ", numInjuries=" + numInjuries + ", stateOrFCntry=" + stateOrFCntry
				+ ", sysOrCompA=" + sysOrCompA + ", sysOrCompB=" + sysOrCompB + ", sysOrCompC=" + sysOrCompC
				+ ", sysOrCompD=" + sysOrCompD + ", sysOrCompE=" + sysOrCompE + ", fileName=" + fileName + "]";
	}

}
