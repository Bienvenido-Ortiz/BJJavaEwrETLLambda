package gov.dot.nhtsa.odi.ewr.aws.data;

/*
 * @author Bienvenido Ortiz
 */
public class AggregateTireDataPersistResults {

	private int numOfTireProductionRecordsPersisted;
	private int numOfTireProdSkyTypeCodeRecordsPersisted;
	private int numOfTireProdOEVehAppRecordsPersisted;
	private int numOfTirePropDamagaRecordsPersisted;
	private int numOfTireWarrantyAdjustmentsRecordsPersisted;
	private int numOfTireCommonGreenRecordsPersisted;

	public int getNumOfTireProductionRecordsPersisted() {
		return numOfTireProductionRecordsPersisted;
	}

	public void setNumOfTireProductionRecordsPersisted(int numOfTireProductionRecordsPersisted) {
		this.numOfTireProductionRecordsPersisted = numOfTireProductionRecordsPersisted;
	}

	public int getNumOfTireProdSkyTypeCodeRecordsPersisted() {
		return numOfTireProdSkyTypeCodeRecordsPersisted;
	}

	public void setNumOfTireProdSkyTypeCodeRecordsPersisted(int numOfTireProdSkyTypeCodeRecordsPersisted) {
		this.numOfTireProdSkyTypeCodeRecordsPersisted = numOfTireProdSkyTypeCodeRecordsPersisted;
	}

	public int getNumOfTireProdOEVehAppRecordsPersisted() {
		return numOfTireProdOEVehAppRecordsPersisted;
	}

	public void setNumOfTireProdOEVehAppRecordsPersisted(int numOfTireProdOEVehAppRecordsPersisted) {
		this.numOfTireProdOEVehAppRecordsPersisted = numOfTireProdOEVehAppRecordsPersisted;
	}

	public int getNumOfTirePropDamagaRecordsPersisted() {
		return numOfTirePropDamagaRecordsPersisted;
	}

	public void setNumOfTirePropDamagaRecordsPersisted(int numOfTirePropDamagaRecordsPersisted) {
		this.numOfTirePropDamagaRecordsPersisted = numOfTirePropDamagaRecordsPersisted;
	}

	public int getNumOfTireWarrantyAdjustmentsRecordsPersisted() {
		return numOfTireWarrantyAdjustmentsRecordsPersisted;
	}

	public void setNumOfTireWarrantyAdjustmentsRecordsPersisted(int numOfTireWarrantyAdjustmentsRecordsPersisted) {
		this.numOfTireWarrantyAdjustmentsRecordsPersisted = numOfTireWarrantyAdjustmentsRecordsPersisted;
	}

	public int getNumOfTireCommonGreenRecordsPersisted() {
		return numOfTireCommonGreenRecordsPersisted;
	}

	public void setNumOfTireCommonGreenRecordsPersisted(int numOfTireCommonGreenRecordsPersisted) {
		this.numOfTireCommonGreenRecordsPersisted = numOfTireCommonGreenRecordsPersisted;
	}

	@Override
	public String toString() {
		return "AggregateTireDataPersistResults [numOfTireProductionRecordsPersisted="
				+ numOfTireProductionRecordsPersisted + ", numOfTireProdSkyTypeCodeRecordsPersisted="
				+ numOfTireProdSkyTypeCodeRecordsPersisted + ", numOfTireProdOEVehAppRecordsPersisted="
				+ numOfTireProdOEVehAppRecordsPersisted + ", numOfTirePropDamagaRecordsPersisted="
				+ numOfTirePropDamagaRecordsPersisted + ", numOfTireWarrantyAdjustmentsRecordsPersisted="
				+ numOfTireWarrantyAdjustmentsRecordsPersisted + ", numOfTireCommonGreenRecordsPersisted="
				+ numOfTireCommonGreenRecordsPersisted + "]";
	}

}
