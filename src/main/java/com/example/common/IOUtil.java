package com.example.common;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by cclie on 2016/7/6.
 */
@Component
public class IOUtil {
    private Logger logger = Logger.getLogger(IOUtil.class);

    /**
     * 序列化对象
     *
     * @param o
     * @param path
     * @return
     */
    public boolean saveObjectTo(Object o, String path) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(o);
            oos.close();
        } catch (IOException e) {
            logger.warn("在保存对象" + o + "到" + path + "时发生异常" + e);
            return false;
        }

        return true;
    }

    /**
     * 反序列化对象
     *
     * @param path
     * @return
     */
    public Object readObjectFrom(String path) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(path));
            Object o = ois.readObject();
            ois.close();
            return o;
        } catch (Exception e) {
            logger.warn("在从" + path + "读取对象时发生异常" + e);
        }

        return null;
    }

    /**
     * 一次性读入纯文本
     *
     * @param path
     * @return
     */
    public String readTxt(String path) {
        if (path == null) return null;
        File file = new File(path);
        Long fileLength = file.length();
        byte[] fileContent = new byte[fileLength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
        } catch (FileNotFoundException e) {
            logger.warn("找不到" + path + e);
            return null;
        } catch (IOException e) {
            logger.warn("读取" + path + "发生IO异常" + e);
            return null;
        }

        return new String(fileContent, Charset.forName("UTF-8"));
    }

    public LinkedList<String[]> readCsv(String path) {
        LinkedList<String[]> resultList = new LinkedList<String[]>();
        LinkedList<String> lineList = readLineList(path);
        for (String line : lineList) {
            resultList.add(line.split(","));
        }
        return resultList;
    }

    /**
     * 快速保存
     *
     * @param path
     * @param content
     * @return
     */
    public boolean saveTxt(String path, String content) {
        try {
            FileChannel fc = new FileOutputStream(path).getChannel();
            fc.write(ByteBuffer.wrap(content.getBytes()));
            fc.close();
        } catch (Exception e) {
//            logger.error("IOUtil", "saveTxt", e);
            logger.warn("IOUtil saveTxt 到" + path + "失败" + e.toString());
            return false;
        }
        return true;
    }

    public boolean saveTxt(String path, StringBuilder content) {
        return saveTxt(path, content.toString());
    }

    public <T> boolean saveCollectionToTxt(Collection<T> collection, String path) {
        StringBuilder sb = new StringBuilder();
        for (Object o : collection) {
            sb.append(o);
            sb.append('\n');
        }
        return saveTxt(path, sb.toString());
    }

    /**
     * 将整个文件读取为字节数组
     *
     * @param path
     * @return
     */
    public byte[] readBytes(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            FileChannel channel = fis.getChannel();
            int fileSize = (int) channel.size();
            ByteBuffer byteBuffer = ByteBuffer.allocate(fileSize);
            channel.read(byteBuffer);
            byteBuffer.flip();
            byte[] bytes = byteBuffer.array();
            byteBuffer.clear();
            channel.close();
            fis.close();
            return bytes;
        } catch (Exception e) {
            logger.warn("读取" + path + "时发生异常" + e);
        }

        return null;
    }

    public LinkedList<String> readLineList(String path) {
        LinkedList<String> result = new LinkedList<String>();
        String txt = readTxt(path);
        if (txt == null) return result;
        StringTokenizer tokenizer = new StringTokenizer(txt, "\n");
        while (tokenizer.hasMoreTokens()) {
            result.add(tokenizer.nextToken());
        }

        return result;
    }

    /**
     * 用省内存的方式读取大文件
     *
     * @param path
     * @return
     */
    public LinkedList<String> readLineListWithLessMemory(String path) {
        LinkedList<String> result = new LinkedList<String>();
        String line = null;
        try {
            BufferedReader bw = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            while ((line = bw.readLine()) != null) {
                result.add(line);
            }
            bw.close();
        } catch (Exception e) {
            logger.warn("加载" + path + "失败，" + e);
        }

        return result;
    }

    public boolean saveMapToTxt(Map<Object, Object> map, String path) {
        return saveMapToTxt(map, path, "=");
    }

    public boolean saveMapToTxt(Map<Object, Object> map, String path, String separator) {
        map = new TreeMap<Object, Object>(map);
        return saveEntrySetToTxt(map.entrySet(), path, separator);
    }

    public boolean saveEntrySetToTxt(Set<Map.Entry<Object, Object>> entrySet, String path, String separator) {
        StringBuilder sbOut = new StringBuilder();
        for (Map.Entry<Object, Object> entry : entrySet) {
            sbOut.append(entry.getKey());
            sbOut.append(separator);
            sbOut.append(entry.getValue());
            sbOut.append('\n');
        }
        return saveTxt(path, sbOut.toString());
    }

    /**
     * 获取文件所在目录的路径
     *
     * @param path
     * @return
     */
    public String dirname(String path) {
        int index = path.lastIndexOf('/');
        if (index == -1) return path;
        return path.substring(0, index + 1);
    }

    public LineIterator readLine(String path) {
        return new LineIterator(path);
    }

    /**
     * 创建一个BufferedWriter
     *
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public BufferedWriter newBufferedWriter(String path) throws FileNotFoundException, UnsupportedEncodingException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
    }

    /**
     * 创建一个BufferedReader
     *
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public BufferedReader newBufferedReader(String path) throws FileNotFoundException, UnsupportedEncodingException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
    }

    public BufferedWriter newBufferedWriter(String path, boolean append) throws FileNotFoundException, UnsupportedEncodingException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, append), "UTF-8"));
    }

    /**
     * 获取最后一个分隔符的后缀
     *
     * @param name
     * @param delimiter
     * @return
     */
    public String getSuffix(String name, String delimiter) {
        return name.substring(name.lastIndexOf(delimiter) + 1);
    }

    /**
     * 写数组，用制表符分割
     *
     * @param bw
     * @param params
     * @throws IOException
     */
    public void writeLine(BufferedWriter bw, String... params) throws IOException {
        for (int i = 0; i < params.length - 1; i++) {
            bw.write(params[i]);
            bw.write('\t');
        }
        bw.write(params[params.length - 1]);
    }

    /**
     * 方便读取按行读取大文件
     */
    public class LineIterator implements Iterator<String> {
        BufferedReader bw;
        String line;

        public LineIterator(String path) {
            try {
                bw = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
                line = bw.readLine();
            } catch (FileNotFoundException e) {
                logger.warn("文件" + path + "不存在，接下来的调用会返回null" + TextUtility.exceptionToString(e));
                bw = null;
            } catch (IOException e) {
                logger.warn("在读取过程中发生错误" + TextUtility.exceptionToString(e));
                bw = null;
            }
        }

        public void close() {
            if (bw == null) return;
            try {
                bw.close();
                bw = null;
            } catch (IOException e) {
                logger.warn("关闭文件失败" + TextUtility.exceptionToString(e));
            }
            return;
        }

        @Override
        public boolean hasNext() {
            if (bw == null) return false;
            if (line == null) {
                try {
                    bw.close();
                    bw = null;
                } catch (IOException e) {
                    logger.warn("关闭文件失败" + TextUtility.exceptionToString(e));
                }
                return false;
            }

            return true;
        }

        @Override
        public String next() {
            String preLine = line;
            try {
                if (bw != null) {
                    line = bw.readLine();
                    if (line == null && bw != null) {
                        try {
                            bw.close();
                            bw = null;
                        } catch (IOException e) {
                            logger.warn("关闭文件失败" + TextUtility.exceptionToString(e));
                        }
                    }
                } else {
                    line = null;
                }
            } catch (IOException e) {
                logger.warn("在读取过程中发生错误" + TextUtility.exceptionToString(e));
            }
            return preLine;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("只读，不可写！");
        }
    }


}
