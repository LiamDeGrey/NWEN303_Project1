package partB;

/**
 * Created by Liam on 19-Aug-15.
 */
public abstract class Member extends Thread {
    private static final int MAX_WAIT_LOOP = 10;
    private static final int MAX_SERVICE_REQUESTS = 3;

    protected static int threadId = -1;

    protected String id;
    protected int serviceId;
    protected int placesLeft;
    private int count = 0;
    private boolean serviceMet = false;
    private long preTime;

    protected Member() {
        threadId++;
    }

    @Override
    public void run() {
        super.run();

        System.out.println(id + " started");

        while (count != MAX_SERVICE_REQUESTS) {

            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            serviceId = (int) (Math.random() * 2) + 1;
            placesLeft = (int) (Math.random() * 5) + 1;

            preTime = System.currentTimeMillis();
            if ((placesLeft = checkService()) != 0) {
                preTime = System.currentTimeMillis();
                postService();
                System.out.println("Service " + id + "'s time taken to submit service : " + (System.currentTimeMillis() - preTime) + "ms");

                int i = 0;
                while (!serviceMet && i != MAX_WAIT_LOOP) {
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }


                if (i == MAX_WAIT_LOOP) {
                    removeService();
                    System.out.println("Service " + id + "'s time taken for post to timeout : " + (System.currentTimeMillis() - preTime) + "ms");
                } else {
                    System.out.println("Service " + id + "'s time taken for post to be accepted : " + (System.currentTimeMillis() - preTime) + "ms");
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
