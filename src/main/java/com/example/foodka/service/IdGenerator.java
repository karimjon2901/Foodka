package com.example.foodka.service;

import com.example.foodka.repository.UniqueIdRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class IdGenerator {
    private final UniqueIdRepository uniqueIdRepository;
    @Transactional
    public String generate() {
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        while(true) {
            StringBuilder id = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int randomIndex = random.nextInt(characters.length());
                id.append(characters.charAt(randomIndex));
            }
            if (uniqueIdRepository.findById(id.toString()).isEmpty()){
                return id.toString();
            }
        }
    }

}
