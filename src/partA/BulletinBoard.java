package partA;

import java.util.ArrayList;

/**
 * Created by Liam on 19-Aug-15.
 */
public class BulletinBoard {
    final private static ArrayList<Integer> serviceProvidedIds = new ArrayList<>();
    final private static ArrayList<Provider> serviceProvidedMember = new ArrayList<>();
    final private static ArrayList<Integer> serviceNeededIds = new ArrayList<>();
    final private static ArrayList<Client> serviceNeededMember = new ArrayList<>();


    public static void postServiceProvided(final int serviceProvidedId) {
        synchronized (serviceProvidedIds) {
            serviceProvidedIds.add(serviceProvidedId);
            serviceProvidedMember.add((Provider) Thread.currentThread());
        }
        System.out.println("Service provided added : " + serviceProvidedId);
    }

    public static boolean checkServiceProvided(final int serviceProvidedId) {
        if (serviceProvidedIds.contains(serviceProvidedId)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (serviceProvidedIds) {
                final Integer element = serviceProvidedId;
                final int index = serviceProvidedIds.indexOf(element);
                if (serviceProvidedIds.remove(element)) {
                    serviceProvidedMember.remove(index).serviceMet();
                    System.out.println("Service provided fulfilled");
                    return true;
                } else {
                    System.out.println("Service provided already taken");
                    return false;
                }
            }
        }
        return false;
    }

    public static void removeServiceProvided(final Provider provider) {
        synchronized (serviceProvidedIds) {
            final int index = serviceProvidedMember.indexOf(provider);
            if (serviceProvidedMember.remove(provider)) {
                serviceProvidedIds.remove(index);
            }
        }
    }

    public static void postServiceNeeded(final int serviceNeededId) {
        synchronized (serviceNeededIds) {
            serviceNeededIds.add(serviceNeededId);
            serviceNeededMember.add((Client) Thread.currentThread());
        }
        System.out.println("Service needed added : " + serviceNeededId);
    }

    public static boolean checkServiceNeeded(final int serviceNeededId) {
        if (serviceNeededIds.contains(serviceNeededId)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (serviceNeededIds) {
                final Integer element = serviceNeededId;
                final int index = serviceNeededIds.indexOf(element);
                if (serviceNeededIds.remove(element)) {
                    serviceNeededMember.remove(index).serviceMet();
                    System.out.println("Service needed fulfilled");
                    return true;
                } else {
                    System.out.println("Service needed already taken");
                    return false;
                }
            }
        }
        return false;
    }

    public static void removeServiceNeeded(final Client client) {
        synchronized (serviceNeededIds) {
            final int index = serviceNeededMember.indexOf(client);
            if (serviceNeededMember.remove(client)) {
                serviceNeededIds.remove(index);
            }
        }
    }
}
