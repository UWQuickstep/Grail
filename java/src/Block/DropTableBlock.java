/**
 * This file copyright (c) 2015-2016, Jing Fan, Adalbert Gerald Soosai Raj, and Jignesh M. Patel
 *
 * See the file CREDITS.txt in the root directory for details.
 **/

package Block;

/**
 * @brief The SQL block for dropping a table.
 */
public class DropTableBlock extends Block {

  /**
   * @brief Constructor.
   * @param stage The string indicate the stage of this code block.
   * @param indent The indent level.
   * @param tbName The name of the table that should be dropped.
   */
  public DropTableBlock(String stage, int indent, String tbName) {
    super(stage, indent);
    this.append("IF OBJECT_ID('dbo."+ tbName +"', 'U') IS NOT NULL DROP TABLE " + tbName + ";");
    this.sql = this.sb.toString();
  }

}
