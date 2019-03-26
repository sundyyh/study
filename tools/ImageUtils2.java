package com.sundy.test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//图片旋转工具类
public class ImageUtils2 {  
      
    //顺时针旋转90度（通过交换图像的整数像素RGB 值）  
    public static BufferedImage rotateClockwise90(BufferedImage bi) {
        int width = bi.getWidth();  
        int height = bi.getHeight();  
        BufferedImage bufferedImage = new BufferedImage(height, width, bi.getType());  
        for (int i = 0; i < width; i++)  
            for (int j = 0; j < height; j++)  
                bufferedImage.setRGB(height - 1 - j, width - 1 - i, bi.getRGB(i, j));  
        return bufferedImage;  
    }  
  
    //逆时针旋转90度（通过交换图像的整数像素RGB 值）  
    public static BufferedImage rotateCounterclockwise90(BufferedImage bi) {  
        int width = bi.getWidth();  
        int height = bi.getHeight();  
        BufferedImage bufferedImage = new BufferedImage(height, width, bi.getType());  
        for (int i = 0; i < width; i++)  
            for (int j = 0; j < height; j++)  
                bufferedImage.setRGB(j, i, bi.getRGB(i, j));  
        return bufferedImage;  
    }  
      
    //旋转180度（通过交换图像的整数像素RGB 值）  
        public static BufferedImage rotate180(BufferedImage bi) {  
            int width = bi.getWidth();  
            int height = bi.getHeight();  
            BufferedImage bufferedImage = new BufferedImage(width,height,bi.getType());  
            for (int i = 0; i < width; i++)  
                for (int j = 0; j < height; j++)  
                    bufferedImage.setRGB( width - i-1,height-j-1, bi.getRGB(i, j));  
            return bufferedImage;  
        }  
          
    //水平翻转  
        public static BufferedImage rotateHorizon(BufferedImage bi) {  
            int width = bi.getWidth();  
            int height = bi.getHeight();  
            BufferedImage bufferedImage = new BufferedImage(width,height,bi.getType());  
            for (int i = 0; i < width; i++)  
                for (int j = 0; j < height; j++)  
                    bufferedImage.setRGB( width - i-1,j, bi.getRGB(i, j));  
            return bufferedImage;  
        }  
    //垂直翻转  
        public static BufferedImage rotateVertical(BufferedImage bi) {  
            int width = bi.getWidth();  
            int height = bi.getHeight();  
            BufferedImage bufferedImage = new BufferedImage(width,height,bi.getType());  
            for (int i = 0; i < width; i++)  
                for (int j = 0; j < height; j++)  
                    bufferedImage.setRGB(i,height-1-j, bi.getRGB(i, j));  
            return bufferedImage;  
        }  
          
        //main方法,用于测试  
    public static void main(String[] args) throws IOException {
         // 测试来源图片    
        String pathname = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "2015-12-29 100431.jpg";
        File file = new File(pathname);    
        // 测试生成图片    
        String testPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "test.jpg";    
        File outFile = new File(testPath);    
          
        //顺时针旋转90度测试  
        BufferedImage image = ImageIO.read(file);
        image=rotateClockwise90(image);  
        //ImageIO.write(image, "png", outFile);    
         
        //逆时针旋转90度测试  
        BufferedImage image2 = ImageIO.read(file);    
        image2=rotateCounterclockwise90(image2);  
        //ImageIO.write(image2, "png", outFile);   
          
        //旋转180度测试  
        BufferedImage image3 = ImageIO.read(file);    
        image3=rotate180(image3);  
        //ImageIO.write(image3, "png", outFile);    
         
        //水平旋转测试  
        BufferedImage image4 = ImageIO.read(file);    
        image4=rotateHorizon(image4);  
        //ImageIO.write(image4, "png", outFile);  
          
        //垂直旋转测试  
        BufferedImage image5 = ImageIO.read(file);    
        image5=rotateVertical(image5);  
        ImageIO.write(image5, "png", outFile);  
    }  
} 