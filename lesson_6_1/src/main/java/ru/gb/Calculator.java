package ru.gb;
import java.util.Arrays;

public class Calculator {

    public int[] test1(int[] mas) {

        for (int i = mas.length-1; i >=0; i--) {
            if  (mas[i] == 4 ) {

                return Arrays.copyOfRange(mas, i+1,mas.length);
            }
        }
        throw  new RuntimeException("Входной массив не содержит заданный элемент");
    }


    public boolean test2(int[] mas) {
        boolean flagInput1 = false;
        boolean flagInput4= false;

        for (int i = 0; i < mas.length; i++) {
            if(mas[i] != 1 && mas[i] != 4  ){
                flagInput1 = false;
                flagInput4= false;
                break;
            }
            if  (mas[i] == 1 ) {
                flagInput1 = true;
            }
            if  ( mas[i] == 4) {
                flagInput4= true;
            }

        }
        return flagInput1 && flagInput4;
    }
}
