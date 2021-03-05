package com.example.dicenotation;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiceNotation {

    private static String pattern =
            "(?<A>\\d*)d((?<B>\\d+)" +
                    "(?<math>(?<mult>[x\\/](?<C>\\d+))?(?<add>[+-](?<D>\\d+))?)?)?";
    private static Pattern p = Pattern.compile(pattern);
    private Random random;

    private int a, b, c, d; // components of a DiceNotation
    private String groupAdd, groupMult, groupMath;
    private String notation;

    private String lastFormula;

    public static boolean isValid(String notation) {
        return p.matcher(notation).matches();
    }


    public DiceNotation(String notation) throws IllegalArgumentException {

        this.notation = notation;
        random = new Random();
        Matcher m = p.matcher(notation);

        if (m.matches()) {
            String groupA = m.group("A");
            if (groupA == null || groupA.equals(""))
                groupA = "1"; // default one roll

            String groupB = m.group("B");
            if (groupB == null)
                groupB = "6"; // default six-sided die

            String groupC = m.group("C");
            if (groupC == null)
                groupC = "1"; // default multiply or divide by 1

            String groupD = m.group("D");
            if (groupD == null)
                groupD = "0"; // default add or subtract 0

            a = Integer.parseInt(groupA);
            b = Integer.parseInt(groupB);
            c = Integer.parseInt(groupC);
            d = Integer.parseInt(groupD);

            groupMath = m.group("math");
            if (groupMath != null && groupMath.isEmpty()) {
                groupMath = null;
            }
            groupAdd = m.group("add");
            groupMult = m.group("mult");
        } else {
            throw new IllegalArgumentException("Invalid dice notation");
        }
    }

    public int roll() {
        int total = 0;
        StringBuffer formula = new StringBuffer("( (");
        for (int i = 0; i < a; i++) {
            int r = random.nextInt(b) + 1;
            if (formula.length() > 3) formula.append(" + ");
            formula.append(String.format("%d", r));
            total += r;
        }
        formula.append(")");
        if (groupMult != null) {
            if (groupMult.startsWith("/")) {
                formula.append(" / " + c);
                total /= c;
            } else {
                formula.append(" * " + c);
                total *= c;
            }
            //total = groupMult.startsWith("/") ? total / c : total * c;
        }
        formula.append(" )");
        if (groupAdd != null) {
            if (groupAdd.startsWith("+")) {
                formula.append(" + " + d);
                total += d;
            } else {
                formula.append(" - " + d);
                total -= d;
            }
            //total = () ? total + d : total - d;
        }
        lastFormula = formula.toString();
        return total;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DiceNotation{");
        sb.append("a=").append(a);
        sb.append(", b=").append(b);
        sb.append(", c=").append(c);
        sb.append(", d=").append(d);
        sb.append(", groupAdd='").append(groupAdd).append('\'');
        sb.append(", groupMult='").append(groupMult).append('\'');
        sb.append(", groupMath='").append(groupMath).append('\'');
        sb.append(", notation='").append(notation).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getLastFormula() {
        return lastFormula;
    }
    public String getNotation(){
        return notation;
    }

}
