package services;

import rest.RestClient;

public class RegisterService {
    private static RestClient restClient = new RestClient();

    public boolean register(String userName, String password, String confirmPass) {
        return restClient.callRegisterApi(userName,password,confirmPass);
    }
}