package gov.dot.nhtsa.odi.ewr.aws.data;

/*
 * @author Bienvenido Ortiz
 */
public class AggregateTirePropertyDamageAndWarrantyAdjustments {

	
	/*
	 * This object is used for the following aggregate reports:
	 * 
	 * Tires 
	 * 
	 * Please note that there is NO aggregate data for Equipment or Low Volume Vehicles
	 * They all shared the same attributes and persist to the same table. 
	 * 
	 * This object maps to the Excel Tabs and XML Tags "PropertyDamage" as well as "WarrantyAdjustments" 
	 * 
	 * Data from this tab/tag maps to the EWR_STG_AGGR_TIRE table.
	 * 
	   <PropertyDamage>
	      <PdTire>
	         <TireLine>SAVERO H/T LT</TireLine>
	         <TireSize>LT235/85R16 120/116Q SAVE</TireSize>
	         <SKU>IGT9152</SKU>
	         <PlantCode>INDONESIA</PlantCode>
	         <ProdYear>2003</ProdYear>
	         <Tread-71>1</Tread-71>
	         <SideWall-72>0</SideWall-72>
	         <Bead-73>0</Bead-73>
	         <Other-98>0</Other-98>
	      </PdTire>
	      ...
	  <PropertyDamage>
  
		<WarrantyAdjustments>
		<WaTire>
		  <TireLine>NPRIZ AH8</TireLine>
		  <TireSize>205/60R16</TireSize>
		  <SKU>XH</SKU>
		  <PlantCode>UA</PlantCode>
		  <ProdYear>2018</ProdYear>
		  <Tread-71>0</Tread-71>
		  <SideWall-72>0</SideWall-72>
		  <Bead-73>0</Bead-73>
		  <Other-98>0</Other-98>
		 </WaTire>
		 ...
		</WarrantyAdjustments>
	 ***/
	
	private int ewrId;
	private int ewrReportId;
	private int recordId; // SEQ ID + 1 - but we don't have sequence ID on the aggregate reports, I will
							// need to use a counter for this
	private String aggregateTypeCode; //I don't know where this data is coming from
	private String tireLine;
	private String tireSize;
	private String sku;
	private String productionYear;
	private String plantCode;
	private int treat71;
	private int sidewall72;
	private int bead73;
	private int other98;
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
	 * @return the aggregateTypeCode
	 */
	public String getAggregateTypeCode() {
		return aggregateTypeCode;
	}

	/**
	 * @param aggregateTypeCode
	 *            the aggregateTypeCode to set
	 */
	public void setAggregateTypeCode(String aggregateTypeCode) {
		this.aggregateTypeCode = aggregateTypeCode;
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
	 * @return the productionYear
	 */
	public String getProductionYear() {
		return productionYear;
	}

	/**
	 * @param productionYear
	 *            the productionYear to set
	 */
	public void setProductionYear(String productionYear) {
		this.productionYear = productionYear;
	}

	/**
	 * @return the plantCode
	 */
	public String getPlantCode() {
		return plantCode;
	}

	/**
	 * @param plantCode
	 *            the plantCode to set
	 */
	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}

	/**
	 * @return the treat71
	 */
	public int getTreat71() {
		return treat71;
	}

	/**
	 * @param treat71
	 *            the treat71 to set
	 */
	public void setTreat71(int treat71) {
		this.treat71 = treat71;
	}

	/**
	 * @return the sidewall72
	 */
	public int getSidewall72() {
		return sidewall72;
	}

	/**
	 * @param sidewall72
	 *            the sidewall72 to set
	 */
	public void setSidewall72(int sidewall72) {
		this.sidewall72 = sidewall72;
	}

	/**
	 * @return the bead73
	 */
	public int getBead73() {
		return bead73;
	}

	/**
	 * @param bead73
	 *            the bead73 to set
	 */
	public void setBead73(int bead73) {
		this.bead73 = bead73;
	}

	/**
	 * @return the other98
	 */
	public int getOther98() {
		return other98;
	}

	/**
	 * @param other98
	 *            the other98 to set
	 */
	public void setOther98(int other98) {
		this.other98 = other98;
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
		return "AggregateTirePropertyDamageAndWarrantyAdjustments [ewrId=" + ewrId + ", ewrReportId=" + ewrReportId
				+ ", recordId=" + recordId + ", aggregateTypeCode=" + aggregateTypeCode + ", tireLine=" + tireLine
				+ ", tireSize=" + tireSize + ", sku=" + sku + ", productionYear=" + productionYear + ", plantCode="
				+ plantCode + ", treat71=" + treat71 + ", sidewall72=" + sidewall72 + ", dead73=" + bead73
				+ ", other98=" + other98 + ", fileName=" + fileName + "]";
	}

}
