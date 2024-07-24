package de.webdev.externalrestapi.RickandMorty.api;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class RickAndMortyApiServiceTest {
    private static MockWebServer mockWebServer;

    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    static void setUp  () throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @DynamicPropertySource
    static void backendProperties(DynamicPropertyRegistry registry) {
        registry.add("basic.url", () -> mockWebServer.url("/").toString());
    }

    @Test
        void loadAllCharactersTest() throws Exception {
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

            mockMvc.perform(MockMvcRequestBuilders.get("/api/characters"))
                            .andExpect(status().isOk())
                            .andExpect(content().json("""
                            [
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
                            """));


    }
    };
