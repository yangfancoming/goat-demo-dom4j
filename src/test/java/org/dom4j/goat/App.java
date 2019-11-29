package org.dom4j.goat;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by 64274 on 2019/7/24.
 *
 * @ Description: TODO
 * @ author  山羊来了
 * @ date 2019/7/24---16:01
 */
public class App {

    @Test
    public void test() throws IOException {

        // 第二种方式:创建文档并设置文档的根元素节点
        Element root2 = DocumentHelper.createElement("student");
        Document document2 = DocumentHelper.createDocument(root2);

        // 添加属性
        root2.addAttribute("name", "zhangsan");
        // 添加子节点:add之后就返回这个元素
        Element helloElement = root2.addElement("hello");
        Element worldElement = root2.addElement("world");
        helloElement.setText("hello Text");
        worldElement.setText("world text");

        // 输出到文件 格式
        //
        OutputFormat format = new OutputFormat("    ", true);// 设置缩进为4个空格，并且另起一行为true
        XMLWriter xmlWriter2 = new XMLWriter( new FileOutputStream("student.xml"), format);
        xmlWriter2.write(document2);

        // 另一种输出方式，记得要调用flush()方法,否则输出的文件中显示空白
        XMLWriter xmlWriter3 = new XMLWriter(new FileWriter("student2.xml"), format);
        xmlWriter3.write(document2);
        xmlWriter3.flush();
        xmlWriter3.close();

    }

    @Test
    public void test2() throws IOException {
        // 第一种方式：创建文档，并创建根元素
        // 创建文档:使用了一个Helper类
        Document document = DocumentHelper.createDocument();

        // 创建根节点并添加进文档
        Element root = DocumentHelper.createElement("student");
        document.setRootElement(root);

        XMLWriter xmlWriter = new XMLWriter();
        xmlWriter.write(document);
    }
}
