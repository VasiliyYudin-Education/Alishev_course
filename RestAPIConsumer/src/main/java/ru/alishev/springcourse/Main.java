package ru.alishev.springcourse;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> sendJson = new HashMap<String, String>();
        sendJson.put("name", "Test name");
        sendJson.put("job", "Test job");
        HttpEntity<Map<String, String>> request = new HttpEntity<Map<String, String>>(sendJson);
        String URL="https://reqres.in/api/users/";
       String response = restTemplate.postForObject(URL,request, String.class);
        System.out.println(response);
        
//        String URL="https://reqres.in/api/users/2";
//       String response = restTemplate.getForObject(URL, String.class);
//        System.out.println(response);
    }
}