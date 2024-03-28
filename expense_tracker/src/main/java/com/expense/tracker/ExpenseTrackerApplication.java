package com.expense.tracker;

import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@WebServlet
public class ExpenseTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApplication.class, args);
	        String url = "http://192.168.200.27:2789/account-transaction/v1/account/group/tree/list/for/ceo/dashboard"; 
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("auth-head", "d3v3lop3r");

	        // Define the request body
	        String requestBody = "{\n" +
	                "    \"reportId\": 5,\n" +
	                "    \"siteIds\": [\n" +
	                "        114,\n" +
	                "        100,\n" +
	                "        116,\n" +
	                "        117,\n" +
	                "        12\n" +
	                "    ],\n" +
	                "    \"financialYearId\": 5,\n" +
	                "    \"siteId\": 12,\n" +
	                "    \"tokenData\": {\n" +
	                "        \"companyId\": 1,\n" +
	                "        \"userId\": 3\n" +
	                "    }\n" +
	                "}";

	        // Create a new RestTemplate    
	        RestTemplate restTemplate = new RestTemplate();

	        // Create the request entity with headers and body
	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

	        // Make the HTTP POST request
	        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

	        
	        HttpStatus statusCode = responseEntity.getStatusCode();
	        String responseBody = responseEntity.getBody();

	        saveToFile(responseBody, "response.txt");
	        System.out.println("Response status code: " + statusCode);
	        System.out.println("Response body: " + responseBody);
	    }
	
	 private static void saveToFile(String content, String filename) {
	        try {
	            FileWriter writer = new FileWriter(filename);
	            writer.write(content);
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	

}
