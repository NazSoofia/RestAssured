package com.qa.restassured;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.qa.pojo.Users;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.response.ValidatableResponse;
import junit.framework.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class PostTests extends BaseClass{

	Map<String, String> map = new HashMap<String, String>();
	
	@Test
	public void createUser() {
		
		map.put("Content-Type", "application/json");
		map.put("Accept", "application/json");
		
		JSONObject request = new JSONObject();
		request.put("name", "morpheus");
		request.put("job", "leader");
		
		ValidatableResponse response = given().
		headers(map).body(request).when().
		post(BaseClass.setUp("post_endpoint")).then().statusCode(RESPONSE_STATUS_CODE_201).log().all();
		
		
		Users user = response.extract().as(Users.class);
		Assert.assertEquals(request.get("name"), user.getName());
	}
	
	
	@Test
	public void registerUser() throws FileNotFoundException, IOException, ParseException, URISyntaxException {
		
		map.put("Content-Type", "application/json");
		map.put("Accept", "application/json"); 
		
		JSONParser parser = new JSONParser();
		JSONObject request = (JSONObject) parser.parse(new FileReader("./src/main/java/com/qa/config/jsonparser.json"));

		System.out.println(BaseClass.setUp("register_endpoint")); 
		ValidatableResponse response = given().headers(map).body(request).
				                       when().post(BaseClass.setUp("register_endpoint")).
				                       then().statusCode(201).log().all();
		
        response.body("token", equalTo("QpwL5tke4Pnpja7X"));
	}
	
}
