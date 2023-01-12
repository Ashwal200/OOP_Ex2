# Project Thread

The Java platform includes a package of concurrency utilities.   
These are classes that are designed to be used as building blocks in building concurrent classes and applications.  
Handling the design complexity for advanced use-cases demands developers to extend the functionality of these built-in concurrency utilities.
In object-oriented programming (OOP), SOLID is an acronym that stands for five principles of good software design, each represented by a single letter:  
__S__ - Single Responsibility Principle: a class should have one and only one reason to change, meaning that it should have only one responsibility or 
task.  
__O__ - Open/Closed Principle: a class should be open for extension but closed for modification, meaning that its behavior can be extended without 
modifying 
the class itself.  
__L__ - Liskov Substitution Principle: objects of a superclass should be able to be replaced with objects of a subclass without affecting the correctness 
of the program.  
__I__ - Interface Segregation Principle: a class should not be forced to implement interfaces it does not use, meaning that a class should not be required 
to implement methods it will not use.    
__D__ - Dependency Inversion Principle: high-level modules should not depend on low-level modules, but rather both should depend on abstractions. This 
helps to minimize coupling between modules and make the code more flexible.  

Together, these five principles make up the SOLID principles, and they can help you to write code that is more maintainable, testable, and scalable.
In this project we show different ways to working with the Thread class.

### Thread
All programmers are familiar with writing sequential programs. You’ve probably written a program that displays "Hello World!" or sorts a list of names or
computes a list of prime numbers. These are sequential programs.    
That is, each has a beginning, an execution sequence, and an end. At any given time during the runtime of the program,      
there is a single point of execution.    
A thread is similar to the sequential programs described previously. A single thread also has a beginning, a sequence, and an end.   
At any given time during the runtime of the thread, there is a single point of execution.   
However, a thread itself is not a program; a thread cannot run on its own. Rather, it runs within a program. The following figure shows this relationship.   
A thread is a single sequential flow of control within a program. The real excitement surrounding threads is not about a single sequential thread. 
Rather, it’s about the use of multiple threads running at the same time and performing different tasks in a single program.   

The __run()__ method of thread class is called if the thread was constructed using a separate Runnable object otherwise this method does nothing and
returns. When the __run()__ method calls, the code specified in the __run()__ method is executed. You can call the __run()__ method multiple times.

The __start()__ method of thread class is used to begin the execution of thread. The result of this method is two threads that are running concurrently:  
the current thread (which returns from the call to the __start()__) and the other thread (which executes its __run()__).

When a program calls the start() method, a new thread is created and then the run() method is executed.   
But if we directly call the run() method then no new thread will be created and run() method will be executed as a normal method call on the current
calling thread itself and no multi-threading will take place.


### Thread Pool
A thread pool is a managed collection of threads that are available to perform tasks. Thread pools usually provide:  
* Improved performance when executing large numbers of tasks due to reduced per-task invocation overhead.  
* A means of bounding the resources, including threads, consumed when executing a collection of tasks.

In addition, thread pools relieve you from having to manage the life cycle of threads.   
They allow to take advantage of threading, but focus on the tasks that you want the threads to perform, instead of the thread mechanics.

### Callable
A task that returns a result and may throw an exception. Implementors define a single method with no arguments called call.  
The Callable interface is similar to Runnable, in that both are designed for classes whose instances are potentially executed by another thread.  
A Runnable, however, does not return a result and cannot throw a checked exception.  
The Executors class contains utility methods to convert from other common forms to Callable classes.

### Future
When the __call()__ method completes, answer must be stored in an object known to the main thread, so that the main thread can know about the result that 
the thread returned. How will the program store and obtain this result later?  
For this, a Future object can be used. Think of a Future as an object that holds the result – it may not hold it right now, but it will do so in the future 
(once the Callable returns).    
Thus, a Future is basically one way the main thread can keep track of the progress and result from other threads.  
To implement this interface, 5 methods have to be overridden, but as the example below uses a concrete implementation from the library,  
only the important methods are listed here.  
Observe that Callable and Future do two different things – Callable is similar to Runnable, in that it encapsulates a task that   
is meant to run on another thread, whereas a Future is used to store a result obtained from a different thread.  
In fact, the Future can be made to work with Runnable as well, which is something that will become clear when Executors come into the picture.  
  
  
The main purpose of this project is to create two new types that extend the functionality of Javas Concurrency Framework
* 1. A generic task with a Type that returns a result and may throw an exception.
Each task has a priority used for scheduling inferred from the integer value of the tasks Type.
* 2. A custom thread pool class that defines a method for submitting a generic task as described in
the section 1 to a priority queue, and a method for submitting a generic task created by a
Callable<V> and a Type, passed as arguments. 

### Enum
In Java, an enumerated type (or "enum") is a class that defines a finite set of enumerated values.  
These values are instances of the enum class and are usually used when a variable can only take on one of a small set of possible values.  
Enums are defined using the enum keyword, followed by a list of enumerated values in curly braces. Each enumerated value is separated by a comma and by 
default are final and static.  



#### In order to understand which process is faster, we did some tests on the functions we built and the results is:
Number of files | NOL | NOL with thread | NOL with threadPool
| :--- | :---: | :---: | :---:
300  | 0.135 | 0.110 | 0.102
500  | 0.198 | 0.149 | 0.111
1000 | 0.247 | 0.166 | 0.141 
2000 | 0.478 | 0.301 | 0.228

* NOL - Number Of Lines 

### GetNumberOfLines  <kbd><</kbd> GetNumberOfLinesThreads
A normal function or a process runs in a single thread, which means that only one instruction can be executed at a time.  
This can lead to delays if a function is waiting for a slow operation, such as I/O or a network request, to complete.   
When a function is blocked, the program is unable to perform any other tasks until the function is unblocked.  
On the other hand, threads allow for concurrent execution of multiple instructions. Each thread runs in its own context and has its own program counter,
stack, and local variables. This means that multiple threads can be executing different instructions at the same time, which can greatly increase the 
overall speed of a program.  
When a thread is blocked, such as waiting for I/O or a network request to complete, other threads can continue to execute instructions, so the program 
doesn't stall. Additionally, modern operating systems can take advantage of multiple processors and cores, which means that multiple threads can run in 
parallel across multiple processors, allowing for even greater speedup.  
It's important to note that not all cases a thread will run faster than a normal function, It depends on the specific situation and on the resources that 
the program needs to access.   

### GetNumberOfLinesThreads  <kbd><</kbd> GetNumberOfLinesThreadPool
Thread pool is faster than creating new threads because it avoids the overhead of creating and destroying threads. Thread pool maintains a collection of 
worker threads, which are available to perform tasks. When a task is submitted, one of the worker threads is assigned to perform the task. This eliminates 
the need to create a new thread for each task, which can be a time-consuming process. Additionally, thread pool also helps with managing and reusing 
threads, which can improve performance and reduce resource usage.
