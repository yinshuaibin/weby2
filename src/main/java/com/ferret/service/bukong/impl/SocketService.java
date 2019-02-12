package com.ferret.service.bukong.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketService{
		public static void main(String[] args) {
			try {
				ServerSocket serverSocket=new ServerSocket(5656);
				Socket sc=serverSocket.accept();
				System.out.println("socket 连接成功");
				DataInputStream in=new DataInputStream(sc.getInputStream());
				DataOutputStream dos=new DataOutputStream(sc.getOutputStream());
				int i=0;
				while(true) {
					System.out.println("接受消息开始"+i++);
					String s=in.readUTF();
					System.out.println("开始标记："+s);
					dos.writeUTF("Heartbeat");
					dos.flush();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
