package de.webdev.externalrestapi.RickandMorty.api.model;

import de.webdev.externalrestapi.RickandMorty.api.RickandMortyAPICharacter;

import java.util.List;

public record RickAndMortyApiResponse(
        RickAndMortyApiResponseInfo info,
        List<RickandMortyAPICharacter> results
) {
}
