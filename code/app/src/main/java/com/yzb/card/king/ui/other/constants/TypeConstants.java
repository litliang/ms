package com.yzb.card.king.ui.other.constants;

/**
 * Created by dev on 2016/5/30.
 */
public class TypeConstants {

    public enum FirstType {
        MEISHI("美食", 1),
        LIREN("丽人", 2),
        LVYOU("旅游", 3),
        HUNQING("婚庆", 4),
        SHECHIPIN("奢侈品", 5),
        JIPIAO("机票", 6),
        DIANYING("电影", 7),
        JIUDIAN("酒店", 8),
        LUSHANGJIAOTONG("陆上交通", 9);

        private String name;
        private int index;

        FirstType(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }
    }

    public enum LushangjiaotongType {
        HUOCHEPIAO("火车票", 77),
        QICHEPIAO("汽车票", 78),
        CUANPIAO("船票", 79),
        JIESONGJI("接送机", 80),
        JIESONGHUOCHE("接送火车", 81),
        ZHUANCHECHUXING("专车出行", 82),
        ZIJIAZUCHE("自驾租车", 83),
        RIZUBAOCHE("日租包车", 84),;

        private String name;
        private int index;

        LushangjiaotongType(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public static String getNameByIndex(int index){
            LushangjiaotongType[] types = LushangjiaotongType.values();
            String name = "";
            for(LushangjiaotongType type:types){
                if(type.getIndex()==index){
                    name = type.getName();
                    break;
                }
            }
            return name;
        }

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }
    }
}
