package com.gzhc365.before_spider;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 18:48 2019/7/10 0010
 * @Modified by:
 */
public class getDeptInfoById {

    private static final String HOSPITAL_URL = "http://www.hldszxyy.com/";

    /**
     * http://www.hldszxyy.com/index.php?_d=doctor&_f=detail&id=184
     http://www.hldszxyy.com/index.php?_d=department&_f=detail&id=129
     http://www.hldszxyy.com/index.php?_d=department&_f=content&id=129
     *
     */

    //TODO：将这家医院的代码拷贝过来
    public void getDocInfo(){

        //1.从http://www.hldszxyy.com/index.php?_d=doctor&_f=index获取医生id

        //2."http://www.hldszxyy.com/index.php?_d=doctor&_f=detail&id=" + id 获取医生详细信息

    }

    public void getDeptInfo(){

        //1.从http://www.hldszxyy.com/index.php?_d=department&_f=index 获取科室id

        //2."http://www.hldszxyy.com/index.php?_d=department&_f=detail&id=" + id 获取科室信息

        //3."http://www.hldszxyy.com/index.php?_d=department&_f=content&id=" + id 获取科室详细介绍

    }


}
