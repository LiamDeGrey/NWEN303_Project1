package partB;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Liam on 19-Aug-15.
 */
public class Main {
    private static Random serviceIdGenerator;
    private static Random placesLeftGenerator;
    public static int sleepTime;
    public static int maxWaitingLoops;

    private static int threadsStarted = 0;
    private static int providedServicesAdded = 0;
    private static int neededServicesAdded = 0;
    private static int servicesFulfilled = 0;
    private static int servicesAlreadyTaken = 0;
    private static int servicesTimedOut = 0;

    public static synchronized int getServiceId() {
        return serviceIdGenerator.nextInt(2);
    }

    public static synchronized int getPlacesLeft() {
        return placesLeftGenerator.nextInt(5);
    }

    public static synchronized void incrementThreadsStarted() {
        threadsStarted++;
        System.out.println("New thread started");
    }

    public static synchronized void incrementProvidedServicesAdded() {
        providedServicesAdded++;
        System.out.println("New service provided");
    }

    public static synchronized void incrementNeededServicesAdded() {
        neededServicesAdded++;
        System.out.println("New service needed");
    }

    public static synchronized void incrementServicesFulfilled() {
        servicesFulfilled++;
        System.out.println("Service has been fulfilled");
    }

    public static synchronized void incrementServicesAlreadyTaken() {
        servicesAlreadyTaken++;
        System.out.println("Service has been taken while waiting");
    }

    public static synchronized void incrementServicesTimedOut() {
        servicesTimedOut++;
        System.out.println("Service has timed out ");
    }

    public static void main(final String[] args) {
        serviceIdGenerator = new Random(1);
        placesLeftGenerator = new Random(2);

        int numClients = args.length > 0 ? Integer.parseInt(args[0]) : 5;
        int numProviders = args.length > 1 ? Integer.parseInt(args[1]) : 5;
        sleepTime = args.length > 2 ? Integer.parseInt(args[2]) : 500;
        maxWaitingLoops = args.length > 3 ? Integer.parseInt(args[3]) : 10;

        final ArrayList<Member> members = new ArrayList<>();
        while (numClients+numProviders > 0) {
            if (numClients > 0) {
                members.add(new Client());
                numClients--;
            }
            if (numProviders > 0) {
                members.add(new Provider());
                numProviders--;
            }
        }

        final long startTime = System.currentTimeMillis();
        members.forEach(Member::start);

        for (Member member : members) {
            try {
                member.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("The program finished in " + (System.currentTimeMillis() - startTime) + "ms\n");

        System.out.println("Threads started = "+threadsStarted);
        System.out.println("Provided services added = "+providedServicesAdded);
        System.out.println("Needed services added = "+neededServicesAdded);
        System.out.println("Services fulfilled = "+servicesFulfilled);
        System.out.println("Services already taken = "+servicesAlreadyTaken);
        System.out.println("Services timed out = "+servicesTimedOut);
    }
}
