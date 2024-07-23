package de.webdev.externalrestapi.RickandMorty.api;

import de.webdev.externalrestapi.RickandMorty.api.model.RickAndMortyApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class RickAndMortyApiService {
    private final RestClient restClient;

    public RickAndMortyApiService() {
        restClient = RestClient.builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .build();
    }

    public List<RickandMortyAPICharacter> loadAllCharacters() {
        RickAndMortyApiResponse response = restClient
                .get()
                .uri("character")
                .retrieve()
                .body(RickAndMortyApiResponse.class);
        return response.results();
    }

    public RickandMortyAPICharacter loadCharacterById(int id) {
        return restClient
                .get()
                .uri("character/{id}", id)
                .retrieve()
                .body(RickandMortyAPICharacter.class);
    }

    public List<RickandMortyAPICharacter> loadCharactersByStatus(String status) {
        RickAndMortyApiResponse responseStatus;
        responseStatus = restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                    .path("character")
                    .queryParam("status", status)
                    .build())
                .retrieve()
                .body(RickAndMortyApiResponse.class);
        assert responseStatus != null;
        return responseStatus.results();
    }

    public int countAliveCharactersBySpecies(String species) {
        RickAndMortyApiResponse response = restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("character")
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
