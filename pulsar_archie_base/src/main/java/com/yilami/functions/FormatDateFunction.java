package com.yilami.functions;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDateFunction implements Function<String,String> {
    // 此方法, 没接收到一条数据, 就会调用一次  process方式, 其中
    //  参数1: 输入的消息数据
    //  参数2: Context 表示上下文对象,用于执行一些相关的统计计算操作, 以及获取到相关的对象以及元数据信息

    private SimpleDateFormat format1 =  new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");
    private SimpleDateFormat format2 =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String process(String input, Context context) throws Exception {
        Date oldDate = format1.parse(input);

        return format2.format(oldDate);
    }
}
