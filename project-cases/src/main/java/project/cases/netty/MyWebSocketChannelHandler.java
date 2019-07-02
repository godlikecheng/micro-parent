package project.cases.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 初始化连接时候的各个组件  => 用到了MyWebSocketHandler.java类
 */
public class MyWebSocketChannelHandler extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel e) throws Exception {
//		e.pipeline().addLast("http-codec", new HttpServerCodec());
//		e.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
//		e.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
//		e.pipeline().addLast("handler", new MyWebSocketHandler());  // MyWebSocketHandler.java

		ChannelPipeline pipeline = e.pipeline();
		pipeline.addLast(new HttpServerCodec());  //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
		pipeline.addLast(new ChunkedWriteHandler());  //以块的方式来写的处理器
		//netty是基于分段请求的，HttpObjectAggregator的作用是将请求分段再聚合,参数是聚合字节的最大长度
		pipeline.addLast(new HttpObjectAggregator(8192));
		//ws://localhost:9999/ws
		//参数指的是contex_path 指定路径
		pipeline.addLast(new WebSocketServerProtocolHandler("/websocket"));
		//websocket定义了传递数据的6中frame类型
		pipeline.addLast(new MyWebSocketHandler());
	}
}
