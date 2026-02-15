package pwc.assignment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pwc.assignment.exception.NoLandRouteFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class RoutingService {

  private final CountryLoaderService countryLoaderService;

  public List<String> findRoute(final String origin, final String destination) {
    var graph = countryLoaderService.getGraph();

    if (!graph.containsKey(origin) || !graph.containsKey(destination)) {
      throw new NoLandRouteFoundException(origin, destination);
    }

    Queue<List<String>> queue = new LinkedList<>();
    var visited = new HashSet<>();

    queue.add(List.of(origin));
    visited.add(origin);

    while (!queue.isEmpty()) {
      var path = queue.poll();
      var lastCountry = path.getLast();

      if (lastCountry.equals(destination)) {
        return path;
      }

      for (var neighbor : graph.getOrDefault(lastCountry, List.of())) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);

          var newPath = new ArrayList<>(path);
          newPath.add(neighbor);
          queue.add(newPath);
        }
      }
    }

    throw new NoLandRouteFoundException(origin, destination);
  }
}
