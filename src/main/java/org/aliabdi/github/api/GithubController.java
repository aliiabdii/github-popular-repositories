package org.aliabdi.github.api;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.inject.Inject;
import org.aliabdi.github.api.data.GitHubRepositoriesDTO;
import org.aliabdi.github.api.mapper.GitHubRepositoryDtoMapper;
import org.aliabdi.github.http.data.GitHubRepository;
import org.aliabdi.github.service.GithubService;

import java.util.List;
import java.util.Map;

@Controller(value = "/github")
public class GithubController {
    private static final String DEFAULT_MAX_RESULT = "10";
    private static final String PARAMETER_MAX_RESULT = "maxResult";
    private static final String PARAMETER_CONTAINS = "contains";

    @Inject
    private GithubService githubService;

    @Inject
    private GitHubRepositoryDtoMapper gitHubRepositoryMapper;

    @Get("/getPopularRepositories{?params*}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Returns a list of popular GitHub repositories based on the given criteria")
    public HttpResponse<List<GitHubRepositoriesDTO>> getPopularRepositories(
            @QueryValue(defaultValue = DEFAULT_MAX_RESULT) @Parameter(description = "Maximum number of repositories to return") final Integer maxResult,
            @QueryValue("params") @Nullable @Parameter(description = "Additional parameters to search for (like createdFrom, language, etc...)") final Map<String, String> parameters
    ) {
        // Remove "maxResult" and "contains" from parameters as they will be handled differently
        String contains = "";
        if (parameters != null) {
            parameters.remove(PARAMETER_MAX_RESULT);
            contains = parameters.remove(PARAMETER_CONTAINS);
        }

        List<GitHubRepository> gitHubRepositories = githubService.getPopularRepositories(contains, parameters, maxResult);

        return HttpResponse.ok(gitHubRepositories.stream().map(gitHubRepositoryMapper::mapDataToDTO).toList());
    }
}
