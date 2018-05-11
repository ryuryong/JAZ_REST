package domain.Services;

import java.util.ArrayList;

import domain.Comment;

public class CommentService {
	private static ArrayList<Comment> db = new ArrayList<Comment>();
	private static int curId = 0;
	
	public int getCurId() {
		return curId;
	}
	
	public Comment get(int id) {
		for (Comment c : db) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
	}
	
	public void add(Comment comment) {
		comment.setId(++curId);
		db.add(comment);
	}
	
	public void delete(Comment comment) {
		db.remove(comment);
	}
}