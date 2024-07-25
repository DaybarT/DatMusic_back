package com.example.music.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.music.Entity.Users;

@Repository("UsersRepo")
public interface UserRepository extends JpaRepository<Users, String> {
    // Métodos adicionales de consulta personalizados pueden ser definidos aquí
    Users findByEmail(String Email);
    Users findByUsername(String Username);
    boolean deleteUserByUsername(String Username);
}

//POR SI TIENES QUE HACER QUERYS PERSONALIZADAS

// public interface UserRepository extends JpaRepository<Users, Long> {

//     @Query("SELECT u FROM Users u WHERE u.username = :username AND u.email = :email")
//     Users findByUsernameAndEmailCustomQuery(@Param("username") String username, @Param("email") String email);

// }
