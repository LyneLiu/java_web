import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nn_liu on 2017/4/19.
 */
public class ExceptionTest {

    public static final Logger logger = LoggerFactory.getLogger(ExceptionTest.class);

    @Test
    public void testException(){
        try {
            int result = 1/0;
        }catch (Exception e){
            System.out.println("=============================");
            System.out.println(String.format("exception error: %s %s",e.getCause(),e.getStackTrace()[1]));
            System.out.println("=============================");
            System.out.println(String.format("exception info:%s",e.getCause()));
            System.out.println("=============================");
            System.out.println(String.format("exception info:%s",e.getMessage()));
            System.out.println("=============================");
            System.out.println(String.format("exception info:%s",e.getLocalizedMessage()));
            System.out.println("=============================");
            logger.error("exception info:",e);
        }
    }

}
