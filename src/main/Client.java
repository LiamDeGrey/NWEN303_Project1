package main;

/**
 * Created by Liam on 19-Aug-15.
 */
public class Client extends Member {

    @Override
    public void postService() {
        BulletinBoard.postServiceNeeded(serviceId);
    }

    @Override
    protected boolean checkService() {
        return BulletinBoard.checkServiceProvided(serviceId);
    }
}
