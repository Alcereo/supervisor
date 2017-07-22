package ru.alcereo.supervisor.daemon.model;

import java.util.List;

/**
 * Created by alcereo on 22.07.17.
 */
public class AgentRunInfoDTO {
    private List<String> command;
    private String name;

    public List<String> getCommand() {
        return command;
    }

    public String getName() {
        return name;
    }
}
