package io.jopen.core.common.util;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author maxuefeng
 */
public class BigDecimalTest {

    @Test
    public void testSimpleAPI(){
        /*BigDecimal var1 = BigDecimal.valueOf(10.9D);
        BigDecimal var2 = BigDecimal.valueOf(10.1D);


        System.err.println(-var1.subtract(var2).doubleValue());

        System.err.println();*/

         /*System.err.println(BigDecimal.valueOf(0.4D).compareTo(BigDecimal.valueOf(0.4D)));

        //    1  用户余额
        Optional.of(BigDecimal.valueOf(0.3D).compareTo(BigDecimal.valueOf(0.3D))).filter(v -> v > 0.3).orElseThrow(() ->new RuntimeException("kkkkkk"));*/

        System.err.println(BigDecimal.valueOf(0.2).compareTo(BigDecimal.valueOf(0.3)) < 0);
    }
}
