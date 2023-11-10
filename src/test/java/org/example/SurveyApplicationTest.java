package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyApplicationTest {

    @Value(value = "${local.server.port}")

    private int port;

    @Test
    public void contextLoads() {
        System.out.println(port);
        //  TODO assert controller is created
    }


}
