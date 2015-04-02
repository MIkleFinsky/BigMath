package HugeInt;

import java.util.Arrays;


/**
 * Класс реализует представления целого, отрицательного или положительного числа любой длинны. А также
 * операции над такими числами.
 * Для хранения числа используется массив типа int.
 */
public class HugeInteger implements Comparable<HugeInteger> {

    private int[] value;  // Хранение числа в формате little-endian
    private int sign = 1; // Знак. 1 - положительное число,  -1 - отрицательное
    private final int base = 10000; //Базис числа. Пример: 100 = 1 * 10^2 + 0 * 10 ^1 + 0*10^0
    private final int digitBase = (int)Math.log10(base); // Количество цифр в одной ячейке массива

    public static final HugeInteger ZERO = new HugeInteger("0");

    public HugeInteger(String value) {
        value = value.replace("_","");

        if(value.lastIndexOf("-") == 0){
            sign = -1;
            value = value.substring(1, value.length());
        }

        value = removeLeadingZeroes(value);
        if(value.length() == 0) {
            value = "0";
            sign = 1;
        }

        int countOfNum = ((value.length() - 1) / digitBase) + 1; //количество цифр в массиве
        int countNumForFirstDigit = value.length() - (countOfNum - 1) * digitBase; //количество цифр в первой ячейке
        this.value = new int[countOfNum];

        for(int i = 0; i < countOfNum; i++){
            this.value[i] = Integer.parseInt(value.substring(Math.max(countNumForFirstDigit + (i - 1) * digitBase, 0), countNumForFirstDigit + i * digitBase));
        }
        this.value = reverseArray(this.value);
    }

    public HugeInteger add(HugeInteger val) {

        if((sign != val.sign))
        {
            if(this.sign < 0) {
                return val.subtract(this.abs());
            }
            else {
                return this.subtract(val.abs());
            }
        }

        //Сравниваем количество элементом в массивах
        int largestSize = Math.max(value.length, val.value.length);
        int[] operand1 = new int[largestSize];
        int[] operand2 = new int[largestSize];

        System.arraycopy(value,0, operand1, 0, value.length);
        System.arraycopy(val.value,0, operand2, 0, val.value.length);

        int d = 0; //перенос
        for(int i = 0; i < largestSize ; i++)
        {
            operand1[i] = operand1[i] + operand2[i] + d;
            d = operand1[i] / base;
            operand1[i] %= base;
        }
        if(d != 0) {
            operand1 = expandArray(operand1, 1);
            operand1[operand1.length - 1] = d;
        }

        return new HugeInteger(convertToString(sign, operand1, this.base));
    }

    public HugeInteger abs() {
        HugeInteger tempClone = new HugeInteger(this.toString());
        tempClone.sign = 1;
        return tempClone;
    }

    public HugeInteger subtract(HugeInteger val){
        if(sign != val.sign){
            if (this.sign > 0) return add(val.abs());
            if (this.sign < 0) {
                HugeInteger res = this.abs().add(val.abs());
                res.sign = -1;
                return res;
            }
        }
        if((sign == val.sign)&&(val.sign < 0)){
                return val.abs().subtract(this.abs());
        }

        //Сравниваем количество элементом в массивах
        int largestSize = Math.max(value.length, val.value.length);
        int[] operand1;
        int[] operand2;
        int sign = 1;

        int cmp = this.compareTo(val);
        if(cmp > 0){
            sign = 1;
            operand1 = expandArray(value,largestSize - value.length);
            operand2 = expandArray(val.value,largestSize - val.value.length);
        }else if(cmp < 0){
            sign = -1;
            operand1 = expandArray(val.value, largestSize - val.value.length);
            operand2 = expandArray(value, largestSize - value.length);
        }else return new HugeInteger("0");

        for(int i = 0; i < largestSize; i++)
        {
            operand1[i] -= operand2[i];
            if((operand1[i] < 0) && (i+1) < largestSize){
                operand1[i]+=base;
                operand1[i+1]--;
            }
        }
        return  new HugeInteger(convertToString(sign, operand1, this.base));
    }

