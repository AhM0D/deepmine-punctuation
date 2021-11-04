package ir.deepmine.asr.normalization;

import java.util.*;

final class Constant {
    static final Map<String, Tuple2<Long, Integer>> basicForms = new HashMap<>();
    static final Map<String, Tuple2<Long, Integer>> scaleForms = new HashMap<>();
    static final Map<String, Integer> decimalForms = new HashMap<>();

    static {
        basicForms.put("صفر", new Tuple2<>(0L, 0));
        basicForms.put("یک", new Tuple2<>(1L, 0));
        basicForms.put("دو", new Tuple2<>(2L, 0));
        basicForms.put("سه", new Tuple2<>(3L, 0));
        basicForms.put("چهار", new Tuple2<>(4L, 0));
        basicForms.put("پنج", new Tuple2<>(5L, 0));
        basicForms.put("شش", new Tuple2<>(6L, 0));
        basicForms.put("شیش", new Tuple2<>(6L, 0));
        basicForms.put("هفت", new Tuple2<>(7L, 0));
        basicForms.put("هشت", new Tuple2<>(8L, 0));
        basicForms.put("نه", new Tuple2<>(9L, 0));
        basicForms.put("ده", new Tuple2<>(10L, 1));
        basicForms.put("یازده", new Tuple2<>(11L, 1));
        basicForms.put("دوازده", new Tuple2<>(12L, 1));
        basicForms.put("سیزده", new Tuple2<>(13L, 1));
        basicForms.put("چهارده", new Tuple2<>(14L, 1));
        basicForms.put("پانزده", new Tuple2<>(15L, 1));
        basicForms.put("شانزده", new Tuple2<>(16L, 1));
        basicForms.put("هفده", new Tuple2<>(17L, 1));
        basicForms.put("هیفده", new Tuple2<>(17L, 1));
        basicForms.put("هجده", new Tuple2<>(18L, 1));
        basicForms.put("هیجده", new Tuple2<>(18L, 1));
        basicForms.put("نوزده", new Tuple2<>(19L, 1));
        basicForms.put("بیست", new Tuple2<>(20L, 1));
        basicForms.put("سی", new Tuple2<>(30L, 1));
        basicForms.put("چهل", new Tuple2<>(40L, 1));
        basicForms.put("پنجاه", new Tuple2<>(50L, 1));
        basicForms.put("شصت", new Tuple2<>(60L, 1));
        basicForms.put("هفتاد", new Tuple2<>(70L, 1));
        basicForms.put("هشتاد", new Tuple2<>(80L, 1));
        basicForms.put("نود", new Tuple2<>(90L, 1));
        basicForms.put("دویست", new Tuple2<>(200L, 2));
        basicForms.put("سیصد", new Tuple2<>(300L, 2));
        basicForms.put("سی‌صد", new Tuple2<>(300L, 2));
        basicForms.put("چهارصد", new Tuple2<>(400L, 2));
        basicForms.put("پانصد", new Tuple2<>(500L, 2));
        basicForms.put("شش‌صد", new Tuple2<>(600L, 2));
        basicForms.put("ششصد", new Tuple2<>(600L, 2));
        basicForms.put("هفت‌صد", new Tuple2<>(700L, 2));
        basicForms.put("هفتصد", new Tuple2<>(700L, 2));
        basicForms.put("هشت‌صد", new Tuple2<>(800L, 2));
        basicForms.put("هشتصد", new Tuple2<>(800L, 2));
        basicForms.put("نه‌صد", new Tuple2<>(900L, 2));
        basicForms.put("نهصد", new Tuple2<>(900L, 2));

        scaleForms.put("صد", new Tuple2<>(100L, 2));
        scaleForms.put("هزار", new Tuple2<>(1000L, 3));
        scaleForms.put("میلیون", new Tuple2<>(1000000L, 4));
        scaleForms.put("میلیارد", new Tuple2<>(1000000000L, 5));
        scaleForms.put("بیلیون", new Tuple2<>(1000000000000L, 6));
        scaleForms.put("بیلیارد", new Tuple2<>(1000000000000000L, 7));
        scaleForms.put("تریلیون", new Tuple2<>(1000000000000000000L, 8));

        decimalForms.put("دهم", 1);
        decimalForms.put("صدم", 2);
        decimalForms.put("هزارم", 3);
        decimalForms.put("ده‌هزارم", 4);
        decimalForms.put("صدهرازم", 5);
        decimalForms.put("میلیونیم", 6);
        decimalForms.put("میلیاردم", 7);
    }
}

public class WordToNumber {

