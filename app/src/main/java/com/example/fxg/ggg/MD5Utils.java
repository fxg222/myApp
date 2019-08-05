package com.example.fxg.ggg;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Utils {

    public static String toMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    public void write() {
        String filePath = "/storage/emulated/0/Android/";
        String fileName = "data.txt";
        String filePath1 = "/storage/emulated/0/Android/";
        String fileName1 = "pass.txt";
        writeTxtToFile("", filePath, fileName);//建立储存登录信息的文件
        writeTxtToFile("", filePath1, fileName1);//建立储存登录信息的文件
    }

    public void writeData(String id) {
        String filePath = "/storage/emulated/0/Android/";
        String fileName = "data.txt";
        writeTxtToFile(id, filePath, fileName);//将登录的ID写入文件
    }

    public void writePass(String pass) {
        String filePath = "/storage/emulated/0/Android/";
        String fileName = "pass.txt";
        writeTxtToFile(pass, filePath, fileName);//将登录的ID写入文件
    }

    private void writeTxtToFile(String strcontent, String filePath, String fileName) {
        makeFilePath(filePath, fileName);
        String strFilePath = filePath + fileName;
        String strContent = strcontent;
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }//执行写入操作的函数

    private File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }//生成文件夹

    private static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }

    public static String read_id() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            File Profile = new File("/storage/emulated/0/Android/data.txt");
            if (!Profile.exists()) {
                return null;
            }
            InputStream ins = new FileInputStream(Profile);
            BufferedReader bf = new BufferedReader(new InputStreamReader(ins));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }//读取文件内容

    public static String read_pass() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            File Profile = new File("/storage/emulated/0/Android/pass.txt");
            if (!Profile.exists()) {
                return null;
            }
            InputStream ins = new FileInputStream(Profile);
            BufferedReader bf = new BufferedReader(new InputStreamReader(ins));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }//读取文件内容

    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    public static boolean deleteDirectory(String dir) {
        return false;
    }
}
