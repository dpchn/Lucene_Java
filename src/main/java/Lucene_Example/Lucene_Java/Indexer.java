package Lucene_Example.Lucene_Java;

import java.io.File; 
import java.io.FileReader; 
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Date; 
import org.apache.lucene.analysis.Analyzer; 
import org.apache.lucene.analysis.standard.StandardAnalyzer; 
import org.apache.lucene.document.Document; 
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableFieldType;
import org.apache.lucene.store.FSDirectory; 
/** 
* This class demonstrate the process of creating index with Lucene 
* for text files 
*/ 
public class Indexer { 
     public static void main(String[] args) throws Exception{ 
     //indexDir is the directory that hosts Lucene's index files 
    
     //dataDir is the directory that hosts the text files that to be indexed 
     File   dataDir  = new File("D:/Lucene/DataDir"); 
     String indexDir = "D:/Lucene/IndexDir";
     FSDirectory fsDirectory = FSDirectory.open(Paths.get(indexDir));
     Analyzer luceneAnalyzer = new StandardAnalyzer(); 
     File[] dataFiles  = dataDir.listFiles(); 
    // IndexWriter indexWriter = new IndexWriter(indexDir,luceneAnalyzer,true); 
     IndexWriterConfig writerConfig = new IndexWriterConfig(luceneAnalyzer);
     IndexWriter writer = new IndexWriter(fsDirectory,writerConfig );
     long startTime = new Date().getTime(); 
     for(int i = 0; i < dataFiles.length; i++){ 
          if(dataFiles[i].isFile()){
               System.out.println("Indexing file " + dataFiles[i].getCanonicalPath()); 
               Document document = new Document(); 
               Reader txtReader = new FileReader(dataFiles[i]); 
            //   Field contents = new Field("contents", txtReader, );
               
            //   document.add(Field.Text("path",dataFiles[i].getCanonicalPath())); 
               document.add(new TextField("contents", txtReader.toString(),Field.Store.YES)); 
               document.add(new TextField("path", dataFiles[i].getCanonicalPath(),Field.Store.YES));
               writer.addDocument(document); 
          } 
     } 
   //  writer.optimize(); 
     writer.close(); 
     long endTime = new Date().getTime(); 
         
     System.out.println("It takes " + (endTime - startTime) 
         + " milliseconds to create index for the files in directory "
         + dataDir.getPath());        
     } 
}