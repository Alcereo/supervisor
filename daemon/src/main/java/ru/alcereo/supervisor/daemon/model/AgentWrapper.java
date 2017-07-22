package ru.alcereo.supervisor.daemon.model;

import ru.alcereo.supervisor.core.agents.Agent;
import ru.alcereo.supervisor.core.agents.DefaultShellAgent;
import ru.alcereo.supervisor.core.parsers.ShellParser;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by alcereo on 23.07.17.
 */
public class AgentWrapper {

    private final UUID uuid;
    private final Agent agent;
    private String name;

    public InputStream getInputStream() {
        return agent.getInputStream();
    }

    public InputStream getErrorStream() {
        return agent.getErrorStream();
    }

    public DefaultShellAgent destroy() {
        return agent.destroy();
    }

    public ShellParser getParser() {
        return agent.getParser();
    }

    public void setParser(ShellParser parser) {
        agent.setParser(parser);
    }

    public void parserDestroySignal() {
        agent.parserDestroySignal();
    }

    public OutputStream getOutputStream() {
        return agent.getOutputStream();
    }

    public int waitFor() throws InterruptedException {
        return agent.waitFor();
    }

    public boolean waitFor(long timeout, TimeUnit unit) throws InterruptedException {
        return agent.waitFor(timeout, unit);
    }

    public int exitValue() {
        return agent.exitValue();
    }

    public Process destroyForcibly() {
        return agent.destroyForcibly();
    }

    public AgentWrapper(UUID uuid, Agent agent) {
        this.uuid = uuid;
        this.agent = agent;
    }


    public Integer getPID() {
        return agent.getPID();
    }

    public boolean isAlive() {
        return agent.isAlive();
    }

    public int getExitCode() {
        return agent.getExitCode();
    }

    public String getType() {
        return agent.getClass().getName();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AgentInfoDTO mapToAgentInfoDTO(){

        return new AgentInfoDTO(
                getUuid(),
                isAlive(),
                getType(),
                getPID(),
                getName()
        );
    }
}
