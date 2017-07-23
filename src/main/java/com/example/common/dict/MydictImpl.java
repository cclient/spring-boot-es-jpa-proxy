package com.example.common.dict;

import com.example.common.IOUtil;
import com.example.dao.sql.BusinessRepository;
import com.example.dao.sql.CommunityRepository;
import com.example.dao.sql.ShopRepository;
import com.example.entity.Community;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


/**
 * 字典文件生成工具，从mysql拿数据，生成词典
 */
@Component("mydict")
public class MydictImpl implements Mydict {
    static String[] commonEndStrings = new String[]{"写字楼", "办公楼", "商铺", "公寓", "中心", "大厦", "花园", "小区"};
    private Logger logger = Logger.getLogger(IOUtil.class);
    @Autowired
    private IOUtil ioUtil;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private CommunityRepository communityRepository;

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int getFirstNumOffset(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public static String getWithOutCommonEndName(String str) {
        for (int i = 0; i < commonEndStrings.length; i++) {
            int offset = str.indexOf(commonEndStrings[i]);
            if (offset != -1) {
                return str.substring(0, offset);
            }
        }
        return str;
    }

    public static String[] splitByFirstNum(String str) {
        int offset = getFirstNumOffset(str);
        if (getFirstNumOffset(str) <= 0) {
            return null;
        } else {
            return new String[]{str.substring(0, offset), str.substring(offset)};
        }
    }

    public List<String> getDictWord() {
        long count = communityRepository.count();
        List<String> ls = new ArrayList<>(((int) count) * 2);
        try (Stream<Community> stream = communityRepository.searchAll()) {
            stream.forEach(shop -> {
                String name = shop.getName();
                String shortname = MydictImpl.getWithOutCommonEndName(name);
                ls.add(name);
                if (shortname.length() != name.length()) {
                    ls.add(shortname);
                }
                String[] subarrs = MydictImpl.splitByFirstNum(shop.getName());
                if (subarrs == null) {
                } else {
                    if (!Character.isDigit(subarrs[0].charAt(0))) {
                        System.out.println(subarrs[0].charAt(0));
                        ls.add(subarrs[0]);
                        ls.add(subarrs[1]);
                    }
                }
            });
        } catch (Exception e) {
            logger.warn("从数据库加载字典失败，" + e);
        }
        return ls;
    }

    @Override
    public Boolean saveDictToFile(List<String> dics, String path) {
        return ioUtil.saveCollectionToTxt(dics, path);
    }

    //从本地文件路径获取
    //如果没有则从数据库取
    @Override
    public List<String> LoadDictFromFile(String path) {
        List<String> ls = ioUtil.readLineListWithLessMemory(path);
        if (ls == null || ls.size() == 0) {
            System.out.println("get dict begin ");
            ls = getDictWord();
            System.out.println("get dict end ");
            saveDictToFile(ls, path);
            System.out.println("save dict end");
            return ls;
        }
        return ls;
    }

    @Override
    public List<String> LoadDictFromFile() {
        return this.LoadDictFromFile(this.getClass().getClassLoader().getResource("").getPath());
    }
}
