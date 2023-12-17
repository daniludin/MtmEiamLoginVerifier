package ch.bgs.anttask;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MtmEiamLoginVerifierTest {

	public static void main(String[] args) {

		new MtmEiamLoginVerifierTest().test();
	}

	public void test() {

		String fileZip = "C:/Users/danie/git/substidoc/dist/substidoc-0.0.0.war";

		try {
			byte[] buffer = new byte[1024];
			ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
			ZipEntry zipEntry = zis.getNextEntry();
			while (zipEntry != null) {
				// System.out.println("zipEntry: " + zipEntry.getName());

				if (zipEntry.getName().contains("applicationContext.xml")) {

					int read = 0;
					StringBuilder s = new StringBuilder();

					while ((read = zis.read(buffer, 0, 1024)) >= 0) {
						s.append(new String(buffer, 0, read));
					}
					// System.out.println("content: " + s.toString());

					// <!--[\s\S\n]*?-->

					Pattern pattern = Pattern.compile("<!--[\\s\\S\\n]*?-->");

					// in case you would like to ignore case sensitivity,
					// you could use this statement:
					// Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
					Matcher matcher = pattern.matcher(s.toString());
					// check all occurance
					while (matcher.find()) {
						// System.out.print("Start index: " + matcher.start());
						// System.out.print(" End index: " + matcher.end() + " ");
						System.out.println(matcher.group());
						if (matcher.group().contains("saml")) {
							System.err.println("ALARM!!!");
						}
					}

				}
				zipEntry = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();
		} catch (

		FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
