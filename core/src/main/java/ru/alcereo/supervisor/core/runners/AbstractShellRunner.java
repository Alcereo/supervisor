package ru.alcereo.supervisor.core.runners;

import ru.alcereo.supervisor.core.agents.DefaultShellAgent;
import ru.alcereo.supervisor.core.workers.ParserWorker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alcereo on 23.07.17.
 */
public abstract class AbstractShellRunner<A extends DefaultShellAgent> implements Runner<A> {

    private ProcessBuilder processBuilder;
    private List<ParserWorker> workerList = new ArrayList<>();

    public AbstractShellRunner(ProcessBuilder processBuilder) {
        this.processBuilder = processBuilder;
    }

    @Override
    public A runNewAgent() throws IOException {
        A agent = createNewAgent();
        getWorkerList().forEach(worker -> agent.getParser().addWorker(worker));
        beforeAgentStart(agent);
        agent.mountProcess(getProcessBuilder().start());
        return agent;
    }

    private void beforeAgentStart(A agent) {
    }

    protected abstract A createNewAgent();

    @Override
    public AbstractShellRunner addWorker(ParserWorker worker){
        workerList.add(worker);
        return this;
    }

    public List<ParserWorker> getWorkerList() {
        return workerList;
    }

    public AbstractShellRunner setWorkerList(List<ParserWorker> workerList) {
        this.workerList = workerList;
        return this;
    }


    public ProcessBuilder getProcessBuilder() {
        return processBuilder;
    }

    public void setProcessBuilder(ProcessBuilder processBuilder) {
        this.processBuilder = processBuilder;
    }

}
