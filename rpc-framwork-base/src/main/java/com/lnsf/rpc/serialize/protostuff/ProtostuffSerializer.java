package com.lnsf.rpc.serialize.protostuff;


import com.lnsf.rpc.serialize.Serializer;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * @program: rpc-framwork
 * @description: protostuf 序列化，ProtocolBuf是一种轻便高效的结构化数据存储格式，
 * 可以用于结构化数据序列化。适合做数据存储或 RPC 数据交换格式。可用于通讯协议、
 * 数据存储等领域的语言无关、平台无关、可扩展的序列化结构数据格式。
 * ProtoBuff序列化对象可以很大程度上将其压缩，可以大大减少数据传输大小，提高系统性能。
 * 对于大量数据的缓存，也可以提高缓存中数据存储量。
 * @author money
 * @create: 2021-02-18 17:54
 */
public class ProtostuffSerializer implements Serializer {

    /**
     *  避免每次序列化都重新申请Buffer空间，LinkedBuffer意为申请一个内存空间用户缓存，LinkedBuffer.DEFAULT_BUFFER_SIZE表示申请了默认大小的	   *	空间512个字节，我们也可以使用MIN_BUFFER_SIZE，表示256个字节
     */
    private static final LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

    @Override
    public byte[] serialize(Object obj) {
        // 获取泛型对象的类型
        Class<?> clazz = obj.getClass();
        // 创建泛型对象的schema对象，schema就是一个组织结构，就好比是数据库中的表、视图等等这样的组织机构，在这里表示的就是序列化对象的结构
        Schema schema = RuntimeSchema.getSchema(clazz);
        byte[] bytes;
        try {
            bytes = ProtostuffIOUtil.toByteArray(obj, schema, BUFFER);
        } finally {
            BUFFER.clear();
        }
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        T obj = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        return obj;
    }
}
