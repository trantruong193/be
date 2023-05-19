package com.shopme.be.repository;

import com.shopme.be.persistant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

//    @Query(value = "select u from User u where u.lastname = ?1")
//    List<User> getByNameByJPQL(String name);
//
//    @Query(value = "SELECT * FROM users WHERE lastname = ?1", nativeQuery = true)
//    List<User> getUserByNameBySQL(String name);
}
