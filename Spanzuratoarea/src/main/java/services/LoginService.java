package services;


import rest.RestClient;

public class LoginService {
    private static RestClient restClient = new RestClient();

    public boolean login(String userName, String password) {
        return restClient.callLoginApi(userName,password);
    }
}