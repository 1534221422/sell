package com.imooc.sell.util;

import java.util.Random;

public class KeyUtil {
    /*生成唯一的主键*/
    public static synchronized String genUniqueKey() {

        Random rm = new Random();
        Integer number = rm.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);

    }
}
