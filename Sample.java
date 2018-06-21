import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ZC on 2018/4/3.
 */
public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "11044327";
    public static final String API_KEY = "rFaSa4O5tdGLOx7B5i7neNAl";
    public static final String SECRET_KEY = "pNAuIZAmzkTj4gdNGuhh6BcHjiEjl15P ";

    public static void main(String[] args) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);


        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        String path = "C:\\Users\\lenovo\\Desktop\\test.png";
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));

    }
}