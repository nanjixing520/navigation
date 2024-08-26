package com.njx.utils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * ClassName: StringUtils
 * Package: com.njx.utils
 * Description:
 *
 * @Author 南极星
 * @Create 2024/6/17 16:15
 * Version 1.0
 */
public class StringUtils {
    //合并并且去重的方法
    public static String[] mergeAndDeduplicate(String[] array1, String[] array2) {
        String[] combinedArray = new String[array1.length + array2.length];
        System.arraycopy(array1, 0, combinedArray, 0, array1.length);
        System.arraycopy(array2, 0, combinedArray, array1.length, array2.length);
        // 使用LinkedHashSet去重并保持插入顺序
        Set<String> uniqueElements = new LinkedHashSet<>();
        for (String element : combinedArray) {
            uniqueElements.add(element);
        }
        // 将Set转换为数组
        return uniqueElements.toArray(new String[0]);
    }
    //判断是否合法
    public boolean isQuality(String array) {
        for (int i = 0; i < array.length(); i++) {
            if(array.charAt(i)<35||array.charAt(i)>122){
                return false;
            }
        }
        return true;
    }
}
