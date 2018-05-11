package rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Comment;
import domain.Film;
import domain.Score;
import domain.Services.CommentService;
import domain.Services.FilmService;

@Path("/films")
public class Resources {

private FilmService db = new FilmService();
private CommentService commentDb = new CommentService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Film> getAll(){
		return db.getAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Add(Film film){
		db.add(film);
		return Response.ok(film.getId()).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id) {
		Film result = db.get(id);
		
		if (result == null) {
			return Response.status(404).build();
		}
		
		return Response.ok(result).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Film film) {
		Film result = db.get(id);
		
		if (result == null) {
			return Response.status(404).build();
		}

		film.setId(id);
		db.update(film);

		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") int id) {
		Film result = db.get(id);
		
		if (result == null) {
			return Response.status(404).build();
		}
		
		db.delete(result);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("/{filmId}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> getCommentList(@PathParam("filmId") int filmId) {
		Film result = db.get(filmId);
		
		if (result == null) {
			return null;
		}
		
		if (result.getCommentList() == null) {
			result.setCommentList(new ArrayList<Comment>());
		}
		
		return result.getCommentList();
	}
	
	@POST
	@Path("/{filmId}/comment")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addComment(@PathParam("filmId") int filmId, Comment comment) {
		Film result = db.get(filmId);
		
		if (result == null) {
			return Response.status(404).build();
		}
		
		if (result.getCommentList() == null) {
			result.setCommentList(new ArrayList<Comment>());
		}
		
		commentDb.add(comment);

		comment.setId(commentDb.getCurId());
		result.getCommentList().add(comment);
		
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{filmId}/comment/{idComment}")
	public Response deleteComment(@PathParam("filmId") int filmId, @PathParam("idComment") int idComment) {
		Film f = db.get(filmId);
		
		if (f == null) {
			return Response.status(404).build();
		}
		
		ArrayList<Comment> comments = f.getCommentList();
		
		if (comments == null) {
			return Response.status(404).build();
		}
		
		Comment found = new Comment();
		
		for (Comment c : comments) {
			if (c.getId() == idComment) {
				found = c;
			}
		}
		
		comments.remove(found);
		
		Comment comment = commentDb.get(idComment);
		commentDb.delete(comment);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("/{filmId}/score")
	@Produces(MediaType.APPLICATION_JSON)
	public String getScoreList(@PathParam("filmId") int filmId) {
		Film result = db.get(filmId);

		if (result == null) {
			return null;
		}

		if (result.getScoreList() == null) {
			result.setScoreList(new ArrayList<Score>());
		}
		
		ArrayList<Score> scoreList = result.getScoreList();
		
		int[] array = new int[scoreList.size()];

	    Iterator<Score> iterator = scoreList.iterator();
	    for (int i = 0; i < array.length; i++)
	    {
	        array[i] = iterator.next().getScore();
	    }
	    return Arrays.toString(array);
	}

	@POST
	@Path("/{filmId}/score")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addScore(@PathParam("filmId") int filmId, Score score) {
		Film result = db.get(filmId);
		
		
		if (result == null) {
			return Response.status(404).build();
		}
		
		if (result.getScoreList() == null) {
			result.setScoreList(new ArrayList<Score>());
		}
		
		result.getScoreList().add(score);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("/{filmId}/averagescore")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAverageScore(@PathParam("filmId") int filmId) {
		Film result = db.get(filmId);
		
		if (result == null) {
			return null;
		}
		
		if (result.getScoreList() == null) {
			result.setScoreList(new ArrayList<Score>());
		}
		
		return String.valueOf(result.getAverageScore());
	}
}