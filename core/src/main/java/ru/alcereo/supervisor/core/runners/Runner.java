package ru.alcereo.supervisor.core.runners;

import ru.alcereo.supervisor.core.agents.Agent;
import ru.alcereo.supervisor.core.workers.ParserWorker;

import java.io.IOException;

/**
 * Created by alcereo on 07.07.17.
 */
public interface Runner<A extends Agent> {

    A runNewAgent() throws IOException;

    Runner addWorker(ParserWorker worker);
}
