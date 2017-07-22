package ru.alcereo.supervisor.daemon.service;

import org.springframework.stereotype.Service;
import ru.alcereo.supervisor.core.agents.Agent;
import ru.alcereo.supervisor.daemon.model.AgentInfoDTO;
import ru.alcereo.supervisor.daemon.model.AgentWrapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alcereo on 22.07.17.
 */
@Service
public class MainAgentsService {

    private List<AgentWrapper> agentsList = new ArrayList<>();


    public AgentInfoDTO registerAndGetInfo(Agent agent, String name) {
        UUID uuid = UUID.randomUUID();
        AgentWrapper agentWrapper = new AgentWrapper(
                uuid,
                agent
        );
        agentWrapper.setName(name);
        agentsList.add(
                agentWrapper
        );

        return agentWrapper.mapToAgentInfoDTO();
    }


    public List<AgentInfoDTO> getAgentsList() {
        return agentsList
                .stream()
                .map(AgentWrapper::mapToAgentInfoDTO)
                .collect(Collectors.toList());
    }

    public void stopAgent(UUID agentUUID) {
        agentsList.stream()
                .filter(agent -> agent.getUuid().equals(agentUUID))
                .findFirst()
                .ifPresent(AgentWrapper::destroy);
    }

}
