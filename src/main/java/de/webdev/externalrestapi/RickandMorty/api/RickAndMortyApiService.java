package de.webdev.externalrestapi.RickandMorty.api;

import de.webdev.externalrestapi.RickandMorty.api.model.RickAndMortyApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class RickAndMortyApiService {
    private final RestClient restClient;


    public RickAndMortyApiService(@Value("${basic.url}") String basicUrl) {
        restClient = RestClient.builder()
                .baseUrl(basicUrl)
                .build();
    }

    public RickAndMortyApiResponse loadAllCharacters() {
        RickAndMortyApiResponse response = restClient
                .get()
                .uri("api/character")
                .retrieve()
                .body(RickAndMortyApiResponse.class);

        return response;
    }

    public RickandMortyAPICharacter loadCharacterById(int id) {
        return restClient
                .get()
                .uri("api/character/{id}", id)
                .retrieve()
                .body(RickandMortyAPICharacter.class);
    }

    public RickAndMortyApiResponse loadCharactersByStatus(String status) {
        RickAndMortyApiResponse responseStatus;
        responseStatus = restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                    .path("api/character")
                    .queryParam("status", status)
                    .build())
                .retrieve()
                .body(RickAndMortyApiResponse.class);
        assert responseStatus != null;
        return responseStatus;
    }

    public int countAliveCharactersBySpecies(String species) {
        RickAndMortyApiResponse response = restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("api/character")
                        .queryParam("species", species)
                        .build())
                .retrieve()
                .body(RickAndMortyApiResponse.class);
        assert response != null;
        return (int) response.results().stream()
                .filter(character -> "alive".equalsIgnoreCase(character.status()))
                .count();
    }
}
