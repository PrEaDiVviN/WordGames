package services;


import rest.RestClient;

public class UserService {
    private static RestClient restClient = new RestClient();

    public void updateScore(String userName) {
        restClient.callUpdateScore(userName);
    }

    public Integer getScore(String username) {
        return restClient.callGetScoreApi(username);
    }
}