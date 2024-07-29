package com.example.music.Service;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.music.Entity.Users;
import com.example.music.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Users register(Users user) {
        // Lógica de negocio para registrar un nuevo usuario
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (userRepository.findByUsername(user.getUsername()) != null){
            throw new IllegalArgumentException("Username no disponible");
        }
        String id = UUID.randomUUID().toString();
        user.setId(id);
        user.setPass(BCrypt.hashpw(user.getPass(),BCrypt.gensalt()));

        return userRepository.save(user);
    }

    @Transactional
    public Users updateUser (Users user){
        Users updateData = userRepository.findByUsername(user.getUsername());
        if (updateData == null) {
            throw new RuntimeException("Usuario no encontrado para actualizar");
        }
        updateData.setEmail(user.getEmail());
        updateData.setNombre(user.getNombre());
        updateData.setApellido(user.getApellido());

        return userRepository.save(updateData);
    }

    @Transactional
    public Users updatePass (Users user){ //mira a ver si puedes hacerlo con un email, que mande un mail con otro metodo y que updatee la contraseña enviando un objeto a aqui.
        Users updatePass = userRepository.findByEmail(user.getEmail());
        if (updatePass == null) {
            throw new RuntimeException("Usuario no encontrado para cambiar la contraseña");
        }
       
        user.setPass(BCrypt.hashpw(user.getPass(),BCrypt.gensalt()));

        return userRepository.save(updatePass);
    }

}