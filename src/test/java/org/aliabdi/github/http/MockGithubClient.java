package org.aliabdi.github.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Primary;
import io.micronaut.core.io.IOUtils;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Singleton;
import org.aliabdi.github.http.data.GitHubSearchResult;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Singleton
@Primary
public class MockGithubClient implements GitHubClient {
    @Override
    public HttpResponse<GitHubSearchResult> getPopularRepositories(String q, String sort, int perPage, String order) {
        try {
            String githubResponsePath = String.format("repositories/%s.json", getFileName(q, sort, perPage, order));
            String responseJson = loadResource(githubResponsePath);
            ObjectMapper mapper = new ObjectMapper();
            return HttpResponse.ok(mapper.readValue(responseJson, GitHubSearchResult.class));
        } catch (IOException ex) {
            return HttpResponse.serverError();
        }
    }

    private String getFileName(String q, String sort, int perPage, String order) {
        String combined = String.join("", q, String.valueOf(perPage), sort, order);
        String nameEncoded = Base64.encodeBase64URLSafeString(combined.getBytes());
        return String.format("repositories_%s", nameEncoded.substring(nameEncoded.length() - 7));
    }

    private String loadResource(String resourcePath) throws IOException {
        String resourceString;

        try (InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            assert resourceStream != null;
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceStream))) {
                resourceString = IOUtils.readText(bufferedReader);
            }
        }

        return resourceString;
    }
}
