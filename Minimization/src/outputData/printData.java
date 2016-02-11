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
				if(file.getName().contains(("behavior"))){
				System.out.println( file.getName() );
				a=file.getName();
				a=a.substring(0, a.length()-4);					//Durch das Zurechtschneiden kann man das Anhaengsel "new.txt" benutzen.
				File ausgabeDatei = new File(longData.results+"/"+a + "new.txt");
				PrintStream ausgabe = null;
				if ( !ausgabeDatei.exists() )
					ausgabe = new PrintStream(ausgabeDatei);
				else {
					System.out.println("Datei existiert schon.");
					ausgabe = new PrintStream(ausgabeDatei);
				}
				GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(longData.testpfad +"/"+file.getName()));
				BufferedReader s = new BufferedReader(new InputStreamReader(gzip));
			
				while ((b = s.readLine()) != null){					//Zwischenspeicherung der aktuellen Zeile
					if(b.contains("{f")){							//D-Bit wurde erkannt!
						if(!LongRemovingBits.removingBits.solution.get(counter)){
							ausgabe.println("-");
						} else{
							ausgabe.println(b);
							countActiveDbits++;
						}
						counter++;
					} else {
						ausgabe.println(b);
					}
				}
				System.out.println("D-Bits"+countActiveDbits);
				s.close();
				gzip.close();
				ausgabe.close();
				
				byte[] buffer = new byte[1024];
			    try{
			      //Specify Name and Path of Output GZip file here
			      GZIPOutputStream gos = 
			       new GZIPOutputStream(new FileOutputStream(longData.results+"/"+a + "new.txt.gz"));
			 
			      //Specify location of Input file here
			      FileInputStream fis = 
			       new FileInputStream(longData.results+"/"+a + "new.txt");
			 
			      //Reading from input file and writing to output GZip file
			      int length;
			      while ((length = fis.read(buffer)) > 0) {
			 
			        /* public void write(byte[] buf, int off, int len): 
			         * Writes array of bytes to the compressed output stream.
			         * This method will block until all the bytes are written.
			         * Parameters:
			         * buf - the data to be written
			         * off - the start offset of the data
			         * len - the length of the data
			         */
			        gos.write(buffer, 0, length);
			      }
			 
			      fis.close();
			 
			      /* public void finish(): Finishes writing compressed 
			       * data to the output stream without closing the 
			       * underlying stream.
			       */
			      gos.finish();
			      gos.close();
			 
			      System.out.println("File Compressed!!");
			 
			    }catch(IOException ioe){
			        ioe.printStackTrace(); 
			     }
			}
			
			}
	}
	public static void ausgabeindateimhsHyperGraph(ArrayList<Integer> mhs) throws IOException{
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