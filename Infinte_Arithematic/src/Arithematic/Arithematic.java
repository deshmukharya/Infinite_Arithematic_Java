package Arithematic;
import java.util.Arrays;

class BigNumber {
    private int[] digits;

    public BigNumber(int[] digits) {
        this.digits = Arrays.copyOf(digits, digits.length);
    }

    public BigNumber(String number) {
        this.digits = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            digits[i] = Character.digit(number.charAt(i), 10);
        }
    }

    public BigNumber add(BigNumber other) {
        int maxLength = Math.max(digits.length, other.digits.length);
        int[] result = new int[maxLength + 1];
        int carry = 0;
        int index1 = digits.length - 1;
        int index2 = other.digits.length - 1;
        int indexResult = result.length - 1;

        while (index1 >= 0 || index2 >= 0) {
            int num1 = index1 >= 0 ? digits[index1--] : 0;
            int num2 = index2 >= 0 ? other.digits[index2--] : 0;
            int sum = num1 + num2 + carry;
            result[indexResult--] = sum % 10;
            carry = sum / 10;
        }

        if (carry != 0) {
            result[0] = carry;
        }

        if (result[0] == 0) {
            return new BigNumber(Arrays.copyOfRange(result, 1, result.length));
        } else {
            return new BigNumber(result);
        }
    }

    public BigNumber subtract(BigNumber other) {
        int maxLength = Math.max(digits.length, other.digits.length);
        int[] result = new int[maxLength];
        int borrow = 0;
        int index1 = digits.length - 1;
        int index2 = other.digits.length - 1;
        int indexResult = result.length - 1;

        while (index1 >= 0 || index2 >= 0) {
            int num1 = index1 >= 0 ? digits[index1--] : 0;
            int num2 = index2 >= 0 ? other.digits[index2--] : 0;
            int diff = num1 - num2 - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            result[indexResult--] = diff;
        }

        while (result.length > 1 && result[0] == 0) {
            result = Arrays.copyOfRange(result, 1, result.length);
        }

        return new BigNumber(result);
    }

    public BigNumber multiply(BigNumber other) {
        int[] result = new int[digits.length + other.digits.length];

        for (int i = digits.length - 1; i >= 0; i--) {
            int carry = 0;
            for (int j = other.digits.length - 1; j >= 0; j--) {
                int product = digits[i] * other.digits[j] + result[i + j + 1] + carry;
                result[i + j + 1] = product % 10;
                carry = product / 10;
            }
            result[i] += carry;
        }

        while (result.length > 1 && result[0] == 0) {
            result = Arrays.copyOfRange(result, 1, result.length);
        }

        return new BigNumber(result);
    }

    public BigNumber divide(BigNumber divisor) {
        BigNumber quotient = new BigNumber("0");
        BigNumber remainder = new BigNumber(this.digits);

        while (remainder.isGreaterThanOrEqualTo(divisor)) {
            BigNumber tempDivisor = new BigNumber(divisor.digits);
            BigNumber tempQuotient = new BigNumber("1");

            while (remainder.isGreaterThanOrEqualTo(tempDivisor.multiply(new BigNumber("2")))) {
                tempDivisor = tempDivisor.multiply(new BigNumber("2"));
                tempQuotient = tempQuotient.multiply(new BigNumber("2"));
            }

            remainder = remainder.subtract(tempDivisor);
            quotient = quotient.add(tempQuotient);
        }

        return quotient;
    }

    private boolean isGreaterThanOrEqualTo(BigNumber other) {
        if (digits.length != other.digits.length) {
            return digits.length > other.digits.length;
        }
        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != other.digits[i]) {
                return digits[i] > other.digits[i];
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int digit : digits) {
            sb.append(digit);
        }
        return sb.toString();
    }
}

public class Arithematic {
	public static void main(String[] args) {
	    // Test addition
	    BigNumber num1 = new BigNumber("12345");
	    BigNumber num2 = new BigNumber("6789");
	    BigNumber sum = num1.add(num2);
	    System.out.println("Sum: " + sum);

	    // Test subtraction
	    BigNumber diff = num1.subtract(num2);
	    System.out.println("Difference: " + diff);

	    // Test multiplication
	    BigNumber product = num1.multiply(num2);
	    System.out.println("Product: " + product);

	    // Test division
	    BigNumber quotient = num1.divide(num2);
	    System.out.println("Quotient: " + quotient);
	}
}
