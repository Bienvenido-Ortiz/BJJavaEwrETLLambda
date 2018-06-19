package gov.dot.nhtsa.odi.ewr.aws.data;

public class ReportNameAttributes {

	
	/*
	  
	000003L173001DP.xml	
	
	000003 	- EWR ID			- first 6 characters - a number - zeros would be removed
	L		- Report Category	- 7th char
	17		- Report Year		- 8th and 9th characters
	3		- Report Quarter	- 10th 
	001 	- Version			- 11th, 12th and 13th characters
	D		- Report Type		- 14th char - A= Agg; D= DEATH & I;  S=Substantially Similar
	P		- Confidentiality 	- 15th char	  
	 
	 * */
	
	private int ewrId;
	/* VEHICLES
	 * L = Light Vehicles 
	 * H = Buses and Medium Heavy Vehicles
	 * Z = Low Volume Vehicles
	 * Y = Trailers
	 * 
	 * C = Child Restraints 
	 * E = Equipment 
	 * M = MotorCycles
	 * T = Tires
	 *  
	 * */
	private String reportCategory;   
	private int reportYear; //17 - two digits
	private int reportQuarter; //1..4
	private String reportVersion; //001
	/*
	 * D = Death & Injury
	 * A = Aggregate 
	 * S = Substantially Similar Vehicles
	 * */
	private String reportType; 
	/*
	 * P = Public
	 * C = Confidential
	 * */
	private String confidentiality; 
	private String xsdName;

	
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
	 * @return the reportCategory
	 */
	public String getReportCategory() {
		return reportCategory;
	}

	/**
	 * @param reportCategory
	 *            the reportCategory to set
	 */
	public void setReportCategory(String reportCategory) {
		this.reportCategory = reportCategory;
	}

	/**
	 * @return the reportYear
	 */
	public int getReportYear() {
		return reportYear;
	}

	/**
	 * @param reportYear
	 *            the reportYear to set
	 */
	public void setReportYear(int reportYear) {
		this.reportYear = reportYear;
	}

	/**
	 * @return the reportQuarter
	 */
	public int getReportQuarter() {
		return reportQuarter;
	}

	/**
	 * @param reportQuarter
	 *            the reportQuarter to set
	 */
	public void setReportQuarter(int reportQuarter) {
		this.reportQuarter = reportQuarter;
	}

	/**
	 * @return the reportVersion
	 */
	public String getReportVersion() {
		return reportVersion;
	}

	/**
	 * @param reportVersion
	 *            the reportVersion to set
	 */
	public void setReportVersion(String reportVersion) {
		this.reportVersion = reportVersion;
	}

	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * @param reportType
	 *            the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	/**
	 * @return the confidentiality
	 */
	public String getConfidentiality() {
		return confidentiality;
	}

	/**
	 * @param confidentiality
	 *            the confidentiality to set
	 */
	public void setConfidentiality(String confidentiality) {
		this.confidentiality = confidentiality;
	}

	/**
	 * @return the xsdName
	 */
	public String getXsdName() {
		return xsdName;
	}

	/**
	 * @param xsdName
	 *            the xsdName to set
	 */
	public void setXsdName(String xsdName) {
		this.xsdName = xsdName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReportNameAttributes [ewrId=" + ewrId + ", reportCategory=" + reportCategory + ", reportYear=" + reportYear
				+ ", reportQuarter=" + reportQuarter + ", reportVersion=" + reportVersion + ", reportType=" + reportType
				+ ", confidentiality=" + confidentiality + ", xsdName=" + xsdName + "]";
	}

}
