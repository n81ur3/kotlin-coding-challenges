package google.bootcamp.example.myapp;

public class JavaRun {

    public static void runNow(Runnable runnable) {
        runnable.run();
    }

    public static void main(String[] args) {
        JavaRun.runNow(() -> { System.out.println("Running in Java"); });
    }
}


