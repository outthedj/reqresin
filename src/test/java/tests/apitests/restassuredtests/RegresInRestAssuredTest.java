package tests.apitests.restassuredtests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import models.apimodels.UpdateUserModel;
import models.apimodels.UserRegisterModel;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import utilities.GenerateFakeMessage;

import java.io.File;

public class RegresInRestAssuredTest {

    @Test
    public void checkResponseCodeTest() {
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log()
                .all()
                .statusCode(404);
    }

    @Test
    public void checkFieldsInResponse() {
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("per_page", Matchers.equalTo(6))
                .body("total", Matchers.equalTo(12))
                .body("total_pages", Matchers.instanceOf(Integer.class))
                .body("data[0].id", Matchers.instanceOf(Integer.class));
    }

    @Test
    public void checkBodyTest() {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/resource.json"));
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log()
                .all()
                .body("", Matchers.equalTo(expectedJson.getMap("")));
    }

    @Test
    public void userUpdateTest() {
        UpdateUserModel updateUserModel = new UpdateUserModel();
        updateUserModel.setName(GenerateFakeMessage.getFirstName());
        updateUserModel.setJob(GenerateFakeMessage.getFirstName());
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(updateUserModel)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(200);
    }

    @Test
    public void userPatchTest() {
        UpdateUserModel updateUserModel = new UpdateUserModel();
        updateUserModel.setName("morpheus");
        updateUserModel.setJob("zion resident");
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(updateUserModel)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(200);
    }

    @Test
    public void userDeleteTest() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body("")
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(204);
    }

    @Test
    public void userPostRegisterSuccessful() {
        UserRegisterModel userRegisterModel = new UserRegisterModel();
        userRegisterModel.setEmail("eve.holt@reqres.in");
        userRegisterModel.setPassword("pistol");
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(userRegisterModel)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("id", Matchers.equalTo(4))
                .body("token", Matchers.equalTo("QpwL5tke4Pnpja7X4"));

    }

    @Test
    public void userPostRegisterUnsuccessful() {
        UserRegisterModel userRegisterModel = new UserRegisterModel();
        userRegisterModel.setEmail("sydney@fife");
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(userRegisterModel)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("error", Matchers.equalTo("Missing password"));
    }

    @Test
    public void userPostLoginSuccessful() {
        UserRegisterModel userRegisterModel = new UserRegisterModel();
        userRegisterModel.setEmail("eve.holt@reqres.in");
        userRegisterModel.setPassword("cityslicka");
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(userRegisterModel)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("token", Matchers.equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void userPostLoginUnsuccessful() {
        UserRegisterModel userRegisterModel = new UserRegisterModel();
        userRegisterModel.setEmail("peter@klaven");
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(userRegisterModel)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("error", Matchers.equalTo("Missing password"));
    }

    @Test
    public void usersGetDelayed() {
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .log()
                .all()
                .statusCode(200);
    }
}
