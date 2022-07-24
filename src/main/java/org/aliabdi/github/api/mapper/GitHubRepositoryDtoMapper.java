package org.aliabdi.github.api.mapper;

import jakarta.inject.Singleton;
import org.aliabdi.github.api.data.GitHubRepositoriesDTO;
import org.aliabdi.github.http.data.GitHubRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

@Singleton
public class GitHubRepositoryDtoMapper {

    private final ModelMapper modelMapper;

    public GitHubRepositoryDtoMapper() {
        modelMapper = new ModelMapper();
        setupCustomTypeMappers();
    }

    public GitHubRepositoriesDTO mapDataToDTO(GitHubRepository data) {
        return modelMapper.map(data, GitHubRepositoriesDTO.class);
    }

    public void setupCustomTypeMappers() {
        TypeMap<GitHubRepository, GitHubRepositoriesDTO> githubRepositoryMapper = this.modelMapper
                .createTypeMap(GitHubRepository.class, GitHubRepositoriesDTO.class);
        githubRepositoryMapper.addMapping(GitHubRepository::getHtmlUrl, GitHubRepositoriesDTO::setUrl);
        githubRepositoryMapper.addMapping(GitHubRepository::getStargazersCount, GitHubRepositoriesDTO::setRatingScore);
        githubRepositoryMapper.addMapping(source -> source.getOwner().getLogin(), GitHubRepositoriesDTO::setOwner);
    }
}
