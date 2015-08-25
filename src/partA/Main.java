package partA;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Liam on 19-Aug-15.
 */
public class Main {

    public static void main(final String[] args) {
        int numClients = args.length > 0? Integer.parseInt(args[0]) : 5;
        int numProviders = args.length > 1? Integer.parseInt(args[1]) : 5;

        ArrayList<Member> members = new ArrayList<>();
        for (int i = 0; i < numClients; i++) {
            members.add(new Client());
        }
        for (int i = 0; i < numProviders; i++) {
            members.add(new Provider());
        }
        Collections.shuffle(members);

        members.forEach(partA.Member::start);
    }
}
