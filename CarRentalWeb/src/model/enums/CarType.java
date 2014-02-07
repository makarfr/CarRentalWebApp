package model.enums;


public enum CarType {
	CABRIOLET("Cabriolet"), HATCHBACK("Hatchback"), SEDAN("Sedan"), TRUCK("Truck");
	

    private String value;

    private CarType(String value) {
        this.value = value;
    }

    public String getValue(){
      return value;
    }
}