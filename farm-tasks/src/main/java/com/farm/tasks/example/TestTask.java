package com.farm.tasks.example;

import com.farm.tasks.Task;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class TestTask implements Task {

    private final Integer value;

    @Inject
    public TestTask(@Named("testValue") Integer value) {
        this.value = value;
    }

    @Override
    public void setup(CommandLine commandLine) {
        System.out.println("Setting up test task");
    }

    @Override
    public Options getOptions() {
        Options options = new Options();

        options.addOption("test", false, "Test option to a test task");

        return options;
    }

    @Override
    public void run() {
        System.out.println("I am alive and I was passed " + value + " from guice :D");
    }
}
