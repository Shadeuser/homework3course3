package server;

import java.sql.SQLException;
import java.sql.Statement;

public interface AuthService {




    String getNicknameByLoginAndPassword(String login, String password);
    boolean registration(String login, String password, String nickname);
}
