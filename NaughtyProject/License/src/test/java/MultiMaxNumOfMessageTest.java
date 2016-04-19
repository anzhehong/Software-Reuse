import static org.junit.Assert.*;

public class MultiMaxNumOfMessageTest {

    private MultiMaxNumOfMessage multiMaxNumOfMessage;

    @org.junit.Before
    public void setUp() throws Exception {
        multiMaxNumOfMessage = new MultiMaxNumOfMessage(10);
    }


    @org.junit.Test
    public void testCheckByKey() throws Exception {
        multiMaxNumOfMessage.addMap("first");
        multiMaxNumOfMessage.addMap("second");
        boolean first;
        boolean second;
        boolean third;
        for(int i= 0 ; i< multiMaxNumOfMessage.getMaxNumOfMessage() + 5;i++) {
            first = multiMaxNumOfMessage.CheckByKey("first");
            second = multiMaxNumOfMessage.CheckByKey("second");
            third = multiMaxNumOfMessage.CheckByKey("third");
            System.out.println(i + " first: \t"+ first);
            System.out.println(i + " second:\t"+ second);
            System.out.println(i + " third: \t"+ third);
            if(i == 10){
                assertEquals(false,first);
                assertEquals(false,second);
                assertEquals(false,third);
            }
            else{
                assertEquals(true,first);
                assertEquals(true,second);
                assertEquals(false,third);

            }

        }
    }

    @org.junit.Test
    public void testCheck() throws Exception {

    }
}