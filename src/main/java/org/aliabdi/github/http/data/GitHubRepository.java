package org.aliabdi.github.http.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GitHubRepository {
    private int id;
    private String nodeId;
    private String name;
    private String fullName;
    @JsonProperty("private")
    private boolean isPrivate;
    private GitHubRepositoryOwner owner;
    private String htmlUrl;
    private String description;
    private boolean fork;
    private String url;
    private String forksUrl;
    private String keysUrl;
    private String collaboratorsUrl;
    private String teamsUrl;
    private String hooksUrl;
    private String issueEventsUrl;
    private String eventsUrl;
    private String assigneesUrl;
    private String branchesUrl;
    private String tagsUrl;
    private String blobsUrl;
    private String gitTagsUrl;
    private String gitRefsUrl;
    private String treesUrl;
    private String statusesUrl;
    private String languagesUrl;
    private String stargazersUrl;
    private String contributorsUrl;
    private String subscribersUrl;
    private String subscriptionUrl;
    private String commitsUrl;
    private String gitCommitsUrl;
    private String commentsUrl;
    private String issueCommentUrl;
    private String contentsUrl;
    private String compareUrl;
    private String mergesUrl;
    private String archiveUrl;
    private String downloadsUrl;
    private String issuesUrl;
    private String pullsUrl;
    private String milestonesUrl;
    private String notificationsUrl;
    private String labelsUrl;
    private String releasesUrl;
    private String deploymentsUrl;
    private Date createdAt;
    private Date updatedAt;
    private Date pushedAt;
    private String gitUrl;
    private String sshUrl;
    private String cloneUrl;
    private String svnUrl;
    private String homepage;
    private int size;
    private int stargazersCount;
    private int watchersCount;
    private String language;
    private boolean hasIssues;
    private boolean hasProjects;
    private boolean hasDownloads;
    private boolean hasWiki;
    private boolean hasPages;
    private int forksCount;
    private boolean archived;
    private boolean disabled;
    private int openIssuesCount;
    private boolean allowForking;
    private boolean isTemplate;
    private boolean webCommitSignoffRequired;
    private String visibility;
    private int forks;
    private int openIssues;
    private int watchers;
    private String defaultBranch;
    private double score;
}
