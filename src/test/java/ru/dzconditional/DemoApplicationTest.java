package ru.dzconditional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    private final GenericContainer<?> devContainer = new GenericContainer<>("devapp:latest")
            .withExposedPorts(8080);

    @Container
    private final GenericContainer<?> prodContainer = new GenericContainer<>("prodapp:1.0")
            .withExposedPorts(8081);

    @Test
    void devTest(){
        Integer devMappedPort = devContainer.getMappedPort(8080);
        ResponseEntity<String> devResponse = restTemplate.getForEntity("http://localhost:" + devMappedPort + "/", String.class);
        Assertions.assertEquals(HttpStatus.OK, devResponse.getStatusCode());
        Assertions.assertEquals("Current profile is dev", devResponse.getBody());
    }

    @Test
    void prodTest(){
        Integer prodMappedPort = prodContainer.getMappedPort(8081);
        ResponseEntity<String> prodResponse = restTemplate.getForEntity("http://localhost:" + prodMappedPort + "/", String.class);
        Assertions.assertEquals(HttpStatus.OK, prodResponse.getStatusCode());
        Assertions.assertEquals("Current profile is production", prodResponse.getBody());
    }
}
