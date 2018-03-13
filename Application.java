import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Bits;

import java.nio.file.Paths;

// core folder:
// /sne/home/lgijtenbeek/Downloads/lucene-solr-master/lucene/core/src/java/

// compile and run:
// javac -cp /sne/home/lgijtenbeek/Downloads/lucene-solr-master/lucene/core/src/java/ Application.java
// java -cp /sne/home/lgijtenbeek/Downloads/lucene-solr-master/lucene/core/src/java/ Application

// argument:
// /sne/home/lgijtenbeek/Desktop/CCF_ELKPROJECT/NFS/Pk8lCCNYRcCJZNoOXUGp4Q/0/index

public class Application {

    public static void main(String[] args) {
    	
        try (Directory directory = FSDirectory.open(Paths.get(args[0]))) {
            try (IndexReader indexReader = DirectoryReader.open(directory)) {
            	
            	Bits bits = MultiFields.getLiveDocs(indexReader);
                String toPrint = "";
                
                for (int docId = 0; docId < indexReader.maxDoc(); docId++) {
                	if (bits.get(docId)) {
                		
                        Document document = indexReader.document(docId);
                        StringBuilder jsonDoc = new StringBuilder("{");
                        
                        for (IndexableField field : document) {
                            jsonDoc.append('"').append(field.name()).append('"').append(' ');
                            jsonDoc.append('"').append(field.stringValue()).append('"').append('\n');
                        }
                        jsonDoc.append("}");
                        toPrint += jsonDoc.toString();
                    }
                
                System.out.println(toPrint);
                }
            } catch ( Exception e) {
            	e.printStackTrace();
            }
            
        } catch ( Exception e) {
        	e.printStackTrace();
        }
 
    }

}
