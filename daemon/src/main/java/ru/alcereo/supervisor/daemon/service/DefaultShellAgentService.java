package ru.alcereo.supervisor.daemon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alcereo.supervisor.core.agents.DefaultShellAgent;
import ru.alcereo.supervisor.core.runners.DefaultShellRunner;
import ru.alcereo.supervisor.daemon.model.AgentInfoDTO;
import ru.alcereo.supervisor.daemon.model.AgentRunInfoDTO;

import java.io.IOException;

/**
 * Created by alcereo on 22.07.17.
 */
@Service
public class DefaultShellAgentService {

    private MainAgentsService mainAgentsService;

    @Autowired
    public DefaultShellAgentService(MainAgentsService mainAgentsService) {
        this.mainAgentsService = mainAgentsService;
    }

    public AgentInfoDTO runNewAgent(AgentRunInfoDTO agentInfo) {

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(agentInfo.getCommand());

        DefaultShellRunner runner = new DefaultShellRunner(processBuilder);

        try {
            DefaultShellAgent agent = runner.runNewAgent();
            return mainAgentsService.registerAndGetInfo(agent, agentInfo.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
