package HugeInt;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class HugeIntegerTest {
    @Test
    public void testConstructor() throws  Exception{
        assertTrue("1_000_000_000_000_000_000_000".replace("_","").equals(new HugeInteger("000_1_000_000_000_000_000_000_000").toString()));
        assertTrue("1_000_000_000_000_000_000_000".replace("_","").equals(new HugeInteger("1_000_000_000_000_000_000_000").toString()));
        assertTrue("1_000_050_000_000_000_000_000".replace("_","").equals(new HugeInteger("1_000_050_000_000_000_000_000").toString()));
        assertTrue("1_234_567_891_013_245_213_235".replace("_","").equals(new HugeInteger("1_234_567_891_013_245_213_235").toString()));
        assertTrue("-1_000_000_000_000_000_000_000".replace("_","").equals(new HugeInteger("-1_000_000_000_000_000_000_000").toString()));
        assertTrue("-1_000_050_000_000_000_000_000".replace("_","").equals(new HugeInteger("-1_000_050_000_000_000_000_000").toString()));
        assertTrue("-1_234_567_891_013_245_213_235".replace("_","").equals(new HugeInteger("-1_234_567_891_013_245_213_235").toString()));
    }
    @Test
    public void testValueOf() throws Exception {
        assertTrue("1".equals(HugeInteger.valueOf(1).toString()));
        assertTrue("15000".equals(HugeInteger.valueOf(15000).toString()));
        assertTrue("1000000000".equals( HugeInteger.valueOf(10_000_000_00).toString()));
        assertTrue("-1".equals(HugeInteger.valueOf(-1).toString()));
        assertTrue("-15000".equals(HugeInteger.valueOf(-15000).toString()));
        assertTrue("-1000000000".equals(HugeInteger.valueOf(-10_000_000_00).toString()));
    }

    @Test
    public void testCompareTo() throws Exception{
        HugeInteger a, b;

        a = new HugeInteger("-1_000_000_000_000_000");
        b = new HugeInteger("-1_000_000_000_000_000");
        assertEquals(a,b);
        assertEquals(b,a);

        a = new HugeInteger("1_000_000_000_000_000");
        b = new HugeInteger("1_000_000_000_000_000");
        assertEquals(a,b);
        assertEquals(b,a);

        a = new HugeInteger("1_000_000_000_000_000");
        b = new HugeInteger("-1_000_000_000_000_000");
        assertNotSame(a,b);
        assertTrue(String.format("Вернуло: %d", a.compareTo(b)),a.compareTo(b) > 0);
        assertTrue(String.format("Вернуло: %d", b.compareTo(a)), b.compareTo(a) < 0);

        a = new HugeInteger("1_000_000_000_000_000");
        b = new HugeInteger("1_000_000_050_000_000");
        assertNotSame(a,b);
        assertTrue(String.format("Вернуло: %d", b.compareTo(a)),b.compareTo(a) > 0);
        assertTrue(String.format("Вернуло: %d", a.compareTo(b)),a.compareTo(b) < 0);

        a = new HugeInteger("128_000_300_000_000_000_234_289");
        b = new HugeInteger("123_000_040_050_000_000_241_245");
        assertNotSame(a,b);
        assertTrue(String.format("Вернуло: %d", a.compareTo(b)),a.compareTo(b) > 0);
        assertTrue(String.format("Вернуло: %d", b.compareTo(a)),b.compareTo(a) < 0);

    }

    @Test
    public void testAdd() throws Exception {
        HugeInteger a, b, result;

        a = new HugeInteger("1_000"); b = new HugeInteger("1_000");
        result = a.add(b);
        assertEquals(result.toString(), "2000" );

        a = new HugeInteger("1_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000");
        b = new HugeInteger("3_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000");
        result = a.add(b);
        assertEquals(result.toString(), "4000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");

        a = new HugeInteger("1_000_000_000_000_000_000_070_000_000_010_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000");
        b = new HugeInteger("3_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000");
        result = a.add(b);
        assertEquals(result, new HugeInteger("4_000_000_000_000_000_000_070_000_000_010_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000_000"));

        a = new HugeInteger("-999_000"); b = new HugeInteger("1_000_000");
        result = a.add(b);
        assertEquals(result, new HugeInteger("1_000") );
        result = b.add(a);
        assertEquals(result, new HugeInteger("1_000") );

        a = new HugeInteger("999_000"); b = new HugeInteger("-1_000_000");
        result = a.add(b);
        assertEquals(result, new HugeInteger("-1_000") );
        result = b.add(a);
        assertEquals(result, new HugeInteger("-1_000") );

        a = new HugeInteger("-1_000"); b = new HugeInteger("-1_000");
        result = a.add(b);
        assertEquals(result.toString(), "-2000" );

        a = new HugeInteger("-1_500"); b = new HugeInteger("-1_700");
        result = b.add(a);
        assertEquals(result.toString(), "-3200" );

        a = new HugeInteger("-999_000"); b = new HugeInteger("1_000");
        result = a.add(b);
        assertEquals(result, new HugeInteger("-998_000") );
    }

    @Test
    public void testSubtract() throws Exception{
        HugeInteger a, b, result;

        a = new HugeInteger("1_500_000");
        b = new HugeInteger("1_000_000");
        result = a.subtract(b);
        assertEquals(new HugeInteger("500_000"),result);

        a = new HugeInteger("1_000_000");
        b = new HugeInteger("1_500_000");
        result = a.subtract(b);
        assertEquals(new HugeInteger("-500_000"),result);

        a = new HugeInteger("1_234_023_000_232_200");
        b = new HugeInteger("1_500_000");
        result = a.subtract(b);
        assertEquals(new HugeInteger("1234022998732200"),result);
        result = b.subtract(a);
        assertEquals(new HugeInteger("-1234022998732200"),result);

        a = new HugeInteger("1_000_000");
        b = new HugeInteger("-1_500_000");
        result = a.subtract(b);
        assertEquals(new HugeInteger("2_500_000"),result);

        a = new HugeInteger("1_000_000");
        b = new HugeInteger("-1_500_000");
        result = b.subtract(a);
        assertEquals(new HugeInteger("-2_500_000"),result);

        a = new HugeInteger("-1_000_000");
        b = new HugeInteger("-1_500_000");
        result = a.subtract(b);
        assertEquals(new HugeInteger("500_000"),result);
        result = b.subtract(a);
        assertEquals(new HugeInteger("-500_000"),result);

        String strA = new String("1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        String strB = new String("-4855000000000000000000000000000000000000000000000000000000003440000000001000000");
        a = new HugeInteger(strA);
        b = new HugeInteger(strB);
        result = a.subtract(b);
        assertEquals(new BigInteger(strA).subtract(new BigInteger(strB)).toString(),result.toString());

        a = new HugeInteger("-1_000_000");
        b = new HugeInteger("-1_500_000");
        result = b.subtract(a);
        assertEquals(new HugeInteger("-500_000"),result);
    }

    @Test
    public void multiply() throws Exception{
        HugeInteger a = new HugeInteger("100");
        HugeInteger b = new HugeInteger("100");
        HugeInteger result = a.multiply(b);
        assertEquals(new HugeInteger("10_000"), result);

        a = new HugeInteger("100");
        b = new HugeInteger("-100");
        result = a.multiply(b);
        assertEquals(new HugeInteger("-10_000"), result);

        a = new HugeInteger("1220309");
        b = new HugeInteger("1712579");
        result = a.multiply(b);
        assertEquals(new HugeInteger("2089875566911"), result);

        a = new HugeInteger("156");
        b = new HugeInteger("3");
        result = a.multiply(b);
        assertEquals(new HugeInteger("468"), result);

    }

    @Test
    public void divide() throws Exception{
        HugeInteger a = new HugeInteger("1250");
        HugeInteger b = new HugeInteger("45");
        HugeInteger result = a.divide(b);
        assertEquals("27", result.toString());

        a = new HugeInteger("100");
        b = new HugeInteger("4");
        result = a.divide(b);
        assertEquals("25", result.toString());

        a = new HugeInteger("-1414");
        b = new HugeInteger("1174");
        result = a.divide(b);
        assertEquals("-1", result.toString());

        a = new HugeInteger("-17");
        b = new HugeInteger("17");
        result = a.divide(b);
        assertEquals("-1", result.toString());

        a = new HugeInteger("-17");
        b = new HugeInteger("19");
        result = a.divide(b);
        assertEquals("0", result.toString());

    }

    @Test
    public void testMultiplyKaratsuba() throws Exception {
        HugeInteger a = new HugeInteger("-1488155107");
        HugeInteger b = new HugeInteger("-1053570202");

        HugeInteger result = a.multiplyKaratsuba(b);
        assertEquals("1567875876689321614", result.toString());

    }

}