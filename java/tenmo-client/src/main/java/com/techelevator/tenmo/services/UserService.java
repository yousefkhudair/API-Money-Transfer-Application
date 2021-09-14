package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

public class UserService {
    private RestTemplate restTemplate = new RestTemplate();
    private String BASE_URL;
    public  static String AUTH_TOKEN= "";

    public UserService(String url){this.BASE_URL = url;}

    public User[] getAllUsers(String token){

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<User[]> response
                = restTemplate.exchange(BASE_URL +"user" , HttpMethod.GET, entity, User[].class);


        return response.getBody();
    }
}
