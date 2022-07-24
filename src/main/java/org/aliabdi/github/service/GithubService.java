package org.aliabdi.github.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.aliabdi.github.http.GitHubClient;
import org.aliabdi.github.http.data.GitHubRepository;
import org.aliabdi.github.http.data.GitHubSearchResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Singleton
public class GithubService {
    private static final Logger LOG = LoggerFactory.getLogger(GithubService.class);
    // To get popular repos, we need to sort them ascending by star
    private static final String FIELD_STAR = "star";
    private static final String ORDER_DESCENDING = "desc";
    private static final String QUERY_PARAM_DELIMITER = " ";
    private static final Pattern QUERY_PARAM_PATTERN = Pattern.compile("^([a-zA-Z]+?)(From|To)?$");

    @Inject
    private GitHubClient gitHubClient;

    public List<GitHubRepository> getPopularRepositories(String body, Map<String, String> parameters, Integer limit) {
        // https://docs.github.com/en/rest/search#constructing-a-search-query
        String githubQuery = String.join(QUERY_PARAM_DELIMITER, StringUtils.defaultString(body), buildFullQueryByParameters(parameters));
        return gitHubClient.getPopularRepositories(githubQuery, FIELD_STAR, limit, ORDER_DESCENDING)
                .getBody()
                .map(GitHubSearchResult::getItems)
                .orElseThrow();
    }

    protected String buildFullQueryByParameters(Map<String, String> parameters) {
        return parameters.entrySet().stream()
                .map(entry -> buildQueryParam(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(QUERY_PARAM_DELIMITER));
    }

    /**
     * Build a query parameter based on the incoming key/value. This method could become handy if we want to extend
     * the service later and support more search parameters. This works by matching the "key" (parameter name) against
     * a pre-defined pattern and extract "From" or "To" out of it to determine the comparison type (lt, gt, eq)
     *
     * @param key   the name of the query parameter
     * @param value of the query parameter
     * @return The query parameter string
     */
    private String buildQueryParam(String key, String value) {
        if (StringUtils.isBlank(value)) {
            return StringUtils.EMPTY;
        }

        Matcher matcher = QUERY_PARAM_PATTERN.matcher(key);
        if (!matcher.find()) {
            LOG.warn("Parameter not recognized: {}", key);
            return String.format("%s:%s", key, value);
        }

        if (matcher.group(2) == null) {
            return String.format("%s:%s", key, value);
        } else {
            return switch (matcher.group(2)) {
                case "From" -> String.format("%s:>%s", matcher.group(1), value);
                case "To" -> String.format("%s:<%s", matcher.group(1), value);
                default -> String.format("%s:%s", key, value);
            };
        }
    }
}
