package org.aliabdi.github.api.data;

import lombok.Data;

@Data
public class GitHubRepositoriesDTO {
    private String name;
    private String url;
    private String owner;
    private String createdAt;
    private long ratingScore;
    private String language;
}
