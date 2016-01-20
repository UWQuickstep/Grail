/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 * 
 * See the file CREDITS.txt in the root directory for details.
 **/

import java.util.HashMap;

/**
 * @brief This class is a parser. It will read Grail input from file and
 * parse it for the translator.
 *
 */
public class Parser {

  /**
   * @brief Parse program and return the setting as a hashmap.
   * @param filename The name of Grail file.
   * @return A hashmap, where every entry is <setting name, value>.
   */
  static HashMap<String, String> parse(String filename) {
    HashMap<String, String> options = new HashMap<String, String>();
    FileCacheReader fileReader = new FileCacheReader(filename);
    String s = null;
    while (( s = fileReader.getFirst()) != null) {
      String[] splitArray = s.split(":");
      for (int i = 0; i < splitArray.length; ++i) {
        splitArray[i] = splitArray[i].trim();
      }
      
      // Usually UpdateAndSend section has multiple lines.
      // If no ":" can be found, it should be a continuation of
      // UpdateAndSend.
      if (splitArray.length == 1) {
        if (options.get("UpdateAndSend") == null) {
          options.put("UpdateAndSend", splitArray[0]);
        } else {
          options.put("UpdateAndSend", options.get("UpdateAndSend")
            + "\n" + splitArray[0]);
        }
      } else {
        options.put(splitArray[0], splitArray[1]);
      }
      fileReader.rmFirst();
    }

    return options;
  }
}
