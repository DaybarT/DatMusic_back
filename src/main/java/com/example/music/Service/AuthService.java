package com.example.music.Service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.music.Entity.Users;
import com.example.music.Repository.UserRepository;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;

    
    public String login(String username, String password) {
        // Buscar al usuario por su nombre de usuario (o email, según tu estructura)
        try {
            Users user = userRepository.findByUsername(username);
        
            // Verificar si se encontró un usuario y si la contraseña coincide
            if (user != null && passwordMatches(password, user.getPass())) {


                String token = new JwtService().generateToken(user);
                return token; 
                // Login exitoso, aqui podriamos llamar a una funcion de JWT para mandar el token al frontend
            } else {
                throw new RuntimeException("Usuario o contraseña erroneos");
            }
        } catch (RuntimeException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return null;
        }
       
    }

    // Método privado para verificar si las contraseñas coinciden
    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        // Verificar si la contraseña sin cifrar coincide con la contraseña hasheada almacenada
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

    public String disconnect(String token) {
        try {
            // Llamar al servicio JwtService para expirar el token
            boolean isTokenExpired = new JwtService().expireToken(token);
            
            // Verificar si el token fue expirado correctamente
            if (isTokenExpired) {
                return "Desconectando";
            } else {
                throw new RuntimeException("el token ya expiró");
            }
        } catch (RuntimeException e) {
            System.out.println("Error al desconectar: " + e.getMessage());
            return "Error al desconectar";
        }
    }

}
