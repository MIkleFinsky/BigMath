package HugeIntRandom;


import HugeInt.HugeInteger;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * Created by MikleF on 3/22/2015.
 */
public class HugeIntegerTest {
    private HugeInteger a;
    private HugeInteger b;
    private BigInteger chA, chB;
    private final int MAX_COUNT_OF_NUMBERS = 300;
    private Random random = new Random();

    @Test
    public void testAdd() throws Exception {

        for (int i = 1; i <= MAX_COUNT_OF_NUMBERS; i++) {
            String strA = generateRandomHugeNumber(i);
            String strB = generateRandomHugeNumber(i);

            a = new HugeInteger(strA);
            b = new HugeInteger(strB);
            chA = new BigInteger(strA);
            chB = new BigInteger(strB);

            assertEquals(String.format("a = %s, b = %s", strA, strB), chA.add(chB).toString(), a.add(b).toString());

        }
    }

    @Test
    public void testSubtract() throws Exception {
        for (int i = 1; i <= MAX_COUNT_OF_NUMBERS; i++) {
            String strA = generateRandomHugeNumber(i);
            String strB = generateRandomHugeNumber(i);

            a = new HugeInteger(strA);
            b = new HugeInteger(strB);
            chA = new BigInteger(strA);
            chB = new BigInteger(strB);

            assertEquals(String.format("a = %s, b = %s", strA, strB), chA.subtract(chB).toString(), a.subtract(b).toString());
        }

    }

    @Test
    public void testMultiply() throws Exception {
        for (int i = 1; i <= MAX_COUNT_OF_NUMBERS; i++) {
            String strA = generateRandomHugeNumber(i);
            String strB = generateRandomHugeNumber(i);

            a = new HugeInteger(strA);
            b = new HugeInteger(strB);
            chA = new BigInteger(strA);
            chB = new BigInteger(strB);

            assertEquals(String.format("a = %s, b = %s", strA, strB), chA.multiply(chB).toString(), a.multiply(b).toString());
        }

    }

    @Test
    public void testMultiplyKaratsuba() throws Exception {
        for (int i = 0; i <= MAX_COUNT_OF_NUMBERS; i++) {
            String strA = generateRandomHugeNumber(i);
            String strB = generateRandomHugeNumber(i);

            a = new HugeInteger(strA);
            b = new HugeInteger(strB);
            chA = new BigInteger(strA);
            chB = new BigInteger(strB);

            assertEquals(String.format("a = %s, b = %s", strA, strB), chA.multiply(chB).toString(), a.multiplyKaratsuba(b).toString());
        }
    }


    @Test
    public void testDivide() throws Exception{
        for(int  i = 1; i <= MAX_COUNT_OF_NUMBERS; i++){
            String strA = generateRandomHugeNumber(i);
            String strB = generateRandomHugeNumber(i);

            a = new HugeInteger(strA);
            b = new HugeInteger(strB);
            chA = new BigInteger(strA);
            chB = new BigInteger(strB);

            assertEquals(String.format("a = %s, b = %s",strA, strB),chA.divide(chB).toString(), a.divide(b).toString());

        }
    }

    @Test
    public void testRaimander() throws Exception{
        for(int  i = 1; i <= MAX_COUNT_OF_NUMBERS; i++){
            String strA = generateRandomHugeNumber(i);
            String strB = generateRandomHugeNumber(i);

            a = new HugeInteger(strA);
            b = new HugeInteger(strB);
            chA = new BigInteger(strA);
            chB = new BigInteger(strB);

            assertEquals(String.format("a = %s, b = %s",strA, strB),chA.remainder(chB).toString(), a.remainder(b).toString());

        }
    }

    public static String generateRandomHugeNumber(int countOfNumbers){
        if (countOfNumbers == 0 ) return new String("0");

        Random random = new Random();;
        int sign = random.nextBoolean() ? 1 : -1;
        StringBuilder sb = new StringBuilder();

        sb.append(sign);
        for(int i = 0; i < countOfNumbers; i++) {
            int number = random.nextInt(10);
            sb.append(number);
        }
        return sb.toString();
    }
}
