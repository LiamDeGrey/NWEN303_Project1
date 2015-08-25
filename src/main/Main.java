package main;

/**
 * Created by Liam on 19-Aug-15.
 */
public class Main {

    public static void main(final String[] args) {
        Client[] clients;
        Provider[] providers;
        if (args.length > 0) {
            clients = new Client[Integer.parseInt(args[0])];
        } else {
            clients = new Client[5];//(int) (Math.random() * 9) + 1];
        }
        if (args.length > 1) {
            providers = new Provider[Integer.parseInt(args[1])];
        } else {
            providers = new Provider[5];//(int) (Math.random() * 9) + 1];
        }

        new Client().start();
        new Provider().start();
        new Client().start();
        new Provider().start();
        new Client().start();
        new Provider().start();
        /*for (Provider provider : providers) {
            provider = new Provider();
            provider.start();
        }

        for (Client client : clients) {
            client = new Client();
            client.start();
        }*/
    }
}
