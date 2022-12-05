package objects.hh;

import lombok.Data;

@Data
public class Salary {

    int from;
    int to;
    String currency;
    Boolean gross;

}
