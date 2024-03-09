package common;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class MultiThreadingTestSettings {
    public static final int TARGET = 1_000_000_000;
    public static final int QUANTITY_EXECUTIONS = 1;
    public static final int PROCESSORS_COUNT = Runtime.getRuntime().availableProcessors();
    public static final int THREADS_COUNT = 2;
    public static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(THREADS_COUNT);
    public static final boolean SHOW_EACH_THREAD_RESULT = true;
    public static final boolean DELETE_OLD_FILE = false;
    private static final File HOME_DIRECTORY = FileSystemView.getFileSystemView().getHomeDirectory();
    public static File RESULT_FILE = new File(HOME_DIRECTORY + "/multiThreadingResults.txt");
}