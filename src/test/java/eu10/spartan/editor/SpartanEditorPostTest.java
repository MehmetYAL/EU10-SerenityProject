package eu10.spartan.editor;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import utilities.SpartanNewBase;
import utilities.SpartanUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static net.serenitybdd.rest.SerenityRest.given;

@Disabled
@SerenityTest
public class SpartanEditorPostTest extends SpartanNewBase {

    @DisplayName("Editor should be able to POST")
    @Test
    public void postSpartanAsEditor(){
        //create one spartan using util
        Map<String,Object> bodyMap= SpartanUtil.getRandomSpartanMap();
        System.out.println("bodyMap = " + bodyMap);

        //send a post request as a editor
        given().auth()
                .basic("editor","editor")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .log().body()
                .when().post("/spartans")
                .prettyPrint();
        /*
                status code is 201
                content type is Json
                success message is A Spartan is Born!
                id is not null
                name is correct
                gender is correct
                phone is correct

                check location header ends with newly generated id
         */
        //status code is 201
        Ensure.that("Status code is 201",validationCode -> validationCode.statusCode(201));
        Ensure.that("content type is Json",VsContent -> VsContent.contentType(ContentType.JSON));
        Ensure.that("success message is A Spartan is Born!",VssuccesMessage -> VssuccesMessage.body("success",is("A Spartan is Born!")));
        Ensure.that("id is not null",idNotNull ->idNotNull.body("data.id",notNullValue()));
        Ensure.that("name is correct", correctName -> correctName.body("data.name",is(bodyMap.get("name"))));
        Ensure.that("gender is correct", correctGender -> correctGender.body("data.gender",is(bodyMap.get("gender"))));
        Ensure.that("phone is correct", correctGender -> correctGender.body("data.phone",is(bodyMap.get("phone"))));

       // check location header ends with newly generated id
        //get id and save

        String id =lastResponse().jsonPath().getString("data.id");
        Ensure.that("check location header ends with newly generated id",vr -> vr.header("Location",endsWith(id)));
        //2.51
    }

    /*
            we can give name to each execution using name = ""
            and if you want to get index of iteration we can use {index}
            and also if you to include parameter in your test name
            {0} , {1},{2} --> based on the order you provide as argument.

         */
    @ParameterizedTest(name = "New Spartan {index} - name:{0}") // here it gives indeX to row and it brings only column we want
    @CsvFileSource(resources = "/SpartanData.csv",numLinesToSkip = 1)
    public void postSpartanWithCSV(String name,String gender,long phone){
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phone = " + phone);

        //3.13

        Map<String,Object> bodyMap= new LinkedHashMap<>();
        bodyMap.put("name",name);
        bodyMap.put("gender",gender);
        bodyMap.put("phone",phone);

        System.out.println("bodyMap = " + bodyMap);

        //send a post request as a editor
        given().auth()
                .basic("editor","editor")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .log().body()
                .when().post("/spartans")
                .prettyPrint();


        Ensure.that("Status code is 201",validationCode -> validationCode.statusCode(201));
        Ensure.that("content type is Json",VsContent -> VsContent.contentType(ContentType.JSON));
        Ensure.that("success message is A Spartan is Born!",VssuccesMessage -> VssuccesMessage.body("success",is("A Spartan is Born!")));
        Ensure.that("id is not null",idNotNull ->idNotNull.body("data.id",notNullValue()));
        Ensure.that("name is correct", correctName -> correctName.body("data.name",is(bodyMap.get("name"))));
        Ensure.that("gender is correct", correctGender -> correctGender.body("data.gender",is(bodyMap.get("gender"))));
        Ensure.that("phone is correct", correctGender -> correctGender.body("data.phone",is(bodyMap.get("phone"))));

        // check location header ends with newly generated id
        //get id and save

        String id =lastResponse().jsonPath().getString("data.id");
        Ensure.that("check location header ends with newly generated id",vr -> vr.header("Location",endsWith(id)));

    }


}
