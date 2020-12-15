import com.github.jar77.job.api.JobInfoApi;
import com.github.jar77.job.api.entity.ReturnT;
import com.github.jar77.job.util.HttpKit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @Classname JobTest
 * @Description
 * @Date 2020/11/17 18:49
 * @Author W.Z
 */
public class JobTest {

    @Test
    public void testInit() throws IllegalArgumentException{
        JobInfoApi.triggerJob(3L, null, null);
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            JobInfoApi.triggerJob(3L, null, null);
        });
    }
    @Test
    public void testTrigger() throws IllegalArgumentException{

        final String init = HttpKit.init("xxl-job.setting");
        System.out.println(init);
        final ReturnT result = JobInfoApi.triggerJob(3L, null, null);
        System.out.println(result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(200,result.getCode());
    }
    @Test
    public void testTriggerTime() throws IllegalArgumentException{

        final ReturnT<List<String>> triggerTime = JobInfoApi.nextTriggerTime("40 30 3 1,11,21 * ?");
        Assertions.assertNotNull(triggerTime);
        final List<String> content = triggerTime.getContent();
        content.forEach(System.out::println);
    }
}
