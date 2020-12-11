package net.uxl21.jdatasciencecb;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class UnitApp {

	public static void main(String[] args) {
		ConfigSet configSet = ConfigSet.getInstance();
		Path path = Paths.get(configSet.getString("fileDir") + File.separator + "input");
		
		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					System.out.println("FILE: " + file.getFileName() + " / " + (attrs.size() / 1024L) + " KB");
					return FileVisitResult.CONTINUE;
				}
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
