package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findUserById(Integer id);
    List<User> findAll();
    //Optional<User> findUserByEmail(String email);
    @Query("SELECT user FROM User user WHERE " +
            "(LOWER(user.username) LIKE LOWER(CONCAT('%',:query, '%'))" +
            "OR LOWER(user.firstname) LIKE LOWER(CONCAT('%',:query, '%'))" +
            "OR LOWER(user.lastname) LIKE LOWER(CONCAT('%',:query, '%'))" +
            "OR LOWER(user.title) LIKE LOWER(CONCAT('%',:query, '%'))" +
            "OR LOWER(CONCAT(user.firstname, ' ', user.lastname)) LIKE LOWER(CONCAT('%',:query, '%')))" +
            "AND (LOWER(user.title) LIKE LOWER(CONCAT('%',:title, '%')) OR :title IS NULL)" +
            "AND (LOWER(user.type) LIKE LOWER(CONCAT('%',:type, '%')) OR :type IS NULL)" +
            "AND (LOWER(user.location) LIKE LOWER(CONCAT('%',:location, '%')) OR :location IS NULL)")
    List<User> searchUser(@Param("query") String query,
                          @Param("title") String title,
                          @Param("type") String type,
                          @Param("location") String location
    );
}
