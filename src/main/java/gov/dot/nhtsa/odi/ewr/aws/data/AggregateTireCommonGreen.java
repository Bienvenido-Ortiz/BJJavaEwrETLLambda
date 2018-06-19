package gov.dot.nhtsa.odi.ewr.aws.data;

/*
 * @author Bienvenido Ortiz
 */
public class AggregateTireCommonGreen {

	
	/*
	 * This object is used for the following aggregate reports:
	 * 
	 * Tires 
	 * 
	 * This object maps to the Excel Tab and XML Tag "CommonGreen". 
	 * 
	 * Data from this tab/tag maps to the EWR_STG_TIRE_CG table.
	 * 
		<CommonGreen>
		<CgTire>
		  <CGreenGroup>NEXEN</CGreenGroup>
		  <TireLine>CP521</TireLine>
		  <TypeCode>1</TypeCode>
		  <SKU>HJ</SKU>
		  <BrandName>NEXEN</BrandName>
		  <BrandOwner>NEXEN</BrandOwner>
		 </CgTire>
		<CgTire>
		  <CGreenGroup>NEXEN</CGreenGroup>
		  <TireLine>CP521</TireLine>
		  <TypeCode>1</TypeCode>
		  <SKU>XF</SKU>
		  <BrandName>NEXEN</BrandName>
		  <BrandOwner>NEXEN</BrandOwner>
		 </CgTire>
		 ...
		 </CommonGreen>
	 ***/
	
	private int ewrId;
	private int ewrReportId;
	private int recordId; // SEQ ID + 1 - but we don't have sequence ID on the aggregate reports, I will
							// need to use a counter for this
	private String commonGreenGroup;
	private String commonGreenTypeCode;//I don't know from where to get this value
	private String tireLine;
	private String sku;
	private String brandName;
	private String brandOwner;
	private String plantCode;
	private String fileName;
	
	
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

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getCommonGreenGroup() {
		return commonGreenGroup;
	}

	public void setCommonGreenGroup(String commonGreenGroup) {
		this.commonGreenGroup = commonGreenGroup;
	}

	public String getCommonGreenTypeCode() {
		return commonGreenTypeCode;
	}

	public void setCommonGreenTypeCode(String commonGreenTypeCode) {
		this.commonGreenTypeCode = commonGreenTypeCode;
	}

	public String getTireLine() {
		return tireLine;
	}

	public void setTireLine(String tireLine) {
		this.tireLine = tireLine;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandOwner() {
		return brandOwner;
	}

	public void setBrandOwner(String brandOwner) {
		this.brandOwner = brandOwner;
	}

	public String getPlantCode() {
		return plantCode;
	}

	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "AggregateTireCommonGreen [ewrId=" + ewrId + ", ewrReportId=" + ewrReportId + ", recordId=" + recordId
				+ ", commonGreenGroup=" + commonGreenGroup + ", commonGreenTypeCode=" + commonGreenTypeCode
				+ ", tireLine=" + tireLine + ", sku=" + sku + ", brandName=" + brandName + ", brandOwner=" + brandOwner
				+ ", plantCode=" + plantCode + ", fileName=" + fileName + "]";
	}

}
