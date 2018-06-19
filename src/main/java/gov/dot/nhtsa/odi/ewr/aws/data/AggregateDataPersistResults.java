package gov.dot.nhtsa.odi.ewr.aws.data;

/*
 * @author Bienvenido Ortiz
 */
public class AggregateDataPersistResults {

	private int numberOfProductionRecordsPersisted;
	private int numberOfConsumerCompliantsRecordsPersisted;
	private int numberOfPropertyDamageRecordsPersisted;
	private int numberOfWarrantyClaimsRecordsPersisted;
	private int numberOfFieldReportsRecordsPersisted;

	public int getNumberOfProductionRecordsPersisted() {
		return numberOfProductionRecordsPersisted;
	}

	public void setNumberOfProductionRecordsPersisted(int numberOfProductionRecordsPersisted) {
		this.numberOfProductionRecordsPersisted = numberOfProductionRecordsPersisted;
	}

	public int getNumberOfConsumerCompliantsRecordsPersisted() {
		return numberOfConsumerCompliantsRecordsPersisted;
	}

	public void setNumberOfConsumerCompliantsRecordsPersisted(int numberOfConsumerCompliantsRecordsPersisted) {
		this.numberOfConsumerCompliantsRecordsPersisted = numberOfConsumerCompliantsRecordsPersisted;
	}

	public int getNumberOfPropertyDamageRecordsPersisted() {
		return numberOfPropertyDamageRecordsPersisted;
	}

	public void setNumberOfPropertyDamageRecordsPersisted(int numberOfPropertyDamageRecordsPersisted) {
		this.numberOfPropertyDamageRecordsPersisted = numberOfPropertyDamageRecordsPersisted;
	}

	public int getNumberOfWarrantyClaimsRecordsPersisted() {
		return numberOfWarrantyClaimsRecordsPersisted;
	}

	public void setNumberOfWarrantyClaimsRecordsPersisted(int numberOfWarrantyClaimsRecordsPersisted) {
		this.numberOfWarrantyClaimsRecordsPersisted = numberOfWarrantyClaimsRecordsPersisted;
	}

	public int getNumberOfFieldReportsRecordsPersisted() {
		return numberOfFieldReportsRecordsPersisted;
	}

	public void setNumberOfFieldReportsRecordsPersisted(int numberOfFieldReportsRecordsPersisted) {
		this.numberOfFieldReportsRecordsPersisted = numberOfFieldReportsRecordsPersisted;
	}

	@Override
	public String toString() {
		return "AggregateDataPersistResults [numberOfProductionRecordsPersisted=" + numberOfProductionRecordsPersisted
				+ ", numberOfConsumerCompliantsRecordsPersisted=" + numberOfConsumerCompliantsRecordsPersisted
				+ ", numberOfPropertyDamageRecordsPersisted=" + numberOfPropertyDamageRecordsPersisted
				+ ", numberOfWarrantyClaimsRecordsPersisted=" + numberOfWarrantyClaimsRecordsPersisted
				+ ", numberOfFieldReportsRecordsPersisted=" + numberOfFieldReportsRecordsPersisted + "]";
	}

}
