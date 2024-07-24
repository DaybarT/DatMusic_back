package com.example.music.Service;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.music.Entity.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;


    public String generateToken(Users user){
        return Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("Email", user.getEmail()) 
                    .setIssuedAt(new Date()) // Fecha de emisión del token (ahora)
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // Fecha de expiración del token
                    .signWith(getSigningKey(), SignatureAlgorithm.HS512) // Firma del token con HMAC SHA-512 y la clave secreta
                    .compact();
    }

    private SecretKey getSigningKey() {
        byte[] decodedSecret = Base64.getDecoder().decode(jwtSecret);
        return new SecretKeySpec(decodedSecret, 0, decodedSecret.length, "HmacSHA512");
    }

    public boolean validateToken(String token) {
        try {
            // Parsear el token y obtener las reclamaciones (claims)
            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(getSigningKey())
                                .build()
                                .parseClaimsJws(token)
                                .getBody();
    
            // Verificar la firma del token (si no lanza excepción, la firma es válida)
            // No es necesario hacer nada específico aquí porque ya está integrado en parseClaimsJws
            
            // Verificar la fecha de expiración
            Date expirationDate = claims.getExpiration();
            Date now = new Date(); // Fecha actual
            
            if (expirationDate != null && expirationDate.after(now)) {
                // El token no ha expirado y la firma es válida
                return true;
            } else {
                // El token ha expirado
                throw new Exception("JWT token no valido");
            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean expireToken(String token) {
        try {
            // Crear una fecha de expiración en el pasado
            Date expirationDate = new Date(System.currentTimeMillis() - 1000); // Restar 1 segundo a la fecha actual
            
            // Parsear el token y obtener las reclamaciones
            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(getSigningKey())
                                .build()
                                .parseClaimsJws(token)
                                .getBody();
            
            // Modificar las fechas del token para que parezca expirado
            claims.setIssuedAt(new Date()); // Establecer la fecha de emisión actual
            claims.setExpiration(expirationDate); // Establecer la fecha de expiración en el pasado
    
            // Firmar nuevamente el token con la clave secreta
            String newToken = Jwts.builder()
                                  .setClaims(claims)
                                  .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                                  .compact();
            
            // Si se genera un nuevo token, se considera exitoso
            if ( newToken != null && !newToken.isEmpty()){
                return true;
            }
            else{
                throw new Exception("Token no valido");
            }
            
        } catch (Exception e) {
            // Manejo de excepciones, por ejemplo, token inválido
            System.out.println("Error al expirar el token: " + e.getMessage());
            return false;
        }
    }


}
/* USO
JwtService jwtService = new JwtService();

try {
    Claims claims = jwtService.validateToken(token);
    String username = claims.getSubject();
    Integer userId = claims.get("ID", Integer.class);
    // Aquí puedes usar el username y el userId según sea necesario
} catch (RuntimeException e) {
    // Manejo de la excepción
    System.out.println(e.getMessage());
}
 */