package org.aliabdi.github.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GithubServiceTest {

    @InjectMocks
    private final GithubService githubService = new GithubService();

    @Test
    void testBuildFullQueryByParameters() {
        Map<String, String> testParamMap = Map.of(
                "language", "java",
                "createdFrom", "2020-01-01",
                "watchersTo", "9000"
        );

        String fullQuery = githubService.buildFullQueryByParameters(testParamMap);

        List<String> querySplit = Arrays.stream(fullQuery.split(" ")).toList();
        assertEquals(3, querySplit.size());
        assertTrue(querySplit.contains("language:java"));
        assertTrue(querySplit.contains("created:>2020-01-01"));
        assertTrue(querySplit.contains("watchers:<9000"));
    }
}