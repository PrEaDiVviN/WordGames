package rest;

import model.Word;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


public class RestClient {
    static RestTemplate restTemplate = new RestTemplate();
    private static final String LOGIN_API = "http://localhost:8082/user/login";
    private static final String REGISTER_API = "http://localhost:8082/user/register";
    private static final String GET_WORD_BY_ID = "http://localhost:8082/word/getById/{id}";

    public static boolean callLoginApi(String username, String password) {
        Map<String,String> parameter = new HashMap<>();
        parameter.put("username",username);
        parameter.put("password",password);
        ResponseEntity<String> response = restTemplate.postForEntity(LOGIN_API, parameter, String.class);
        if(response.getBody().equals("Logged in successfully!"))
            return true;
        return false;
    }

    public static boolean callRegisterApi(String username, String password, String confirmPass) {
        Map<String,String> parameter = new HashMap<>();
        parameter.put("username",username);
        parameter.put("password",password);
        parameter.put("confirmPass",confirmPass);
        ResponseEntity<String> response = restTemplate.postForEntity(REGISTER_API, parameter, String.class);
        if(response.getBody().equals("Registered successfully!"))
            return true;
        return false;
    }

    public static Word callGetWordById(Integer id) {
        Map<String,Integer> parameter = new HashMap<>();
        parameter.put("id",id);
        Word word = restTemplate.getForObject(GET_WORD_BY_ID, Word.class, parameter);
        return word;
    }
}