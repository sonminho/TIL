import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

public class UtilTest {

    @Getter
    @ToString
    static class Dish implements Serializable {
        @JsonIgnore
        String name;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String source;
    }

    @Test
    void test( ) {
        Dish dish = new Dish();
        dish.name = "fish";
        dish.source = "hot";

        try {
            String dishStr = new ObjectMapper().writeValueAsString(dish);
            System.out.println(dishStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
