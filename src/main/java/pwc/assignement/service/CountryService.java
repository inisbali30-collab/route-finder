package pwc.assignement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pwc.assignement.model.Country;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CountryService {

  private final ObjectMapper mapper = new ObjectMapper();

  @Getter
  private final Map<String, List<String>> graph = new HashMap<>();

  @PostConstruct
  public void init() throws IOException {
    log.info("loading country graph..");
    var countries = mapper.readValue(
        URI.create("https://raw.githubusercontent.com/mledoze/countries/master/countries.json").toURL(),
        Country[].class
    );

    for (var country : countries) {
      graph.put(country.cca3(), country.borders() != null
          ? country.borders()
          : List.of());
    }
    log.info("{} countries loaded", countries.length);
  }

}