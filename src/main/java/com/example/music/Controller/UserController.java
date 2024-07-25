package com.example.music.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.music.Entity.Users;
import com.example.music.Repository.UserRepository;
import com.example.music.Service.UserService;

// package com.example.music.



@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@RequestBody Users user) {
        try {
            Users newUser = userService.register(user);
            return ResponseEntity.ok(newUser); // Devuelve el usuario registrado si no hay errores
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // Devuelve un mensaje de error específico si se lanza IllegalArgumentException
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario"); // Devuelve un mensaje genérico si se lanza RuntimeException
        }
    }
    @GetMapping("/getUser")
    public ResponseEntity<Object> getUser(@RequestBody String name){
        try {
            Users findUser = userRepository.findByUsername(name);
            return ResponseEntity.ok(findUser); 
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // Devuelve un mensaje de error específico si se lanza IllegalArgumentException
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al recuperar el usuario"); // Devuelve un mensaje genérico si se lanza RuntimeException
        }
    }
    @DeleteMapping("/deleteUser")
    public ResponseEntity<Object> deleteUser(@RequestBody String username){
        try {
            boolean deleteUser = userRepository.deleteUserByUsername(username);
            if (deleteUser != false){
                return ResponseEntity.status(HttpStatus.OK).body(username + "Eliminado"); 
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // Devuelve un mensaje de error específico si se lanza IllegalArgumentException
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el usuario"); // Devuelve un mensaje genérico si se lanza RuntimeException
        }
    }
}
