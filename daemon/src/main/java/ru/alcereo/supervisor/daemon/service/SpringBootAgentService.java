package ru.alcereo.supervisor.daemon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alcereo.supervisor.core.agents.DefaultShellAgent;
import ru.alcereo.supervisor.core.runners.springboot.SpringBootTomcatJavaJarRunner;
import ru.alcereo.supervisor.daemon.model.AgentInfoDTO;
import ru.alcereo.supervisor.daemon.model.SpringBootAgentInfoDTO;

import java.io.File;
import java.io.IOException;

/**
 * Created by alcereo on 23.07.17.
 */
@Service
public class SpringBootAgentService {

    private MainAgentsService mainAgentsService;

    @Autowired
    public SpringBootAgentService(MainAgentsService mainAgentsService) {
        this.mainAgentsService = mainAgentsService;
    }

    public AgentInfoDTO runNewAgent(SpringBootAgentInfoDTO agentInfoDTO) {

        SpringBootTomcatJavaJarRunner runner =
                new SpringBootTomcatJavaJarRunner(
                        new File(agentInfoDTO.getProjectDir())
                );

        try {
            DefaultShellAgent agent = runner.runNewAgent();
            return mainAgentsService.registerAndGetInfo(agent, agentInfoDTO.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
