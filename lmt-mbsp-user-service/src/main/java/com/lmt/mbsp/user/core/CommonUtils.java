package com.lmt.mbsp.user.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/*
 * @描述：检测类中如果有Null值的赋默认初始值
 * @作者：Tangsm
 * @创建时间：2018-07-12 11:31:39
 */
public class CommonUtils {
    /**
     * 检测类中如果有Null值的赋默认初始值
     * @param obj 需检测的类
     */
    public static void initialValue4Null(Object obj){
        Field[] fields = obj.getClass().getDeclaredFields();
        try{
            for (int i = 0; i < fields.length; i++){
                Field field = fields[i];
                String name = field.getName();  // 获取属性的名字
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = field.getGenericType().toString();    // 获取属性的类型
                if (type.equals("class java.lang.String")){
                    Method m = obj.getClass().getMethod("get" + name);
                    String value = (String) m.invoke(obj); // 调用getter方法获取属性值
                    if (value == null) {
                        m = obj.getClass().getMethod("set"+name, String.class);
                        m.invoke(obj, "");
                    }
                }else if (type.equals("class java.lang.Long")){
                    Method m = obj.getClass().getMethod("get" + name);
                    Long value = (Long) m.invoke(obj);
                    if (value == null) {
                        m = obj.getClass().getMethod("set"+name, Long.class);
                        m.invoke(obj, 0L);
                    }
                }else if (type.equals("class java.lang.Integer")){
                    Method m = obj.getClass().getMethod("get" + name);
                    Integer value = (Integer) m.invoke(obj);
                    if (value == null) {
                        m = obj.getClass().getMethod("set"+name, Integer.class);
                        m.invoke(obj, 0);
                    }
                }else if (type.equals("class java.lang.Boolean")){
                    Method m = obj.getClass().getMethod("get" + name);
                    Boolean value = (Boolean) m.invoke(obj);
                    if (value == null) {
                        m = obj.getClass().getMethod("set"+name, Boolean.class);
                        m.invoke(obj, false);
                    }
                }else if (type.equals("class java.lang.Float")){
                    Method m = obj.getClass().getMethod("get" + name);
                    Float value = (Float) m.invoke(obj);
                    if (value == null) {
                        m = obj.getClass().getMethod("set"+name, Float.class);
                        m.invoke(obj, 0.0f);
                    }
                }else if (type.equals("class java.lang.Double")){
                    Method m = obj.getClass().getMethod("get" + name);
                    Double value = (Double) m.invoke(obj);
                    if (value == null) {
                        m = obj.getClass().getMethod("set"+name, Double.class);
                        m.invoke(obj, 0.0d);
                    }
                }else if (type.equals("class java.lang.Short")){
                    Method m = obj.getClass().getMethod("get" + name);
                    Short value = (Short) m.invoke(obj);
                    if (value == null) {
                        m = obj.getClass().getMethod("set"+name, Short.class);
                        m.invoke(obj, 0);
                    }
                }else if (type.equals("class java.lang.Byte")){
                    Method m = obj.getClass().getMethod("get" + name);
                    Byte value = (Byte) m.invoke(obj);
                    if (value == null) {
                        m = obj.getClass().getMethod("set"+name, Byte.class);
                        m.invoke(obj, 0);
                    }
                }else if (type.equals("class java.util.Date")){
                    Method m = obj.getClass().getMethod("get" + name);
                    Date value = (Date) m.invoke(obj);
                    if (value == null) {
                        m = obj.getClass().getMethod("set"+name, Date.class);
                        m.invoke(obj, new Date());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 循环对象集合，获取对象中指定属性值添加至List中并返回
     * @param objList 需遍历的对象集合
     * @param propertyName  属性名
     * @return List<V>
     */
    public static <V> List<V> getPropertyValues2List(List<?> objList, String propertyName){
        // 组装指定ID集合
        List<V> ids = new ArrayList<V>();
        if (objList != null && objList.size() > 0){
            for (Object obj : objList){
                if (obj != null){
                    Method[] ms = obj.getClass().getMethods();
                    for (Method m : ms) {
                        if(("get" + propertyName).toLowerCase().equals(m.getName().toLowerCase())){
                            V v = getPropertyVal(obj, propertyName);

                            ids.add(v);
                        }
                    }
                }
            }
        }

        return ids;
    }

    /**
     * 循环对象集合，获取对象中指定属性值作为key，对象作为value，Key为值转为的字符串（只适用KEY不重复的属性）
     * @param objList 需遍历的对象集合
     * @param propertyName  属性名
     * @return V(key:属性值，value:属性对象)
     */
    public static <V> V getPropertyValues2Map(List<?> objList, String propertyName){
        // 组装指定ID集合
        Map<String, V> ids = new HashMap<String, V>();
        if (objList != null && objList.size() > 0){
            for (Object obj : objList){
                if (obj != null){
                    Method[] ms = obj.getClass().getMethods();
                    for (Method m : ms) {
                        if(("get" + propertyName).toLowerCase().equals(m.getName().toLowerCase())){
                            V v = getPropertyVal(obj, propertyName);

                            ids.put(v.toString(), (V)obj);
                        }
                    }
                }
            }
        }

        return (V)ids;
    }

    /**
     * 获取指定属性名称的属性值
     * @param obj   获取对象
     * @param propertyName  属性名称
     * @param <V>
     * @return V
     */
    private static <V> V getPropertyVal(Object obj, String propertyName){
        try {
            Field field = obj.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);//对所有属性设置访问权限  当类中的成员变量为private时 必须设置此项

            return (V)field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据两个对象中相同属性进行匹配，然后将中间表的主键ID作为key，主表对象作为value进行组装并返回
     * @param mainC 主表对象集合
     * @param middleC 关联表对象集合
     * @param mainProName 主表属性名（例：account表的主键ID）
     * @param midProName 中间表属性名（例：accountUser表的accountId）
     * @param <V>
     * @return V
     */
    public static <V> V assembly2Map(List<?> mainC, List<?> middleC, String mainProName, String midProName){
        // 将主键ID作为key，对象作为value
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (mainC != null && mainC.size() > 0
                && middleC != null && middleC.size() > 0){
            Map<V, V> middleM = getPropertyValues2Map(middleC, midProName);
            for (Object obj : mainC){
                if (obj != null){
                    Method[] ms = obj.getClass().getMethods();
                    for (Method m : ms) {
                        if(("get" + mainProName).toLowerCase().equals(m.getName().toLowerCase())){
                            V v = getPropertyVal(obj, mainProName);
                            String vTemp = v.toString();
                            if (middleM.get(vTemp) != null){
                                V middleObj = middleM.get(vTemp);
                                if (middleObj != null){
                                    Method[] midMt = middleObj.getClass().getMethods();
                                    for (Method midM : midMt) {
                                        if(("get" + mainProName).toLowerCase().equals(midM.getName().toLowerCase())){
                                            V midValue = getPropertyVal(middleObj, mainProName);
                                            resultMap.put(midValue.toString(), obj);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return (V)resultMap;
    }
//
//    /**
//     * 根据两个对象中相同属性进行匹配，然后将中间表的主键ID作为key，主表对象作为value进行组装并返回
//     * @param mainC 主表对象集合
//     * @param middleC 关联表对象集合
//     * @param mainProName 主表属性名（例：account表的主键ID）
//     * @param midProName 中间表属性名（例：accountUser表的accountId）
//     * @param <V>
//     * @return V
//     */
//    public static <V> V assembly2Map(List<?> mainC, List<?> middleC, String mainProName, String midProName){
//        // 将主键ID作为key，对象作为value
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        if (mainC != null && mainC.size() > 0
//                && middleC != null && middleC.size() > 0){
//            Map<V, V> middleM = getPropertyValues2Map(middleC, midProName);
//            for (Object obj : mainC){
//                if (obj != null){
//                    Method[] ms = obj.getClass().getMethods();
//                    for (Method m : ms) {
//                        if(("get" + mainProName).toLowerCase().equals(m.getName().toLowerCase())){
//                            V v = getPropertyVal(obj, mainProName);
//                            String vTemp = v.toString();
//                            if (middleM.get(vTemp) != null && vTemp.equals(middleM.get(vTemp).toString())){
//                                resultMap.put(vTemp, obj);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        return (V)resultMap;
//    }

//    public static void main(String[] args) {
//        User u = new User();
//        u.setId(1L);
//        u.setName("a");
//
//        User u1 = new User();
//        u1.setId(2L);
//        u1.setName("b");
//
//        List<User> us = new ArrayList<>();
//        us.add(u);
//        us.add(u1);
//
//        //
//        UserAccount a = new UserAccount();
//        a.setUserId(1L);
//
//        UserAccount a1 = new UserAccount();
//        a1.setUserId(2L);
//
//        List<UserAccount> ua = new ArrayList<>();
//        ua.add(a);
//        ua.add(a1);
//
//        Map<Long, User> id = assembly2Map(us, ua, "id", "userId");
//        User test = id.get("1");
//
//        System.out.println(test.getName());
//    }
}
