#ДЗ 1
Архитектура проекта:

    DemoApplication — точка входа

    CounterTask — печатает имя потока и номер

    ThreadDemoService — бизнес-логика запуска потоков и вывод списка активных

    Controller — REST-точки входа

Реализация:

    Способ 1: new Thread(new CounterTask(5), "CounterWorker").start()

    Способ 2: Runnable task = new CounterTask(5); new Thread(task, "LoggerThread").start()

    Разница: в первом случае задача — это сам Runnable, во втором — Runnable передаётся как параметр; в обоих случаях поток создаётся классом Thread

Вывод списка активных потоков: Thread.getAllStackTraces().keySet() — возвращает все живые потоки JVM; для каждого выводим имя, состояние, daemon, приоритет.
<details>
<summary>Вывод консоли</summary>
Запущен поток: CounterWorker
[CounterWorker] номер: 1
Запущен поток: LoggerThread
[LoggerThread] номер: 1
=== Активные потоки (27) ===
name=Attach Listener           state=RUNNABLE     daemon=true priority=5
name=Catalina-utility-1        state=WAITING      daemon=false priority=1
name=Catalina-utility-2        state=TIMED_WAITING daemon=false priority=1
name=Common-Cleaner            state=TIMED_WAITING daemon=true priority=8
name=CounterWorker             state=TIMED_WAITING daemon=false priority=5
name=Finalizer                 state=WAITING      daemon=true priority=8
name=HikariPool-1:connection-adder state=TIMED_WAITING daemon=true priority=5
name=HikariPool-1:housekeeper  state=TIMED_WAITING daemon=true priority=5
name=LoggerThread              state=TIMED_WAITING daemon=false priority=5
name=Monitor Ctrl-Break        state=RUNNABLE     daemon=true priority=5
name=Notification Thread       state=RUNNABLE     daemon=true priority=9
name=Reference Handler         state=RUNNABLE     daemon=true priority=10
name=Signal Dispatcher         state=RUNNABLE     daemon=true priority=9
name=container-0               state=TIMED_WAITING daemon=false priority=5
name=http-nio-8080-Acceptor    state=RUNNABLE     daemon=true priority=5
name=http-nio-8080-Poller      state=RUNNABLE     daemon=true priority=5
name=http-nio-8080-exec-1      state=WAITING      daemon=true priority=5
name=http-nio-8080-exec-10     state=WAITING      daemon=true priority=5
name=http-nio-8080-exec-2      state=WAITING      daemon=true priority=5
name=http-nio-8080-exec-3      state=WAITING      daemon=true priority=5
name=http-nio-8080-exec-4      state=WAITING      daemon=true priority=5
name=http-nio-8080-exec-5      state=WAITING      daemon=true priority=5
name=http-nio-8080-exec-6      state=WAITING      daemon=true priority=5
name=http-nio-8080-exec-7      state=WAITING      daemon=true priority=5
name=http-nio-8080-exec-8      state=WAITING      daemon=true priority=5
name=http-nio-8080-exec-9      state=WAITING      daemon=true priority=5
name=main                      state=RUNNABLE     daemon=false priority=5
===================================
[CounterWorker] номер: 2
[LoggerThread] номер: 2
[LoggerThread] номер: 3
[CounterWorker] номер: 3
[CounterWorker] номер: 4
[LoggerThread] номер: 4
[CounterWorker] номер: 5
[LoggerThread] номер: 5
[LoggerThread] завершён
[CounterWorker] завершён
</details>