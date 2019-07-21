package com.dotsub.fileupload;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { FileuploadApplication.class }, webEnvironment = WebEnvironment.DEFINED_PORT)
public class FileuploadApplicationTests {

  private static final String API_ROOT = "http://localhost:8080/files";

  @Value("classpath:test.txt")
  Resource testFile;

  @Test
  public void getAllFiles() {
    Response response = RestAssured.get(API_ROOT);

    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
  }

  @Test
  public void uploadFile_success() throws IOException {
    Response response = RestAssured.given()
        .formParam("title", "Title")
        .formParam("desc", "Description")
        .formParam("date", "01/01/2000")
        .multiPart("file", testFile.getFile())
        .when()
        .post(API_ROOT);

    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
  }

  @Test
  public void uploadFile_missingFields() throws IOException {
    Response response = RestAssured.given()
        .formParam("title", "Title")
        .when()
        .post(API_ROOT);

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
  }

  @Test
  public void uploadFile_invalidData() throws IOException {
    Response response = RestAssured.given()
        .formParam("title", "Title")
        .formParam("desc", "Description")
        .formParam("date", "INVALID_DATE")
        .multiPart("file", testFile.getFile())
        .when()
        .post(API_ROOT);

    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
  }

}
