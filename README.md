# Console
Simple log adapter

Show logs on:
* Logcat
* File
* DB (sqlite)
* System.print
* Custom output


### Add to project

Repo:
```
repositories {
	maven {
		url "https://jitpack.io"
	}
}
```

Dependences:
```
dependencies {
	compile 'com.github.Hivedi:Console:1.0.2'
}
```

Sample code:
```java
Console.setEnabled(true);
Console.setTag("log_tag");
Console.addLogWriterLogCat();
Console.logi("log text");
```
