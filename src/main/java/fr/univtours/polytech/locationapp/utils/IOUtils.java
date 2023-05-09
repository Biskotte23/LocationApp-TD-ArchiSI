package fr.univtours.polytech.locationapp.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {

	/**
	 * Permet de transformer un {@link InputStream} en tableau de byte.
	 * 
	 * @param is : L'InputStream à transformer.
	 * @return Le tableau de byte.
	 * @throws IOException
	 */
	public static byte[] InputStreamToByteArray(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		return buffer.toByteArray();
	}
}
