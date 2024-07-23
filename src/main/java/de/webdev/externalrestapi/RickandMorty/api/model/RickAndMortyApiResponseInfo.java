package de.webdev.externalrestapi.RickandMorty.api.model;

public record RickAndMortyApiResponseInfo(
        int count,
        int pages,
        String next,
        String prev
) {
}
