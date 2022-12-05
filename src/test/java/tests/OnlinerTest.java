package tests;

import com.google.gson.Gson;
import io.restassured.response.Response;
import objects.hh.VacanciesList;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.equalTo;

public class OnlinerTest {

    @Test
    public void getCurrencyUSDRateTest(){
        Response response = given()
                .log().all()
                .when()
                .get("https://www.onliner.by/sdapi/kurs/api/bestrate?currency=USD&type=nbrb\n")
                .then()
                .log().all()
                .statusCode(200)
                .body("amount",equalTo("2,4350"))
                .extract().response();
        Assert.assertEquals(response.statusCode(),HTTP_CREATED);
    }

    @Test
    public void vacancyTest(){
        String body = given()
                .when()
                .get("https://api.hh.ru/vacancies?text=QA automation")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();
        VacanciesList vacanciesList = new Gson().fromJson(body, VacanciesList.class);
        int salaryTo = vacanciesList.getItems().get(1).getSalary().getTo();
        System.out.println(salaryTo);
    }

}
