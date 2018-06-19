package gov.dot.nhtsa.odi.ewr.aws.data;

/*
 * @author Bienvenido Ortiz
 */
public class PersistEwrReportInfoOutput {

	private String reportInfoDataPersisted;
	private String restOfTheDataPersisted;
	private String message;
	private String filename;
	private int numberOfDIRecordsPersisted;
	private AggregateDataPersistResults aggDataPersistResult;
	private AggregateTireDataPersistResults aggTireDataPersistResult;

	public String getReportInfoDataPersisted() {
		return reportInfoDataPersisted;
	}

	public void setReportInfoDataPersisted(String reportInfoDataPersisted) {
		this.reportInfoDataPersisted = reportInfoDataPersisted;
	}

	public String getRestOfTheDataPersisted() {
		return restOfTheDataPersisted;
	}

	public void setRestOfTheDataPersisted(String restOfTheDataPersisted) {
		this.restOfTheDataPersisted = restOfTheDataPersisted;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getNumberOfDIRecordsPersisted() {
		return numberOfDIRecordsPersisted;
	}

	public void setNumberOfDIRecordsPersisted(int numberOfDIRecordsPersisted) {
		this.numberOfDIRecordsPersisted = numberOfDIRecordsPersisted;
	}

	public AggregateDataPersistResults getAggDataPersistResult() {
		return aggDataPersistResult;
	}

	public void setAggDataPersistResult(AggregateDataPersistResults aggDataPersistResult) {
		this.aggDataPersistResult = aggDataPersistResult;
	}

	public AggregateTireDataPersistResults getAggTireDataPersistResult() {
		return aggTireDataPersistResult;
	}

	public void setAggTireDataPersistResult(AggregateTireDataPersistResults aggTireDataPersistResult) {
		this.aggTireDataPersistResult = aggTireDataPersistResult;
	}

	@Override
	public String toString() {
		return "PersistEwrReportInfoOutput [reportInfoDataPersisted=" + reportInfoDataPersisted
				+ ", restOfTheDataPersisted=" + restOfTheDataPersisted + ", message=" + message + ", filename="
				+ filename + ", numberOfDIRecordsPersisted=" + numberOfDIRecordsPersisted + ", aggDataPersistResult="
				+ aggDataPersistResult + ", aggTireDataPersistResult=" + aggTireDataPersistResult + "]";
	}

}