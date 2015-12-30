package pl.mg.checkers.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by maciej on 25.12.15.
 */
@Service
public class JsonService {

    private ObjectMapper mapper = new ObjectMapper();

    public <T>Optional<String> stringify(T object) {
        try {
            return Optional.of(mapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public <T>Optional<T> parseObject(String string, Class<T> _class) {
        try {
            return Optional.of(mapper.readValue(string,_class));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<JsonNode> parseNode(String string) {
        try {
            return Optional.ofNullable(mapper.readTree(string));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
