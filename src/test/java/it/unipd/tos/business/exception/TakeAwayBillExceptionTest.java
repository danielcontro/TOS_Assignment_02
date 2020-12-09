////////////////////////////////////////////////////////////////////
// Daniel Eduardo Contro 1187597
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business.exception;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TakeAwayBillExceptionTest {

    @Test
    public void test_ToString_ValidMessage() {
        TakeAwayBillException exception = new TakeAwayBillException("Test message");
        assertEquals("Test message", exception.getMessage());
    }
    
    @Test
    public void test_ToString_NullMessage() {
        TakeAwayBillException exception = new TakeAwayBillException(null);
        assertEquals(null, exception.getMessage());
    }
}
