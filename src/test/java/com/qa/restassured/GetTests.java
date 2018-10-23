package com.qa.restassured;

import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

public class GetTests extends BaseClass{
	
	
	@Test
	public void getUsers() {
		ValidatableResponse response = given().
	                                   get(BaseClass.setUp("users_endpoint")).
		                               then().statusCode(200).and().
		                               log().all();
		response.body("page", is(2),"per_page", equalTo(3),"total", is(12), "total_pages", equalTo(4));
		response.body("data.id[0]", is(4), "data.id[1]", is(5), "data.id[2]", is(6));
		response.body("data.first_name[0]", is("Eve") , "data.first_name[1]", is("Charles") , "data.first_name[2]", is("Tracey"));
		response.body("data.last_name[0]", is("Holt"), "data.last_name[1]", is("Morris"), "data.last_name[2]", is("Ramos"));
		response.body("data.avatar[0]", is("https://s3.amazonaws.com/uifaces/faces/twitter/marcoramires/128.jpg"),
			       	  "data.avatar[1]", is("https://s3.amazonaws.com/uifaces/faces/twitter/stephenmoon/128.jpg"), 
				      "data.avatar[2]", is("https://s3.amazonaws.com/uifaces/faces/twitter/bigmancho/128.jpg"));	
	}
	
	@Test
	public void getSingleUser() {
		
		ValidatableResponse response =  given().when().get(BaseClass.setUp("user2_endpoint")).then().statusCode(200).and().log().all();
	    response.body("data.id", is(2),"data.first_name", is("Janet"),"data.last_name",is("Weaver"),"data.avatar",is("https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg"));
	}
	
	@Test
	public void invalidUser() throws Exception {
		ValidatableResponse response = given().when().get(BaseClass.setUp("user23_endpoint")).then().statusCode(404).log().all(); 
		System.out.println("$".getClass()); 	
	}
	
	
	@Test
	public void listUsers()  {
		ValidatableResponse response = when().get(BaseClass.setUp("listresource_endpoint")).then().statusCode(200).log().all();
		response.body("page", is(1),"per_page", equalTo(3),"total", is(12), "total_pages", equalTo(4));
		response.appendRoot("data").
		body("id[0]", is(1), "id[1]", is(2), "id[2]", is(3));
		response.body("name[0]", is("cerulean") , "name[1]", is("fuchsia rose") , "name[2]", is("true red"));
		response.body("year[0]", is(2000), "year[1]", is(2001), "year[2]", is(2002));
		response.body("color[0]", is("#98B2D1"), "color[1]", is("#C74375"), "color[2]", is("#BF1932"));	
	}
	
	@Test
	public void singleUser() {
		ValidatableResponse response = given().when().get(BaseClass.setUp("singleresource_endpoint")).then().statusCode(equalTo(200)).log().all();
		response.appendRoot("data").
		body("id", equalTo(2),"name", equalTo("fuchsia rose"),"year", equalTo(2001), "color", equalTo("#C74375"))
		.detachRoot("data")
		.body("data.pantone_value",is("17-2031"));
		response.assertThat().contentType(ContentType.JSON);
	}
	
	
	
}
   