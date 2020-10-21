package com.ab.week01.homework;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

public class HelloClassloader extends ClassLoader{

    public static void main(String[] args) {
        String filePath = "C:\\Users\\root\\Desktop\\Hello\\Hello.xlass";
        try {
            Object o = new HelloClassloader(filePath).findClass("Hello").newInstance();
            Method helloMethod = o.getClass().getDeclaredMethod("hello");
            helloMethod.invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String filePath;
    HelloClassloader(String filePath){
        this.filePath = filePath;
    }

    @Override
    protected Class<?> findClass(String name){
        byte[] byteArr = getByteArr();
        byte[] newByteArr = new byte[byteArr.length];
        for (int i = 0;i<byteArr.length;i++) {
            newByteArr[i] = (byte) (255-byteArr[i]);
        }
        return defineClass(name, newByteArr,0 , newByteArr.length);
    }

    private byte[] getByteArr() {
        File file = new File(filePath);
        byte[] buffer = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n =fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return buffer;
    }
}