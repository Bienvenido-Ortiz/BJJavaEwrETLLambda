package gov.dot.nhtsa.odi.ewr.aws.data;

import java.util.List;

public class ReportInfo {

	int ewrId;
	int ewrReportId;
	private String fileName;
	private String reportType; //A or D
	private String reportCategory;
	private String manufacturerName;
	private Integer reportQuarter;
	private Integer reportYear;
	private String reportName;
	private String reportVersion;
	private String reportGeneratedDate;
	private String reportContactName;
	private String reportContactEmail;
	private String reportContactPhone;
	private String nhtsaTemplateRevisionNo;
	//Death and Injury Related Data
	private List<DiRecord> deathsInjuries;
	private List<DeathInjuryTire> deathsInjuriesTire;
	private List<DeathInjuryEquipment> deathsInjuriesEquipment;
	private List<SimilarVehicles> similarVehicles;

	//Aggregate 
	private List<AggregateProduction> aggregateProduction; //Common for all Aggregate Data reports, but Tires
	//Aggregate Data
	private List<AggregateData> aggregateDataConsumerComplaints;
	private List<AggregateData> aggregateDataPropertyDamage;
	private List<AggregateData> aggregateDataWarrantyClaims;
	private List<AggregateData> aggregateDataFieldReports;
	
	//Aggregate Tires
	private List<AggregateTireProduction> aggregateTireProduction;
	private List<AggregateTireProdSkuTypeCode> aggregateTireProdSkuTypeCode;
	private List<AggregateTireProdOEVehApp> aggregateTireProdOEVehApp;
	private List<AggregateTirePropertyDamageAndWarrantyAdjustments> aggregateTirePropertyDamage;
	private List<AggregateTirePropertyDamageAndWarrantyAdjustments> aggregateTireWarrantyAdjustments;
	private List<AggregateTireCommonGreen> aggregateTireCommonGreen;
	
	
	public int getEwrId() {
		return ewrId;
	}

	public void setEwrId(int ewrId) {
		this.ewrId = ewrId;
	}

	public int getEwrReportId() {
		return ewrReportId;
	}

