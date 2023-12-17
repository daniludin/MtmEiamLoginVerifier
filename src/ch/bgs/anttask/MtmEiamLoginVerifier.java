package ch.bgs.anttask;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

//java.util.zip.ZipInputStream;

public class MtmEiamLoginVerifier extends Task {

	private String searchItem;

	@Override
	public void execute() {

		if (getSearchItem() == null || getSearchItem().length() == 0) {
			log("Das Attribut 'searchItem' fehlt oder ist leer.", Project.MSG_ERR);
			return;
		}
		String fileZip = getProject().getProperty("dist.home") + "/" + getProject().getProperty("app.name") + "-" + getProject().getProperty("app.version") + ".war";

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

					Pattern pattern = Pattern.compile("<!--[\\s\\S\\n]*?-->");

					Matcher matcher = pattern.matcher(s.toString());
					while (matcher.find()) {
						if (matcher.group().contains("saml")) {
							log("*********************************************************", Project.MSG_ERR);
							log("ALARM! Der Eintrag, der den Suchbegriff  '" + getSearchItem() + "' enthält, ist auskommentiert! ", Project.MSG_ERR);
							log("\r\n", Project.MSG_ERR);
							log(matcher.group(), Project.MSG_ERR);
							log("\r\n", Project.MSG_ERR);
							log("*********************************************************", Project.MSG_ERR);
						}
					}

				}
				zipEntry = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log("FileNotFoundException : " + e.getMessage(), Project.MSG_ERR);

		} catch (IOException e) {
			log("IOException : " + e.getMessage(), Project.MSG_ERR);
		}

	}

	public String getSearchItem() {

		return this.searchItem;
	}

	public void setSearchItem(String searchItem) {

		this.searchItem = searchItem;
	}

}