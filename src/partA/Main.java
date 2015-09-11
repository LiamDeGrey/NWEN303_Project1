package partA;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Liam on 19-Aug-15.
 */
public class Main {
    private static Random randomGenerator;

    public static synchronized int getServiceId() {
        return randomGenerator.nextInt(2);
    }

    public static void main(final String[] args) {
        randomGenerator = new Random(1);

        int numClients = args.length > 0 ? Integer.parseInt(args[0]) : 5;
        int numProviders = args.length > 1 ? Integer.parseInt(args[1]) : 5;

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
        System.out.println("The program finished in " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
