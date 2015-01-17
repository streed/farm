package com.farm.tasks.example;

import com.farm.tasks.TaskRunner;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;

public class TestTaskRunner extends TaskRunner {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Integer.class).annotatedWith(Names.named("testValue")).toInstance(1337);
            }
        });
    }

    @Override
    protected void configure() {
        register("test", "Run a test task", TestTask.class);
    }

    public static void main(String [] args) throws Exception {
        new TestTaskRunner().run(args);
    }
}
