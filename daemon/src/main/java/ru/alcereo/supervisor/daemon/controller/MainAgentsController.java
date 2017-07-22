package ru.alcereo.supervisor.daemon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alcereo.supervisor.daemon.model.AgentInfoDTO;
import ru.alcereo.supervisor.daemon.service.MainAgentsService;

import java.util.List;
import java.util.UUID;

/**
 * Created by alcereo on 22.07.17.
 */
@RestController
@RequestMapping("agents")
public class MainAgentsController {

    private MainAgentsService agentService;

    @Autowired
    public MainAgentsController(MainAgentsService agentService) {
        this.agentService = agentService;
    }

    @GetMapping("ps")
    public ResponseEntity<List<AgentInfoDTO>> agentsList(){

        List<AgentInfoDTO> agentsList = agentService.getAgentsList();
        return ResponseEntity.ok(agentsList);
    }

    @PostMapping("stop")
    public ResponseEntity stopAgent(@RequestParam UUID agentUUID){

        agentService.stopAgent(agentUUID);
        return ResponseEntity.ok("");
    }

}
