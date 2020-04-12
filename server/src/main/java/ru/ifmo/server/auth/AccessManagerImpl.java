package ru.ifmo.server.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ifmo.server.data.entities.User;

import java.util.Calendar;
import java.util.Date;

@Component
public class AccessManagerImpl implements AccessManager {

    private static final long ONE_MINUTE = 60_000;

    private static final long EXPIRES_AFTER = ONE_MINUTE * 30;

    @Autowired
    private AccessTokenService accessTokenService;


    @Override
    public AccessToken registerTokenForUser(User user) {
        long currentTimeMills = Calendar.getInstance().getTimeInMillis();
        AccessToken accessToken = new AccessToken(user.getId(), new Date(currentTimeMills + EXPIRES_AFTER));
        //Register new token in service and delete all previous
        accessTokenService.deleteAccessToken(user.getId());
        accessTokenService.save(accessToken);
        return accessToken;
    }
}
