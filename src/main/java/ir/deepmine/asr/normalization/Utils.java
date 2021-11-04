package ir.deepmine.asr.normalization;

import java.util.StringJoiner;

class Utils {
    /**
     * This method convert English digits to Persian one.
     *
     * @param inputString Input digit string
     * @return Normalized (Persian) digits
     */
    public static String normalizeDigits(String inputString) {
        StringBuilder output = new StringBuilder(inputString);
        for (int i = 0; i < inputString.length(); i++) {
            char ch = output.charAt(i);
            char c = ch;
            switch (ch) {
                case '1':
                    c = '۱';
                    break;
                case '2':
                    c = '۲';
                    break;
                case '3':
                    c = '۳';
                    break;
                case '4':
                    c = '۴';
                    break;
                case '5':
                    c = '۵';
                    break;
                case '6':
                    c = '۶';
                    break;
                case '7':
                    c = '۷';
                    break;
                case '8':
                    c = '۸';
                    break;
                case '9':
                    c = '۹';
                    break;
                case '0':
                    c = '۰';
                    break;
                default:
                    break;
            }
            if (c != ch)
                output.setCharAt(i, c);
        }
        return output.toString();
    }

    public static String join(CharSequence delimiter,
                              Iterable<? extends CharSequence> elements) {
        StringJoiner joiner = new StringJoiner(delimiter);
        for (CharSequence cs: elements) {
            joiner.add(cs);
        }
        return joiner.toString();
    }
}
