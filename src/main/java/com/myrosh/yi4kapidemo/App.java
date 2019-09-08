package com.myrosh.yi4kapidemo;

/**
 * A launcher class for YI4KAPIDemo
 *
 * https://yitechnology.github.io/YIOpenAPIDocs/java
 * https://github.com/YITechnology/YIOpenAPI
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("STARTING");

        CamRig rig = new CamRig("192.168.1.16");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("STOPPING");

            rig.uninitialize();
        }));

        rig.initialize();
        rig.run();
    }
}
