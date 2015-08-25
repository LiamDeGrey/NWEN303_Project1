package partB;

import java.util.ArrayList;

/**
 * Created by Liam on 19-Aug-15.
 */
public class BulletinBoard {
    final private static ArrayList<Integer> serviceProvidedIds = new ArrayList<>();
    final private static ArrayList<Integer> serviceProvidedPlacesLeft = new ArrayList<>();
    final private static ArrayList<Provider> serviceProvidedMember = new ArrayList<>();
    final private static ArrayList<Integer> serviceNeededIds = new ArrayList<>();
    final private static ArrayList<Client> serviceNeededMember = new ArrayList<>();


    public static void postServiceProvided(final int serviceProvidedId, final int serviceProvidedPlaces) {
        synchronized (serviceProvidedIds) {
            serviceProvidedIds.add(serviceProvidedId);
            serviceProvidedPlacesLeft.add(serviceProvidedPlaces);
            serviceProvidedMember.add((Provider) Thread.currentThread());
        }
        System.out.println("Service provided added : " + serviceProvidedId);
    }

    public static int checkServiceProvided(final int serviceProvidedId) {
        if (serviceProvidedIds.contains(serviceProvidedId)) {
            try {
                Thread.sleep((int) (Math.random() * 499) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (serviceProvidedIds) {
                final Integer element = serviceProvidedId;
                final int index = serviceProvidedIds.indexOf(element);
                if (index != -1) {
                    final int placesLeft = serviceProvidedPlacesLeft.get(index);
                    if (placesLeft > 1) {
                        serviceProvidedPlacesLeft.set(index, placesLeft - 1);

                        System.out.println("Service provided place taken : " + serviceProvidedId);
                    } else {
                        serviceProvidedIds.remove(index);
                        serviceProvidedPlacesLeft.remove(index);
                        serviceProvidedMember.remove(index).serviceMet();

                        System.out.println("Service provided removed : " + serviceProvidedId);
                    }
                }
            }

            return 1;
        }
        return 0;
    }

    public static void removeServiceProvided(final Provider provider) {
        synchronized (serviceProvidedIds) {
            final int index = serviceProvidedMember.indexOf(provider);
            if (serviceProvidedMember.remove(provider)) {
                serviceProvidedPlacesLeft.remove(index);
                serviceProvidedIds.remove(index);
            }
        }
    }

    public static void printServiceProvidedArray() {
        System.out.println();
        for (int i = 0; i < serviceProvidedIds.size(); i++) {
            System.out.print(serviceProvidedIds.get(i) + ", ");
        }
    }

    public static void postServiceNeeded(final int serviceNeededId) {
        synchronized (serviceNeededIds) {
            serviceNeededIds.add(serviceNeededId);
            serviceNeededMember.add((Client) Thread.currentThread());
        }
        System.out.println("Service needed added : " + serviceNeededId);
    }

    public static int checkServiceNeeded(final int serviceNeededId, final int serviceNeededPlaces) {
        int placesLeft = serviceNeededPlaces;
        if (serviceNeededIds.contains(serviceNeededId)) {
            try {
                Thread.sleep((int) (Math.random() * 499) + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (serviceNeededIds) {
                for (int i = 0; i < serviceNeededIds.size(); i++) {
                    if (serviceNeededIds.get(i) == serviceNeededId && placesLeft >= 1) {
                        serviceNeededIds.remove(i);
                        serviceNeededMember.remove(i).serviceMet();
                        placesLeft--;
                        System.out.println("Service needed removed : " + serviceNeededId);
                    }
                }
            }
        }
        return placesLeft;
    }

    public static void removeServiceNeeded(final Client client) {
        synchronized (serviceNeededIds) {
            final int index = serviceNeededMember.indexOf(client);
            if (serviceNeededMember.remove(client)) {
                serviceNeededIds.remove(index);
            }
        }
    }

    public static void printServiceNeededArray() {
        System.out.println();
        for (int serviceId : serviceNeededIds) {
            System.out.print(serviceId + ", ");
        }
    }
}
