package com.ferret.utils;

import com.ferret.bean.clustercompare.ImageAroundSize;
import com.ferret.bean.clustercompare.WidthHeight;
import com.ferret.service.face.FaceImage;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class CutImgUtil {

	/**
	 * 根据坐标 截取图片
	 * @author zwc
	 * @return
	 */
	public static boolean cutPic(String srcFile, String outFile, int x, int y,
			int width, int height) {
		FileInputStream is = null;
		ImageInputStream iis = null;
		try {
			// 如果源图片不存在
			if (!new File(srcFile).exists()) {
				return false;
			}
			// 读取图片文件
			is = new FileInputStream(srcFile);
			// 获取文件格式
		//	String ext = srcFile.substring(srcFile.lastIndexOf(".") + 1);
			String ext = "jpg";
			// ImageReader声称能够解码指定格式
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(ext);
			ImageReader reader = it.next();
			// 获取图片流
			iis = ImageIO.createImageInputStream(is);
			// 输入源中的图像将只按顺序读取
			reader.setInput(iis, true);
			// 描述如何对流进行解码
			ImageReadParam param = reader.getDefaultReadParam();
			// 图片裁剪区域
			Rectangle rect = new Rectangle(x, y, width, height);
			// 提供一个 BufferedImage，将其用作解码像素数据的目标
			param.setSourceRegion(rect);
			// 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象
			BufferedImage bi = reader.read(0, param);
			// 保存新图片
			File tempOutFile = new File(outFile);
			if (!tempOutFile.exists()) {
				boolean mkdirs = tempOutFile.mkdirs();
				if(!mkdirs){
					log.error("名称为:"+tempOutFile.getName()+" 的文件夹创建失败,请检查文件路径");
					return false;
				}
			}
			ImageIO.write(bi, ext, new File(outFile));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (iis != null) {
					iis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
	}
	/**
	 * 在图片上绘制矩形 ， 并返回图片的宽和高
	 * @param srcPath 原图片路径
	 * @param destPath 目的图片路径
	 * @param imageAroundSize 绘制矩形的左边
	 * @return 原来图片的宽和高
	 * @author zwc 
	 */
	public static WidthHeight drawRectangle(String srcPath,String destPath,List<ImageAroundSize> imageAroundSize) throws IOException {
		FileOutputStream out = new FileOutputStream(destPath);//输出图片的地址
		WidthHeight widthHeight = new WidthHeight();
		try {
			//图片的宽高
			BufferedImage image = ImageIO.read(new File(srcPath));
			int height = image.getHeight();
			int width = image.getWidth();
			widthHeight.setX(width);
			widthHeight.setY(height);
			Graphics2D g2 = (Graphics2D)image.getGraphics();
			g2.setStroke(new BasicStroke(3)); //画笔宽度
			g2.setColor(Color.blue);//画笔颜色
			for (int i = 0; i < imageAroundSize.size(); i++) {
				ImageAroundSize imageSize = imageAroundSize.get(i);
				g2.drawRect(imageSize.getLeft(), imageSize.getTop(), imageSize.getWidth(), imageSize.getHeight());//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
			}
			new File(destPath).createNewFile();
			ImageIO.write(image, "jpg", out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		return widthHeight;
	}
	/**
	 * 在图片上绘制矩形
	 * @param srcPath 原图片路径
	 * @param destPath 目的图片路径
	 * @param imageFaces 绘制矩形的范围
	 * @return 原来图片的宽和高
	 * @author zwc
	 */
	public static Boolean drawRectangleToImage(String srcPath,String destPath,List<FaceImage> imageFaces) throws IOException {
		FileOutputStream out = new FileOutputStream(destPath);//输出图片的地址
		try {
			BufferedImage image = ImageIO.read(new File(srcPath));
			Graphics2D g2 = (Graphics2D)image.getGraphics();
			g2.setStroke(new BasicStroke(3)); //画笔宽度
			g2.setColor(Color.blue);//画笔颜色
			for (int i = 0; i < imageFaces.size(); i++) {
				FaceImage faceImage = imageFaces.get(i);
				g2.drawRect(faceImage.getLeft(), faceImage.getTop(), faceImage.getWidth(), faceImage.getHeight());//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
			}
			ImageIO.write(image, "jpg", out);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			out.close();
		}
	}
}
