package gov.dot.nhtsa.odi.ewr.aws.data;

/*
 * @author Bienvenido Ortiz
 */
public class AggregateTireProduction {

	
	/*
	 * This object is used for the following aggregate reports:
	 * 
	 * Tires 
	 * 
	 * This object maps to the Excel Tab and XML Tag "Production".
	 * Data from this tab/tag maps to the EWR_STG_PRODUCTION_TIRE table.
	 * 
	 <Production>
      <PrTire>
         <TireLine>ADVENTURO A/T</TireLine>
         <TireSize>30X9.50R15 104S ADVENTURO</TireSize>
         <SKU>CGT9316</SKU>
         <ProdYear>2005</ProdYear>
         <PlantCode>CHINA</PlantCode>
         <OrigEquip>U</OrigEquip>
         <WarrantyProduction>250</WarrantyProduction>
         <TotalProduction>250</TotalProduction>
      </PrTire>
      ...
      <PrTire>
         <TireLine>WINGRO GT</TireLine>
         <TireSize>235/60R16 100H WINGRO-60</TireSize>
         <SKU>CWI6068</SKU>
         <ProdYear>2005</ProdYear>
         <PlantCode>CHINA</PlantCode>
         <OrigEquip>U</OrigEquip>
         <WarrantyProduction>4943</WarrantyProduction>
         <TotalProduction>4943</TotalProduction>
      </PrTire>
   </Production>
	 ***/
	
	private int ewrId;
	private int ewrReportId;
	private int recordId; // SEQ ID + 1 - but we don't have sequence ID on the aggregate reports, I will
							// need to use a counter for this
	private String tireLine;
	private String tireSize;
	private String sku;
	private String productionYear; //Number
	private String plantCode;
	private String originalEquipYN;
	private String warrantyProduction; //this is a number
	private String totalProduction; //This is a number
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
	 * @return the originalEquipYN
	 */
	public String getOriginalEquipYN() {
		return originalEquipYN;
	}

	/**
	 * @param originalEquipYN
	 *            the originalEquipYN to set
	 */
	public void setOriginalEquipYN(String originalEquipYN) {
		this.originalEquipYN = originalEquipYN;
	}

	/**
	 * @return the warrantyProduction
	 */
	public String getWarrantyProduction() {
		return warrantyProduction;
	}

	/**
	 * @param warrantyProduction
	 *            the warrantyProduction to set
	 */
	public void setWarrantyProduction(String warrantyProduction) {
		this.warrantyProduction = warrantyProduction;
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
		return "AggregateTireProduction [ewrId=" + ewrId + ", ewrReportId=" + ewrReportId + ", recordId=" + recordId
				+ ", tireLine=" + tireLine + ", tireSize=" + tireSize + ", sku=" + sku + ", productionYear="
				+ productionYear + ", plantCode=" + plantCode + ", originalEquipYN=" + originalEquipYN
				+ ", warrantyProduction=" + warrantyProduction + ", totalProduction=" + totalProduction + ", fileName="
				+ fileName + "]";
	}
	
}
