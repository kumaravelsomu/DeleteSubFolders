package org.main;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Main {
	static String PROPERTY_PATH = "";
	static String C_DRIVE = "C:\\Program Files (x86)";
	//static String E_DRIVE = "C:\\Users\\kumsomu\\Documents\\AutomationTest";
	static String E_DRIVE = "E:\\EMEA1";
	static int FOLDER_EXPIRY_DAYS = 20;
	static String HIS_FOL_DATE_FORMAT = "dd-MM-yyy HH-mm-ss";
	static String FOL_TO_BE_DELETED = "scoped_dir";
	static String HISTORY_PATH = "\\output\\history";

	public static void main(String[] args) throws ParseException {
		// Specify the path to the folder containing subfolders to be deleted
		//String folderPath = C_DRIVE;

		// Call the method to delete subfolders
		cleanTempFolders(C_DRIVE);
		deleteHistoryFolders(E_DRIVE);
	}

	public static void cleanTempFolders(String folderPath) {
		File folder = new File(folderPath);

		// Check if the specified path exists and is a directory
		if (folder.exists() && folder.isDirectory()) {
			// List all files and subdirectories in the folder
			File[] filesAndDirs = folder.listFiles();

			if (filesAndDirs != null) {
				for (File fileOrDir : filesAndDirs) {
					// Check if it is a subfolder
					if (fileOrDir.isDirectory() && fileOrDir.getName().startsWith(FOL_TO_BE_DELETED)) {
						// Recursively delete the subfolder and its contents
						
						deleteSubfolder(fileOrDir);
						System.out.println("Deleted => "+fileOrDir);
					}
				}
			}
		} else {
			System.out.println("The specified folder does not exist or is not a directory.");
		}
	}

	private static void deleteHistoryFolders(String folderPath) throws ParseException {

		File folder = new File(folderPath);
		List<String> historyFolders = new LinkedList();
		Utils utl = new Utils();

		// Check if the specified path exists and is a directory
		if (folder.exists() && folder.isDirectory()) {
			// List all files and subdirectories in the folder
			File[] filesAndDirs = folder.listFiles();

			if (filesAndDirs != null) {

				for (File fileOrDir : filesAndDirs) {
					if (fileOrDir.isDirectory()) {
						String path = fileOrDir.toString() + HISTORY_PATH;
						File history = new File(path);
						if (history.exists()) {
							historyFolders.add(path);
						}

					}
				}
				System.out.println(historyFolders);
				System.out.println("===================================================");

				for (int i = 0; i < historyFolders.size(); i++) {
					File historyFolder = new File(historyFolders.get(i));
					String[] list = historyFolder.list();
					for (int j = 0; j < list.length; j++) {
						//System.out.println(list[j]);
						Date date = utl.convertStringtoDate(list[j], HIS_FOL_DATE_FORMAT);
						if (utl.isFileOlder(date, FOLDER_EXPIRY_DAYS)) {
							String del = historyFolders.get(i) + "\\" + list[j];
							//System.out.println(del + " should be deleted!!");
							// new File(list[j]).delete();
							deleteSubfolder(new File(del));
							System.out.println("Deleted => "+del);
						}
					}

				}
			}
		}
	}

	public static void deleteSubfolder(File subfolder) {
		if (subfolder.exists() && subfolder.isDirectory()) {
			File[] filesAndDirs = subfolder.listFiles();
			if (filesAndDirs != null) {
				for (File fileOrDir : filesAndDirs) {
					if (fileOrDir.isDirectory()) {
						// Recursively delete sub-subfolders
						deleteSubfolder(fileOrDir);
					} else {
						// Delete files within the subfolder
						fileOrDir.delete();
					}
				}
			}
			// Finally, delete the subfolder itself
			subfolder.delete();
			//System.out.println("Deleted subfolder: " + subfolder.getAbsolutePath());
		}
	}

	private static String getAppPath() {
		String applicationPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		return new File(applicationPath).getParentFile().getParentFile().getAbsolutePath();
	}
}
