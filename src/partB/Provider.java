package partB;

/**
 * Created by Liam on 19-Aug-15.
 */
public class Provider extends Member {

    public Provider() {
        super();

        id = "Provider "+threadId;
    }

    @Override
    protected void postService() {
        BulletinBoard.postServiceProvided(serviceId, placesLeft);
    }

    @Override
    protected int checkService() {
        return BulletinBoard.checkServiceNeeded(serviceId, placesLeft);
    }

    @Override
    protected void removeService() {
        BulletinBoard.removeServiceProvided(this);
    }
}
