package me.ihxq.acejump.lite.util;

public class ThreadUtil {
    public static void sleep(long milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException ignored) {
        }
    }
}
