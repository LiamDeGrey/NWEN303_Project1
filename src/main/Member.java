package main;

/**
 * Created by Liam on 19-Aug-15.
 */
public abstract class Member extends Thread {
    private static final int MAX_WAIT_LOOP = 10;

    protected int serviceId;
    private int count = 0;
    private boolean serviceMet = false;

    @Override
    public void run() {
        super.run();


        System.out.println(this instanceof Client ? "Client run" : "Provider run");

        while (count != 3) {

            try {
                sleep((int) (Math.random() * 499) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            serviceId = (int) (Math.random() * 2) + 1;

            if (!checkService()) {
                postService();
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

            serviceMet = false;

            if (i == MAX_WAIT_LOOP) {
                BulletinBoard.removeService(this);
            }

            count++;
        }
    }

    protected abstract void postService();

    protected abstract boolean checkService();

    protected void serviceMet() {
        serviceMet = true;
    }
}
