package com.example.music.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.music.Service.AuthService;
import com.example.music.Service.JwtService;

@RestController

public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService JwtService;

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody String username, String pass, String license) {
        try {
            String token = authService.login(username,pass, license);
            if (token != null) {
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al logear: " + e.getMessage());
        }
    }

    @GetMapping("/reconnect")
    public ResponseEntity<Object> validateToken(@RequestHeader(name = "Authorization") String Authorization) {
        try {
            boolean token = JwtService.validateToken(Authorization);
            if (token == true) {
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no valido");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al comprobar el token: " + e.getMessage());
        }
    }

    @GetMapping("/disconnect")
    public ResponseEntity<Object> disconnect(@RequestHeader(name = "Authorization") String Authorization) {
        try {
            String token = authService.disconnect(Authorization);
            if (token != null) {
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error al desconectar");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al comprobar el token: " + e.getMessage());
        }
    }


}

// public class UserController {

//     @Autowired
//     private UserService UserService;

//     @PostMapping("/createUser")
//     public ResponseEntity<Object> createUser(@RequestBody Users user) {
//         try {
//             Users newUser = UserService.register(user);
//             return ResponseEntity.ok(newUser); // Devuelve el usuario registrado si no hay errores
//         } catch (IllegalArgumentException e) {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // Devuelve un mensaje de error específico si se lanza IllegalArgumentException
//         } catch (RuntimeException e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario"); // Devuelve un mensaje genérico si se lanza RuntimeException
//         }
//     }
// }