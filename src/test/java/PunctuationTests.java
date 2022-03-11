import ir.deepmine.asr.normalization.Punctuations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PunctuationTests {
    Punctuations punctuations = Punctuations.getInstance();
    {
        punctuations.add("نقطه ویرگول", "؛ ");
        punctuations.add("علامت تعجب", "! ");
        punctuations.add("نقطه", ". ");
        punctuations.add("دلار", " $ ");
        punctuations.add("خط جدید", "\\n");

    }

    String[][] testCases = {
            {"امروز هوا سرد است نقطه ویرگول نه اینطور نیست نقطه",
                    "امروز هوا سرد است؛ نه اینطور نیست. "},
            {"امروز هوا سرد است نقطه‌ویرگول نه اینطور علامت‌تعجب نیست نقطه",
                    "امروز هوا سرد است؛ نه اینطور! نیست. "},
            {"این یک علامت دلار است نقطه",
                    "این یک علامت $ است. "},
            {"این یک خط جدید است نقطه",
                    "این یک\nاست. "},
    };

    @Test
    void test() {
        for (String[] testCase : testCases) {
            String outText = punctuations.apply(testCase[0]);
            Assertions.assertEquals(testCase[1], outText);
        }
    }
}
