package review;


/**
 * 数字转中文
 * 特殊case:
 * 1. 单个数 1，2，3
 * 2. 两位数 11 33 99
 * 3. 10万 11万  10110
 * 4. 11亿
 * 5. 零 0
 * 1000010000
 * 1000010010
 * 1300000001
 */
public class NumberToChinese {
    public final int part = 10000;

    public final String[] map = {"", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    // 100000000 亿 10000 万 1000千 100百 10十
    // 一 二 三 四 五.....
    // 十 二十 三十 四十 五十


    private String convert(int num) {
        // 低四位
        int partOne = num % part;
        // 中间四位
        int partTwo = (num / part) % part;
        // 剩余高位
        int partThree = (num / part / part) % part;

        StringBuilder res = new StringBuilder();
        if (partThree != 0) {
            handle(res, partThree);
            res.append("亿");
            if ((partTwo < 1000 && partTwo > 0) || (partThree % 10 == 0 && partTwo > 0)) {
                res.append("零");
            }
        }

        if (partTwo != 0) {
            if ((partTwo >= 10 && partTwo < 20) && partThree > 0) {
                res.append("一");
            }
            handle(res, partTwo);
            res.append("万");
            if ((partOne < 1000 && partOne > 0) || (partTwo % 10 == 0 && partOne > 0)) {
                res.append("零");
            }
        }
        if (partTwo == 0 && partThree > 0) {
            if (partOne > 0) {
                res.append("零");
            }
        }

        if (partOne != 0) {
            if ((partOne < 20 && partOne >= 10) && (partThree > 0 || partTwo > 0)) {
                res.append("一");
            }
            handle(res, partOne);
        }
        return res.toString();
    }

    // num {1, 9999}之间
    // 此函数用于将一万以下的数转为数字
    // 个位数 一 二 需要一个数组
    // 十位数 十 二十 三十 需要一个数组
    // 百位数 一百 两百 三百....这里不需要
    private void handle(StringBuilder str, int num) {
        // 个位
        int one = num % 10;
        // 十位
        int two = (num / 10) % 10;
        // 百位
        int three = (num / 100) % 10;
        // 千位
        int four = (num / 1000) % 10;

        if (four != 0) {
            str.append(map[four]);
            str.append("千");
        }

        if (three != 0) {
            str.append(map[three]);
            str.append("百");
        }

        if (four != 0 && three == 0 && (two > 0 || one > 0)) {
            str.append("零");
        }

        if (two != 0) {
            // 11十一这个情况需要特殊处理
            if (two > 1) {
                str.append(map[two]);
            }
            if (two == 1 && (three > 0 || four > 0))
                str.append("一");

            str.append("十");
        }

        if (two == 0 && three != 0 && one > 0) {
            str.append("零");
        }
        str.append(map[one]);
    }

    public static void main(String[] args) {
        NumberToChinese main = new NumberToChinese();
        System.out.println(main.convert(134790000));
    }
}
