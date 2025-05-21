package com.coco.inspirelink.unknowtest;

/**
 * @Author:     MOHE
 * @Description:  TODO
 * @Date:    2025/5/20 上午10:26
 * @Version:    1.0
 */

public class itest {
    /**
     5      * divider:除数
     6      * result:结果
     7      * try-catch捕获while循环
     8      * 每次循环，divider减一，result=result+100/divider
     9      * 如果：捕获异常，打印输出“异常抛出了”，返回-1
     10      * 否则：返回result
     11      * @return
     12      */
     public int test1(){
        int divider=10;
        int result=100;
       try{
            while(divider>-1){
               divider--;
                result=result+100/divider;
            }
             return result;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("异常抛出了！！");
             return -1;
      }
     }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        itest t1=new itest();
        System.out.println("test1方法执行完毕！result的值为："+t1.test1());
    }
}
