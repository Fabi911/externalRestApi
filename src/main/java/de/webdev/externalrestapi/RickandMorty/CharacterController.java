package de.webdev.externalrestapi.RickandMorty;


import de.webdev.externalrestapi.RickandMorty.api.RickAndMortyApiService;
import de.webdev.externalrestapi.RickandMorty.api.RickandMortyAPICharacter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class CharacterController {
    private final RickAndMortyApiService rickAndMortyApiService;

    @GetMapping("characters")
    public List<RickandMortyAPICharacter> getCharacters(@RequestParam(required = false) String status) {
        if (status != null) {
            return rickAndMortyApiService.loadCharactersByStatus(status).results();
        } else {
            return rickAndMortyApiService.loadAllCharacters().results();
        }
    }

    @GetMapping("characters/{id}")
    public RickandMortyAPICharacter getCharacterById(@PathVariable int id) {
        return rickAndMortyApiService.loadCharacterById(id);
    }

    @GetMapping("species-statistic")
    public String getSpeciesStatistic(@RequestParam String species) {
        int count = rickAndMortyApiService.countAliveCharactersBySpecies(species);
        return String.valueOf(count);
    }

}
