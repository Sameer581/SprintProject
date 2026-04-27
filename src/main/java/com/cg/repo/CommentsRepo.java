package com.cg.repo;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> 26f938fe5dfa1c2b1ead5e61104464a56daeb59f
import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Comments;

<<<<<<< HEAD
public interface CommentsRepo extends JpaRepository<Comments, Integer>{
=======
public interface CommentsRepo extends JpaRepository<Comments, Long>{
	public List<Comments> findCommentsByPostID(Long PostID);
	
	public List<Comments> findCommentsByUserId(Long userID);
>>>>>>> 26f938fe5dfa1c2b1ead5e61104464a56daeb59f

}
