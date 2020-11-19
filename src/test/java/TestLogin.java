import cn.hutool.core.io.FileUtil;
import cn.hutool.setting.GroupedMap;
import cn.hutool.setting.Setting;
import net.oschina.xxl.job.util.HttpKit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @Classname TestLogin
 * @Description
 * @Date 2020/11/17 15:07
 * @Author W.Z
 */
public class TestLogin {

    @Test
    public void testLogin(){

        final String token = HttpKit.init("xxl-job.setting");
        System.out.println(token);
        Assertions.assertNotNull(token);
    }
    @Test
    public void testLogin2(){

        final String token = HttpKit.init("xxl-job.setting");
        System.out.println(token);
        Assertions.assertNotNull(token);
    }
    @Test
    public void readSetting(){

        Setting setting=new Setting("xxl-job.setting");
        System.out.println(setting);
        final String url = setting.getStr("xxl-job.url");
        System.out.println(url);
        Assertions.assertNotNull(url);
    }
}