    public HugeInteger divide(HugeInteger val){

         if(val.value.length == 0 && val.compareTo(ZERO) == 0){
            throw new IllegalArgumentException("Деление на ноль");
        }

        HugeInteger operand1 = this.abs();
        HugeInteger operand2 = val.abs();

        int n = operand2.value.length;
        if(operand2.value[n-1] < (base / 2)){
            int scale =  base / (operand2.value[n-1] + 1);
            operand1 = operand1.multiply(scale);
            operand2 = operand2.multiply(scale);
        }

        if(operand1.value.length < operand2.value.length) operand1.value = expandArray(operand1.value, operand2.value.length - operand1.value.length);
        n = operand2.value.length;
        int m = operand1.value.length - n ;

        int[] q = new int[m+1];
        if (operand1.compareTo(operand2.multiply((int) Math.pow(base, m))) >= 0)  {
            q[m] = 1;
            operand1 = operand1.subtract(operand2.multiply((int) Math.pow(base, m)));
        }
        else q[m] = 0;
        int qTemp;

        for(int i = m - 1; i >=0; i--){
            operand1.value = expandArray(operand1.value, n + m - operand1.value.length);
            qTemp  = (operand1.value[n + i]*base+operand1.value[n+i-1])/operand2.value[n-1];
            q[i] = Math.min(qTemp, base - 1);
            operand1 = operand1.subtract(operand2.multiply((int) (q[i] * Math.pow(base, i))));
            while(operand1.compareTo(HugeInteger.ZERO)<0){
                q[i] = q[i] - 1;
                operand1 = operand1.add(operand2.multiply((int) Math.pow(base, i)));
            }
        }

        return new HugeInteger(convertToString(this.sign / val.sign, q, this.base));

    }

    public HugeInteger multiply(long value){
        return multiply(HugeInteger.valueOf(value));
    }

    public HugeInteger multiply(HugeInteger val){
        int[] result = new int[value.length + val.value.length + 1];
        int temp;
        int q = 0;
        for(int i = 0; i < value.length; i++){
            for(int j = 0; j < val.value.length; j++){
                temp = result[i+j] + q + value[i]*val.value[j];
                result[i+j] = temp % base;
                q = temp / base;
            }
            result[i + val.value.length] = q;
            q = 0;
        }
        return new HugeInteger(convertToString(sign * val.sign, result, this.base));
    }

    public static HugeInteger valueOf(long val){
        return new HugeInteger(String.valueOf(val));
    }

    @Override
    public String toString() {
        return convertToString(sign, value, this.base);
    }

    @Override
    public int compareTo(HugeInteger o) {
        if(o == null) throw new NullPointerException("arg is null");

        //Проверка по знаку
        if(this.sign > o.sign) return 1;
        else if(this.sign < o.sign) return -1;

        //проверка по длинне массивов
        if(this.value.length < o.value.length) return -1;
        else if (this.value.length > o.value.length ) return 1;

        //Проверка по элементно
        if(sign > 0) {
            for (int i = value.length - 1; i >= 0; i--) {
                if (value[i] > o.value[i]) return 1;
                else if (value[i] < o.value[i]) return -1;
            }
        }else
        {
            for (int i = value.length - 1; i >= 0; i--) {
                if (value[i] > o.value[i]) return -1;
                else if (value[i] < o.value[i]) return 1;
            }
        }

        return 0;
    }

    public HugeInteger remainder(HugeInteger val){
        HugeInteger q = this.divide(val);
        return this.subtract(q.multiply(val));

    }


