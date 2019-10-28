package util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.lwjgl.BufferUtils;

/**
 * Eine Klasse mit verschiedenen statischen Methoden, die von �berall aus mit
 * <code>StaticUtils.xyz()</code> verwendet werden k�nnen.<br>
 * Enth�lt bisher vor allem Methoden zum Laden von Dateien, da das durch das
 * Verpacken in eine Jar-Datei ziemich verkompliziert wurde. <br>
 * <br>
 * 10% von mir, 90% von Stackoverflow
 * 
 * @author Ben
 */
public class StaticUtils {
	
	/**
	 * L�dt eine Ressource aus einer Datei in einen ByteBuffer.<br>
	 * Das ganze muss unterschiedlich geladen werden, je nachdem ob das Programm von Eclipse oder von einer Jar-Datei aus ausgef�hrt wird, deshalb ist das ganze etwas komplizierter.
	 * <br><br>
	 * Der Code dazu stammt quasi komplett von
	 * <a href="https://gamedev.stackexchange.com/questions/105555/setting-window-icon-using-glfw-lwjgl-3">gamedev.stackexchange.com</a> - macht euch keine Sorgen,
	 * wenn ihr den nicht versteht, ich versteh da auch nicht alles. Hauptsache erstmal, es funktioniert, um eine h�bschere Methode kann man sich sp�ter immer noch k�mmern.
	 * 
	 * @param resource der Dateipfad relativ zum Projekt-Ordner, also z.B. res/Icon.png
	 * @return ByteBuffer mit dem Inhalt der Datei
	 * @throws IOException falls was schiefgeht. Ich hoffe nicht, sonst haben wir ein echt nerviges Problem
	 */
	public static ByteBuffer ioResourceToByteBuffer(String resource) throws IOException {
		ByteBuffer buffer;
		Path path = Paths.get(resource);
		// pr�ft, ob die Datei normal aus dem Dateisystem geladen werden kann
		if (Files.isReadable(path)) {
			// l�dt die Datei aus dem Dateisystem
			try (SeekableByteChannel fc = Files.newByteChannel(path)) {
				buffer = BufferUtils.createByteBuffer((int)fc.size()+1);
				while (fc.read(buffer)!=-1){
					
				}
			}
		} else {
			// l�dt die Datei aus der Jar-Datei. Ganz h�ssliche Angelegenheit
			try (InputStream source = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
				ReadableByteChannel rbc = Channels.newChannel(source)) {
				buffer = BufferUtils.createByteBuffer(4096);
				while (true) {
					int bytes = rbc.read(buffer);
					if (bytes==-1) {
						// beendet die Schleife, weil die Datei komplett geladen wurde
						break;
					}
					if (buffer.remaining()==0) {
						// vergr��ert den Buffer, um Platz f�r den Rest der Datei zu schaffen
						buffer = resizeBuffer(buffer, buffer.capacity()*2);
					}
				}
			}
		}
		buffer.flip();
		return buffer;
	}
	
	/**
	 * �ndert die Gr��e eines ByteBuffers, indem ein neuer Buffer erstellt und mit dem Inhalt des alten bef�llt wird.
	 * 
	 * @param buffer der alte Buffer.
	 * @param newCapacity Kapazit�t bzw. Gr��e des neuen Buffers
	 * @return der neue Buffer
	 */
	public static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}
	
}
