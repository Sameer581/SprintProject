package com.cg.repo;



import java.util.List;

>>>>>>> 26f938fe5dfa1c2b1ead5e61104464a56daeb59f
import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Comments;


public interface CommentsRepo extends JpaRepository<Comments, Long>{
	public List<Comments> findCommentsByPostID(Long PostID);
	
	public List<Comments> findCommentsByUserId(Long userID);


}
