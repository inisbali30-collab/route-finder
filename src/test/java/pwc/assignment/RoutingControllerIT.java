package pwc.assignment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import pwc.assignment.controller.dto.RouteResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoutingControllerIT {

  private static final String URL = "http://localhost:";

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  private String baseUrl(final String path) {
    return URL + port + path;
  }

  @Test
  @SneakyThrows
  void shouldReturnRouteWhenLandRouteExists() {

    final var response =
        restTemplate.getForEntity(
            baseUrl("/api/v1/routing/CZE/ITA"),
            String.class
        );

    final var routeResponse = objectMapper.readValue(response.getBody(), RouteResponse.class);
    final var route = routeResponse.route();

    assertEquals(200, response.getStatusCode().value());
    assertEquals(3, route.size());
    assertThat(route).containsExactly("CZE", "AUT", "ITA");
  }

  @Test
  void shouldReturnBadRequestWhenNoLandRouteExists() {

    final var response =
        restTemplate.getForEntity(
            baseUrl("/api/v1/routing/USA/AUS"),
            String.class
        );

    assertEquals(400, response.getStatusCode().value());
    assertThat(response.getBody()).contains("no land route found from USA to AUS");
  }

  @Test
  void shouldReturnBadRequestWhenCountryDoesNotExist() {

    final var response =
        restTemplate.getForEntity(
            baseUrl("/api/v1/routing/XXX/ITA"),
            String.class
        );

    assertEquals(400, response.getStatusCode().value());
  }
}
