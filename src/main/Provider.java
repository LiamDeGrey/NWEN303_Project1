package main;

/**
 * Created by Liam on 19-Aug-15.
 */
public class Provider extends Member {

    @Override
    protected void postService() {
        BulletinBoard.postServiceProvided(serviceId);
    }

    @Override
    protected boolean checkService() {
        return BulletinBoard.checkServiceNeeded(serviceId);
    }
}
