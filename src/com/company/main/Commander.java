package com.company.main;
import java.util.concurrent.Semaphore;

class Foo
{
    public Semaphore printedFirst = new Semaphore(0);
    public Semaphore printedSecond = new Semaphore(0);

    public void first() throws InterruptedException
    {
        System.out.println("first");
        printedFirst.release();
    }

    public void second() throws InterruptedException
    {
        printedFirst.acquire();
        System.out.println("second");
        printedFirst.release();
        printedSecond.release();
    }

    public void third() throws InterruptedException
    {
        printedSecond.acquire();
        System.out.println("third");
        printedSecond.release();
    }
}

class MyThread extends Thread
{
    Foo foo;
    String name = "";
    String func = "";

    MyThread(String name, String func, Foo foo)
    {
        super(name);
        this.name = name;
        this.func = func;
        this.foo = foo;
    }

    public void run()
    {
        if(func.equals("printFirst"))
        {
            try
            {
                foo.first();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        else if(func.equals("printSecond"))
        {
            try
            {
                foo.second();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        else if(func.equals("printThird"))
        {
            try
            {
                foo.third();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}

public class Commander
{
    public static void main(String[] args) throws InterruptedException
    {
        Foo foo = new Foo();
        new MyThread("B", "printSecond", foo).start();
        new MyThread("C", "printThird", foo).start();
        new MyThread("A", "printFirst", foo).start();
    }
}
