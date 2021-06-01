package controller;

import dao.Users;
import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("user")

public class UsersController {
    private Users usersDao = new Users();

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        User user = usersDao.getUserById(id);
        return user;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String> userInf) {
        String username = userInf.get("username");
        String password = userInf.get("password");
        if(usersDao.login(username,password))
            return new ResponseEntity<>(
                    "Logged in successfully!", HttpStatus.OK);
        else
            return new ResponseEntity<>(
                    "Unauthorized!", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<String> register(@RequestBody Map<String,String> userInf) {
        String username = userInf.get("username");
        String password = userInf.get("password");
        String confirmPassword = userInf.get("confirmPass");
        if(usersDao.register(username,password,confirmPassword))
            return new ResponseEntity<>(
                    "Registered successfully!", HttpStatus.OK);
        else
            return new ResponseEntity<>(
                    "Unauthorized!", HttpStatus.OK);
    }

    @PutMapping("/score/{username}")
    public void updateScore(@PathVariable String username) {
        usersDao.updateScore(username);
    }

    @GetMapping("/getScore/{username}")
    public Integer getScore(@PathVariable String username) {
        return usersDao.getScore(username);
    }
}