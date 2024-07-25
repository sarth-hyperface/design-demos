# Temporal demo

If you don't already have temporal, install temporal cli (https://github.com/temporalio/cli?tab=readme-ov-file#quick-install)

Run temporal

```bash
$ temporal server start-dev
```

Start the service from IntelliJ or start it from bash,

```bash
$ ./gradlew bootRun
```

### Trigger a parent workflow

```bash
$ curl http://localhost:9190/test
```

### To trigger multiple workflows with higher concurrency, use Apache Bench

```bash
$ ab -c 3 -n 100 http://localhost:9190/test
```