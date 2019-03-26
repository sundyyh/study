package com.sundy.test;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageRename {
    static SimpleDateFormat dest = new SimpleDateFormat("yyyy-MM-dd HHmmss");
    static SimpleDateFormat src = new SimpleDateFormat("yyyyMMddHHmmss");//yyyy:MM:dd HH:mm:ss

    public static void main(String[] args) throws Exception {
//        handlerPhone(new File(""), false);
        handlerWeixin(new File(""), false);

    }

    private void rename() {

    }

    private static void handlerDigitalCamera(File file) {
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File jpegFile : files) {
                Metadata metadata;
                try {
                    metadata = ImageMetadataReader.readMetadata(jpegFile);
                    for (Directory directory : metadata.getDirectories()) {
                        if ("ExifSubIFDDirectory".equalsIgnoreCase(directory.getClass().getSimpleName())) {//2015-03-07 222600
                            String createTime = directory.getString(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                            if (createTime != null) {
                                createTime = createTime.replaceAll(createTime.replaceAll("\\d", "").replaceAll(" ", "").substring(0, 1), "").replaceAll(" ", "");
                                Date date = src.parse(createTime);
                                String path = jpegFile.getPath();
                                String dir = path.substring(0, path.lastIndexOf("\\") + 1);
                                String suffix = path.substring(path.lastIndexOf("."));
                                String name = dest.format(date);
                                jpegFile.renameTo(new File(dir + name + suffix));
                            }
                        }
                    }
                } catch (ImageProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void handlerPhone(File file, boolean preview) {
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File jpegFile : files) {
                if (jpegFile.isDirectory())
                    continue;
                if (!(jpegFile.getName().startsWith("IMG") || jpegFile.getName().startsWith("VID"))) {
                    continue;
                }
                String dir = jpegFile.getParent();
                String suffix = jpegFile.getName().substring(jpegFile.getName().lastIndexOf("."));
                String name = jpegFile.getName()
                        .replace("IMG", "")
                        .replace("VID", "")
                        .replaceAll("_", "")
                        .replace("HDR", "")
                        .replace("HHT", "")
                        .replace("BURST2", "")
                        .replace("BURST1", "")
                        .replace("BURST2", "")
                        .replace("BURST6", "");

                try {
                    name = dest.format(src.parse(name));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                name += suffix;
                if (preview) {
                    System.out.println(jpegFile.getName() + " -> " + dir + File.separator + name);
                } else {
                    String path = dir + File.separator + name;
                    jpegFile.renameTo(new File(path));
                }
            }
        }
    }

    //mmexport1521601529415
    private static void handlerWeixin(File file, boolean preview) {
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File jpegFile : files) {
                String path = jpegFile.getPath();
                String dir = path.substring(0, path.lastIndexOf("\\") + 1);
                String suffix = path.substring(path.lastIndexOf("."));
                String name = jpegFile.getName()
                        .replace("trimed_", "")
                        .replace("wx_camera_", "")
                        .replace("S_IMG", "")
                        .replace("mmexport", "")
                        .replace(suffix, "");
                Date date = new Date();
                date.setTime(Long.parseLong(name));
                name = dest.format(date);
                path = dir + name + suffix;
                if (preview) {
                    System.out.println(jpegFile.getName() + " -> " + path);
                } else {
                    jpegFile.renameTo(new File(path));
                }
            }
        }
    }

    /**
     * <!-- 图片 -->
     * <dependency>
     * <groupId>com.drewnoakes</groupId>
     * <artifactId>metadata-extractor</artifactId>
     * <version>2.11.0</version>
     * </dependency>
     */
    private static void getImageInfo() {
        File jpegFile = new File("G:\\soft\\sys\\baby\\DSC_2590.JPG");
        Metadata metadata;
        try {
            metadata = ImageMetadataReader.readMetadata(jpegFile);
            for (Directory directory : metadata.getDirectories()) {

                if ("ExifSubIFDDirectory".equalsIgnoreCase(directory.getClass().getSimpleName())) {

                    //光圈F值=镜头的焦距/镜头光圈的直径
                    System.out.println("光圈值: f/" + directory.getString(ExifSubIFDDirectory.TAG_FNUMBER));

                    System.out.println("曝光时间: " + directory.getString(ExifSubIFDDirectory.TAG_EXPOSURE_TIME) + "秒");
                    System.out.println("ISO速度: " + directory.getString(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT));
                    System.out.println("焦距: " + directory.getString(ExifSubIFDDirectory.TAG_FOCAL_LENGTH) + "毫米");

                    System.out.println("拍照时间: " + directory.getString(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));
                    System.out.println("宽: " + directory.getString(ExifSubIFDDirectory.TAG_EXIF_IMAGE_WIDTH));
                    System.out.println("高: " + directory.getString(ExifSubIFDDirectory.TAG_EXIF_IMAGE_HEIGHT));

                }

                if ("ExifIFD0Directory".equalsIgnoreCase(directory.getClass().getSimpleName())) {
                    System.out.println("照相机制造商: " + directory.getString(ExifIFD0Directory.TAG_MAKE));
                    System.out.println("照相机型号: " + directory.getString(ExifIFD0Directory.TAG_MODEL));
                    System.out.println("水平分辨率: " + directory.getString(ExifIFD0Directory.TAG_X_RESOLUTION));
                    System.out.println("垂直分辨率: " + directory.getString(ExifIFD0Directory.TAG_Y_RESOLUTION));
                }
            }
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
