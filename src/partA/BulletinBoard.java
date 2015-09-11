package partA;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Liam on 19-Aug-15.
 */
public class BulletinBoard {
    final private static ArrayList<Integer> serviceProvidedIds = new ArrayList<>();
    final private static ArrayList<Provider> serviceProvidedMember = new ArrayList<>();
    final private static ArrayList<Integer> serviceNeededIds = new ArrayList<>();
    final private static ArrayList<Client> serviceNeededMember = new ArrayList<>();

    private static Lock providedIdsRemoveLock = new ReentrantLock();
    private static Lock providedIdsAddLock = new ReentrantLock();
    private static Lock neededIdsRemoveLock = new ReentrantLock();
    private static Lock neededIdsAddLock = new ReentrantLock();

    public static void postServiceProvided(final int serviceProvidedId) {
        providedIdsAddLock.lock();
        serviceProvidedIds.add(serviceProvidedId);
        serviceProvidedMember.add((Provider) Thread.currentThread());
        providedIdsAddLock.unlock();
        System.out.println("Service provided added : " + serviceProvidedId);
    }

    public static boolean checkServiceProvided(final int serviceProvidedId) {
        if (serviceProvidedIds.contains(serviceProvidedId)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final Integer element = serviceProvidedId;

            providedIdsRemoveLock.lock();
            final int index = serviceProvidedIds.indexOf(element);
            if (serviceProvidedIds.remove(element)) {
                serviceProvidedMember.remove(index).serviceMet();
                providedIdsRemoveLock.unlock();
                System.out.println("Service provided fulfilled");
                return true;
            } else {
                providedIdsRemoveLock.unlock();
                System.out.println("Service provided already taken");
                return false;
            }
        }

        return false;
    }

    public static void removeServiceProvided(final Provider provider) {
        providedIdsRemoveLock.lock();
        final int index = serviceProvidedMember.indexOf(provider);
        if (serviceProvidedMember.remove(provider)) {
            serviceProvidedIds.remove(index);
        }
        providedIdsRemoveLock.unlock();
    }

    public static void postServiceNeeded(final int serviceNeededId) {
        neededIdsAddLock.lock();
        serviceNeededIds.add(serviceNeededId);
        serviceNeededMember.add((Client) Thread.currentThread());
        neededIdsAddLock.unlock();
        System.out.println("Service needed added : " + serviceNeededId);
    }

    public static boolean checkServiceNeeded(final int serviceNeededId) {
        if (serviceNeededIds.contains(serviceNeededId)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final Integer element = serviceNeededId;
            neededIdsRemoveLock.lock();
            final int index = serviceNeededIds.indexOf(element);
            if (serviceNeededIds.remove(element)) {
                serviceNeededMember.remove(index).serviceMet();
                neededIdsRemoveLock.unlock();
                System.out.println("Service needed fulfilled");
                return true;
            } else {
                neededIdsRemoveLock.unlock();
                System.out.println("Service needed already taken");
                return false;
            }
        }
        return false;
    }

    public static void removeServiceNeeded(final Client client) {
        neededIdsRemoveLock.lock();
        final int index = serviceNeededMember.indexOf(client);
        if (serviceNeededMember.remove(client)) {
            serviceNeededIds.remove(index);
        }
        neededIdsRemoveLock.unlock();
    }
}