	public void setEwrReportId(int ewrReportId) {
		this.ewrReportId = ewrReportId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportCategory() {
		return reportCategory;
	}

	public void setReportCategory(String reportCategory) {
		this.reportCategory = reportCategory;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public Integer getReportQuarter() {
		return reportQuarter;
	}

	public void setReportQuarter(Integer reportQuarter) {
		this.reportQuarter = reportQuarter;
	}

	public Integer getReportYear() {
		return reportYear;
	}

	public void setReportYear(Integer reportYear) {
		this.reportYear = reportYear;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportVersion() {
		return reportVersion;
	}

	public void setReportVersion(String reportVersion) {
		this.reportVersion = reportVersion;
	}

	public String getReportGeneratedDate() {
		return reportGeneratedDate;
	}

	public void setReportGeneratedDate(String reportGeneratedDate) {
		this.reportGeneratedDate = reportGeneratedDate;
	}

	public String getReportContactName() {
		return reportContactName;
	}

	public void setReportContactName(String reportContactName) {
		this.reportContactName = reportContactName;
	}

	public String getReportContactEmail() {
		return reportContactEmail;
	}

	public void setReportContactEmail(String reportContactEmail) {
		this.reportContactEmail = reportContactEmail;
	}

	public String getReportContactPhone() {
		return reportContactPhone;
	}

	public void setReportContactPhone(String reportContactPhone) {
		this.reportContactPhone = reportContactPhone;
	}

	public String getNhtsaTemplateRevisionNo() {
		return nhtsaTemplateRevisionNo;
	}

	public void setNhtsaTemplateRevisionNo(String nhtsaTemplateRevisionNo) {
		this.nhtsaTemplateRevisionNo = nhtsaTemplateRevisionNo;
	}

	public List<DiRecord> getDeathsInjuries() {
		return deathsInjuries;
	}

	public void setDeathsInjuries(List<DiRecord> deathsInjuries) {
		this.deathsInjuries = deathsInjuries;
	}

	public List<DeathInjuryTire> getDeathsInjuriesTire() {
		return deathsInjuriesTire;
	}

	public void setDeathsInjuriesTire(List<DeathInjuryTire> deathsInjuriesTire) {
		this.deathsInjuriesTire = deathsInjuriesTire;
	}

	public List<DeathInjuryEquipment> getDeathsInjuriesEquipment() {
		return deathsInjuriesEquipment;
	}

	public void setDeathsInjuriesEquipment(List<DeathInjuryEquipment> deathsInjuriesEquipment) {
		this.deathsInjuriesEquipment = deathsInjuriesEquipment;
	}

	public List<SimilarVehicles> getSimilarVehicles() {
		return similarVehicles;
	}

	public void setSimilarVehicles(List<SimilarVehicles> similarVehicles) {
		this.similarVehicles = similarVehicles;
	}

	public List<AggregateProduction> getAggregateProduction() {
		return aggregateProduction;
	}

	public void setAggregateProduction(List<AggregateProduction> aggregateProduction) {
		this.aggregateProduction = aggregateProduction;
	}

	public List<AggregateData> getAggregateDataConsumerComplaints() {
		return aggregateDataConsumerComplaints;
	}

	public void setAggregateDataConsumerComplaints(List<AggregateData> aggregateDataConsumerComplaints) {
		this.aggregateDataConsumerComplaints = aggregateDataConsumerComplaints;
	}

	public List<AggregateData> getAggregateDataPropertyDamage() {
		return aggregateDataPropertyDamage;
	}

	public void setAggregateDataPropertyDamage(List<AggregateData> aggregateDataPropertyDamage) {
		this.aggregateDataPropertyDamage = aggregateDataPropertyDamage;
	}

	public List<AggregateData> getAggregateDataWarrantyClaims() {
		return aggregateDataWarrantyClaims;
	}

	public void setAggregateDataWarrantyClaims(List<AggregateData> aggregateDataWarrantyClaims) {
		this.aggregateDataWarrantyClaims = aggregateDataWarrantyClaims;
	}

	public List<AggregateData> getAggregateDataFieldReports() {
		return aggregateDataFieldReports;
	}

	public void setAggregateDataFieldReports(List<AggregateData> aggregateDataFieldReports) {
		this.aggregateDataFieldReports = aggregateDataFieldReports;
	}

	public List<AggregateTireProduction> getAggregateTireProduction() {
		return aggregateTireProduction;
	}

	public void setAggregateTireProduction(List<AggregateTireProduction> aggregateTireProduction) {
		this.aggregateTireProduction = aggregateTireProduction;
	}

	public List<AggregateTireProdSkuTypeCode> getAggregateTireProdSkuTypeCode() {
		return aggregateTireProdSkuTypeCode;
	}

	public void setAggregateTireProdSkuTypeCode(List<AggregateTireProdSkuTypeCode> aggregateTireProdSkuTypeCode) {
		this.aggregateTireProdSkuTypeCode = aggregateTireProdSkuTypeCode;
	}

	public List<AggregateTireProdOEVehApp> getAggregateTireProdOEVehApp() {
		return aggregateTireProdOEVehApp;
	}

	public void setAggregateTireProdOEVehApp(List<AggregateTireProdOEVehApp> aggregateTireProdOEVehApp) {
		this.aggregateTireProdOEVehApp = aggregateTireProdOEVehApp;
	}

	public List<AggregateTirePropertyDamageAndWarrantyAdjustments> getAggregateTirePropertyDamage() {
		return aggregateTirePropertyDamage;
	}

	public void setAggregateTirePropertyDamage(
			List<AggregateTirePropertyDamageAndWarrantyAdjustments> aggregateTirePropertyDamage) {
		this.aggregateTirePropertyDamage = aggregateTirePropertyDamage;
	}

	public List<AggregateTirePropertyDamageAndWarrantyAdjustments> getAggregateTireWarrantyAdjustments() {
		return aggregateTireWarrantyAdjustments;
	}

	public void setAggregateTireWarrantyAdjustments(
			List<AggregateTirePropertyDamageAndWarrantyAdjustments> aggregateTireWarrantyAdjustments) {
		this.aggregateTireWarrantyAdjustments = aggregateTireWarrantyAdjustments;
	}

	public List<AggregateTireCommonGreen> getAggregateTireCommonGreen() {
		return aggregateTireCommonGreen;
	}

	public void setAggregateTireCommonGreen(List<AggregateTireCommonGreen> aggregateTireCommonGreen) {
		this.aggregateTireCommonGreen = aggregateTireCommonGreen;
	}

	@Override
	public String toString() {
		return "ReportInfo [ewrId=" + ewrId + ", ewrReportId=" + ewrReportId + ", fileName=" + fileName
				+ ", reportType=" + reportType + ", reportCategory=" + reportCategory + ", manufacturerName="
				+ manufacturerName + ", reportQuarter=" + reportQuarter + ", reportYear=" + reportYear + ", reportName="
				+ reportName + ", reportVersion=" + reportVersion + ", reportGeneratedDate=" + reportGeneratedDate
				+ ", reportContactName=" + reportContactName + ", reportContactEmail=" + reportContactEmail
				+ ", reportContactPhone=" + reportContactPhone + ", nhtsaTemplateRevisionNo=" + nhtsaTemplateRevisionNo
				+ ", deathsInjuries=" + deathsInjuries + ", deathsInjuriesTire=" + deathsInjuriesTire
				+ ", deathsInjuriesEquipment=" + deathsInjuriesEquipment + ", similarVehicles=" + similarVehicles
				+ ", aggregateProduction=" + aggregateProduction + ", aggregateDataConsumerComplaints="
				+ aggregateDataConsumerComplaints + ", aggregateDataPropertyDamage=" + aggregateDataPropertyDamage
				+ ", aggregateDataWarrantyClaims=" + aggregateDataWarrantyClaims + ", aggregateDataFieldReports="
				+ aggregateDataFieldReports + ", aggregateTireProduction=" + aggregateTireProduction
				+ ", aggregateTireProdSkuTypeCode=" + aggregateTireProdSkuTypeCode + ", aggregateTireProdOEVehApp="
				+ aggregateTireProdOEVehApp + ", aggregateTirePropertyDamage=" + aggregateTirePropertyDamage
				+ ", aggregateTireWarrantyAdjustments=" + aggregateTireWarrantyAdjustments
				+ ", aggregateTireCommonGreen=" + aggregateTireCommonGreen + "]";
	}

}
