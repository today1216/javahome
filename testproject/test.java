package testproject;

import java.util.*;

class BMICalculator {
    public static double calculateBMI(double weight, double tall) {
        
        double a = Math.pow(tall,2);
        double bmi = weight/a;
        return bmi;
    }
    public static void printBMIClassification(double bmi) {
        // 해당 메소드를 구현하세요.
        String re;
        if(bmi<18.5)
        {
            re = "저체중";
        }
        else if(bmi<23.0)
        {
            re = "정상";
        }
        else if(bmi<25.0)
        {
            re = "과체중";
        }
        else
        {
            re = "비만";
        }
        System.out.printf("BMI수치 : %.2f , %s 입니다",bmi,re);
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double weight = input.nextDouble();
        double tall = input.nextDouble();
        // BMI 지수 계산
        double bmi = calculateBMI(weight, tall);
        // BMI 지수를 입력하여 비만도 결과 출력
        printBMIClassification(bmi);
    }
}