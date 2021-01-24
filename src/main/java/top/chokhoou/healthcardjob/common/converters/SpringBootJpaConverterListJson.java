package top.chokhoou.healthcardjob.common.converters;

import cn.hutool.json.JSONUtil;

import javax.persistence.AttributeConverter;

/**
 * @author ChoKhoOu
 */
public class SpringBootJpaConverterListJson implements AttributeConverter<Object, String> {
    @Override
    public String convertToDatabaseColumn(Object o) {
        return JSONUtil.toJsonStr(o);
    }

    @Override
    public Object convertToEntityAttribute(String s) {
        return JSONUtil.toList(s,String.class);
    }
}
