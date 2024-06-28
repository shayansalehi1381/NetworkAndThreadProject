package shared.Model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@JsonTypeName("user")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public class User{
    private String username;
    private String password;
    private UUID id;

    private List<File> files;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        id = UUID.randomUUID();
    }

    // Default constructor for deserialization
    public User() {}


    public UUID getId() {
        return id;
    }


    public void setId(UUID id) {
        this.id = id;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
