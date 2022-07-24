package org.aliabdi.github;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.aliabdi.github.api.data.GitHubRepositoriesDTO;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class GithubIntegrationTest {

    @Inject
    @Client("/github")
    HttpClient client;

    @Test
    void testApiWithLanguageFilter() {
        HttpRequest<String> request = buildHttpRequest(Map.of("maxResult", "5", "language", "C#"));

        List<GitHubRepositoriesDTO> response = client.toBlocking().retrieve(request, Argument.listOf(GitHubRepositoriesDTO.class));
        assertEquals(5, response.size());

        GitHubRepositoriesDTO sample = response.get(3);
        assertEquals("v2rayN", sample.getName());
        assertEquals("https://github.com/2dust/v2rayN", sample.getUrl());
        assertEquals("2dust", sample.getOwner());
        assertEquals("Tue Jul 30 05:47:24 CEST 2019", sample.getCreatedAt());
        assertEquals(30564, sample.getRatingScore());
        assertEquals("C#", sample.getLanguage());
    }

    @Test
    void testApiWithMultiFiltersAndBody() {
        HttpRequest<String> request = buildHttpRequest(Map.of("maxResult", "10",
                "body", "Cloud",
                "createdFrom", "2021-01-01",
                "archived", "false"));

        List<GitHubRepositoriesDTO> response = client.toBlocking().retrieve(request, Argument.listOf(GitHubRepositoriesDTO.class));
        assertEquals(10, response.size());

        GitHubRepositoriesDTO sample = response.get(1);
        assertEquals("PointCloudsFusion", sample.getName());
        assertEquals("https://github.com/mahsunbngl/PointCloudsFusion", sample.getUrl());
        assertEquals("mahsunbngl", sample.getOwner());
        assertEquals("Wed Aug 18 16:25:39 CEST 2021", sample.getCreatedAt());
        assertEquals(6, sample.getRatingScore());
        assertEquals("C++", sample.getLanguage());
    }

    private HttpRequest<String> buildHttpRequest(Map<String, String> params) {
        UriBuilder uriBuilder = UriBuilder.of("/getPopularRepositories");
        params.forEach(uriBuilder::queryParam);
        return HttpRequest.GET(uriBuilder.build());
    }

}
