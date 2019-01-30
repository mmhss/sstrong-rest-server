package com.gates.standstrong.domain.project;

import com.gates.standstrong.base.BaseResource;
import com.gates.standstrong.domain.mother.MotherResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping(value = BaseResource.BASE_URL + ProjectResource.RESOURCE_URL)
public class ProjectResource extends BaseResource<Project, ProjectDto> {

    public static final String RESOURCE_URL = "/projects";

    public ProjectService projectService;

    @Inject
    public ProjectResource(ProjectService projectService, ProjectMapper projectMapper) {
        super(projectService, projectMapper, Project.class, QProject.project);
    }
}
