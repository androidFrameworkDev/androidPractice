# ServiceOnHandlerThread
This is demo project to understand running service on a handler thread

#### Logs when startService(intent) is called once in MainActivity
```
E: onCreate() is executing on tid:1
E: myServiceThread tid:462
E: onStartCommand() is executing on tid:1
E: startServiceId is 1
E: handleMessage() is executing on tid:462
E: message from MainActivity is received on tid:462
E: onDestroy() is executing on tid:1
```

#### Logs when startService(intent) is called twice in MainActivity
```
E: onCreate() is executing on tid:1
E: myServiceThread tid:457
E: onStartCommand() is executing on tid:1
E: onStartCommand() is executing on tid:1
E: startServiceId is 1
E: handleMessage() is executing on tid:457
E: message from MainActivity is received on tid:457
E: startServiceId is 2
E: handleMessage() is executing on tid:457
E: message from MainActivity is received on tid:457
E: onDestroy() is executing on tid:1
```

Observe that when you call `startService(intent)` twice or more, `onCreate()` is being called only once. 
Moreover, since myServiceThread is being created inside `onCreate()`, only one thread is created,
and from there on whenever you start a service, all those intents will be passed on to `onStartCommand()` directly
and will be executed on myServiceThread.
