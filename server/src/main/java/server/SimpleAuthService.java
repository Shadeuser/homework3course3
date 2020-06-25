package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleAuthService implements AuthService {
    Connection connection;
    Statement statement;


    private class UserData{
        String login;
        String password;
        String nickname;

        public UserData(String login, String password, String nickname) {
            this.login = login;
            this.password = password;
            this.nickname = nickname;
        }
    }

    @Override
    public boolean registration(String login, String password, String nickname) {

        try {
            connectBd();
            String sql = String.format("SELECT * FROM users WHERE login = '%s' OR nickname='%s'", login, nickname );
            ResultSet rs = statement.executeQuery(sql);
            if (!rs.next()) {
                sql = String.format("INSERT INTO users (login, nickname, password) VALUES('%s', '%s', '%s')", login, nickname, password);
                System.out.println(sql);
                statement.executeUpdate(sql);
                return true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            disconnectBd();
        }

        return false;
    }



    @Override
    public String getNicknameByLoginAndPassword(String login, String password){
        try {
            connectBd();
            String sql = String.format("SELECT nickname FROM users WHERE password = '%s' AND login = '%s'", password, login);
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return rs.getString("nickname");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            disconnectBd();
        }

        return null;
    }

    private void connectBd() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:messenger.db");
        statement = connection.createStatement();
    }


    private void disconnectBd() {
        try {
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


}
