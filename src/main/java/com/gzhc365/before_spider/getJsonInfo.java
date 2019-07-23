package com.gzhc365.before_spider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 18:12 2019/7/10 0010
 * @Modified by:
 */
public class getJsonInfo {

    public static final String URL = "http://www.ntzyy.com";
    public static final int his_id = 2063;

    public static void main(String[] args) {

        /**从json中获取科室信息URL**/
        String json1 = "[{\"id\":\"19\",\"name\":\"内科\",\"href\":\"/intro/19.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527126577538},{\"id\":\"20\",\"name\":\"消化内科\",\"href\":\"/intro/20.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527126635742},{\"id\":\"21\",\"name\":\"肾病内分泌科\",\"href\":\"/intro/21.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527126743257},{\"id\":\"22\",\"name\":\"呼吸科\",\"href\":\"/intro/22.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527126819636},{\"id\":\"23\",\"name\":\"心血管内科\",\"href\":\"/intro/23.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527126853407},{\"id\":\"24\",\"name\":\"肝胆病科\",\"href\":\"/intro/24.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527126874357},{\"id\":\"25\",\"name\":\"肿瘤内科\",\"href\":\"/intro/25.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527126896876},{\"id\":\"26\",\"name\":\"老年病科\",\"href\":\"/intro/26.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527126918311},{\"id\":\"27\",\"name\":\"风湿病科\",\"href\":\"/intro/27.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527126942524},{\"id\":\"28\",\"name\":\"神经内科\",\"href\":\"/intro/28.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527126966966},{\"id\":\"29\",\"name\":\"大外科概况\",\"href\":\"/intro/29.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527126989093},{\"id\":\"30\",\"name\":\"普外科\",\"href\":\"/intro/30.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127017526},{\"id\":\"31\",\"name\":\"创面治疗中心\",\"href\":\"/intro/31.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127036438},{\"id\":\"32\",\"name\":\"胸心外科\",\"href\":\"/intro/32.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127082981},{\"id\":\"33\",\"name\":\"其他科室介绍\",\"href\":\"/intro/33.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127171116},{\"id\":\"34\",\"name\":\"妇科\",\"href\":\"/intro/34.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127204383},{\"id\":\"35\",\"name\":\"儿科\",\"href\":\"/intro/35.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127240214},{\"id\":\"36\",\"name\":\"急诊科\",\"href\":\"/intro/36.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127263385},{\"id\":\"37\",\"name\":\"骨伤科\",\"href\":\"/intro/37.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127288693},{\"id\":\"38\",\"name\":\"肛肠科\",\"href\":\"/intro/38.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127494808},{\"id\":\"39\",\"name\":\"皮肤科\",\"href\":\"/intro/39.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127517205},{\"id\":\"40\",\"name\":\"眼科\",\"href\":\"/intro/40.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127536099},{\"id\":\"41\",\"name\":\"耳鼻咽喉科\",\"href\":\"/intro/41.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127556943},{\"id\":\"42\",\"name\":\"口腔科\",\"href\":\"/intro/42.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127576298},{\"id\":\"43\",\"name\":\"针灸科\",\"href\":\"/intro/43.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127595509},{\"id\":\"44\",\"name\":\"推拿科\",\"href\":\"/intro/44.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127619993},{\"id\":\"45\",\"name\":\"治未病科\",\"href\":\"/intro/45.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127637835},{\"id\":\"46\",\"name\":\"超声科\",\"href\":\"/intro/46.html\",\"open\":false,\"target\":\"_self\",\"type\":\"content\",\"children\":[],\"num\":0,\"image\":\"\",\"displayOrder\":1527127659396}]";

        System.out.println(json1);
        JSONArray objects2 = JSON.parseArray(json1);

        for (Object object : objects2) {
            String href = URL + ((JSONObject) object).getString("href");
            String dept_name = ((JSONObject) object).getString("name");
            System.out.println(dept_name + "   " + href);
        }

        /**从json中获取医生url**/
        String json = "[{\"id\":5,\"pId\":14,\"name\":\"大内科医生概况\",\"linkUrl\":\"/product/5/\",\"targetType\":false,\"leaf\":false,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":true,\"isParent\":true,\"children\":[{\"id\":6,\"pId\":5,\"name\":\"脾胃病科\",\"linkUrl\":\"/product/6/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"16\",\"content\":false},{\"id\":7,\"pId\":5,\"name\":\"肾病内分泌科\",\"linkUrl\":\"/product/7/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"9\",\"content\":false},{\"id\":25,\"pId\":5,\"name\":\"呼吸内科\",\"linkUrl\":\"/product/25/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"5\",\"content\":false},{\"id\":8,\"pId\":5,\"name\":\"心血管病科\",\"linkUrl\":\"/product/8/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"6\",\"content\":false},{\"id\":26,\"pId\":5,\"name\":\"肝胆病科\",\"linkUrl\":\"/product/26/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"7\",\"content\":false},{\"id\":27,\"pId\":5,\"name\":\"肿瘤内科\",\"linkUrl\":\"/product/27/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"4\",\"content\":false},{\"id\":28,\"pId\":5,\"name\":\"老年病科\",\"linkUrl\":\"/product/28/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"2\",\"content\":false},{\"id\":29,\"pId\":5,\"name\":\"风湿病科\",\"linkUrl\":\"/product/29/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"2\",\"content\":false},{\"id\":30,\"pId\":5,\"name\":\"神经内科\",\"linkUrl\":\"/product/30/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"1\",\"content\":false}],\"num\":\"52\",\"content\":false},{\"id\":23,\"pId\":14,\"name\":\"大外科医生概况\",\"linkUrl\":\"/product/23/\",\"targetType\":false,\"leaf\":false,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":true,\"children\":[{\"id\":31,\"pId\":23,\"name\":\"普外科\",\"linkUrl\":\"/product/31/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"6\",\"content\":false},{\"id\":32,\"pId\":23,\"name\":\"中医外科\",\"linkUrl\":\"/product/32/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"4\",\"content\":false},{\"id\":33,\"pId\":23,\"name\":\"泌尿外科\",\"linkUrl\":\"/product/33/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"2\",\"content\":false},{\"id\":34,\"pId\":23,\"name\":\"神经外科\",\"linkUrl\":\"/product/34/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"1\",\"content\":false},{\"id\":35,\"pId\":23,\"name\":\"胸心外科\",\"linkUrl\":\"/product/35/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"2\",\"content\":false}],\"num\":\"15\",\"content\":false},{\"id\":24,\"pId\":14,\"name\":\"其他科室医生介绍\",\"linkUrl\":\"/product/24/\",\"targetType\":false,\"leaf\":false,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":true,\"children\":[{\"id\":36,\"pId\":24,\"name\":\"妇科\",\"linkUrl\":\"/product/36/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"14\",\"content\":false},{\"id\":37,\"pId\":24,\"name\":\"儿科\",\"linkUrl\":\"/product/37/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"7\",\"content\":false},{\"id\":38,\"pId\":24,\"name\":\"急诊科\",\"linkUrl\":\"/product/38/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"14\",\"content\":false},{\"id\":39,\"pId\":24,\"name\":\"骨伤科\",\"linkUrl\":\"/product/39/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"6\",\"content\":false},{\"id\":40,\"pId\":24,\"name\":\"肛肠科\",\"linkUrl\":\"/product/40/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"5\",\"content\":false},{\"id\":41,\"pId\":24,\"name\":\"皮肤科\",\"linkUrl\":\"/product/41/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"6\",\"content\":false},{\"id\":42,\"pId\":24,\"name\":\"眼科\",\"linkUrl\":\"/product/42/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"6\",\"content\":false},{\"id\":43,\"pId\":24,\"name\":\"耳鼻咽喉科\",\"linkUrl\":\"/product/43/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"9\",\"content\":false},{\"id\":44,\"pId\":24,\"name\":\"口腔科\",\"linkUrl\":\"/product/44/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"8\",\"content\":false},{\"id\":45,\"pId\":24,\"name\":\"针灸科\",\"linkUrl\":\"/product/45/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"10\",\"content\":false}],\"num\":\"85\",\"content\":false},{\"id\":74,\"pId\":14,\"name\":\"如东县中医院\",\"linkUrl\":\"/product/74/\",\"targetType\":false,\"leaf\":true,\"img\":\"\",\"open\":true,\"listPageid\":\"\",\"selected\":false,\"isParent\":false,\"children\":[],\"num\":\"0\",\"content\":false}]";

        System.out.println(json);

        JSONArray objects = JSON.parseArray(json);
        for (Object object : objects) {
            String name = ((JSONObject) object).getString("name");
            JSONArray children = ((JSONObject) object).getJSONArray("children");
            for (Object child : children) {
                String linkUrl = URL + ((JSONObject) child).getString("linkUrl");
                String dept_name = ((JSONObject) child).getString("name");
                System.out.println(name + "  " + dept_name + "   " + linkUrl);
            }
        }

        /**有一些信息，比如医生信息也会在json数组中**/
        //识别json格式、有些是乱码、不过可以大概看出来
    }


}

