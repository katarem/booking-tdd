package io.github.katarem.domain.policy;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfanityPolicy {

    List<String> profanities = List.of("mierda", "gilipollas", "idiota");

    public void validateContent(String field, String value) {
        if(profanities.stream().anyMatch(value::contains)) {
            throw new RuntimeException(field + " has profanities");
        }
    }


}