    private final Map<String, Tuple2<Long, Integer>> basicForms = Constant.basicForms;
    private final Map<String, Tuple2<Long, Integer>> scaleForms = Constant.scaleForms;
    private final Map<String, Integer> decimalForms = Constant.decimalForms;

    private static final WordToNumber instance = new WordToNumber();

    private WordToNumber() {
    }

    public static WordToNumber getInstance() {
        return instance;
    }

    public String convert(String str, boolean convertGeneralNumber, boolean convertDateAndTime,
                          boolean convertPhoneNumber) {
        if (!convertGeneralNumber && !convertDateAndTime && !convertPhoneNumber)
            return str;
        String[] words = str.split(" ");
        List<String> outputWords = new ArrayList<>(words.length);
        int index = 0;
        while (index < words.length) {
            if (convertDateAndTime) {
                Tuple2<String, Integer> outputTime = wordToTime(words, index);
                if (outputTime.Item1.length() == 5) {
                    outputWords.add(normalize(outputTime.Item1));
                    index = outputTime.Item2;
                    continue;
                }
            }
            Tuple2<String, Integer> outputDigit = wordToNum(words, index);

            if (convertDateAndTime) {
                Tuple2<String, Integer> outputDate = wordToDate(words, index);
                if (outputDate.Item1.length() >= 8 && outputDate.Item2 > outputDigit.Item2) {
                    outputWords.add(normalize(outputDate.Item1));
                    index = outputDate.Item2;
                    continue;
                }
            }

            if (convertPhoneNumber) {
                Tuple2<String, Integer> outputPhone = wordToPhoneNumber(words, index);
                if (outputPhone.Item1.length() >= 8 && outputPhone.Item2 > outputDigit.Item2) {
                    outputWords.add(normalize(outputPhone.Item1));
                    index = outputPhone.Item2;
                    continue;
                }
            }
            if (outputDigit.Item1.length() == 0 || !convertGeneralNumber) {
                outputWords.add(words[index]);
                index++;
            } else {
                outputWords.add(normalize(outputDigit.Item1));
                index = outputDigit.Item2;
            }
        }
        return Utils.join(" ", outputWords);
    }

    private String normalize(String str) {
        // I moved this replacement here to do it only for numbers.
        return Utils.normalizeDigits(str).replace('.', '٫');
    }

