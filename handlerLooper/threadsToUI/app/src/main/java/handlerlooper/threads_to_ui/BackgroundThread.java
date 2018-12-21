package handlerlooper.threads_to_ui;

class BackgroundThread extends Thread {

    BackgroundThread(Runnable runnable) {
        super(runnable);
    }
}
