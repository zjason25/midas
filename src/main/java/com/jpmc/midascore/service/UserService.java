package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserRecord findUserById(Long id) {
        Optional<UserRecord> user = userRepository.findById(id);
        if (user.isEmpty()) {
            System.err.println("User not found with id: " + id);
        }
        return user.orElse(null);
    }

    public UserRecord findUserByName(String name) {
        Optional<UserRecord> user = userRepository.findByName(name);
        if (user.isEmpty()) {
            System.err.println("User not found with name: " + name);
        }
        return user.orElse(null);
    }

    public void updateUser(UserRecord user) {
        userRepository.save(user);
    }
}
