package objects.reqres;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    String name;
    String job;
    String email;
    String password;
}