package Lucene_Example.Lucene_Java;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.text.Document;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;

public class Searcher {
	public static void main(String[] args) {
		System.out.println("Enter query to Search : ");
		Scanner scanner = new Scanner(System.in);
		String textToFind = scanner.nextLine();
		String indexDir = "D:/Lucene/IndexDir";
		try {
			FSDirectory fsDirectory = FSDirectory.open(Paths.get(indexDir));
			IndexReader reader = DirectoryReader.open(fsDirectory);
			IndexSearcher searcher = new IndexSearcher(reader);
			
			Query query = getQuery(textToFind);
			TopDocs topDocs = searcher.search(query, 10);
			ScoreDoc[] scoreDoc = topDocs.scoreDocs;
			if (topDocs.totalHits == 0)
				System.out.println("Not Found ");
			else {
				for (ScoreDoc sd : scoreDoc) {
					org.apache.lucene.document.Document document = searcher.doc(sd.doc);
					System.out.println(document.get("path"));
					System.out.println(searcher.doc(sd.doc).getField("contents"));
					System.out.println(sd.score);
				}
			}
scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static Query getQuery(String textToFind){
		Analyzer analyzer = new StandardAnalyzer();
		QueryBuilder queryParser = new QueryBuilder(analyzer);
		Query query = queryParser.createPhraseQuery("contents", textToFind);
		return query;
	}
}
