package com.wntime.service.common.util;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RequestUtil {

    public static <T> void paramToObject(HttpServletRequest request, T t) {

        Map<String, String> paraMap = getParameterMap(request);

        Method[] methods = t.getClass().getMethods();

        String methodName;
        for (Method method : methods) {
            methodName = method.getName();
            if (methodName.startsWith("set")) {
                //获取set方法并去掉set将首字母小写
                String paraKey = firstLowercase(methodName.substring(3));
                if (paraMap.containsKey(paraKey)) {
                    String paraValueString = paraMap.get(paraKey);
                    Object paraValueObject = null;
                    Class<?> type = method.getParameterTypes()[0];
                    //将请求的参数转变成set方法的参数类型
                    if (type == String.class) {
                        if (paraValueString != null && paraValueString.length() > 0) {
                            if ("null".equals(paraValueString.toLowerCase())) {
                                paraValueObject = null;
                            } else {
                                paraValueObject = paraValueString;
                            }
                        } else {
                            paraValueObject = null;
                        }
                    } else if (type == Timestamp.class) {
                        if (paraValueString != null && paraValueString.length() > 0) {
                            if ("null".equals(paraValueString.toLowerCase())) {
                                paraValueObject = null;
                            } else {
                                paraValueObject = Timestamp.valueOf(paraValueString);
                            }
                        }
                    } else if (type == int.class || type == Integer.class) {
                        if (paraValueString != null && paraValueString.length() > 0) {
                            if ("null".equals(paraValueString.toLowerCase())) {
                                paraValueObject = null;
                            } else {
                                paraValueObject = Integer.valueOf(paraValueString);
                            }
                        }
                    } else if (type == double.class || type == Double.class) {
                        if (paraValueString != null && paraValueString.length() > 0) {
                            if ("null".equals(paraValueString.toLowerCase())) {
                                paraValueObject = null;
                            } else {
                                paraValueObject = Double.valueOf(paraValueString);
                            }
                        }
                    } else if (type == float.class || type == Float.class) {
                        if (paraValueString != null && paraValueString.length() > 0) {
                            if ("null".equals(paraValueString.toLowerCase())) {
                                paraValueObject = null;
                            } else {
                                paraValueObject = Float.valueOf(paraValueString);
                            }
                        }
                    }

                    try {
                        method.invoke(t, paraValueObject); //
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        System.out.println("error:" + method.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        Set<String> keySet = map.keySet();
        Iterator<String> iterator = keySet.iterator();
        Map<String, String> paraMap = new HashMap<>();
        while (iterator.hasNext()) {
            String keyTemp = iterator.next();
            if (map.get(keyTemp).length == 1) {
                paraMap.put(keyTemp, map.get(keyTemp)[0]);
            }
        }
        return paraMap;
    }

    private static String firstLowercase(String s) {
        char[] chars = s.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    public static void main(String[] args) {
        Timestamp a = Timestamp.valueOf("2019-09-01 12:00:00");
        System.out.println(a);


    }

}
