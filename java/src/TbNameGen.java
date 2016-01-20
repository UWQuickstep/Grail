/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 * 
 * See the file CREDITS.txt in the root directory for details.
 **/
 
/**
 * @brief Generate next available table name. The table name is used for
 * auxiliary tables, in the format of "Table" + tableid. The policy is simple :
 * the table id grows monotonically. There is no reuse here.
 *
 */
public class TbNameGen {
    // Current table id.
  static int curIdx = 0;

  /**
   * @brief Get the next available table name.
   * @return The new table name.
   */
  public static String getNextTbName() {
    return "Table" + (curIdx++);
  }
}
