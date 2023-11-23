import org.dom4j.*;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        try {
            // Construit un document xml
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("hello");
            root.addElement("petstore").addAttribute("date", new Date().toString()).addText("Hello Petstore!");

            // Affiche le document xml
            System.out.println(document.asXML());

            // Récupère les données avec xPath
            Node node = document.selectSingleNode("/hello/petstore");
            System.out.println(node.getText());
            System.out.println(node.valueOf("@date"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

