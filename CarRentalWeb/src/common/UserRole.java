package common;

public enum UserRole {
	ADMIN(0L), CLIENT(1L), DEALER(2L);

	
	private UserRole(Long code) {
		this.code = code;
	}

	private Long code;

	public Long getCode() {
		return code;
	}
	
}
