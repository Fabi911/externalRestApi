package de.webdev.externalrestapi.RickandMorty.api;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
public class RickAndMortyApiServiceTest {
    private static MockWebServer mockWebServer;


    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @DynamicPropertySource
    static void backendProperties(DynamicPropertyRegistry registry) {
        registry.add("${basic.url}", () -> mockWebServer.url("/").toString());
    }

    @Test
    void loadAllCharacters() {
       mockWebServer.enqueue(new MockResponse()
               .setResponseCode(200)
               .addHeader("Content-Type", "application/json")
               .setBody("""
                       {
                           "info": {
                           },
                           "results": [
                               {
                                   "id": 1,
                                   "name": "Rick Sanchez",
                                   "status": "Alive"
                               },
                               {
                                   "id": 2,
                                   "name": "Morty Smith",
                                   "status": "Alive"
                               }
                           ]
                       }
                       """));
    }
}