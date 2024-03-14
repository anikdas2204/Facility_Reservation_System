package com.amenity_reservation_system.repos;

import com.amenity_reservation_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);
    List<User> findUserByUsernameIn(List<String> usernames);
}
