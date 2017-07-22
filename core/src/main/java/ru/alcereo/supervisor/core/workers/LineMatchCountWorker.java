package ru.alcereo.supervisor.core.workers;

import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alcereo on 08.07.17.
 */
public class LineMatchCountWorker implements ParserWorker {

    private AtomicLong counter = new AtomicLong();
    private Pattern pattern;

    public LineMatchCountWorker(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public void workForLine(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches())
            counter.incrementAndGet();
    }

    public AtomicLong getCounter() {
        return counter;
    }

    public void setCounter(AtomicLong counter) {
        this.counter = counter;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public static void main(String[] args) {
        LineMatchCountWorker some = new LineMatchCountWorker(Pattern.compile(".*some.*"));

        some.workForLine("have some staring");

        System.out.println(some.getCounter());
    }
}
