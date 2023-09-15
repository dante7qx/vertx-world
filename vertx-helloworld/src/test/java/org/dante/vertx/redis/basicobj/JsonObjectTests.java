package org.dante.vertx.redis.basicobj;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.Test;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.buffer.Buffer;

/**
 * 参考：https://silentbalanceyh.gitbooks.io/vert-x/content/chapter02/02-2-vertx-json.html
 * 
 * @author dante
 *
 */
public class JsonObjectTests {

	@Test
	public void createJsonObject() {
		final String jsonString = "{\"foo\":\"bar\"}";
		final JsonObject object = new JsonObject(jsonString);
		assertTrue(object.containsKey("foo"));
		
		// 使用Buffer初始化
		final String literal = "{\"age\":18,\"name\":\"Lang\"}";
		final Buffer buffer = Buffer.buffer(literal);
		final JsonObject bufferData = new JsonObject(buffer);
		System.out.println(bufferData.encodePrettily());
		
	}

	@Test
	public void jsonTest() {
		String objJsonStr = Json.encode(new MsgInfo("1001", "太空电梯开始搭建", Date.from(Instant.now())));
		System.out.println(objJsonStr);
		MsgInfo msg = Json.decodeValue(objJsonStr, MsgInfo.class);
		assertNotNull(msg);
		System.out.println("在时间：" + msg.getSendDate() + "，发送消息 【 " +msg.getMsg()+ " 】");
	}
	
	@Test
	public void pojo2JsonObject() {
		MsgInfo msgInfo = new MsgInfo("1001", "太空电梯开始搭建", Date.from(Instant.now()));
		// 将Pojo转换为JsonObject
		JsonObject jsonObj = JsonObject.mapFrom(msgInfo);
		System.out.println(jsonObj.encodePrettily());
		// 将JsonObject转化为Pojo
		JsonObject jsonObj2 = new JsonObject()
				.put("msgId", "2001")
				.put("msg", "中庸之道")
				.put("sendDate", Instant.now().toEpochMilli());
		final MsgInfo msgInfo2 = jsonObj2.mapTo(MsgInfo.class);
		System.out.println(msgInfo2);
		assertNotNull(msgInfo2);		
	}
	
	@Test
	public void jsonArray() {
		JsonArray jsonArray = new JsonArray();
		jsonArray.add("但丁").add(1000).add(false);
		System.out.println(jsonArray.getString(0));
		System.out.println(jsonArray);
		assertNotNull(jsonArray.getString(0));
	}
}

class MsgInfo implements Serializable {
	private static final long serialVersionUID = -7086399081268210157L;

	private String msgId;
	private String msg;
	private Date sendDate;

	public MsgInfo() {
	}

	public MsgInfo(String msgId, String msg, Date sendDate) {
		super();
		this.msgId = msgId;
		this.msg = msg;
		this.sendDate = sendDate;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Override
	public String toString() {
		return "MsgInfo [msgId=" + msgId + ", msg=" + msg + ", sendDate=" + sendDate + "]";
	}
}