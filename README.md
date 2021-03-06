# YI4KAPIDemo

Illustrates use of YI 4K action camera [Java API](https://github.com/YITechnology/YIOpenAPI). This demo
application connects to a YI 4K camera over wifi and continuously records
[10 second](src/main/java/com/myrosh/yi4kapidemo/CamRig.java#L77) videos.

### Prerequisites

* Make sure your camera's serial number starts with `Z16V13L`
* Install custom firmware by downloading `firmware_1.10.50.bin` from [psolyca/Yi_4k_ROOTFS](https://github.com/psolyca/Yi_4k_ROOTFS/releases), renaming the file to `firmware.bin`, copying it to the root of your SD card and restarting the camera
* Put the camera in station mode and connect to your wireless network by creating a file named `wifi.conf` at the root of your SD card that looks like this:
```
##### STA mode configuration ##########################################
# ESSID
ESSID=<YOUR_WIFI_NAME>
# Passphrase. Leave empty at no security mode
PASSWORD=<YOUR_WIFI_PASSWORD>
# Device Name for AMBA Discovery Protocol (optional and different for each cam)
STA_DEVICE_NAME=cam01 
# Static IP address if your access point does not have a DHCP server (remove # to activate)
#STA_IP=<YOUR_STATIC_IP>
```
* You need to know the [IP address](src/main/java/com/myrosh/yi4kapidemo/App.java#L14) of your camera
* Install [YI 4K Java API JAR](https://github.com/YITechnology/YIOpenAPI/blob/master/sdk/java/libs/libyiaction.jar) as
a [local Maven dependency](https://stackoverflow.com/questions/4955635/how-to-add-local-jar-files-to-a-maven-project)

### Running YI4KAPIDemo

YI4KAPIDemo is using [Apache Maven](https://maven.apache.org). You can package and run the application as follows:

```
$ mvn package exec:java -Dexec.mainClass="com.myrosh.yi4kapidemo.App"
```
