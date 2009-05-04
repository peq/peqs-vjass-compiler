package main;

public class StopWatch {
    private long start;
    
    StopWatch() {
        start = System.currentTimeMillis(); // start timing
    }
    
    
    public long elapsedTimeMillis() {
        return System.currentTimeMillis() - start;
    }
    
    public String printElapsedTime() {
    	long elapsed = elapsedTimeMillis();
        return elapsed + "ms"; // print execution time
    }
}