    public HugeInteger multiplyKaratsuba(HugeInteger val){
        if(val.value.length < 80) return this.multiply(val);

        HugeInteger a = this.abs();
        HugeInteger b = val.abs();

        int k = Math.max(value.length, val.value.length) / 2;
        k = (k / 2) + (k % 2);

        HugeInteger a0 = a.remainder(HugeInteger.valueOf((int) Math.pow(base, k)));
        HugeInteger b0 = b.remainder(HugeInteger.valueOf((int) Math.pow(base, k)));

        HugeInteger a1 = a.divide(HugeInteger.valueOf((int) Math.pow(base, k)));
        HugeInteger b1 = b.divide(HugeInteger.valueOf((int) Math.pow(base, k)));

        HugeInteger c0 = a0.multiplyKaratsuba(b0);
        HugeInteger c1 = a1.multiplyKaratsuba(b1);

        HugeInteger absPart1 = a0.subtract(a1).abs();
        HugeInteger absPart2 = b0.subtract(b1).abs();

        HugeInteger c2 = absPart1.multiplyKaratsuba(absPart2);

        int sa = a0.subtract(a1).sign;
        int sb = b0.subtract(b1).sign;
        c2.sign = sa * sb;

        HugeInteger part = c0.add(c1).subtract(c2);
        part = part.multiply((int) Math.pow(base, k));

        HugeInteger part2 = c1.multiply((int)Math.pow(base, 2 * k));
        HugeInteger result = c0.add(part).add(part2);
        result.sign = this.sign * val.sign;

        return  result;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HugeInteger that = (HugeInteger) o;

        if (base != that.base) return false;
        if (digitBase != that.digitBase) return false;
        if (sign != that.sign) return false;
        if (!Arrays.equals(value, that.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? Arrays.hashCode(value) : 0;
        result = 31 * result + sign;
        result = 31 * result + base;
        result = 31 * result + digitBase;
        return result;
    }

    /**
     * Метод преобразует число представленное в форме массива в его строковый аналог
     * @param sign Знак числа
     * @param value Число представленное в виде массива
     * @param base Основание в котором представлено число
     * @return Возращает число в строковом формате. Пример: convertToString(-1, [1,2,3], 10) -> "123"
     */
    private static String convertToString(int sign, int[] value, int base) {
        StringBuilder resultStr = new StringBuilder();
        int digitBase = (int)Math.log10(base);

        if(sign == -1) resultStr.append('-');
        String frmt = "%0" + digitBase + "d"; //формируем формат вывода с учетом основания
        if (value.length - 1 >= 0) resultStr.append(value[value.length-1]);
        for(int i = value.length - 2 ; i >= 0 ; i--)
            resultStr.append(String.format(frmt, value[i]));
        return resultStr.toString();
    }


    /**
     * Метод удаляет незначимые нули
     * @param value число которое необходимо обработать
     * @return Возвращает число в виде строки без незначимых нулей. Пример: 0001546 -> 1546
     */
    private static String removeLeadingZeroes(String value) {
        char[] valueByArray = value.toCharArray();
        int i = 0;

        for(; (i < valueByArray.length); i++){
            if(valueByArray[i] != '0') break;
        }
        return value.substring(i, value.length());
    }

    private static int[] reverseArray(int[] value) {
        int temp;
        int[] tempArray = new int[value.length];
        System.arraycopy(value, 0, tempArray, 0, value.length);

        for(int i = 0; i < tempArray.length / 2; i++){
            temp = tempArray[i];
            tempArray[i] = tempArray[tempArray.length - 1 - i];
            tempArray[tempArray.length - 1 -i] = temp;
        }
        return tempArray;
    }

    /**
     * Метод расширяет полученный массив
     * @param extended массив который нужно расширить
     * @param num насколько требуется увеличить массив
     * @return Возвращает копию полученного массива увеличиного на num элементов
     */
    private static int[] expandArray(int[] extended, int num) {
        if(num < 0) num = 0;
        int[] newArr = new int[extended.length + num];
        System.arraycopy(extended, 0, newArr, 0, extended.length);
        return newArr;
    }
}
