public class MessageTxt implements Message {

	private String m;

	public MessageTxt(String txt) {
		this.m = txt;
	}

	public String getMessage() {
		return this.m;
	}

}
