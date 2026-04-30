package com.cg.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.entity.Friendship;
import com.cg.entity.User;
import com.cg.enums.FriendshipStatus;


public interface FriendshipRepo extends JpaRepository<Friendship, Long>{
	
	
	// Check if a friendship already exists between two users (either direction)
    @Query("SELECT f FROM Friendship f WHERE " +
           "(f.user1 = :user1 AND f.user2 = :user2) OR " +
           "(f.user1 = :user2 AND f.user2 = :user1)")
    Optional<Friendship> findByUsers(@Param("user1") User user1, 
                                     @Param("user2") User user2);

    // Get all accepted friends of a user
    @Query("SELECT f FROM Friendship f WHERE " +
           "(f.user1 = :user OR f.user2 = :user) AND f.status = :status")
    List<Friendship> findByUserAndStatus(@Param("user") User user,
                                         @Param("status") FriendshipStatus status);
    

    // Get all pending requests received by a user
//    @Query("SELECT f FROM Friendship f WHERE f.user2 = :user AND f.status = 'PENDING'")
//    List<Friendship> findPendingRequestsForUser(@Param("user") User user);
    
    
    
 // Get all pending requests received by a user
    @Query("SELECT f FROM Friendship f WHERE f.user2 = :user AND f.status = :status")
    List<Friendship> findPendingRequestsForUser(@Param("user") User user, 
                                                @Param("status") FriendshipStatus status);


    
    @Query("""
    	    SELECT COUNT(f)
    	    FROM Friendship f
    	    WHERE (f.user1.userId = :userId OR f.user2.userId = :userId)
    	      AND f.status = :status
    	""")
    	long countByUserIdAndStatus(@Param("userId") Long userId,
    	                            @Param("status") FriendshipStatus status);    


}
