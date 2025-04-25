package client;

import enw.ParamDto;
import enw.ParamHitDto;
import enw.ViewStats;
import exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RestStatClient implements StatClient {
    private final String statUrl;
    private final RestClient restClient;


    public RestStatClient(@Value("${client.url}") String statUrl) {
        this.statUrl = statUrl;
        this.restClient = RestClient.builder()
                .baseUrl(statUrl)
                .build();
    }

    @Override
    public void hit(String app, String uri, String ip) {
        ParamHitDto dto = new ParamHitDto();
        dto.setIp(ip);
        dto.setTimestamp(LocalDateTime.now());
        dto.setUri(uri);
        dto.setApp(app);
        restClient.post()
                .uri("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .onStatus(status -> status != HttpStatus.CREATED, (request, response) -> {
                    throw new InvalidRequestException(response.getStatusCode().value() + ": " + response.getBody());
                });
    }

    @Override
    public List<ViewStats> getStat(ParamDto paramDto) {
        return restClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder
                            .path("/stats")
                            .queryParam("start", paramDto.getStart().toString())
                            .queryParam("end", paramDto.getEnd().toString());
                    List<String> uris = paramDto.getUris();
                    if (uris != null && !uris.isEmpty()) {
                        builder.queryParam("uris", uris);
                    }
                    builder.queryParam("unique", paramDto.getUnique());
                    return builder.build();
                })
                .retrieve()
                .onStatus(status -> status != HttpStatus.OK, (request, response) -> {
                    throw new InvalidRequestException(response.getStatusCode().value() + ": " + response.getBody());
                })
                .body(ParameterizedTypeReference.forType(List.class));
    }
}