package ua.lifebook.notification;

public class MailType {
	private String name;

	private MailType(String name) {
		this.name = name;
	}

	public final static MailType HTML = new MailType("text/html; charset=utf-8");
	public final static MailType TEXT = new MailType("text/plain; charset=utf-8");

	@Override
	public String toString() {
		return name;
	}
}
