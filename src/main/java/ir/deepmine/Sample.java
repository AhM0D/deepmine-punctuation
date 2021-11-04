package ir.deepmine;

import ir.deepmine.asr.normalization.Punctuations;
import ir.deepmine.asr.normalization.WordToNumber;

public class Sample {
    public static void main(String[] argv) {
        Punctuations punctuations = Punctuations.getInstance();
        // first enabled default punctuations should be add
        punctuations.add("نقطه ویرگول", "؛ ");
        punctuations.add("علامت تعجب", "! ");
        punctuations.add("نقطه", ". ");
        punctuations.add("دو نقطه", ": ");
        punctuations.add("سه نقطه", "… ");
        // then the users punctuation should be added. It replaces the default one.
        punctuations.add("علامت تعجب", " ! ");
        WordToNumber wordToNumber = WordToNumber.getInstance();

        // you can remove a punctuation (for example after deleted/disabled by the user
        punctuations.remove("علامت تعجب");

        // For converting, first punctuation should be replaced and then word to numbers.
        String test = "امروز هوا سرد است دو نقطه حداقل دما سه نقطه منفی دوازده درجه و حداکثر آن سیزده ممیز پنج دهم درجه خواهد بود علامت تعجب";
        String output = punctuations.apply(test);
        output = wordToNumber.convert(output, true, false, false);
        System.out.println(output);
    }
}
