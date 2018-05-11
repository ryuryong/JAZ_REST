package domain;

public class Comment {

	private int id;
	private String author, text;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBody() {
		return text;
	}
	public void setBody(String text) {
		this.text = text;
	}
}