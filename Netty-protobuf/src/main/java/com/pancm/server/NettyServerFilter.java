package com.pancm.server;

import java.util.concurrent.TimeUnit;

import com.pancm.protobuf.UserInfo.UserMsg;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

/**
  * 
 * @Title: HelloServerInitializer
 * @Description: Netty 服务端过滤器
 * @Version:1.0.0  
 * @author wtoco
  * @date 2019-10-14
  */
public class NettyServerFilter extends ChannelInitializer<SocketChannel> {
	
     @Override
     protected void initChannel(SocketChannel ch) throws Exception {
         ChannelPipeline ph = ch.pipeline();
      
         //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
         ph.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
         // 解码和编码，应和客户端一致
         //传输的协议 Protobuf
         ph.addLast(new ProtobufVarint32FrameDecoder());
         ph.addLast(new ProtobufDecoder(UserMsg.getDefaultInstance()));
         ph.addLast(new ProtobufVarint32LengthFieldPrepender());
         ph.addLast(new ProtobufEncoder());
         
         //业务逻辑实现类
         ph.addLast("nettyServerHandler", new NettyServerHandler());
     }
 }
