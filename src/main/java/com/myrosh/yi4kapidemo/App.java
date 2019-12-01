package com.myrosh.yi4kapidemo;

/**
 * A launcher class for YI4KAPIDemo
 *
 * https://github.com/YITechnology/YIOpenAPI
 * https://github.com/psolyca/Yi_4k_ROOTFS
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
