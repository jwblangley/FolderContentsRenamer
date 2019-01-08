package jwblangley.filetools;

import java.io.File;
import java.util.Arrays;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class FolderContentsRenamer extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage primaryStage) {
    DirectoryChooser dc = new DirectoryChooser();
    dc.setTitle("Directory to name files");
    File workingDirectory = dc.showDialog(primaryStage);

    if (workingDirectory != null && workingDirectory.isDirectory()) {
      Arrays.stream(workingDirectory.listFiles())
          .parallel()
          .filter(File::isFile)
          .forEach(file
              -> prependToFileName(file, workingDirectory.getName(), "_"));
    }
    Platform.exit();
  }

  /**
   * Renames a file to the same directory with a prefix prepended to the file
   * name, separated by a joining string
   *
   * @param f File to be renamed
   * @param prefix prefix to prepend to file name
   * @param join string to connect the prefix to the file name
   */
  private static void prependToFileName(File f, String prefix, String join) {
    prependToFileName(f, prefix + join);
  }

  /**
   * Renames a file to the same directory with a prefix prepended to the file
   * name
   *
   * @param f File to be renamed
   * @param prefix prefix to prepend to file name
   */
  private static void prependToFileName(File f, String prefix) {
    String newPath = f.getParent() + "/" + prefix + f.getName();
    File newFile = new File(newPath);
    if (!newFile.exists()) {
      if (!f.getName().startsWith(prefix)) {
        f.renameTo(newFile);
      } else {
        System.out.println(f.getName() + " already prefixed"
            + " - will not rename to " + newFile.getName());
      }
    } else {
      System.out.println(newFile.getName() + " already exists in directory"
          + " - will not overwrite. " + f.getName() + " not renamed");
    }

  }
}
