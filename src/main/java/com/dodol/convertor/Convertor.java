package com.dodol.convertor;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Convertor {
    public static void main(String[] args) {
        new Convertor().init();
    }

    public void init() {
        // 1. 전문
        List<String> literalList = new ArrayList<>();
        literalList.add(" ,A.LION_ANIMAL_TYPE, B.LION_ANIMAL, C.APPLE_FRUIT, D.PEACH_FRUIT_TYPE ");
        literalList.add(" (E.MBC_BROAD_TYPE) AS mbc_broad_type, F.MBC_BROAD, G.KBS_BROAD, H.KBS_BROAD_TYPE ");


        // 2. 전문 한줄씩 변경한다.
        String rtn = "";
        for(String literal :literalList) {
            rtn = convet(literal);
        }


    }

    public String convet(String literal) {
        List<String> filterLiteral = new ArrayList();
        filterLiteral.add("LION_TYPE:LION_ANIMAL_TYPE");
        filterLiteral.add("LION_TYPE:LION_ANIMAL");
        filterLiteral.add("APPLE_TYPE:APPLE_FRUIT");
        filterLiteral.add("PEACH_TYPE:PEACH_FRUIT_TYPE");
        filterLiteral.add("MBC_TYPE:MBC_BROAD_TYPE");
        filterLiteral.add("MBC_TYPE:MBC_BROAD");
        filterLiteral.add("MBS_TYPE:KBS_BROAD_TYPE");
        filterLiteral.add("KBS_TYPE:KBS_BROAD");



        filterLiteral.stream().sorted().unordered();






        return literal;

    }


}
