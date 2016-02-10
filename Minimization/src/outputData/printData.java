package outputData;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import readdata.longData;

public class printData {
	/**
	 * Gibt die minimierte Funktion zurueck in einer Datei nach dem Format, wie sie eingelesen wurde.
	 * @throws IOException
	 */
	public static void ausgabeindatei() throws IOException{
		//longData.testpfad hat den Pfad der verschiedenen Scaltungen die getestet werden.
		File folder = new File(longData.testpfad);
		String b = "";
		int counter=0;
		int countActiveDbits=0;
		String a ="";
			for( File file : folder.listFiles() ){
				
				a=file.getName();
				if (file.getName().contains(("behavior"))) {
					a=a.substring(0, a.length()-7);					//Durch das Zurechtschneiden kann man das Anhaengsel "new.txt" benutzen.
					File ausgabeDatei = new File(longData.results+"/"+a+"new.txt.gz" );
					System.out.println( file.getName() + "new.txt.gz");
					@SuppressWarnings("resource")
					BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream( ausgabeDatei)) ));
				
					GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(longData.testpfad +"/"+file.getName()));
					BufferedReader s = new BufferedReader(new InputStreamReader(gzip));
				
					while ((b = s.readLine()) != null){					//Zwischenspeicherung der aktuellen Zeile
						if(b.contains("{f")){									//D-Bit wurde erkannt!
							if(!LongRemovingBits.removingBits.solution.get(counter)){
								bufferedWriter.write("-");
							} else{
								bufferedWriter.write(b);
								countActiveDbits++;
							}
							counter++;
						} else {
							bufferedWriter.write(b);
						}
					}
				
					System.out.println("D-Bits"+countActiveDbits);
					s.close();
					gzip.close();
				}
			}
			
		
	}public static void ausgabeindateimhsHyperGraph(ArrayList<Integer> mhs) throws IOException{
		//longData.testpfad hat den Pfad der verschiedenen Scaltungen die getestet werden.
		File folder = new File(longData.testpfad);
		String b = "";
		int counter=0;
		int countActiveDbits=0;
		String a ="";
			for( File file : folder.listFiles() ){
				System.out.println( file.getName() );
				a=file.getName();
				a=a.substring(0, a.length()-4);					//Durch das Zurechtschneiden kann man das Anhaengsel "new.txt" benutzen.
				File ausgabeDatei = new File(longData.results+"/"+a + "newHyperGraph.txt");
				PrintStream ausgabe = null;
				if ( !ausgabeDatei.exists() )
					ausgabe = new PrintStream(ausgabeDatei);
				else {
					System.out.println("Datei existiert schon.");
					ausgabe = new PrintStream(ausgabeDatei);
				}
				Scanner s = new Scanner(new File(longData.testpfad +"/"+file.getName()));				
				while (s.hasNextLine()){									
					Scanner tmp= new Scanner(s.nextLine());
					b=tmp.nextLine(); 										//Zwischenspeicherung der aktuellen Zeile
					if(b.contains("{f")){									//D-Bit wurde erkannt!
						//Zwischenspeicher ist da um auch die D-Bits abzudecken die von Anfang an wegen Dominanzen entfernt wurden 
						if(!mhs.contains(counter)){
							ausgabe.println("-");
						} else{
							ausgabe.println(b);
							countActiveDbits++;
						}
						counter++;
					} else {
						ausgabe.println(b);
					}
					tmp.close();
				}
				System.out.println("Uebriggebliebende D-Bits"+countActiveDbits);
				s.close();
				ausgabe.close();
			}
			
		
	}
}