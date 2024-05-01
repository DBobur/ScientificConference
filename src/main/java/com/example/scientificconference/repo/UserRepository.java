package com.example.scientificconference.repo;

import com.example.scientificconference.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    UserEntity getByUsername(String username);

    UserEntity getByUsernameAndPassword(String username, String password);
}