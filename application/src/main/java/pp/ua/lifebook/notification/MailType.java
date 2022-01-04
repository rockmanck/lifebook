package pp.ua.lifebook.notification;

public enum MailType {
    HTML("text/html; charset=utf-8"),
    TEXT("text/plain; charset=utf-8");

	private final String name;

	MailType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
