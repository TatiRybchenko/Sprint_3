package sptint3;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@Builder
public class Courier {

    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Step( "Create a random courier")
    public static Courier getRandom(){

        final String courierLogin = RandomStringUtils.randomAlphabetic(10);
        final String courierPassword = RandomStringUtils.randomAlphabetic(10);
        final String courierFirstName = RandomStringUtils.randomAlphabetic(10);

        Allure.addAttachment("Login courier: ", courierLogin);
        Allure.addAttachment("Password courier:", courierPassword);
        Allure.addAttachment("FirstName courier", courierFirstName);

        return new Courier(courierLogin, courierPassword, courierFirstName);
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
