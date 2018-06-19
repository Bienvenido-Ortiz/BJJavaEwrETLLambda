package gov.dot.nhtsa.odi.ewr.aws.data;


/*
 * @author Bienvenido Ortiz
 */
public class XMLDocumentPayloadInput {

	private String bucketName;
	private String xmlFileName;//a.k.a Key for S3 objects
	private String xmlSchemaName;
	/**
	 * @return the bucketName
	 */
	public String getBucketName() {
		return bucketName;
	}
	/**
	 * @param bucketName the bucketName to set
	 */
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	/**
	 * @return the xmlFileName
	 */
	public String getXmlFileName() {
		return xmlFileName;
	}
	/**
	 * @param xmlFileName the xmlFileName to set
	 */
	public void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}
	/**
	 * @return the xmlSchemaName
	 */
	public String getXmlSchemaName() {
		return xmlSchemaName;
	}
	/**
	 * @param xmlSchemaName the xmlSchemaName to set
	 */
	public void setXmlSchemaName(String xmlSchemaName) {
		this.xmlSchemaName = xmlSchemaName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "XMLDocumentPayloadInput [bucketName=" + bucketName + ", xmlFileName=" + xmlFileName + ", xmlSchemaName="
				+ xmlSchemaName + ", getBucketName()=" + getBucketName() + ", getXmlFileName()=" + getXmlFileName()
				+ ", getXmlSchemaName()=" + getXmlSchemaName() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
