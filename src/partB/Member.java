package partB;

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
    private long preTime;

    protected Member() {
        threadId++;
    }

    @Override
    public void run() {
        super.run();

        System.out.println(id + " started");

        while (count != 1) {

            try {
                sleep((int) (Math.random() * 499) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            serviceId = (int) (Math.random() * 2) + 1;

            preTime = System.currentTimeMillis();
            if (!checkService()) {
                System.out.println(id + "'s time taken to check service : " + (System.currentTimeMillis() - preTime) + "ms");
                preTime = System.currentTimeMillis();
                postService();
                System.out.println(id + "'s time taken to post service : " + (System.currentTimeMillis() - preTime) + "ms");
            }

            int i = 0;
            while (!serviceMet && i != MAX_WAIT_LOOP) {
                try {
                    sleep((int) (Math.random() * 499) + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }


            if (i == MAX_WAIT_LOOP) {
                removeService();
            } else {
                System.out.println(id + "'s time taken for post to be accepted : " + (System.currentTimeMillis() - preTime) + "ms");
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
