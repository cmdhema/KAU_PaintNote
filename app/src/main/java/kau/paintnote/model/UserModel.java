package kau.paintnote.model;

/**
 * Created by Jaewook on 2015-06-22.
 */
public class UserModel {

    private String user_id;
    private String password;

    public UserModel(String id, String password) {
        this.user_id = id;
        this.password = password;
    }

    public UserModel() {

    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
