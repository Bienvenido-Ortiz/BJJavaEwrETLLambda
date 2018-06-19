package gov.dot.nhtsa.odi.ewr.aws.data;

/*
 * @author Bienvenido Ortiz
 */
public class AggregateData {

	
	/*
	 * This object is used for the following aggregate reports:
	 * 
	 * LightVehicles 
	 * BusesAndMediumHeavyVehicles
	 * Trailers
	 * MotorCycles 
	 * 
	 * ChildRestraints
	 * 
	 * They all shared the same attributes and persist to the same table. 
	 * 
	 * Please note there is NO aggregate data for Equipment or Low Volume Vehicles
	 * 
	 * This object maps to the Excel Tabs and XML Tags "ConsumerComplaints, PropertyDamage, WarrantyClaims and FieldReports" on Vehicles
	 * and in Child Restraints for tags / tabs  "ComplaintsWarrantyClaims and FieldReports"
	 * 
	 * Data from this tabs/tags maps to the EWR_STG_AGGR table.
 

		LightVehicles
		*************
		
		<ConsumerComplaints>
		 	<CcLVehicle>
			<CcLVehicle>
		<ConsumerComplaints>
		
		<PropertyDamage>
		 	<PdLVehicle>
			<PdLVehicle> 	  
		</PropertyDamage>
			 
		<WarrantyClaims>
		 	<WcLVehicle>
		 	</WcLVehicle>
		</WarrantyClaims>
			 
		<FieldReports>
			<FrLVehicle>
			</FrLVehicle>
		</FieldReports>

	 	BusesAndMediumHeavyVehicles
	 	***************************
	 	For this type of report please replace L in the inner child by BMH
	 	Ex:
		<ConsumerComplaints>
		 	<CcBMHVehicle>
			<CcBMHVehicle>
		<ConsumerComplaints> 	
	 
	 	Trailers
	 	********
	 	This one looks like this:
	 	 
	 	<ConsumerComplaints>
    		<CcTrailer>
    		</CcTrailer>
    	</ConsumerComplaints>
    	
    	MotorCycles
    	***********
    	Motorcycles look like this:
    	<ConsumerComplaints>
    		<CcMCycle>
        	</CcMCycle>
        </ConsumerComplaints>
    	
    	
    	ChildRestraints
    	***************
    	
	 ***/
	
	private int ewrId;
	private int ewrReportId;
	private int recordId; // SEQ ID + 1 - but we don't have sequence ID on the aggregate reports, I will
							// need to use a counter for this
	private String make; 		//Use in Child Restraint as well
	private String model;		//Use in Child Restraint as well
	private String modelYear;	//Use in Child Restraint as well, mapped to ProdYear tag in XML / tab in Excel
	private String aggregateTypeCode; //This is: Cc=Consumer Complaints, Pd=Property Damage, Wc=Warranty Claims, Fr=Field Reports
	
	private int steering01;
	private int suspension02;
	private int serviceBreak03;
	private int serviceBreakAir04;
	private int parkingBreak05;
	private int engAndEngCooling06;
	private int fuelSystem07;
	private int fuelSystemDiesel08;
	private int fuelSystemOther09;
	private int powerTrain10;
	private int electrical11;
	private int extLighting12;
	private int visibility13;
	private int airbags14;
	private int seatbelts15;
	private int structure16;
	private int latch17;
	private int speedControl18;
	private int tiresRelated19;
	private int wheels20;
	private int trailerHitch21;
	private int seats22;
	private int fireRelated23;
	private int rollover24;	
	private int electronicStabilityCtrl25;
	private int forwardCollision26;
	private int laneDeparture27;
	private int backoverPrevention28;
	
	//These four are for Child Restraint
	private int buckle51;
	private int seatshell52;
	private int handle53;
	private int base54;
	
