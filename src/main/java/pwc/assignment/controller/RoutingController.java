package pwc.assignment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwc.assignment.controller.dto.RouteResponse;
import pwc.assignment.service.RoutingService;

@RestController
@RequestMapping("/api/v1/routing")
@RequiredArgsConstructor
@Slf4j
public class RoutingController {

  private final RoutingService routingService;

  @GetMapping("/{origin}/{destination}")
  public ResponseEntity<RouteResponse> route(
      @PathVariable final String origin,
      @PathVariable final String destination) {

    log.info("trying to find shortest land route between {} and {}", origin, destination);
    final var route = routingService.findRoute(origin, destination);
    log.info("found shortest land route: {}", route);
    return ResponseEntity.ok(new RouteResponse(route));
  }
}
