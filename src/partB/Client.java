package partB;

/**
 * Created by Liam on 19-Aug-15.
 */
public class Client extends Member {

    public Client() {
        super();

        id = "Client "+threadId;
    }

    @Override
    public void postService() {
        BulletinBoard.postServiceNeeded(serviceId);
    }

    @Override
    protected int checkService() {
        return BulletinBoard.checkServiceProvided(serviceId);
    }

    @Override
    protected void removeService() {
        BulletinBoard.removeServiceNeeded(this);
    }
}
