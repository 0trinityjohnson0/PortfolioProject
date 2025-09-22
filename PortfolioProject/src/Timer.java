
public class Timer {
    private long startTime;
    private long endTime;
    private boolean running;

    // constructor
    public Timer() {
        this.startTime = 0;
        this.endTime = 0;
        this.running = false;
    }

    // methods
    public void start() { /* to-do later */ }
    public void stop() { /* to-do later */ }
    public long getElapsedTime() { return 0; /* to-do later */ }

    // getters
    public boolean isRunning() { return this.running; }
}
