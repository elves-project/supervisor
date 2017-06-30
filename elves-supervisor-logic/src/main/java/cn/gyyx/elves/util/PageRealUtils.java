package cn.gyyx.elves.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageRealUtils {
    
	public static List<Map<String,Object>> getPageDate(int firstResult,int pageCount,List<Map<String,Object>> SubList){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if (SubList.size() > 0) {
			if (firstResult+pageCount < SubList.size()) {
				for (int i = firstResult; i < firstResult+pageCount; i++) {
					list.add(SubList.get(i));
				}
			} else {
				for (int i = firstResult; i < SubList.size(); i++) {
					list.add(SubList.get(i));
				}
			}
			// 没有条件分页查询
		}
		
		return list;
	}
}
