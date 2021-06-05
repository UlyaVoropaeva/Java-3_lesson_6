package calcTest;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.gb.Calculator;


public class ArrayTestCalc {

    Calculator calc;

    public ArrayTestCalc(){
        this.calc = new Calculator();
    }


    @Test
    public void test1MethodReturnArray1(){
        int [] input = {1,2,44,2,3,4,1,7};
        int [] output = {1,7};
        Assertions.assertArrayEquals(output,calc.test1(input));
    }
    @Test
    public void test1MethodReturnArray2(){
        int [] input = {1,2,44,2,3,4};
        int [] output = {};
        Assertions.assertArrayEquals(output,calc.test1(input));
    }

    @Test
    public void test1MethodReturnArray3(){
        int [] input = {1,2,44,2,34,1,2};

        Assertions.assertThrows(RuntimeException.class, ()->{calc.test1(input);});
    }

    @Test
    public void test1MethodReturnArray4(){
        int [] input = {1,2,1,7};

        Assertions.assertThrows(RuntimeException.class, ()->{calc.test1(input);});
    }

    @Test
    public void test2MethodTrueFalse1(){
        int [] input = {1,1,1,4,4,1,4,4};

        Assertions.assertTrue(calc.test2(input));
    }
    @Test
    public void test2MethodTrueFalse2(){
        int [] input = {1,1,1,1,1,1};

        Assertions.assertFalse(calc.test2(input));
    }
    @Test
    public void test2MethodTrueFalse3(){
        int [] input = {4,4,4,4,4,4,4};

        Assertions.assertFalse(calc.test2(input));
    }
    @Test
    public void test2MethodTrueFalse4(){
        int [] input = {1,1,2,1,4,4,1,4,4,3};

        Assertions.assertFalse(calc.test2(input));
    }

}
