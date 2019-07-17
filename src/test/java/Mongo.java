import com.gzhc365.pre_learn._10_Mongo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: ZhouYuling
 * @Description:
 * @Date: Created in 下午 18:15 2019/7/17 0017
 * @Modified by:
 */
public class Mongo {

    public static void main(String[] args) {

        int size = 5;
        ExecutorService pool = Executors.newFixedThreadPool(size);
        for (int i = 0; i < size; i++) {
            pool.submit(new Thread(new Runnable() {
                public void run() {
                    while (true) {
//                        _10_Mongo2 instans = _10_Mongo2.getInstanstance();
//                        instans.insertDocument();

                        _10_Mongo.insertDocument();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }));
        }

    }

}
