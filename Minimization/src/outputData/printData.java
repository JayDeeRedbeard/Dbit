package outputData;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import readdata.longData;

public class printData {
	/**
	 * Gibt die minimierte Funktion zurück in einer Datei nach dem Format, wie sie eingelesen wurde.
	 * @throws IOException
	 */
	public static void ausgabeindatei() throws IOException{
		//longData.testpfad hat den Pfad der verschiedenen Scaltungen die getestet werden.
		File folder = new File(longData.testpfad);
		String b = "";
		int counter=0;
		int counter1=0;
		String a ="";
			for( File file : folder.listFiles() ){
				System.out.println( file.getName() );
				a=file.getName();
				a=a.substring(0, a.length()-4);					//Durch das Zurechtschneiden kann man das Anhängsel "new.txt" benutzen.
				File ausgabeDatei = new File(longData.results+"/"+a + "new.txt");
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
						if(!longData.validRowZwischenspeicher.get(counter)){									
							ausgabe.println("-");
						} else{
							if( !LongRemovingBits.removingBits.solution.get(counter1) ){
								ausgabe.println("-");
							} else{
								ausgabe.println(b);
							}
							counter1++;
						}
						counter++;
					} else {
						ausgabe.println(b);
						
					}
					tmp.close();
				}
				s.close();
				ausgabe.close();
			}
			
		
	}
}
