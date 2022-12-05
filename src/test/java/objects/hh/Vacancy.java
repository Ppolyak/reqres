package objects.hh;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Vacancy {

    String name;
    Salary salary;
    @SerializedName("alternate_url")
    String alternateUrl;

}
