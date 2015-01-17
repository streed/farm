farm
====

Farm Water Optiomization

Setup
=====

We're using Java 1.8 so add the following to your `~/.mavenrc` to build properly, of course after install the 1.8 JDK.

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home
```

Install `apache-hbase` version `hbase-0.98.8-hadoop2`.
Install `redis` the latest version.

To build this project you need `maven` installed. 

The following build process is required.

`cd farm-tasks && mvn clean install && cd ../sensor-backend && mvn package`

Make sure that `hbase` and `redis` are running already and then you can run the service as follows:

`cd sensor-backend && java -jar SensorService/target/SensorService-1.0-SNAPSHOT.jar server farm.deploy/farmApi.yaml`

Then you can hit the api by doing:

`curl https://localhost:8080`
