package ru.alcereo.supervisor.daemon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alcereo.supervisor.daemon.model.AgentInfoDTO;
import ru.alcereo.supervisor.daemon.model.AgentRunInfoDTO;
import ru.alcereo.supervisor.daemon.service.DefaultShellAgentService;

/**
 * Created by alcereo on 22.07.17.
 */
@RestController
@RequestMapping("def-shell")
public class DefaultShellController {

    private final DefaultShellAgentService agentService;

    @Autowired
    public DefaultShellController(DefaultShellAgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("run")
    public ResponseEntity<AgentInfoDTO> runAgent(@RequestBody AgentRunInfoDTO agentRunInfo){

        AgentInfoDTO agentInfo = agentService.runNewAgent(agentRunInfo);
        return ResponseEntity.ok(agentInfo);
    }

}
