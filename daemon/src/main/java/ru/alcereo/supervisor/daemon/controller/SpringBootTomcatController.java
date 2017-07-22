package ru.alcereo.supervisor.daemon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alcereo.supervisor.daemon.model.AgentInfoDTO;
import ru.alcereo.supervisor.daemon.model.SpringBootAgentInfoDTO;
import ru.alcereo.supervisor.daemon.service.SpringBootAgentService;

/**
 * Created by alcereo on 23.07.17.
 */
@RestController
@RequestMapping("spring-boot")
public class SpringBootTomcatController {

    private SpringBootAgentService agentService;

    @Autowired
    public SpringBootTomcatController(SpringBootAgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("run")
    public ResponseEntity<AgentInfoDTO> runAgent(@RequestBody SpringBootAgentInfoDTO agentInfoDTO){

        return ResponseEntity.ok(agentService.runNewAgent(agentInfoDTO));
    }
}