    private Tuple2<String, Integer> wordToNum(String[] words, int index) {
        try {
            Tuple2<Long, Integer> temp1;
            long tempVal = 0;
            int len = words.length;
            Tuple2<Long, Integer> temp2;
            Integer decimalPoints = null;
            int additionalZero = 0;
            boolean inDecimal = false;
            List<Tuple4<Long, Long, Integer, String>> levels = new ArrayList<>(4);
            List<Tuple4<Long, Long, Integer, String>> decimalLevels = new ArrayList<>(4);
            List<Tuple4<Long, Long, Integer, String>> currentLevels = levels;
            boolean vaIsRequired = false;
            int lastLevel = 1000, lastLevel2 = 1000;
            while (index < len) {
                temp1 = basicForms.get(words[index]);
                if (temp1 != null) {
                    if (vaIsRequired) {
                        // in this case we should finish the current number.
                        break;
                    }
                    if (temp1.Item2 < lastLevel || tempVal == 0) {
                        tempVal += temp1.Item1;
                        lastLevel = temp1.Item2;
                    } else {
                        if (index > 1 && words[index - 1].equals("و"))
                            index--;
                        break;
                    }
                    index++;
                    // tempVal = tempVal < 0 ? temp1 : tempVal + temp1;
                    if (temp1.Item1 != 0) {
                        vaIsRequired = true;
                    } else if (inDecimal) {
                        additionalZero++;
                    } else {
                        return new Tuple2<>("0", index);
                    }
                    continue;
                }
                temp2 = scaleForms.get(words[index]);
                if (temp2 != null) {
                    if (vaIsRequired || temp2.Item2 < lastLevel2) {
                        if (temp2.Item1 != 100) {
                            if (tempVal == 0) {
                                tempVal = 1;
                            }
                            currentLevels.add(new Tuple4<>(tempVal, temp2.Item1, temp2.Item2, words[index]));
                            tempVal = 0;
                        } else {
                            if (tempVal >= 10) {
                                if (index > 1 && words[index - 1].equals("و"))
                                    index--;
                                break;
                            }
                            tempVal = tempVal == 0 ? temp2.Item1 : tempVal * temp2.Item1;
                        }
                        lastLevel = 1000;
                        lastLevel2 = temp2.Item2;
                    } else {
                        if (index > 1 && words[index - 1].equals("و"))
                            index--;
                        break;
                    }
                    vaIsRequired = true;
                    index++;
                    continue;
                }
                decimalPoints = decimalForms.get(words[index]);
                if (decimalPoints != null) {
                    index++;
                    break;
                }
                if (words[index].equals("ممیز")) {
                    vaIsRequired = false;
                    if (tempVal != 0) {
                        currentLevels.add(new Tuple4<>(tempVal, 1L, 0, ""));
                    }
                    currentLevels = decimalLevels;
                    tempVal = 0;
                    lastLevel = lastLevel2 = 1000;
                    inDecimal = true;
                    index++;
                    continue;
                }
                if (words[index].equals("و")) {
                    vaIsRequired = false;
                    if (levels.size() > 0 || tempVal != 0) {
                        index++;
                        continue;
                    }
                } else if (vaIsRequired) {
                    break;
                }
                break;
            }
            if (tempVal != 0) {
                currentLevels.add(new Tuple4<>(tempVal, 1L, 0, ""));
            }
            long outputNum = 0;
            StringBuilder outputStr = new StringBuilder(8);
            if (levels.size() > 0) {
                if (levels.get(levels.size() - 1).Item3 < 1) {
                    for (Tuple4<Long, Long, Integer, String> level : levels) {
                        outputNum += level.Item1 * level.Item2;
                    }
                    outputStr.append(outputNum);
                } else {
                    for (Tuple4<Long, Long, Integer, String> level : levels) {
                        outputStr.append(String.format(Locale.ENGLISH, "%d %s و ", level.Item1,
                                level.Item4));
                    }
                    outputStr.setLength(outputStr.length() - 3);
                }
            }
            long decimalNum = 0;
            String decimalOutputStr = "";
            if (decimalLevels.size() > 0) {
                for (Tuple4<Long, Long, Integer, String> level : decimalLevels) {
                    decimalNum += level.Item1 * level.Item2;
                }
                if (decimalPoints != null) {
                    decimalOutputStr = String.format(".%0" + decimalPoints + "d", decimalNum);
                } else if (additionalZero > 0) {
                    decimalOutputStr = String.format(".%0" + additionalZero + "d%d", 0, decimalNum);
                } else {
                    decimalOutputStr = "." + decimalNum;
                }
            }
            if (outputStr.length() == 0 && decimalOutputStr.length() > 0) {
                outputStr.append("0");
            }
            outputStr.append(decimalOutputStr);
            return new Tuple2<>(outputStr.toString(), index);
        } catch (Exception exp) {
            return new Tuple2<>("", 0);
        }
    }

    private Tuple2<String, Integer> wordToPhoneNumber(String[] words, int index) {
        try {
            Map<String, Tuple2<Long, Integer>> basicForms = Constant.basicForms;
            Map<String, Tuple2<Long, Integer>> scaleForms = Constant.scaleForms;
            Tuple2<Long, Integer> temp1;
            long tempVal = 0;
            long currentVal = 0;
            int len = words.length;
            Tuple2<Long, Integer> temp2;
            StringBuilder outputNumber = new StringBuilder(16);
            boolean commaWasLast = true;
            while (index < len) {
                temp1 = basicForms.get(words[index]);
                if (temp1 != null) {
                    index++;
                    if (!commaWasLast) {
                        outputNumber.append(tempVal + currentVal);
                        currentVal = 0;
                        tempVal = temp1.Item1;
                        continue;
                    }
                    if (temp1.Item1 == 0) {
                        outputNumber.append('0');
                        continue;
                    }
                    commaWasLast = false;
                    tempVal += temp1.Item1;
                    continue;
                }
                temp2 = scaleForms.get(words[index]);
                if (temp2 != null) {
                    commaWasLast = false;
                    currentVal += tempVal == 0 ? temp2.Item1 : tempVal * temp2.Item1;
                    tempVal = 0;
                    index++;
                    continue;
                }
                if (words[index].equals("و")) {
                    commaWasLast = true;
                    if (outputNumber.length() > 0 || tempVal != 0) {
                        index++;
                        continue;
                    }
                } else if (!commaWasLast) {
                    break;
                }
                break;
            }
            if (tempVal + currentVal != 0) {
                outputNumber.append(tempVal + currentVal);
            }

            return new Tuple2<>(outputNumber.toString(), index);
        } catch (Exception exp) {
            return new Tuple2<>("", 0);
        }
    }

