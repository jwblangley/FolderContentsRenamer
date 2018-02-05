package jwblangley.filetools;

import java.io.File;
import java.util.Arrays;
import javax.swing.JFileChooser;

public class FolderContentsRenamer {

  public static void main(String[] args) {
    JFileChooser fc = new JFileChooser();
    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      File workingDirectory = fc.getSelectedFile();
      Arrays.stream(workingDirectory.listFiles())
          .filter(File::isFile)
          .forEach(file
              -> prependToFileName(file, workingDirectory.getName(), "_"));
    }
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
