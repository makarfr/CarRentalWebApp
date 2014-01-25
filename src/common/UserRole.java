package common;

public enum UserRole {
	ADMIN(0), CLIENT(1), DEALER(2);

	
	private int code;

	public int getCode() {
		return code;
	}

	UserRole(int code) {
		this.code = code;
	}
}
