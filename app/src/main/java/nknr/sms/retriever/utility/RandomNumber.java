package nknr.sms.retriever.utility;

import java.util.Random;

public class RandomNumber {

    public static int getRandomNum() {
        Random r = new Random(System.currentTimeMillis());
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));

    }
}
