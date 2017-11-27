package com.jinkegroup.supportlib.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MathUtils {
    public static BigDecimal stringToBigDecemal(String value) {
        BigDecimal result;
        if (TextUtils.isEmpty(value)) {
            result = new BigDecimal("0");
        } else {
            result = new BigDecimal(value);
        }
        return result;
    }

    public static double ADividerB(double a, double b) {
        double result = 0.0;
        if (b == 0) {
            result = 0.0;
        } else {
            result = a / b;
        }
        return result;
    }

    /**
     * 保留2位小数
     *
     * @param d
     * @return double
     * @author jason
     * @date 2016年3月23日
     */

    public static double keepTwoDecimalPlaces(double d) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return Double.valueOf(df.format(d));
    }

    /**
     * 保留2位小数
     *
     * @param b
     * @return String
     * @author jason
     * @date 2016年3月30日
     */
    public static String keepTwoDecimalPlaces(BigDecimal b) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(b);
    }

    /**
     * 计算a*b保留2位小数
     *
     * @param a
     * @param b
     * @return String
     * @author jason
     * @date 2016年3月30日
     */
    public static String decimalMultiplyKeepTwoPlaces(double a, double b) {
        BigDecimal bda = new BigDecimal(a);
        BigDecimal bdb = new BigDecimal(b);
        return keepTwoDecimalPlaces(bda.multiply(bdb));
    }

    /**
     * 计算a*b保留3位小数
     *
     * @param a
     * @param b
     * @return String
     * @author jason
     * @date 2016年3月30日
     */
    public static String decimalMultiplyKeepThreePlaces(double a, double b) {
        BigDecimal bda = new BigDecimal(a);
        BigDecimal bdb = new BigDecimal(b);
        return keepThreeDecimalPlaces(bda.multiply(bdb));
    }

    /**
     * 计算a*b保留2位小数
     *
     * @param aa
     * @param bb
     * @return double
     * @author jason
     * @date 2016年3月31日
     */
    public static double decimalMultiplyKeepThreePlacesDown(double aa, double bb) {
        BigDecimal a = new BigDecimal(aa);
        BigDecimal b = new BigDecimal(bb);
        double mul = Double.parseDouble(keepTwoDecimalPlaces(a.multiply(b)));
        double mul1 = a.multiply(b).doubleValue();
        return mul == mul1 ? mul : decimalAddKeepTwoPlaces(mul1, -0.01);
    }

    /**
     * 计算a*b保留2位小数
     *
     * @param a
     * @param b
     * @return double
     * @author jason
     * @date 2016年3月31日
     */
    public static double decimalMultiplyKeepTwoPlaces(BigDecimal a, BigDecimal b) {
        double mul = Double.parseDouble(keepTwoDecimalPlaces(a.multiply(b)));
        double mul1 = a.multiply(b).doubleValue();
        return mul == mul1 ? mul : decimalAddKeepTwoPlaces(mul1, 0.01);
    }

    /**
     * 计算a*b保留2位小数
     *
     * @param a
     * @param b
     * @return double
     * @author jason
     * @date 2016年3月31日
     */
    public static double decimalMultiplyKeepTwoPlacesDown(BigDecimal a, BigDecimal b) {
        double mul = Double.parseDouble(keepTwoDecimalPlaces(a.multiply(b)));
        double mul1 = a.multiply(b).doubleValue();
        return mul == mul1 ? mul : decimalAddKeepTwoPlaces(mul1, -0.01);
    }

    /**
     * 计算a+b+c保留2位小数
     *
     * @param a
     * @param b
     * @param c
     * @return double
     * @author jason
     * @date 2016年4月5日
     */
    public static double decimalAddKeepTwoPlaces(double a, double b, double c) {
        BigDecimal dba = new BigDecimal(a);
        BigDecimal dbb = new BigDecimal(b);
        BigDecimal dbc = new BigDecimal(c);
        return keepTwoDecimalPlaces(dba.add(dbb).add(dbc).doubleValue());
    }

    /**
     * 计算a+b+c保留2位小数
     *
     * @param a
     * @param b
     * @param c
     * @return double
     * @author jason
     * @date 2016年4月5日
     */
    public static double decimalAddKeepThreePlaces(double a, double b, double c) {
        BigDecimal dba = new BigDecimal(a);
        BigDecimal dbb = new BigDecimal(b);
        BigDecimal dbc = new BigDecimal(c);
        return keepThreeDecimalPlaces(dba.add(dbb).add(dbc).doubleValue());
    }

    /**
     * 计算a+b+c保留2位小数
     *
     * @param a
     * @param b
     * @param c
     * @return double
     * @author jason
     * @date 2016年4月5日
     */
    public static double decimalAddKeepThreePlaces(double a, double b, double c, double d) {
        BigDecimal dba = new BigDecimal(a);
        BigDecimal dbb = new BigDecimal(b);
        BigDecimal dbc = new BigDecimal(c);
        BigDecimal dbd = new BigDecimal(d);
        return keepThreeDecimalPlaces(dba.add(dbb).add(dbc).add(dbd).doubleValue());
    }

    /**
     * 计算a+b保留2位小数
     *
     * @param a
     * @param b
     * @return double
     * @author jason
     * @date 2016年4月5日
     */
    public static double decimalAddKeepTwoPlaces(double a, double b) {
        BigDecimal dba = new BigDecimal(a);
        BigDecimal dbb = new BigDecimal(b);
        return keepThreeDecimalPlaces(dba.add(dbb).doubleValue());
    }

    /**
     * a-b保留3位小数
     *
     * @param a
     * @param b
     * @return double
     * @author jason
     * @date 2016年4月5日
     */
    public static double decimalReduceKeepTwoPlaces(double a, double b) {
        BigDecimal dba = new BigDecimal(a);
        BigDecimal dbb = new BigDecimal(b);
        return keepTwoDecimalPlaces(dba.subtract(dbb).doubleValue());
    }

    /**
     * a-b保留2位小数
     *
     * @param a
     * @param b
     * @return double
     * @author jason
     * @date 2016年4月5日
     */
    public static double decimalReduceKeepThreePlaces(double a, double b) {
        BigDecimal dba = new BigDecimal(a);
        BigDecimal dbb = new BigDecimal(b);
        return keepThreeDecimalPlaces(dba.subtract(dbb).doubleValue());
    }

    public static double decimalReduceKeepTwoPlaces(double a, double b, double c) {
        BigDecimal dba = new BigDecimal(a);
        BigDecimal dbb = new BigDecimal(b);
        BigDecimal dbc = new BigDecimal(c);
        return keepTwoDecimalPlaces(dba.subtract(dbb).subtract(dbc).doubleValue());
    }

    public static double decimalReduceKeepThreePlaces(double a, double b, double c) {
        BigDecimal dba = new BigDecimal(a);
        BigDecimal dbb = new BigDecimal(b);
        BigDecimal dbc = new BigDecimal(c);
        return keepThreeDecimalPlaces(dba.subtract(dbb).subtract(dbc).doubleValue());
    }

    /**
     * 保留3位小数
     *
     * @param total_gold
     * @return
     */
    public static double keepThreeDecimalPlaces(double total_gold) {
        DecimalFormat df = new DecimalFormat("#0.000");
        return Double.valueOf(df.format(total_gold));
    }

    /**
     * Double转Int四舍五入
     *
     * @param value
     * @return
     */
    public static String doubleToInt(double value) {
        String result = null;
        if (value == Double.NaN || value == Double.NEGATIVE_INFINITY || value == Double.POSITIVE_INFINITY) {
            result = "0";
        } else {
            DecimalFormat df = new DecimalFormat("######0");
            result = df.format(value);
        }
        return result;
    }

    /**
     * Double保留两位小数四舍五入
     *
     * @param value
     * @return
     */
    public static String doubleKeepToDecimal(double value) {
        String f1 = null;
        try {
            BigDecimal b = new BigDecimal(value);
            f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {
            f1 = "0.00";
            e.printStackTrace();
            return f1;
        }
        return f1;
    }

    /**
     * 保留3位小数
     *
     * @param b
     * @return
     */
    public static String keepThreeDecimalPlaces(BigDecimal b) {
        DecimalFormat df = new DecimalFormat("#0.000");
        return df.format(b);
    }

    /**
     * a/b保留2位小数
     *
     * @param a
     * @param b
     * @return String
     * @author jason
     * @date 2016年4月21日
     */
    public static String decimalADivideB(String a, String b) {
        BigDecimal ba = new BigDecimal(a);
        BigDecimal bb = new BigDecimal(b);
        return keepThreeDecimalPlaces(ba.divide(bb, 2, RoundingMode.FLOOR));
    }


    /**
     * a/b保留2位小数
     *
     * @param a
     * @param b
     * @return String
     * @author jason
     * @date 2016年4月21日
     */
    public static BigDecimal decimalADivideB(double a, double b) {
        BigDecimal result = null;
        if (b != 0) {
            try {
                BigDecimal ba = new BigDecimal(a);
                BigDecimal bb = new BigDecimal(b);
                result = ba.divide(bb, 2, RoundingMode.FLOOR);
            } catch (ArithmeticException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * a/b保留position位小数
     *
     * @param a
     * @param b
     * @return String
     * @author jason
     * @date 2016年4月21日
     */
    public static double ADivideB(double a, double b, int position) {
        double result = 0.00;
        try {
            BigDecimal ba = new BigDecimal(a);
            BigDecimal bb = new BigDecimal(b);
            result = ba.divide(bb, position, RoundingMode.FLOOR).doubleValue();
        } catch (ArithmeticException e) {
            e.printStackTrace();
            return 0;
        }

        return result;
    }

    /**
     * a/b保留2位小数
     *
     * @param a
     * @param b
     * @return String
     * @author jason
     * @date 2016年4月21日
     */
    public static String doubleADivideB(double a, double b) {
        String result = "0.00";
        if (b != 0) {
            try {
                BigDecimal ba = new BigDecimal(a);
                BigDecimal bb = new BigDecimal(b);
                result = ba.divide(bb, 2, RoundingMode.FLOOR).toString();
            } catch (ArithmeticException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static double decimalAddThreeTwoPlaces(double a, double b) {
        BigDecimal dba = new BigDecimal(a);
        BigDecimal dbb = new BigDecimal(b);
        return keepThreeDecimalPlaces(dba.add(dbb).doubleValue());
    }

    /**
     * double保存3位小数
     *
     * @param a
     * @return double
     * @author jason
     * @date 2016年6月10日
     */

    public static double get3Double(double a) {
        int inta = (int) (a * 1000);
        return inta / 1000.0;
    }


    /**
     * 返回原值的百分比
     *
     * @param original
     * @param pa
     * @param pb
     * @return double
     * @author jason
     * @date 2016年6月10日
     */
    public static double numberDivideByPercent(double original, double pa, double pb) {
        String result = keepThreeDecimalPlaces(decimalADivideB(pa, pb).multiply(new BigDecimal(original)));
        return Double.parseDouble(result);
    }

    public static void main(String[] args) {
        String a = "130";
        String b = "268.79";
        System.out.println(decimalADivideB(a, b));
    }
}
