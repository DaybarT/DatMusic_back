package com.example.music.Service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.music.Entity.Artists;
import com.example.music.Entity.Users;
import com.example.music.Repository.ArtistRepository;
import com.example.music.Repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    public String login(String username, String password, String license) {

        try {
            String token = null;

            if (license == null) {
                Users user = userRepository.findByUsername(username);
                // Verificar si se encontró un usuario y si la contraseña coincide
                if (user != null && passwordMatches(password, user.getPass())) {
                    token = new JwtService().generateToken(user.getUsername(), user.getEmail());
                } 
            } else {
                Artists artists = artistRepository.findByUsername(username);
                if (artists != null && passwordMatches(password, artists.getPass())) {
                    token = new JwtService().generateToken(artists.getUsername(), artists.getEmail());
                }
            }

            return token != null ? token : "Usuario o contraseña erróneos";

        } catch (RuntimeException e) {
            return "Error al conectar: " + e.getMessage();
        }
    }

    // Método privado para verificar si las contraseñas coinciden
    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        // Verificar si la contraseña sin cifrar coincide con la contraseña hasheada
        // almacenada
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
