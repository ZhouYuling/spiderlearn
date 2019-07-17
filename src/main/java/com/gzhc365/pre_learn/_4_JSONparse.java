package com.gzhc365.pre_learn;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gzhc365.vo.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 12:15 2019/7/17 0017
 * @Modified by:
 */
public class _4_JSONparse {

    @Test
    public void beanToJSON(){/** 将对象序例化成JSON **/
        Student json = new Student(19, "李明");
        System.out.println(JSON.toJSON(json));//{"name":"李明","age":19}
    }

    @Test
    public void listToJSON(){/** 将集合序例化成JSON **/
        Student json = new Student(19, "李明");
        List<Student> list = new ArrayList<Student>();
        list.add(json);
        list.add(new Student(12, "张三"));
        System.out.println(JSON.toJSON(list));//[{"name":"李明","age":19},{"name":"张三","age":12}]
    }

    @Test
    public void JSONtoBean(){/** Json串反序列化成对象 **/
        Student person = JSON.parseObject("{\"name\":\"李明\",\"age\":19}", Student.class);
        System.out.printf("name:%s,age:%d\n",person.getName(),person.getAge());//name:李明,age:19
    }

    @Test
    public void JSONArrayToListBean(){/** 数组对象反序列化成集合 **/
        String str = "[{\"name\":\"李明\",\"age\":19},{\"name\":\"张三\",\"age\":12}]";
        List<Student> listPerson = JSON.parseArray(str, Student.class);
        for (Student item : listPerson) {
            System.out.println( item.getName() );//李明 张三
            System.out.println( item.getAge());  //19    12
        }
    }

    @Test
    public void StringToJSONByNative(){/** 手撕JSON对象 **/
        JSONObject jobj = JSON.parseObject("{\"name\":\"李明\",\"age\":19}");
        System.out.printf("name:%s,age:%d\n",jobj.getString("name"),jobj.getBigInteger("age"));//name:李明,age:19
        //字符串转换为JSONObject以后IDE里面，实例.编辑器会提示很多信息
    }

    @Test
    public void StringToJSONArrayByNative(){/** 手撕JSON数组 **/
        JSONArray jarr = JSON.parseArray("[{'name':'李明','age':18},{'name':'小四','age':21}]");
//        JSONArray jarr = JSON.parseArray("[{\"name\":\"李明\",\"age\":19},{\"name\":\"张三\",\"age\":12}]");//转义字符  效果是一致的
        System.out.println(jarr);//[{"name":"李明","age":18},{"name":"小四","age":21}]
        for (int i = 0, len = jarr.size(); i < len; i++) {
            JSONObject temp = jarr.getJSONObject(i);
            System.out.println(temp.getString("name") + "," + temp.getBigInteger("age"));//name:李明,age:19    name:张三,age:12
            System.out.printf("name:%s,age:%d\n", temp.getString("name"), temp.getBigInteger("age"));//name:李明,age:18   name:小四,age:21
        }
    }

    @Test
    public void JSONtoString(){/** 对JSON数组直接toString输出 **/
        JSONArray jarr = JSON.parseArray("[{'name':'李明','age':18},{'name':'小四','age':21}]");
        for (Object obj : jarr) {
            System.out.println(obj.toString()); /*{"name":"李明","age":19}    {"name":"张三","age":12}*/
        }
    }

    //TODO:补充一个医院数据解析的例子
    //见com.gzhc365.spider.getJsonInfo 实际情况解析科室和医生信息

}


