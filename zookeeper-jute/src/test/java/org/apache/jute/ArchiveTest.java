package org.apache.jute;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.TreeMap;

import org.apache.jute.BinaryInputArchive;
import org.apache.jute.BinaryOutputArchive;
import org.apache.jute.Index;
import org.apache.jute.InputArchive;
import org.apache.jute.OutputArchive;
import org.apache.jute.Record;

public class ArchiveTest {
    public static void main( String[] args ) throws IOException {
        String path = "D:\\shenxixi1\\project\\zookeeper\\conf\\test.txt";
        String path2 = "D:\\shenxixi1\\project\\zookeeper\\conf\\test2.txt";
        // write operation
        OutputStream outputStream = new FileOutputStream(new File(path));
        OutputStream outputStream2 = new FileOutputStream(new File(path2));
        BinaryOutputArchive binaryOutputArchive = BinaryOutputArchive.getArchive(outputStream);
        CsvOutputArchive csvOutputArchive = CsvOutputArchive.getArchive(outputStream2);

        // tag
        Person person3 = new Person(25, "leesf1");
        csvOutputArchive.writeRecord(person3, "leesf1");


        //  tag 是标识作用   荣来标识唯对象  反序列化的时候需要用到
        binaryOutputArchive.writeBool(true, "boolean");
        byte[] bytes = "leesf".getBytes();
        binaryOutputArchive.writeBuffer(bytes, "buffer");
        binaryOutputArchive.writeDouble(13.14, "double");
        binaryOutputArchive.writeFloat(5.20f, "float");
        binaryOutputArchive.writeInt(520, "int");
        Person person = new Person(25, "leesf");
        binaryOutputArchive.writeRecord(person, "leesf");
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        map.put("leesf", 25);
        map.put("dyd", 25);
        Set<String> keys = map.keySet();
        binaryOutputArchive.startMap(map, "map");
        int i = 0;
        for (String key: keys) {
            String tag = i + "";
            binaryOutputArchive.writeString(key, tag);
            binaryOutputArchive.writeInt(map.get(key), tag);
            i++;
        }

        binaryOutputArchive.endMap(map, "map");


        // read operation
        InputStream inputStream = new FileInputStream(new File(path));
        BinaryInputArchive binaryInputArchive = BinaryInputArchive.getArchive(inputStream);


        System.out.println(binaryInputArchive.readBool("boolean"));
        System.out.println(new String(binaryInputArchive.readBuffer("buffer")));
        System.out.println(binaryInputArchive.readDouble("double"));
        System.out.println(binaryInputArchive.readFloat("float"));
        System.out.println(binaryInputArchive.readInt("int"));
        Person person2 = new Person();
        binaryInputArchive.readRecord(person2, "leesf");
        System.out.println(person2);
        Index index = binaryInputArchive.startMap("map");
        int j = 0;
        while (!index.done()) {
            String tag = j + "";
            System.out.println("key = " + binaryInputArchive.readString(tag)
                    + ", value = " + binaryInputArchive.readInt(tag));
            index.incr();
            j++;
        }

        InputStream inputStream2 = new FileInputStream(new File(path2));
        CsvInputArchive csvInputArchive = CsvInputArchive.getArchive(inputStream2);
        Person person4 = new Person();
        csvInputArchive.readRecord(person4,"leesf1");
        System.out.println(person4);
    }

    static class Person implements Record {
        private int age;
        private String name;

        public Person() {

        }

        public Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public void serialize(OutputArchive archive, String tag) throws IOException {
            archive.startRecord(this, tag);
            archive.writeInt(age, "age");
            archive.writeString(name, "name");
            archive.endRecord(this, tag);
        }

        public void deserialize(InputArchive archive, String tag) throws IOException {
            archive.startRecord(tag);
            age = archive.readInt("age");
            name = archive.readString("name");
            archive.endRecord(tag);
        }

        public String toString() {
            return "age = " + age + ", name = " + name;
        }
    }
}