package partB;


/**
 * Created by Liam on 19-Aug-15.
 */
public abstract class Member extends Thread {
    protected static int threadId = -1;

    protected String id;
    protected int serviceId;
    protected int placesLeft;
    private int count = 0;
    private boolean serviceMet = false;

    protected Member() {
        threadId++;
    }

    @Override
    public void run() {
        super.run();

        while (count != 5) {
            serviceId = Main.getServiceId();
            placesLeft = Main.getPlacesLeft();

            if ((placesLeft = checkService()) != 0) {
                postService();

                int i = 0;
                while (!serviceMet && i != partA.Main.maxWaitingLoops) { //Will wait for sleeptime * MAX_WAIT_LOOP ms at most
                    try {
                        sleep(partA.Main.sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }

                if (i == partA.Main.maxWaitingLoops) {
                    removeService();
                }
            }

            serviceMet = false;
            count++;
        }
    }

    protected abstract void postService();

    protected abstract int checkService();

    protected abstract void removeService();

    protected void serviceMet() {
        serviceMet = true;
    }
}
