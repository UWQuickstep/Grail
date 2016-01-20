/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 * 
 * See the file CREDITS.txt in the root directory for details.
 **/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @brief A reader to handle user input file. It reads the user file into
 * memory. The user can get next line in the file by calling getFirst() and
 * move to next line by calling rmFirst().
 */
public class FileCacheReader {
  // Buffered lines.
  ArrayList<String> buffer = new ArrayList<String>();
  // Current read pointer in the buffer.
  int curIdx = 0;

  /**
   * @brief Constructor.
   * @param filename The input file name.
   */
  public FileCacheReader(String filename) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(new File(
          filename)));
      String s;
      while (( s = br.readLine()) != null) {
        // Skip all the empty lines.
        s = s.trim();
          if (s.equals("")) continue;
        buffer.add(s);
      }
      br.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @brief Read next line from the input stream.
   * @return The next line.
   */
  public String getFirst() {
    if (curIdx < buffer.size()) {
      return buffer.get(curIdx);
    } else {
      return null;
    }
  }

  /**
   * @brief Shift read pointer.
   */
  void rmFirst() {
    ++curIdx;
  }
}
