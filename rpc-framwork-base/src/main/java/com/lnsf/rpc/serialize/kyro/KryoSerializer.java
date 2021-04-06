package com.lnsf.rpc.serialize.kyro;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.lnsf.rpc.exception.SerializeException;
import com.lnsf.rpc.remoting.dto.RpcRequest;
import com.lnsf.rpc.remoting.dto.RpcResponse;
import com.lnsf.rpc.serialize.Serializer;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @program: rpc-framwork
 * @description: kryo 序列化，kryo序列化的性能很高，只兼容java
 * @author money
 * @create: 2021-02-18 17:54
 */
@Slf4j
public class KryoSerializer implements Serializer {

    /**
     * 因为kryo线程不安全，使用 threadlocal 存储 kryo 对象
     * ThreadLocal 提供了线程本地的实例。它与普通变量的区别在于，每个使用该变量的线程都会初始化一个完全独立的实例副本。
     *
     * tips:
     * Kryo 支持对注册行为，如 kryo.register(SomeClazz.class);,
     * 这会赋予该 Class 一个从 0 开始的编号，但 Kryo 使用注册行为最大的问题在于，
     * 其不保证同一个 Class 每一次注册的号码想用，这与注册的顺序有关，也就意味着在不同的机器、
     * 同一个机器重启前后都有可能拥有不同的编号，这会导致序列化产生问题，所以在分布式项目中，一般关闭注册行为
     **/
    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcResponse.class);
        kryo.register(RpcRequest.class);
        return kryo;

    });

    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            // Object->byte:将对象序列化为byte数组
            //把数据写入out
            kryo.writeObject(output, obj);
            //为这个线程局部变量删除当前线程的值。如果这个线程局部变量随后被当前线程读取，
            // 它的值将通过调用它的initialValue方法重新初始化，除非它的值是由curr设置的
            kryoThreadLocal.remove();
            return output.toBytes();
        } catch (Exception e) {
            throw new SerializeException("Serialization failed");
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            Input input = new Input(byteArrayInputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            // byte->Object:从byte数组中反序列化出对对象
            Object o = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();
            return clazz.cast(o);
        } catch (Exception e) {
            throw new SerializeException("Deserialization failed");
        }
    }

}
