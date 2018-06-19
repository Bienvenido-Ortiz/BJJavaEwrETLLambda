package gov.dot.nhtsa.odi.ewr.aws.data;

/*
 * @author Bienvenido Ortiz
 */
public class DeathInjuryTire {

	private Integer seqId;
	private int ewrId;
	private int ewrReportId;
	private String manUniqueId;
	private String tireLine;
	private String tireSize;
	private String sku;
	private String prodYear;
	private String tin;
	private String incidentDate;
	private Integer numDeaths;
	private Integer numInjuries;
	private String stateOrFCntry;
	private String vehicleMake;
	private String vehicleModel;
	private String vehicleModelYear;
	private String compA;
	private String compB;
	private String compC;
	private String compD;
	private String compE;
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
	 * @return the tireLine
	 */
	public String getTireLine() {
		return tireLine;
	}

	/**
	 * @param tireLine
	 *            the tireLine to set
	 */
	public void setTireLine(String tireLine) {
		this.tireLine = tireLine;
	}

	/**
	 * @return the tireSize
	 */
	public String getTireSize() {
		return tireSize;
	}

	/**
	 * @param tireSize
	 *            the tireSize to set
	 */
	public void setTireSize(String tireSize) {
		this.tireSize = tireSize;
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
	 * @return the prodYear
	 */
	public String getProdYear() {
		return prodYear;
	}

	/**
	 * @param prodYear
	 *            the prodYear to set
	 */
	public void setProdYear(String prodYear) {
		this.prodYear = prodYear;
	}

	/**
	 * @return the tin
	 */
	public String getTin() {
		return tin;
	}

	/**
	 * @param tin
	 *            the tin to set
	 */
	public void setTin(String tin) {
		this.tin = tin;
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
	 * @return the vehicleMake
	 */
	public String getVehicleMake() {
		return vehicleMake;
	}

	/**
	 * @param vehicleMake
	 *            the vehicleMake to set
	 */
	public void setVehicleMake(String vehicleMake) {
		this.vehicleMake = vehicleMake;
	}

	/**
	 * @return the vehicleModel
	 */
	public String getVehicleModel() {
		return vehicleModel;
	}

	/**
	 * @param vehicleModel
	 *            the vehicleModel to set
	 */
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	/**
	 * @return the vehicleModelYear
	 */
	public String getVehicleModelYear() {
		return vehicleModelYear;
	}

	/**
	 * @param vehicleModelYear
	 *            the vehicleModelYear to set
	 */
	public void setVehicleModelYear(String vehicleModelYear) {
		this.vehicleModelYear = vehicleModelYear;
	}

	/**
	 * @return the compA
	 */
	public String getCompA() {
		return compA;
	}

	/**
	 * @param compA
	 *            the compA to set
	 */
	public void setCompA(String compA) {
		this.compA = compA;
	}

	/**
	 * @return the compB
	 */
	public String getCompB() {
		return compB;
	}

	/**
	 * @param compB
	 *            the compB to set
	 */
	public void setCompB(String compB) {
		this.compB = compB;
	}

	/**
	 * @return the compC
	 */
	public String getCompC() {
		return compC;
	}

	/**
	 * @param compC
	 *            the compC to set
	 */
	public void setCompC(String compC) {
		this.compC = compC;
	}

	/**
	 * @return the compD
	 */
	public String getCompD() {
		return compD;
	}

	/**
	 * @param compD
	 *            the compD to set
	 */
	public void setCompD(String compD) {
		this.compD = compD;
	}

	/**
	 * @return the compE
	 */
	public String getCompE() {
		return compE;
	}

	/**
	 * @param compE
	 *            the compE to set
	 */
	public void setCompE(String compE) {
		this.compE = compE;
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
		return "DeathInjuryTire [seqId=" + seqId + ", ewrId=" + ewrId + ", ewrReportId=" + ewrReportId
				+ ", manUniqueId=" + manUniqueId + ", tireLine=" + tireLine + ", tireSize=" + tireSize + ", sku=" + sku
				+ ", prodYear=" + prodYear + ", tin=" + tin + ", incidentDate=" + incidentDate + ", numDeaths="
				+ numDeaths + ", numInjuries=" + numInjuries + ", stateOrFCntry=" + stateOrFCntry + ", vehicleMake="
				+ vehicleMake + ", vehicleModel=" + vehicleModel + ", vehicleModelYear=" + vehicleModelYear + ", compA="
				+ compA + ", compB=" + compB + ", compC=" + compC + ", compD=" + compD + ", compE=" + compE
				+ ", fileName=" + fileName + "]";
	}

}
