package org.aliabdi.github.http;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import org.aliabdi.github.http.data.GitHubSearchResult;

@Client("${githubpopular.http.urls.github}")
public interface GitHubClient {

    /**
     * Make a request to GitHub Popular Repositories endpoint.
     * @param q is the query, for example "created:>2019-01-10"
     * @param sort will sort the results based on a field
     * @param order is the order of the records ("desc" or "asc")
     * @return
     */
    @Get("/search/repositories")
    @Header(name = "Accept", value = "application/vnd.github+json")
    @Header(name = "User-Agent", value = "aliiabdii")
    HttpResponse<GitHubSearchResult> getPopularRepositories(@NonNull @QueryValue String q,
                                                            @QueryValue String sort,
                                                            @QueryValue("per_page") int perPage,
                                                            @QueryValue(defaultValue = "asc") String order);
}
