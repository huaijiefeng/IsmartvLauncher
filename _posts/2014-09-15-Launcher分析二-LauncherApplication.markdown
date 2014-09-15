---
layout: post
title:  "Launcher 源码分析二-LauncherApplication"
date:   2014-09-10 15:37:57
categories: jekyll update
---

{% highlight java %}
public class LauncherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LauncherAppState.setApplicationContext(this);
        LauncherAppState.getInstance();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        LauncherAppState.getInstance().onTerminate();
    }
}
{% endhighlight %}