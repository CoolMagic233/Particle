package me.coolmagic233.particle;

import cn.nukkit.math.Vector3;

import java.util.*;

public class Utils {
    /**
     * 将使用分割符号的字符串分割为数组
     * @param key 解析字符串
     * @param indexFormat 分割符
     *
     * @return list
     */
    public static List<Object> toList(String key,String indexFormat){
        int index = 0;
        for (char c : key.toCharArray()) {
            if(String.valueOf(c).equals(indexFormat)) index++;
        }
        String[] ketArray = key.split(indexFormat);
        return new ArrayList<>(Arrays.asList(ketArray));
    }

    /**
     * 将list转换为拥有自定义分隔符的String
     * @param list 解析list
     * @param indexFormat 分割符
     *
     * @return String
     */
    public static String formList(List list, String indexFormat){
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : list) {
            stringBuilder.append(o).append(indexFormat);
        }
        return String.join(indexFormat, stringBuilder.toString().split(indexFormat));
    }



    /**
     * 根据圆心坐标和圆的半径获取圆边上的点
     * @param center 圆心
     * @param diameter 半径
     *
     * @return 圆边上的点
     */
    public static LinkedList<Vector3> getRoundEdgePoint(Vector3 center, double diameter) {
        LinkedList<Vector3> list = new LinkedList<>();
        Vector3 point = center.clone();
        point.x += diameter;
        double xDistance = point.x - center.x;
        double zDistance = point.z - center.z;
        for (int i = 0; i < 360; i += 1) {
            list.add(new Vector3(
                    xDistance * Math.cos(i) - zDistance * Math.sin(i) + center.x,
                    center.y,
                    xDistance * Math.sin(i) + zDistance * Math.cos(i) + center.z));
        }
        return list;
    }
}
