package top.lisicheng;

import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MathTest {

    @Test
    void doubleMath() {

        // int 转 double
        assertEquals(DoubleMath.factorial(1), 1.0);

        try {
            IntMath.checkedAdd(Integer.MAX_VALUE, Integer.MIN_VALUE);
        }catch (ArithmeticException e){
            // do nothing
        }


    }

}
