package ru.alcereo.supervisor.core.runners;

import ru.alcereo.supervisor.core.agents.DefaultShellAgent;
import sun.management.Agent;

import java.io.IOException;

/**
 * Created by alcereo on 08.07.17.
 */
public abstract class DockerImageShellRunner<A extends Agent> implements Runner<DefaultShellAgent> {

    private ProcessBuilder processBuilder = new ProcessBuilder();

    private String imageName;

    @Override
    public DefaultShellAgent runNewAgent() throws IOException {

        return null;
    }
}
