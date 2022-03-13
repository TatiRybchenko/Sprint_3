package sprint3;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


import sptint3.Courier;
import sptint3.CourierClient;
import lombok.Data;
import sptint3.CourierCredentials;


public class LoginCourierTest {

   private CourierClient courierClient;
   private Courier courier;
   private int courierId;


    @Before
    public void setUp() {
        courierClient = new CourierClient();
      //  Courier courier = Courier.getRandom();
     //   courierClient.create(courier);
    }

    @After
    public void tearDown(){
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Create new courier")
    @Description("Create new courier and  what can login")
    public void courierCanLoginWithValidCredentials() {
        Courier courier = Courier.builder()
                .
        boolean isCreated = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));
        //ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
       // int statusCode = loginResponse.extract().statusCode();
      //  courierId = loginResponse.extract().path("id");

        System.out.println(courier.getLogin());
        System.out.println(courier.getPassword());
        System.out.println(courier.getFirstName());

        assertThat("Курьер выполнил Логин",isCreated);
        assertThat("ID Курьера некорректно",courierId,is(not(0)));

    }

}
