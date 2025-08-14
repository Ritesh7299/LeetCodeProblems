package leetcode.solution.LC1071;

public class GcdOfStrings {
    public String gcdOfStrings(String str1, String str2) {
        if (str1.length() == str2.length() && !str1.equals(str2)) {
            return "";
        }

        int l1 = str1.length();
        int l2 = str2.length();


        int minLen = Math.min(l1, l2);

        for (int len = minLen; len >= 1; len--) {
            if (l1 % len == 0 && l2 % len ==0) {
                String pattern = str1.substring(0, len);

                if (validatePattern(str1, pattern) && validatePattern(str2, pattern)) {
                    return pattern;
                }
            }
        }

        return "";
    }

    public boolean validatePattern(String str, String pattern) {
        int pLen = pattern.length();
        int sLen = str.length();

        for (int i = 0; i <= sLen - pLen; i += pLen) {
            if (!str.substring(i, i + pLen).equals(pattern)) {
                return false;
            }
        }

        return true;
    }
}
