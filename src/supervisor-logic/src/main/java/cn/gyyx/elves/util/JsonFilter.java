package cn.gyyx.elves.util;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * @ClassName: JsonFilter
 * @Description: 自定义jsonfilter，将null值转化为“”,避免转json后数据丢失
 * @author FuDongFang
 * @date 2016年7月11日 下午4:20:54
 */
public class JsonFilter {

	public static final ValueFilter filter = new ValueFilter() {
	    @Override
	    public Object process(Object obj, String s, Object v) {
		    if(v==null)
		        return "";
		    return v;
	    }
	};
	
}
