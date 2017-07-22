package ru.alcereo.supervisor.core.agents.springboot;

import ru.alcereo.supervisor.core.agents.DefaultShellAgent;
import ru.alcereo.supervisor.core.agents.exceptions.ProcessDiedException;
import ru.alcereo.supervisor.core.workers.ParserWorker;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by alcereo on 08.07.17.
 */
public class SpringBootTomcatAgent extends DefaultShellAgent {

    private Set<AgentState> agentStates = Collections.synchronizedSet(EnumSet.noneOf(AgentState.class));

    private final Object stateChangeMonitor = new Object();

    public SpringBootTomcatAgent() {
        super();
        getParser()
                .addWorker(
                        new PermanentStateWorker(
                                Pattern.compile(".*Starting Application on.*"),
                                AgentState.STARTING
                        ))
                .addWorker(new PermanentStateWorker(
                        Pattern.compile(".*Tomcat initialized with port.*"),
                        AgentState.INITIALIZED
                ))
                .addWorker(new PermanentStateWorker(
                        Pattern.compile(".*Started Application in.*"),
                        AgentState.STARTED
                ));
    }

    /**
     * Get code from: {@link Process#waitFor(long timeout, TimeUnit unit)}
     * @param state
     * @param timeout
     * @param unit
     * @return
     * @throws InterruptedException
     */
    public boolean waitForState(AgentState state, long timeout, TimeUnit unit) throws InterruptedException, ProcessDiedException {

        long startTime = System.nanoTime();
        long rem = unit.toNanos(timeout);

        synchronized (stateChangeMonitor) {
            do{
                if (agentStates.contains(state))
                    return true;

                if (rem>0)
                    stateChangeMonitor.wait(unit.toMillis(timeout));

                if (!isAlive())
                    throw new ProcessDiedException();

                rem = unit.toNanos(timeout) - (System.nanoTime() - startTime);
            } while (rem>0);
            return false;
        }
    }

    public SpringBootTomcatAgent waitForState(AgentState state) throws InterruptedException, ProcessDiedException {

        synchronized (stateChangeMonitor) {
            while (!agentStates.contains(state)) {

                if (!isAlive())
                    throw new ProcessDiedException();

                stateChangeMonitor.wait();
            }
        }

        return this;

    }

//    Tomcat initialized

    private class PermanentStateWorker implements ParserWorker {

        private Pattern startUpPattern;
        private AgentState state;

        PermanentStateWorker(Pattern startUpPattern, AgentState state) {
            this.startUpPattern = startUpPattern;
            this.state = state;
        }

        @Override
        public void workForLine(String line) {
            if (startUpPattern.matcher(line).matches()) {
                synchronized (stateChangeMonitor) {
                    SpringBootTomcatAgent.this
                            .setUpState(state)
                            .getParser().removeWorker(this);

                    stateChangeMonitor.notify();
                    System.out.println("Set state to: " + state);
                }
            }
        }
    }

    private SpringBootTomcatAgent setUpState(AgentState state) {
        this.agentStates.add(state);
        return this;
    }

    @Override
    public void parserDestroySignal() {
        super.parserDestroySignal();
        synchronized (stateChangeMonitor) {
            stateChangeMonitor.notify();
        }
    }
}
