package ru.ifmo.server.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import ru.ifmo.server.auth.access.AccessToken;
import ru.ifmo.server.auth.exception.InvalidPasswordException;
import ru.ifmo.server.auth.exception.InvalidUserException;
import ru.ifmo.server.data.entities.User;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class AuthControllerTest {
    private final AuthManager testAuthManager = new AuthManager() {

        private final Map<String, User> userMap = new HashMap<>();

        @Override
        public User signUp(String login, String password) throws InvalidUserException {
            if (userMap.containsKey(login))
                throw new InvalidUserException();
            User user = new User();
            user.setLogin(login);
            user.setHashedPassword(password);
            user.setId(userMap.size() + 1);
            userMap.put(login, user);
            return user;
        }

        @Override
        public AccessToken signIn(String login, String password) throws InvalidUserException {
            if (!userMap.containsKey(login))
                throw new InvalidUserException();
            if (!userMap.get(login).getHashedPassword().equals(password))
                throw new InvalidPasswordException();
            return null;
        }

        @Override
        public void logout(String accessToken, int usedId) {
        }
    };

    private final AuthController authController = new AuthController(testAuthManager);

    @Test
    void testSignUp() {
        String[] userNames = new String[] { "A", "B", "C", "D" };
        String[] pwds = new String[] { "1", "2", "3", "4", "5" };
        for (int i = 0; i < userNames.length; i++) {
            ResponseEntity<String> response = authController.signUp(userNames[i], pwds[i]);
            assert response.getBody() != null;
            assert response.getBody().contains(userNames[i]);
        }
        for (String userName : userNames) {
            ResponseEntity<String> response = authController.signUp(userName, userName);
            assert response.getBody() != null;
            assert response.getBody().contains("1");
        }
    }

    @Test
    void testSignIn() {
        String[] userNames = new String[] { "A", "B", "C", "D" };
        String[] pwds = new String[] { "1", "2", "3", "4", "5" };
        for (int i = 0; i < userNames.length; i++) {
            ResponseEntity<String> response = authController.signUp(userNames[i], pwds[i]);
        }
        for (String userName : userNames) {
            ResponseEntity<String> response = authController.signIn(userName, userName);
            assert response.getBody() != null;
            assert response.getBody().contains("1");
        }
        for (String pwd : pwds) {
            ResponseEntity<String> response = authController.signIn(pwd, pwd);
            assert response.getBody() != null;
            assert response.getBody().contains("2");
        }
    }

    @Test
    void testLogout() {
        //Useless Test
    }
}
