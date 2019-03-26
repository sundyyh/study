package com.sundy.test;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PicUtil {

    /**
     * 获取图片正确显示需要旋转的角度(顺时针)
     *
     * @return
     */
    public static int getRotateAngleForPhoto(String filePath) {

        File file = new File(filePath);

        int angle = 0;

        Metadata metadata;
        try {
            metadata = JpegMetadataReader.readMetadata(file);
            for (Directory directory : metadata.getDirectories()) {
                if (directory.containsTag(ExifSubIFDDirectory.TAG_ORIENTATION)) {

                    // Exif信息中方向
                    int orientation = directory.getInt(ExifSubIFDDirectory.TAG_ORIENTATION);

                    // 原图片的方向信息
                    if (6 == orientation) {
                        //6旋转90
                        angle = 90;
                    } else if (3 == orientation) {
                        //3旋转180
                        angle = 180;
                    } else if (8 == orientation) {
                        //8旋转90
                        angle = 270;
                    }
                }
            }

        } catch (JpegProcessingException e) {
            e.printStackTrace();
        } catch (MetadataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return angle;
    }

    /**
     * 旋转手机照片
     *
     * @return
     */
    public static String rotatePhonePhoto(String fullPath, int angel) {

        if (angel == 0) {
            return fullPath;
        }

        BufferedImage src;
        try {
            src = ImageIO.read(new File(fullPath));

            int src_width = src.getWidth(null);
            int src_height = src.getHeight(null);

            Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);

            BufferedImage res = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = res.createGraphics();

            g2.translate((rect_des.width - src_width) / 2,
                    (rect_des.height - src_height) / 2);
            g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

            g2.drawImage(src, null, null);

            ImageIO.write(res, "jpg", new File(fullPath));

        } catch (IOException e) {

            e.printStackTrace();
        }

        return fullPath;

    }

    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        // if angel is greater than 90 degree, we need to do some conversion  
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new java.awt.Rectangle(new Dimension(des_width, des_height));
    }


}