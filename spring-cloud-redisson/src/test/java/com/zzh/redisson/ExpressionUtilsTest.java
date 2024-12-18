package com.zzh.redisson;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/17 14:34
 */
@Slf4j
public class ExpressionUtilsTest {


    @Test
    public void testExpression() {
        boolean b = evaluateCondition("%", new String[]{"2"}, "==", "1", 5);
        boolean c = evaluateCondition("substring", new String[]{"0", "1"}, "in", "23,546,4", 1123);

        boolean flag = true;
        flag= evaluateLogicalCondition(true,"or",b);
        flag= evaluateLogicalCondition(flag,"and",c);
        System.out.println(b);
        System.out.println(c);
        System.out.println(flag);
    }


    private static boolean evaluateCondition(String extraOperation, String[] extraValues,
                                             String operator, String value, Object fieldValue) {
        if ("%".equals(extraOperation)) {
            double fieldVal = Double.parseDouble(fieldValue.toString());
            double divisor = Double.parseDouble(extraValues[0]);
            double result = fieldVal % divisor;
            return evaluateWithOperator(result, operator, String.valueOf(Double.parseDouble(value)));
        }
        if ("substring".equals(extraOperation)) {
            String fieldVal = fieldValue.toString();
            String subString = fieldVal.substring(Integer.parseInt(extraValues[0]), Integer.parseInt(extraValues[1]));
            return evaluateWithOperator(subString, operator, value);
        }
        return evaluateWithOperator(fieldValue, operator, value);
    }

    private static boolean evaluateWithOperator(Object fieldValue, String operator, String value) {
        switch (operator) {
            case "==" -> {
                return fieldValue.toString().equals(value);
            }
            case "!=" -> {
                return !fieldValue.toString().equals(value);
            }
            case ">" -> {
                return Double.parseDouble(fieldValue.toString()) > Double.parseDouble(value);
            }
            case "<" -> {
                return Double.parseDouble(fieldValue.toString()) < Double.parseDouble(value);
            }
            case ">=" -> {
                return Double.parseDouble(fieldValue.toString()) >= Double.parseDouble(value);
            }
            case "<=" -> {
                return Double.parseDouble(fieldValue.toString()) <= Double.parseDouble(value);
            }
            case "in" -> {
                String[] values = value.split(",");
                for (String v : values) {
                    if (fieldValue.toString().equals(v.trim())) {
                        return true;
                    }
                }
                return false;
            }
            case "between" -> {
                String[] bounds = value.split("and");
                double minValue = Double.parseDouble(bounds[0].trim());
                double maxValue = Double.parseDouble(bounds[1].trim());
                double fieldVal = Double.parseDouble(fieldValue.toString());
                return fieldVal >= minValue && fieldVal <= maxValue;
            }
            default -> {
                return false;
            }
        }
    }

    private static boolean evaluateLogicalCondition(boolean left, String logicalFlag, boolean right) {
        return switch (logicalFlag) {
            case "and" -> left && right;
            case "or" -> left || right;
            default -> left;  // 默认返回左边条件
        };
    }

}
