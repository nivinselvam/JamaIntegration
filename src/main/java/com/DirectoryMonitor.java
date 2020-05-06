package com;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Calendar;

public class DirectoryMonitor {

	public static void main(String[] args) throws IOException, InterruptedException {

		Path resultReportFolder = Paths.get(Initializer.GUI.txtResultReport.getText());
		WatchService watchService = FileSystems.getDefault().newWatchService();
		resultReportFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

		boolean valid = true, found = false;
		do {
			WatchKey watchKey = watchService.take();

			for (WatchEvent event : watchKey.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
					String fileName = event.context().toString();
					if (fileName.contains(Constants.defaultResultReportName)) {
						System.out.println(Constants.logsDateFormat.format(Calendar.getInstance().getTime())
								+  "Execution report has been generated");
						found = true;
					}
				}
			}
			if (found) {
				valid = false;
			} else {
				valid = watchKey.reset();
			}

		} while (valid);

	}
}
