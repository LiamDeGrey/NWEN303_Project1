package partA;

/**
 * Created by Liam on 19-Aug-15.
 */
public abstract class Member extends Thread {
    private static final int MAX_WAIT_LOOP = 10;

    protected static int threadId = -1;

    protected String id;
    protected int serviceId;
    private int count = 0;
    private boolean serviceMet = false;

    protected Member() {
        threadId++;
    }

    @Override
    public void run() {
        super.run();

        Main.incrementThreadsStarted();

        while (count != 5) {
            serviceId = Main.getServiceId();

            if (!checkService()) {
                postService();

                int i = 0;
                while (!serviceMet && i != MAX_WAIT_LOOP) { //Will wait for 500 * MAX_WAIT_LOOP ms at most
                    try {
                        sleep(Main.sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }

                if (i == MAX_WAIT_LOOP) {
                    removeService();
                }
            }

            serviceMet = false;
            count++;
        }
    }

    protected abstract void postService();

    protected abstract boolean checkService();

    protected abstract void removeService();

    protected void serviceMet() {
        serviceMet = true;
    }
}
