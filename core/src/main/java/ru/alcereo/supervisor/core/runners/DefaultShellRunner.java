package ru.alcereo.supervisor.core.runners;

import ru.alcereo.supervisor.core.agents.DefaultShellAgent;

/**
 * Created by alcereo on 08.07.17.
 */
public class DefaultShellRunner extends AbstractShellRunner<DefaultShellAgent> {

    public DefaultShellRunner(ProcessBuilder processBuilder) {
        super(processBuilder);
    }

    @Override
    protected DefaultShellAgent createNewAgent() {
        return new DefaultShellAgent();
    }

}
