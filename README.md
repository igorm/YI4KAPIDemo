# YI4KAPIDemo

Illustrates use of YI 4K action camera [Java API](https://yitechnology.github.io/YIOpenAPIDocs/java). This demo
application connects to a YI 4K camera over wifi and continuously records
[10 second](src/main/java/com/myrosh/yi4kapidemo/CamRig.java#L77) videos.

### Prerequisites

* The camera has to be in station mode (not access point)
* The camera has to be on the same network as the device this application is running on
* You need to know the [IP address](src/main/java/com/myrosh/yi4kapidemo/App.java#L14) of your camera
* Install [YI 4K Java API JAR](https://github.com/YITechnology/YIOpenAPI/blob/master/sdk/java/libs/libyiaction.jar) as
a [local Maven dependency](https://stackoverflow.com/questions/4955635/how-to-add-local-jar-files-to-a-maven-project)

### Running YI4KAPIDemo

YI4KAPIDemo is using [Apache Maven](https://maven.apache.org). You can package and run the application as follows:

```
mvn package exec:java -Dexec.mainClass="com.myrosh.yi4kapidemo.App"
```
