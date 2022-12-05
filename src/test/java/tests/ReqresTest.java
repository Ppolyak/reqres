package tests;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.ValueMatcher;

import io.restassured.internal.path.json.JSONAssertion;
import io.restassured.response.Response;
import objects.reqres.User;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ReqresTest {

    private final static String BASE_URI = "https://reqres.in/api";

    //https://reqres.in/api/users

    @Test
    public void postCreateUserTest() {
        User user = User.builder()
                .name("morpheus")
                .job("leader")
                .build();
        given()
                .body(user)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void getListUserTest(){
        Response response = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .body("page",equalTo(2))
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK);
    }

    @Test
    public void getSingleUser() throws JSONException {
        String expectedBody = "{\n" +
                "    \"data\": {\n" +
                "        \"id\": 2,\n" +
                "        \"email\": \"janet.weaver@reqres.in\",\n" +
                "        \"first_name\": \"Janet\",\n" +
                "        \"last_name\": \"Weaver\",\n" +
                "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
                "    },\n" +
                "    \"support\": {\n" +
                "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                "    }\n" +
                "}";
        String body = given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();
        JSONAssert.assertEquals(expectedBody, body, false);
    }

    @Test
    public void getSingleUserNotFoundTest() throws JSONException {
        String expectedBody = "{}";
        String body = given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().all()
                .statusCode(404)
                .extract().body().asString();
        JSONAssert.assertEquals(expectedBody, body, false);
    }

    @Test
    public void putUpdateTest() throws JSONException {
        /*String name = "morpheus";
        String job = "zion resident";*/
        /*RestAssured.baseURI = BASE_URI;*/
        RestAssured.baseURI = "https://reqres.in/api/users/2";
        RequestSpecification request = RestAssured.given();

        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "morpheus");
        requestParams.put("job", "zion resident");

        request.body(requestParams.toString());
        Response response = request.put("/users/2");

        int statusCode = response.getStatusCode();
        String body = response.body().asString();
        String expectedBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\",\n" +
                "    \"updatedAt\": \"2022-12-05T17:49:00.590Z\"\n" +
                "}";
        System.out.println(body);
       /* JSONAssert.assertEquals(expectedBody, body, false);*/
    /*
        Assert.assertEquals(statusCode, 200);*/
    }

    @Test
    public void putUpdateUserTest() {
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        given()
                .body(user)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void patchUpdateUserTest() {
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        given()
                .body(user)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void deleteTest(){
        RestAssured.baseURI = "https://reqres.in/api/users/2";
        RequestSpecification request = RestAssured.given();
        Response response = request.delete();
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode,HTTP_NO_CONTENT);
    }

    @Test
    public void postRegisterTest() throws JSONException {
        /*User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();*/
        String expectedResponse = "{\n" +
                "    \"id\": 4,\n" +
                "    \"token\": \"QpwL5tke4Pnpja7X4\"\n" +
                "}";
        /*RestAssured.baseURI = "https://reqres.in/api/register";*/
        JSONObject requestBody = new JSONObject();
        requestBody.put("email","eve.holt@reqres.in");
        requestBody.put("password","pistol");


        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post("https://reqres.in/api/register");
        int statusCode = response.getStatusCode();
        String actualResponse = response.body().asString();
        System.out.println(actualResponse);
        JSONAssert.assertEquals(expectedResponse,actualResponse,false);
        /*Assert.assertEquals(statusCode, HTTP_OK);*/

    }


}