    private Tuple2<String, Integer> wordToDate(String[] words, int index) {
        try {
            Map<String, Tuple2<Long, Integer>> basicForms = Constant.basicForms;
            Map<String, Tuple2<Long, Integer>> scaleForms = Constant.scaleForms;
            Tuple2<Long, Integer> temp1;
            long tempVal = 0;
            long currentVal = 0;
            int len = words.length;
            Tuple2<Long, Integer> temp2;
            boolean commaWasLast = true;
            List<Long> parts = new ArrayList<>(4);
            while (index < len) {
                temp1 = basicForms.get(words[index]);
                if (temp1 != null) {
                    index++;
                    // zero is invalid here
                    if (temp1.Item1 == 0) {
                        return new Tuple2<>("", 0);
                    }
                    if (!commaWasLast) {
                        parts.add(tempVal + currentVal);
                        currentVal = 0;
                        tempVal = temp1.Item1;
                        continue;
                    }
                    commaWasLast = false;
                    tempVal += temp1.Item1;
                    continue;
                }
                temp2 = scaleForms.get(words[index]);
                if (temp2 != null) {
                    commaWasLast = false;
                    if (parts.size() == 1 && tempVal <= 12) {
                        parts.add(tempVal);
                        tempVal = 1;
                    }
                    currentVal += tempVal == 0 ? temp2.Item1 : tempVal * temp2.Item1;
                    tempVal = 0;
                    index++;
                    continue;
                }
                if (words[index].equals("و")) {
                    commaWasLast = true;
                    if (parts.size() > 0 || tempVal != 0) {
                        index++;
                        continue;
                    }
                } else if (!commaWasLast) {
                    break;
                }
                break;
            }
            if (tempVal + currentVal != 0) {
                parts.add(tempVal + currentVal);
            }
            // the data should has exactly 3 parts
            if (parts.size() != 3 || parts.get(0) > 31 || parts.get(1) > 12) {
                return new Tuple2<>("", 0);
            }
            String outputNumber = String.format(Locale.ENGLISH, "%d/%02d/%02d", parts.get(2),
                    parts.get(1), parts.get(0));

            return new Tuple2<>(outputNumber, index);
        } catch (Exception exp) {
            return new Tuple2<>("", 0);
        }
    }

    private Tuple2<String, Integer> wordToTime(String[] words, int index) {
        try {
            Map<String, Tuple2<Long, Integer>> basicForms = Constant.basicForms;
            Tuple2<Long, Integer> temp1;
            long tempVal = 0;
            int len = words.length;
            long lastAdded = 0;
            boolean commaWasLast = true;
            boolean minWasSeen = false;
            List<Long> parts = new ArrayList<>(4);
            while (index < len) {
                temp1 = basicForms.get(words[index]);
                if (temp1 != null) {
                    index++;
                    // zero is invalid here
                    if (temp1.Item1 == 0 || (parts.size() == 0 && temp1.Item1 > 20)) {
                        return new Tuple2<>("", 0);
                    }
                    if (parts.size() == 0) {
                        parts.add(temp1.Item1);
                        commaWasLast = false;
                        tempVal = 0;
                        continue;
                    } else if (parts.size() == 1) {
                        if (!commaWasLast) {
                            parts.add(tempVal);
                            tempVal = temp1.Item1;
                        } else {
                            if (parts.get(0) == 20 && temp1.Item1 < 4) {
                                lastAdded = temp1.Item1;
                                parts.set(0, parts.get(0) + temp1.Item1);
                            } else {
                                tempVal += temp1.Item1;
                            }
                            commaWasLast = false;
                            continue;
                        }
                    }
                }
                if (!commaWasLast && words[index].equals("و")) {
                    commaWasLast = true;
                    if (parts.size() > 0 || tempVal != 0) {
                        index++;
                        continue;
                    }
                } else if (words[index].equals("دقیقه")) {
                    minWasSeen = true;
                    index++;
                    break;
                }
                break;
            }
            if (!minWasSeen) {
                return new Tuple2<>("", 0);
            }

            if (tempVal != 0) {
                parts.add(tempVal);
            }
            if (lastAdded > 0 && parts.size() == 1) {
                parts.set(0, parts.get(0) - lastAdded);
                parts.add(lastAdded);
            }
            // the data should has exactly 3 parts
            if (parts.size() != 2 || parts.get(0) > 23 || parts.get(1) > 59) {
                return new Tuple2<>("", 0);
            }
            String outputNumber = String.format(Locale.ENGLISH, "%02d:%02d", parts.get(0),
                    parts.get(1));

            return new Tuple2<>(outputNumber, index);
        } catch (Exception exp) {
            return new Tuple2<>("", 0);
        }
    }
}
