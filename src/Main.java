import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Main {

    public static void main(String[] args) throws IOException, JAXBException {
        Node[] nodes = new Node[1023];

        for (int i = 0; i < nodes.length; i++)
            nodes[i] = new Node("Node" + i);

        for (int i = 0; i < nodes.length / 2; i++) {
            nodes[i].children.add(nodes[(2 * i) + 1]);
            nodes[i].children.add(nodes[(2 * i) + 2]);
        }

        String type = "xml";

        switch (type) {
            case "xml":
                serializeToXML(nodes);
            case "binary":
                serializeToBinary(nodes);

        }

    }

    static void serializeToBinary(Node[] nodes) throws IOException {
        long startTime = System.nanoTime();
        long endTime = System.nanoTime();

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("binaryTest"));
        for (int i = 0; i < 100; i++)
            outputStream.writeObject(nodes[i]);

        long duration = (endTime - startTime);
        System.out.println("Total execution time for Binary Serialization in millis: "
                + duration / 1000000f);
    }

    static void serializeToXML(Node[] nodes) throws IOException, JAXBException {
        long startTime = System.nanoTime();
        long endTime = System.nanoTime();

        JAXBContext context = JAXBContext.newInstance(Node.class);
        Marshaller m = context.createMarshaller();
        for (int i = 0; i < 100; i++)
            m.marshal(nodes[i], new FileOutputStream("xmlTest.xml"));

        long duration = (endTime - startTime);
        System.out.println("Total execution time for XML Serialization in millis: "
                + duration / 1000000f);
    }
}
