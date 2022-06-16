package math;

public class NumberToWords {
    String[] oneToNine = {"", "One ", "Two ", "Three ", "Four ", "Five ", "Six ", "Seven ", "Eight ", "Nine "};
    String[] tenToNineteen = {"Ten ", "Eleven ", "Twelve ", "Thirteen ", "Fourteen ", "Fifteen ", "Sixteen ", "Seventeen ", "Eighteen ", "Nineteen "};
    String[] twentyToNinety = {"", "", "Twenty ", "Thirty ", "Forty ", "Fifty ", "Sixty ", "Seventy ", "Eighty ", "Ninety "};

    public String numberToWords(int num) {
        if (num == 0) {
            System.out.println("Zero");
            return "Zero";
        }
        //小于1000的部分
        int part1 = num % 1000;
        num = num / 1000;
        //1000到100万直接的部分
        int part2 = num % 1000;
        num = num / 1000;
        //100万到10亿之间的部分
        int part3 = num % 1000;
        num = num / 1000;
        //10亿以上的部分
        int part4 = num;

        StringBuilder res = new StringBuilder();
        if (part4 > 0) {
            handlePart(part4, res);
            res.append("Billion ");
        }
        if (part3 > 0) {
            handlePart(part3, res);
            res.append("Million ");
        }
        if (part2 > 0) {
            handlePart(part2, res);
            res.append("Thousand ");
        }
        if (part1 > 0) {
            handlePart(part1, res);
        }
        res.deleteCharAt(res.length() - 1);
        System.out.println(res.toString());
        return res.toString();
    }

    public void handlePart(int part, StringBuilder res) {
        //个位
        int part1 = part % 10;
        part = part / 10;
        //十位
        int part2 = part % 10;
        part = part / 10;
        //百位
        int part3 = part;

        //百位
        if (part3 > 0) {
            res.append(oneToNine[part3]);
            res.append("Hundred ");
        }

        //十位
        if (part2 == 1) {
            res.append(tenToNineteen[part1]);
        } else if (part2 > 1) {
            res.append(twentyToNinety[part2]);
            //个位
            res.append(oneToNine[part1]);
        }else {
            //个位
            res.append(oneToNine[part1]);
        }
    }

    public static void main(String[] args) {
        NumberToWords nm = new NumberToWords();
        String res = nm.numberToWords(12321341);
        assert res.equals("One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven");

    }
}
