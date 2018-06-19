package gov.dot.nhtsa.odi.ewr.aws.data;

/*
 * @author Bienvenido Ortiz
 */
public class AggregateTireProdSkuTypeCode {

	
	/*
	 * This object is used for the following aggregate reports:
	 * 
	 * Tires 
	 * 
	 * This object maps to the Excel Tab "ProdSkuTypeCode" and XML Tag "ProdSKUTypeCode".
	 * Data from this tab/tag maps to the EWR_STG_SKU_TYPECD table.
	 * 
	 *  
	 <ProdSKUTypeCode>
      <PstTire>
         <SKU>CAMT9220</SKU>
         <TypeCode>Tire</TypeCode>
      </PstTire>
      ...
      </ProdSKUTypeCode>
	 ***/
	
	private int ewrId;
	private int ewrReportId;
	private int recordId; // SEQ ID + 1 - but we don't have sequence ID on the aggregate reports, I will
							// need to use a counter for this
	private String sku;
	private String typeCode;
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
		return "AggregateTireProdSkuTypeCode [ewrId=" + ewrId + ", ewrReportId=" + ewrReportId + ", recordId="
				+ recordId + ", sku=" + sku + ", typeCode=" + typeCode + ", fileName=" + fileName + "]";
	}

}