	private String typeCode;
	private String fuelPropulsionSystem;
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

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModelYear() {
		return modelYear;
	}

	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}

	public String getAggregateTypeCode() {
		return aggregateTypeCode;
	}

	public void setAggregateTypeCode(String aggregateTypeCode) {
		this.aggregateTypeCode = aggregateTypeCode;
	}

	public int getSteering01() {
		return steering01;
	}

	public void setSteering01(int steering01) {
		this.steering01 = steering01;
	}

	public int getSuspension02() {
		return suspension02;
	}

	public void setSuspension02(int suspension02) {
		this.suspension02 = suspension02;
	}

	public int getServiceBreak03() {
		return serviceBreak03;
	}

	public void setServiceBreak03(int serviceBreak03) {
		this.serviceBreak03 = serviceBreak03;
	}

	public int getServiceBreakAir04() {
		return serviceBreakAir04;
	}

	public void setServiceBreakAir04(int serviceBreakAir04) {
		this.serviceBreakAir04 = serviceBreakAir04;
	}

	public int getParkingBreak05() {
		return parkingBreak05;
	}

	public void setParkingBreak05(int parkingBreak05) {
		this.parkingBreak05 = parkingBreak05;
	}

	public int getEngAndEngCooling06() {
		return engAndEngCooling06;
	}

	public void setEngAndEngCooling06(int engAndEngCooling06) {
		this.engAndEngCooling06 = engAndEngCooling06;
	}

	public int getFuelSystem07() {
		return fuelSystem07;
	}

	public void setFuelSystem07(int fuelSystem07) {
		this.fuelSystem07 = fuelSystem07;
	}

	public int getFuelSystemDiesel08() {
		return fuelSystemDiesel08;
	}

	public void setFuelSystemDiesel08(int fuelSystemDiesel08) {
		this.fuelSystemDiesel08 = fuelSystemDiesel08;
	}

	public int getFuelSystemOther09() {
		return fuelSystemOther09;
	}

	public void setFuelSystemOther09(int fuelSystemOther09) {
		this.fuelSystemOther09 = fuelSystemOther09;
	}

	public int getPowerTrain10() {
		return powerTrain10;
	}

	public void setPowerTrain10(int powerTrain10) {
		this.powerTrain10 = powerTrain10;
	}

	public int getElectrical11() {
		return electrical11;
	}

	public void setElectrical11(int electrical11) {
		this.electrical11 = electrical11;
	}

	public int getExtLighting12() {
		return extLighting12;
	}

	public void setExtLighting12(int extLighting12) {
		this.extLighting12 = extLighting12;
	}

	public int getVisibility13() {
		return visibility13;
	}

	public void setVisibility13(int visibility13) {
		this.visibility13 = visibility13;
	}

	public int getAirbags14() {
		return airbags14;
	}

	public void setAirbags14(int airbags14) {
		this.airbags14 = airbags14;
	}

	public int getSeatbelts15() {
		return seatbelts15;
	}

	public void setSeatbelts15(int seatbelts15) {
		this.seatbelts15 = seatbelts15;
	}

	public int getStructure16() {
		return structure16;
	}

	public void setStructure16(int structure16) {
		this.structure16 = structure16;
	}

	public int getLatch17() {
		return latch17;
	}

	public void setLatch17(int latch17) {
		this.latch17 = latch17;
	}

	public int getSpeedControl18() {
		return speedControl18;
	}

	public void setSpeedControl18(int speedControl18) {
		this.speedControl18 = speedControl18;
	}

	public int getTiresRelated19() {
		return tiresRelated19;
	}

	public void setTiresRelated19(int tiresRelated19) {
		this.tiresRelated19 = tiresRelated19;
	}

	public int getWheels20() {
		return wheels20;
	}

	public void setWheels20(int wheels20) {
		this.wheels20 = wheels20;
	}

	public int getTrailerHitch21() {
		return trailerHitch21;
	}

	public void setTrailerHitch21(int trailerHitch21) {
		this.trailerHitch21 = trailerHitch21;
	}

	public int getSeats22() {
		return seats22;
	}

	public void setSeats22(int seats22) {
		this.seats22 = seats22;
	}

	public int getFireRelated23() {
		return fireRelated23;
	}

	public void setFireRelated23(int fireRelated23) {
		this.fireRelated23 = fireRelated23;
	}

	public int getRollover24() {
		return rollover24;
	}

	public void setRollover24(int rollover24) {
		this.rollover24 = rollover24;
	}

	public int getElectronicStabilityCtrl25() {
		return electronicStabilityCtrl25;
	}

	public void setElectronicStabilityCtrl25(int electronicStabilityCtrl25) {
		this.electronicStabilityCtrl25 = electronicStabilityCtrl25;
	}

	public int getForwardCollision26() {
		return forwardCollision26;
	}

	public void setForwardCollision26(int forwardCollision26) {
		this.forwardCollision26 = forwardCollision26;
	}

	public int getLaneDeparture27() {
		return laneDeparture27;
	}

	public void setLaneDeparture27(int laneDeparture27) {
		this.laneDeparture27 = laneDeparture27;
	}

	public int getBackoverPrevention28() {
		return backoverPrevention28;
	}

	public void setBackoverPrevention28(int backoverPrevention28) {
		this.backoverPrevention28 = backoverPrevention28;
	}

	public int getBuckle51() {
		return buckle51;
	}

	public void setBuckle51(int buckle51) {
		this.buckle51 = buckle51;
	}

	public int getSeatshell52() {
		return seatshell52;
	}

	public void setSeatshell52(int seatshell52) {
		this.seatshell52 = seatshell52;
	}

	public int getHandle53() {
		return handle53;
	}

	public void setHandle53(int handle53) {
		this.handle53 = handle53;
	}

	public int getBase54() {
		return base54;
	}

	public void setBase54(int base54) {
		this.base54 = base54;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getFuelPropulsionSystem() {
		return fuelPropulsionSystem;
	}

	public void setFuelPropulsionSystem(String fuelPropulsionSystem) {
		this.fuelPropulsionSystem = fuelPropulsionSystem;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "AggregateData [ewrId=" + ewrId + ", ewrReportId=" + ewrReportId + ", recordId=" + recordId + ", make="
				+ make + ", model=" + model + ", modelYear=" + modelYear + ", aggregateTypeCode=" + aggregateTypeCode
				+ ", steering01=" + steering01 + ", suspension02=" + suspension02 + ", serviceBreak03=" + serviceBreak03
				+ ", serviceBreakAir04=" + serviceBreakAir04 + ", parkingBreak05=" + parkingBreak05
				+ ", engAndEngCooling06=" + engAndEngCooling06 + ", fuelSystem07=" + fuelSystem07
				+ ", fuelSystemDiesel08=" + fuelSystemDiesel08 + ", fuelSystemOther09=" + fuelSystemOther09
				+ ", powerTrain10=" + powerTrain10 + ", electrical11=" + electrical11 + ", extLighting12="
				+ extLighting12 + ", visibility13=" + visibility13 + ", airbags14=" + airbags14 + ", seatbelts15="
				+ seatbelts15 + ", structure16=" + structure16 + ", latch17=" + latch17 + ", speedControl18="
				+ speedControl18 + ", tiresRelated19=" + tiresRelated19 + ", wheels20=" + wheels20 + ", trailerHitch21="
				+ trailerHitch21 + ", seats22=" + seats22 + ", fireRelated23=" + fireRelated23 + ", rollover24="
				+ rollover24 + ", electronicStabilityCtrl25=" + electronicStabilityCtrl25 + ", forwardCollision26="
				+ forwardCollision26 + ", laneDeparture27=" + laneDeparture27 + ", backoverPrevention28="
				+ backoverPrevention28 + ", buckle51=" + buckle51 + ", seatshell52=" + seatshell52 + ", handle53="
				+ handle53 + ", base54=" + base54 + ", typeCode=" + typeCode + ", fuelPropulstionSystem="
				+ fuelPropulsionSystem + ", fileName=" + fileName + "]";
	}

}
