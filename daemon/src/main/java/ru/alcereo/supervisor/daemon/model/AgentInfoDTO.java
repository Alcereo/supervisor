package ru.alcereo.supervisor.daemon.model;

import java.util.UUID;

/**
 * Created by alcereo on 22.07.17.
 */
public class AgentInfoDTO {

    private final UUID uuid;
    private final boolean alive;
    private final String type;
    private final Integer pid;
    private final String name;

    public AgentInfoDTO(UUID uuid, boolean alive, String type, Integer pid, String name) {

        this.uuid = uuid;
        this.alive = alive;
        this.type = type;
        this.pid = pid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isAlive() {
        return alive;
    }

    public String getType() {
        return type;
    }

    public Integer getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }
}
