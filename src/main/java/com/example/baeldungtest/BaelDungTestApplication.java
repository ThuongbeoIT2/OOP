package com.example.baeldungtest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BaelDungTestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BaelDungTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("FIX CODE ĐI SẾP ƠI");
        int count=0;
        for(float y=2.5f;y>-2.0f;y-=0.12f){
            for (float x=-2.3f;x<2.3f;x+=0.041f){
                float a = x*x + y*y - 4f;
                if(a*a*a-x*x*y*y*y<-0.0f){
                    String str =" Thao My";
                    int nua = count%str.length();
                    System.out.print(str.charAt(nua));
                    count++;
                }else {
                    System.out.print(" ");
                }
            }
            System.out.println();
            Thread.sleep(100);
        }

    }
}
