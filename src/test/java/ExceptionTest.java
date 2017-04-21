import org.junit.Test;

/**
 * Created by nn_liu on 2017/4/19.
 */
public class ExceptionTest {

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
            e.printStackTrace();
        }
    }

}
